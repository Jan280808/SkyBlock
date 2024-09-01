package de.jan.skyblock.equipment;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.LuckyPenny;
import de.jan.skyblock.equipment.ability.type.Miner;
import de.jan.skyblock.equipment.ability.type.OwlVision;
import de.jan.skyblock.equipment.ability.PotionAbility;
import de.jan.skyblock.equipment.ability.type.Speed;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum Equipment {
    OWL(Material.DIAMOND_HELMET, new OwlVision()),
    MINER(Material.GOLDEN_HELMET, new Miner()),
    HERMES_BOOTS(Material.LEATHER_BOOTS, new Speed()),
    LUCKY_PENNY(Material.GOLD_NUGGET, new LuckyPenny());

    private final Material material;
    private final Ability ability;

    Equipment(Material material, Ability ability) {
        this.material = material;
        this.ability = ability;
    }

    public ItemStack getItemStack() {
        try {
            PotionAbility potionAbility = (PotionAbility) ability;
            return new ItemBuilder(material).setDisplayName(potionAbility.displayName()).setLoreString(potionAbility.lore()).build();
        } catch (Exception ignore) {
            return new ItemBuilder(material).setDisplayName(ability.displayName()).setLoreString(ability.lore()).build();
        }
    }
}
