package net.rainrome.revampedprogression.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.rainrome.revampedprogression.item.ModItems;

public class ModLootTableModifiers {

    static LootCondition.Builder IS_SHOVEL = MatchToolLootCondition.builder(ItemPredicate.Builder.create()
            .tag(TagKey.of(RegistryKeys.ITEM, new Identifier("c:shovels"))));
    static LootCondition.Builder IS_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create()
            .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.ANY)));
    public static void modifyLootTables() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (DIRT_DROP.equals(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.DIRT_PILE)
                                .conditionally(IS_SHOVEL.invert())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))
                        .with(ItemEntry.builder(Items.DIRT)
                                .conditionally(IS_SHOVEL)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                return LootTable.builder().pool(pool).build();
            }
            if (GRASS_BLOCK_DROP.equals(id)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.DIRT_PILE)
                                .conditionally(IS_SHOVEL.invert())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))
                        .with(ItemEntry.builder(Items.DIRT)
                                .conditionally(IS_SHOVEL)
                                .conditionally(IS_SILK_TOUCH.invert())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))))
                        .with(ItemEntry.builder(Items.GRASS_BLOCK)
                            .conditionally(IS_SILK_TOUCH)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1))));
                return LootTable.builder().pool(pool).build();
            }
            else { return original; }
        });
    }


    private static final Identifier DIRT_DROP = new Identifier("minecraft", "blocks/dirt");
    private static final Identifier GRASS_BLOCK_DROP = new Identifier("minecraft", "blocks/grass_block");
}
