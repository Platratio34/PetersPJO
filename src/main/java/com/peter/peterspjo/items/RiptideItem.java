package com.peter.peterspjo.items;

import java.util.List;

import com.peter.peterspjo.PJO;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RiptideItem extends Sword {

    public static final String NAME = "riptide";
    public static final Identifier ID = new Identifier(PJO.NAMESPACE, NAME);

    public static final RiptideItem ITEM = Registry.register(Registries.ITEM, ID, new RiptideItem(
            new FabricItemSettings()));

    public static void register() {
    }

    public boolean isSword = false;

    public RiptideItem(Settings settings) {
        super(CelestialBronzeMaterial.INSTANCE, 8, 0.2f, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item." + PJO.NAMESPACE + "." + NAME + ".tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 10;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.putBoolean("is_sword", !nbt.getBoolean("is_sword"));
            stack.setNbt(nbt);
            user.getItemCooldownManager().set(this, 1);
        }
        return super.use(world, user, hand);
    }

}
