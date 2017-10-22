package net.pl3x.forge.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.item.tool.ItemAxe;
import net.pl3x.forge.item.tool.claimtool.ItemClaimToolCreate;
import net.pl3x.forge.item.tool.claimtool.ItemClaimToolResize;
import net.pl3x.forge.item.tool.claimtool.ItemClaimToolVisual;
import net.pl3x.forge.item.tool.ItemHoe;
import net.pl3x.forge.item.tool.ItemPickaxe;
import net.pl3x.forge.item.tool.ItemShovel;
import net.pl3x.forge.item.tool.ItemSword;

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

    public static final ItemOre ruby = new ItemOre("ruby", "ruby").setCreativeTab(CreativeTabs.MISC);

    public static final ItemSword rubySword = new ItemSword(rubyToolMaterial, "ruby_sword").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemPickaxe rubyPickaxe = new ItemPickaxe(rubyToolMaterial, "ruby_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemShovel rubyShovel = new ItemShovel(rubyToolMaterial, "ruby_shovel").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemAxe rubyAxe = new ItemAxe(rubyToolMaterial, "ruby_axe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemHoe rubyHoe = new ItemHoe(rubyToolMaterial, "ruby_hoe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemArmor rubyHelmet = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.HEAD, "ruby_helmet").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor rubyChestplate = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.CHEST, "ruby_chestplate").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor rubyLeggings = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.LEGS, "ruby_leggings").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor rubyBoots = new ItemArmor(rubyArmorMaterial, EntityEquipmentSlot.FEET, "ruby_boots").setCreativeTab(CreativeTabs.COMBAT);

    public static final ItemSword emeraldSword = new ItemSword(emeraldToolMaterial, "emerald_sword").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemPickaxe emeraldPickaxe = new ItemPickaxe(emeraldToolMaterial, "emerald_pickaxe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemShovel emeraldShovel = new ItemShovel(emeraldToolMaterial, "emerald_shovel").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemAxe emeraldAxe = new ItemAxe(emeraldToolMaterial, "emerald_axe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemHoe emeraldHoe = new ItemHoe(emeraldToolMaterial, "emerald_hoe").setCreativeTab(CreativeTabs.TOOLS);
    public static final ItemArmor emeraldHelmet = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.HEAD, "emerald_helmet").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor emeraldChestplate = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.CHEST, "emerald_chestplate").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor emeraldLeggings = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.LEGS, "emerald_leggings").setCreativeTab(CreativeTabs.COMBAT);
    public static final ItemArmor emeraldBoots = new ItemArmor(emeraldArmorMaterial, EntityEquipmentSlot.FEET, "emerald_boots").setCreativeTab(CreativeTabs.COMBAT);

    public static final ItemHat hatAfro = new ItemHat("hat_afro");
    public static final ItemHat hatBigheadBillygalbreath = new ItemHat("hat_bighead_billygalbreath");
    public static final ItemHat hatBigheadChrysti = new ItemHat("hat_bighead_chrysti");
    public static final ItemHat hatCake = new ItemHat("hat_cake");
    public static final ItemHat hatCloak = new ItemHat("hat_cloak");
    public static final ItemHat hatCowboy = new ItemHat("hat_cowboy");
    public static final ItemHat hatEyeBand = new ItemHat("hat_eye_band");
    public static final ItemHat hatFez = new ItemHat("hat_fez");
    public static final ItemHat hatHardhatOff = new ItemHat("hat_hardhat_off");
    public static final ItemHat hatHardhatOn = new ItemHat("hat_hardhat_on");
    public static final ItemHat hatHeadphonesCatEars = new ItemHat("hat_headphones_cat_ears");
    public static final ItemHat hatHighhat = new ItemHat("hat_highhat");
    public static final ItemHat hatMullet = new ItemHat("hat_mullet");
    public static final ItemHat hatOcelot = new ItemHat("hat_ocelot");
    public static final ItemHat hatOcelotSiamese = new ItemHat("hat_ocelot_siamese");
    public static final ItemHat hatOcelotTabby = new ItemHat("hat_ocelot_tabby");
    public static final ItemHat hatOcelotTux = new ItemHat("hat_ocelot_tux");
    public static final ItemHat hatSanta = new ItemHat("hat_santa");
    public static final ItemHat hatSombrero = new ItemHat("hat_sombrero");
    public static final ItemHat hatSquid = new ItemHat("hat_squid");
    public static final ItemHat hatTophatBlackBlue = new ItemHat("hat_tophat_black_blue");
    public static final ItemHat hatTophatBlackGrey = new ItemHat("hat_tophat_black_gray");
    public static final ItemHat hatTophatBlackRed = new ItemHat("hat_tophat_black_red");
    public static final ItemHat hatTophatBlackWhite = new ItemHat("hat_tophat_black_white");
    public static final ItemHat hatTophatBlackYellow = new ItemHat("hat_tophat_black_yellow");
    public static final ItemHat hatTophatRoundBlackWhite = new ItemHat("hat_tophat_round_black_white");
    public static final ItemHat hatTophatRoundWhiteBlack = new ItemHat("hat_tophat_round_white_black");
    public static final ItemHat hatTophatWhiteRed = new ItemHat("hat_tophat_white_red");
    public static final ItemHat hatWizard = new ItemHat("hat_wizard");
    public static final ItemHat hatWizard2 = new ItemHat("hat_wizard2");

    public static final ItemMoney COIN = new ItemMoney("coin");

    public static final ItemBase seasonal = new ItemBase("seasonal");

    public static final ItemClaimToolCreate CLAIM_TOOL_CREATE = new ItemClaimToolCreate("claim_tool");
    public static final ItemClaimToolResize CLAIM_TOOL_RESIZE = new ItemClaimToolResize("claim_tool_resize");
    public static final ItemClaimToolVisual CLAIM_TOOL_VISUAL = new ItemClaimToolVisual("claim_tool_visual");

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                ruby,

                rubySword,
                rubyPickaxe,
                rubyShovel,
                rubyAxe,
                rubyHoe,
                rubyHelmet,
                rubyChestplate,
                rubyLeggings,
                rubyBoots,

                emeraldSword,
                emeraldPickaxe,
                emeraldShovel,
                emeraldAxe,
                emeraldHoe,
                emeraldHelmet,
                emeraldChestplate,
                emeraldLeggings,
                emeraldBoots,

                hatAfro,
                hatBigheadBillygalbreath,
                hatBigheadChrysti,
                hatCake,
                hatCloak,
                hatCowboy,
                hatEyeBand,
                hatFez,
                hatHardhatOff,
                hatHardhatOn,
                hatHeadphonesCatEars,
                hatHighhat,
                hatMullet,
                hatOcelot,
                hatOcelotSiamese,
                hatOcelotTabby,
                hatOcelotTux,
                hatSanta,
                hatSombrero,
                hatSquid,
                hatTophatBlackBlue,
                hatTophatBlackGrey,
                hatTophatBlackRed,
                hatTophatBlackWhite,
                hatTophatBlackYellow,
                hatTophatRoundBlackWhite,
                hatTophatRoundWhiteBlack,
                hatTophatWhiteRed,
                hatWizard,
                hatWizard2,

                COIN,

                seasonal,

                CLAIM_TOOL_CREATE,
                CLAIM_TOOL_RESIZE,
                CLAIM_TOOL_VISUAL
        );
    }

    public static void registerModels() {
        ruby.registerItemModel();
        rubySword.registerItemModel();
        rubyPickaxe.registerItemModel();
        rubyShovel.registerItemModel();
        rubyAxe.registerItemModel();
        rubyHoe.registerItemModel();
        rubyHelmet.registerItemModel();
        rubyChestplate.registerItemModel();
        rubyLeggings.registerItemModel();
        rubyBoots.registerItemModel();

        emeraldSword.registerItemModel();
        emeraldPickaxe.registerItemModel();
        emeraldShovel.registerItemModel();
        emeraldAxe.registerItemModel();
        emeraldHoe.registerItemModel();
        emeraldHelmet.registerItemModel();
        emeraldChestplate.registerItemModel();
        emeraldLeggings.registerItemModel();
        emeraldBoots.registerItemModel();

        hatAfro.registerItemModel();
        hatBigheadBillygalbreath.registerItemModel();
        hatBigheadChrysti.registerItemModel();
        hatCake.registerItemModel();
        hatCloak.registerItemModel();
        hatCowboy.registerItemModel();
        hatEyeBand.registerItemModel();
        hatFez.registerItemModel();
        hatHardhatOff.registerItemModel();
        hatHardhatOn.registerItemModel();
        hatHeadphonesCatEars.registerItemModel();
        hatHighhat.registerItemModel();
        hatMullet.registerItemModel();
        hatOcelot.registerItemModel();
        hatOcelotSiamese.registerItemModel();
        hatOcelotTabby.registerItemModel();
        hatOcelotTux.registerItemModel();
        hatSanta.registerItemModel();
        hatSombrero.registerItemModel();
        hatSquid.registerItemModel();
        hatTophatBlackBlue.registerItemModel();
        hatTophatBlackGrey.registerItemModel();
        hatTophatBlackRed.registerItemModel();
        hatTophatBlackWhite.registerItemModel();
        hatTophatBlackYellow.registerItemModel();
        hatTophatRoundBlackWhite.registerItemModel();
        hatTophatRoundWhiteBlack.registerItemModel();
        hatTophatWhiteRed.registerItemModel();
        hatWizard.registerItemModel();
        hatWizard2.registerItemModel();

        COIN.registerItemModel();

        seasonal.registerItemModel();

        CLAIM_TOOL_CREATE.registerItemModel();
        CLAIM_TOOL_RESIZE.registerItemModel();
        CLAIM_TOOL_VISUAL.registerItemModel();
    }
}
