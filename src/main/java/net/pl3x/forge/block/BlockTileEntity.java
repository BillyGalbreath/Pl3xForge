package net.pl3x.forge.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTileEntity<TE extends TileEntity> extends BlockBase {
    public BlockTileEntity(Material material, String name) {
        super(material, name);

        ModBlocks.__BLOCKS__.add(this);
    }

    public abstract Class<TE> getTileEntityClass();

    public abstract TE getTileEntity(IBlockAccess world, BlockPos pos);

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
