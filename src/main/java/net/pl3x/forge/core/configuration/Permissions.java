package net.pl3x.forge.core.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Permissions {
    private List<Track> tracks = new ArrayList<Track>() {{
        add(new Track("default", 0));
        add(new Track("mod", 1));
        add(new Track("admin", 2));
    }};

    private List<Group> groups = new ArrayList<Group>() {{
        add(new Group("default", "", ""));
        add(new Group("mod", "[M]", ""));
        add(new Group("admin", "[A]", ""));
    }};

    private List<Player> players = new ArrayList<Player>() {{
        add(new Player(UUID.fromString("0b54d4f1-8ce9-46b3-a723-4ffdeeae3d7d"),
                "BillyGalbreath", "admin", "", ""));
        add(new Player(UUID.fromString("5db23b22-9dd8-4672-94ab-4963a48c2e71"),
                "Chrysti", "mod", "", ""));
    }};

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public class Track {
        private final String group;
        private final int order;

        public Track(String group, int order) {
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

    public class Group {
        private final String name;
        private final String prefix;
        private final String suffix;
        private final Map<String, Boolean> permissions =
                new TreeMap<String, Boolean>(String.CASE_INSENSITIVE_ORDER) {{
                    put("test.node", true);
                    put("negated.node", false);
                }};

        public Group(String name, String prefix, String suffix) {
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

    public class Player {
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

        public Player(UUID uuid, String name, String group, String prefix, String suffix) {
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
}
