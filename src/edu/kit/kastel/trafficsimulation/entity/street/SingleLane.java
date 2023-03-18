package edu.kit.kastel.trafficsimulation.entity.street;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;

import java.util.List;

/**
 * The type Single lane represents a single lane street between two {@link Crossing} objects.
 * It inherits from the abstract class {@link Street} and implements its abstract methods.
 * This class calculates the driving distance between two {@link Car} objects.
 *
 * @author uuovz
 * @version 1.0
 */
public class SingleLane extends Street {

    /**
     * Instantiates a new SingleLaneStreet object.
     *
     * @param id the id
     * @param startCrossing the start {@link Crossing}
     * @param endCrossing the end {@link Crossing}
     * @param length the length of the {@link Street}
     * @param maximumSpeed the maximum speed limit of the {@link Street}
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
