package de.jan.skyblock.equipment;

import de.jan.skyblock.equipment.ability.Ability;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Equipment {

    String displayName();

    Material material();

    List<String> lore();

    Ability ability();

    Type type();

    ItemStack itemStack();

    @Getter
    enum Type {
        EQUIPMENT("<gray>Zieh dieses Item an"),
        OFF_HAND("<gray>Halte dieses Item in der OffHand"),
        MAIN_HAND("<gray>Halte dieses Item in der MainHand");

        private final String description;

        Type(String description) {
            this.description = description;
        }
    }
}
