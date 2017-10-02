package net.pl3x.forge.client.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerShopOwner;
import net.pl3x.forge.client.network.PacketHandler;
import net.pl3x.forge.client.network.ShopChangePacket;
import net.pl3x.forge.client.network.ShopClientRequestPacket;
import net.pl3x.forge.client.network.ShopUpdateClientPacket;

import javax.annotation.Nullable;

public class TileEntityShop extends TileEntity implements ITickable, IInteractionObject {
    public EnumFacing facing;
    public int price = 0;
    public int quantity = 0;
    public ItemStack stack = ItemStack.EMPTY;

    public ItemStackHandler inventory = new ItemStackHandler(54) {
        @Override
        public void onContentsChanged(int slot) {
            if (!world.isRemote) {
                PacketHandler.INSTANCE.sendToAllAround(new ShopUpdateClientPacket(TileEntityShop.this),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }
    };

    public TileEntityShop() {
        this(EnumFacing.SOUTH);
    }

    public TileEntityShop(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            PacketHandler.INSTANCE.sendToServer(new ShopClientRequestPacket(this));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("price", price);
        compound.setInteger("quantity", quantity);
        return super.writeToNBT(compound);
    }

    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        price = compound.getInteger("price");
        quantity = compound.getInteger("quantity");
        super.readFromNBT(compound);
        updateStack();
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

    public void update() {
    }

    public String getName() {
        return "tile.shop";
    }

    public boolean hasCustomName() {
        return false;
    }

    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName());
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerShopOwner(playerInventory, this);
    }

    public String getGuiID() {
        return Pl3xForgeClient.modId + ":" + "shop";
    }

    public void incrementPrice() {
        if (price < 999) {
            price++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_PRICE));
    }

    public void decrementPrice() {
        if (price > 0) {
            price--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_PRICE));
    }

    public void incrementQuantity() {
        if (quantity < 999) {
            quantity++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_QUANTITY));
    }

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_QUANTITY));
    }

    public void updateStack() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                this.stack = stack.copy();
                this.stack.setCount(1);
                return;
            }
        }
        stack = ItemStack.EMPTY;
    }
}
