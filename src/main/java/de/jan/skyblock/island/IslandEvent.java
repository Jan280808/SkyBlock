package de.jan.skyblock.island;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class IslandEvent implements Listener {

    private final PlayerManager playerManager;

    public IslandEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!event.getEntity().getType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getEntity();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(skyPlayer.isOnIsland()) return;
        event.setCancelled(true);
    }
}
