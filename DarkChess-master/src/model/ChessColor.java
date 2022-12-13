package model;

import java.awt.*;
import java.util.Objects;

/**
 * 这个类主要用于包装Color对象，用于Chess游戏使用。
 */
public enum ChessColor {
    BLACK("Black", Color.BLACK), RED("Red", Color.RED), NONE("No Player", Color.WHITE);
    private final String name;
    private final Color color;
    ChessColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return Objects.equals(name, "Red") ? "红" : "黑";
    }
    public Color getColor() {
        return color;
    }
}
