package io;

import sprites.Block;

import java.util.Map;

public class BlocksFromSymbolsFactory {
    private final Map<String, Integer> spacerWidths;
    private final Map<String, BlockCreator> blockCreators;

    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths,
                                    Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    public int getSpaceWidth(String s) {
        Integer w = spacerWidths.get(s);
        if (w == null) {
            throw new RuntimeException("Unknown spacer symbol: " + s);
        }
        return w;
    }

    public Block getBlock(String s, int xpos, int ypos) {
        BlockCreator bc = blockCreators.get(s);
        if (bc == null) {
            throw new RuntimeException("Unknown block symbol: " + s);
        }
        return bc.create(xpos, ypos);
    }
}
