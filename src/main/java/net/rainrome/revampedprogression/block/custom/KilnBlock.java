package net.rainrome.revampedprogression.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.rainrome.revampedprogression.block.entity.KilnBlockEntity;
import net.rainrome.revampedprogression.block.entity.ModBlockEntities;
import net.rainrome.revampedprogression.util.ModBlockWithEntityBase;
import org.jetbrains.annotations.Nullable;

public class KilnBlock extends ModBlockWithEntityBase {

    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 14, 16, 14);

    public KilnBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if(player.getMainHandStack().getItem() == Items.STICK)
            {
                ((KilnBlockEntity) world.getBlockEntity(pos)).start();
            }
            else {
                super.onUse(state, world, pos, player, hand, hit);
            }
        }
        return ActionResult.SUCCESS;
    }

}
