package net.pl3x.forge.configuration;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.Pl3xSettings;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.ReplyCapePacket;
import net.pl3x.forge.util.gl.HUDPosition;

@Config(modid = Pl3x.modId)
@Config.LangKey("pl3x.config.title")
public class ClientConfig {
    @Name("Balance HUD")
    @Comment("Control the Balance HUD element")
    public static BalanceHUDConfig balanceHud = new BalanceHUDConfig();

    @Name("Cape Manager")
    @Comment("Control player capes")
    public static CapeOptions capeOptions = new CapeOptions();

    @Name("Chat Options")
    @Comment("Control chat GUI")
    public static ChatOptions chatOptions = new ChatOptions();

    @Name("Claim Visuals")
    @Comment("Control the visuals for claims")
    public static ClaimVisuals claimVisuals = new ClaimVisuals();

    @Name("Mirror Options")
    @Comment("Control render settings for mirror blocks")
    public static MirrorOptions mirrorOptions = new MirrorOptions();

    @Name("TV Options")
    @Comment("Control render settings for TV blocks")
    public static TVOptions tvOptions = new TVOptions();

    public static class BalanceHUDConfig {
        @Name("Enabled")
        @Comment("Toggle the HUD on/off")
        public boolean enabled = true;

        @Name("Position")
        @Comment("Anchor position of the HUD")
        public HUDPosition position = HUDPosition.TOP_CENTER;

        @Name("Relative X")
        @Comment("X position relative to anchor")
        public int relativeX = 0;

        @Name("Relative Y")
        @Comment("Y position relative to anchor")
        public int relativeY = 0;
    }

    public static class CapeOptions {
        @Name("Cape URL")
        @Comment("URL to cape texture")
        public String capeURL = "";
    }

    public static class ChatOptions {
        @Name("Background Color")
        @Comment("Chat GUI background color")
        public Color background = new Color();
    }

    public static class ClaimVisuals {
        @Name("1) Outline Color")
        @Comment("Choose a hex color value")
        public String outlineColor = "#00FF0088";

        @Name("2) Grid Color")
        @Comment("Choose a hex color value")
        public String gridColor = "#FFFF0088";

        @Name("3) Wire Thickness")
        @Comment("Choose a value for the wire frame thickness between 1 and 7.")
        @RangeInt(min = 1, max = 7)
        public int thickness = 1;

        @Name("4) Render Behind Blocks")
        @Comment("True to see translucent wire frame behind blocks")
        public boolean renderBehind = true;

        @Name("5) Details HUD Position")
        @Comment("Anchor position of the HUD")
        public HUDPosition position = HUDPosition.BOTTOM_RIGHT;
    }

    public static class MirrorOptions {
        @Name("1) Enabled")
        @Comment("Toggle rendering mirrors on/off")
        public boolean enabled = false;

        @Name("2) Render Quality")
        @Comment("Image quality for rendered reflection (CPU intensive)")
        public int quality = 128;

        @Name("3) Activation Radius")
        @Comment("Block radius for activating mirror reflection")
        public int radius = 5;

        @Name("4) Mirror FOV (Field of View)")
        @Comment("Reflection's FOV setting")
        public int fov = 70;

        @Name("5) Render Clouds")
        @Comment("Render clouds in the reflection")
        public boolean clouds = true;
    }

    public static class TVOptions {
        @Name("1) Use TESR")
        @Comment("Use a brighter render for drawing the screens (CPU intensive)")
        public boolean useTESR = true;
    }

    public static class Color {
        @Name("1) Red")
        @Comment("Red Color Value (0-255)")
        @RangeInt(min = 0, max = 255)
        public int red = 0x00;

        @Name("2) Green")
        @Comment("Green Color Value (0-255)")
        @RangeInt(min = 0, max = 255)
        public int green = 0x33;

        @Name("3) Blue")
        @Comment("Blue Color Value (0-255)")
        @RangeInt(min = 0, max = 255)
        public int blue = 0x66;

        @Name("4) Alpha")
        @Comment("Alpha Color Value (0-255)")
        @RangeInt(min = 0, max = 255)
        public int alpha = 0x80;
    }

    @Mod.EventBusSubscriber(modid = Pl3x.modId)
    private static class EventHandler {
        @SubscribeEvent
        public static void on(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Pl3x.modId)) {
                ConfigManager.sync(Pl3x.modId, Config.Type.INSTANCE);
                PacketHandler.INSTANCE.sendToServer(new ReplyCapePacket(
                        Minecraft.getMinecraft().getSession().getUsername(), ClientConfig.capeOptions.capeURL));
                Pl3xSettings.INSTANCE.chatBGRed = ClientConfig.chatOptions.background.red;
                Pl3xSettings.INSTANCE.chatBGGreen = ClientConfig.chatOptions.background.green;
                Pl3xSettings.INSTANCE.chatBGBlue = ClientConfig.chatOptions.background.blue;
                Pl3xSettings.INSTANCE.chatBGAlpha = ClientConfig.chatOptions.background.alpha;
            }
        }
    }
}
