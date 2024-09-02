package de.jan.skyblock.equipment;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class EquipmentEvent implements Listener {

    private final EquipmentManager equipmentManager;

    public EquipmentEvent(EquipmentManager equipmentManager) {
        this.equipmentManager = equipmentManager;
    }

    @EventHandler
    public void onChangeArmor(PlayerArmorChangeEvent event) {
        equipmentManager.handleEquipmentAbility(event.getPlayer(), event.getOldItem(), event.getNewItem(), Equipment.Type.EQUIPMENT);
    }

    @EventHandler
    public void onChangeOffHand(PlayerSwapHandItemsEvent event) {
        equipmentManager.handleEquipmentAbility(event.getPlayer(), event.getMainHandItem(), event.getOffHandItem(), Equipment.Type.OFF_HAND);
    }

    @EventHandler
    public void onChangeMainHand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack oldStack = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newStack = player.getInventory().getItem(event.getNewSlot());
        equipmentManager.handleEquipmentAbility(player, oldStack, newStack, Equipment.Type.MAIN_HAND);
    }
}
