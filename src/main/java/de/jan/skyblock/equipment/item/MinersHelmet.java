package de.jan.skyblock.equipment.item;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.Equipment;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.Miner;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MinersHelmet implements Equipment {

    @Override
    public String displayName() {
        return "<gold>Berkwerk";
    }

    @Override
    public Material material() {
        return Material.GOLDEN_HELMET;
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Der Staub und die lauten Maschinen machen dich Krank...");
    }

    @Override
    public Ability ability() {
        return new Miner(5);
    }

    @Override
    public Type type() {
        return Type.EQUIPMENT;
    }

    @Override
    public ItemStack itemStack() {
        return new ItemBuilder(material()).setDisplayName(displayName()).setLoreString(lore()).setLoreString(ability().lore()).setLore(type().getDescription()).build();
    }
}
