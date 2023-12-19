package net.rainrome.revampedprogression.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.block.ModBlocks;
import net.rainrome.revampedprogression.RevampedProgression;
import net.rainrome.revampedprogression.block.custom.KilnBlock;

public class ModBlockEntities {
    public static final BlockEntityType<KilnBlockEntity> KILN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RevampedProgression.MOD_ID, "kiln-block-entity"),
                    FabricBlockEntityTypeBuilder.create(KilnBlockEntity::new, ModBlocks.KILN_BLOCK).build());

    public static void registerBlockEntities() {
        RevampedProgression.LOGGER.info("Registering block entities for " + RevampedProgression.MOD_ID);
        ((KilnBlock) ModBlocks.KILN_BLOCK).setType(KILN_BLOCK_ENTITY);
    }
}
