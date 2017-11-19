package net.pl3x.forge.claims;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.configuration.ClientConfig;

public class Selection {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRenderer = mc.fontRenderer;

    public static Selection CURRENT_SELECTION = new Selection();

    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    private final int dimension;

    private BlockPos rightClickPos;
    private BlockPos leftClickPos;

    private Visual visual;

    public Selection() {
        this(null, null, -1337);
    }

    public Selection(BlockPos rightClickPos, BlockPos leftClickPos, int dimension) {
        this.rightClickPos = rightClickPos;
        this.leftClickPos = leftClickPos;
        this.dimension = dimension;
        recalcMinMax();
    }

    public void setRightClickPos(BlockPos pos) {
        rightClickPos = pos;
        recalcMinMax();
    }

    public void setLeftClickPos(BlockPos pos) {
        leftClickPos = pos;
        recalcMinMax();
    }

    public BlockPos getMinPos() {
        return new BlockPos(minX, 0, minZ);
    }

    public BlockPos getMaxPos() {
        return new BlockPos(maxX, 256, maxZ);
    }

    private void recalcMinMax() {
        if (rightClickPos == null && leftClickPos == null) {
            minX = 0;
            minZ = 0;
            maxX = 0;
            maxZ = 0;
            visual = null;
            return;
        } else if (rightClickPos == null) {
            minX = leftClickPos.getX();
            minZ = leftClickPos.getZ();
            maxX = leftClickPos.getX();
            maxZ = leftClickPos.getZ();
        } else if (leftClickPos == null) {
            minX = rightClickPos.getX();
            minZ = rightClickPos.getZ();
            maxX = rightClickPos.getX();
            maxZ = rightClickPos.getZ();
        } else {
            minX = Math.min(rightClickPos.getX(), leftClickPos.getX());
            minZ = Math.min(rightClickPos.getZ(), leftClickPos.getZ());
            maxX = Math.max(rightClickPos.getX(), leftClickPos.getX());
            maxZ = Math.max(rightClickPos.getZ(), leftClickPos.getZ());
        }

        visual = new Visual(minX, minZ, maxX, maxZ);
    }

    public int getDimension() {
        return dimension;
    }

    public Visual getVisual() {
        return visual;
    }

    public void drawDetails() {
        if (!ClientConfig.claimVisuals.enabled) {
            return;
        }

        String minMsg = leftClickPos == null ? "n/a" : minX + "," + minZ;
        String maxMsg = rightClickPos == null ? "n/a" : maxX + "," + maxZ;

        int minWidth = fontRenderer.getStringWidth(minMsg);
        int maxWidth = fontRenderer.getStringWidth(maxMsg);
        int greatestWidth = Math.max(minWidth, maxWidth);

        int x, y;
        ScaledResolution scale = new ScaledResolution(mc);
        switch (ClientConfig.claimVisuals.position) {
            case BOTTOM_LEFT:
                x = 10;
                y = scale.getScaledHeight() - 32;
                break;
            case BOTTOM_CENTER:
                x = scale.getScaledWidth() / 2 - (greatestWidth / 2);
                y = scale.getScaledHeight() - 32;
                break;
            case BOTTOM_RIGHT:
                x = scale.getScaledWidth() - greatestWidth - 10;
                y = scale.getScaledHeight() - 32;
                break;
            case CENTER_LEFT:
                x = 10;
                y = scale.getScaledHeight() / 2 - 6;
                break;
            case CENTER_CENTER:
                x = scale.getScaledWidth() / 2 - (greatestWidth / 2);
                y = scale.getScaledHeight() / 2 - 6;
                break;
            case CENTER_RIGHT:
                x = scale.getScaledWidth() - greatestWidth - 10;
                y = scale.getScaledHeight() / 2 - 6;
                break;
            case TOP_LEFT:
                x = 10;
                y = 10;
                break;
            case TOP_RIGHT:
                x = scale.getScaledWidth() - greatestWidth - 10;
                y = 10;
                break;
            case TOP_CENTER:
            default:
                x = scale.getScaledWidth() / 2 - (greatestWidth / 2);
                y = 10;
        }

        fontRenderer.drawStringWithShadow(minMsg, x + (greatestWidth - minWidth), y, 0xffffff);
        fontRenderer.drawStringWithShadow(maxMsg, x + (greatestWidth - maxWidth), y + 12, 0xffffff);
    }
}
