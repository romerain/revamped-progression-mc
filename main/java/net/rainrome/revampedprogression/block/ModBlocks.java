package net.rainrome.revampedprogression.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.block.custom.KilnBlock;
import net.rainrome.revampedprogression.RevampedProgression;

public class ModBlocks {
    private static Block registerBlock(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(RevampedProgression.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
        return Registry.register(Registries.BLOCK, new Identifier(RevampedProgression.MOD_ID, name), block);
    }
    public static void registerModBlocks() {
        RevampedProgression.LOGGER.info("Registering blocks for: " + RevampedProgression.MOD_ID);
    }

    //BLOCKS

    public static final Block COB_BLOCK = registerBlock( "cob-block", new Block(FabricBlockSettings.copyOf(Blocks.BROWN_TERRACOTTA)));

    public static final Block KILN_BLOCK = registerBlock("kiln-block", new KilnBlock(FabricBlockSettings.copyOf(Blocks.BROWN_TERRACOTTA).nonOpaque()));

}
