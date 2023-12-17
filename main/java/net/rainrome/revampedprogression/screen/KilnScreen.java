package net.rainrome.revampedprogression.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.RevampedProgression;

public class KilnScreen extends HandledScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(RevampedProgression.MOD_ID, "textures/gui/kiln-gui.png");
    private static final Identifier PROGRESS_BAR = new Identifier(RevampedProgression.MOD_ID, "textures/gui/kiln-progress-bar.png");
    private static final Identifier FIRE_EFFECTS = new Identifier(RevampedProgression.MOD_ID, "textures/gui/kiln-fire-effects.png");
    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        titleX = 1000;
        playerInventoryTitleX = 1000;

    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressEffects(context, x, y);
    }

    private void renderProgressEffects(DrawContext context, int x, int y) {
        if(handler.isOn()) {
            int barProgress = (int) (56 * (handler.getMaxProgress() - handler.getProgress()) / handler.getMaxProgress());
            int fireProgress = (int) (27 * (handler.getMaxProgress() - handler.getProgress()) / handler.getMaxProgress());

            context.drawTexture(PROGRESS_BAR, x + 143, y + (70 - barProgress),
                    0, 56 - barProgress, 9, barProgress);
            context.drawTexture(FIRE_EFFECTS, x + 50, y + (47 - fireProgress),
                    0, 27 - fireProgress, 73, fireProgress);
        }
        else {
            int fuelLevel = (int) (56 * handler.getFuelLevel() / handler.getFullFuelLevel());
            context.drawTexture(PROGRESS_BAR, x + 143, y + (70 - fuelLevel),
                    0, 56 - fuelLevel, 9, fuelLevel);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
