package net.pl3x.forge.client.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;

import java.text.DecimalFormat;

import static net.minecraft.client.gui.Gui.drawModalRectWithCustomSizedTexture;

public class ClientEventHandler {
    public static double balance = 0;

    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fontRenderer = mc.fontRenderer;
    private ResourceLocation texture = new ResourceLocation(Pl3xForgeClient.modId + ":textures/items/money_coin.png");

    private DecimalFormat df = new DecimalFormat("#,###");

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return; // only render when exp HUD is showing too
        }

        String balanceStr = df.format(balance);
        mc.getTextureManager().bindTexture(texture);
        ScaledResolution scale = new ScaledResolution(mc);

        //int x = scale.getScaledWidth() / 2 + 95;
        //int y = scale.getScaledHeight() - 34;

        int x = scale.getScaledWidth() / 2 - ((fontRenderer.getStringWidth(balanceStr) + 16) / 2);
        int y = 10;

        drawModalRectWithCustomSizedTexture(x, y - 5, 0, 0, 16, 16, 16, 16);
        fontRenderer.drawStringWithShadow(balanceStr, x + 18, y, 16777215);
    }
}
