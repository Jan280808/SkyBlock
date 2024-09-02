package de.jan.skyblock.equipment.item;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.Equipment;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.OwlVision;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class NightVisionDevice implements Equipment {

    @Override
    public String displayName() {
        return "<aqua>Nachtsichtgerät";
    }

    @Override
    public Material material() {
        return Material.DIAMOND_HELMET;
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Du bist gerne Nachts unterwegs");
    }

    @Override
    public Ability ability() {
        return new OwlVision();
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
