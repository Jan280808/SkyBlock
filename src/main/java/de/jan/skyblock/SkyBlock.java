package de.jan.skyblock;

import de.jan.skyblock.command.*;
import de.jan.skyblock.command.islandCommands.IslandCommand;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.EquipmentEvent;
import de.jan.skyblock.equipment.EquipmentManager;
import de.jan.skyblock.event.*;
import de.jan.skyblock.event.custom.PlayerMoveEvent;
import de.jan.skyblock.island.IslandEvent;
import de.jan.skyblock.island.generator.GeneratorEvent;
import de.jan.skyblock.player.stats.StatsEvent;
import de.jan.skyblock.spawn.SpawnIslandEvent;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.spawn.SpawnIsland;
import de.jan.skyblock.spawn.island.WardenEvent;
import de.jan.skyblock.spawn.pinata.PinataEvent;
import de.jan.skyblock.trade.TradeEvent;
import de.jan.skyblock.trade.TradeManager;
import de.jan.skyblock.trade.display.DisplayEvent;
import lombok.Getter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Getter
public final class SkyBlock extends JavaPlugin {

    public static SkyBlock instance;
    public static final Logger Logger = LoggerFactory.getLogger("SkyBlock");
    public static Component Prefix = ComponentSerializer.deserialize("<gradient:green:blue>SkyBlock <dark_gray>● ");
    private PlayerManager playerManager;
    private IslandManager islandManager;
    private SpawnIsland spawnIsland;
    private TradeManager tradeManager;
    private EquipmentManager equipmentManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        float start = System.currentTimeMillis();
        instance = this;
        islandManager = new IslandManager();
        playerManager = new PlayerManager(islandManager);
        spawnIsland = new SpawnIsland(islandManager);
        tradeManager = new TradeManager();
        equipmentManager = new EquipmentManager(playerManager);
        registerListener(Bukkit.getPluginManager());
        registerCommands();
        float time = System.currentTimeMillis() - start;
        Logger.info("SkyBlock finish in: {}ms", time);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CitizensAPI.getNPCRegistries().forEach(npc -> npc.despawnNPCs(DespawnReason.RELOAD));
        spawnIsland.getPinata().removePinata();
    }

    private void registerListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new PlayerConnectionEvent(playerManager, spawnIsland), this);
        pluginManager.registerEvents(new EnterPortalEvent(playerManager), this);
        pluginManager.registerEvents(new PlayerInventoryEvent(spawnIsland.getShopManager()), this);
        pluginManager.registerEvents(new TradeEvent(tradeManager), this);
        pluginManager.registerEvents(new SpawnIslandEvent(playerManager, spawnIsland), this);
        pluginManager.registerEvents(new PlayerDamageEvent(playerManager), this);
        pluginManager.registerEvents(new DisplayEvent(tradeManager), this);
        pluginManager.registerEvents(new StatsEvent(playerManager), this);
        pluginManager.registerEvents(new EquipmentEvent(equipmentManager), this);
        pluginManager.registerEvents(new GeneratorEvent(islandManager, playerManager), this);
        pluginManager.registerEvents(new PinataEvent(spawnIsland), this);
        pluginManager.registerEvents(new WardenEvent(playerManager, spawnIsland), this);
        pluginManager.registerEvents(new IslandEvent(playerManager), this);

        //trigger move scheduler
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> pluginManager.callEvent(new PlayerMoveEvent(playerManager)), 0, 5);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("island")).setExecutor(new IslandCommand(islandManager, playerManager));
        Objects.requireNonNull(getCommand("is")).setExecutor(new IslandCommand(islandManager, playerManager));
        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(playerManager, spawnIsland));
        Objects.requireNonNull(getCommand("trade")).setExecutor(new TradeCommand(tradeManager));
        Objects.requireNonNull(getCommand("display")).setExecutor(new DisplayCommand(tradeManager));
        Objects.requireNonNull(getCommand("stats")).setExecutor(new StatsCommand(playerManager));
        Objects.requireNonNull(getCommand("s")).setExecutor(new StatsCommand(playerManager));
        Objects.requireNonNull(getCommand("equipment")).setExecutor(new EquipmentCommand(equipmentManager));
        Objects.requireNonNull(getCommand("generator")).setExecutor(new GeneratorCommand(playerManager, islandManager.getGeneratorManager()));
        Objects.requireNonNull(getCommand("pinata")).setExecutor(new PinataCommand());
    }
}
