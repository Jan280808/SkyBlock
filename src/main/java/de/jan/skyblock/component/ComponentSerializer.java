package de.jan.skyblock.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class ComponentSerializer {

    static MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component deserialize(String input) {
        if(input == null) return Component.empty();
        return miniMessage.deserialize(input).decoration(TextDecoration.ITALIC, false);
    }

    public static String serialize(Component input) {
        if(input == null) return null;
        return miniMessage.serialize(input);
    }
}
