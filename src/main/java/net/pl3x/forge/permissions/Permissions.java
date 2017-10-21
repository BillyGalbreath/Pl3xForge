package net.pl3x.forge.permissions;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.configuration.PermsConfig;
import net.pl3x.forge.util.Utils;

import java.util.UUID;

public class Permissions {
    public static boolean hasPermission(EntityPlayerMP player, String node) {
        return Utils.isOp(player) || hasPermission(player.getUniqueID(), node);
    }

    public static boolean hasPermission(UUID uuid, String node) {
        PermissionsPlayer player = PermsConfig.INSTANCE.data.getPlayer(uuid);
        if (player != null && player.getPermissions().containsKey(node)) {
            return player.hasPermission(node);
        }
        PermissionsGroup group = PermsConfig.INSTANCE.data
                .getGroup(player != null ? player.getGroup() : "default");
        if (group == null) {
            return player != null && player.hasPermission(node);
        }
        return hasPermission(group, node);
    }

    public static boolean hasPermission(PermissionsGroup group, String node) {
        PermissionsTrack track = PermsConfig.INSTANCE.data.getTrack(group.getName());
        if (track == null) {
            return group.hasPermission(node);
        }
        if (group.getPermissions().containsKey(node)) {
            return group.hasPermission(node);
        }
        PermissionsGroup parent = track.getParent();
        return parent != null && hasPermission(parent, node);
    }

    public static String getPrefix(EntityPlayerMP player) {
        return getPrefix(player.getUniqueID());
    }

    public static String getPrefix(UUID uuid) {
        PermissionsPlayer player = PermsConfig.INSTANCE.data.getPlayer(uuid);
        if (player != null && player.getPrefix() != null && !player.getPrefix().isEmpty()) {
            return player.getPrefix();
        }
        PermissionsGroup group = PermsConfig.INSTANCE.data
                .getGroup(player != null ? player.getGroup() : "default");
        return group != null ? group.getPrefix() : "";
    }

    public static String getSuffix(EntityPlayerMP player) {
        return getSuffix(player.getUniqueID());
    }

    public static String getSuffix(UUID uuid) {
        PermissionsPlayer player = PermsConfig.INSTANCE.data.getPlayer(uuid);
        if (player != null && player.getSuffix() != null && !player.getSuffix().isEmpty()) {
            return player.getSuffix();
        }
        PermissionsGroup group = PermsConfig.INSTANCE.data
                .getGroup(player != null ? player.getGroup() : "default");
        return group != null ? group.getSuffix() : "";
    }
}
