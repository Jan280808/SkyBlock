package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import org.bukkit.entity.Player;

public class BoarderCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, PlayerManager playerManager, SkyPlayer skyPlayer, Player player, String[] args) {
        Island island = skyPlayer.getIsland();
        island.setShowCubeActive(!island.isShowCubeActive());
        String message = island.isShowCubeActive() ? "Deine IslandBorder wird dir nun angezeigt" : "Deine IslandBorder wird dir nun nicht angezeigt";
        player.sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize(message)));
        SoundManager.playSound(Sounds.SUCCESSES, player);
    }
}
