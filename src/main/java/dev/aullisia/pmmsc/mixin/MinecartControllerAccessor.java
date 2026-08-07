package dev.aullisia.pmmsc.mixin;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(net.minecraft.world.entity.vehicle.MinecartBehavior.class)
public interface MinecartControllerAccessor {
    @Accessor("minecart")
    AbstractMinecart getMinecart();
}
