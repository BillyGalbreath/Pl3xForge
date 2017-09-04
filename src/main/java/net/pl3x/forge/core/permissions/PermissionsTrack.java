package net.pl3x.forge.core.permissions;

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
}
