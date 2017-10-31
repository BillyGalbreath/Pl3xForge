package net.pl3x.forge.sound;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.Pl3x;

import java.util.List;

@Mod.EventBusSubscriber
public class ModSounds {
    private static List<SoundEvent> sounds = Lists.newArrayList();
    public static final SoundEvent PENGUIN_AMBIENT = createSound("penguin.ambient");
    public static final SoundEvent PENGUIN_BABY_AMBIENT = createSound("penguin.baby.ambient");
    public static final SoundEvent PENGUIN_DEATH = createSound("penguin.death");
    public static final SoundEvent PENGUIN_HURT = createSound("penguin.hurt");

    private static SoundEvent createSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Pl3x.modId, name);
        SoundEvent sound = new SoundEvent(resourceLocation);
        sound.setRegistryName(resourceLocation);
        sounds.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sound : sounds) {
            event.getRegistry().register(sound);
        }
    }
}
