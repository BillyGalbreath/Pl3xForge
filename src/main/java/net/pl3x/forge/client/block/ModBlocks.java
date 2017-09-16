package net.pl3x.forge.client.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.client.block.custom.BlockFrostedObsidian;
import net.pl3x.forge.client.block.custom.BlockRuby;
import net.pl3x.forge.client.block.custom.BlockRubyOre;

public class ModBlocks {
    public static final BlockRubyOre oreRuby = new BlockRubyOre();
    public static final BlockRuby blockRuby = new BlockRuby();
    public static final BlockFrostedObsidian frostedObsidian = new BlockFrostedObsidian();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                oreRuby,
                blockRuby,
                frostedObsidian
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                oreRuby.createItemBlock(),
                blockRuby.createItemBlock(),
                frostedObsidian.createItemBlock()
        );
    }

    public static void registerModels() {
        oreRuby.registerItemModel(Item.getItemFromBlock(oreRuby));
        blockRuby.registerItemModel(Item.getItemFromBlock(blockRuby));
        frostedObsidian.registerItemModel(Item.getItemFromBlock(frostedObsidian));
    }
}
