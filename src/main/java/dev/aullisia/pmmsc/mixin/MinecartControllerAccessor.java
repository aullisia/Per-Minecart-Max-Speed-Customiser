package dev.aullisia.pmmsc.mixin;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(net.minecraft.entity.vehicle.MinecartController.class)
public interface MinecartControllerAccessor {
    @Accessor("minecart")
    AbstractMinecartEntity getMinecart();
}
