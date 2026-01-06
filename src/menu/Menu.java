package menu;

import animation.Animation;

public interface Menu<T> extends Animation {
    void addSelection(String key, String message, T returnVal);
    T getStatus();
    void reset();
}
