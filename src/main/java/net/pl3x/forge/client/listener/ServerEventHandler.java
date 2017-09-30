package net.pl3x.forge.client.listener;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.pl3x.forge.client.ChatColor;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.item.ItemMoney;
import net.pl3x.forge.client.item.ModItems;
import net.pl3x.forge.client.network.PacketHandler;

public class ServerEventHandler {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            PacketHandler.updatePlayerData((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
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

        player.sendStatusMessage(new TextComponentString(
                ChatColor.colorize("&a&oPicked up " + entityItem.getItem().getDisplayName())), true);

        PacketHandler.updatePlayerData(player);
    }

    @SubscribeEvent
    public void onMobDeathDropCoin(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
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

    @SubscribeEvent
    public void onPlayerDeathDropCoins(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayerMP)) {
            return; // only care for players
        }
        EntityPlayerMP player = (EntityPlayerMP) entity;

        PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);

        long balance = capability.getCoins();
        capability.setCoins(0);

        ItemStack coin = ModItems.COIN.getDefaultInstance();
        for (long i = 0; i < balance; i++) {
            player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, coin));
        }

        PacketHandler.updatePlayerData(player);
    }
}
