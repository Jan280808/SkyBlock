package de.jan.skyblock.npc;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NPCManager {

    private final List<NPC> npcList;

    public NPCManager() {
        this.npcList = new ArrayList<>();
    }

    public NPC createNPC(String displayName, EntityType entityType, Location location, Type type, NPCInteractHandler interactionHandler) {
        NPC npc = new NPC(displayName, entityType, location, type, interactionHandler);
        this.npcList.add(npc);
        return npc;
    }

    public void despawnAllNPC() {
        this.npcList.forEach(NPC::despawn);
    }
}