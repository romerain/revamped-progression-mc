package net.rainrome.revampedprogression.util;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.rainrome.revampedprogression.RevampedProgression;
import net.rainrome.revampedprogression.block.entity.ImplementedInventory;
import org.jetbrains.annotations.Nullable;

public class ModBlockWithEntityBase extends BlockWithEntity implements BlockEntityProvider {

    protected BlockEntityType<? extends ModBlockEntityBase> type;

    public void setType(BlockEntityType<? extends ModBlockEntityBase> type) {
        this.type = type;
    }

    ///

    protected ModBlockWithEntityBase(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    ///

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            assert blockEntity != null;
            if (blockEntity.getType() == type) {
                ItemScatterer.spawn(world, pos, (ImplementedInventory) blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type == this.type) {
            return (World var1, BlockPos var2, BlockState var3, T var4) -> ((ModBlockEntityBase) var4).tick(var1, var2, var3);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return type.instantiate(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            var screenHandlerFactory = world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen((NamedScreenHandlerFactory) screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
}
