package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.capability.PlayerDataProvider;
import net.pl3x.forge.capability.PlayerData;
import net.pl3x.forge.tileentity.TileEntityShop;
import net.pl3x.forge.util.NumberUtil;

public class ShopChangePacket implements IMessage {
    public static final byte INCREMENT_PRICE = 0;
    public static final byte DECREMENT_PRICE = 1;
    public static final byte INCREMENT_QUANTITY = 2;
    public static final byte DECREMENT_QUANTITY = 3;
    public static final byte INCREMENT_SCALE = 4;
    public static final byte DECREMENT_SCALE = 5;
    public static final byte INCREMENT_Y_OFFSET = 6;
    public static final byte DECREMENT_Y_OFFSET = 7;
    public static final byte INCREMENT_X_ROT = 8;
    public static final byte DECREMENT_X_ROT = 9;
    public static final byte INCREMENT_Y_ROT = 10;
    public static final byte DECREMENT_Y_ROT = 11;
    public static final byte INCREMENT_Z_ROT = 12;
    public static final byte DECREMENT_Z_ROT = 13;
    public static final byte ADD_COIN = 14;
    public static final byte REMOVE_COIN = 15;

    private BlockPos pos;
    private int packetType;

    public ShopChangePacket() {
    }

    public ShopChangePacket(BlockPos pos, byte packetType) {
        this.pos = pos;
        this.packetType = packetType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        packetType = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeByte(packetType);
    }

    public static class Handler implements IMessageHandler<ShopChangePacket, IMessage> {
        @Override
        public IMessage onMessage(ShopChangePacket packet, MessageContext context) {
            EntityPlayerMP player = context.getServerHandler().player;
            TileEntity te = player.world.getTileEntity(packet.pos);
            if (!(te instanceof TileEntityShop)) {
                return null; // ignore
            }
            TileEntityShop shop = (TileEntityShop) te;
            player.getServerWorld().addScheduledTask(() -> {
                PlayerData capability = player.getCapability(PlayerDataProvider.CAPABILITY, null);
                switch (packet.packetType) {
                    case INCREMENT_PRICE:
                        if (shop.price < 9999) {
                            shop.price++;
                        }
                        break;
                    case DECREMENT_PRICE:
                        if (shop.price > 0) {
                            shop.price--;
                        }
                        break;
                    case INCREMENT_QUANTITY:
                        if (shop.quantity < 9999) {
                            shop.quantity++;
                        }
                        break;
                    case DECREMENT_QUANTITY:
                        if (shop.quantity > 0) {
                            shop.quantity--;
                        }
                        break;
                    case INCREMENT_SCALE:
                        if (shop.display_scale < 1.5) {
                            shop.display_scale = NumberUtil.round(shop.display_scale + 0.01);
                        }
                        break;
                    case DECREMENT_SCALE:
                        if (shop.display_scale > 0.0) {
                            shop.display_scale = NumberUtil.round(shop.display_scale - 0.01);
                        }
                        break;
                    case INCREMENT_Y_OFFSET:
                        if (shop.display_yOffset < 1.5) {
                            shop.display_yOffset = NumberUtil.round(shop.display_yOffset + 0.01);
                        }
                        break;
                    case DECREMENT_Y_OFFSET:
                        if (shop.display_yOffset > 0.0) {
                            shop.display_yOffset = NumberUtil.round(shop.display_yOffset - 0.01);
                        }
                        break;
                    case INCREMENT_X_ROT:
                        if (shop.display_rotateX < 360) {
                            shop.display_rotateX++;
                        }
                        break;
                    case DECREMENT_X_ROT:
                        if (shop.display_rotateX > -360) {
                            shop.display_rotateX--;
                        }
                        break;
                    case INCREMENT_Y_ROT:
                        if (shop.display_rotateY < 360) {
                            shop.display_rotateY++;
                        }
                        break;
                    case DECREMENT_Y_ROT:
                        if (shop.display_rotateY > -360) {
                            shop.display_rotateY--;
                        }
                        break;
                    case INCREMENT_Z_ROT:
                        if (shop.display_rotateZ < 360) {
                            shop.display_rotateZ++;
                        }
                        break;
                    case DECREMENT_Z_ROT:
                        if (shop.display_rotateZ > -360) {
                            shop.display_rotateZ--;
                        }
                        break;
                    case ADD_COIN:
                        if (capability.getCoins() > 0) {
                            capability.setCoins(capability.getCoins() - 1);
                            shop.coins++;
                        }
                        PacketHandler.updatePlayerData(player);
                        break;
                    case REMOVE_COIN:
                        if (shop.coins > 0) {
                            capability.setCoins(capability.getCoins() + 1);
                            shop.coins--;
                        }
                        PacketHandler.updatePlayerData(player);
                        break;
                }
                shop.markDirty();
                PacketHandler.INSTANCE.sendToAllAround(new ShopUpdateClientPacket(shop),
                        new NetworkRegistry.TargetPoint(te.getWorld().provider.getDimension(),
                                te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), 64));
            });
            return null;
        }
    }
}
