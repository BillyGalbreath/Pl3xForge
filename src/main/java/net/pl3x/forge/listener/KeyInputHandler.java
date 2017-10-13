package net.pl3x.forge.listener;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.configuration.ClientConfig;

public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.showClaims.isPressed()) {
            ClientConfig.claimVisuals.enabled = !ClientConfig.claimVisuals.enabled;
            ConfigManager.sync(Pl3x.modId, Config.Type.INSTANCE);
        }
    }
}
