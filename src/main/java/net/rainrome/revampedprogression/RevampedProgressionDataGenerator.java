package net.rainrome.revampedprogression;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.rainrome.revampedprogression.datagen.ModBlockTagProvider;
import net.rainrome.revampedprogression.datagen.ModItemTagProvider;
import net.rainrome.revampedprogression.datagen.ModLootTableProvider;
import net.rainrome.revampedprogression.datagen.ModModelProvider;

public class RevampedProgressionDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);

	}
}
