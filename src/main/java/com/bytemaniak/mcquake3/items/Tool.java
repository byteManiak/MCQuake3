package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class Tool extends Item{
    public Tool() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Used to change orientation and properties"));
        tooltip.add(Text.of("of MCQuake3 props (Jumppads, Portals etc)."));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));

        QuakePlayer player = (QuakePlayer) user;
        player.setLinkedPortalCoords();
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
