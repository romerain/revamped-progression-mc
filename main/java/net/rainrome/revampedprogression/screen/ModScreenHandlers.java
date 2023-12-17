package net.rainrome.revampedprogression.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.RevampedProgression;

public class ModScreenHandlers {
    public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(RevampedProgression.MOD_ID, "kiln-smelting"),
                    new ExtendedScreenHandlerType<>(KilnScreenHandler::new));

    public static void registerScreenHandlers() {
        RevampedProgression.LOGGER.info("Registering screen handlers for " + RevampedProgression.MOD_ID);
    }
}
