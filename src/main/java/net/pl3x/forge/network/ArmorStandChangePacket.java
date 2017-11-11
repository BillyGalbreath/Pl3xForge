package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Rotations;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.Logger;

public class ArmorStandChangePacket implements IMessage {
    private int entityId;
    private int dimension;
    private int key;
    private int value;

    public ArmorStandChangePacket() {
    }

    public ArmorStandChangePacket(int entityId, int dimension, int key, int value) {
        this.entityId = entityId;
        this.dimension = dimension;
        this.key = key;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        dimension = buf.readInt();
        key = buf.readInt();
        value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(dimension);
        buf.writeInt(key);
        buf.writeInt(value);
    }

    public static class Handler implements IMessageHandler<ArmorStandChangePacket, IMessage> {
        @Override
        public IMessage onMessage(ArmorStandChangePacket packet, MessageContext context) {
            EntityPlayerMP player = context.getServerHandler().player;
            if (player.dimension != packet.dimension) {
                Logger.warn("Received ArmorStand change packet from " + player.getName() + " for a different dimension");
                return null;
            }
            player.getServerWorld().addScheduledTask(() -> {
                Entity entity = player.world.getEntityByID(packet.entityId);
                if (entity == null || entity.isDead) {
                    Logger.warn("Received ArmorStand change packet from " + player.getName() + " for non existent entity");
                    return;
                }
                if (!(entity instanceof EntityArmorStand)) {
                    Logger.warn("Received ArmorStand change packet from " + player.getName() + " but entity is not an armorstand");
                    return;
                }

                EntityArmorStand armorStand = (EntityArmorStand) entity;
                if (packet.key == 5) { // baby
                    armorStand.setSmall(packet.value == 1);
                } else if (packet.key == 6) { // arms
                    armorStand.setShowArms(packet.value == 1);
                } else if (packet.key == 7) { // base
                    armorStand.setNoBasePlate(packet.value != 1);
                } else if (packet.key == 8) { // rotate
                    armorStand.rotationYaw = packet.value;
                } else if (packet.key == 9) { // head x
                    Rotations rot = armorStand.getHeadRotation();
                    armorStand.setHeadRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 10) { // head y
                    Rotations rot = armorStand.getHeadRotation();
                    armorStand.setHeadRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 11) { // head z
                    Rotations rot = armorStand.getHeadRotation();
                    armorStand.setHeadRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                } else if (packet.key == 12) { // body x
                    Rotations rot = armorStand.getBodyRotation();
                    armorStand.setBodyRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 13) { // body y
                    Rotations rot = armorStand.getBodyRotation();
                    armorStand.setBodyRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 14) { // body z
                    Rotations rot = armorStand.getBodyRotation();
                    armorStand.setBodyRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                } else if (packet.key == 15) { // left leg x
                    Rotations rot = armorStand.getLeftLegRotation();
                    armorStand.setLeftLegRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 16) { // left leg y
                    Rotations rot = armorStand.getLeftLegRotation();
                    armorStand.setLeftLegRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 17) { // left leg z
                    Rotations rot = armorStand.getLeftLegRotation();
                    armorStand.setLeftLegRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                } else if (packet.key == 18) { // right leg x
                    Rotations rot = armorStand.getRightLegRotation();
                    armorStand.setRightLegRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 19) { // right leg y
                    Rotations rot = armorStand.getRightLegRotation();
                    armorStand.setRightLegRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 20) { // right leg z
                    Rotations rot = armorStand.getRightLegRotation();
                    armorStand.setRightLegRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                } else if (packet.key == 21) { // left arm x
                    Rotations rot = armorStand.getLeftArmRotation();
                    armorStand.setLeftArmRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 22) { // left arm y
                    Rotations rot = armorStand.getLeftArmRotation();
                    armorStand.setLeftArmRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 23) { // left arm z
                    Rotations rot = armorStand.getLeftArmRotation();
                    armorStand.setLeftArmRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                } else if (packet.key == 24) { // right arm x
                    Rotations rot = armorStand.getRightArmRotation();
                    armorStand.setRightArmRotation(new Rotations(packet.value, rot.getY(), rot.getZ()));
                } else if (packet.key == 25) { // right arm y
                    Rotations rot = armorStand.getRightArmRotation();
                    armorStand.setRightArmRotation(new Rotations(rot.getX(), packet.value, rot.getZ()));
                } else if (packet.key == 26) { // right arm z
                    Rotations rot = armorStand.getRightArmRotation();
                    armorStand.setRightArmRotation(new Rotations(rot.getX(), rot.getY(), packet.value));
                }

                PacketHandler.INSTANCE.sendToAllAround(new ArmorStandRefreshPacket(packet.entityId, packet.dimension, armorStand),
                        new NetworkRegistry.TargetPoint(armorStand.world.provider.getDimension(),
                                armorStand.posX, armorStand.posY, armorStand.posZ, 64));
            });
            return null;
        }
    }
}
