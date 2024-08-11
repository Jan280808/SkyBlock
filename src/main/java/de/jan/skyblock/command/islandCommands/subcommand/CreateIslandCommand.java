package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;

public class CreateIslandCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, PlayerManager playerManager, Player player, String[] args) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(skyPlayer.getIsland() != null) {
            player.sendMessage("du hast bereits eine insel :)");
            return;
        }
        islandManager.createIsland(skyPlayer);
    }
}
