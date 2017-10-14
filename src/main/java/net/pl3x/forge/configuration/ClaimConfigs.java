package net.pl3x.forge.configuration;

import net.pl3x.forge.Logger;
import net.pl3x.forge.claims.Claim;
import net.pl3x.forge.claims.ClaimManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ClaimConfigs {
    public static final String CLAIM_DIRECTORY = "claims";
    private static final Map<Long, ClaimConfig> configs = new HashMap<>();
    private static File configDir;

    public static Map<Long, ClaimConfig> getConfigs() {
        synchronized (configs) {
            return configs;
        }
    }

    public static ClaimConfig getConfig(long id) {
        synchronized (configs) {
            return configs.computeIfAbsent(id, k -> new ClaimConfig(configDir, id));
        }
    }

    public static void unloadConfig(long id) {
        synchronized (configs) {
            configs.remove(id);
        }
    }

    public static void unloadConfigs() {
        synchronized (configs) {
            configs.clear();
        }
    }

    public static void init(File dir) {
        configDir = dir;
        reload();
    }

    public static void reload() {
        configs.clear();

        File[] citiesList = new File(configDir, CLAIM_DIRECTORY)
                .listFiles((dir, name) -> name.endsWith(".json"));
        if (citiesList == null) {
            citiesList = new File[0];
        }
        for (File file : citiesList) {
            getConfig(Long.parseLong(file.getName().split(".json")[0]));
        }

        // iterate all configs and create claims starting from lowest id number
        TreeMap<Long, ClaimConfig> treeMap = new TreeMap<>(getConfigs());
        long count = 0; // lets count how many claims we actually load up
        long total = treeMap.size();

        Logger.info("Loading all claims (total: " + total + ")");
        for (Map.Entry<Long, ClaimConfig> entry : treeMap.entrySet()) {
            long id = entry.getKey();
            ClaimConfig config = entry.getValue();

            // sanity check the id
            if (config.getId() < 0 || config.getId() != id) {
                Logger.error("   Claim id mismatch! Skipping.. (file " + id + ".json)");
                continue;
            }

            // get if an admin claim or not
            boolean isAdminClaim = config.data.isAdminClaim();

            // get the parent (if any)
            long parentId = config.data.getParent();
            Claim parent = null;
            if (parentId > -1) {
                // there is a parent, lets make sure its already been stored in manager
                parent = ClaimManager.INSTANCE.getClaim(parentId);
                if (parent == null) {
                    Logger.error("   Could not get parent! Skipping.. (file " + id + ".json)");
                    continue;
                }
            }

            // get the owner
            UUID owner = config.data.getOwner();
            if (owner == null && !isAdminClaim) {
                if (parent != null) {
                    owner = parent.getOwner();
                    config.data.setOwner(owner);
                    config.save();
                } else {
                    Logger.error("   Could not get owner! Skipping.. (file " + id + ".json)");
                    continue;
                }
            }

            // get the coordinates
            int dimension, minX, minY, minZ, maxX, maxY, maxZ;
            try {
                dimension = config.data.getDimension();
                minX = config.data.getMinX();
                minY = config.data.getMinY();
                minZ = config.data.getMinZ();
                maxX = config.data.getMaxX();
                maxY = config.data.getMaxY();
                maxZ = config.data.getMaxZ();
            } catch (Exception e) {
                Logger.error("   Could not get coordinates! Skipping.. (file " + id + ".json)");
                // most likely the world is not loaded yet
                continue;
            }

            // everything looks good, make the claim
            Claim claim = new Claim(id, owner, parent, isAdminClaim, dimension, minX, minY, minZ, maxX, maxY, maxZ);

            // TODO trusts
            //claim.getTrusts().putAll(config.data.getTrusts());
            //claim.getManagers().addAll(config.data.getManagers());

            // TODO flags
            //config.getFlags().forEach(claim::setFlag);

            Logger.debug("  id: " + id);
            Logger.debug("  owner: " + owner);
            Logger.debug("  parent: " + (parent == null ? "null" : parent.getId()));
            Logger.debug("  dimension: " + dimension);
            Logger.debug("  coords: " + minX + "," + minY + "," + minZ + " - " + maxX + "," + maxY + "," + maxZ);
            Logger.debug("  isAdminClaim: " + isAdminClaim);
            //Logger.debug("  trusts: " + claim.getTrusts());
            //Logger.debug("  flags: " + claim.getFlags());

            // finally store the claim
            if (parent != null) {
                // add to parent as child (if there is a parent)
                parent.addChild(claim);
            } else {
                // add to chunk map
                ClaimManager.INSTANCE.addTopLevelClaim(claim);
            }

            ClaimManager.INSTANCE.NEXT_ID = id + 1; // make sure next id is actually the next id...
            Logger.debug("   Claim #" + id + " loaded successfully.");
            count++;
        }

        Logger.info("Finished loading all claims! (total: " + count + " / " + total + ")");
    }
}
