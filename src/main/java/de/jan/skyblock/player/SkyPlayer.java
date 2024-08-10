package de.jan.skyblock.player;

import de.jan.skyblock.island.Island;

import java.util.UUID;

public class SkyPlayer {

    private final UUID uuid;
    private final Island island;

    public SkyPlayer(UUID uuid, Island island) {
        this.uuid = uuid;
        this.island = island;
    }
}
