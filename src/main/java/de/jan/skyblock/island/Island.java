package de.jan.skyblock.island;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import de.jan.skyblock.location.Locations;
import de.jan.skyblock.player.SkyPlayer;
import de.jan.skyblock.sound.SoundManager;
import de.jan.skyblock.sound.Sounds;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
public class Island implements Locations {

    private final int id;
    private final UUID owner;
    private final Location center;
    private final List<UUID> members;
    private final IslandLevel islandLevel;
    private final String createDate;

    private World world;

    @Setter
    private boolean showCubeActive;

    @Setter
    private boolean worldLoaded;

    //create completely new island
    public Island(int id, @NotNull UUID owner, @NotNull Location center)  {
        this.id = id;
        this.owner = owner;
        this.center = center;
        this.members = new ArrayList<>();
        this.islandLevel = new IslandLevel(this);
        this.createDate = new SimpleDateFormat("dd,MM,yy").format(new Date());
        this.showCubeActive = false;
    }

    //load island from .json
    public Island(int id, @NotNull UUID owner, @NotNull Location center, @NotNull List<UUID> members,  @NotNull String islandLevel,  @NotNull String createDate) {
        this.id = id;
        this.owner = owner;
        this.center = center;
        this.members = members;
        this.islandLevel = new IslandLevel(this, islandLevel);
        this.createDate = createDate;
        this.showCubeActive = false;
    }

    public void teleport(SkyPlayer skyPlayer) {
        Player player = skyPlayer.getPlayer();
        if(islandWorld() == null) {
            player.sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize("<red>Your Island world could not be loaded")));
            player.sendMessage(SkyBlock.Prefix.append(ComponentSerializer.deserialize("<red>Please contact the support")));
            SoundManager.playSound(Sounds.ERROR, skyPlayer);
            return;
        }
        player.teleport(center);
        skyPlayer.setCurrentLocation(this);
    }

    @Override
    public String locationName() {
        return Objects.requireNonNull(Bukkit.getPlayer(owner)).getName() + "-island";
    }

    public World islandWorld() {
        return Bukkit.createWorld(new WorldCreator("islandWorld"+id));
    }
}
