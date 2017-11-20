package net.pl3x.forge.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.PlateRequestUpdatePacket;
import net.pl3x.forge.network.PlateUpdatePacket;
import net.pl3x.forge.util.ItemStackUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityPlate extends TileEntity {
    public int rotY = 0;

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!world.isRemote) {
                PacketHandler.INSTANCE.sendToAllAround(new PlateUpdatePacket(TileEntityPlate.this),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }

        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (ItemStackUtil.isFood(stack.getItem())) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
        }
    };

    public TileEntityPlate() {
        this(EnumFacing.SOUTH);
    }

    public TileEntityPlate(EnumFacing facing) {
        switch (facing) {
            case NORTH:
                rotY = 0;
                break;
            case WEST:
                rotY = 90;
                break;
            case EAST:
                rotY = 270;
                break;
            case SOUTH:
            default:
                rotY = 180;
        }
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            PacketHandler.INSTANCE.sendToServer(new PlateRequestUpdatePacket(this));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }
}
