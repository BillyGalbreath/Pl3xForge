package net.pl3x.forge.core.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionsHolder {
    private List<PermissionsTrack> tracks = new ArrayList<PermissionsTrack>() {{
        add(new PermissionsTrack("default", 0));
        add(new PermissionsTrack("mod", 1));
        add(new PermissionsTrack("admin", 2));
    }};

    private List<PermissionsGroup> groups = new ArrayList<PermissionsGroup>() {{
        add(new PermissionsGroup("default", "", ""));
        add(new PermissionsGroup("mod", "[M]", ""));
        add(new PermissionsGroup("admin", "[A]", ""));
    }};

    private List<PermissionsPlayer> players = new ArrayList<PermissionsPlayer>() {{
        add(new PermissionsPlayer(UUID.fromString("0b54d4f1-8ce9-46b3-a723-4ffdeeae3d7d"),
                "BillyGalbreath", "admin", "", ""));
        add(new PermissionsPlayer(UUID.fromString("5db23b22-9dd8-4672-94ab-4963a48c2e71"),
                "Chrysti", "mod", "", ""));
    }};

    public List<PermissionsTrack> getTracks() {
        return tracks;
    }

    public List<PermissionsGroup> getGroups() {
        return groups;
    }

    public List<PermissionsPlayer> getPlayers() {
        return players;
    }

    public PermissionsTrack getTrack(String group) {
        return tracks.stream()
                .filter(track -> track.getGroup().equalsIgnoreCase(group))
                .findAny().orElse(null);
    }

    public PermissionsTrack getTrack(int order) {
        return tracks.stream()
                .filter(track -> track.getOrder() == order)
                .findAny().orElse(null);
    }

    public PermissionsGroup getGroup(String name) {
        return groups.stream()
                .filter(group -> group.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public PermissionsPlayer getPlayer(UUID uuid) {
        return players.stream()
                .filter(player -> player.getUuid().equals(uuid))
                .findAny().orElse(null);
    }
}
