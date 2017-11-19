package net.pl3x.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.EntityVehicle;
import net.pl3x.forge.gui.ModGuiHandler;

public class KeyInputHandler {
    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent event) {
        if (KeyBindings.showClaimsGUI.isPressed()) {
            Minecraft.getMinecraft().player.openGui(Pl3x.instance, ModGuiHandler.CLAIM,
                    Minecraft.getMinecraft().world, 0, 0, 0);
        }
        Entity vehicle = Minecraft.getMinecraft().player.getRidingEntity();
        if (vehicle != null && vehicle instanceof EntityVehicle) {
            ((EntityVehicle) vehicle).updateInputs(
                    KeyBindings.vehicleAccelerate.isKeyDown(),
                    KeyBindings.vehicleDecelerate.isKeyDown(),
                    KeyBindings.vehicleTurnLeft.isKeyDown(),
                    KeyBindings.vehicleTurnRight.isKeyDown(),
                    KeyBindings.vehicleAction.isKeyDown()
            );
        }
    }
}
