package net.pl3x.forge.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pl3x.forge.tileentity.TileEntityTrafficLightControlBox;

public class ContainerTrafficLightControlBox extends Container {
    public final EntityPlayer player;
    public final TileEntityTrafficLightControlBox controlBox;

    public ContainerTrafficLightControlBox(EntityPlayer player, TileEntityTrafficLightControlBox controlBox) {
        this.player = player;
        this.controlBox = controlBox;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return player != null && player.equals(playerIn);
    }
}
