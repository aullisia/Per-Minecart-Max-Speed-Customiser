package dev.aullisia.pmmsc.mixin;

import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.network.packet.WrenchScrollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && client.player.isInSneakingPose()) {
            ItemStack mainHand = client.player.getMainHandStack();
            if (mainHand.getItem() == ModItems.WRENCH) {
                ci.cancel(); // Prevent default scroll behavior
                ClientPlayNetworking.send(new WrenchScrollPayload(vertical));
            }
        }
    }
}
