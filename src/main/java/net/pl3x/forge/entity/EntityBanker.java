package net.pl3x.forge.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.network.PacketHandler;

public class EntityBanker extends EntityCreature implements INpc {
    public EntityBanker(World world) {
        super(world);
        setSize(0.6F, 1.95F);
        setCanPickUpLoot(false);
        enablePersistence();
        setEntityInvulnerable(true);
    }

    public void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3, 1));
        tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8));
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack handStack = player.getHeldItem(hand);
        if (handStack.getItem() == Items.NAME_TAG) {
            handStack.interactWithEntity(player, this, hand);
            return true;
        }

        if (isEntityAlive() && !player.isSneaking()) {
            if (hand == EnumHand.MAIN_HAND) {
                player.addStat(StatList.TALKED_TO_VILLAGER);
            }
            if (!world.isRemote) {
                PacketHandler.updatePlayerData((EntityPlayerMP) player);
                player.openGui(Pl3x.instance, ModGuiHandler.BANKER, world, 0, 0, 0);
            }
        }
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setCanPickUpLoot(false);
        enablePersistence();
        setEntityInvulnerable(true);
    }

    @Override
    public boolean canDespawn() {
        return false; // we need you, banker!
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    public float getEyeHeight() {
        return 1.62F;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false; // no leashing! tisk tisk
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        // do not burn or create witch
    }

    @Override
    public void updateEquipmentIfNeeded(EntityItem itemEntity) {
        // do not pickup stuff
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        return false; // yeah, no inventory
    }
}
