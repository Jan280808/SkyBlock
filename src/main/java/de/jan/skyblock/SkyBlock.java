package de.jan.skyblock;

import de.jan.skyblock.command.IslandCommand;
import de.jan.skyblock.command.WorldCommand;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import lombok.Getter;
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
    private PlayerManager playerManager;
    private IslandManager islandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        islandManager = new IslandManager();
        playerManager = new PlayerManager();
        registerListener(Bukkit.getPluginManager());
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListener(PluginManager pluginManager) {

    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("island")).setExecutor(new IslandCommand(islandManager));
        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand());
    }
}
