package de.jan.skyblock.island.generator;

import de.jan.skyblock.island.Island;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class GeneratorEvent implements Listener {

    private final IslandManager islandManager;
    private final GeneratorManager generatorManager;
    private final PlayerManager playerManager;

    public GeneratorEvent(IslandManager islandManager, PlayerManager playerManager) {
        this.islandManager = islandManager;
        this.generatorManager = islandManager.getGeneratorManager();
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        if(!event.getNewState().getType().equals(Material.COBBLESTONE)) return;
        Block block = event.getBlock();

        Island island = islandManager.getIslandFromLocation(block.getLocation());
        if(island == null) return;

        SkyPlayer skyPlayer = playerManager.getSkyPlayer(island.getOwner());
        if(skyPlayer == null) return;

        block.setType(generatorManager.generateOre(skyPlayer));
        event.setCancelled(true);
    }
}
