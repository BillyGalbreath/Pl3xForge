package net.pl3x.forge.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.pl3x.forge.advancement.trigger.DepositCoinTrigger;
import net.pl3x.forge.advancement.trigger.PickupCoinTrigger;
import net.pl3x.forge.advancement.trigger.PlayTimeTrigger;

public class ModAdvancements {
    public static final DepositCoinTrigger DEPOSIT_COIN_TRIGGER = new DepositCoinTrigger();
    public static final PickupCoinTrigger PICKUP_COIN_TRIGGER = new PickupCoinTrigger();
    public static final PlayTimeTrigger PLAY_TIME_TRIGGER = new PlayTimeTrigger();

    public static void registerTriggers() {
        CriteriaTriggers.register(DEPOSIT_COIN_TRIGGER);
        CriteriaTriggers.register(PICKUP_COIN_TRIGGER);
        CriteriaTriggers.register(PLAY_TIME_TRIGGER);
    }
}
