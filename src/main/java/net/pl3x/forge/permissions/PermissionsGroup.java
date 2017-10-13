package net.pl3x.forge.permissions;

import java.util.Map;
import java.util.TreeMap;

public class PermissionsGroup {
    private final String name;
    private final String prefix;
    private final String suffix;
    private final Map<String, Boolean> permissions =
            new TreeMap<String, Boolean>(String.CASE_INSENSITIVE_ORDER) {{
                put("test.node", true);
                put("negated.node", false);
            }};

    public PermissionsGroup(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getName() {
        return name;
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
