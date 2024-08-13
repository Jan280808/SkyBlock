package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;

public class CreateIslandCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, SkyPlayer skyPlayer, Player player, String[] args) {
        if(skyPlayer.getIsland() != null) {
            player.sendMessage("du hast bereits eine insel :)");
            return;
        }
        islandManager.createNewIsland(skyPlayer, Category.ISLAND_NORMAL);
    }
}
