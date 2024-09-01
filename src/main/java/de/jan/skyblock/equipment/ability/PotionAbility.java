package de.jan.skyblock.equipment.ability;

import org.bukkit.potion.PotionEffectType;

public interface PotionAbility extends Ability {

    PotionEffectType potionEffectType();

    int amplifier();

}
