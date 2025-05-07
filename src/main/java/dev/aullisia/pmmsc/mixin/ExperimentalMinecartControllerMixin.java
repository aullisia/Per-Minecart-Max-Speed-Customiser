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
    @Overwrite
    public double getMaxSpeed(ServerWorld world) {
        AbstractMinecartEntity minecart = ((MinecartControllerAccessor) this).getMinecart();
        double customMaxSpeed = ((CustomMaxSpeedAccessor) minecart).getCustomMaxSpeed();

        if (customMaxSpeed > 0) {
            return customMaxSpeed * (minecart.isTouchingWater() ? (double)0.5F : (double)1.0F) / (double)20.0F;
        } else {
            return (double)world.getGameRules().getInt(GameRules.MINECART_MAX_SPEED) * (minecart.isTouchingWater() ? (double)0.5F : (double)1.0F) / (double)20.0F;
        }
    }
}
