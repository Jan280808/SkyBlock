package de.jan.skyblock.equipment.ability.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;

import java.util.List;

public class Miner implements Ability {

    private final int amount = 5;

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>Miner");
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Erhöht die Wahrscheinlichkeit Erze zu generieren", "<gray> +" + amount + "% Erz-Wahrscheinlichkeit");
    }


    @Override
    public void activate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("active Miner");
    }

    @Override
    public void deactivate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("deactive Miner");
    }
}
