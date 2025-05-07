package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin implements CustomMaxSpeedAccessor {
    @Unique
    private double customMaxSpeed = -1.0D;

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomMaxSpeed(NbtCompound nbt, CallbackInfo ci) {
        nbt.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomMaxSpeed(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("CustomMaxSpeed")) {
            this.customMaxSpeed = nbt.getDouble("CustomMaxSpeed");
        }
    }

    public double getCustomMaxSpeed() {
        return this.customMaxSpeed;
    }

    public void setCustomMaxSpeed(double speed) {
        this.customMaxSpeed = speed;
    }
}
