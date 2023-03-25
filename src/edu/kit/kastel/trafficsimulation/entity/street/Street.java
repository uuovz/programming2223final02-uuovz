package edu.kit.kastel.trafficsimulation.entity.street;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.CarCollection;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;

import java.util.List;

/**
 * The type Street represents an abstract class for all {@link Street} objects in the traffic simulation.
 * It defines the common properties and methods that all {@link Street} objects should have.
 * It also includes an abstract method for calculating the driving distance of a {@link Car} on a {@link Street}.
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class Street {

    /**
     * The constant MIN_DISTANCE.
     */
    protected static final int MIN_DISTANCE = 10;
    private static final String EXCEPTION_TEMPLATE_TO_MANY_CARS = "To many cars on street %d.";
    private static final String EXCEPTION_TEMPLATE_SAME = "Same start and end crossing on street %d.";
    /**
     * The Car collection.
     */
    protected CarCollection carCollection;
    private final Crossing startCrossing;
    private final Crossing endCrossing;
    private final int maximumSpeed;
    private final int id;
    private final int length;
    private int nextStartPosition;

    /**
     * Instantiates a new {@link Street} object with the specified parameters.
     *
     * @param id the id of the {@link Street}
     * @param startCrossing the start {@link Crossing} of the {@link Street}
     * @param endCrossing the end {@link Crossing} of the {@link Street}
     * @param length the length of the {@link Street}
     * @param maximumSpeed the maximum speed limit allowed on the {@link Street}
     */
    protected Street(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        this.id = id;
        this.startCrossing = startCrossing;
        this.endCrossing = endCrossing;
        this.length = length;
        this.nextStartPosition = length;
        this.maximumSpeed = maximumSpeed;
    }

    /**
     * Calculates the driving distance of the specified {@link Car} on the {@link Street}.
     *
     * @param car the {@link Car} for which to calculate the driving distance
     * @param distance the distance the {@link Car} wants to drive
     * @return the driving distance of the {@link Car} on the {@link Street}
     */
    public abstract int calculateDrivingDistance(Car car, int distance);

    /**
     * Checks whether the specified {@link Car} overtook another {@link Car} on the {@link Street}.
     *
     * @param car the {@link Car} to check for overtaking
     * @return true if the {@link Car} overtook another {@link Car}, false otherwise
     */
    public abstract boolean didCarOvertake(Car car);

    /**
     * Returns the length of the {@link Street}.
     *
     * @return the length of the {@link Street}
     */
    public int getLength() { return length; }

    /**
     * Returns the start crossing of the {@link Street}.
     *
     * @return the start crossing of the {@link Street}
     */
    public Crossing getStartCrossing() { return startCrossing; }

    /**
     * Returns the end crossing of the {@link Street}.
     *
     * @return the end crossing of the {@link Street}
     */
    public Crossing getEndCrossing() { return endCrossing; }

    /**
     * Gets the maximum speed of the {@link Car}.
     *
     * @return the maximum speed of the {@link Car}.
     */
    public int getMaximumSpeed() { return maximumSpeed; }

    /**
     * Gets the start mileage of the {@link Car}.
     *
     * @return the start mileage of the {@link Car}.
     * @throws SimulationException if the {@link Car}'s start position is invalid.
     */
    public int getStartMileage() {
        int startPosition = nextStartPosition;
        if (startPosition < 0) {
            throw new SimulationException(EXCEPTION_TEMPLATE_TO_MANY_CARS);
        }
        this.nextStartPosition -= MIN_DISTANCE;
        return startPosition;
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
     * Sets {@link CarCollection} object.
     *
     * @param carCollection the {@link CarCollection}
     */
    public void setCarCollection(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    /**
     * Calculates the distance a {@link Car} can travel on the street before getting on it.
     *
     * @param distance the distance to travel.
     * @return the distance the {@link Car} can travel.
     */
    public int calculateGetOnStreetDistance(int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car lastCar = getLastCar(carsOnStreet);
        if (lastCar == null) {
            return Math.min(distance, this.getLength());
        }
        return getDriveDistance(lastCar.getPosition().getMileage(), distance);
    }

    /**
     * Gets the distance between two {@link Car} objects.
     *
     * @param carA the first {@link Car}.
     * @param carB the second {@link Car}.
     * @return the distance between the two {@link Car} objects.
     */
    protected static int getDistanceBetweenCars(Car carA, Car carB) {
        return Math.abs(carA.getPosition().getMileage() - carB.getPosition().getMileage());
    }

    /**
     * Gets the next {@link Car} object on the {@link Street} after the specified car.
     *
     * @param carsOnStreet the list of {@link Car} objects on the {@link Street}.
     * @param car the current {@link Car}.
     * @return the next {@link Car} on the {@link Street} or null if there is no car in front.
     */
    protected static Car getNextCar(List<Car> carsOnStreet, Car car) {
        int indexOfCar = carsOnStreet.indexOf(car);
        //no car before
        if (indexOfCar == 0) {
            return null;
        }
        return carsOnStreet.get(carsOnStreet.indexOf(car) - 1);
    }

    /**
     * Gets the last {@link Car} on the {@link Street}.
     * @param carsOnStreet the list of {@link Car} objects on the {@link Street}.
     * @return the last {@link Car} on the {@link Street} or null if the {@link Street} is empty.
     */
    private static Car getLastCar(List<Car> carsOnStreet) {
        if (carsOnStreet.isEmpty()) {
            return null;
        }
        return carsOnStreet.get(carsOnStreet.size() - 1);
    }

    /**
     * Calculates the distance that the {@link Car} can drive given the distance to the next
     * {@link Car} and the {@link Car}'s minimum distance.
     * @param distanceBetweenCars the distance between this {@link Car} and the {@link Car} in front.
     * @param distance the distance the {@link Car} can drive.
     * @return the distance the {@link Car} can drive.
     */
    protected static int getDriveDistance(int distanceBetweenCars, int distance) {
        return Math.min(distanceBetweenCars - MIN_DISTANCE, distance);
    }

    /**
     * This method is used for debugging purposes. It checks if the start {@link Crossing}
     * is the same as the end {@link Crossing} .
     * If they are the same, it throws a SimulationException with an error message.
     */
    public void debug() {
        if (startCrossing == endCrossing) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SAME, this.id));
        }
    }

}
