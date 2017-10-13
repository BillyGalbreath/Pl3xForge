package net.pl3x.forge.icons;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.configuration.IconConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IconManager {
    public final static IconManager INSTANCE = new IconManager();
    private final static Pattern TAG_REGEX_PATTERN = Pattern.compile("\\{(.*?)}");

    private final Collection<Icon> icons = new HashSet<>();

    public Collection<Icon> getAllIcons() {
        return icons;
    }

    public Icon getIconByTag(String tag) {
        return icons.stream()
                .filter(icon -> icon.getCode().equalsIgnoreCase(tag))
                .findFirst().orElse(null);
    }

    public Icon getIconByHex(String hex) {
        return icons.stream()
                .filter(icon -> icon.getHex().equalsIgnoreCase(hex))
                .findFirst().orElse(null);
    }

    public Icon getIconByUnicode(Character unicode) {
        return getIconByHex(String.format("%04x", (int) unicode));
    }

    public String translateIconsTags(String string) {
        if (string == null) {
            return null;
        }

        Matcher match = TAG_REGEX_PATTERN.matcher(string);
        while (match.find()) {
            String code = match.group(1);
            if (code == null) {
                continue;
            }
            Icon icon = getIconByTag(code);
            if (icon == null) {
                continue;
            }
            string = string.replace("{" + code + "}",
                    icon.getCharacter().toString());
        }
        return string;
    }

    public String untranslateIconUnicode(String string) {
        if (string == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            Icon icon = getIconByUnicode(c);
            if (icon == null) {
                sb.append(c);
                continue;
            }
            sb.append("{").append(icon.getCode()).append("}");
        }
        return sb.toString();
    }

    public ITextComponent tooltipComponents(ITextComponent message) {
        List<ITextComponent> iconSiblings = new ArrayList<>();
        for (ITextComponent sibling : message.getSiblings()) {
            if (sibling.getStyle().getHoverEvent() != null) {
                iconSiblings.add(sibling);
                continue;
            }
            String text = sibling.getUnformattedComponentText();
            StringBuilder sb = new StringBuilder();
            for (char c : text.toCharArray()) {
                Icon icon = getIconByUnicode(c);
                if (icon == null) {
                    // not an icon, track character and move on
                    sb.append(c);
                    continue;
                }

                String lastColor = ChatColor.getLastColors(sb.toString());

                // add previous characters to new component
                if (sb.length() > 0) {
                    iconSiblings.add(new TextComponentString(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(lastColor);
                }

                // make new component with tooltip for the icon
                ITextComponent iconComponent = new TextComponentString(lastColor + Character.toString(c));
                iconComponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new TextComponentString("{" + icon.getCode() + "}")));
                iconSiblings.add(iconComponent);
            }
            // lets make sure we dont leave any characters behind
            if (sb.length() > 0) {
                iconSiblings.add(new TextComponentString(sb.toString()));
            }
        }
        message = new TextComponentString("");
        iconSiblings.forEach(message::appendSibling);
        return message;
    }

    public void reloadIcons() {
        icons.clear();

        IconConfig.Data iconData = IconConfig.getData();
        for (String hex : iconData.icons.keySet()) {
            if (hex == null || hex.length() != 4) {
                continue;
            }
            String code = iconData.icons.get(hex);
            if (code == null || code.isEmpty()) {
                continue;
            }
            icons.add(new Icon(hex, code));
        }
    }
}
