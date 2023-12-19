package net.rainrome.revampedprogression;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.rainrome.revampedprogression.screen.KilnScreen;
import net.rainrome.revampedprogression.screen.ModScreenHandlers;

public class RevampedProgressionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.KILN_SCREEN_HANDLER, KilnScreen::new);

    }
}
