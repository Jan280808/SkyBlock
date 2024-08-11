package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import org.bukkit.entity.Player;

public class InfoCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, PlayerManager playerManager, Player player, String[] args) {
        Island island = islandManager.getIslandPlayerIsOn(player);
        if(island == null) {
            player.sendMessage("du bist auf keiner insel");
            return;
        }

        player.sendMessage("owner: " + island.getOwner());
        player.sendMessage("id: " + island.getId());
    }
}
