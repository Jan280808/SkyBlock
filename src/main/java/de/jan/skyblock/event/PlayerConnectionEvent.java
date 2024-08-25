package de.jan.skyblock.event;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.spawn.SpawnIsland;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        event.joinMessage(ComponentSerializer.deserialize("<green>+ " + player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        spawnIsland.teleport(skyPlayer);
        event.quitMessage(ComponentSerializer.deserialize("<red>- " + player.getName()));
    }
}
