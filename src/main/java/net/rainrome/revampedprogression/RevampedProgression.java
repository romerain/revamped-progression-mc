package net.rainrome.revampedprogression;

import net.fabricmc.api.ModInitializer;

import net.rainrome.revampedprogression.block.ModBlocks;
import net.rainrome.revampedprogression.block.entity.ModBlockEntities;
import net.rainrome.revampedprogression.item.ModItems;
import net.rainrome.revampedprogression.recipe.ModRecipes;
import net.rainrome.revampedprogression.screen.ModScreenHandlers;
import net.rainrome.revampedprogression.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevampedProgression implements ModInitializer {
	public static final String MOD_ID = "revamped-progression";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing mod:" + MOD_ID);

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();

		ModLootTableModifiers.modifyLootTables();
	}
}