package net.pl3x.forge.util;

import net.minecraft.entity.player.EntityPlayer;

import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * @author desht
 * <p>
 * Adapted from ExperienceUtils code originally in ScrollingMenuSign.
 * <p>
 * Credit to nisovin (http://forums.bukkit.org/threads/experienceutils-make-giving-taking-exp-a-bit-more-intuitive.54450/#post-1067480)
 * for an implementation that avoids the problems of getTotalExperience(), which doesn't work properly after a player has enchanted something.
 * <p>
 * Credit to comphenix for further contributions:
 * See http://forums.bukkit.org/threads/experiencemanager-was-experienceutils-make-giving-taking-exp-a-bit-more-intuitive.54450/page-3#post-1273622
 */
public class ExperienceManager {
    private static int xpTotalToReachLevel[];

    static {
        // 25 is an arbitrary value for the initial table size - the actual
        // value isn't critically important since the table is resized as needed.
        initLookupTables(25);
    }

    private final WeakReference<EntityPlayer> player;
    private final String playerName;

    /**
     * Create a new ExperienceManager for the given player.
     *
     * @param player the player for this ExperienceManager object
     * @throws IllegalArgumentException if the player is null
     */
    public ExperienceManager(EntityPlayer player) {
        this.player = new WeakReference<>(player);
        this.playerName = player.getName();
    }

    /**
     * Initialize the XP lookup table. See http://minecraft.gamepedia.com/Experience
     *
     * @param maxLevel The highest level handled by the lookup tables
     */
    private static void initLookupTables(int maxLevel) {
        xpTotalToReachLevel = new int[maxLevel];

        for (int i = 0; i < xpTotalToReachLevel.length; i++) {
            xpTotalToReachLevel[i] =
                    i >= 30 ? (int) (3.5 * i * i - 151.5 * i + 2220) :
                            i >= 16 ? (int) (1.5 * i * i - 29.5 * i + 360) :
                                    17 * i;
        }
    }

    /**
     * Calculate the level that the given XP quantity corresponds to, without
     * using the lookup tables. This is needed if getLevelForExp() is called
     * with an XP quantity beyond the range of the existing lookup tables.
     *
     * @param exp Experience
     * @return Experience
     */
    private static int calculateLevelForExp(int exp) {
        int level = 0;
        int curExp = 7; // level 1
        int incr = 10;

        while (curExp <= exp) {
            curExp += incr;
            level++;
            incr += (level % 2 == 0) ? 3 : 4;
        }
        return level;
    }

    /**
     * Get the Player associated with this ExperienceManager.
     *
     * @return the Player object
     * @throws IllegalStateException if the player is no longer online
     */
    public EntityPlayer getPlayer() {
        EntityPlayer p = player.get();
        if (p == null) {
            throw new IllegalStateException("Player " + playerName + " is not online");
        }
        return p;
    }

    /**
     * Adjust the player's XP by the given amount in an intelligent fashion.
     * Works around some of the non-intuitive behaviour of the basic Bukkit
     * player.giveExp() method.
     *
     * @param amt Amount of XP, may be negative
     */
    public void changeExp(double amt) {
        setExp(getCurrentFractionalXP(), amt);
    }

    private void setExp(double base, double amt) {
        int xp = (int) Math.max(base + amt, 0);

        EntityPlayer player = getPlayer();
        int curLvl = player.experienceLevel;
        int newLvl = getLevelForExp(xp);

        // Increment level
        if (curLvl != newLvl) {
            player.experienceLevel = newLvl;
        }
        // Increment total experience - this should force the server to send an update packet
        if (xp > base) {
            player.experienceTotal = player.experienceTotal + xp - (int) base;
        }

        double pct = (base - getXpForLevel(newLvl) + amt) / (double) (getXpNeededToLevelUp(newLvl));
        player.experience = (float) pct;

        player.addExperienceLevel(0); // forces server to send update packet
    }

    /**
     * Get the player's current XP total.
     *
     * @return the player's total XP
     */
    public int getCurrentExp() {
        EntityPlayer player = getPlayer();

        int lvl = player.experienceLevel;
        return getXpForLevel(lvl) + Math.round(getXpNeededToLevelUp(lvl) * player.experience);
    }

    /**
     * Get the player's current fractional XP.
     *
     * @return The player's total XP with fractions.
     */
    private double getCurrentFractionalXP() {
        EntityPlayer player = getPlayer();

        int lvl = player.experienceLevel;
        return getXpForLevel(lvl) + (double) (getXpNeededToLevelUp(lvl) * player.experience);
    }

    /**
     * Get the level that the given amount of XP falls within.
     *
     * @param exp the amount to check for
     * @return the level that a player with this amount total XP would be
     * @throws IllegalArgumentException if the given XP is less than 0
     */
    private int getLevelForExp(int exp) {
        if (exp <= 0) {
            return 0;
        }
        if (exp > xpTotalToReachLevel[xpTotalToReachLevel.length - 1]) {
            // need to extend the lookup tables
            int newMax = calculateLevelForExp(exp) * 2;
            initLookupTables(newMax);
        }
        int pos = Arrays.binarySearch(xpTotalToReachLevel, exp);
        return pos < 0 ? -pos - 2 : pos;
    }

    /**
     * Retrieves the amount of experience the experience bar can hold at the given level.
     *
     * @param level the level to check
     * @return the amount of experience at this level in the level bar
     * @throws IllegalArgumentException if the level is less than 0
     */
    private int getXpNeededToLevelUp(int level) {
        return level > 30 ? 62 + (level - 30) * 7 : level >= 16 ? 17 + (level - 15) * 3 : 17;
    }

    /**
     * Return the total XP needed to be the given level.
     *
     * @param level The level to check for.
     * @return The amount of XP needed for the level.
     * @throws IllegalArgumentException if the level is less than 0 or greater than the current hard maximum
     */
    private int getXpForLevel(int level) {
        if (level >= xpTotalToReachLevel.length) {
            initLookupTables(level * 2);
        }
        return xpTotalToReachLevel[level];
    }
}