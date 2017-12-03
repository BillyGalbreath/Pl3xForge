package net.pl3x.forge.block.custom.stairs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public class BlockStairsGlassStained extends BlockStairs {
    private final EnumDyeColor color;

    public BlockStairsGlassStained(EnumDyeColor color) {
        super(Material.GLASS, "stairs_glass_" + color.getName());
        this.color = color;
        setSoundType(SoundType.GLASS);
        setHardness(1);

        setCreativeTab(TAB_STAIRS);

        ModBlocks.blocks.add(this);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        IBlockState state1 = world.getBlockState(pos.offset(face));
        Block block = state1.getBlock();
        return (block == this && ((BlockStairsGlassStained) block).color == color) ||
                (block == Blocks.STAINED_GLASS && state1.getValue(BlockStainedGlass.COLOR) == color);
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

    private static BlockStairsGlassStained getBlock(EnumDyeColor color) {
        switch (color) {
            case BLACK:
                return ModBlocks.STAIRS_GLASS_BLACK;
            case BLUE:
                return ModBlocks.STAIRS_GLASS_BLUE;
            case BROWN:
                return ModBlocks.STAIRS_GLASS_BROWN;
            case CYAN:
                return ModBlocks.STAIRS_GLASS_CYAN;
            case GRAY:
                return ModBlocks.STAIRS_GLASS_GRAY;
            case GREEN:
                return ModBlocks.STAIRS_GLASS_GREEN;
            case LIGHT_BLUE:
                return ModBlocks.STAIRS_GLASS_LIGHT_BLUE;
            case LIME:
                return ModBlocks.STAIRS_GLASS_LIME;
            case MAGENTA:
                return ModBlocks.STAIRS_GLASS_MAGENTA;
            case ORANGE:
                return ModBlocks.STAIRS_GLASS_ORANGE;
            case PINK:
                return ModBlocks.STAIRS_GLASS_PINK;
            case PURPLE:
                return ModBlocks.STAIRS_GLASS_PURPLE;
            case RED:
                return ModBlocks.STAIRS_GLASS_RED;
            case SILVER:
                return ModBlocks.STAIRS_GLASS_SILVER;
            case YELLOW:
                return ModBlocks.STAIRS_GLASS_YELLOW;
            case WHITE:
            default:
                return ModBlocks.STAIRS_GLASS_WHITE;
        }
    }
}
