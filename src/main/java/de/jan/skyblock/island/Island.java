package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import lombok.Getter;
import org.bukkit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
public class Island implements Locations {

    private final int id;
    private final String worldName;
    private final UUID owner;
    private final Location center;
    private final int maxRadius;
    private final String createDate;

    private World world;

    public Island(int id, World world, UUID owner, Location center) {
        this.id = id;
        this.worldName = world.getName();
        this.world = world;
        this.owner = owner;
        this.center = center;
        this.maxRadius = 50;
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        playBoarder();
    }

    public Island(int id, String worldName, UUID owner, Location center) {
        this.id = id;
        this.worldName = worldName;
        this.owner = owner;
        this.center = center;
        this.maxRadius = 50;
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        playBoarder();
    }

    public Island(int id, String worldName, UUID owner, Location center, String createDate) {
        this.id = id;
        this.worldName = worldName;
        this.owner = owner;
        this.center = center;
        this.maxRadius = 50;
        this.createDate = createDate;
        //playBoarder();
    }

    public void teleport(SkyPlayer skyPlayer) {
        loadWorld();
        skyPlayer.getPlayer().teleport(center);
        skyPlayer.setCurrentLocation(this);
    }

    private void loadWorld() {
        if(world != null) return;
        world = Bukkit.createWorld(new WorldCreator(worldName));
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

    @Override
    public String locationName() {
        return owner.toString();
    }
}
