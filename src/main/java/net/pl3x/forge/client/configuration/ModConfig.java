package net.pl3x.forge.client.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.gui.HUDPosition;

@Config(modid = Pl3xForgeClient.modId)
@Config.LangKey("pl3x.config.title")
public class ModConfig {

    @Config.Name("Balance HUD")
    @Config.Comment("Control the Balance HUD element")
    public static BalanceHUDConfig balanceHud = new BalanceHUDConfig();

    public static class BalanceHUDConfig {
        @Config.Name("Enabled")
        @Config.Comment("Toggle the HUD on/off")
        public boolean enabled = true;

        @Config.Name("Position")
        @Config.Comment("Anchor position of the HUD")
        public HUDPosition position = HUDPosition.TOP_CENTER;

        @Config.Name("Relative X")
        @Config.Comment("X position relative to anchor")
        public int relativeX = 0;

        @Config.Name("Relative Y")
        @Config.Comment("Y position relative to anchor")
        public int relativeY = 0;
    }

    @Mod.EventBusSubscriber(modid = Pl3xForgeClient.modId)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Pl3xForgeClient.modId)) {
                ConfigManager.sync(Pl3xForgeClient.modId, Config.Type.INSTANCE);
            }
        }
    }
}
