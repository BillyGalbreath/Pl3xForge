package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.custom.decoration.BlockTV;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.TVUpdateChannelPacket;
import net.pl3x.forge.scheduler.Pl3xRunnable;

import java.io.IOException;

public class TileEntityTV extends TileEntity implements ITickable {
    public BlockTV.EnumChannel channel;
    public BlockTV.EnumChannel prevCh;
    public float uvMin = 0;
    public float uvMax = 1;

    public final int rot;
    public double xOffset;
    public double zOffset;

    public TileEntityTV() {
        this(BlockTV.EnumChannel.OFF, EnumFacing.SOUTH);
    }

    public TileEntityTV(BlockTV.EnumChannel channel, EnumFacing facing) {
        this.channel = channel;
        this.prevCh = channel;

        switch (facing) {
            case NORTH: // light faces to the EAST
                rot = 90;
                xOffset = -0.435;
                zOffset = -1.375;
                break;
            case SOUTH: // light faces to the WEST
                rot = -90;
                xOffset = 0.565;
                zOffset = -0.375;
                break;
            case WEST: // light faces to the NORTH
                rot = 180;
                xOffset = -0.435;
                zOffset = -0.375;
                break;
            case EAST: // light faces to the SOUTH
            default:
                rot = 0;
                xOffset = 0.565;
                zOffset = -1.375;
                break;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void update() {
        if (!ClientConfig.tvOptions.useTESR || channel == BlockTV.EnumChannel.OFF) {
            return; // TESR disabled by client
        }

        if (prevCh != channel) {
            setupTexture(channel);
        }

        float step = 1F / channel.getFrames();
        uvMin = step * channel.getFrame() - (step / 2);
        uvMax = step * channel.getFrame() - step;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("channel", channel.ordinal());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        try {
            channel = BlockTV.EnumChannel.values()[compound.getInteger("channel")];
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        super.readFromNBT(compound);
        new Pl3xRunnable() {
            public void run() {
                if (!world.isRemote) {
                    PacketHandler.INSTANCE.sendToAllAround(new TVUpdateChannelPacket(pos, channel),
                            new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
                }
            }
        }.runTaskLater(20);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1337, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt) {
        if (net.getDirection() == EnumPacketDirection.CLIENTBOUND && pkt.getTileEntityType() == 1337) {
            NBTTagCompound nbt = pkt.getNbtCompound();
            deserializeNBT(nbt);
        }
    }

    @SideOnly(Side.CLIENT)
    private void setupTexture(BlockTV.EnumChannel channel) {
        prevCh = this.channel;
        try {
            PngSizeInfo info = PngSizeInfo.makeFromResource(Minecraft.getMinecraft().getResourceManager().getResource(channel.getResource()));
            if (info.pngWidth != info.pngHeight) {
                channel.setFrames(info.pngHeight / info.pngWidth);
            } else {
                channel.setFrames(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
