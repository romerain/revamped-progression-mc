package net.rainrome.revampedprogression.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class KilnRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final Ingredient input;

    public KilnRecipe(Ingredient input, ItemStack output) {
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient) {
            return false;
        }
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<KilnRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "kiln";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<KilnRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "kiln";

        public static final Codec<KilnRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("input").forGetter(KilnRecipe::getInput),
                ItemStack.CODEC.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, KilnRecipe::new));

        @Override
        public Codec<KilnRecipe> codec() {
            return CODEC;
        }

        @Override
        public KilnRecipe read(PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();

            return new KilnRecipe(input, output);
        }

        @Override
        public void write(PacketByteBuf buf, KilnRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.getResult(null));
        }
    }
}
