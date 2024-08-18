package de.jan.skyblock.npc;

import de.jan.skyblock.component.ComponentSerializer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Objects;

@Getter
public class NPC {

    private final Component displayName;
    private final EntityType entityType;
    private final Location location;
    private final Type type;
    private final NPCInteractHandler interactionHandler;

    private LivingEntity livingEntity;

    public NPC(String displayName, EntityType entityType, Location location, Type type, NPCInteractHandler interactionHandler) {
        this.displayName = ComponentSerializer.deserialize(displayName);
        this.entityType = entityType;
        this.location = location;
        this.type = type;
        this.interactionHandler = interactionHandler;
        spawn();
    }

    public void interact(Player player, InteractType interactType) {
        if(interactionHandler == null) return;
        NPCInteractEvent event = new NPCInteractEvent(player, this, interactType);
        interactionHandler.handleInteraction(event);
    }

    public void despawn() {
        if(livingEntity == null) return;
        livingEntity.remove();
    }

    private void spawn() {
        if(livingEntity != null) return;
        livingEntity = (LivingEntity) Objects.requireNonNull(location.getWorld()).spawnEntity(location, entityType);
        livingEntity.setAI(false);
        livingEntity.setCustomNameVisible(true);
        livingEntity.customName(displayName.append(Component.text(type.name())));
    }
}