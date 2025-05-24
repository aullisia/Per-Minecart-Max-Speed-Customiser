package dev.aullisia.pmmsc.item.custom;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.network.ModNetwork;
import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WrenchItem extends Item {
    public WrenchItem(Settings settings) {
        super(settings);
    }

    private static final Map<UUID, AbstractMinecartEntity> MODIFYING_CARTS = new HashMap<>();

    public static void useWrench(PlayerEntity player, AbstractMinecartEntity cart, Hand hand) {
        MODIFYING_CARTS.put(player.getUuid(), cart);
        player.setCurrentHand(hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            AbstractMinecartEntity cart = MODIFYING_CARTS.get(player.getUuid());
            if (cart != null) {
                double scroll = ModNetwork.WRENCH_SCROLL_VALUES.getOrDefault(player.getUuid(), 0.0);
                ModNetwork.WRENCH_SCROLL_VALUES.remove(player.getUuid());

                double currentSpeed = ((CustomMaxSpeedAccessor) cart).getCustomMaxSpeed();
                double newSpeed = Math.max(-1, currentSpeed + scroll * 0.5);
                ((CustomMaxSpeedAccessor) cart).setCustomMaxSpeed(newSpeed);

                if (!world.isClient) {
                    if (newSpeed > 0) {
                        player.sendMessage(Text.literal("Maximum Speed: " + newSpeed), true);
                    } else {
                        player.sendMessage(Text.literal("Maximum Speed: Default"), true);
                    }
                }
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            MODIFYING_CARTS.remove(player.getUuid());
            ModNetwork.WRENCH_SCROLL_VALUES.remove(player.getUuid());
        }
        return true;
    }
}
