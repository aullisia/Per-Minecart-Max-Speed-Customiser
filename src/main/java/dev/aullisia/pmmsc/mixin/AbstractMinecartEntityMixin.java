package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
//? if >=1.21.6 {
/*import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
*///?}

@Mixin(AbstractMinecart.class)
public class AbstractMinecartEntityMixin implements CustomMaxSpeedAccessor {
    @Unique
    private double customMaxSpeed = -1.0D;

    //? if <=1.21.4 {
    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void writeCustomMaxSpeed(CompoundTag nbt, CallbackInfo ci) {
        nbt.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readCustomMaxSpeed(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("CustomMaxSpeed")) {
            this.customMaxSpeed = nbt.getDouble("CustomMaxSpeed");
        }
    }
    //?} elif =1.21.5 {
    /*@Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void writeCustomMaxSpeed(CompoundTag nbt, CallbackInfo ci) {
        nbt.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void readCustomMaxSpeed(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("CustomMaxSpeed")) {
            Optional<Double> optionalSpeed = nbt.getDouble("CustomMaxSpeed");
            optionalSpeed.ifPresent(speed -> this.customMaxSpeed = speed);
        }
    }
    *///?} else {
    /*@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeCustomMaxSpeed(ValueOutput view, CallbackInfo ci) {
        view.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readCustomMaxSpeed(ValueInput view, CallbackInfo ci) {
        this.customMaxSpeed = view.getDoubleOr("CustomMaxSpeed", -1.0);
    }
    *///?}

    public double getCustomMaxSpeed() {
        return this.customMaxSpeed;
    }

    public void setCustomMaxSpeed(double speed) {
        this.customMaxSpeed = speed;
    }
}