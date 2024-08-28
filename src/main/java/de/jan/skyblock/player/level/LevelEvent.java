package de.jan.skyblock.player.level;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.level.type.FishingLevel;
import de.jan.skyblock.player.level.type.KillEntityLevel;
import de.jan.skyblock.player.level.type.LumberJackLevel;
import de.jan.skyblock.player.level.type.MiningLevel;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.*;

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

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity.getKiller() == null) return;
        SkyPlayer skyPlayer = this.playerManager.getSkyPlayer(entity.getKiller().getUniqueId());
        if(!skyPlayer.isOnIsland()) return;
        Arrays.stream(KillEntityLevel.KillingEntity.values()).filter(killingEntity -> entity.getType().equals(killingEntity.getEntityType())).forEach(killingEntity -> skyPlayer.getKillEntity().addXP(killingEntity.getXp()));
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        SkyPlayer skyPlayer = this.playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(!skyPlayer.isOnIsland()) return;

        if(event.getCaught() == null) {
            if(!event.getHook().isInOpenWater()) return;
            skyPlayer.getFishingLevel().addXP(0.01);
            return;
        }

        PlayerFishEvent.State state = event.getState();
        Arrays.stream(FishingLevel.FishingOffer.values()).filter(fishingOffer -> state.equals(fishingOffer.getState())).forEach(fishingOffer -> skyPlayer.getFishingLevel().addXP(fishingOffer.getXp()));
    }

    private final List<Block> naturallyGeneratedWood = new ArrayList<>();

    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        event.getBlocks().forEach(blockState -> Arrays.stream(LumberJackLevel.Wood.values()).filter(wood -> blockState.getType().equals(wood.getMaterial())).forEach(wood -> naturallyGeneratedWood.add(blockState.getBlock())));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        SkyPlayer skyPlayer = this.playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        if(!skyPlayer.isOnIsland()) return;

        Block block = event.getBlock();
        if(!naturallyGeneratedWood.contains(block)) return;
        skyPlayer.getLumberJackLevel().addXP(1);
        naturallyGeneratedWood.remove(block);
    }
}
