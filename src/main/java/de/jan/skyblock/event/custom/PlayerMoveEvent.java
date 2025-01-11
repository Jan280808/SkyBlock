package de.jan.skyblock.event.custom;

import de.jan.skyblock.player.PlayerManager;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerMoveEvent extends Event {

    private final PlayerManager playerManager;

    @Getter
    public static final HandlerList handlerList = new HandlerList();

    public PlayerMoveEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
