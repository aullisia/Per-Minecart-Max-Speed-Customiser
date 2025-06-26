package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ExperimentalMinecartController.class)
public class ExperimentalMinecartControllerMixin {
    /**
     * Overwrites the default max speed calculation for minecarts to support custom per-minecart speeds.
     * If a custom speed is set (greater than 0), it is used instead of the global game rule.
     * @author aullisia
     * @reason Allow individual minecarts to have custom speeds instead of a single global game rule.
     */
    @Overwrite
    public double getMaxSpeed(ServerWorld world) {
        AbstractMinecartEntity minecart = ((MinecartControllerAccessor) this).getMinecart();
        double customMaxSpeed = ((CustomMaxSpeedAccessor) minecart).getCustomMaxSpeed();

        if (customMaxSpeed > 0) {
            return customMaxSpeed * (minecart.isTouchingWater() ? (double) 0.5F : (double) 1.0F) / (double) 20.0F;
        } else {
            return (double) world.getGameRules().getInt(GameRules.MINECART_MAX_SPEED) * (minecart.isTouchingWater() ? (double) 0.5F : (double) 1.0F) / (double) 20.0F;
        }
    }
}
