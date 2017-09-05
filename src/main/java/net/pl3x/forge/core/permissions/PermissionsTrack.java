package net.pl3x.forge.core.permissions;

import net.pl3x.forge.core.configuration.PermissionsConfig;

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
            PermissionsTrack track = PermissionsConfig.getHolder().getTrack(i);
            if (track != null) {
                return PermissionsConfig.getHolder().getGroup(track.group);
            }
        }
        return null;
    }
}
