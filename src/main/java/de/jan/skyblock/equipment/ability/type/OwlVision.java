package de.jan.skyblock.equipment.ability.type;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.ability.PotionAbility;
import de.jan.skyblock.player.SkyPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class OwlVision implements PotionAbility {

    @Override
    public Component displayName() {
        return ComponentSerializer.deserialize("<aqua>OwlVision");
    }

    @Override
    public void activate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().addPotionEffect(new PotionEffect(potionEffectType(), Integer.MAX_VALUE, amplifier(), false, false));
        skyPlayer.getPlayer().sendMessage("activ owl");
    }

    @Override
    public void deactivate(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().removePotionEffect(potionEffectType());
        skyPlayer.getPlayer().sendMessage("deactiv owl");
    }

    @Override
    public List<String> lore() {
        return List.of("<gray>Du erhälst dauerhaft <gold>" + potionEffectType().key().asMinimalString() + " <gray>x<gold>" + amplifier(), "<gray>Sehr nützlich wenn man <aqua>'full bright' <gray>nutzt...");
    }

    @Override
    public PotionEffectType potionEffectType() {
        return PotionEffectType.NIGHT_VISION;
    }

    @Override
    public int amplifier() {
        return 1;
    }
}
