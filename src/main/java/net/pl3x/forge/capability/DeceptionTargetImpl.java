package net.pl3x.forge.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class DeceptionTargetImpl implements DeceptionTarget {
    private NBTTagCompound targets = new NBTTagCompound();

    public void ignoreTarget(EntityLivingBase target, boolean ignored) {
        targets.setBoolean(target.getUniqueID().toString(), ignored);
    }

    public boolean hasIgnoredTarget(EntityLivingBase target) {
        return targets.hasKey(target.getUniqueID().toString());
    }

    public boolean shouldIgnoreTarget(EntityLivingBase target) {
        return targets.getBoolean(target.getUniqueID().toString());
    }

    @Override
    public NBTTagCompound getDataAsNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("targets", targets);
        return nbt;
    }

    @Override
    public void setDataFromNBT(NBTTagCompound compound) {
        targets = compound.getCompoundTag("targets");
    }

    public static class DeceptionTargetStorage implements Capability.IStorage<DeceptionTarget> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<DeceptionTarget> capability, DeceptionTarget instance, EnumFacing side) {
            return instance.getDataAsNBT();
        }

        @Override
        public void readNBT(Capability<DeceptionTarget> capability, DeceptionTarget instance, EnumFacing side, NBTBase nbt) {
            instance.setDataFromNBT((NBTTagCompound) nbt);
        }
    }
}
