package io;

import geometry.Velocity;
import levels.Background;
import levels.FileLevel;
import levels.LevelInformation;
import sprites.Block;
import sprites.Sprite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelSpecificationReader {

    public List<LevelInformation> fromReader(Reader reader) {
        try {
            BufferedReader br = new BufferedReader(reader);
            List<LevelInformation> levels = new ArrayList<>();

            String line;
            List<String> oneLevelLines = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // ignore comments/empty lines
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.equals("START_LEVEL")) {
                    oneLevelLines = new ArrayList<>();
                    continue;
                }

                if (line.equals("END_LEVEL")) {
                    if (oneLevelLines == null) {
                        throw new RuntimeException("END_LEVEL without START_LEVEL");
                    }
                    levels.add(parseOneLevel(oneLevelLines));
                    oneLevelLines = null;
                    continue;
                }

                if (oneLevelLines != null) {
                    oneLevelLines.add(line);
                }
            }

            return levels;

        } catch (Exception e) {
            throw new RuntimeException("Failed parsing levels file", e);
        }
    }

    private LevelInformation parseOneLevel(List<String> lines) throws Exception {
        // store key->value fields
        Map<String, String> fields = new HashMap<>();

        // block layout rows
        List<String> blockRows = new ArrayList<>();
        boolean inBlocks = false;

        for (String line : lines) {
            if (line.equals("START_BLOCKS")) {
                inBlocks = true;
                continue;
            }
            if (line.equals("END_BLOCKS")) {
                inBlocks = false;
                continue;
            }

            if (inBlocks) {
                blockRows.add(line);
                continue;
            }

            int idx = line.indexOf(':');
            if (idx < 0) {
                continue;
            }
            String key = line.substring(0, idx).trim();
            String val = line.substring(idx + 1).trim();
            fields.put(key, val);
        }

        // required fields (fail parsing if missing)
        String levelName = require(fields, "level_name");
        String velocitiesStr = require(fields, "ball_velocities");
        String backgroundStr = require(fields, "background");
        int paddleSpeed = Integer.parseInt(require(fields, "paddle_speed"));
        int paddleWidth = Integer.parseInt(require(fields, "paddle_width"));

        String bdefPath = require(fields, "block_definitions");
        int startX = Integer.parseInt(require(fields, "blocks_start_x"));
        int startY = Integer.parseInt(require(fields, "blocks_start_y"));
        int rowHeight = Integer.parseInt(require(fields, "row_height"));
        int numBlocksToRemove = Integer.parseInt(require(fields, "num_blocks"));

        // parse velocities
        List<Velocity> velocities = parseVelocities(velocitiesStr);
        int numberOfBalls = velocities.size();

        // parse background (currently only color(...))
        Sprite background = parseBackground(backgroundStr);

        // load blocks factory from bdef (classpath relative)
        BlocksFromSymbolsFactory factory = loadBlocksFactory(bdefPath);

        // build blocks from layout
        List<Block> blocks = buildBlocks(factory, blockRows, startX, startY, rowHeight);

        return new FileLevel(
                numberOfBalls,
                velocities,
                paddleSpeed,
                paddleWidth,
                levelName,
                background,
                blocks,
                numBlocksToRemove
        );
    }

    private List<Block> buildBlocks(BlocksFromSymbolsFactory factory,
                                    List<String> rows,
                                    int startX,
                                    int startY,
                                    int rowHeight) {
        List<Block> blocks = new ArrayList<>();

        int y = startY;

        for (String row : rows) {
            int x = startX;

            for (int i = 0; i < row.length(); i++) {
                String sym = String.valueOf(row.charAt(i));

                if (factory.isSpaceSymbol(sym)) {
                    x += factory.getSpaceWidth(sym);
                } else if (factory.isBlockSymbol(sym)) {
                    Block b = factory.getBlock(sym, x, y);
                    blocks.add(b);

                    // advance x by block width
                    x += (int) b.getCollisionRectangle().getWidth();
                } else {
                    // unknown symbol -> spec says fail parsing
                    throw new RuntimeException("Unknown symbol in blocks layout: '" + sym + "'");
                }
            }

            y += rowHeight;
        }

        return blocks;
    }

    private BlocksFromSymbolsFactory loadBlocksFactory(String path) throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Block definitions file not found in classpath: " + path);
        }
        return BlocksDefinitionReader.fromReader(new InputStreamReader(is));
    }

    private Sprite parseBackground(String s) {
        s = s.trim();

        // background:color(...)
        if (s.startsWith("color(")) {
            ColorsParser cp = new ColorsParser();
            return new Background(cp.colorFromString(s));
        }

        // background:image(path)
        if (s.startsWith("image(") && s.endsWith(")")) {
            String path = s.substring("image(".length(), s.length() - 1).trim();
            return new levels.ImageBackground(path);
        }

        throw new RuntimeException("Unsupported background format: " + s);
    }


    private List<Velocity> parseVelocities(String s) {
        // format: "angle,speed angle,speed ..."
        List<Velocity> list = new ArrayList<>();
        String[] items = s.split("\\s+");

        for (String it : items) {
            String[] parts = it.split(",");
            int angle = Integer.parseInt(parts[0].trim());
            double speed = Double.parseDouble(parts[1].trim());
            list.add(Velocity.fromAngleAndSpeed(angle, speed));
        }

        return list;
    }

    private String require(Map<String, String> fields, String key) {
        String val = fields.get(key);
        if (val == null || val.isEmpty()) {
            throw new RuntimeException("Missing required field: " + key);
        }
        return val;
    }
}
