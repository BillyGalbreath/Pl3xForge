package net.pl3x.forge.core.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IPlayerData.class)
    public static final Capability<IPlayerData> PLAYER_DATA_CAPABILITY = null;

    private IPlayerData instance = PLAYER_DATA_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PLAYER_DATA_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == PLAYER_DATA_CAPABILITY ? PLAYER_DATA_CAPABILITY.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return PLAYER_DATA_CAPABILITY.getStorage().writeNBT(PLAYER_DATA_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        PLAYER_DATA_CAPABILITY.getStorage().readNBT(PLAYER_DATA_CAPABILITY, this.instance, null, nbt);
    }
}
