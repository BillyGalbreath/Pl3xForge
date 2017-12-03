package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.block.ModBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockStairsTerracotta extends BlockStairs {
    private static final Map<EnumDyeColor, MapColor> MAP_COLORS = new HashMap<>();

    static {
        MAP_COLORS.put(EnumDyeColor.WHITE, MapColor.WHITE_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.ORANGE, MapColor.ORANGE_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.MAGENTA, MapColor.MAGENTA_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.LIGHT_BLUE, MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.YELLOW, MapColor.YELLOW_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.LIME, MapColor.LIME_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.PINK, MapColor.PINK_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.GRAY, MapColor.GRAY_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.SILVER, MapColor.SILVER_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.CYAN, MapColor.CYAN_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.PURPLE, MapColor.PURPLE_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.BLUE, MapColor.BLUE_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.BROWN, MapColor.BROWN_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.GREEN, MapColor.GREEN_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.RED, MapColor.RED_STAINED_HARDENED_CLAY);
        MAP_COLORS.put(EnumDyeColor.BLACK, MapColor.BLACK_STAINED_HARDENED_CLAY);
    }

    private final EnumDyeColor color;

    public BlockStairsTerracotta() {
        super(Material.ROCK, "stairs_terracotta");
        this.color = null;

        init();
    }

    public BlockStairsTerracotta(EnumDyeColor color) {
        super(Material.ROCK, "stairs_terracotta_" + color.getName());
        this.color = color;

        init();
    }

    private void init() {
        setSoundType(SoundType.STONE);
        setHardness(1.25F);
        setResistance(7);

        setCreativeTab(TAB_STAIRS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return color != null ? MAP_COLORS.get(color) : MapColor.ADOBE;
    }

    public Item createItemBlock() {
        return new ItemBlock(getBlock(color)).setRegistryName(getRegistryName());
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getBlock(color));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(getBlock(color));
    }

    private static BlockStairsTerracotta getBlock(EnumDyeColor color) {
        if (color == null) {
            return ModBlocks.STAIRS_TERRACOTTA;
        }
        switch (color) {
            case BLACK:
                return ModBlocks.STAIRS_TERRACOTTA_BLACK;
            case BLUE:
                return ModBlocks.STAIRS_TERRACOTTA_BLUE;
            case BROWN:
                return ModBlocks.STAIRS_TERRACOTTA_BROWN;
            case CYAN:
                return ModBlocks.STAIRS_TERRACOTTA_CYAN;
            case GRAY:
                return ModBlocks.STAIRS_TERRACOTTA_GRAY;
            case GREEN:
                return ModBlocks.STAIRS_TERRACOTTA_GREEN;
            case LIGHT_BLUE:
                return ModBlocks.STAIRS_TERRACOTTA_LIGHT_BLUE;
            case LIME:
                return ModBlocks.STAIRS_TERRACOTTA_LIME;
            case MAGENTA:
                return ModBlocks.STAIRS_TERRACOTTA_MAGENTA;
            case ORANGE:
                return ModBlocks.STAIRS_TERRACOTTA_ORANGE;
            case PINK:
                return ModBlocks.STAIRS_TERRACOTTA_PINK;
            case PURPLE:
                return ModBlocks.STAIRS_TERRACOTTA_PURPLE;
            case RED:
                return ModBlocks.STAIRS_TERRACOTTA_RED;
            case SILVER:
                return ModBlocks.STAIRS_TERRACOTTA_SILVER;
            case YELLOW:
                return ModBlocks.STAIRS_TERRACOTTA_YELLOW;
            case WHITE:
            default:
                return ModBlocks.STAIRS_TERRACOTTA_WHITE;
        }
    }
}
