package de.jan.skyblock;

import de.jan.skyblock.command.IslandCommand;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public final class SkyBlock extends JavaPlugin {

    public static SkyBlock instance;
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
    }
}
