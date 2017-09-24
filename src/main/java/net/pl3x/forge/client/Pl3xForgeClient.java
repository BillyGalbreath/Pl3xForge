package net.pl3x.forge.client;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.pl3x.forge.client.block.ModBlocks;
import net.pl3x.forge.client.enchantment.ModEnchantments;
import net.pl3x.forge.client.item.ModItems;
import net.pl3x.forge.client.proxy.ServerProxy;

import java.lang.reflect.Field;

@Mod(modid = Pl3xForgeClient.modId, name = Pl3xForgeClient.name, version = Pl3xForgeClient.version)
public class Pl3xForgeClient {
    public static final String modId = "pl3x";
    public static final String name = "Pl3xForgeClient";
    public static final String version = "@VERSION@";

    @Mod.Instance(modId)
    public static Pl3xForgeClient instance;

    @SidedProxy(serverSide = "net.pl3x.forge.client.proxy.ServerProxy", clientSide = "net.pl3x.forge.client.proxy.ClientProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
            ModEnchantments.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            // set vanilla diamond recipe group
            try {
                ShapedRecipes diamondRecipe = (ShapedRecipes) event.getRegistry().getValue(
                        new ResourceLocation("minecraft", "diamond"));
                Field group = diamondRecipe.getClass().getDeclaredField("field_194137_e");
                group.setAccessible(true);
                group.set(diamondRecipe, "minecraft:diamond");
            } catch (Exception ignore) {
            }
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }
    }
}
