package dev.aullisia.pmmsc.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiserConfig;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//? if >=1.21.6 {
/*import net.minecraft.client.gl.RenderPipelines;
*///?}
//? if <1.21.5 {
import net.minecraft.client.gl.ShaderProgramKeys;
 //?}
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MinecartSpeedScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(
            PerMinecartMaxSpeedCustomiser.MOD_ID,
            "textures/gui/container/minecart_max_speed_menu.png"
    );

    private final AbstractMinecartEntity minecart;
    private TextFieldWidget speedInput;
    private double currentSpeed = -1;
    private boolean suppressUpdate = false;

    // Screen position variables
    private int x;
    private int y;
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 166;
    private int titleX;
    private int titleY = 6;

    public MinecartSpeedScreen(Text title, AbstractMinecartEntity minecart) {
        super(title);
        this.minecart = minecart;
    }

    @Override
    protected void init() {
        super.init();

        x = (width - backgroundWidth) / 2;
        y = (height - backgroundHeight) / 2;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        speedInput = new TextFieldWidget(textRenderer, x + 90, y + 70, 60, 20, Text.of("Speed"));
        if (currentSpeed <= -1) {
            speedInput.setText("Gamerule");
        } else {
            speedInput.setText(String.valueOf(currentSpeed));
        }
        speedInput.setChangedListener(value -> {
            if (value == null || value.isEmpty()) return;
            try {
                double speed = Double.parseDouble(value);
                if (speed > PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get())
                    speedInput.setText(String.valueOf(PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()));
                if (suppressUpdate) return;
                if (speed == currentSpeed) return;
                sendSpeedPacket(speed);
            } catch (NumberFormatException ignored) {
            }
        });
        this.addSelectableChild(speedInput);

        ButtonWidget addButton = ButtonWidget.builder(Text.of("+"), (btn) -> {
            double max = PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get();
            double newSpeed = Math.min(currentSpeed + 1, max);
            sendSpeedPacket(newSpeed);
        }).dimensions(x + 70, y + 70, 20, 20).build();
        this.addDrawableChild(addButton);

        ButtonWidget subtractButton = ButtonWidget.builder(Text.of("-"), (btn) -> {
            double newSpeed = Math.max(currentSpeed - 1, -1);
            sendSpeedPacket(newSpeed);
        }).dimensions(x + 150, y + 70, 20, 20).build();
        this.addDrawableChild(subtractButton);
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
                speedInput.setText("Gamerule");
            } else {
                speedInput.setText(String.valueOf(newSpeed));
            }
            suppressUpdate = false;
        } else if (newSpeed > PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()) {
            speedInput.setText(String.valueOf(PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get()));
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBase(context);
        drawBackground(context, delta, mouseX, mouseY);
        drawForeground(context, mouseX, mouseY);
        super.render(context, mouseX, mouseY, delta);
        speedInput.render(context, mouseX, mouseY, delta);

    }

    private void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 + 40;
        //? if <1.21.6 {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);
         //?}
        //? if >=1.21.6 {
        /*context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
        *///?}

    }

    private void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, title, x + titleX, y + titleY + 40, -12566464, false);
        context.drawText(textRenderer, Text.of("Max Speed"), x + 95, y + titleY + 55, -12566464, false);
    }

    private void renderBase(DrawContext context) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
        //? if <1.21.6 {
        this.applyBlur();
        //?}
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    //? if <1.21.6 {
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }
    //?}
}
