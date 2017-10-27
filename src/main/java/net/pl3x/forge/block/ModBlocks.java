package net.pl3x.forge.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.block.custom.BlockChristmasTree;
import net.pl3x.forge.block.custom.BlockDepositBox;
import net.pl3x.forge.block.custom.BlockEnchantmentSplitter;
import net.pl3x.forge.block.custom.BlockFrostedObsidian;
import net.pl3x.forge.block.custom.BlockLamp;
import net.pl3x.forge.block.custom.BlockMicrowave;
import net.pl3x.forge.block.custom.BlockRuby;
import net.pl3x.forge.block.custom.BlockRubyOre;
import net.pl3x.forge.block.custom.BlockShop;
import net.pl3x.forge.block.custom.BlockTable;
import net.pl3x.forge.block.custom.curb.BlockCurb;
import net.pl3x.forge.block.custom.curb.BlockCurbCorner;
import net.pl3x.forge.block.custom.slab.BlockConcreteSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockConcreteSlabHalf;
import net.pl3x.forge.block.custom.slab.BlockCurbSlab;
import net.pl3x.forge.block.custom.slab.BlockCurbSlabCorner;
import net.pl3x.forge.block.custom.slab.BlockDirtSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockDirtSlabHalf;
import net.pl3x.forge.block.custom.slab.BlockGrassSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockGrassSlabHalf;

public class ModBlocks {
    public static final BlockRubyOre RUBY = new BlockRubyOre();
    public static final BlockRuby RUBY_BLOCK = new BlockRuby();
    public static final BlockFrostedObsidian FROSTED_OBSIDIAN = new BlockFrostedObsidian();
    public static final BlockEnchantmentSplitter ENCHANTMENT_SPLITTER = new BlockEnchantmentSplitter();
    public static final BlockShop SHOP = new BlockShop();
    public static final BlockDepositBox DEPOSIT_BOX = new BlockDepositBox();
    public static final BlockMicrowave MICROWAVE = new BlockMicrowave();
    public static final BlockLamp LAMP = new BlockLamp();
    public static final BlockTable TABLE = new BlockTable();
    public static final BlockChristmasTree CHRISTMAS_TREE = new BlockChristmasTree();

