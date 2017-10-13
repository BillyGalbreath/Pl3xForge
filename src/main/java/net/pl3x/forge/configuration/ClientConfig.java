package net.pl3x.forge.configuration;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.gui.HUDBalance;

@Config(modid = Pl3x.modId)
@Config.LangKey("pl3x.config.title")
public class ClientConfig {
    @Name("Balance HUD")
    @Comment("Control the Balance HUD element")
    public static BalanceHUDConfig balanceHud = new BalanceHUDConfig();

    @Name("Claim Visuals")
    @Comment("Control the visuals for claims")
    public static ClaimVisuals claimVisuals = new ClaimVisuals();

    public static class BalanceHUDConfig {
        @Name("Enabled")
        @Comment("Toggle the HUD on/off")
        public boolean enabled = true;

        @Name("Position")
        @Comment("Anchor position of the HUD")
        public HUDBalance.HUDPosition position = HUDBalance.HUDPosition.TOP_CENTER;

        @Name("Relative X")
        @Comment("X position relative to anchor")
        public int relativeX = 0;

        @Name("Relative Y")
        @Comment("Y position relative to anchor")
        public int relativeY = 0;
    }

    public static class ClaimVisuals {
        @Name("1) Enable Claim Visuals")
        @Comment("Turn on/off claim visuals")
        public boolean enabled = true;

        @Name("2) Outline Color")
        @Comment("Choose a hex color value")
        public String outlineColor = "#00FF0088";

        @Name("3) Grid Color")
        @Comment("Choose a hex color value")
        public String gridColor = "#FFFF0088";

        @Name("4) Wire Thickness")
        @Comment("Choose a value for the wire frame thickness between 1 and 7.")
        @RangeInt(min = 1, max = 7)
        public int thickness = 1;

        @Name("5) Render Behind Blocks")
        @Comment("True to see translucent wire frame behind blocks")
        public boolean renderBehind = true;
    }

    @Mod.EventBusSubscriber(modid = Pl3x.modId)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Pl3x.modId)) {
                ConfigManager.sync(Pl3x.modId, Config.Type.INSTANCE);
            }
        }
    }
}
