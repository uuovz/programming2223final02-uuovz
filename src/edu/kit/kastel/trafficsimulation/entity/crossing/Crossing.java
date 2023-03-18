package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.Tick;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * The Crossing class is an abstract class that represents a {@link Crossing} in a traffic simulation.
 * It contains a list of incoming and outgoing {@link Street} objects, and the maximum allowed number of
 * incoming and outgoing {@link Street}. The {@link Crossing} class provides an abstract method 'cross'
 * that should be implemented in its subclasses to determine
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class Crossing {

    /**
     * The constant MAX_ALLOWED_STREETS is the maximum allowed number of incoming and outgoing streets.
     */
    public static final int MAX_ALLOWED_STREETS = 4;
    private static final String EXCEPTION_DEBUG = "Missing incoming/outgoing street.";
    private static final String EXCEPTION_MAX_ALLOWED_EDGES = String.format(
        "Only %d streets incoming/outgoing from crossing", MAX_ALLOWED_STREETS);
    /**
     * The Tick.
     */
    protected Tick tick;
    /**
     * The Incoming roads.
     */
    protected final List<Street> incomingStreets = new ArrayList<>();
    /**
     * The Outgoing roads.
     */
    private final List<Street> outgoingStreets = new ArrayList<>();
    private final int id;

    /**
     * Constructs a Crossing object with the specified ID.
     *
     * @param id the ID of the crossing
     */
    Crossing(int id) { this.id = id; }

    /**
     * This abstract method should be implemented in subclasses to determine which {@link Street} a car should cross.
     *
     * @param car the car to cross the {@link Street}
     * @param index the index of the {@link Street} in the preference list
     * @return the {@link Street} to cross
     */
    public abstract Street cross(Car car, int index);

    /**
     * Adds an incoming {@link Street} to the list of incoming {@link Street} objects.
     *
     * @param street the incoming {@link Street} to add
     */
    public void addIncomingStreet(Street street) {
        if (this.incomingStreets.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.incomingStreets.add(street);
    }

    /**
     * Adds an outgoing {@link Street} to the list of outgoing {@link Street} objects.
     *
     * @param street the outgoing {@link Street} to add
     */
    public void addOutgoingStreet(Street street) {
        if (this.outgoingStreets.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.outgoingStreets.add(street);
    }

    /**
     * Checks if incoming and outgoing {@link Street} object are present.
     * If not, throws a SimulationException.
     */
    public void debug() {
        if (this.incomingStreets.isEmpty() || this.outgoingStreets.isEmpty()) {
            throw new SimulationException(EXCEPTION_DEBUG);
        }
    }

    /**
     * Gets the {@link Street} of preference based on the index passed as parameter.
     * If the preference index is greater than or equal to the number of outgoing streets,
     * returns the first {@link Street} in the outgoing roads list.
     *
     * @param preference the preference index
     * @return the {@link Street} of preference
     */
    protected Street getStreetOfPreference(int preference) {
        if (preference >= this.outgoingStreets.size()) {
            return this.outgoingStreets.get(0);
        }
        return this.outgoingStreets.get(preference);
    }

    /**
     * Gets the ID of this object.
     *
     * @return the ID of this object.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the {@link Tick} for this object.
     *
     * @param tick the tick to be set.
     */
    public void setTick(Tick tick) {
        this.tick = tick;
    }

}
