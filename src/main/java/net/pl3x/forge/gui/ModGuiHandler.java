package net.pl3x.forge.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.pl3x.forge.container.ContainerArmorstand;
import net.pl3x.forge.container.ContainerBanker;
import net.pl3x.forge.container.ContainerBedsideTable;
import net.pl3x.forge.container.ContainerEnchantmentSplitter;
import net.pl3x.forge.container.ContainerShopCustomer;
import net.pl3x.forge.container.ContainerShopOwner;
import net.pl3x.forge.container.ContainerTrafficLightControlBox;
import net.pl3x.forge.gui.armorstand.GuiArmorStand;
import net.pl3x.forge.gui.armorstand.GuiArmorStandDisplay;
import net.pl3x.forge.gui.banker.GuiBanker;
import net.pl3x.forge.gui.banker.GuiBankerAction;
import net.pl3x.forge.gui.claim.GuiClaim;
import net.pl3x.forge.gui.shop.GuiShopCustomer;
import net.pl3x.forge.gui.shop.GuiShopOwner;
import net.pl3x.forge.gui.shop.GuiShopOwnerDisplay;
import net.pl3x.forge.gui.shop.GuiShopOwnerFunds;
import net.pl3x.forge.network.BankPacket;
import net.pl3x.forge.tileentity.TileEntityBedsideTable;
import net.pl3x.forge.tileentity.TileEntityShop;
import net.pl3x.forge.tileentity.TileEntityTrafficLightControlBox;

public class ModGuiHandler implements IGuiHandler {
    public static final int ENCHANTMENT_SPLITTER = 0;
    public static final int BANKER = 1;
    public static final int BANKER_DEPOSIT_COIN = 2;
    public static final int BANKER_WITHDRAW_COIN = 3;
    public static final int BANKER_DEPOSIT_EXP = 4;
    public static final int BANKER_WITHDRAW_EXP = 5;
    public static final int SHOP_OWNER = 6;
    public static final int SHOP_OWNER_DISPLAY = 7;
    public static final int SHOP_OWNER_FUNDS = 8;
    public static final int SHOP_CUSTOMER = 9;
    public static final int TRAFFIC_LIGHT_CONTROL_BOX = 10;
    public static final int ARMOR_STAND = 11;
    public static final int ARMOR_STAND_DISPLAY = 12;
    public static final int CLAIM = 13;
    public static final int BEDSIDE_TABLE = 14;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z, int entityId) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new ContainerEnchantmentSplitter(player.inventory, world, new BlockPos(x, y, z));
            case BANKER:
                return new ContainerBanker(player);
            case SHOP_OWNER:
                return new ContainerShopOwner(player.inventory, (TileEntityShop) world.getTileEntity(new BlockPos(x, y, z)), true);
            case SHOP_OWNER_DISPLAY:
            case SHOP_OWNER_FUNDS:
                return new ContainerShopOwner(player.inventory, (TileEntityShop) world.getTileEntity(new BlockPos(x, y, z)), false);
            case SHOP_CUSTOMER:
                return new ContainerShopCustomer(player.inventory, (TileEntityShop) world.getTileEntity(new BlockPos(x, y, z)));
            case TRAFFIC_LIGHT_CONTROL_BOX:
                return new ContainerTrafficLightControlBox(player, (TileEntityTrafficLightControlBox) world.getTileEntity(new BlockPos(x, y, z)));
            case ARMOR_STAND:
                return new ContainerArmorstand(player, entityId, true);
            case ARMOR_STAND_DISPLAY:
                return new ContainerArmorstand(player, entityId, false);
            case BEDSIDE_TABLE:
                return new ContainerBedsideTable(player.inventory, (TileEntityBedsideTable) world.getTileEntity(new BlockPos(x, y, z)), entityId);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z, int entityId) {
        switch (ID) {
            case ENCHANTMENT_SPLITTER:
                return new GuiEnchantmentSplitter(getServerGuiElement(ID, player, world, x, y, z, entityId), player.inventory);
            case BANKER:
                return new GuiBanker(getServerGuiElement(ID, player, world, x, y, z, entityId));
            case BANKER_DEPOSIT_COIN:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z, entityId), player, BankPacket.DEPOSIT_COIN);
            case BANKER_WITHDRAW_COIN:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z, entityId), player, BankPacket.WITHDRAW_COIN);
            case BANKER_DEPOSIT_EXP:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z, entityId), player, BankPacket.DEPOSIT_EXP);
            case BANKER_WITHDRAW_EXP:
                return new GuiBankerAction(getServerGuiElement(BANKER, player, world, x, y, z, entityId), player, BankPacket.WITHDRAW_EXP);
            case SHOP_OWNER:
                return new GuiShopOwner(getServerGuiElement(ID, player, world, x, y, z, entityId));
            case SHOP_OWNER_DISPLAY:
                return new GuiShopOwnerDisplay(getServerGuiElement(ID, player, world, x, y, z, entityId), player.inventory);
            case SHOP_OWNER_FUNDS:
                return new GuiShopOwnerFunds(getServerGuiElement(ID, player, world, x, y, z, entityId), player.inventory);
            case SHOP_CUSTOMER:
                return new GuiShopCustomer(getServerGuiElement(ID, player, world, x, y, z, entityId), player.inventory);
            case TRAFFIC_LIGHT_CONTROL_BOX:
                return new GuiTrafficLightControlBox(getServerGuiElement(ID, player, world, x, y, z, entityId), player);
            case ARMOR_STAND:
                return new GuiArmorStand(getServerGuiElement(ID, player, world, x, y, z, entityId), player, entityId);
            case ARMOR_STAND_DISPLAY:
                return new GuiArmorStandDisplay(getServerGuiElement(ID, player, world, x, y, z, entityId), player, entityId);
            case CLAIM:
                return new GuiClaim();
            case BEDSIDE_TABLE:
                return new GuiBedsideTable(getServerGuiElement(ID, player, world, x, y, z, entityId));
            default:
                return null;
        }
    }
}
