package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NewMinecartBehavior.class)
public class ExperimentalMinecartControllerMixin {
    /**
     * Overwrites the default max speed calculation for minecarts to support custom per-minecart speeds.
     * If a custom speed is set (greater than 0), it is used instead of the global game rule.
     * @author aullisia
     * @reason Allow individual minecarts to have custom speeds instead of a single global game rule.
     */
    @Overwrite
    public double getMaxSpeed(ServerLevel world) {
        AbstractMinecart minecart = ((MinecartControllerAccessor) this).getMinecart();
        double customMaxSpeed = ((CustomMaxSpeedAccessor) minecart).getCustomMaxSpeed();

        if (customMaxSpeed >= 0) {
            return customMaxSpeed * (minecart.isInWater() ? (double) 0.5F : (double) 1.0F) / (double) 20.0F;
        } else {
            //? if >=1.21.11 {
            /*return (double) world.getGameRules().get(GameRules.MAX_MINECART_SPEED) * (minecart.isInWater() ? (double) 0.5F : (double) 1.0F) / (double) 20.0F;
            *///?}
            //? if <1.21.11 {
            return (double) world.getGameRules().getInt(GameRules.RULE_MINECART_MAX_SPEED) * (minecart.isInWater() ? (double) 0.5F : (double) 1.0F) / (double) 20.0F;
             //?}
        }
    }
}
