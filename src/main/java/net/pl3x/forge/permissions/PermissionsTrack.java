package net.pl3x.forge.permissions;

import net.pl3x.forge.configuration.PermsConfig;

public class PermissionsTrack {
    private final String group;
    private final int order;

    public PermissionsTrack(String group, int order) {
        this.group = group;
        this.order = order;
    }

    public String getGroup() {
        return group;
    }

    public int getOrder() {
        return order;
    }

    public PermissionsGroup getParent() {
        for (int i = order - 1; i >= 0; i--) {
            PermissionsTrack track = PermsConfig.getHolder().getTrack(i);
            if (track != null) {
                return PermsConfig.getHolder().getGroup(track.group);
            }
        }
        return null;
    }
}
