package de.jan.skyblock.event;

import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionEvent implements Listener {

    private final IslandManager islandManager;
    private final PlayerManager playerManager;

    public PlayerConnectionEvent(IslandManager islandManager, PlayerManager playerManager) {
        this.islandManager = islandManager;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
    }
}
