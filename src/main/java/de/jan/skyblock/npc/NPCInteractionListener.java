package de.jan.skyblock.npc;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCInteractionListener implements Listener {

    private final NPCManager npcManager;

    public NPCInteractionListener(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickEntity = event.getRightClicked();
        npcManager.getNpcList().stream().filter(npc -> npc.getLivingEntity() != null && npc.getEntityType().equals(clickEntity.getType()) && clickEntity.customName() != null && npc.getDisplayName().equals(clickEntity.customName()) && npc.getLocation().equals(clickEntity.getLocation())).forEach(npc -> {
            InteractType interactType;
            if(player.isSneaking()) interactType = InteractType.SHIFT_RIGHT;
            else interactType = InteractType.RIGHT;
            npc.interact(player, interactType);
            event.setCancelled(true);
        });
    }

    @EventHandler
    public void onHitEntity(EntityDamageByEntityEvent event) {
        if(!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getDamager();
        Entity damagedEntity = event.getEntity();

        npcManager.getNpcList().stream().filter(npc -> npc.getLivingEntity() != null && npc.getEntityType().equals(damagedEntity.getType()) && damagedEntity.customName() != null && npc.getDisplayName().equals(damagedEntity.customName()) && npc.getLocation().equals(damagedEntity.getLocation())).forEach(npc -> {
            InteractType interactType;
            if(player.isSneaking()) interactType = InteractType.SHIFT_LEFT;
            else interactType = InteractType.LEFT;
            npc.interact(player, interactType);
            event.setCancelled(true);
        });
    }
}
