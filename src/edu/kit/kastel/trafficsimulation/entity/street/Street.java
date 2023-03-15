package edu.kit.kastel.trafficsimulation.entity.street;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.CarCollection;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;

import java.util.List;

/**
 * The type Street.
 */
public abstract class Street {

    /**
     * The constant MIN_DISTANCE.
     */
    protected static final int MIN_DISTANCE = 10;
    private static final String EXCEPTION_TEMPLATE_TO_MANY_CARS = "To many cars on street %d";
    private static final String EXCEPTION_TEMPLATE_SAME = "Same start and end crossing on street %d";
    /**
     * The Start crossing.
     */
    private final Crossing startCrossing;
    /**
     * The End crossing.
     */
    private final Crossing endCrossing;
    private final int maximumSpeed;
    /**
     * The Car collection.
     */
    protected CarCollection carCollection;
    private final int id;
    private final int length;
    private int nextStartPosition;

    /**
     * Instantiates a new Street.
     *
     * @param id            the id
     * @param startCrossing the start crossing
     * @param endCrossing   the end crossing
     * @param length        the length
     * @param maximumSpeed  the maximum speed
     */
    public Street(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        this.id = id;
        this.startCrossing = startCrossing;
        this.endCrossing = endCrossing;
        this.length = length;
        this.nextStartPosition = length;
        this.maximumSpeed = maximumSpeed;
    }

    /**
     * Calculate driving distance int.
     *
     * @param car      the car
     * @param distance the distance
     * @return the int
     */
    public abstract int calculateDrivingDistance(Car car, int distance);

    /**
     * Did car overtake boolean.
     *
     * @param car the car
     * @return the boolean
     */
    public abstract boolean didCarOvertake(Car car);

    /**
     * Gets length.
     *
     * @return the length
     */
    public int getLength() { return length; }

    /**
     * Gets start crossing.
     *
     * @return the start crossing
     */
    public Crossing getStartCrossing() { return startCrossing; }

    /**
     * Gets end crossing.
     *
     * @return the end crossing
     */
    public Crossing getEndCrossing() { return endCrossing; }

    /**
     * Gets maximum speed.
     *
     * @return the maximum speed
     */
    public int getMaximumSpeed() { return maximumSpeed; }

    /**
     * Gets start mileage.
     *
     * @return the start mileage
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
     * Sets car collection.
     *
     * @param carCollection the car collection
     */
    public void setCarCollection(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    /**
     * Calculate get on street distance int.
     *
     * @param distance the distance
     * @return the int
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
     * Gets distance between cars.
     *
     * @param carA the car a
     * @param carB the car b
     * @return the distance between cars
     */
    protected static int getDistanceBetweenCars(Car carA, Car carB) {
        return Math.abs(carA.getPosition().getMileage() - carB.getPosition().getMileage());
    }

    /**
     * Gets next car.
     *
     * @param carsOnStreet the cars on street
     * @param car          the car
     * @return the next car
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
     * Gets last car.
     *
     * @param carsOnStreet the cars on street
     * @return the last car
     */
    protected static Car getLastCar(List<Car> carsOnStreet) {
        if (carsOnStreet.isEmpty()) {
            return null;
        }
        return carsOnStreet.get(carsOnStreet.size() - 1);
    }

    /**
     * Gets drive distance.
     *
     * @param distanceBetweenCars the distance between cars
     * @param distance            the distance
     * @return the drive distance
     */
    protected static int getDriveDistance(int distanceBetweenCars, int distance) {
        return Math.min(distanceBetweenCars - MIN_DISTANCE, distance);
    }

    /**
     * Debug.
     */
    public void debug() {
        if (startCrossing == endCrossing) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SAME, this.id));
        }
    }


}
