package de.jan.skyblock.player;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerManager {

    private final Map<UUID, SkyPlayer> playerMap;

    public PlayerManager() {
        this.playerMap = new HashMap<>();
    }

    public SkyPlayer getSkyPlayer(UUID uuid) {
        if(playerMap.containsKey(uuid)) return playerMap.get(uuid);
        SkyPlayer skyPlayer = new SkyPlayer(uuid, null);
        this.playerMap.put(uuid, skyPlayer);
        return skyPlayer;
    }
}
