package edu.kit.kastel.trafficsimulation.entity;

/**
 * The type Tick represents the game tick counter.
 *
 * @author uuovz
 * @version 1.0
 */
public class Tick {

    private int tick = 0;

    /**
     * Simulate the passage of time by increasing the tick by 1.
     */
    public void simulate() {
        this.tick += 1;
    }

    /**
     * Gets the current tick count.
     *
     * @return the current tick count.
     */
    public int getTick() {
        return tick;
    }

    /**
     * Reset the tick counter to 0.
     */
    public void reset() {
        this.tick = 0;
    }
}
