package de.jan.skyblock.command;

import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.island.generator.GeneratorManager;
import de.jan.skyblock.player.PlayerManager;
import de.jan.skyblock.player.SkyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorCommand implements TabExecutor {

    private final PlayerManager playerManager;
    private final GeneratorManager generatorManager;

    public GeneratorCommand(PlayerManager playerManager, GeneratorManager generatorManager) {
        this.playerManager = playerManager;
        this.generatorManager = generatorManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        long start = System.currentTimeMillis(); // Use long for time measurements
        runExperiment();
        long elapsedTime = System.currentTimeMillis() - start;
        SkyBlock.Logger.info("time: " + elapsedTime);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("generate");
    }

    public void runExperiment() {
        Map<Material, Integer> materialCounts = new HashMap<>();
        SkyPlayer skyPlayer = playerManager.getSkyPlayer(Bukkit.getPlayerUniqueId("Jan2808"));
        for(int i = 0; i < 100; i++) {
            Material material =  generatorManager.generateOre(skyPlayer);
            materialCounts.put(material, materialCounts.getOrDefault(material, 0) + 1);
        }
        materialCounts.forEach((material, integer) -> SkyBlock.Logger.info(material.toString() + ": " + integer));
    }
}
