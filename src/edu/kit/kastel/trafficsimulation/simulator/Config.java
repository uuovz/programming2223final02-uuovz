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
 * The type Config.
 */
public class Config {

    private Map<Integer, Crossing> crossings = new HashMap<>();
    private Map<Integer, Street> sreets = new HashMap<>();
    private List<Integer> streetPlaceOrder = new ArrayList<>();
    private Map<Integer, Car> cars = new HashMap<>();
    private List<Integer> carPlaceOrder = new ArrayList<>();

    /**
     * Sets crossings.
     *
     * @param crossings the crossings
     */
    public void setCrossings(Map<Integer, Crossing> crossings) { this.crossings = new HashMap<>(crossings); }

    /**
     * Gets crossings.
     *
     * @return the crossings
     */
    public Map<Integer, Crossing> getCrossings() {
        return Collections.unmodifiableMap(crossings);
    }

    /**
     * Sets streets.
     *
     * @param streets the streets
     */
    public void setStreets(Map<Integer, Street> streets) { this.sreets = new HashMap<>(streets); }

    /**
     * Gets streets.
     *
     * @return the streets
     */
    public Map<Integer, Street> getStreets() {
        return Collections.unmodifiableMap(sreets);
    }

    /**
     * Gets road place order.
     *
     * @return the road place order
     */
    public List<Integer> getRoadPlaceOrder() {
        return Collections.unmodifiableList(this.streetPlaceOrder);
    }

    /**
     * Sets street place order.
     *
     * @param streetPlaceOrder the street place order
     */
    public void setStreetPlaceOrder(List<Integer> streetPlaceOrder) {
        this.streetPlaceOrder = new ArrayList<>(streetPlaceOrder);
    }

    /**
     * Sets cars.
     *
     * @param cars the cars
     */
    public void setCars(Map<Integer, Car> cars) { this.cars = new HashMap<>(cars); }

    /**
     * Gets cars.
     *
     * @return the cars
     */
    public Map<Integer, Car> getCars() {
        return Collections.unmodifiableMap(cars);
    }

    /**
     * Sets car place order.
     *
     * @param carPlaceOrder the car place order
     */
    public void setCarPlaceOrder(List<Integer> carPlaceOrder) { this.carPlaceOrder = new ArrayList<>(carPlaceOrder); }

    /**
     * Gets car place order.
     *
     * @return the car place order
     */
    public List<Integer> getCarPlaceOrder() { return Collections.unmodifiableList(carPlaceOrder); }


}
