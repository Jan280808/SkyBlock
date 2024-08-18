package de.jan.skyblock.event;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class EnterPortalEvent implements Listener {

    private final PlayerManager playerManager;

    public EnterPortalEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onChangeWorld(PlayerPortalEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) return;
        if(skyPlayer.teleportToIsland()) return;
        player.setVelocity(new Vector(1, 2, 0));
    }
}
