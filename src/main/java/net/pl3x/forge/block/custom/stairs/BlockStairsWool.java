package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public class BlockStairsWool extends BlockStairs {
    private final EnumDyeColor color;

    public BlockStairsWool(EnumDyeColor color) {
        super(Material.CLOTH, "stairs_wool_" + color.getName());
        this.color = color;
        setSoundType(SoundType.CLOTH);
        setHardness(1);

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        ModBlocks.blocks.add(this);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.getBlockColor(color);
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

    private static BlockStairsWool getBlock(EnumDyeColor color) {
        switch (color) {
            case BLACK:
                return ModBlocks.STAIRS_WOOL_BLACK;
            case BLUE:
                return ModBlocks.STAIRS_WOOL_BLUE;
            case BROWN:
                return ModBlocks.STAIRS_WOOL_BROWN;
            case CYAN:
                return ModBlocks.STAIRS_WOOL_CYAN;
            case GRAY:
                return ModBlocks.STAIRS_WOOL_GRAY;
            case GREEN:
                return ModBlocks.STAIRS_WOOL_GREEN;
            case LIGHT_BLUE:
                return ModBlocks.STAIRS_WOOL_LIGHT_BLUE;
            case LIME:
                return ModBlocks.STAIRS_WOOL_LIME;
            case MAGENTA:
                return ModBlocks.STAIRS_WOOL_MAGENTA;
            case ORANGE:
                return ModBlocks.STAIRS_WOOL_ORANGE;
            case PINK:
                return ModBlocks.STAIRS_WOOL_PINK;
            case PURPLE:
                return ModBlocks.STAIRS_WOOL_PURPLE;
            case RED:
                return ModBlocks.STAIRS_WOOL_RED;
            case SILVER:
                return ModBlocks.STAIRS_WOOL_SILVER;
            case YELLOW:
                return ModBlocks.STAIRS_WOOL_YELLOW;
            case WHITE:
            default:
                return ModBlocks.STAIRS_WOOL_WHITE;
        }
    }
}
