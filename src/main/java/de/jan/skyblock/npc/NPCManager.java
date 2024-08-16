package de.jan.skyblock.npc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NPCManager {

    private final List<NPC> npcList;

    public NPCManager() {
        this.npcList = new ArrayList<>();
    }

    public void despawnAllNPC() {
        this.npcList.forEach(NPC::despawn);
    }

    public void registerNPC(NPC npc) {
        this.npcList.add(npc);
    }
}
