package de.jan.skyblock.equipment.item;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.equipment.Equipment;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.equipment.ability.type.Miner;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MinersPickaxe implements Equipment {

    @Override
    public String displayName() {
        return "<gold>MinersPickaxe";
    }

    @Override
    public Material material() {
        return Material.GOLDEN_PICKAXE;
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Das wichtigste Werkzeug eines Bergarbeiters");
    }

    @Override
    public Ability ability() {
        return new Miner(10);
    }

    @Override
    public Type type() {
        return Type.MAIN_HAND;
    }

    @Override
    public ItemStack itemStack() {
        return new ItemBuilder(material()).setDisplayName(displayName()).setLoreString(lore()).setLoreString(ability().lore()).setLore(type().getDescription()).build();
    }
}
