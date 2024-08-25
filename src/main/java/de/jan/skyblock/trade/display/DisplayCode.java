package de.jan.skyblock.trade.display;

import lombok.Getter;

@Getter
public enum DisplayCode {
    MAIN_HAND("[i]");

    private final String string;

    DisplayCode(String string) {
        this.string = string;
    }
}
