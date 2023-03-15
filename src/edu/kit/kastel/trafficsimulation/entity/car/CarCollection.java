package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.simulator.Simulatable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Car collection.
 */
public class CarCollection implements Simulatable {

    private final Map<Integer, Car> idCarMap;
    private final List<Integer> carPlaceOrder;
    private final Comparator<Car> carPositionComparator = new CarPositionComparator();

    /**
     * Instantiates a new Car collection.
     *
     * @param idCarMap      the id car map
     * @param carPlaceOrder the car place order
     */
    public CarCollection(Map<Integer, Car> idCarMap, List<Integer> carPlaceOrder) {
        this.idCarMap = new HashMap<>(idCarMap);
        this.carPlaceOrder = new ArrayList<>(carPlaceOrder);
    }

    /**
     * Place cars.
     */
    public void placeCars() {
        for (int id: carPlaceOrder) {
            Car car = this.idCarMap.get(id);
            Position position = car.getPosition();
            position.setMileage(position.getStreet().getStartMileage());
        }
    }

    /**
     * Gets cars on street.
     *
     * @param street the street
     * @return the cars on street
     */
    public List<Car> getCarsOnStreet(Street street) {
        List<Car> carsOnStreet = new ArrayList<>();
        for (Car car: idCarMap.values()) {
            if (car.getPosition().getStreet().equals(street)) {
                carsOnStreet.add(car);
            }
        }
        Collections.sort(carsOnStreet, this.carPositionComparator);
        return carsOnStreet;
    }

    /**
     * Gets car by id.
     *
     * @param id the id
     * @return the car by id
     */
    public Car getCarById(int id) {
        return this.idCarMap.get(id);
    }

    /**
     * Simulate.
     */
    @Override
    public void simulate() {
        List<Car> allCars = new ArrayList<>(idCarMap.values());
        Collections.sort(allCars, this.carPositionComparator);
        for (Car car: allCars) {
            car.simulate();
        }
    }
}
