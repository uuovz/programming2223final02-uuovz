package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.Graph;
import edu.kit.kastel.trafficsimulation.entity.Tick;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.CarCollection;


/**
 * The type Simulation.
 */
public class Simulation implements Simulatable {

    private boolean configured = false;
    private CarCollection carCollection;
    private final Tick tick = new Tick();

    /**
     * Configure.
     *
     * @param config the config
     */
    public void configure(Config config) {
        this.tick.reset();
        this.carCollection = new CarCollection(config.getCars(), config.getCarPlaceOrder());
        Graph graph = new Graph(this.tick, this.carCollection, config.getStreets(),
            config.getRoadPlaceOrder(), config.getCrossings());
        graph.connect();
        graph.debug();
        this.carCollection.placeCars();
        this.configured = true;
    }

    /**
     * Gets car by id.
     *
     * @param id the id
     * @return the car by id
     */
    public Car getCarById(int id) {
        return this.carCollection.getCarById(id);
    }

    /**
     * Simulate.
     */
    @Override
    public void simulate() {
        this.carCollection.simulate();
        this.tick.simulate();
    }

    /**
     * Is configured boolean.
     *
     * @return the boolean
     */
    public boolean isConfigured() {
        return this.configured;
    }
}
