package net.pl3x.forge.tileentity;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.container.ContainerShopOwner;
import net.pl3x.forge.gui.HUDBalance;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.ShopChangePacket;
import net.pl3x.forge.network.ShopClientRequestPacket;
import net.pl3x.forge.network.ShopUpdateClientPacket;
import net.pl3x.forge.util.ItemStackUtil;
import net.pl3x.forge.util.NumberUtil;
import net.pl3x.forge.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileEntityShop extends TileEntity implements ITickable, IInteractionObject {
    public UUID owner;
    public int price = 0;
    public int quantity = 0;
    public long coins = 0;

    public double display_scale = 1.0; // 0.6 - 1.0
    public double display_yOffset = 0.7; // 0.6 - 1.0
    public int display_rotateX = 0; // -360 - 360
    public int display_rotateY = 0; // -360 - 360
    public int display_rotateZ = 0; // -360 - 360

    public final int facingRot;
    public final int signRot;
    public float signPosX = 0.5F;
    public float signPosY = 0.5F;
    public float signPosZ = 0.5F;

    public ItemStack stack = ItemStack.EMPTY;
    public int stackCount = 0;
    public IBakedModel model;

    public ItemStackHandler inventory = new ItemStackHandler(54) {
        @Override
        public void onContentsChanged(int slot) {
            if (!world.isRemote) {
                updateClients();
            }
        }
    };

    public TileEntityShop() {
        this(EnumFacing.EAST);
    }

    public TileEntityShop(EnumFacing facing) {
        switch (facing) {
            case NORTH: // sign faces to the EAST
                facingRot = 90;
                signPosX = 0.7501F;
                signPosZ = 0.5F;
                signRot = 270;
                break;
            case SOUTH: // sign faces to the WEST
                facingRot = -90;
                signPosX = 0.2499F;
                signPosZ = 0.5F;
                signRot = 90;
                break;
            case WEST: // sign faces to the NORTH
                facingRot = 180;
                signPosX = 0.5F;
                signPosZ = 0.2499F;
                signRot = 0;
                break;
            case EAST: // sign faces to the SOUTH
            default:
                facingRot = 0;
                signPosX = 0.5F;
                signPosZ = 0.7501F;
                signRot = 180;
                break;
        }
    }

    public void updateClients() {
        if (!world.isRemote) {
            PacketHandler.INSTANCE.sendToAllAround(new ShopUpdateClientPacket(TileEntityShop.this),
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
        }
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            PacketHandler.INSTANCE.sendToServer(new ShopClientRequestPacket(this));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("owner", owner == null ? "" : owner.toString());
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setInteger("price", price);
        compound.setInteger("quantity", quantity);
        compound.setLong("coins", coins);
        NBTTagCompound display = new NBTTagCompound();
        display.setDouble("scale", display_scale);
        display.setDouble("y-offset", display_yOffset);
        display.setInteger("rot-x", display_rotateX);
        display.setInteger("rot-y", display_rotateY);
        display.setInteger("rot-z", display_rotateZ);
        compound.setTag("display", display);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        try {
            owner = UUID.fromString(compound.getString("owner"));
        } catch (IllegalArgumentException ignore) {
        }
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        price = compound.getInteger("price");
        quantity = compound.getInteger("quantity");
        coins = compound.getLong("coins");
        if (compound.hasKey("display")) {
            NBTTagCompound display = compound.getCompoundTag("display");
            display_scale = NumberUtil.round(display.getDouble("scale"));
            display_yOffset = NumberUtil.round(display.getDouble("y-offset"));
            display_rotateX = display.getInteger("rot-x");
            display_rotateY = display.getInteger("rot-y");
            display_rotateZ = display.getInteger("rot-z");
        }
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

    @Override
    public void update() {
    }

    @Override
    public String getName() {
        return "tile.shop";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(getName());
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerShopOwner(playerInventory, this, true);
    }

    @Override
    public String getGuiID() {
        return Pl3x.modId + ":" + "shop";
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

    @SideOnly(Side.CLIENT)
    public void incrementPrice() {
        if (price < 999) {
            price++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_PRICE));
    }

    @SideOnly(Side.CLIENT)
    public void decrementPrice() {
        if (price > 0) {
            price--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_PRICE));
    }

    @SideOnly(Side.CLIENT)
    public void incrementQuantity() {
        if (quantity < 999) {
            quantity++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_QUANTITY));
    }

    @SideOnly(Side.CLIENT)
    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_QUANTITY));
    }

    @SideOnly(Side.CLIENT)
    public void incrementScale() {
        if (display_scale < 1.5) {
            display_scale = NumberUtil.round(display_scale + 0.01); // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_SCALE));
    }

    @SideOnly(Side.CLIENT)
    public void decrementScale() {
        if (display_scale > 0.0) {
            display_scale = NumberUtil.round(display_scale - 0.01); // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_SCALE));
    }

    @SideOnly(Side.CLIENT)
    public void incrementYOffset() {
        if (display_yOffset < 1.5) {
            display_yOffset = NumberUtil.round(display_yOffset + 0.01); // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_Y_OFFSET));
    }

    @SideOnly(Side.CLIENT)
    public void decrementYOffset() {
        if (display_yOffset > 0.0) {
            display_yOffset = NumberUtil.round(display_yOffset - 0.01); // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_Y_OFFSET));
    }

    @SideOnly(Side.CLIENT)
    public void incrementRotX() {
        if (display_rotateX < 360) {
            display_rotateX++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_X_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void decrementRotX() {
        if (display_rotateX > -360) {
            display_rotateX--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_X_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void incrementRotY() {
        if (display_rotateY < 360) {
            display_rotateY++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_Y_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void decrementRotY() {
        if (display_rotateY > -360) {
            display_rotateY--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_Y_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void incrementRotZ() {
        if (display_rotateZ < 360) {
            display_rotateZ++; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.INCREMENT_Z_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void decrementRotZ() {
        if (display_rotateZ > -360) {
            display_rotateZ--; // go ahead and show local visual change
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.DECREMENT_Z_ROT));
    }

    @SideOnly(Side.CLIENT)
    public void addCoin() {
        if (HUDBalance.balance > 0) {
            coins++;
            HUDBalance.balance--;
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.ADD_COIN));
    }

    @SideOnly(Side.CLIENT)
    public void removeCoin() {
        if (coins > 0) {
            coins--;
            HUDBalance.balance++;
        }
        PacketHandler.INSTANCE.sendToServer(new ShopChangePacket(pos, ShopChangePacket.REMOVE_COIN));
    }

    public void updateStack() {
        stackCount = 0;
        model = null;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stackCount += stack.getCount();
                this.stack = stack.copy();
                this.stack.setCount(1);
            }
        }
        if (stackCount < 1) {
            stack = ItemStack.EMPTY;
        }
    }

    public int processPurchase(ItemStack stack, EntityPlayerMP player) {
        ItemStack[] contents = ItemStackUtil.getContents(inventory);
        int i = stack.getCount();
        for (int j = 0; j < contents.length; j++) {
            ItemStack slotStack = contents[j];
            if (!slotStack.isEmpty() && ItemStackUtil.equalsIgnoreCount(slotStack, stack)) {
                if (slotStack.getCount() > i) {
                    contents[j].setCount(slotStack.getCount() - i);
                    ItemStackUtil.setContents(inventory, contents);
                    return 0;
                }
                if (slotStack.getCount() == i) {
                    contents[j] = ItemStack.EMPTY;
                    ItemStackUtil.setContents(inventory, contents);
                    return 0;
                }
                i -= slotStack.getCount();
                contents[j] = ItemStack.EMPTY;
            }
        }
        if (i == 0) {
            ItemStackUtil.setContents(inventory, contents);
        }
        return i;
    }
}
