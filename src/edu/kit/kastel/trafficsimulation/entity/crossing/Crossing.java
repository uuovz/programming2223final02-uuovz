package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.Tick;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Crossing.
 */
public abstract class Crossing {

    /**
     * The constant MAX_ALLOWED_STREETS.
     */
    public static final int MAX_ALLOWED_STREETS = 4;
    private static final String EXCEPTION_DEBUG = "Missing incoming/outgoing street.";
    private static final String EXCEPTION_MAX_ALLOWED_EDGES = "Only " + MAX_ALLOWED_STREETS +
        " streets incoming/outgoing from crossing";
    /**
     * The Incoming roads.
     */
    protected final List<Street> incomingRoads = new ArrayList<>();
    /**
     * The Outgoing roads.
     */
    protected final List<Street> outgoingRoads = new ArrayList<>();
    /**
     * The Tick.
     */
    protected Tick tick;
    private final int id;

    /**
     * Instantiates a new Crossing.
     *
     * @param id the id
     */
    Crossing(int id) { this.id = id; }

    /**
     * Cross street.
     *
     * @param car   the car
     * @param index the index
     * @return the street
     */
    public abstract Street cross(Car car, int index);

    /**
     * Add incoming street.
     *
     * @param street the street
     */
    public void addIncomingStreet(Street street) {
        if (this.incomingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.incomingRoads.add(street);
    }

    /**
     * Add outgoing street.
     *
     * @param street the street
     */
    public void addOutgoingStreet(Street street) {
        if (this.outgoingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.outgoingRoads.add(street);
    }

    /**
     * Debug.
     */
    public void debug() {
        if (this.incomingRoads.isEmpty() || this.outgoingRoads.isEmpty()) {
            throw new SimulationException(EXCEPTION_DEBUG);
        }
    }

    /**
     * Gets street of preference.
     *
     * @param preference the preference
     * @return the street of preference
     */
    protected Street getStreetOfPreference(int preference) {
        if (preference >= this.outgoingRoads.size()) {
            return this.outgoingRoads.get(0);
        }
        return this.outgoingRoads.get(preference);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets tick.
     *
     * @param tick the tick
     */
    public void setTick(Tick tick) {
        this.tick = tick;
    }

}
