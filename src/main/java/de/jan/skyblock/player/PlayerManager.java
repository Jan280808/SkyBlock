package de.jan.skyblock.player;

import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.stats.StatsManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerManager {

    private final IslandManager islandManager;
    private final Map<UUID, SkyPlayer> playerMap;
    private final StatsManager levelManager;

    public PlayerManager(IslandManager islandManager) {
        this.islandManager = islandManager;
        this.playerMap = new HashMap<>();
        this.levelManager = new StatsManager();
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
