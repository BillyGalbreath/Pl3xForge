package net.pl3x.forge.client.listener;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    public static KeyBinding showClaims;

    public static void init() {
        showClaims = new KeyBinding("key.showClaims", Keyboard.KEY_V, "key.categories.pl3x");
        ClientRegistry.registerKeyBinding(showClaims);
    }
}
