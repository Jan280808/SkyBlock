package de.jan.skyblock.equipment.ability.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.ability.Ability;
import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Speed implements Ability {

    private final int amplifier;

    public Speed(int amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("Speed");
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Erhalte dauerhaft Schnelligkeit x" + amplifier);
    }

    @Override
    public void activate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
        skyPlayer.getPlayer().sendMessage("activ speed");
    }

    @Override
    public void deactivate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        skyPlayer.getPlayer().sendMessage("deactiv speed");
    }
}
