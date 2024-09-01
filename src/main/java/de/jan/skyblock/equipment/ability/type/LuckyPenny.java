package de.jan.skyblock.equipment.ability.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;

import java.util.List;

public class LuckyPenny implements Ability {

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<gold>Glück's Penny");
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Händler Schätzen diesen Penny", " <gray>- Du erhälst bei den meisten Shops einen <gold>Discount", "<gray> - Dafür musst du den Penny aber vorzeigen");
    }

    @Override
    public void activate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("luckyPenny activated");
    }

    @Override
    public void deactivate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().sendMessage("luckyPenny deactivated");
    }
}
