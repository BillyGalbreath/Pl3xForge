package net.pl3x.forge.permissions;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class PermissionsPlayer {
    private final UUID uuid;
    private final String name;
    private final String group;
    private final String prefix;
    private final String suffix;
    private final Map<String, Boolean> permissions =
            new TreeMap<String, Boolean>(String.CASE_INSENSITIVE_ORDER) {{
                put("test.node", true);
                put("negated.node", false);
            }};

    public PermissionsPlayer(UUID uuid, String name, String group, String prefix, String suffix) {
        this.uuid = uuid;
        this.name = name;
        this.group = group;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String node) {
        Boolean result = permissions.get(node);
        return result == null ? false : result;
    }
}
