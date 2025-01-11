package de.jan.skyblock.event.custom;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class SkyPlayerEnterIslandEvent extends Event {

    private final SkyPlayer skyPlayer;
    private final Island island;

    public static final HandlerList handlerList = new HandlerList();

    public SkyPlayerEnterIslandEvent(SkyPlayer skyPlayer, Island island) {
        this.skyPlayer = skyPlayer;
        this.island = island;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
