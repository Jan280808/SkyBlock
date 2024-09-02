package de.jan.skyblock.equipment.item;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.Equipment;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.Speed;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HermesBoots implements Equipment {

    @Override
    public String displayName() {
        return "<aqua>Hermsstiefel";
    }

    @Override
    public Material material() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Diese magischen Stiefel lassen den Träger schneller werden");
    }

    @Override
    public Ability ability() {
        return new Speed(1);
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
