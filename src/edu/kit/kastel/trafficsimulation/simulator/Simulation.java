package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.Graph;
import edu.kit.kastel.trafficsimulation.entity.Tick;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.CarCollection;


/**
 * The Simulation class represents a simulation of a traffic network. It implements the {@link Simulatable} interface,
 * which requires that it provides a method to simulate the behavior of the network.
 * The class is responsible for configuring the network.
 *
 * @author uuovz
 * @version 1.0
 */
public class Simulation implements Simulatable {

    private boolean configured = false;
    private CarCollection carCollection;
    private final Tick tick = new Tick();

    /**
     * Configures the simulation based on the given configuration.
     *
     * @param config the {@link Config} object to use for the simulation
     */
    public void configure(Config config) {
        this.tick.reset();
        this.carCollection = new CarCollection(config.getCars(), config.getCarPlaceOrder());
        Graph graph = new Graph(this.tick, this.carCollection, config.getStreets(),
            config.getStreetPlaceOrder(), config.getCrossings());
        graph.connect();
        graph.debug();
        this.carCollection.placeCars();
        this.configured = true;
    }

    /**
     * Returns the {@link Car} object with the specified ID.
     *
     * @param id the ID of the {@link Car} to retrieve
     * @return the {@link Car} object with the specified ID
     */
    public Car getCarById(int id) {
        return this.carCollection.getCarById(id);
    }

    /**
     * Simulates the behavior of the traffic network.
     * {@link CarCollection} and {@link Tick} will be updated
     */
    @Override
    public void simulate() {
        this.carCollection.simulate();
        this.tick.simulate();
    }

    /**
     * Returns whether or not the simulation has been configured.
     *
     * @return true if the simulation has been configured, false otherwise
     */
    public boolean isConfigured() {
        return this.configured;
    }
}
