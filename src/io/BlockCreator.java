package io;

import sprites.Block;

public interface BlockCreator {
    Block create(int xpos, int ypos);
}
