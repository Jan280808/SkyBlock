package de.jan.skyblock.sound;

import lombok.Getter;
import org.bukkit.Sound;

@Getter
public enum Sounds {
    ERROR(Sound.ENTITY_CHICKEN_EGG, 1, 1),
    SUCCESSES(Sound.ENTITY_PLAYER_LEVELUP, 1, 1),
    TELEPORT(Sound.ENTITY_SHULKER_TELEPORT, 1, 2),
    OPEN(Sound.BLOCK_CHEST_OPEN, 1, 1),
    TRADE_LOADING(Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1),
    TRADE_ACCEPT(Sound.ENTITY_CHICKEN_EGG, 1, 1),
    TRADE_RESET(Sound.ENTITY_CHICKEN_EGG, 1, 0);

    private final Sound sound;
    private final int volume;
    private final int pitch;

    Sounds(Sound sound, int volume, int pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }
}
