package net.pl3x.forge.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public interface DeceptionTarget {
    void ignoreTarget(EntityLivingBase target, boolean ignored);

    boolean hasIgnoredTarget(EntityLivingBase target);

    boolean shouldIgnoreTarget(EntityLivingBase target);

    NBTTagCompound getDataAsNBT();

    void setDataFromNBT(NBTTagCompound compound);
}
