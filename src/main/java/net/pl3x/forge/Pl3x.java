package net.pl3x.forge;

import club.minnced.discord.rpc.DiscordRPC;
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
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.enchantment.ModEnchantments;
import net.pl3x.forge.item.ModItems;
import net.pl3x.forge.proxy.ServerProxy;

import java.io.File;

@Mod(modid = Pl3x.modId, name = Pl3x.name, version = Pl3x.version,
        dependencies = "required-after:stairs;required-after:rubies")
public class Pl3x {
    public static final String modId = "pl3x";
    public static final String name = "Pl3x";
    public static final String version = "@DEV_BUILD@";

    @Mod.Instance(modId)
    public static Pl3x instance;

    @SidedProxy(serverSide = "net.pl3x.forge.proxy.ServerProxy", clientSide = "net.pl3x.forge.proxy.ClientProxy")
    public static ServerProxy proxy;

    public static File configDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), Pl3x.name);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
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

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void onDisable(final FMLModDisabledEvent event) {
        DiscordRPC.INSTANCE.Discord_Shutdown();
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
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            // set vanilla diamond recipe group
            ShapedRecipes diamondRecipe = (ShapedRecipes) event.getRegistry()
                    .getValue(new ResourceLocation("minecraft", "diamond"));
            if (diamondRecipe != null) {
                diamondRecipe.setGroup("minecraft:diamond");
            }
        }
    }
}
