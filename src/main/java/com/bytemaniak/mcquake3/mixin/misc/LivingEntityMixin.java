package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.entity.ItemEntityGotoNonHotbar;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.*;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements QuakePlayer {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow public abstract float getHealth();

    @Unique private static final TrackedData<Boolean> QUAD_DAMAGE_VISIBLE = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

    public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

    @WrapOperation(method = "onDamaged", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;hurtTime:I", opcode = Opcodes.PUTFIELD))
    // Don't show the vanilla damage hurt tilt when damaged by Quake weapons as some of them fire multiple times per second
    private void cancelHurtTilt(LivingEntity entity, int value, Operation<Void> original) {
        DamageSource lastDamageSource = entity.getRecentDamageSource();
        if (lastDamageSource != null && entity instanceof PlayerEntity && lastDamageSource.getName().contains("mcquake3"))
            entity.hurtTime = 0;
        else original.call(entity, value);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;sendEquipmentChanges()V"))
    // Send synced states for Railgun and Lightning Gun ammo to allow trails coming from other players to render in survival
    private void sendAmmoUpdatesWeaponTrailFix(LivingEntity entity, Operation<Void> original) {
        if (entity instanceof PlayerEntity player) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeUuid(player.getUuid());
            buf.writeBoolean(player.getInventory().getSlotWithStack(new ItemStack(Weapons.LIGHTNING_CELL)) > -1);
            buf.writeBoolean(player.getInventory().getSlotWithStack(new ItemStack(Weapons.RAILGUN_ROUND)) > -1);

            for (ServerPlayerEntity plr : PlayerLookup.tracking(player))
                ServerPlayNetworking.send(plr, Packets.PLAYER_AMMO_TRAIL_FIX, buf);
        }

        original.call(entity);
    }

    @WrapOperation(method = "tickStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updatePotionVisibility()V"))
    // Update visibility of Quake damage effect when the visibility for other effects is changed as well
    private void updateQuadDamageVisibility(LivingEntity entity, Operation<Void> original) {
        dataTracker.set(QUAD_DAMAGE_VISIBLE, hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE));
        original.call(entity);
    }

    @WrapOperation(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    // Respawn player to a custom arena spawnpoint if playing in Quake dimension instead of killing them
    private void respawnQuakePlayer(LivingEntity entity, DamageSource damageSource, Operation<Void> original) {
        if (entity instanceof ServerPlayerEntity player && ((QuakePlayer)player).mcquake3$inQuakeArena() &&
                player.getWorld().getDimensionKey() == Blocks.Q3_DIMENSION_TYPE &&
                !player.isCreative() && !player.isSpectator()) {
            ServerWorld world = player.getWorld();
            QuakeArenasParameters.ArenaData arena = ServerEvents.QUAKE_MATCH_STATE.arena;

            if (player.getMainHandStack().getItem() instanceof Weapon weapon) {
                for (int i = PlayerInventory.getHotbarSize(); i < player.getInventory().size(); ++i) {
                    ItemStack itemStack = player.getInventory().getStack(i);
                    if (itemStack.isOf(weapon.ammoType)) {
                        int countLeft = Weapon.MAX_AMMO - MiscUtils.getCountOfItemType(player.getInventory(), weapon.ammoType);
                        int count = Math.min(weapon.ammoBoxCount/2, countLeft);
                        ItemStack ammoDrop = new ItemStack(weapon.ammoType, count);
                        ItemEntityGotoNonHotbar ammoEntity = new ItemEntityGotoNonHotbar(world, player.getX(), player.getY(), player.getZ(), ammoDrop, 198);
                        ammoEntity.setPickupDelay(20);
                        world.spawnEntity(ammoEntity);
                        break;
                    }
                }
            }

            world.playSound(null, player.getBlockPos(), ((QuakePlayer)player).mcquake3$getPlayerDeathSound(), SoundCategory.PLAYERS);

            ServerEvents.QUAKE_MATCH_STATE.spawnQuakePlayer(player, arena);
            ServerEvents.QUAKE_MATCH_STATE.recordDeath(player, damageSource);

            return;
        }

        original.call(entity, damageSource);
    }

    @WrapOperation(method = "playHurtSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent playQuakePlayerHurtSound(LivingEntity entity, DamageSource source, Operation<SoundEvent> original) {
        if (entity instanceof PlayerEntity player && ((QuakePlayer)player).mcquake3$quakePlayerSoundsEnabled())
            return ((QuakePlayer)player).mcquake3$getPlayerHurtSound(source);

        return original.call(entity, source);
    }

    @WrapOperation(method = "onDamaged", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent playQuakePlayerHurtSound2(LivingEntity entity, DamageSource source, Operation<SoundEvent> original) {
        if (entity instanceof PlayerEntity player && ((QuakePlayer)player).mcquake3$quakePlayerSoundsEnabled())
            return ((QuakePlayer)player).mcquake3$getPlayerHurtSound(source);

        return original.call(entity, source);
    }

    @WrapOperation(method = "handleStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent playQuakePlayerDeathSound(LivingEntity entity, Operation<SoundEvent> original) {
        if (entity instanceof PlayerEntity player && ((QuakePlayer)player).mcquake3$quakePlayerSoundsEnabled())
            return ((QuakePlayer)player).mcquake3$getPlayerDeathSound();

        return original.call(entity);
    }

    @WrapOperation(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;"))
    private SoundEvent playQuakePlayerDeathSound2(LivingEntity entity, Operation<SoundEvent> original) {
        if (entity instanceof PlayerEntity player && ((QuakePlayer)player).mcquake3$quakePlayerSoundsEnabled())
            return ((QuakePlayer)player).mcquake3$getPlayerDeathSound();

        return original.call(entity);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initQuadDamageTracker(CallbackInfo ci) {
        dataTracker.startTracking(QUAD_DAMAGE_VISIBLE, false);
    }

    public boolean mcquake3$hasQuadDamage() {
        return dataTracker.get(QUAD_DAMAGE_VISIBLE);
    }
}
