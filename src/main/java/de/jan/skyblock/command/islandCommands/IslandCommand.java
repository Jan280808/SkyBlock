package de.jan.skyblock.command.islandCommands;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.command.islandCommands.subcommand.SubCommands;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class IslandCommand implements TabExecutor {

    private final IslandManager islandManager;
    private final PlayerManager playerManager;

    private final Map<String, IslandCommands> subCommandMap;

    public IslandCommand(IslandManager islandManager, PlayerManager playerManager) {
        this.islandManager = islandManager;
        this.playerManager = playerManager;
        this.subCommandMap = new HashMap<>();
        registerSubCommands();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;
        SkyPlayer skyPlayer = this.playerManager.getSkyPlayer(player.getUniqueId());

        if(args.length == 0) {
            if(skyPlayer.teleportToIsland()) return true;
            sendHelpInformation(player);
            return false;
        }

        String string = args[0].toLowerCase();
        IslandCommands islandCommands = subCommandMap.get(string);
        if(islandCommands != null) {
            islandCommands.onCommand(islandManager, skyPlayer, player, args);
            return true;

        } else {
            sendHelpInformation(player);
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return Collections.emptyList();
        if(args.length == 1) return subCommandMap.keySet().stream().toList();
        return Collections.emptyList();
    }

    private void sendHelpInformation(Player player) {
        subCommandMap.forEach((string, islandCommands) -> player.sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize("<gray>/island <" + string + ">"))));
    }

    private void registerSubCommands() {
        Arrays.stream(SubCommands.values()).forEach(subCommands -> this.subCommandMap.put(subCommands.getSubCommand(), subCommands.getIslandCommands()));
    }
}
