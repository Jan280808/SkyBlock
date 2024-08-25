package de.jan.skyblock.player;

import de.jan.skyblock.island.IslandManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerManager {

    private final IslandManager islandManager;
    private final Map<UUID, SkyPlayer> playerMap;

    public PlayerManager(IslandManager islandManager) {
        this.islandManager = islandManager;
        this.playerMap = new HashMap<>();
    }

    public SkyPlayer getSkyPlayer(UUID uuid) {
        if(playerMap.containsKey(uuid)) return playerMap.get(uuid);
        SkyPlayer skyPlayer = new SkyPlayer(uuid, null);
        checkIslandExist(skyPlayer);
        this.playerMap.put(uuid, skyPlayer);
        return skyPlayer;
    }

    private void checkIslandExist(SkyPlayer skyPlayer) {
        islandManager.getIslandList().stream().filter(island -> island.getOwner().equals(skyPlayer.getUuid())).forEach(skyPlayer::setIsland);
    }
}
