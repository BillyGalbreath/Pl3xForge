package net.pl3x.forge.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.block.custom.BlockEnchantmentSplitter;
import net.pl3x.forge.block.custom.BlockLamp;
import net.pl3x.forge.block.custom.BlockTable;
import net.pl3x.forge.block.custom.BlockDepositBox;
import net.pl3x.forge.block.custom.BlockFrostedObsidian;
import net.pl3x.forge.block.custom.BlockMicrowave;
import net.pl3x.forge.block.custom.BlockRuby;
import net.pl3x.forge.block.custom.BlockRubyOre;
import net.pl3x.forge.block.custom.BlockShop;

public class ModBlocks {
    public static final BlockRubyOre oreRuby = new BlockRubyOre();
    public static final BlockRuby blockRuby = new BlockRuby();
    public static final BlockFrostedObsidian frostedObsidian = new BlockFrostedObsidian();
    public static final BlockEnchantmentSplitter enchantmentSplitter = new BlockEnchantmentSplitter();
    public static final BlockShop shopBlock = new BlockShop();
    public static final BlockDepositBox depositBox = new BlockDepositBox();
    public static final BlockMicrowave microwave = new BlockMicrowave();
    public static final BlockLamp lamp = new BlockLamp();
    public static final BlockTable table = new BlockTable();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                oreRuby,
                blockRuby,
                frostedObsidian,
                enchantmentSplitter,
                shopBlock,
                depositBox,
                microwave,
                lamp,
                table
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
                shopBlock.createItemBlock(),
                depositBox.createItemBlock(),
                microwave.createItemBlock(),
                lamp.createItemBlock(),
                table.createItemBlock()
        );
    }

    public static void registerModels() {
        oreRuby.registerItemModel(Item.getItemFromBlock(oreRuby));
        blockRuby.registerItemModel(Item.getItemFromBlock(blockRuby));
        frostedObsidian.registerItemModel(Item.getItemFromBlock(frostedObsidian));
        enchantmentSplitter.registerItemModel(Item.getItemFromBlock(enchantmentSplitter));
        shopBlock.registerItemModel(Item.getItemFromBlock(shopBlock));
        depositBox.registerItemModel(Item.getItemFromBlock(depositBox));
        microwave.registerItemModel(Item.getItemFromBlock(microwave));
        lamp.registerItemModel(Item.getItemFromBlock(lamp));
        table.registerItemModel(Item.getItemFromBlock(table));
    }
}
