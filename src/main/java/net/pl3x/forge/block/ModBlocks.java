package net.pl3x.forge.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.custom.crops.BlockCropCorn;
import net.pl3x.forge.block.custom.crops.BlockCropEnderPearl;
import net.pl3x.forge.block.custom.decoration.BlockBarrel;
import net.pl3x.forge.block.custom.decoration.BlockBathroomCabinet;
import net.pl3x.forge.block.custom.decoration.BlockCampfire;
import net.pl3x.forge.block.custom.decoration.BlockChristmasTree;
import net.pl3x.forge.block.custom.decoration.BlockComputer;
import net.pl3x.forge.block.custom.decoration.BlockCurb;
import net.pl3x.forge.block.custom.decoration.BlockCuttingBoard;
import net.pl3x.forge.block.custom.decoration.BlockDepositBox;
import net.pl3x.forge.block.custom.decoration.BlockEnchantmentSplitter;
import net.pl3x.forge.block.custom.decoration.BlockLamp;
import net.pl3x.forge.block.custom.decoration.BlockMicrowave;
import net.pl3x.forge.block.custom.decoration.BlockMirror;
import net.pl3x.forge.block.custom.decoration.BlockPathCobble;
import net.pl3x.forge.block.custom.decoration.BlockPathStone;
import net.pl3x.forge.block.custom.decoration.BlockPathWood;
import net.pl3x.forge.block.custom.decoration.BlockPlate;
import net.pl3x.forge.block.custom.decoration.BlockPole;
import net.pl3x.forge.block.custom.decoration.BlockShop;
import net.pl3x.forge.block.custom.decoration.BlockStairsSpiral;
import net.pl3x.forge.block.custom.decoration.BlockStove;
import net.pl3x.forge.block.custom.decoration.BlockTV;
import net.pl3x.forge.block.custom.decoration.BlockTable;
import net.pl3x.forge.block.custom.decoration.BlockToilet;
import net.pl3x.forge.block.custom.decoration.BlockTrafficLight;
import net.pl3x.forge.block.custom.decoration.BlockTrafficLightControlBox;
import net.pl3x.forge.block.custom.farmland.BlockTilledEndStone;
import net.pl3x.forge.block.custom.furniture.BlockBedsideTable;
import net.pl3x.forge.block.custom.furniture.BlockChair;
import net.pl3x.forge.block.custom.furniture.BlockChairLawn;
import net.pl3x.forge.block.custom.furniture.BlockCoffeeTable;
import net.pl3x.forge.block.custom.furniture.BlockCouch;
import net.pl3x.forge.block.custom.ore.BlockFrostedObsidian;
import net.pl3x.forge.block.custom.slab.BlockConcreteSlab;
import net.pl3x.forge.block.custom.slab.BlockConcreteSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockConcreteSlabHalf;
import net.pl3x.forge.block.custom.slab.BlockCurbSlab;
import net.pl3x.forge.block.custom.slab.BlockDirtSlab;
import net.pl3x.forge.block.custom.slab.BlockDirtSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockDirtSlabHalf;
import net.pl3x.forge.block.custom.slab.BlockGrassSlab;
import net.pl3x.forge.block.custom.slab.BlockGrassSlabDouble;
import net.pl3x.forge.block.custom.slab.BlockGrassSlabHalf;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlab;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabAcacia;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabAcaciaDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabBirch;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabBirchDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabDarkOak;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabDarkOakDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabJungle;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabJungleDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabOak;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabOakDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabSpruce;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabSpruceDouble;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabStone;
import net.pl3x.forge.block.custom.vertical_slab.BlockVerticalSlabStoneDouble;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {
    public static final Set<Block> __BLOCKS__ = new HashSet<>();

    public static final BlockBarrel BARREL = new BlockBarrel();
    public static final BlockBathroomCabinet BATHROOM_CABINET = new BlockBathroomCabinet();
    public static final BlockBedsideTable BEDSIDE_TABLE = new BlockBedsideTable();
    public static final BlockCampfire CAMPFIRE = new BlockCampfire();
    public static final BlockChair CHAIR = new BlockChair();
    public static final BlockChairLawn CHAIR_LAWN = new BlockChairLawn();
    public static final BlockChristmasTree CHRISTMAS_TREE = new BlockChristmasTree();
    public static final BlockCoffeeTable COFFEE_TABLE = new BlockCoffeeTable();
    public static final BlockCouch COUCH = new BlockCouch();
    public static final BlockComputer COMPUTER = new BlockComputer();
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
    public static final BlockCropCorn CROP_CORN = new BlockCropCorn();
    public static final BlockCropEnderPearl CROP_ENDER_PEARL = new BlockCropEnderPearl();
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
    public static final BlockCurb CURB_WHITE = new BlockCurb(EnumDyeColor.WHITE);
    public static final BlockCurb CURB_YELLOW = new BlockCurb(EnumDyeColor.YELLOW);
    public static final BlockCuttingBoard CUTTING_BOARD = new BlockCuttingBoard();
    public static final BlockDepositBox DEPOSIT_BOX = new BlockDepositBox();
    public static final BlockDirtSlabHalf DIRT_SLAB = new BlockDirtSlabHalf();
    public static final BlockDirtSlabDouble DIRT_SLAB_DOUBLE = new BlockDirtSlabDouble();
    public static final BlockEnchantmentSplitter ENCHANTMENT_SPLITTER = new BlockEnchantmentSplitter();
    public static final BlockFrostedObsidian FROSTED_OBSIDIAN = new BlockFrostedObsidian();
    public static final BlockGrassSlabHalf GRASS_SLAB = new BlockGrassSlabHalf();
    public static final BlockGrassSlabDouble GRASS_SLAB_DOUBLE = new BlockGrassSlabDouble();
    public static final BlockLamp LAMP = new BlockLamp();
    public static final BlockPole METAL_POLE = new BlockPole("metal_pole");
    public static final BlockMicrowave MICROWAVE = new BlockMicrowave();
    public static final BlockMirror MIRROR = new BlockMirror();
    public static final BlockPathCobble PATH_COBBLE = new BlockPathCobble();
    public static final BlockPathStone PATH_STONE = new BlockPathStone();
    public static final BlockPathWood PATH_WOOD = new BlockPathWood();
    public static final BlockPlate PLATE = new BlockPlate();
    public static final BlockShop SHOP = new BlockShop();
    public static final BlockStairsSpiral STAIRS_SPIRAL = new BlockStairsSpiral();
    public static final BlockStove STOVE = new BlockStove();
    public static final BlockTable TABLE = new BlockTable();
    public static final BlockTilledEndStone TILLED_END_STONE = new BlockTilledEndStone();
    public static final BlockToilet TOILET = new BlockToilet();
    public static final BlockTrafficLight TRAFFIC_LIGHT = new BlockTrafficLight();
    public static final BlockTrafficLightControlBox TRAFFIC_LIGHT_CONTROL_BOX = new BlockTrafficLightControlBox();
    public static final BlockTV TV = new BlockTV();
    public static final BlockVerticalSlabAcacia VERTICAL_SLAB_ACACIA = new BlockVerticalSlabAcacia();
    public static final BlockVerticalSlabAcaciaDouble VERTICAL_SLAB_ACACIA_DOUBLE = new BlockVerticalSlabAcaciaDouble();
    public static final BlockVerticalSlabBirch VERTICAL_SLAB_BIRCH = new BlockVerticalSlabBirch();
    public static final BlockVerticalSlabBirchDouble VERTICAL_SLAB_BIRCH_DOUBLE = new BlockVerticalSlabBirchDouble();
    public static final BlockVerticalSlabDarkOak VERTICAL_SLAB_DARK_OAK = new BlockVerticalSlabDarkOak();
    public static final BlockVerticalSlabDarkOakDouble VERTICAL_SLAB_DARK_OAK_DOUBLE = new BlockVerticalSlabDarkOakDouble();
    public static final BlockVerticalSlabJungle VERTICAL_SLAB_JUNGLE = new BlockVerticalSlabJungle();
    public static final BlockVerticalSlabJungleDouble VERTICAL_SLAB_JUNGLE_DOUBLE = new BlockVerticalSlabJungleDouble();
    public static final BlockVerticalSlabOak VERTICAL_SLAB_OAK = new BlockVerticalSlabOak();
    public static final BlockVerticalSlabOakDouble VERTICAL_SLAB_OAK_DOUBLE = new BlockVerticalSlabOakDouble();
    public static final BlockVerticalSlabSpruce VERTICAL_SLAB_SPRUCE = new BlockVerticalSlabSpruce();
    public static final BlockVerticalSlabSpruceDouble VERTICAL_SLAB_SPRUCE_DOUBLE = new BlockVerticalSlabSpruceDouble();
    public static final BlockVerticalSlabStone VERTICAL_SLAB_STONE = new BlockVerticalSlabStone();
    public static final BlockVerticalSlabStoneDouble VERTICAL_SLAB_STONE_DOUBLE = new BlockVerticalSlabStoneDouble();

    public static void register(IForgeRegistry<Block> registry) {
        __BLOCKS__.forEach(registry::register);

        GameRegistry.registerTileEntity(BEDSIDE_TABLE.getTileEntityClass(), BEDSIDE_TABLE.getRegistryName().toString());
        GameRegistry.registerTileEntity(CUTTING_BOARD.getTileEntityClass(), CUTTING_BOARD.getRegistryName().toString());
        GameRegistry.registerTileEntity(ENCHANTMENT_SPLITTER.getTileEntityClass(), ENCHANTMENT_SPLITTER.getRegistryName().toString());
        GameRegistry.registerTileEntity(MIRROR.getTileEntityClass(), MIRROR.getRegistryName().toString());
        GameRegistry.registerTileEntity(PLATE.getTileEntityClass(), PLATE.getRegistryName().toString());
        GameRegistry.registerTileEntity(SHOP.getTileEntityClass(), SHOP.getRegistryName().toString());
        GameRegistry.registerTileEntity(STOVE.getTileEntityClass(), STOVE.getRegistryName().toString());
        GameRegistry.registerTileEntity(TRAFFIC_LIGHT.getTileEntityClass(), TRAFFIC_LIGHT.getRegistryName().toString());
        GameRegistry.registerTileEntity(TRAFFIC_LIGHT_CONTROL_BOX.getTileEntityClass(), TRAFFIC_LIGHT_CONTROL_BOX.getRegistryName().toString());
        GameRegistry.registerTileEntity(TV.getTileEntityClass(), TV.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        __BLOCKS__.forEach(block -> {
            if (block instanceof BlockConcreteSlabHalf) {
                registry.register(((BlockConcreteSlab) block).createItemBlock());
            } else if (block instanceof BlockDirtSlabHalf) {
                registry.register(((BlockDirtSlab) block).createItemBlock());
            } else if (block instanceof BlockGrassSlabHalf) {
                registry.register(((BlockGrassSlab) block).createItemBlock());
            } else if (block instanceof BlockVerticalSlab) {
                registry.register(((BlockVerticalSlab) block).createItemBlock());
            } else if (block instanceof BlockBase) {
                registry.register(((BlockBase) block).createItemBlock());
            } else {
                registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
        });
    }

    public static void registerModels() {
        __BLOCKS__.forEach(block -> Pl3x.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, block.getUnlocalizedName().substring(5)));
    }
}
