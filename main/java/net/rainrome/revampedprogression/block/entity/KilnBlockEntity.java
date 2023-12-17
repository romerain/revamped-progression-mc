package net.rainrome.revampedprogression.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.rainrome.revampedprogression.screen.KilnScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KilnBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);

    public boolean isReady = false;
    private boolean isLit = false;
    private int progress = 0;
    private int maxProgress = 500;
    private int fuelLevel = 0;
    private int fullFuelLevel = 4;

    public PropertyDelegate propertyDelegate;

    //server stuff

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KilnBlockEntity.this.progress;
                    case 1 -> KilnBlockEntity.this.maxProgress;
                    case 2 -> KilnBlockEntity.this.fuelLevel;
                    case 3 -> KilnBlockEntity.this.fullFuelLevel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> KilnBlockEntity.this.progress = value;
                    case 1 -> KilnBlockEntity.this.maxProgress = value;
                    case 2 -> KilnBlockEntity.this.fuelLevel = value;
                    case 3 -> KilnBlockEntity.this.fullFuelLevel = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("kiln.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("kiln.progress");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    //

    @Override
    public Text getDisplayName() {
        return Text.translatable("kiln-entity-display-name");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    // logic

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) { return; }

        int fuel = 0;
        for(int i = 4; i < 8; i++) {
            if(TagUtil.isIn(ItemTags.LOGS_THAT_BURN, this.getStack(i).getItem())) {
                fuel ++;
            }
        }
        fuelLevel = fuel;

        if(this.hasRecipe() && (fuelLevel >= fullFuelLevel)) {
            isReady = true;
        }
        else { isReady = false; }

        if(isLit) {
            this.progress++;
            markDirty(world, pos, state);

            if (this.progress >= this.maxProgress) {
                this.produceItem();
                this.isLit = false;
                this.resetProgress();
            }
        }
        else {
            resetProgress();
            markDirty(world, pos, state);
        }
    }

    private boolean hasRecipe() {
        for(int i = 0; i < 4; i++) {
            if(this.getStack(i).getItem() == Items.CLAY_BALL) {
                return true;
            }
        }
        return false;
    }

    private void produceItem() {
        for(int i = 0; i < 4; i++) {
            if (!this.getStack(i).isEmpty()) {
                this.setStack(i, new ItemStack(Items.BRICK));
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public void start() {
        if(isReady) {
            isLit = true;
            for(int i = 4; i < 8; i++) {
                if (!this.getStack(i).isEmpty()) {
                    this.removeStack(i);
                }
            }
        }
    }
}
