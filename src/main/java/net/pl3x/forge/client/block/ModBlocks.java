package net.pl3x.forge.client.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.client.block.custom.BlockEnchantmentSplitter;
import net.pl3x.forge.client.block.custom.BlockFrostedObsidian;
import net.pl3x.forge.client.block.custom.BlockRuby;
import net.pl3x.forge.client.block.custom.BlockRubyOre;
import net.pl3x.forge.client.block.custom.BlockShop;

public class ModBlocks {
    public static final BlockRubyOre oreRuby = new BlockRubyOre();
    public static final BlockRuby blockRuby = new BlockRuby();
    public static final BlockFrostedObsidian frostedObsidian = new BlockFrostedObsidian();
    public static final BlockEnchantmentSplitter enchantmentSplitter = new BlockEnchantmentSplitter();
    public static final BlockShop shopBlock = new BlockShop();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                oreRuby,
                blockRuby,
                frostedObsidian,
                enchantmentSplitter,
                shopBlock
        );

        GameRegistry.registerTileEntity(enchantmentSplitter.getTileEntityClass(), enchantmentSplitter.getRegistryName().toString());
        GameRegistry.registerTileEntity(shopBlock.getTileEntityClass(), shopBlock.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                oreRuby.createItemBlock(),
                blockRuby.createItemBlock(),
                frostedObsidian.createItemBlock(),
                enchantmentSplitter.createItemBlock(),
                shopBlock.createItemBlock()
        );
    }

    public static void registerModels() {
        oreRuby.registerItemModel(Item.getItemFromBlock(oreRuby));
        blockRuby.registerItemModel(Item.getItemFromBlock(blockRuby));
        frostedObsidian.registerItemModel(Item.getItemFromBlock(frostedObsidian));
        enchantmentSplitter.registerItemModel(Item.getItemFromBlock(enchantmentSplitter));
        shopBlock.registerItemModel(Item.getItemFromBlock(shopBlock));
    }
}
