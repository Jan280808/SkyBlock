package de.jan.skyblock.equipment.ability.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;

import java.util.List;

public class Customer implements Ability {

    private final double discount;

    public Customer(double discount) {
        this.discount = discount;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>Der Kunde ist König");
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Die meisten Händler Schätzen dieses Item", " <gray>Erhalte eine Begünstigung von " + discount + "% bei den meisten Shops");
    }

    @Override
    public void activate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("customer activated");
    }

    @Override
    public void deactivate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("customer deactivated");
    }
}
