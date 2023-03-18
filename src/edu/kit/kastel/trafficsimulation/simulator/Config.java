package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Config class represents the configuration for a {@link Simulation}. It contains maps of all the {@link Crossing},
 * {@link Street}, and {@link Car} in the simulation,
 * as well as lists specifying the order in which {@link Street} and {@link Car} should be placed.
 *
 * @author uuovz
 * @version 1.0
 */
public class Config {

    private Map<Integer, Crossing> crossings = new HashMap<>();
    private Map<Integer, Street> sreets = new HashMap<>();
    private List<Integer> streetPlaceOrder = new ArrayList<>();
    private Map<Integer, Car> cars = new HashMap<>();
    private List<Integer> carPlaceOrder = new ArrayList<>();

    /**
     * Sets all {@link Crossing} objects in the configuration.
     *
     * @param crossings the map of {@link Crossing} objects to set
     */
    public void setCrossings(Map<Integer, Crossing> crossings) { this.crossings = new HashMap<>(crossings); }

    /**
     * Returns an unmodifiable map of the {@link Crossing} objects in the configuration.
     *
     * @return an unmodifiable map of the {@link Crossing} objects
     */
    public Map<Integer, Crossing> getCrossings() {
        return Collections.unmodifiableMap(crossings);
    }

    /**
     * Sets all {@link Street} objects in the configuration.
     *
     * @param streets the map of {@link Street} objects to set
     */
    public void setStreets(Map<Integer, Street> streets) { this.sreets = new HashMap<>(streets); }

    /**
     * Returns an unmodifiable map of the {@link Street} objects in the configuration.
     *
     * @return an unmodifiable map of the {@link Street} objects objects
     */
    public Map<Integer, Street> getStreets() {
        return Collections.unmodifiableMap(sreets);
    }

    /**
     * Returns an unmodifiable list specifying the order in which {@link Street} objects should be placed.
     *
     * @return an unmodifiable list specifying the order in which {@link Street} objects should be placed
     */
    public List<Integer> getStreetPlaceOrder() {
        return Collections.unmodifiableList(this.streetPlaceOrder);
    }

    /**
     * Sets the order in which {@link Street} objects should be placed.
     *
     * @param streetPlaceOrder the list specifying the order in which {@link Street} objects should be placed
     */
    public void setStreetPlaceOrder(List<Integer> streetPlaceOrder) {
        this.streetPlaceOrder = new ArrayList<>(streetPlaceOrder);
    }

    /**
     * Sets the {@link Car} objects in the configuration.
     *
     * @param cars the map of {@link Car} objects to set
     */
    public void setCars(Map<Integer, Car> cars) { this.cars = new HashMap<>(cars); }

    /**
     * Returns an unmodifiable map of the {@link Car} objects in the configuration.
     *
     * @return an unmodifiable map of the {@link Car} objects
     */
    public Map<Integer, Car> getCars() {
        return Collections.unmodifiableMap(cars);
    }

    /**
     * Sets the order in which {@link Car} objects should be placed.
     *
     * @param carPlaceOrder the list specifying the order in which {@link Car} objects should be placed
     */
    public void setCarPlaceOrder(List<Integer> carPlaceOrder) { this.carPlaceOrder = new ArrayList<>(carPlaceOrder); }

    /**
     * Returns an unmodifiable list specifying the order in which {@link Car} objects should be placed.
     *
     * @return an unmodifiable list specifying the order in which {@link Car} objects should be placed
     */
    public List<Integer> getCarPlaceOrder() { return Collections.unmodifiableList(carPlaceOrder); }


}
