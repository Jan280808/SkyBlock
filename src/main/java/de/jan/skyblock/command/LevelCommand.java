package de.jan.skyblock.command;

import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.player.level.LevelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LevelCommand implements TabExecutor {

    private final PlayerManager playerManager;
    private final LevelManager levelManager;

    public LevelCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.levelManager = playerManager.getLevelManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        levelManager.createInventory(skyPlayer);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("level");
    }
}