    public static final BlockConcreteSlabHalf CONCRETE_SLAB_BLACK = new BlockConcreteSlabHalf(EnumDyeColor.BLACK);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_BLACK_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.BLACK);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_BLUE = new BlockConcreteSlabHalf(EnumDyeColor.BLUE);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_BLUE_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.BLUE);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_BROWN = new BlockConcreteSlabHalf(EnumDyeColor.BROWN);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_BROWN_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.BROWN);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_CYAN = new BlockConcreteSlabHalf(EnumDyeColor.CYAN);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_CYAN_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.CYAN);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_GRAY = new BlockConcreteSlabHalf(EnumDyeColor.GRAY);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_GRAY_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.GRAY);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_GREEN = new BlockConcreteSlabHalf(EnumDyeColor.GREEN);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_GREEN_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.GREEN);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_LIGHT_BLUE = new BlockConcreteSlabHalf(EnumDyeColor.LIGHT_BLUE);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_LIGHT_BLUE_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.LIGHT_BLUE);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_LIME = new BlockConcreteSlabHalf(EnumDyeColor.LIME);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_LIME_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.LIME);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_MAGENTA = new BlockConcreteSlabHalf(EnumDyeColor.MAGENTA);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_MAGENTA_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.MAGENTA);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_ORANGE = new BlockConcreteSlabHalf(EnumDyeColor.ORANGE);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_ORANGE_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.ORANGE);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_PINK = new BlockConcreteSlabHalf(EnumDyeColor.PINK);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_PINK_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.PINK);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_PURPLE = new BlockConcreteSlabHalf(EnumDyeColor.PURPLE);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_PURPLE_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.PURPLE);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_RED = new BlockConcreteSlabHalf(EnumDyeColor.RED);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_RED_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.RED);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_SILVER = new BlockConcreteSlabHalf(EnumDyeColor.SILVER);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_SILVER_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.SILVER);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_WHITE = new BlockConcreteSlabHalf(EnumDyeColor.WHITE);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_WHITE_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.WHITE);
    public static final BlockConcreteSlabHalf CONCRETE_SLAB_YELLOW = new BlockConcreteSlabHalf(EnumDyeColor.YELLOW);
    public static final BlockConcreteSlabDouble CONCRETE_SLAB_YELLOW_DOUBLE = new BlockConcreteSlabDouble(EnumDyeColor.YELLOW);

    public static final BlockCurb CURB_BLACK = new BlockCurb(EnumDyeColor.BLACK);
    public static final BlockCurb CURB_BLUE = new BlockCurb(EnumDyeColor.BLUE);
    public static final BlockCurb CURB_BROWN = new BlockCurb(EnumDyeColor.BROWN);
    public static final BlockCurb CURB_CYAN = new BlockCurb(EnumDyeColor.CYAN);
    public static final BlockCurb CURB_GRAY = new BlockCurb(EnumDyeColor.GRAY);
    public static final BlockCurb CURB_GREEN = new BlockCurb(EnumDyeColor.GREEN);
    public static final BlockCurb CURB_LIGHT_BLUE = new BlockCurb(EnumDyeColor.LIGHT_BLUE);
    public static final BlockCurb CURB_LIME = new BlockCurb(EnumDyeColor.LIME);
    public static final BlockCurb CURB_MAGENTA = new BlockCurb(EnumDyeColor.MAGENTA);
    public static final BlockCurb CURB_ORANGE = new BlockCurb(EnumDyeColor.ORANGE);
    public static final BlockCurb CURB_PINK = new BlockCurb(EnumDyeColor.PINK);
    public static final BlockCurb CURB_PURPLE = new BlockCurb(EnumDyeColor.PURPLE);
    public static final BlockCurb CURB_RED = new BlockCurb(EnumDyeColor.RED);
    public static final BlockCurb CURB_SILVER = new BlockCurb(EnumDyeColor.SILVER);
    public static final BlockCurb CURB_WHITE = new BlockCurb(EnumDyeColor.WHITE);
    public static final BlockCurb CURB_YELLOW = new BlockCurb(EnumDyeColor.YELLOW);

    public static final BlockCurbSlab CURB_SLAB_BLACK = new BlockCurbSlab(EnumDyeColor.BLACK);
    public static final BlockCurbSlab CURB_SLAB_BLUE = new BlockCurbSlab(EnumDyeColor.BLUE);
    public static final BlockCurbSlab CURB_SLAB_BROWN = new BlockCurbSlab(EnumDyeColor.BROWN);
    public static final BlockCurbSlab CURB_SLAB_CYAN = new BlockCurbSlab(EnumDyeColor.CYAN);
    public static final BlockCurbSlab CURB_SLAB_GRAY = new BlockCurbSlab(EnumDyeColor.GRAY);
    public static final BlockCurbSlab CURB_SLAB_GREEN = new BlockCurbSlab(EnumDyeColor.GREEN);
    public static final BlockCurbSlab CURB_SLAB_LIGHT_BLUE = new BlockCurbSlab(EnumDyeColor.LIGHT_BLUE);
    public static final BlockCurbSlab CURB_SLAB_LIME = new BlockCurbSlab(EnumDyeColor.LIME);
    public static final BlockCurbSlab CURB_SLAB_MAGENTA = new BlockCurbSlab(EnumDyeColor.MAGENTA);
    public static final BlockCurbSlab CURB_SLAB_ORANGE = new BlockCurbSlab(EnumDyeColor.ORANGE);
    public static final BlockCurbSlab CURB_SLAB_PINK = new BlockCurbSlab(EnumDyeColor.PINK);
    public static final BlockCurbSlab CURB_SLAB_PURPLE = new BlockCurbSlab(EnumDyeColor.PURPLE);
    public static final BlockCurbSlab CURB_SLAB_RED = new BlockCurbSlab(EnumDyeColor.RED);
    public static final BlockCurbSlab CURB_SLAB_SILVER = new BlockCurbSlab(EnumDyeColor.SILVER);
    public static final BlockCurbSlab CURB_SLAB_WHITE = new BlockCurbSlab(EnumDyeColor.WHITE);
    public static final BlockCurbSlab CURB_SLAB_YELLOW = new BlockCurbSlab(EnumDyeColor.YELLOW);

    public static final BlockCurbCorner CURB_CORNER = new BlockCurbCorner();
    public static final BlockCurbSlabCorner CURB_SLAB_CORNER = new BlockCurbSlabCorner();

    public static final BlockDirtSlabHalf DIRT_SLAB = new BlockDirtSlabHalf();
    public static final BlockDirtSlabDouble DIRT_SLAB_DOUBLE = new BlockDirtSlabDouble();

    public static final BlockGrassSlabHalf GRASS_SLAB = new BlockGrassSlabHalf();
    public static final BlockGrassSlabDouble GRASS_SLAB_DOUBLE = new BlockGrassSlabDouble();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                RUBY,
                RUBY_BLOCK,
                FROSTED_OBSIDIAN,
                ENCHANTMENT_SPLITTER,
                SHOP,
                DEPOSIT_BOX,
                MICROWAVE,
                LAMP,
                TABLE,
                CHRISTMAS_TREE,

                CONCRETE_SLAB_BLACK,
                CONCRETE_SLAB_BLACK_DOUBLE,
                CONCRETE_SLAB_BLUE,
                CONCRETE_SLAB_BLUE_DOUBLE,
                CONCRETE_SLAB_BROWN,
                CONCRETE_SLAB_BROWN_DOUBLE,
                CONCRETE_SLAB_CYAN,
                CONCRETE_SLAB_CYAN_DOUBLE,
                CONCRETE_SLAB_GRAY,
                CONCRETE_SLAB_GRAY_DOUBLE,
                CONCRETE_SLAB_GREEN,
                CONCRETE_SLAB_GREEN_DOUBLE,
                CONCRETE_SLAB_LIGHT_BLUE,
                CONCRETE_SLAB_LIGHT_BLUE_DOUBLE,
                CONCRETE_SLAB_LIME,
                CONCRETE_SLAB_LIME_DOUBLE,
                CONCRETE_SLAB_MAGENTA,
                CONCRETE_SLAB_MAGENTA_DOUBLE,
                CONCRETE_SLAB_ORANGE,
                CONCRETE_SLAB_ORANGE_DOUBLE,
                CONCRETE_SLAB_PINK,
                CONCRETE_SLAB_PINK_DOUBLE,
                CONCRETE_SLAB_PURPLE,
                CONCRETE_SLAB_PURPLE_DOUBLE,
                CONCRETE_SLAB_RED,
                CONCRETE_SLAB_RED_DOUBLE,
                CONCRETE_SLAB_SILVER,
                CONCRETE_SLAB_SILVER_DOUBLE,
                CONCRETE_SLAB_WHITE,
                CONCRETE_SLAB_WHITE_DOUBLE,
                CONCRETE_SLAB_YELLOW,
                CONCRETE_SLAB_YELLOW_DOUBLE,

                CURB_BLACK,
                CURB_BLUE,
                CURB_BROWN,
                CURB_CYAN,
                CURB_GRAY,
                CURB_GREEN,
                CURB_LIGHT_BLUE,
                CURB_LIME,
                CURB_MAGENTA,
                CURB_ORANGE,
                CURB_PINK,
                CURB_PURPLE,
                CURB_RED,
                CURB_SILVER,
                CURB_WHITE,
                CURB_YELLOW,

                CURB_SLAB_BLACK,
                CURB_SLAB_BLUE,
                CURB_SLAB_BROWN,
                CURB_SLAB_CYAN,
                CURB_SLAB_GRAY,
                CURB_SLAB_GREEN,
                CURB_SLAB_LIGHT_BLUE,
                CURB_SLAB_LIME,
                CURB_SLAB_MAGENTA,
                CURB_SLAB_ORANGE,
                CURB_SLAB_PINK,
                CURB_SLAB_PURPLE,
                CURB_SLAB_RED,
                CURB_SLAB_SILVER,
                CURB_SLAB_WHITE,
                CURB_SLAB_YELLOW,

                CURB_CORNER,
                CURB_SLAB_CORNER,

                DIRT_SLAB,
                DIRT_SLAB_DOUBLE,

                GRASS_SLAB,
                GRASS_SLAB_DOUBLE
        );

        GameRegistry.registerTileEntity(ENCHANTMENT_SPLITTER.getTileEntityClass(), ENCHANTMENT_SPLITTER.getRegistryName().toString());
        GameRegistry.registerTileEntity(SHOP.getTileEntityClass(), SHOP.getRegistryName().toString());


    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                RUBY.createItemBlock(),
                RUBY_BLOCK.createItemBlock(),
                FROSTED_OBSIDIAN.createItemBlock(),
                ENCHANTMENT_SPLITTER.createItemBlock(),
                SHOP.createItemBlock(),
                DEPOSIT_BOX.createItemBlock(),
                MICROWAVE.createItemBlock(),
                LAMP.createItemBlock(),
                TABLE.createItemBlock(),
                CHRISTMAS_TREE.createItemBlock(),

                CONCRETE_SLAB_BLACK.createItemBlock(),
                CONCRETE_SLAB_BLUE.createItemBlock(),
                CONCRETE_SLAB_BROWN.createItemBlock(),
                CONCRETE_SLAB_CYAN.createItemBlock(),
                CONCRETE_SLAB_GRAY.createItemBlock(),
                CONCRETE_SLAB_GREEN.createItemBlock(),
                CONCRETE_SLAB_LIGHT_BLUE.createItemBlock(),
                CONCRETE_SLAB_LIME.createItemBlock(),
                CONCRETE_SLAB_MAGENTA.createItemBlock(),
                CONCRETE_SLAB_ORANGE.createItemBlock(),
                CONCRETE_SLAB_PINK.createItemBlock(),
                CONCRETE_SLAB_PURPLE.createItemBlock(),
                CONCRETE_SLAB_RED.createItemBlock(),
                CONCRETE_SLAB_SILVER.createItemBlock(),
                CONCRETE_SLAB_WHITE.createItemBlock(),
                CONCRETE_SLAB_YELLOW.createItemBlock(),

                CURB_BLACK.createItemBlock(),
                CURB_BLUE.createItemBlock(),
                CURB_BROWN.createItemBlock(),
                CURB_CYAN.createItemBlock(),
                CURB_GRAY.createItemBlock(),
                CURB_GREEN.createItemBlock(),
                CURB_LIGHT_BLUE.createItemBlock(),
                CURB_LIME.createItemBlock(),
                CURB_MAGENTA.createItemBlock(),
                CURB_ORANGE.createItemBlock(),
                CURB_PINK.createItemBlock(),
                CURB_PURPLE.createItemBlock(),
                CURB_RED.createItemBlock(),
                CURB_SILVER.createItemBlock(),
                CURB_WHITE.createItemBlock(),
                CURB_YELLOW.createItemBlock(),

                CURB_SLAB_BLACK.createItemBlock(),
                CURB_SLAB_BLUE.createItemBlock(),
                CURB_SLAB_BROWN.createItemBlock(),
                CURB_SLAB_CYAN.createItemBlock(),
                CURB_SLAB_GRAY.createItemBlock(),
                CURB_SLAB_GREEN.createItemBlock(),
                CURB_SLAB_LIGHT_BLUE.createItemBlock(),
                CURB_SLAB_LIME.createItemBlock(),
                CURB_SLAB_MAGENTA.createItemBlock(),
                CURB_SLAB_ORANGE.createItemBlock(),
                CURB_SLAB_PINK.createItemBlock(),
                CURB_SLAB_PURPLE.createItemBlock(),
                CURB_SLAB_RED.createItemBlock(),
                CURB_SLAB_SILVER.createItemBlock(),
                CURB_SLAB_WHITE.createItemBlock(),
                CURB_SLAB_YELLOW.createItemBlock(),

                CURB_CORNER.createItemBlock(),
                CURB_SLAB_CORNER.createItemBlock(),

                DIRT_SLAB.createItemBlock(),
                GRASS_SLAB.createItemBlock()
        );
    }

    public static void registerModels() {
        RUBY.registerItemModel(Item.getItemFromBlock(RUBY));
        RUBY_BLOCK.registerItemModel(Item.getItemFromBlock(RUBY_BLOCK));
        FROSTED_OBSIDIAN.registerItemModel(Item.getItemFromBlock(FROSTED_OBSIDIAN));
        ENCHANTMENT_SPLITTER.registerItemModel(Item.getItemFromBlock(ENCHANTMENT_SPLITTER));
        SHOP.registerItemModel(Item.getItemFromBlock(SHOP));
        DEPOSIT_BOX.registerItemModel(Item.getItemFromBlock(DEPOSIT_BOX));
        MICROWAVE.registerItemModel(Item.getItemFromBlock(MICROWAVE));
        LAMP.registerItemModel(Item.getItemFromBlock(LAMP));
        TABLE.registerItemModel(Item.getItemFromBlock(TABLE));
        CHRISTMAS_TREE.registerItemModel(Item.getItemFromBlock(CHRISTMAS_TREE));

        CONCRETE_SLAB_BLACK.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BLACK));
        CONCRETE_SLAB_BLACK_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BLACK_DOUBLE));
        CONCRETE_SLAB_BLUE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BLUE));
        CONCRETE_SLAB_BLUE_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BLUE_DOUBLE));
        CONCRETE_SLAB_BROWN.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BROWN));
        CONCRETE_SLAB_BROWN_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_BROWN_DOUBLE));
        CONCRETE_SLAB_CYAN.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_CYAN));
        CONCRETE_SLAB_CYAN_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_CYAN_DOUBLE));
        CONCRETE_SLAB_GRAY.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_GRAY));
        CONCRETE_SLAB_GRAY_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_GRAY_DOUBLE));
        CONCRETE_SLAB_GREEN.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_GREEN));
        CONCRETE_SLAB_GREEN_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_GREEN_DOUBLE));
        CONCRETE_SLAB_LIGHT_BLUE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_LIGHT_BLUE));
        CONCRETE_SLAB_LIGHT_BLUE_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_LIGHT_BLUE_DOUBLE));
        CONCRETE_SLAB_LIME.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_LIME));
        CONCRETE_SLAB_LIME_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_LIME_DOUBLE));
        CONCRETE_SLAB_MAGENTA.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_MAGENTA));
        CONCRETE_SLAB_MAGENTA_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_MAGENTA_DOUBLE));
        CONCRETE_SLAB_ORANGE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_ORANGE));
        CONCRETE_SLAB_ORANGE_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_ORANGE_DOUBLE));
        CONCRETE_SLAB_PINK.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_PINK));
        CONCRETE_SLAB_PINK_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_PINK_DOUBLE));
        CONCRETE_SLAB_PURPLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_PURPLE));
        CONCRETE_SLAB_PURPLE_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_PURPLE_DOUBLE));
        CONCRETE_SLAB_RED.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_RED));
        CONCRETE_SLAB_RED_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_RED_DOUBLE));
        CONCRETE_SLAB_SILVER.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_SILVER));
        CONCRETE_SLAB_SILVER_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_SILVER_DOUBLE));
        CONCRETE_SLAB_WHITE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_WHITE));
        CONCRETE_SLAB_WHITE_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_WHITE_DOUBLE));
        CONCRETE_SLAB_YELLOW.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_YELLOW));
        CONCRETE_SLAB_YELLOW_DOUBLE.registerItemModel(Item.getItemFromBlock(CONCRETE_SLAB_YELLOW_DOUBLE));

        CURB_BLACK.registerItemModel(Item.getItemFromBlock(CURB_BLACK));
        CURB_BLUE.registerItemModel(Item.getItemFromBlock(CURB_BLUE));
        CURB_BROWN.registerItemModel(Item.getItemFromBlock(CURB_BROWN));
        CURB_CYAN.registerItemModel(Item.getItemFromBlock(CURB_CYAN));
        CURB_GRAY.registerItemModel(Item.getItemFromBlock(CURB_GRAY));
        CURB_GREEN.registerItemModel(Item.getItemFromBlock(CURB_GREEN));
        CURB_LIGHT_BLUE.registerItemModel(Item.getItemFromBlock(CURB_LIGHT_BLUE));
        CURB_LIME.registerItemModel(Item.getItemFromBlock(CURB_LIME));
        CURB_MAGENTA.registerItemModel(Item.getItemFromBlock(CURB_MAGENTA));
        CURB_ORANGE.registerItemModel(Item.getItemFromBlock(CURB_ORANGE));
        CURB_PINK.registerItemModel(Item.getItemFromBlock(CURB_PINK));
        CURB_PURPLE.registerItemModel(Item.getItemFromBlock(CURB_PURPLE));
        CURB_RED.registerItemModel(Item.getItemFromBlock(CURB_RED));
        CURB_SILVER.registerItemModel(Item.getItemFromBlock(CURB_SILVER));
        CURB_WHITE.registerItemModel(Item.getItemFromBlock(CURB_WHITE));
        CURB_YELLOW.registerItemModel(Item.getItemFromBlock(CURB_YELLOW));

        CURB_SLAB_BLACK.registerItemModel(Item.getItemFromBlock(CURB_SLAB_BLACK));
        CURB_SLAB_BLUE.registerItemModel(Item.getItemFromBlock(CURB_SLAB_BLUE));
        CURB_SLAB_BROWN.registerItemModel(Item.getItemFromBlock(CURB_SLAB_BROWN));
        CURB_SLAB_CYAN.registerItemModel(Item.getItemFromBlock(CURB_SLAB_CYAN));
        CURB_SLAB_GRAY.registerItemModel(Item.getItemFromBlock(CURB_SLAB_GRAY));
        CURB_SLAB_GREEN.registerItemModel(Item.getItemFromBlock(CURB_SLAB_GREEN));
        CURB_SLAB_LIGHT_BLUE.registerItemModel(Item.getItemFromBlock(CURB_SLAB_LIGHT_BLUE));
        CURB_SLAB_LIME.registerItemModel(Item.getItemFromBlock(CURB_SLAB_LIME));
        CURB_SLAB_MAGENTA.registerItemModel(Item.getItemFromBlock(CURB_SLAB_MAGENTA));
        CURB_SLAB_ORANGE.registerItemModel(Item.getItemFromBlock(CURB_SLAB_ORANGE));
        CURB_SLAB_PINK.registerItemModel(Item.getItemFromBlock(CURB_SLAB_PINK));
        CURB_SLAB_PURPLE.registerItemModel(Item.getItemFromBlock(CURB_SLAB_PURPLE));
        CURB_SLAB_RED.registerItemModel(Item.getItemFromBlock(CURB_SLAB_RED));
        CURB_SLAB_SILVER.registerItemModel(Item.getItemFromBlock(CURB_SLAB_SILVER));
        CURB_SLAB_WHITE.registerItemModel(Item.getItemFromBlock(CURB_SLAB_WHITE));
        CURB_SLAB_YELLOW.registerItemModel(Item.getItemFromBlock(CURB_SLAB_YELLOW));

        CURB_CORNER.registerItemModel(Item.getItemFromBlock(CURB_CORNER));
        CURB_SLAB_CORNER.registerItemModel(Item.getItemFromBlock(CURB_SLAB_CORNER));

        DIRT_SLAB.registerItemModel(Item.getItemFromBlock(DIRT_SLAB));
        DIRT_SLAB_DOUBLE.registerItemModel(Item.getItemFromBlock(DIRT_SLAB_DOUBLE));

        GRASS_SLAB.registerItemModel(Item.getItemFromBlock(GRASS_SLAB));
        GRASS_SLAB_DOUBLE.registerItemModel(Item.getItemFromBlock(GRASS_SLAB_DOUBLE));
    }
}
