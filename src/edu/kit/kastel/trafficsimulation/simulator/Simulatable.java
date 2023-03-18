package edu.kit.kastel.trafficsimulation.simulator;

/**
 * The Simulatable interface defines the behavior of a simulation. It requires that classes that implement it
 * provide a method to simulate the behavior of the system being modeled.
 *
 * @author uuovz
 * @version 1.0
 */
public interface Simulatable {
    /**
     * Simulates the behavior of the system being modeled.
     */
    void simulate();
}
