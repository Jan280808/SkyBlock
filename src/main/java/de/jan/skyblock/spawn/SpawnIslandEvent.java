package de.jan.skyblock.spawn;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class SpawnIslandEvent implements Listener {

    private final PlayerManager playerManager;
    private final SpawnIsland spawnIsland;

    public SpawnIslandEvent(PlayerManager playerManager, SpawnIsland spawnIsland) {
        this.playerManager = playerManager;
        this.spawnIsland = spawnIsland;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!skyPlayer.getCurrentLocation().equals(spawnIsland)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!skyPlayer.getCurrentLocation().equals(spawnIsland)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!skyPlayer.getCurrentLocation().equals(spawnIsland)) return;
        if(event.getItem() != null) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!event.getEntityType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getEntity();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!skyPlayer.getCurrentLocation().equals(spawnIsland)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        World world =  event.getWorld();
        if(!world.equals(spawnIsland.getLocation().getWorld())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        World world = event.getLocation().getWorld();
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        if(reason.equals(CreatureSpawnEvent.SpawnReason.COMMAND) || reason.equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if(!world.equals(spawnIsland.getWorld())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(!skyPlayer.getCurrentLocation().equals(spawnIsland)) return;
        event.setCancelled(true);
    }
}
