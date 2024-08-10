package de.jan.skyblock.event;

import de.jan.skyblock.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionEvent implements Listener {

    private final PlayerManager playerManager;

    public PlayerConnectionEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.playerManager.loadSkyPlayer(player.getUniqueId());
    }
}
