package de.jan.skyblock.command.islandCommands.subcommand;

import de.jan.skyblock.command.islandCommands.IslandCommands;
import lombok.Getter;

@Getter
public enum SubCommands {
    CREATE_ISLAND(new CreateIslandCommand(), "create", true),
    INFO(new InfoCommand(), "info", true),
    DEBUG(new DebugCommand(), "debug", false);

    private final IslandCommands islandCommands;
    private final String subCommand;
    private final boolean displayCommand;

    SubCommands(IslandCommands islandCommands, String subCommand, Boolean displayCommand) {
        this.islandCommands = islandCommands;
        this.subCommand = subCommand;
        this.displayCommand = displayCommand;
    }
}
