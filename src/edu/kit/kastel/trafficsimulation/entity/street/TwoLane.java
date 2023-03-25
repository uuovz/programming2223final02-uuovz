package edu.kit.kastel.trafficsimulation.entity.street;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;

import java.util.List;

/**
 * The type Two lane, which is a subclass of the {@link Street} class.
 * This class represents a two-lane street so {@link Car} objects can overtake.
 *
 * @author uuovz
 * @version 1.0
 */
public class TwoLane extends Street {

    private Car lastCarOvertake = null;

    /**
     * Instantiates a new TwoLaneStreet.
     *
     * @param id the unique identifier of the {@link Street}
     * @param startCrossing the {@link Crossing} at the start of the {@link Street}
     * @param endCrossing the {@link Crossing} at the end of the {@link Street}
     * @param length the length of the {@link Street} in meters
     * @param maximumSpeed the maximum speed allowed on the {@link Street}.
     */
    public TwoLane(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(id, startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateDrivingDistance(Car car, int distance) {
        lastCarOvertake = null;
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car nextCar = getNextCar(carsOnStreet, car);
        if (nextCar == null) {
            return distance;
        }
        Car nextNextCar = getNextCar(carsOnStreet, nextCar);
        if (nextNextCar == null && distance >= getDistanceBetweenCars(car , nextCar) + MIN_DISTANCE) {
            this.lastCarOvertake = car;
            return distance;
        }
        if (distance >= getDistanceBetweenCars(car , nextCar) + MIN_DISTANCE && nextNextCar != null
            && getDistanceBetweenCars(car, nextCar) + MIN_DISTANCE
            <= getDistanceBetweenCars(car, nextNextCar) - MIN_DISTANCE ) {
            this.lastCarOvertake = car;
            return getDriveDistance(getDistanceBetweenCars(car, nextNextCar), distance);
        }

        return getDriveDistance(getDistanceBetweenCars(car, nextCar), distance);
    }

    @Override
    public boolean didCarOvertake(Car car) {
        return car.equals(this.lastCarOvertake);
    }
}
