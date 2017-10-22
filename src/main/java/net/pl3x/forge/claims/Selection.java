package net.pl3x.forge.claims;

import net.minecraft.util.math.BlockPos;

public class Selection {
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

    public void recalcMinMax() {
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
}
