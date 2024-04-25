package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.render.QuadDamageGlintRenderer;
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
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements QuadDamageGlintRenderer.QuadDamageVisibility {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Boolean> QUAD_DAMAGE_VISIBLE = DataTracker.registerData(LivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

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

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initQuadDamageTracker(CallbackInfo ci) {
        dataTracker.startTracking(QUAD_DAMAGE_VISIBLE, false);
    }

    public boolean hasQuadDamage() {
        return dataTracker.get(QUAD_DAMAGE_VISIBLE);
    }
}
