package de.jan.skyblock.island;

import de.jan.skyblock.builder.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum Difficulty {
    ISLAND_CLASSIC(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<gray>Klassisch").setLore(" ", "<gray>Spiele auf eine klassische Insel wie von Früher", "<gray>- Kleine Insel", "<gray>- 1x Lavaeimer", "<gray>- 1x IceBlock", "<gray>- 8x Äpfel").build()),
    ISLAND_NORMAL(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<gray>Normal").setLore(" ", "<gray>Erhalte eine großzügie Insel, mit vielen Rohstoffen", "<gray>- 1x Lavaeimer", "<gray>- 8x Äpfel", "<gray>- Ein angebissenen Kuchen", "<gray>- Einige Erze").build()),
    ISLAND_HARD(new ItemBuilder(Material.GRASS_BLOCK).setDisplayName("<red>Harte").setLore(" ", "<gray>Verusch es doch, wenn du dich traust", "<gray>Nur für Fortgeschrittene SkyBlock-Spieler", " ", "<gray>???", "<gray>???").build());

    private final ItemStack itemStack;

    Difficulty(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
