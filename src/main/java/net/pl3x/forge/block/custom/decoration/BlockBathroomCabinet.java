package net.pl3x.forge.block.custom.decoration;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.pl3x.forge.block.BlockDirectional;

public class BlockBathroomCabinet extends BlockDirectional {
    public BlockBathroomCabinet() {
        super(Material.WOOD, "bathroom_cabinet");
        setSoundType(SoundType.METAL);
        setHardness(1);

        setCreativeTab(CreativeTabs.DECORATIONS);

        AABB_NORTH = new AxisAlignedBB(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.1875D);
        AABB_SOUTH = new AxisAlignedBB(0.875D, 0.125D, 0.8125D, 0.125D, 0.875D, 1.0D);
        AABB_EAST = new AxisAlignedBB(0.8125D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
        AABB_WEST = new AxisAlignedBB(0.0D, 0.125D, 0.125D, 0.1875D, 0.875D, 0.875D);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.QUARTZ;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
