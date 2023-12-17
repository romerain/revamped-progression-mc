package net.rainrome.revampedprogression.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.block.ModBlocks;
import net.rainrome.revampedprogression.RevampedProgression;

public class ModItems {

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(RevampedProgression.MOD_ID, name), item);
    }
    public static void registerModItems() {
        RevampedProgression.LOGGER.info("Registering items for: " + RevampedProgression.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::ingredientsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItems::functionalItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::buildingBlocksItemGroup);
    }

    //ITEMS

    public static final Item DIRT_PILE = registerItem("dirt-pile", new Item(new FabricItemSettings()));
    public static final Item SAND_PILE = registerItem("sand-pile", new Item(new FabricItemSettings()));

    public static final Item CLAY_VESSEL = registerItem("clay-vessel", new Item(new FabricItemSettings()));
    public static final Item CLAY_JUG = registerItem("clay-jug", new Item(new FabricItemSettings()));


    //ITEM GROUPS

    private static void ingredientsItemGroup(FabricItemGroupEntries entries) {
        entries.add(DIRT_PILE);
        entries.add(SAND_PILE);
    }

    private static void functionalItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.KILN_BLOCK);
    }

    private static void buildingBlocksItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.COB_BLOCK);
    }

}
