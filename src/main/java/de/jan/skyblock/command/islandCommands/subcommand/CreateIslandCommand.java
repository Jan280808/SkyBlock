package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.island.schematic.Category;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;

public class CreateIslandCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, SkyPlayer skyPlayer, Player player, String[] args) {
        if(skyPlayer.getIsland() != null) {
            player.sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize("<gray>Du hast bereits eine Insel erstellt")));
            return;
        }
        islandManager.createIsland(skyPlayer, Category.ISLAND_NORMAL);
    }
}
