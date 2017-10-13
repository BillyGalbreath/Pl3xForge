package net.pl3x.forge.client.listener;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.configuration.ModConfig;

public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.showClaims.isPressed()) {
            ModConfig.claimVisuals.enabled = !ModConfig.claimVisuals.enabled;
            ConfigManager.sync(Pl3xForgeClient.modId, Config.Type.INSTANCE);
        }
    }
}
