package de.jan.skyblock.command.islandCommands;

import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;

public interface IslandCommands {

    void onCommand(IslandManager islandManager, SkyPlayer skyPlayer, Player player, String[] args);
}
