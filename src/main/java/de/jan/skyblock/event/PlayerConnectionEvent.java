package de.jan.skyblock.event;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.spawn.SpawnIsland;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionEvent implements Listener {

    private final PlayerManager playerManager;
    private final SpawnIsland spawnIsland;

    public PlayerConnectionEvent(PlayerManager playerManager, SpawnIsland spawnIsland) {
        this.playerManager = playerManager;
        this.spawnIsland = spawnIsland;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        spawnIsland.teleport(skyPlayer);
    }
}
