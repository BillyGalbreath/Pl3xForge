package net.pl3x.forge.icons;

public class Icon {
    private final String hex;
    private final String code;
    private final char character;

    public Icon(String hex, String code) {
        this.hex = hex.toLowerCase();
        this.code = code.toLowerCase();
        this.character = (char) Integer.parseInt(hex, 16);
    }

    public String getHex() {
        return hex;
    }

    public String getCode() {
        return code.toLowerCase();
    }

    public Character getCharacter() {
        return character;
    }

    public static Icon getIcon(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        Icon icon;
        if (string.length() == 1) {
            icon = IconManager.INSTANCE.getIconByUnicode(string.charAt(0));
            if (icon != null) {
                return icon;
            }
        }
        icon = IconManager.INSTANCE.getIconByTag(string);
        if (icon != null) {
            return icon;
        }
        return IconManager.INSTANCE.getIconByHex(string);
    }
}
