package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
public class Island {

    private int id;
    private final World world;
    private final UUID owner;
    private final Location center;
    private final int maxRadius;
    private final String createDate;

    public Island(int id, World world, UUID owner, Location center) {
        this.id = id;
        this.world = world;
        this.owner = owner;
        this.center = center;
        this.maxRadius = 50;
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        playBoarder();
    }

    public void teleport(SkyPlayer skyPlayer) {
        skyPlayer.getPlayer().teleport(center);
    }

    //not final way to display the boarder of an island
    private void playBoarder() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.instance, () -> {
            double angleStep = 2 * Math.PI / 360;

            for(int i = 0; i < 360; i++) {
                double angle = i * angleStep;
                double xOffset = maxRadius * Math.cos(angle);
                double zOffset = maxRadius * Math.sin(angle);
                Location particleLocation = center.clone().add(xOffset, 0, zOffset);
                world.spawnParticle(Particle.HAPPY_VILLAGER, particleLocation, 1);
            }
        }, 0, 20);
    }
}
