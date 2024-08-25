package de.jan.skyblock;

import de.jan.skyblock.command.DisplayCommand;
import de.jan.skyblock.command.SpawnCommand;
import de.jan.skyblock.command.TradeCommand;
import de.jan.skyblock.command.WorldCommand;
import de.jan.skyblock.command.islandCommands.IslandCommand;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.event.EnterPortalEvent;
import de.jan.skyblock.event.PlayerConnectionEvent;
import de.jan.skyblock.event.PlayerDamageEvent;
import de.jan.skyblock.spawn.SpawnIslandEvent;
import de.jan.skyblock.npc.NPCInteractionListener;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.npc.NPCManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.spawn.SpawnIsland;
import de.jan.skyblock.event.PlayerInventoryEvent;
import de.jan.skyblock.trade.TradeEvent;
import de.jan.skyblock.trade.TradeManager;
import de.jan.skyblock.trade.display.DisplayEvent;
import lombok.Getter;
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
    private NPCManager npcManager;
    private IslandManager islandManager;
    private SpawnIsland spawnIsland;
    private TradeManager tradeManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        float start = System.currentTimeMillis();
        instance = this;
        islandManager = new IslandManager();
        npcManager = new NPCManager();
        playerManager = new PlayerManager(islandManager);
        spawnIsland = new SpawnIsland();
        tradeManager = new TradeManager();
        registerListener(Bukkit.getPluginManager());
        registerCommands();
        float time = start-System.currentTimeMillis();
        Logger.info("SkyBlock finish in: {}", time);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        npcManager.despawnAllNPC();
    }

    private void registerListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new PlayerConnectionEvent(playerManager, spawnIsland), this);
        pluginManager.registerEvents(new NPCInteractionListener(npcManager), this);
        pluginManager.registerEvents(new EnterPortalEvent(playerManager), this);
        pluginManager.registerEvents(new PlayerInventoryEvent(spawnIsland.getShopManager()), this);
        pluginManager.registerEvents(new TradeEvent(tradeManager), this);
        pluginManager.registerEvents(new SpawnIslandEvent(playerManager, spawnIsland), this);
        pluginManager.registerEvents(new PlayerDamageEvent(playerManager), this);
        pluginManager.registerEvents(new DisplayEvent(tradeManager), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("island")).setExecutor(new IslandCommand(islandManager, playerManager));
        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(playerManager, spawnIsland));
        Objects.requireNonNull(getCommand("trade")).setExecutor(new TradeCommand(tradeManager));
        Objects.requireNonNull(getCommand("display")).setExecutor(new DisplayCommand(tradeManager));
    }
}
