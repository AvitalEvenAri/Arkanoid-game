package io;

import geometry.Point;
import geometry.Rectangle;
import sprites.Block;
import sprites.BlockFill;
import sprites.ColorFill;
import sprites.ImageFill;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class BlocksDefinitionReader {

    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        try {
            BufferedReader br = new BufferedReader(reader);

            Map<String, String> defaults = new HashMap<>();
            Map<String, Integer> spacerWidths = new HashMap<>();
            Map<String, BlockCreator> blockCreators = new HashMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("default")) {
                    Map<String, String> props = parseProperties(line.substring("default".length()).trim());
                    defaults.putAll(props);
                    continue;
                }

                if (line.startsWith("sdef")) {
                    Map<String, String> props = parseProperties(line.substring("sdef".length()).trim());
                    String symbol = require(props, "symbol");
                    int width = Integer.parseInt(require(props, "width"));
                    spacerWidths.put(symbol, width);
                    continue;
                }

                if (line.startsWith("bdef")) {
                    Map<String, String> props = parseProperties(line.substring("bdef".length()).trim());

                    Map<String, String> merged = new HashMap<>(defaults);
                    merged.putAll(props);

                    String symbol = require(merged, "symbol");
                    int width = Integer.parseInt(require(merged, "width"));
                    int height = Integer.parseInt(require(merged, "height"));
                    int hitPoints = Integer.parseInt(require(merged, "hit_points"));

                    final Color stroke;
                    if (merged.containsKey("stroke")) {
                        stroke = new ColorsParser().colorFromString(merged.get("stroke"));
                    } else {
                        stroke = null;
                    }

                    Map<Integer, BlockFill> fillsByHp = parseFills(merged, hitPoints);

                    BlockCreator creator = (x, y) -> {
                        Rectangle rect = new Rectangle(new Point(x, y), width, height);
                        return new Block(rect, hitPoints, fillsByHp, stroke);
                    };

                    blockCreators.put(symbol, creator);
                    continue;
                }

                throw new RuntimeException("Unknown line type in blocks definition: " + line);
            }

            return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);

        } catch (Exception e) {
            throw new RuntimeException("Failed reading blocks definition file", e);
        }
    }

    private static Map<String, String> parseProperties(String s) {
        Map<String, String> map = new HashMap<>();
        String[] parts = s.split("\\s+");
        for (String p : parts) {
            if (p.isEmpty()) continue;
            int idx = p.indexOf(':');
            if (idx < 0) continue;
            String key = p.substring(0, idx).trim();
            String val = p.substring(idx + 1).trim();
            map.put(key, val);
        }
        return map;
    }

    private static String require(Map<String, String> map, String key) {
        String v = map.get(key);
        if (v == null || v.isEmpty()) {
            throw new RuntimeException("Missing required property: " + key);
        }
        return v;
    }

    private static Map<Integer, BlockFill> parseFills(Map<String, String> props, int hitPoints) {
        Map<Integer, BlockFill> fills = new HashMap<>();

        // fill-k
        for (int k = 1; k <= hitPoints; k++) {
            String fk = "fill-" + k;
            if (props.containsKey(fk)) {
                fills.put(k, parseFillValue(props.get(fk)));
            }
        }

        // fill
        if (props.containsKey("fill")) {
            BlockFill base = parseFillValue(props.get("fill"));
            if (fills.isEmpty()) {
                for (int k = 1; k <= hitPoints; k++) {
                    fills.put(k, base);
                }
            } else {
                for (int k = 1; k <= hitPoints; k++) {
                    fills.putIfAbsent(k, base);
                }
            }
        }

        if (fills.isEmpty()) {
            throw new RuntimeException("Block missing fill definition (fill or fill-k).");
        }

        return fills;
    }

    private static BlockFill parseFillValue(String value) {
        value = value.trim();

        if (value.startsWith("color(")) {
            Color c = new ColorsParser().colorFromString(value);
            return new ColorFill(c);
        }

        if (value.startsWith("image(") && value.endsWith(")")) {
            String path = value.substring("image(".length(), value.length() - 1).trim();
            Image img = loadImageFromClasspath(path);
            return new ImageFill(img);
        }

        throw new RuntimeException("Unsupported fill value: " + value);
    }

    private static Image loadImageFromClasspath(String path) {
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new RuntimeException("Image not found in classpath: " + path);
            }
            return ImageIO.read(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed loading image: " + path, e);
        }
    }
}
