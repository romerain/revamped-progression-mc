package net.rainrome.revampedprogression.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.rainrome.revampedprogression.RevampedProgression;
import net.rainrome.revampedprogression.block.entity.KilnBlockEntity;

public class KilnScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final KilnBlockEntity blockEntity;

    public KilnScreenHandler(int syncID, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncID, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(4));
    }

    public KilnScreenHandler(int syncID, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.KILN_SCREEN_HANDLER, syncID);
        checkSize(((Inventory) blockEntity), 8);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        inventory.getMaxCountPerStack();

        this.propertyDelegate = propertyDelegate;
        this.blockEntity = ((KilnBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 0, 71, 13));
        this.addSlot(new Slot(inventory, 1, 89, 13));
        this.addSlot(new Slot(inventory, 2, 71, 31));
        this.addSlot(new Slot(inventory, 3, 89, 31));
        this.addSlot(new Slot(inventory, 4, 53, 55));
        this.addSlot(new Slot(inventory, 5, 71, 55));
        this.addSlot(new Slot(inventory, 6, 89, 55));
        this.addSlot(new Slot(inventory, 7, 107, 55));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    public float getProgress() {
        return (float) this.propertyDelegate.get(0);
    }
    public float getMaxProgress() {
        return (float) this.propertyDelegate.get(1);
    }
    public float getFuelLevel() {
        return (float) this.propertyDelegate.get(2);
    }
    public float getFullFuelLevel() {
        return (float) this.propertyDelegate.get(3);
    }


    public boolean isOn() {
        return this.propertyDelegate.get(0) > 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
