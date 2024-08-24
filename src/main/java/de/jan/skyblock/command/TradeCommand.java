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
        Player requester = (Player) sender;

        if(args.length == 0) {
            requester.sendMessage("/trade send/accept/deny <player>");
            return false;
        }

        if(args.length == 2) {
            String playerName = args[1];
            Player recipient = getPlayerByName(requester, playerName);
            if(recipient == null) return false;
            switch(args[0]) {
                case "send" -> {
                    tradeManager.sendRequest(requester, recipient);
                    return true;
                }
                case "accept" -> {
                    tradeManager.acceptRequest(requester, recipient);
                    return true;
                }
                case "deny" -> {
                    tradeManager.denyRequest(requester, recipient);
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
        if(args.length == 2) return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> !name.equals(sender.getName())).collect(Collectors.toList());
        return List.of("");
    }

    private Player getPlayerByName(Player requester, String playerName) {
        UUID uuid = Bukkit.getPlayerUniqueId(playerName);
        Player recipient = uuid != null ? Bukkit.getPlayer(uuid) : null;
        if(recipient == null || !recipient.isOnline()) {
            requester.sendMessage("Der Spieler " + playerName + " wurde nicht gefunden");
            return null;
        }
        if(requester.equals(recipient)) {
            requester.sendMessage("Du kannst nicht mit dir selbst handeln");
            return null;
        }
        return recipient;
    }
}
