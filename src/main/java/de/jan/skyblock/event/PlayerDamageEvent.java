package de.jan.skyblock.event;

import de.jan.skyblock.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDamageEvent implements Listener {

    private final PlayerManager playerManager;

    public PlayerDamageEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage("du wärst jetzt tot...");
    }
}
