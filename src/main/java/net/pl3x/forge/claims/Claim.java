package net.pl3x.forge.claims;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.claims.visual.ClaimVisual;
import net.pl3x.forge.util.PlayerUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Claim {
    private final long id;
    private final Claim parent;
    private final Collection<Claim> children = new HashSet<>();
    private UUID owner;
    private boolean isAdminClaim;
    private final int dimension;
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;
    private ClaimVisual visual;

    public Claim(long id, UUID owner, Claim parent, boolean isAdminClaim,
                 int dimension, BlockPos pos1, BlockPos pos2) {
        this(id, owner, parent, isAdminClaim, dimension,
                pos1.getX(), pos1.getY(), pos1.getZ(),
                pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public Claim(long id, UUID owner, Claim parent, boolean isAdminClaim,
                 int dimension, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.id = id;
        this.owner = owner;
        this.parent = parent;
        this.isAdminClaim = isAdminClaim;
        this.dimension = dimension;
        resize(x1, y1, z1, x2, y2, z2);
    }

    public long getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getOwnerName() {
        if (isAdminClaim || owner == null) {
            return "admin";
        }

        GameProfile target = PlayerUtil.getCachedProfile(owner);
        return target == null ? "unknown" : (target.getName() == null ? "unknown" : target.getName());
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public boolean isOwner(EntityPlayer player) {
        if (owner == null && getParent() != null) {
            return getParent().isOwner(player);
        }
        return isAdminClaim /*&& player.hasPermission("command.adminclaims")*/ || isOwner(player.getUniqueID());
    }

    public boolean isOwner(UUID uuid) {
        return owner != null && owner.equals(uuid);
    }

    public Claim getParent() {
        return parent;
    }

    public boolean isAdminClaim() {
        return isAdminClaim;
    }

    public void setAdminClaim(boolean isAdminClaim) {
        this.isAdminClaim = isAdminClaim;
    }

    public int getDimension() {
        return dimension;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public void resize(int x1, int y1, int z1, int x2, int y2, int z2) {
        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);
        minZ = Math.min(z1, z2);
        maxX = Math.max(x1, x2);
        maxY = Math.max(y1, y2);
        maxZ = Math.max(z1, z2);

        visual = new ClaimVisual(this);
    }

    public ClaimVisual getVisual() {
        return visual;
    }

    public int getArea() {
        return getWidthX() * getWidthZ();
    }

    public int getWidthX() {
        return maxX - minX + 1;
    }

    public int getWidthZ() {
        return maxZ - minZ + 1;
    }

    public BlockPos getMinPos() {
        return new BlockPos(minX, minY, minZ);
    }

    public BlockPos getMaxPos() {
        return new BlockPos(maxX, maxY, maxZ);
    }

    public boolean overlaps(Claim claim) {
        for (int x = claim.minX; x <= claim.maxX; x++) {
            for (int y = claim.minY; y <= claim.maxY; y++) {
                for (int z = claim.minZ; z <= claim.maxZ; z++) {
                    if (contains(x, y, z)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean contains(BlockPos pos) {
        return contains(pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean isCorner(BlockPos pos) {
        return isCorner(pos, false);
    }

    public boolean isCorner(BlockPos pos, boolean checkY) {
        return (pos.getX() == minX || pos.getX() == maxX) &&
                (pos.getZ() == minZ || pos.getZ() == maxZ) &&
                (!checkY || pos.getY() == minY || pos.getY() == maxY);
    }

    public Collection<Long> getChunkHashes() {
        Collection<Long> hashes = new HashSet<>();
        for (int x = minX >> 4; x <= maxX >> 4; x++) {
            for (int z = minZ >> 4; z <= maxZ >> 4; z++) {
                hashes.add(ClaimManager.INSTANCE.getChunkHash(x, z));
            }
        }
        return hashes;
    }

    public Collection<Claim> getChildren() {
        return children;
    }

    public void addChild(Claim claim) {
        children.add(claim);
    }

    public void removeChild(Claim claim) {
        children.remove(claim);
    }

    @Override
    public String toString() {
        return "Coordinates[dimension:" + dimension +
                ",minX:" + minX +
                ",minY:" + minY +
                ",minZ:" + minZ +
                ",maxX:" + maxX +
                ",maxY:" + maxY +
                ",maxZ:" + maxZ + "]";
    }
}
