package net.rainrome.revampedprogression.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.RevampedProgression;

public class ModRecipes {
    public static void registerRecipes() {
        RevampedProgression.LOGGER.info("Registering recipe types");
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(RevampedProgression.MOD_ID, KilnRecipe.Serializer.ID),
                KilnRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(RevampedProgression.MOD_ID, KilnRecipe.Type.ID),
                KilnRecipe.Type.INSTANCE);
    }
}
