package de.jan.skyblock.equipment.ability;

import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;

import java.util.List;

public interface Ability {

    Component displayName();

    List<String> lore();

    void activate(SkyPlayer skyPlayer);

    void deactivate(SkyPlayer skyPlayer);
}
