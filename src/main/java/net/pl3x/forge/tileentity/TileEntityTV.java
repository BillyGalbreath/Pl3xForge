package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.pl3x.forge.block.custom.decoration.BlockTV;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.TVUpdateChannelPacket;
import net.pl3x.forge.scheduler.Pl3xRunnable;

public class TileEntityTV extends TileEntity implements ITickable {
    public BlockTV.EnumChannel channel;

    public TileEntityTV() {
        this(BlockTV.EnumChannel.OFF);
    }

    public TileEntityTV(BlockTV.EnumChannel channel) {
        this.channel = channel;
    }

    @Override
    public void update() {
        //
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
}
