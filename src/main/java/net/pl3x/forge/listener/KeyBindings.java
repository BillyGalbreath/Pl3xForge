package net.pl3x.forge.listener;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    private final static String CATEGORY = "key.categories.pl3x";

    public static KeyBinding showClaims;
    public static KeyBinding vehicleAccelerate;
    public static KeyBinding vehicleDecelerate;
    public static KeyBinding vehicleTurnLeft;
    public static KeyBinding vehicleTurnRight;
    public static KeyBinding vehicleAction;

    public static void init() {
        ClientRegistry.registerKeyBinding(showClaims = new KeyBinding("key.showClaims", Keyboard.KEY_V, CATEGORY));
        ClientRegistry.registerKeyBinding(vehicleAccelerate = new KeyBinding("key.vehicleAccelerate", Keyboard.KEY_W, CATEGORY));
        ClientRegistry.registerKeyBinding(vehicleDecelerate = new KeyBinding("key.vehicleDecelerate", Keyboard.KEY_S, CATEGORY));
        ClientRegistry.registerKeyBinding(vehicleTurnLeft = new KeyBinding("key.vehicleTurnLeft", Keyboard.KEY_A, CATEGORY));
        ClientRegistry.registerKeyBinding(vehicleTurnRight = new KeyBinding("key.vehicleTurnRight", Keyboard.KEY_D, CATEGORY));
        ClientRegistry.registerKeyBinding(vehicleAction = new KeyBinding("key.vehicleAction", Keyboard.KEY_SPACE, CATEGORY));
    }
}
