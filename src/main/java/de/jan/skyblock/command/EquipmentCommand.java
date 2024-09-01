package de.jan.skyblock.command;

import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.equipment.Equipment;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EquipmentCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;
        Player player = (Player) sender;
        Inventory inventory = Bukkit.createInventory(player, 27, ComponentSerializer.deserialize("Equipment"));
        Arrays.stream(Equipment.values()).forEach(equipment -> inventory.addItem(equipment.getItemStack()));
        player.openInventory(inventory);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("equipment");
    }
}
