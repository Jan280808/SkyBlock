package de.jan.skyblock.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class ComponentSerializer {

    public static Component deserialize(String input) {
        if (input == null) return Component.empty();
        return MiniMessage.miniMessage().deserialize(input);
    }

    public static String format(String input) {
        if (input == null) return "";
        return LegacyComponentSerializer.legacySection().serialize(deserialize(input));
    }

    public static String serialize(Component input) {
        if (input == null) return null;
        return MiniMessage.miniMessage().serialize(input);
    }
}
