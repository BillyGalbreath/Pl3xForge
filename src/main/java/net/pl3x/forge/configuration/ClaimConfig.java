package net.pl3x.forge.configuration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ClaimConfig extends ConfigLoader {
    final String FILE_NAME;
    private final File configDir;
    private final long id;
    protected ClaimConfig.Data data;

    public ClaimConfig(File dir, long id) {
        this.id = id;
        this.configDir = dir;
        this.FILE_NAME = id + ".json";

        reload();
    }

    public long getId() {
        return id;
    }

    public void reload() {
        try {
            data = loadConfig(new ClaimConfig.Data(), ClaimConfig.Data.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            saveConfig(data, ClaimConfig.Data.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Data {
        private long id;
        private boolean isAdminClaim;
        private UUID owner;
        private long parent;
        private int dimension;
        private int minX;
        private int minY;
        private int minZ;
        private int maxX;
        private int maxY;
        private int maxZ;
        // TODO: trusts
        // TODO: managers
        // TODO: flags

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isAdminClaim() {
            return isAdminClaim;
        }

        public void setAdminClaim(boolean adminClaim) {
            isAdminClaim = adminClaim;
        }

        public UUID getOwner() {
            return owner;
        }

        public void setOwner(UUID owner) {
            this.owner = owner;
        }

        public long getParent() {
            return parent;
        }

        public void setParent(long parent) {
            this.parent = parent;
        }

        public int getDimension() {
            return dimension;
        }

        public void setDimension(int dimension) {
            this.dimension = dimension;
        }

        public int getMinX() {
            return minX;
        }

        public void setMinX(int minX) {
            this.minX = minX;
        }

        public int getMinY() {
            return minY;
        }

        public void setMinY(int minY) {
            this.minY = minY;
        }

        public int getMinZ() {
            return minZ;
        }

        public void setMinZ(int minZ) {
            this.minZ = minZ;
        }

        public int getMaxX() {
            return maxX;
        }

        public void setMaxX(int maxX) {
            this.maxX = maxX;
        }

        public int getMaxY() {
            return maxY;
        }

        public void setMaxY(int maxY) {
            this.maxY = maxY;
        }

        public int getMaxZ() {
            return maxZ;
        }

        public void setMaxZ(int maxZ) {
            this.maxZ = maxZ;
        }
    }
}
