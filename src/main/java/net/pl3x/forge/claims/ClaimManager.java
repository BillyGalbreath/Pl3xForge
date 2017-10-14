package net.pl3x.forge.claims;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ClaimManager {
    public static final ClaimManager INSTANCE = new ClaimManager();

    private final Map<Long, Collection<Claim>> chunks = new HashMap<>();
    private final Map<Long, Claim> topLevelClaims = new HashMap<>();
    public long NEXT_ID = 0;

    public long getNextID() {
        return NEXT_ID++;
    }

    public Collection<Claim> getTopLevelClaims() {
        return topLevelClaims.values();
    }

    public Claim getClaim(long id) {
        return topLevelClaims.get(id);
    }

    public Claim getClaim(BlockPos pos, int dimension) {
        return getTopLevelClaims().stream()
                .filter(topLevelClaim -> topLevelClaim.getDimension() == dimension)
                .filter(topLevelClaim -> topLevelClaim.contains(pos))
                .findFirst().map(
                        topLevelClaim -> topLevelClaim.getChildren().stream()
                                .filter(child -> child.contains(pos))
                                .findFirst()
                                .orElse(topLevelClaim)
                ).orElse(null);
    }

    public Collection<Claim> getNearbyClaims(BlockPos pos, int dimension) {
        Vec3i near = new Vec3i(150, 0, 150);
        WorldServer world = DimensionManager.getWorld(dimension);
        Chunk minChunk = world.getChunkFromBlockCoords(pos.subtract(near));
        Chunk maxChunk = world.getChunkFromBlockCoords(pos.add(near));

        Collection<Claim> claims = new HashSet<>();
        for (int x = minChunk.x; x <= maxChunk.x; x++) {
            for (int z = minChunk.z; z <= maxChunk.z; z++) {
                Collection<Claim> claimsInChunk = chunks.get(getChunkHash(x, z));
                if (claimsInChunk != null) {
                    claims.addAll(claimsInChunk);
                }
            }
        }
        return claims;
    }

    public void addTopLevelClaim(Claim claim) {
        topLevelClaims.put(claim.getId(), claim);
        calculateChunkHashes(claim);
    }

    public void createNewClaim(Claim claim) {
        //
    }

    public void resizeClaim(EntityPlayer player, Claim claim, BlockPos pos1, BlockPos pos2) {
        //
    }

    public boolean deleteClaim(Claim claim) {
        return deleteClaim(claim, false);
    }

    public boolean deleteClaim(Claim claim, boolean deleteChildren) {
        //
        return true;
    }

    public void loadClaims() {
        //
    }

    public void unloadClaims() {
        //
    }

    public void removeChunkHashes(Claim claim) {
        chunks.forEach((k, v) -> v.removeIf(c -> c.getId() == claim.getId())); // remove claim's chunk hashes
        chunks.entrySet().removeIf(e -> e.getValue().isEmpty()); // remove empty chunk hashes from memory
    }

    public void calculateChunkHashes(Claim claim) {
        removeChunkHashes(claim);
        claim.getChunkHashes().forEach(hash ->
                chunks.computeIfAbsent(hash, k -> new HashSet<>()).add(claim));
    }

    public long getChunkHash(long x, long z) {
        return (z ^ (x << 32));
    }
}
