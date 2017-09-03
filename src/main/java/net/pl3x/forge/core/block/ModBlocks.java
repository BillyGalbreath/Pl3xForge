package net.pl3x.forge.core.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
    public static final BlockOre oreRuby = new BlockOre("ore_ruby", "oreRuby").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static final BlockOre blockRuby = new BlockOre("block_ruby", "blockRuby").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                oreRuby,
                blockRuby
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                oreRuby.createItemBlock(),
                blockRuby.createItemBlock()
        );
    }

    public static void registerModels() {
        oreRuby.registerItemModel(Item.getItemFromBlock(oreRuby));
        blockRuby.registerItemModel(Item.getItemFromBlock(blockRuby));
    }
}
