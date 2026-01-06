package io;

import java.awt.Color;

public class ColorsParser {

    public Color colorFromString(String s) {
        s = s.trim();

        // expected: color(red) or color(RGB(1,2,3))
        if (!s.startsWith("color(") || !s.endsWith(")")) {
            throw new RuntimeException("Invalid color format: " + s);
        }

        String inner = s.substring("color(".length(), s.length() - 1).trim();

        // RGB(x,y,z)
        if (inner.startsWith("RGB(") && inner.endsWith(")")) {
            String rgb = inner.substring("RGB(".length(), inner.length() - 1);
            String[] parts = rgb.split(",");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid RGB format: " + s);
            }
            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());
            return new Color(r, g, b);
        }

        // named colors
        switch (inner) {
            case "black": return Color.BLACK;
            case "blue": return Color.BLUE;
            case "cyan": return Color.CYAN;
            case "gray": return Color.GRAY;
            case "lightGray": return Color.LIGHT_GRAY;
            case "green": return Color.GREEN;
            case "orange": return Color.ORANGE;
            case "pink": return Color.PINK;
            case "red": return Color.RED;
            case "white": return Color.WHITE;
            case "yellow": return Color.YELLOW;
            default:
                throw new RuntimeException("Unknown color name: " + inner);
        }
    }
}
