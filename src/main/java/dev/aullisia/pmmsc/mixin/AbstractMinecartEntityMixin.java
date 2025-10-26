package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
//? if >=1.21.6 {
/*import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
*///?}
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin implements CustomMaxSpeedAccessor {
    @Unique
    private double customMaxSpeed = -1.0D;

    //? if <=1.21.4 {
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
    //?} elif =1.21.5 {
    /*@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomMaxSpeed(NbtCompound nbt, CallbackInfo ci) {
        nbt.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomMaxSpeed(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("CustomMaxSpeed")) {
            Optional<Double> optionalSpeed = nbt.getDouble("CustomMaxSpeed");
            optionalSpeed.ifPresent(speed -> this.customMaxSpeed = speed);
        }
    }
    *///?} else {
    /*@Inject(method = "writeCustomData", at = @At("TAIL"))
    private void writeCustomMaxSpeed(WriteView view, CallbackInfo ci) {
        view.putDouble("CustomMaxSpeed", this.customMaxSpeed);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void readCustomMaxSpeed(ReadView view, CallbackInfo ci) {
        this.customMaxSpeed = view.getDouble("CustomMaxSpeed", -1.0);
    }
    *///?}

    public double getCustomMaxSpeed() {
        return this.customMaxSpeed;
    }

    public void setCustomMaxSpeed(double speed) {
        this.customMaxSpeed = speed;
    }
}