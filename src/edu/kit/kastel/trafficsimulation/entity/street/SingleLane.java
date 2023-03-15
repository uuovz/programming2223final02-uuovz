package edu.kit.kastel.trafficsimulation.entity.street;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;

import java.util.List;

/**
 * The type Single lane.
 */
public class SingleLane extends Street {

    /**
     * Instantiates a new Single lane.
     *
     * @param id            the id
     * @param startCrossing the start crossing
     * @param endCrossing   the end crossing
     * @param length        the length
     * @param maximumSpeed  the maximum speed
     */
    public SingleLane(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(id, startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateDrivingDistance(Car car, int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car nextCar = getNextCar(carsOnStreet, car);
        if (nextCar == null) {
            return distance;
        }
        return getDriveDistance(getDistanceBetweenCars(car, nextCar), distance);
    }

    @Override
    public boolean didCarOvertake(Car car) {
        return false;
    }
}
