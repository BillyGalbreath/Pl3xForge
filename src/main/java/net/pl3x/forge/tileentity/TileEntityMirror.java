package net.pl3x.forge.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.custom.decoration.BlockMirror;
import net.pl3x.forge.entity.EntityMirror;
import net.pl3x.forge.tileentity.renderer.TileEntityMirrorRenderer;

public class TileEntityMirror extends TileEntity {
    private EntityMirror bindedMirror = null;

    @SideOnly(Side.CLIENT)
    public EntityMirror getMirror() {
        if (bindedMirror == null) {
            if (getBlockType() instanceof BlockMirror) {
                EnumFacing facing = world.getBlockState(pos).getValue(BlockMirror.FACING);
                bindedMirror = new EntityMirror(world, pos.getX(), pos.getY(), pos.getZ(), facing);
                world.spawnEntity(bindedMirror);
            }
        }
        return bindedMirror;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onChunkUnload() {
        if (bindedMirror != null) {
            TileEntityMirrorRenderer.removeRegisteredMirror(bindedMirror);
            bindedMirror.setDead();
            bindedMirror = null;
        }
    }
}
