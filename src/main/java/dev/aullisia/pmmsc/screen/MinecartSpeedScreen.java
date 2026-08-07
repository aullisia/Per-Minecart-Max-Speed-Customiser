package dev.aullisia.pmmsc.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiserConfig;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//? if <26.1 {
import net.minecraft.client.gui.GuiGraphics;
//?} else {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
 *///?}
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
//? if <1.21.6 {
import net.minecraft.client.renderer.RenderType;
//?}
//? if >=1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///?}
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class MinecartSpeedScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            PerMinecartMaxSpeedCustomiser.MOD_ID,
            "textures/gui/container/minecart_max_speed_menu.png"
    );

    private final AbstractMinecart minecart;
    private EditBox speedInput;
    private double currentSpeed = -1;
    private boolean suppressUpdate = false;

    // Screen position variables
    private int x;
    private int y;
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 166;
    private int titleX;
    private int titleY = 6;

    public MinecartSpeedScreen(Component title, AbstractMinecart minecart) {
        super(title);
        this.minecart = minecart;
    }

    @Override
    protected void init() {
        super.init();

        x = (width - backgroundWidth) / 2;
        y = (height - backgroundHeight) / 2;

        titleX = (backgroundWidth - font.width(title)) / 2;

        speedInput = new EditBox(font, x + 90, y + 70, 60, 20, Component.nullToEmpty("Speed"));
        if (currentSpeed <= -1) {
            speedInput.setValue("Gamerule");
        } else {
            speedInput.setValue(String.valueOf(currentSpeed));
        }
        speedInput.setResponder(value -> {
            if (value == null || value.isEmpty()) return;
            try {
                double speed = Double.parseDouble(value);
                if (speed > PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get())
                    speedInput.setValue(String.valueOf(PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()));
                if (suppressUpdate) return;
                if (speed == currentSpeed) return;
                sendSpeedPacket(speed);
            } catch (NumberFormatException ignored) {
            }
        });
        this.addWidget(speedInput);

        Button addButton = Button.builder(Component.nullToEmpty("+"), (btn) -> {
            double max = PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get();
            double newSpeed = Math.min(currentSpeed + 1, max);
            sendSpeedPacket(newSpeed);
        }).bounds(x + 70, y + 70, 20, 20).build();
        this.addRenderableWidget(addButton);

        Button subtractButton = Button.builder(Component.nullToEmpty("-"), (btn) -> {
            double newSpeed = Math.max(currentSpeed - 1, -1);
            sendSpeedPacket(newSpeed);
        }).bounds(x + 150, y + 70, 20, 20).build();
        this.addRenderableWidget(subtractButton);
    }

    private void sendSpeedPacket(double speed) {
        ClientPlayNetworking.send(new MinecartMaxSpeedPayload(speed));
    }

    public void updateSpeedField(double newSpeed) {
        if (newSpeed == currentSpeed) return;
        currentSpeed = newSpeed;
        if (!speedInput.isFocused()) {
            suppressUpdate = true;
            if (newSpeed <= -1) {
                speedInput.setValue("Gamerule");
            } else {
                speedInput.setValue(String.valueOf(newSpeed));
            }
            suppressUpdate = false;
        } else if (newSpeed > PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()) {
            speedInput.setValue(String.valueOf(PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()));
        }
    }


    //? if <26.1 {
    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBase(context);
        drawBackground(context, delta, mouseX, mouseY);
        drawForeground(context, mouseX, mouseY);
        super.render(context, mouseX, mouseY, delta);
        speedInput.render(context, mouseX, mouseY, delta);

    }

    private void drawBackground(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 + 40;
        //? if <1.21.6 {
        context.blit(RenderType::guiTextured, TEXTURE, x, y, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);
         //?}
        //? if >=1.21.6 {
        /*context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        *///?}

    }

    private void drawForeground(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(font, title, x + titleX, y + titleY + 40, -12566464, false);
        context.drawString(font, Component.nullToEmpty("Max Speed"), x + 95, y + titleY + 55, -12566464, false);
    }

    private void renderBase(GuiGraphics context) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
        //? if <1.21.6 {
        this.renderBlurredBackground();
        //?}
    }
    //?} else {
    /*@Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        renderBase(context);
        drawBackground(context, delta, mouseX, mouseY);
        drawForeground(context, mouseX, mouseY);
        super.extractRenderState(context, mouseX, mouseY, delta);
        speedInput.extractRenderState(context, mouseX, mouseY, delta);
    }

    private void drawBackground(GuiGraphicsExtractor context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 + 40;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
    }

    private void drawForeground(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        context.text(font, title, x + titleX, y + titleY + 40, -12566464, false);
        context.text(font, Component.nullToEmpty("Max Speed"), x + 95, y + titleY + 55, -12566464, false);
    }

    private void renderBase(GuiGraphicsExtractor context) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
    }
    *///?}

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    //? if <1.21.6 {
    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
    }
    //?}
}
