package de.jan.skyblock.equipment.item;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.Equipment;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.Customer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Coupon implements Equipment {

    @Override
    public String displayName() {
        return "<gold>Coupon";
    }

    @Override
    public Material material() {
        return Material.PAPER;
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Bist du der neue Schnäpchenjäger?");
    }

    @Override
    public Ability ability() {
        return new Customer(10.0);
    }

    @Override
    public Type type() {
        return Type.OFF_HAND;
    }

    @Override
    public ItemStack itemStack() {
        return new ItemBuilder(material()).setDisplayName(displayName()).setLoreString(lore()).setLoreString(ability().lore()).setLore(type().getDescription()).build();
    }
}
