package edu.kit.kastel.trafficsimulation.entity;

/**
 * The type Tick.
 */
public class Tick {

    private int tick = 0;

    /**
     * Simulate.
     */
    public void simulate() {
        this.tick += 1;
    }

    /**
     * Gets tick.
     *
     * @return the tick
     */
    public int getTick() {
        return tick;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.tick = 0;
    }
}
