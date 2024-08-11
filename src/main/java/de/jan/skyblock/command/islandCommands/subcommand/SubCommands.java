package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.command.islandCommands.IslandCommands;
import lombok.Getter;

@Getter
public enum SubCommands {
    CREATE_ISLAND(new CreateIslandCommand(), "create"),
    INFO(new InfoCommand(), "info");

    private final IslandCommands islandCommands;
    private final String subCommand;

    SubCommands(IslandCommands islandCommands, String subCommand) {
        this.islandCommands = islandCommands;
        this.subCommand = subCommand;
    }
}
