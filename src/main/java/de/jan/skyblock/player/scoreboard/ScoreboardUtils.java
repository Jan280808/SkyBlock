package de.jan.skyblock.player.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

public class ScoreboardUtils {

    public void sendSidebar(Player player) {
        try {
            // Create the Scoreboard Objective Packet
            PacketContainer createObjectivePacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);

            // Objective name (field 0) must exist, double-check this field is available
            createObjectivePacket.getStrings().writeSafely(0, "sidebarObjective"); // Objective name

            // Title: Get chat components safely
            WrappedChatComponent title = WrappedChatComponent.fromText("Scoreboard Title");
            createObjectivePacket.getChatComponents().writeSafely(0, title); // Title

            // Set the mode (0 = create, 1 = remove, 2 = update)
            createObjectivePacket.getIntegers().writeSafely(0, 0); // Ensure mode is valid

            // Send the packet to the player
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, createObjectivePacket);

            // Display the sidebar for the player (SCOREBOARD_DISPLAY_OBJECTIVE)
            PacketContainer displayObjectivePacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);

            // Slot 1 = sidebar
            displayObjectivePacket.getIntegers().writeSafely(0, 1); // Sidebar slot

            // Link to the objective name
            displayObjectivePacket.getStrings().writeSafely(0, "sidebarObjective");

            // Send the display packet
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, displayObjectivePacket);

            // Set the scorelines (using a method like setScore)
            setScore(player, "First Line", 10);
            setScore(player, "Second Line", 5);
            setScore(player, "Third Line", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setScore(Player player, String line, int score) {
        try {
            // Create the Scoreboard Score Packet
            PacketContainer scorePacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_SCORE);

            // Ensure the correct objective and score fields are being written
            scorePacket.getStrings().writeSafely(0, line); // The actual line content
            scorePacket.getStrings().writeSafely(1, "sidebarObjective"); // Objective name
            scorePacket.getIntegers().writeSafely(0, score); // The score value

            // Send the packet to the player
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, scorePacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}