package de.jan.skyblock.command.islandCommands;

import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import org.bukkit.entity.Player;

public interface IslandCommands {

    void onCommand(IslandManager islandManager, PlayerManager playerManager, Player player, String[] args);
}
