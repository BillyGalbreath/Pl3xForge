package net.pl3x.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.entity.EntityVehicle;

public class KeyInputHandler {
    @SubscribeEvent
    public void on(InputEvent.KeyInputEvent event) {
        if (KeyBindings.showClaims.isPressed()) {
            ClientConfig.claimVisuals.enabled = !ClientConfig.claimVisuals.enabled;
            ConfigManager.sync(Pl3x.modId, Config.Type.INSTANCE);
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
