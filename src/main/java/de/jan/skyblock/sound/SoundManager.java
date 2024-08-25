package de.jan.skyblock.sound;

import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SoundManager {

    public static void playSound(Sounds sounds, Player... players) {
        Arrays.stream(players).forEach(player -> player.playSound(player, sounds.getSound(), sounds.getVolume(), sounds.getPitch()));
    }

    public static void playSound(Sounds sounds, SkyPlayer... skyPlayers) {
        Arrays.stream(skyPlayers).forEach(skyPlayer -> skyPlayer.getPlayer().playSound(skyPlayer.getPlayer(), sounds.getSound(), sounds.getVolume(), sounds.getPitch()));
    }
}
