package de.jan.skyblock.spawn.pinata;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

@Getter @Setter
public class Pinata {

    private final Location location;
    private final Sheep sheep;
    private final TextDisplay textDisplay;
    private double health = 500;

    public Pinata(Location spawnLocation) {
        this.location = spawnLocation.toCenterLocation();
        this.sheep = (Sheep) location.getWorld().spawnEntity(location.subtract(0, 0.5, 0), EntityType.SHEEP);
        this.textDisplay = (TextDisplay) location.getWorld().spawnEntity(location.clone().add(0, 2.4, 0), EntityType.TEXT_DISPLAY);
        setupEntity();
        updateTextDisplay();
    }

    public void takeDamage(double damage) {
        health = health - damage;
        health = Math.ceil(health * 100) / 100.0;

        if(health <= 0) {
            open();
            return;
        }
        updateTextDisplay();
    }

    public void removePinata() {
        sheep.remove();
        textDisplay.remove();
    }

    private void open() {
        textDisplay.setVisibleByDefault(false);
        for(int i = 0; i < 5; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.instance, () -> {
                Item item = (Item) location.getWorld().spawnEntity(location, EntityType.ITEM);
                item.setItemStack(new ItemStack(Material.values()[new Random().nextInt(Material.values().length)]));
                double x = -1 + 2 * new Random().nextDouble();
                double y = 0.5 + new Random().nextDouble();
                double z = -1 + 2 * new Random().nextDouble();
                item.setVelocity(new Vector(x, y, z).normalize().multiply(0.8));
                item.setGlowing(true);
            }, 8*i);
        }
    }

    private void updateTextDisplay() {
        textDisplay.text(ComponentSerializer.deserialize("<gray>Schlag mich \n <red>Leben: <gray>" + health));
    }

    @SuppressWarnings("deprecation")
    private void setupEntity() {
        sheep.setAI(false);
        sheep.setInvulnerable(true);
        sheep.setCustomName("jeb_");
        sheep.setCustomNameVisible(false);

        textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
        textDisplay.setSeeThrough(true);
        textDisplay.setBillboard(Display.Billboard.FIXED);
    }
}
