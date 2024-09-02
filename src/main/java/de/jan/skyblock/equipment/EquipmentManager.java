package de.jan.skyblock.equipment;

import de.jan.skyblock.equipment.item.*;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class EquipmentManager {

    private final Map<ItemStack, Equipment> equipmentMap;
    private final PlayerManager playerManager;

    public EquipmentManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.equipmentMap = Stream.of(
                new Coupon(),
                new HermesBoots(),
                new MinersHelmet(),
                new MinersPickaxe(),
                new NightVisionDevice()
        ).collect(Collectors.toMap(Equipment::itemStack, equipment -> equipment));
    }

    public void handleEquipmentAbility(Player player, ItemStack oldStack, ItemStack newStack, Equipment.Type type) {
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(player.getUniqueId());
        if(oldStack != null) {
            Equipment equipment = equipmentMap.get(oldStack);
            if(equipment != null && equipment.type() == type) equipment.ability().deactivate(skyPlayer);
        }

        if(newStack != null && !newStack.getType().equals(Material.AIR)) {
            Equipment equipment = equipmentMap.get(newStack);
            if(equipment != null && equipment.type() == type) equipment.ability().activate(skyPlayer);
        }
    }

    private Equipment getEquipment(ItemStack itemStack) {
        if(equipmentMap.containsKey(itemStack)) return equipmentMap.get(itemStack);
        return null;
    }
}
