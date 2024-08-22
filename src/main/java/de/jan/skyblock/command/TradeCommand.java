package de.jan.skyblock.command;

import de.jan.skyblock.trade.TradeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TradeCommand implements TabExecutor {

    private final TradeManager tradeManager;

    public TradeCommand(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage("/trade send/accept/deny <player>");
            return false;
        }

        if(args.length == 2) {
            player.sendMessage("debug1");
            String playerName = args[1];
            UUID uuid = Bukkit.getPlayerUniqueId(playerName);
            player.sendMessage("string: " + playerName);
            player.sendMessage("stringID: " + uuid);
            if(uuid == null) return false;
            Player recipient = Bukkit.getPlayer(uuid);
            if(recipient == null) return false;
            player.sendMessage("debug2");
            player.sendMessage("player: " + recipient);
            switch(args[0]) {
                case "send" -> {
                    tradeManager.sendRequest(player, recipient);
                    player.sendMessage("send");
                    return true;
                }
                case "accept" -> {
                    tradeManager.acceptRequest(player, recipient);
                    player.sendMessage("accept");
                    return true;
                }
                case "deny" -> {
                    tradeManager.denyRequest(player, recipient);
                    player.sendMessage("deny");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return null;
        if(args.length == 1) return List.of("send", "accept", "deny");
        if(args.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return List.of("");
    }
}
