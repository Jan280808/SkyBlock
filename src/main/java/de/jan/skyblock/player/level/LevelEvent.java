package de.jan.skyblock.player.level;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;

public class LevelEvent implements Listener {

    private final PlayerManager playerManager;

    public LevelEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        SkyPlayer skyPlayer = this.playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        Block block = event.getBlock();
        if(!skyPlayer.isOnIsland()) return;
        Arrays.stream(MiningLevel.MiningBlocks.values()).filter(miningBlocks -> block.getType().equals(miningBlocks.getMaterial())).forEach(miningBlocks -> skyPlayer.getMiningLevel().addXP(miningBlocks.getXp()));
    }
}
