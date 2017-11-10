package net.pl3x.forge.listener;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.Logger;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.advancement.ModAdvancements;
import net.pl3x.forge.color.ChatColor;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.data.CapabilityProvider;
import net.pl3x.forge.data.PlayerData;
import net.pl3x.forge.entity.EntityBanker;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.item.ItemMoney;
import net.pl3x.forge.item.ModItems;
import net.pl3x.forge.item.custom.ItemClaimTool;
import net.pl3x.forge.motd.MOTDCache;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.scheduler.Pl3xRunnable;
import net.pl3x.forge.scheduler.Pl3xScheduler;
import net.pl3x.forge.util.Location;
import net.pl3x.forge.util.task.EntityLifeSpan;
import net.pl3x.forge.util.teleport.Teleport;
import net.pl3x.forge.util.teleport.TeleportRequest;

import java.util.Iterator;

public class ServerEventHandler {
    private short tick = 0;

    @SubscribeEvent
    public void on(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            PacketHandler.updatePlayerData((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void on(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.isCanceled() || event.player == null) {
            return;
        }
        Iterator<TeleportRequest> iter = Teleport.TELEPORT_REQUESTS.values().iterator();
        while (iter.hasNext()) {
            TeleportRequest request = iter.next();
            if (request.getRequester().equals(event.player) ||
                    request.getTarget().equals(event.player)) {
                request.cancel();
                iter.remove();
            }
        }
    }

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return; // not an online player
        }
        SPacketPlayerListHeaderFooter packet = new SPacketPlayerListHeaderFooter();
        packet.setHeader(new TextComponentString(ChatColor.colorize("\n&6&o&lPl3xCraft\n    &cWebsite&e: &7&opl3x.net\n&9&m-----------------")));
        packet.setFooter(new TextComponentString(ChatColor.colorize("&9&m-----------------\n&cMap&e: &7&opl3x.net/dynmap\n  &cVoice&e: &7&opl3x.net/discord  \n")));
        ((EntityPlayerMP) event.getEntity()).connection.sendPacket(packet);
    }

    @SubscribeEvent
    public void on(EntityItemPickupEvent event) {
        // Fires when player is close enough to item
        // whether item was actually picked up or not

        EntityItem entityItem = event.getItem();
        if (!ItemMoney.isMoney(entityItem)) {
            return; // not money; dont care
        }

        EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();

        player.onItemPickup(entityItem, 1);
        entityItem.setDead();
        event.setCanceled(true);

        PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
        capability.setCoins(capability.getCoins() + 1);

        ModAdvancements.PICKUP_COIN_TRIGGER.trigger(player, capability.getCoins());

        player.sendStatusMessage(new TextComponentString(
                ChatColor.colorize("&a&oPicked up " + entityItem.getItem().getDisplayName())), true);

        PacketHandler.updatePlayerData(player);
    }

    @SubscribeEvent
    public void on(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entity;

            // set /back location and notify
            Teleport.BACK_DB.put(player.getUniqueID(), new Location(player));
            Lang.send(player, Lang.INSTANCE.data.BACK_ON_DEATH);

            // drop all coins
            PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
            long balance = capability.getCoins();
            capability.setCoins(0);
            ItemStack coin = ModItems.COIN.getDefaultInstance();
            for (long i = 0; i < balance; i++) {
                player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, coin));
            }
            PacketHandler.updatePlayerData(player);
        } else {
            if (entity.fromSpawner) {
                return; // dont drop money if from a spawner
            }
            if (!(entity instanceof EntityMob)) {
                return; // only care for hostiles
            }
            if (!(event.getSource().getTrueSource() instanceof EntityPlayerMP)) {
                return; // was not killed by a player
            }
            entity.world.spawnEntity(new EntityItem(entity.world,
                    entity.posX, entity.posY, entity.posZ,
                    ModItems.COIN.getDefaultInstance()));
        }
    }

    @SubscribeEvent
    public void on(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof EntityBanker) {
            if (event.getEntityLiving() instanceof EntityZombie) {
                ((EntityZombie) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    @SubscribeEvent
    public void on(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        PlayerData playerData = player.getCapability(CapabilityProvider.CAPABILITY, null);
        Location bed = playerData.getHome("bed");
        Location newLoc = new Location(player.world, event.getPos());
        if (newLoc.equals(bed)) {
            return;
        }
        playerData.addHome("bed", newLoc);
        Lang.send(player, Lang.INSTANCE.data.HOME_BED_SET);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), true);
        // if cancelled, do not process anything else
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, true);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), false);
        // if cancelled, do not process anything else
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, false);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getSide() == Side.SERVER && event.getTarget() instanceof EntityArmorStand) {
            EntityPlayer player = event.getEntityPlayer();
            if (!player.isSneaking() && player.getHeldItemMainhand().getItem() != Items.NAME_TAG) {
                player.openGui(Pl3x.instance, ModGuiHandler.ARMOR_STAND,
                        event.getWorld(), 0, 0, 0, event.getTarget().getEntityId());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void on(CommandEvent event) {
        new Pl3xRunnable() {
            public void run() {
                if (event.isCanceled() || event.getSender() == null) {
                    return;
                }
                new Thread(() -> Logger.info("&6" + event.getSender().getName() + " issued server command: &3/" +
                        event.getCommand().getName() + " " + String.join(" ", event.getParameters())),
                        ChatColor.colorize("&3Command&r")).start();
            }
        }.runTaskLater(1);
    }

    @SubscribeEvent
    public void on(LivingSpawnEvent.CheckSpawn event) {
        ResourceLocation key = EntityList.getKey(event.getEntity());
        if (key == null) {
            return;
        }
        switch (key.toString()) {
            case "minecraft:blaze":
            case "minecraft:cave_spider":
            case "minecraft:creeper":
            case "minecraft:enderman":
            case "minecraft:endermite":
            case "minecraft:ghast":
            case "minecraft:guardian":
            case "minecraft:husk":
            case "minecraft:magma_cube":
            case "minecraft:silverfish":
            case "minecraft:skeleton":
            case "minecraft:slime":
            case "minecraft:spider":
            case "minecraft:stray":
            case "minecraft:vex":
            case "minecraft:witch":
            case "minecraft:wither_skeleton":
            case "minecraft:zombie":
            case "minecraft:zombie_pigman":
            case "minecraft:zombie_villager":
                new EntityLifeSpan(event.getEntityLiving())
                        .runTaskLater(12000); // 10 minutes
        }
    }

    @SubscribeEvent
    public void on(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return; // only process at end of tick to prevent double processing each tick
        }

        // IMPORTANT!
        // IMPORTANT! Do not store variables here. GC will freak out and cause lag
        // IMPORTANT!

        // tick the scheduler
        Pl3xScheduler.INSTANCE.tick();

        // update the MOTD
        FMLCommonHandler.instance().getMinecraftServerInstance().getServerStatusResponse()
                .setServerDescription(MOTDCache.INSTANCE.getDescription());
        if (MOTDCache.INSTANCE.getIconBlob() != null) {
            FMLCommonHandler.instance().getMinecraftServerInstance()
                    .getServerStatusResponse().setFavicon(MOTDCache.INSTANCE.getIconBlob());
        }

        tick++;
        if (tick > 20) {
            tick = 0;
            FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player ->
                    ModAdvancements.PLAY_TIME_TRIGGER.trigger(player, player.getStatFile().readStat(StatList.PLAY_ONE_MINUTE)));
        }
    }
}
