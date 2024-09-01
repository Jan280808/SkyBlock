package de.jan.skyblock.equipment;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EquipmentEvent implements Listener {

    private final PlayerManager playerManager;

    public EquipmentEvent(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        ItemStack oldStack = event.getOldItem();
        ItemStack newStack = event.getNewItem();

        Arrays.stream(Equipment.values()).filter(equipment -> oldStack.equals(equipment.getItemStack())).forEach(equipment -> equipment.getAbility().deactivate(skyPlayer));
        if(newStack.getType().equals(Material.AIR)) return;
        Arrays.stream(Equipment.values()).filter(equipment -> newStack.equals(equipment.getItemStack())).forEach(equipment -> equipment.getAbility().activate(skyPlayer));
    }

    @EventHandler
    public void onChangeOffHand(PlayerSwapHandItemsEvent event) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(event.getPlayer().getUniqueId());
        ItemStack oldStack = event.getMainHandItem();
        ItemStack newStack = event.getOffHandItem();

        Arrays.stream(Equipment.values()).filter(equipment -> oldStack.equals(equipment.getItemStack())).forEach(equipment -> equipment.getAbility().deactivate(skyPlayer));
        if(newStack.getType().equals(Material.AIR)) return;
        Arrays.stream(Equipment.values()).filter(equipment -> newStack.equals(equipment.getItemStack())).forEach(equipment -> equipment.getAbility().activate(skyPlayer));
    }
}
