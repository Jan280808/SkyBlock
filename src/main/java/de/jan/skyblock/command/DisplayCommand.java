package de.jan.skyblock.command;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import de.jan.skyblock.trade.TradeManager;
import de.jan.skyblock.trade.display.DisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class DisplayCommand implements TabExecutor {

    private final DisplayManager displayManager;

    public DisplayCommand(TradeManager tradeManager) {
        this.displayManager = tradeManager.getDisplayManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;

        if(args.length != 1) {
            player.sendMessage(TradeManager.Prefix.append(ComponentSerializer.deserialize("Nutze /display <name>")));
            return false;
        }
        String string = args[0];
        Player displayCreator = getPlayerByName(player, string);
        if(displayCreator == null) return false;
        this.displayManager.openDisplay(player, displayCreator);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return displayManager.getDisplayItemMap().keySet().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).filter(Player::isOnline).map(Player::getName).collect(Collectors.toList());
    }

    private Player getPlayerByName(Player player, String playerName) {
        UUID uuid = Bukkit.getPlayerUniqueId(playerName);
        Player wantedPlayer = uuid != null ? Bukkit.getPlayer(uuid) : null;
        if(wantedPlayer == null || !wantedPlayer.isOnline()) {
            player.sendMessage(TradeManager.Prefix.append(ComponentSerializer.deserialize("<red>Der Spieler <gray>" + playerName + " <red>wurde nicht gefunden")));
            SoundManager.playSound(Sounds.ERROR, player);
            return null;
        }
        return wantedPlayer;
    }
}
