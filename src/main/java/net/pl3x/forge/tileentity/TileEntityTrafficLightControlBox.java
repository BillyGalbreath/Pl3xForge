package net.pl3x.forge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.TrafficLightControlBoxUpdatePacket;
import net.pl3x.forge.util.PlayerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TileEntityTrafficLightControlBox extends TileEntity implements ITickable {
    public UUID owner;
    public int tick = 0;
    public IntersectionState intersectionState = IntersectionState.NS_GREEN_EW_RED;
    public final Map<IntersectionState, Integer> ticksPerCycle = new HashMap<>();

    public TileEntityTrafficLightControlBox() {
        ticksPerCycle.put(IntersectionState.NS_GREEN_EW_RED, 100);
        ticksPerCycle.put(IntersectionState.NS_YELLOW_EW_RED, 20);
        ticksPerCycle.put(IntersectionState.NS_RED_EW_GREEN, 100);
        ticksPerCycle.put(IntersectionState.NS_RED_EW_YELLOW, 20);

        tick = ThreadLocalRandom.current().nextInt(ticksPerCycle.get(IntersectionState.NS_GREEN_EW_RED));
    }

    @Override
    public void update() {
        // tick the logic
        tick++;
        if (tick > ticksPerCycle.get(intersectionState)) {
            tick = 0;
            int next = intersectionState.ordinal() + 1;
            if (next >= IntersectionState.values().length) {
                next = 0;
            }
            intersectionState = IntersectionState.values()[next];
            updateClients();
        }
    }

    public void updateClients() {
        if (!world.isRemote) {
            PacketHandler.INSTANCE.sendToAllAround(new TrafficLightControlBoxUpdatePacket(pos, tick, intersectionState),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128));
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("owner", owner == null ? "" : owner.toString());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        try {
            owner = UUID.fromString(compound.getString("owner"));
        } catch (IllegalArgumentException ignore) {
        }
        super.readFromNBT(compound);
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isOwner(EntityPlayer player) {
        return player.getUniqueID().equals(owner) || PlayerUtil.isOp(player);
    }

    public enum IntersectionState {
        NS_GREEN_EW_RED,
        NS_YELLOW_EW_RED,
        NS_RED_EW_GREEN,
        NS_RED_EW_YELLOW
    }
}
