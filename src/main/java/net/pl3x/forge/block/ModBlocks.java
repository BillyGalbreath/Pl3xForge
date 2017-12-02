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
import net.pl3x.forge.block.custom.ore.BlockRuby;
import net.pl3x.forge.block.custom.ore.BlockRubyOre;
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
import net.pl3x.forge.block.custom.stairs.BlockStairsAndesite;
import net.pl3x.forge.block.custom.stairs.BlockStairsAndesiteSmooth;
import net.pl3x.forge.block.custom.stairs.BlockStairsBone;
import net.pl3x.forge.block.custom.stairs.BlockStairsCoal;
import net.pl3x.forge.block.custom.stairs.BlockStairsCobblestoneMossy;
import net.pl3x.forge.block.custom.stairs.BlockStairsConcrete;
import net.pl3x.forge.block.custom.stairs.BlockStairsDarkPrismarine;
import net.pl3x.forge.block.custom.stairs.BlockStairsDiamond;
import net.pl3x.forge.block.custom.stairs.BlockStairsDiorite;
import net.pl3x.forge.block.custom.stairs.BlockStairsDioriteSmooth;
import net.pl3x.forge.block.custom.stairs.BlockStairsDirt;
import net.pl3x.forge.block.custom.stairs.BlockStairsDirtCoarse;
import net.pl3x.forge.block.custom.stairs.BlockStairsEmerald;
import net.pl3x.forge.block.custom.stairs.BlockStairsEndBricks;
import net.pl3x.forge.block.custom.stairs.BlockStairsEndStone;
import net.pl3x.forge.block.custom.stairs.BlockStairsGlass;
import net.pl3x.forge.block.custom.stairs.BlockStairsGlassStained;
import net.pl3x.forge.block.custom.stairs.BlockStairsGlowstone;
import net.pl3x.forge.block.custom.stairs.BlockStairsGold;
import net.pl3x.forge.block.custom.stairs.BlockStairsGranite;
import net.pl3x.forge.block.custom.stairs.BlockStairsGraniteSmooth;
import net.pl3x.forge.block.custom.stairs.BlockStairsGrass;
import net.pl3x.forge.block.custom.stairs.BlockStairsIcePacked;
import net.pl3x.forge.block.custom.stairs.BlockStairsIron;
import net.pl3x.forge.block.custom.stairs.BlockStairsLapis;
import net.pl3x.forge.block.custom.stairs.BlockStairsMagma;
import net.pl3x.forge.block.custom.stairs.BlockStairsNetherWart;
import net.pl3x.forge.block.custom.stairs.BlockStairsNetherrack;
import net.pl3x.forge.block.custom.stairs.BlockStairsObsidian;
import net.pl3x.forge.block.custom.stairs.BlockStairsPrismarine;
import net.pl3x.forge.block.custom.stairs.BlockStairsPrismarineBrick;
import net.pl3x.forge.block.custom.stairs.BlockStairsPurpurPillar;
import net.pl3x.forge.block.custom.stairs.BlockStairsQuartzChiseled;
import net.pl3x.forge.block.custom.stairs.BlockStairsQuartzPillar;
import net.pl3x.forge.block.custom.stairs.BlockStairsRedNetherBrick;
import net.pl3x.forge.block.custom.stairs.BlockStairsRedstone;
import net.pl3x.forge.block.custom.stairs.BlockStairsRuby;
import net.pl3x.forge.block.custom.stairs.BlockStairsSeaLantern;
import net.pl3x.forge.block.custom.stairs.BlockStairsStoneBrickChiseled;
import net.pl3x.forge.block.custom.stairs.BlockStairsStoneBrickCracked;
import net.pl3x.forge.block.custom.stairs.BlockStairsStoneBrickMossy;
import net.pl3x.forge.block.custom.stairs.BlockStairsTerracotta;
import net.pl3x.forge.block.custom.stairs.BlockStairsWool;
import net.pl3x.forge.block.custom.wall.BlockVerticalSlabStone;
import net.pl3x.forge.block.custom.wall.BlockVerticalSlabStoneDouble;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {
    public static final Set<Block> blocks = new HashSet<>();

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
    public static final BlockRubyOre RUBY = new BlockRubyOre();
    public static final BlockRuby RUBY_BLOCK = new BlockRuby();
    public static final BlockShop SHOP = new BlockShop();
    public static final BlockStairsAndesite STAIRS_ANDESITE = new BlockStairsAndesite();
    public static final BlockStairsAndesiteSmooth STAIRS_ANDESITE_SMOOTH = new BlockStairsAndesiteSmooth();
    public static final BlockStairsBone STAIRS_BONE = new BlockStairsBone();
    public static final BlockStairsCoal STAIRS_COAL = new BlockStairsCoal();
    public static final BlockStairsCobblestoneMossy STAIRS_COBBLESTONE_MOSSY = new BlockStairsCobblestoneMossy();
    public static final BlockStairsConcrete STAIRS_CONCRETE_BLACK = new BlockStairsConcrete(EnumDyeColor.BLACK);
    public static final BlockStairsConcrete STAIRS_CONCRETE_BLUE = new BlockStairsConcrete(EnumDyeColor.BLUE);
    public static final BlockStairsConcrete STAIRS_CONCRETE_BROWN = new BlockStairsConcrete(EnumDyeColor.BROWN);
    public static final BlockStairsConcrete STAIRS_CONCRETE_CYAN = new BlockStairsConcrete(EnumDyeColor.CYAN);
    public static final BlockStairsConcrete STAIRS_CONCRETE_GRAY = new BlockStairsConcrete(EnumDyeColor.GRAY);
    public static final BlockStairsConcrete STAIRS_CONCRETE_GREEN = new BlockStairsConcrete(EnumDyeColor.GREEN);
    public static final BlockStairsConcrete STAIRS_CONCRETE_LIGHT_BLUE = new BlockStairsConcrete(EnumDyeColor.LIGHT_BLUE);
    public static final BlockStairsConcrete STAIRS_CONCRETE_LIME = new BlockStairsConcrete(EnumDyeColor.LIME);
    public static final BlockStairsConcrete STAIRS_CONCRETE_MAGENTA = new BlockStairsConcrete(EnumDyeColor.MAGENTA);
    public static final BlockStairsConcrete STAIRS_CONCRETE_ORANGE = new BlockStairsConcrete(EnumDyeColor.ORANGE);
    public static final BlockStairsConcrete STAIRS_CONCRETE_PINK = new BlockStairsConcrete(EnumDyeColor.PINK);
    public static final BlockStairsConcrete STAIRS_CONCRETE_PURPLE = new BlockStairsConcrete(EnumDyeColor.PURPLE);
    public static final BlockStairsConcrete STAIRS_CONCRETE_RED = new BlockStairsConcrete(EnumDyeColor.RED);
    public static final BlockStairsConcrete STAIRS_CONCRETE_SILVER = new BlockStairsConcrete(EnumDyeColor.SILVER);
    public static final BlockStairsConcrete STAIRS_CONCRETE_WHITE = new BlockStairsConcrete(EnumDyeColor.WHITE);
    public static final BlockStairsConcrete STAIRS_CONCRETE_YELLOW = new BlockStairsConcrete(EnumDyeColor.YELLOW);
    public static final BlockStairsDarkPrismarine STAIRS_DARK_PRISMARINE = new BlockStairsDarkPrismarine();
    public static final BlockStairsDiamond STAIRS_DIAMOND = new BlockStairsDiamond();
    public static final BlockStairsDiorite STAIRS_DIORITE = new BlockStairsDiorite();
    public static final BlockStairsDioriteSmooth STAIRS_DIORITE_SMOOTH = new BlockStairsDioriteSmooth();
    public static final BlockStairsDirt STAIRS_DIRT = new BlockStairsDirt();
    public static final BlockStairsDirtCoarse STAIRS_DIRT_COARSE = new BlockStairsDirtCoarse();
    public static final BlockStairsEmerald STAIRS_EMERALD = new BlockStairsEmerald();
    public static final BlockStairsEndBricks STAIRS_END_BRICKS = new BlockStairsEndBricks();
    public static final BlockStairsEndStone STAIRS_END_STONE = new BlockStairsEndStone();
    public static final BlockStairsGlass STAIRS_GLASS = new BlockStairsGlass();
    public static final BlockStairsGlassStained STAIRS_GLASS_BLACK = new BlockStairsGlassStained(EnumDyeColor.BLACK);
    public static final BlockStairsGlassStained STAIRS_GLASS_BLUE = new BlockStairsGlassStained(EnumDyeColor.BLUE);
    public static final BlockStairsGlassStained STAIRS_GLASS_BROWN = new BlockStairsGlassStained(EnumDyeColor.BROWN);
    public static final BlockStairsGlassStained STAIRS_GLASS_CYAN = new BlockStairsGlassStained(EnumDyeColor.CYAN);
    public static final BlockStairsGlassStained STAIRS_GLASS_GRAY = new BlockStairsGlassStained(EnumDyeColor.GRAY);
    public static final BlockStairsGlassStained STAIRS_GLASS_GREEN = new BlockStairsGlassStained(EnumDyeColor.GREEN);
    public static final BlockStairsGlassStained STAIRS_GLASS_LIGHT_BLUE = new BlockStairsGlassStained(EnumDyeColor.LIGHT_BLUE);
    public static final BlockStairsGlassStained STAIRS_GLASS_LIME = new BlockStairsGlassStained(EnumDyeColor.LIME);
    public static final BlockStairsGlassStained STAIRS_GLASS_MAGENTA = new BlockStairsGlassStained(EnumDyeColor.MAGENTA);
    public static final BlockStairsGlassStained STAIRS_GLASS_ORANGE = new BlockStairsGlassStained(EnumDyeColor.ORANGE);
    public static final BlockStairsGlassStained STAIRS_GLASS_PINK = new BlockStairsGlassStained(EnumDyeColor.PINK);
    public static final BlockStairsGlassStained STAIRS_GLASS_PURPLE = new BlockStairsGlassStained(EnumDyeColor.PURPLE);
    public static final BlockStairsGlassStained STAIRS_GLASS_RED = new BlockStairsGlassStained(EnumDyeColor.RED);
    public static final BlockStairsGlassStained STAIRS_GLASS_SILVER = new BlockStairsGlassStained(EnumDyeColor.SILVER);
    public static final BlockStairsGlassStained STAIRS_GLASS_WHITE = new BlockStairsGlassStained(EnumDyeColor.WHITE);
    public static final BlockStairsGlassStained STAIRS_GLASS_YELLOW = new BlockStairsGlassStained(EnumDyeColor.YELLOW);
    public static final BlockStairsGlowstone STAIRS_GLOWSTONE = new BlockStairsGlowstone();
    public static final BlockStairsGold STAIRS_GOLD = new BlockStairsGold();
    public static final BlockStairsGranite STAIRS_GRANITE = new BlockStairsGranite();
    public static final BlockStairsGraniteSmooth STAIRS_GRANITE_SMOOTH = new BlockStairsGraniteSmooth();
    public static final BlockStairsGrass STAIRS_GRASS = new BlockStairsGrass();
    public static final BlockStairsIcePacked STAIRS_ICE_PACKED = new BlockStairsIcePacked();
    public static final BlockStairsIron STAIRS_IRON = new BlockStairsIron();
    public static final BlockStairsLapis STAIRS_LAPIS = new BlockStairsLapis();
    public static final BlockStairsMagma STAIRS_MAGMA = new BlockStairsMagma();
    public static final BlockStairsNetherWart STAIRS_NETHER_WART = new BlockStairsNetherWart();
    public static final BlockStairsNetherrack STAIRS_NETHERRACK = new BlockStairsNetherrack();
    public static final BlockStairsObsidian STAIRS_OBSIDIAN = new BlockStairsObsidian();
    public static final BlockStairsPrismarine STAIRS_PRISMARINE = new BlockStairsPrismarine();
    public static final BlockStairsPrismarineBrick STAIRS_PRISMARINE_BRICK = new BlockStairsPrismarineBrick();
    public static final BlockStairsPurpurPillar STAIRS_PURPUR_PILLAR = new BlockStairsPurpurPillar();
    public static final BlockStairsQuartzChiseled STAIRS_QUARTZ_CHISELED = new BlockStairsQuartzChiseled();
    public static final BlockStairsQuartzPillar STAIRS_QUARTZ_PILLAR = new BlockStairsQuartzPillar();
    public static final BlockStairsRedNetherBrick STAIRS_RED_NETHER_BRICK = new BlockStairsRedNetherBrick();
    public static final BlockStairsRedstone STAIRS_REDSTONE = new BlockStairsRedstone();
    public static final BlockStairsRuby STAIRS_RUBY = new BlockStairsRuby();
    public static final BlockStairsSeaLantern STAIRS_SEA_LANTERN = new BlockStairsSeaLantern();
    public static final BlockStairsSpiral STAIRS_SPIRAL = new BlockStairsSpiral();
    public static final BlockStairsStoneBrickChiseled STAIRS_STONE_BRICK_CHISELED = new BlockStairsStoneBrickChiseled();
    public static final BlockStairsStoneBrickCracked STAIRS_STONE_BRICK_CRACKED = new BlockStairsStoneBrickCracked();
    public static final BlockStairsStoneBrickMossy STAIRS_STONE_BRICK_MOSSY = new BlockStairsStoneBrickMossy();
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA = new BlockStairsTerracotta();
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_BLACK = new BlockStairsTerracotta(EnumDyeColor.BLACK);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_BLUE = new BlockStairsTerracotta(EnumDyeColor.BLUE);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_BROWN = new BlockStairsTerracotta(EnumDyeColor.BROWN);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_CYAN = new BlockStairsTerracotta(EnumDyeColor.CYAN);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_GRAY = new BlockStairsTerracotta(EnumDyeColor.GRAY);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_GREEN = new BlockStairsTerracotta(EnumDyeColor.GREEN);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_LIGHT_BLUE = new BlockStairsTerracotta(EnumDyeColor.LIGHT_BLUE);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_LIME = new BlockStairsTerracotta(EnumDyeColor.LIME);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_MAGENTA = new BlockStairsTerracotta(EnumDyeColor.MAGENTA);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_ORANGE = new BlockStairsTerracotta(EnumDyeColor.ORANGE);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_PINK = new BlockStairsTerracotta(EnumDyeColor.PINK);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_PURPLE = new BlockStairsTerracotta(EnumDyeColor.PURPLE);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_RED = new BlockStairsTerracotta(EnumDyeColor.RED);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_SILVER = new BlockStairsTerracotta(EnumDyeColor.SILVER);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_WHITE = new BlockStairsTerracotta(EnumDyeColor.WHITE);
    public static final BlockStairsTerracotta STAIRS_TERRACOTTA_YELLOW = new BlockStairsTerracotta(EnumDyeColor.YELLOW);
    public static final BlockStairsWool STAIRS_WOOL_BLACK = new BlockStairsWool(EnumDyeColor.BLACK);
    public static final BlockStairsWool STAIRS_WOOL_BLUE = new BlockStairsWool(EnumDyeColor.BLUE);
    public static final BlockStairsWool STAIRS_WOOL_BROWN = new BlockStairsWool(EnumDyeColor.BROWN);
    public static final BlockStairsWool STAIRS_WOOL_CYAN = new BlockStairsWool(EnumDyeColor.CYAN);
    public static final BlockStairsWool STAIRS_WOOL_GRAY = new BlockStairsWool(EnumDyeColor.GRAY);
    public static final BlockStairsWool STAIRS_WOOL_GREEN = new BlockStairsWool(EnumDyeColor.GREEN);
    public static final BlockStairsWool STAIRS_WOOL_LIGHT_BLUE = new BlockStairsWool(EnumDyeColor.LIGHT_BLUE);
    public static final BlockStairsWool STAIRS_WOOL_LIME = new BlockStairsWool(EnumDyeColor.LIME);
    public static final BlockStairsWool STAIRS_WOOL_MAGENTA = new BlockStairsWool(EnumDyeColor.MAGENTA);
    public static final BlockStairsWool STAIRS_WOOL_ORANGE = new BlockStairsWool(EnumDyeColor.ORANGE);
    public static final BlockStairsWool STAIRS_WOOL_PINK = new BlockStairsWool(EnumDyeColor.PINK);
    public static final BlockStairsWool STAIRS_WOOL_PURPLE = new BlockStairsWool(EnumDyeColor.PURPLE);
    public static final BlockStairsWool STAIRS_WOOL_RED = new BlockStairsWool(EnumDyeColor.RED);
    public static final BlockStairsWool STAIRS_WOOL_SILVER = new BlockStairsWool(EnumDyeColor.SILVER);
    public static final BlockStairsWool STAIRS_WOOL_WHITE = new BlockStairsWool(EnumDyeColor.WHITE);
    public static final BlockStairsWool STAIRS_WOOL_YELLOW = new BlockStairsWool(EnumDyeColor.YELLOW);
    public static final BlockStove STOVE = new BlockStove();
    public static final BlockTable TABLE = new BlockTable();
    public static final BlockTilledEndStone TILLED_END_STONE = new BlockTilledEndStone();
    public static final BlockToilet TOILET = new BlockToilet();
    public static final BlockTrafficLight TRAFFIC_LIGHT = new BlockTrafficLight();
    public static final BlockTrafficLightControlBox TRAFFIC_LIGHT_CONTROL_BOX = new BlockTrafficLightControlBox();
    public static final BlockTV TV = new BlockTV();
    public static final BlockVerticalSlabStone VERTICAL_SLAB_STONE = new BlockVerticalSlabStone();
    public static final BlockVerticalSlabStoneDouble VERTICAL_SLAB_STONE_DOUBLE = new BlockVerticalSlabStoneDouble();

    public static void register(IForgeRegistry<Block> registry) {
        blocks.forEach(registry::register);

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
        blocks.forEach(block -> {
            if (block instanceof BlockBase) {
                registry.register(((BlockBase) block).createItemBlock());
            } else if (block instanceof BlockConcreteSlabHalf) {
                registry.register(((BlockConcreteSlab) block).createItemBlock());
            } else if (block instanceof BlockDirtSlabHalf) {
                registry.register(((BlockDirtSlab) block).createItemBlock());
            } else if (block instanceof BlockGrassSlabHalf) {
                registry.register(((BlockGrassSlab) block).createItemBlock());
            } else {
                registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
        });
    }

    public static void registerModels() {
        blocks.forEach(block -> Pl3x.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, block.getUnlocalizedName().substring(5)));
    }
}
