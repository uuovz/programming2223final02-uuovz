package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.simulator.Simulatable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Car collection represents a collection of {@link Car} object that can be placed and simulated.
 * This class implements the {@link Simulatable} interface.
 *
 * @author uuovz
 * @version 1.0
 */
public class CarCollection implements Simulatable {

    private final Map<Integer, Car> idCarMap;
    private final List<Integer> carPlaceOrder;
    private final Comparator<Car> carPositionComparator = new CarPositionComparator();

    /**
     * Constructs a CarCollection with the given idCarMap and carPlaceOrder.
     *
     * @param idCarMap the map of {@link Car} ids to their corresponding cars
     * @param carPlaceOrder the order in which the {@link Car} objects will be placed
     */
    public CarCollection(Map<Integer, Car> idCarMap, List<Integer> carPlaceOrder) {
        this.idCarMap = new HashMap<>(idCarMap);
        this.carPlaceOrder = new ArrayList<>(carPlaceOrder);
    }

    /**
     * Places the {@link Car} objects in the order specified by carPlaceOrder on the
     * starting mileage of their corresponding streets.
     */
    public void placeCars() {
        for (int id: this.carPlaceOrder) {
            Car car = this.idCarMap.get(id);
            Position position = car.getPosition();
            position.setMileage(position.getStreet().getStartMileage());
        }
    }

    /**
     * Returns a list of all {@link Car} objects on the given street sorted by their position
     * according to the CarPositionComparator.
     *
     * @param street the {@link Street} to check for cars
     * @return a list of all {@link Car} objects on the given street sorted by their position
     */

    public List<Car> getCarsOnStreet(Street street) {
        List<Car> carsOnStreet = new ArrayList<>();
        for (Car car: idCarMap.values()) {
            if (car.getPosition().getStreet().equals(street)) {
                carsOnStreet.add(car);
            }
        }
        carsOnStreet.sort(this.carPositionComparator);
        return carsOnStreet;
    }

    /**
     * Returns the {@link Car} with the given id.
     *
     * @param id the id of the {@link Car} to retrieve
     * @return the {@link Car} with the given id, or null if no such {@link Car} exists
     */
    public Car getCarById(int id) {
        return this.idCarMap.get(id);
    }

    /**
     * Simulates all @link Car} objects in the collection
     * in order of their position according to the CarPositionComparator.
     */

    @Override
    public void simulate() {
        List<Car> allCars = new ArrayList<>(idCarMap.values());
        allCars.sort(this.carPositionComparator);
        for (Car car: allCars) {
            car.simulate();
        }
    }

}
