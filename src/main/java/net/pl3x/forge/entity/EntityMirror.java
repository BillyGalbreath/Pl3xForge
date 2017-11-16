package net.pl3x.forge.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.custom.decoration.BlockMirror;
import net.pl3x.forge.tileentity.renderer.TileEntityMirrorRenderer;

@SideOnly(Side.CLIENT)
public class EntityMirror extends Entity {
    private Minecraft mc = Minecraft.getMinecraft();

    private int facing;
    public boolean rendering;

    public EntityMirror(World worldIn) {
        super(worldIn);
    }

    public EntityMirror(World worldIn, double x, double y, double z, EnumFacing facing) {
        super(worldIn);
        this.facing = facing.getHorizontalIndex();
        this.noClip = true;
        this.height = 0.001F;
        this.width = 0.001F;
        setPostionConsideringRotation(x, y, z, facing.getHorizontalIndex());
    }

    public void setPostionConsideringRotation(double x, double y, double z, int rotation) {
        double offset = -0.43;
        switch (rotation) {
            case 2:
                z += offset;
                break;
            case 0:
                z -= offset;
                break;
            case 3:
                x -= offset;
                break;
            case 1:
                x += offset;
                break;
        }
        setPosition(x + 0.5D, y + 0.5, z + 0.5D);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onUpdate() {
        super.onUpdate();

        if (rendering) {
            double dy = this.posZ - mc.player.posZ;
            double dx = this.posX - mc.player.posX;
            double angleYaw = Math.atan2(dy, dx) * (180D / Math.PI);

            if (facing == 1) {
                angleYaw -= 90D;
                if (angleYaw <= -180D) {
                    angleYaw = 360D + angleYaw;
                }

            }
            if (facing > 1) {
                angleYaw += 360D - (facing * 90D);
            }

            if (angleYaw >= 170D) {
                angleYaw = 170D;
            }
            if (angleYaw <= 10D) {
                angleYaw = 10D;
            }

            this.rotationYaw = (float) (-90F + facing * 90F - angleYaw);

            double distance = getDistance(mc.player);
            double height = (mc.player.getEyeHeight() + mc.player.posY) - this.posY;
            double anglePitch = Math.atan2(height, distance) * (180D / Math.PI);
            if (anglePitch > 45F) {
                anglePitch = 45F;
            }
            if (anglePitch < -45F) {
                anglePitch = -45F;
            }
            this.rotationPitch = (float) anglePitch;
        }

        if (!(world.getBlockState(getPosition().down()).getBlock() instanceof BlockMirror)) {
            TileEntityMirrorRenderer.removeRegisteredMirror(this);
            this.setDead();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
    }
}
