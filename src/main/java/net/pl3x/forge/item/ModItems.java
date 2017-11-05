package net.pl3x.forge.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.item.custom.ItemClaimTool;
import net.pl3x.forge.item.custom.ItemTrafficCone;
import net.pl3x.forge.item.custom.tool.ItemAxe;
import net.pl3x.forge.item.custom.tool.ItemHoe;
import net.pl3x.forge.item.custom.tool.ItemPickaxe;
import net.pl3x.forge.item.custom.tool.ItemShovel;
import net.pl3x.forge.item.custom.tool.ItemSword;

public class ModItems {
    // ARMORS // new int[]{boots, leggings, chestplate, helmet}
    public static final net.minecraft.item.ItemArmor.ArmorMaterial emeraldArmorMaterial =
            EnumHelper.addArmorMaterial("EMERALD", Pl3x.modId + ":emerald",
                    35, new int[]{3, 7, 9, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3);
    public static final net.minecraft.item.ItemArmor.ArmorMaterial rubyArmorMaterial =
            EnumHelper.addArmorMaterial("RUBY", Pl3x.modId + ":ruby",
                    40, new int[]{4, 7, 10, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4);

    // TOOLS
    public static final Item.ToolMaterial emeraldToolMaterial =
            EnumHelper.addToolMaterial("EMERALD",
                    4, 1776, 10, 4, 22);
    public static final Item.ToolMaterial rubyToolMaterial =
            EnumHelper.addToolMaterial("RUBY",
                    5, 2038, 12, 5, 30);

    public static final ItemOre RUBY = new ItemOre("ruby", "ruby").setCreativeTab(CreativeTabs.MISC);

    public static final ItemSword RUBY_SWORD = new ItemSword(rubyToolMaterial, "ruby_sword").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemPickaxe RUBY_PICKAXE = new ItemPickaxe(rubyToolMaterial, "ruby_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemShovel RUBY_SHOVEL = new ItemShovel(rubyToolMaterial, "ruby_shovel").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemAxe RUBY_AXE = new ItemAxe(rubyToolMaterial, "ruby_axe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemHoe RUBY_HOE = new ItemHoe(rubyToolMaterial, "ruby_hoe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemArmor RUBY_HELMET = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.HEAD, "ruby_helmet").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor RUBY_CHESTPLATE = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.CHEST, "ruby_chestplate").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor RUBY_LEGGINGS = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.LEGS, "ruby_leggings").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor RUBY_BOOTS = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.FEET, "ruby_boots").setCreativeTab(CreativeTabs.COMBAT);

    public static final ItemSword EMERALD_SWORD = new ItemSword(emeraldToolMaterial, "emerald_sword").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemPickaxe EMERALD_PICKAXE = new ItemPickaxe(emeraldToolMaterial, "emerald_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemShovel EMERALD_SHOVEL = new ItemShovel(emeraldToolMaterial, "emerald_shovel").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemAxe EMERALD_AXE = new ItemAxe(emeraldToolMaterial, "emerald_axe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemHoe EMERALD_HOE = new ItemHoe(emeraldToolMaterial, "emerald_hoe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemArmor EMERALD_HELMET = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.HEAD, "emerald_helmet").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor EMERALD_CHESTPLATE = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.CHEST, "emerald_chestplate").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor EMERALD_LEGGINGS = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.LEGS, "emerald_leggings").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor EMERALD_BOOTS = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.FEET, "emerald_boots").setCreativeTab(CreativeTabs.COMBAT);

    public static final ItemHat HAT_AFRO = new ItemHat("hat_afro");
    public static final ItemHat HAT_BIGHEAD_BILLYGALBREATH = new ItemHat("hat_bighead_billygalbreath");
    public static final ItemHat HAT_BIGHEAD_CHRYSTI = new ItemHat("hat_bighead_chrysti");
    public static final ItemHat HAT_CAKE = new ItemHat("hat_cake");
    public static final ItemHat HAT_CLOAK = new ItemHat("hat_cloak");
    public static final ItemHat HAT_COWBOY = new ItemHat("hat_cowboy");
    public static final ItemHat HAT_EYE_BAND = new ItemHat("hat_eye_band");
    public static final ItemHat HAT_FEZ = new ItemHat("hat_fez");
    public static final ItemHat HAT_HARDHAT_OFF = new ItemHat("hat_hardhat_off");
    public static final ItemHat HAT_HARDHAT_ON = new ItemHat("hat_hardhat_on");
    public static final ItemHat HAT_HEADPHONES_CAT_EARS = new ItemHat("hat_headphones_cat_ears");
    public static final ItemHat HAT_HIGHHAT = new ItemHat("hat_highhat");
    public static final ItemHat HAT_MULLET = new ItemHat("hat_mullet");
    public static final ItemHat HAT_OCELOT = new ItemHat("hat_ocelot");
    public static final ItemHat HAT_OCELOT_SIAMESE = new ItemHat("hat_ocelot_siamese");
    public static final ItemHat HAT_OCELOT_TABBY = new ItemHat("hat_ocelot_tabby");
    public static final ItemHat HAT_OCELOT_TUX = new ItemHat("hat_ocelot_tux");
    public static final ItemHat HAT_SANTA = new ItemHat("hat_santa");
    public static final ItemHat HAT_SOMBRERO = new ItemHat("hat_sombrero");
    public static final ItemHat HAT_SQUID = new ItemHat("hat_squid");
    public static final ItemHat HAT_TOPHAT_BLACK_BLUE = new ItemHat("hat_tophat_black_blue");
    public static final ItemHat HAT_TOPHAT_BLACK_GRAY = new ItemHat("hat_tophat_black_gray");
    public static final ItemHat HAT_TOPHAT_BLACK_RED = new ItemHat("hat_tophat_black_red");
    public static final ItemHat HAT_TOPHAT_BLACK_WHITE = new ItemHat("hat_tophat_black_white");
    public static final ItemHat HAT_TOPHAT_BLACK_YELLOW = new ItemHat("hat_tophat_black_yellow");
    public static final ItemHat HAT_TOPHAT_ROUND_BLACK_WHITE = new ItemHat("hat_tophat_round_black_white");
    public static final ItemHat HAT_TOPHAT_ROUND_WHITE_BLACK = new ItemHat("hat_tophat_round_white_black");
    public static final ItemHat HAT_TOPHAT_WHITE_RED = new ItemHat("hat_tophat_white_red");
    public static final ItemHat HAT_WIZARD = new ItemHat("hat_wizard");
    public static final ItemHat HAT_WIZARD_2 = new ItemHat("hat_wizard2");

    public static final ItemClaimTool CLAIM_TOOL = new ItemClaimTool();
    public static final ItemMoney COIN = new ItemMoney("coin");
    public static final ItemBase SEASONAL = new ItemBase("seasonal");
    public static final ItemTrafficCone TRAFFIC_CONE = new ItemTrafficCone();


    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                RUBY,

                RUBY_SWORD,
                RUBY_PICKAXE,
                RUBY_SHOVEL,
                RUBY_AXE,
                RUBY_HOE,
                RUBY_HELMET,
                RUBY_CHESTPLATE,
                RUBY_LEGGINGS,
                RUBY_BOOTS,

                EMERALD_SWORD,
                EMERALD_PICKAXE,
                EMERALD_SHOVEL,
                EMERALD_AXE,
                EMERALD_HOE,
                EMERALD_HELMET,
                EMERALD_CHESTPLATE,
                EMERALD_LEGGINGS,
                EMERALD_BOOTS,

                HAT_AFRO,
                HAT_BIGHEAD_BILLYGALBREATH,
                HAT_BIGHEAD_CHRYSTI,
                HAT_CAKE,
                HAT_CLOAK,
                HAT_COWBOY,
                HAT_EYE_BAND,
                HAT_FEZ,
                HAT_HARDHAT_OFF,
                HAT_HARDHAT_ON,
                HAT_HEADPHONES_CAT_EARS,
                HAT_HIGHHAT,
                HAT_MULLET,
                HAT_OCELOT,
                HAT_OCELOT_SIAMESE,
                HAT_OCELOT_TABBY,
                HAT_OCELOT_TUX,
                HAT_SANTA,
                HAT_SOMBRERO,
                HAT_SQUID,
                HAT_TOPHAT_BLACK_BLUE,
                HAT_TOPHAT_BLACK_GRAY,
                HAT_TOPHAT_BLACK_RED,
                HAT_TOPHAT_BLACK_WHITE,
                HAT_TOPHAT_BLACK_YELLOW,
                HAT_TOPHAT_ROUND_BLACK_WHITE,
                HAT_TOPHAT_ROUND_WHITE_BLACK,
                HAT_TOPHAT_WHITE_RED,
                HAT_WIZARD,
                HAT_WIZARD_2,

                CLAIM_TOOL,
                COIN,
                SEASONAL,
                TRAFFIC_CONE
        );
    }

    public static void registerModels() {
        RUBY.registerItemModel();
        RUBY_SWORD.registerItemModel();
        RUBY_PICKAXE.registerItemModel();
        RUBY_SHOVEL.registerItemModel();
        RUBY_AXE.registerItemModel();
        RUBY_HOE.registerItemModel();
        RUBY_HELMET.registerItemModel();
        RUBY_CHESTPLATE.registerItemModel();
        RUBY_LEGGINGS.registerItemModel();
        RUBY_BOOTS.registerItemModel();

        EMERALD_SWORD.registerItemModel();
        EMERALD_PICKAXE.registerItemModel();
        EMERALD_SHOVEL.registerItemModel();
        EMERALD_AXE.registerItemModel();
        EMERALD_HOE.registerItemModel();
        EMERALD_HELMET.registerItemModel();
        EMERALD_CHESTPLATE.registerItemModel();
        EMERALD_LEGGINGS.registerItemModel();
        EMERALD_BOOTS.registerItemModel();

        HAT_AFRO.registerItemModel();
        HAT_BIGHEAD_BILLYGALBREATH.registerItemModel();
        HAT_BIGHEAD_CHRYSTI.registerItemModel();
        HAT_CAKE.registerItemModel();
        HAT_CLOAK.registerItemModel();
        HAT_COWBOY.registerItemModel();
        HAT_EYE_BAND.registerItemModel();
        HAT_FEZ.registerItemModel();
        HAT_HARDHAT_OFF.registerItemModel();
        HAT_HARDHAT_ON.registerItemModel();
        HAT_HEADPHONES_CAT_EARS.registerItemModel();
        HAT_HIGHHAT.registerItemModel();
        HAT_MULLET.registerItemModel();
        HAT_OCELOT.registerItemModel();
        HAT_OCELOT_SIAMESE.registerItemModel();
        HAT_OCELOT_TABBY.registerItemModel();
        HAT_OCELOT_TUX.registerItemModel();
        HAT_SANTA.registerItemModel();
        HAT_SOMBRERO.registerItemModel();
        HAT_SQUID.registerItemModel();
        HAT_TOPHAT_BLACK_BLUE.registerItemModel();
        HAT_TOPHAT_BLACK_GRAY.registerItemModel();
        HAT_TOPHAT_BLACK_RED.registerItemModel();
        HAT_TOPHAT_BLACK_WHITE.registerItemModel();
        HAT_TOPHAT_BLACK_YELLOW.registerItemModel();
        HAT_TOPHAT_ROUND_BLACK_WHITE.registerItemModel();
        HAT_TOPHAT_ROUND_WHITE_BLACK.registerItemModel();
        HAT_TOPHAT_WHITE_RED.registerItemModel();
        HAT_WIZARD.registerItemModel();
        HAT_WIZARD_2.registerItemModel();

        CLAIM_TOOL.registerItemModel();
        COIN.registerItemModel();
        SEASONAL.registerItemModel();
        TRAFFIC_CONE.registerItemModel();
    }
}
