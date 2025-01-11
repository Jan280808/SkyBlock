package de.jan.skyblock.event.custom;

import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class SkyPlayerQuitIslandEvent extends Event {

    private final SkyPlayer skyPlayer;

    public static final HandlerList handlerList = new HandlerList();

    public SkyPlayerQuitIslandEvent(SkyPlayer skyPlayer) {
        this.skyPlayer = skyPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
