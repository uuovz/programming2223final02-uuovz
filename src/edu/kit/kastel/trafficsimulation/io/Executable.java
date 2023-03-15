package edu.kit.kastel.trafficsimulation.io;

/**
 * Classes implementing this interface can be used for a command line interface.
 *
 * @author uuovz
 * @author Thomas Weber
 * @version 1.0
 */
public interface Executable {
    /**
     * Returns whether or not this structure is active.
     *
     * @return whether or not this structure is active
     */
    boolean isActive();

    /**
     * Quits this structure so {@link #isActive()} will return {@code false}.
     */
    void quit();
}
