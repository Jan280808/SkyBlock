package de.jan.skyblock.npc;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class NPCInteractEvent extends Event implements Cancellable {

    private final Player player;
    private final NPC npc;
    private final InteractType interactType;

    private boolean canceled;

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public NPCInteractEvent(Player player, NPC npc, InteractType interactType) {
        this.player = player;
        this.npc = npc;
        this.interactType = interactType;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        canceled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
