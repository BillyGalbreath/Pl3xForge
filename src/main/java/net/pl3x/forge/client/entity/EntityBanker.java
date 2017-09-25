package net.pl3x.forge.client.entity;

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
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBanker extends EntityCreature implements INpc {
    public EntityBanker(World world) {
        super(world);
        setSize(0.6F, 1.95F);
        setCanPickUpLoot(false);
        enablePersistence();
    }

    public void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
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
                // TODO open bank GUI
                //player.displayVillagerTradeGui(this);
                //
                //
                //
                //
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
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 12) {
            spawnParticles(EnumParticleTypes.HEART);
        } else if (id == 13) {
            spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        } else if (id == 14) {
            spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType) {
        for (int i = 0; i < 5; ++i) {
            double d0 = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            world.spawnParticle(particleType,
                    posX + rand.nextFloat() * width * 2.0D - width,
                    posY + rand.nextFloat() * height + 1.0D,
                    posZ + rand.nextFloat() * width * 2.0D - width,
                    d0, d1, d2);
        }
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
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        // TODO make invincible to damage and death
        //
        //
        //
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
