package game;

/**
 * A simple counter for counting things (blocks, balls, score, etc.).
 */
public class Counter {
    private int value;

    /** Create a counter with initial value 0. */
    public Counter() {
        this(0);
    }

    /** Create a counter with a given initial value. */
    public Counter(int initialValue) {
        this.value = initialValue;
    }

    /** Add number to current count. */
    public void increase(int number) {
        this.value += number;
    }

    /** Subtract number from current count. */
    public void decrease(int number) {
        this.value -= number;
    }

    /** Get current count. */
    public int getValue() {
        return this.value;
    }
}
