package net.pl3x.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.math.Rotations;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.forge.Logger;

public class ArmorStandRefreshPacket implements IMessage {
    private int entityId;
    private int dimension;
    private int rotation;
    private int headX;
    private int headY;
    private int headZ;
    private int bodyX;
    private int bodyY;
    private int bodyZ;
    private int leftLegX;
    private int leftLegY;
    private int leftLegZ;
    private int rightLegX;
    private int rightLegY;
    private int rightLegZ;
    private int leftArmX;
    private int leftArmY;
    private int leftArmZ;
    private int rightArmX;
    private int rightArmY;
    private int rightArmZ;
    private boolean baby;
    private boolean arms;
    private boolean base;

    public ArmorStandRefreshPacket() {
    }

    public ArmorStandRefreshPacket(int entityId, int dimension, EntityArmorStand armorStand) {
        this.entityId = entityId;
        this.dimension = dimension;
        this.rotation = (int) armorStand.rotationYaw;
        this.headX = (int) armorStand.getHeadRotation().getX();
        this.headY = (int) armorStand.getHeadRotation().getY();
        this.headZ = (int) armorStand.getBodyRotation().getZ();
        this.bodyX = (int) armorStand.getBodyRotation().getX();
        this.bodyY = (int) armorStand.getBodyRotation().getY();
        this.bodyZ = (int) armorStand.getBodyRotation().getZ();
        this.leftLegX = (int) armorStand.getLeftLegRotation().getX();
        this.leftLegY = (int) armorStand.getLeftLegRotation().getY();
        this.leftLegZ = (int) armorStand.getLeftLegRotation().getZ();
        this.rightLegX = (int) armorStand.getRightLegRotation().getX();
        this.rightLegY = (int) armorStand.getRightLegRotation().getY();
        this.rightLegZ = (int) armorStand.getRightLegRotation().getZ();
        this.leftArmX = (int) armorStand.getLeftArmRotation().getX();
        this.leftArmY = (int) armorStand.getLeftArmRotation().getY();
        this.leftArmZ = (int) armorStand.getLeftArmRotation().getZ();
        this.rightArmX = (int) armorStand.getRightArmRotation().getX();
        this.rightArmY = (int) armorStand.getRightArmRotation().getY();
        this.rightArmZ = (int) armorStand.getRightArmRotation().getZ();
        this.baby = armorStand.isSmall();
        this.arms = armorStand.getShowArms();
        this.base = !armorStand.hasNoBasePlate();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        dimension = buf.readInt();
        rotation = buf.readInt();
        headX = buf.readInt();
        headY = buf.readInt();
        headZ = buf.readInt();
        bodyX = buf.readInt();
        bodyY = buf.readInt();
        bodyZ = buf.readInt();
        leftLegX = buf.readInt();
        leftLegY = buf.readInt();
        leftLegZ = buf.readInt();
        rightLegX = buf.readInt();
        rightLegY = buf.readInt();
        rightLegZ = buf.readInt();
        leftArmX = buf.readInt();
        leftArmY = buf.readInt();
        leftArmZ = buf.readInt();
        rightArmX = buf.readInt();
        rightArmY = buf.readInt();
        rightArmZ = buf.readInt();
        baby = buf.readBoolean();
        arms = buf.readBoolean();
        base = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(dimension);
        buf.writeInt(rotation);
        buf.writeInt(headX);
        buf.writeInt(headY);
        buf.writeInt(headZ);
        buf.writeInt(bodyX);
        buf.writeInt(bodyY);
        buf.writeInt(bodyZ);
        buf.writeInt(leftLegX);
        buf.writeInt(leftLegY);
        buf.writeInt(leftLegZ);
        buf.writeInt(rightLegX);
        buf.writeInt(rightLegY);
        buf.writeInt(rightLegZ);
        buf.writeInt(leftArmX);
        buf.writeInt(leftArmY);
        buf.writeInt(leftArmZ);
        buf.writeInt(rightArmX);
        buf.writeInt(rightArmY);
        buf.writeInt(rightArmZ);
        buf.writeBoolean(baby);
        buf.writeBoolean(arms);
        buf.writeBoolean(base);
    }

    public static class Handler implements IMessageHandler<ArmorStandRefreshPacket, IMessage> {
        @Override
        public IMessage onMessage(ArmorStandRefreshPacket packet, MessageContext context) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player.dimension != packet.dimension) {
                Logger.warn("Received ArmorStand refresh packet for a different dimension");
                return null;
            }
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = player.world.getEntityByID(packet.entityId);
                if (entity == null || entity.isDead) {
                    Logger.warn("Received ArmorStand change packet for non existent entity");
                    return;
                }
                if (!(entity instanceof EntityArmorStand)) {
                    Logger.warn("Received ArmorStand change packet but entity is not an armorstand");
                    return;
                }
                EntityArmorStand armorStand = (EntityArmorStand) entity;
                armorStand.rotationYaw = packet.rotation;
                armorStand.setHeadRotation(new Rotations(packet.headX, packet.headY, packet.headZ));
                armorStand.setBodyRotation(new Rotations(packet.bodyX, packet.bodyY, packet.bodyZ));
                armorStand.setLeftLegRotation(new Rotations(packet.leftLegX, packet.leftLegY, packet.leftLegZ));
                armorStand.setRightLegRotation(new Rotations(packet.rightLegX, packet.rightLegY, packet.rightLegZ));
                armorStand.setLeftArmRotation(new Rotations(packet.leftArmX, packet.leftArmY, packet.leftArmZ));
                armorStand.setRightArmRotation(new Rotations(packet.rightArmX, packet.rightArmY, packet.rightArmZ));
                armorStand.setSmall(packet.baby);
                armorStand.setShowArms(packet.arms);
                armorStand.setNoBasePlate(!packet.base);
            });
            return null;
        }
    }
}
