package edu.kit.kastel.trafficsimulation.simulator;

/**
 * The Simulation class represents a simulation which can be configured with a network and activated.
 *
 * @author uuovz
 * @version 1.0
 */
public class Simulation {

    private boolean active = false;
    private Network network;

    /**
     * Sets the network configuration for the simulation.
     *
     * @param network the network to be configured
     */

    public void setNetwork(Network network) {
        this.network = network;
        this.active = true;
    }

    /**
     * Gets the network configuration for the simulation.
     * @return the configured network
     */
    public Network getNetwork() {
        return this.network;
    }

    /**
     * Returns whether or not the simulation has been activated.
     *
     * @return true if the simulation is active, false otherwise
     */
    public boolean isActive() {
        return this.active;
    }
}

