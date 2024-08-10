package de.jan.skyblock.command;

import de.jan.skyblock.island.IslandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IslandCommand implements TabExecutor {

    private final IslandManager islandManager;

    public IslandCommand(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;
        if(!player.hasPermission("")) return false;
        islandManager.createIsland(player);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
