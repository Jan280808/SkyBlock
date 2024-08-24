package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.builder.ItemBuilder;
import de.jan.skyblock.command.islandCommands.IslandCommands;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.island.IslandManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class DebugCommand implements IslandCommands {

    @Override
    public void onCommand(IslandManager islandManager, SkyPlayer skyPlayer, Player player, String[] args) {
        if(!player.hasPermission("xxx.xxx")) return;
        Inventory inventory = Bukkit.createInventory(null, 54, ComponentSerializer.deserialize("islands"));
        islandManager.getIslandList().forEach(island -> {
            String ownerName = Objects.requireNonNull(Bukkit.getPlayer(island.getOwner())).getName();
            inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkull("Island").setDisplayName("island").setLore("<gray>id: " + island.getId(), "<gray>World: " + island.getWorld().getName(), "<gray>Owner: " + ownerName, "<gray>Location: " + skyPlayer.getCurrentLocation().locationName()).build());
        });
        player.openInventory(inventory);
    }
}
