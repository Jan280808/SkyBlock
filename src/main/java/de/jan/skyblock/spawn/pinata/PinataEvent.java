package de.jan.skyblock.spawn.pinata;

import de.jan.skyblock.spawn.SpawnIsland;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class PinataEvent implements Listener {

    private final Pinata pinata;

    public PinataEvent(SpawnIsland spawnIsland) {
        this.pinata = spawnIsland.getPinata();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!entity.equals(pinata.getSheep())) return;
        pinata.takeDamage(event.getDamage());
        event.setDamage(0);
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;

        Entity entity = event.getEntity();
        if(!entity.equals(pinata.getSheep())) return;

        pinata.takeDamage(event.getDamage());
        event.setDamage(0);
    }

    @EventHandler
    public void onCheer(PlayerShearEntityEvent event) {
        event.getEntity().remove();
    }
}
