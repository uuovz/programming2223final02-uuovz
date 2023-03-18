package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.simulator.Simulatable;

/**
 * A class that represents a Car in a simulation.
 *
 * @author uuovz
 * @version 1.0
 */
public class Car implements Simulatable {

    private final int id;
    private final Position position;
    private int speed = 0;
    private final int maxSpeed;
    private final int acceleration;
    private int turnPreference = 0;

    /**
     * Constructs a Car with the given id, position, maximum speed, and acceleration.
     *
     * @param id the id of the car
     * @param position the initial position of the car
     * @param maxSpeed the maximum speed of the car
     * @param acceleration the acceleration of the car
     */
    public Car(int id, Position position, int maxSpeed, int acceleration) {
        this.id = id;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    /**
     * Simulates the car's movement for one Tick in the simulation.
     */
    @Override
    public void simulate() {
        Position position = this.getPosition();
        Street street = position.getStreet();
        this.speed = Math.min(Math.min(maxSpeed, this.speed + acceleration), street.getMaximumSpeed());
        int possibleDriveDistance = this.speed;

        int leftDistanceOnStreet = this.getLeftDistanceOnStreet(street, this.position);

        int actualDriveDistanceOnStreet = 0;
        if (leftDistanceOnStreet != 0) {
            int possibleDriveOnStreet = Math.min(leftDistanceOnStreet, possibleDriveDistance);
            actualDriveDistanceOnStreet = this.getPosition().getStreet()
                .calculateDrivingDistance(this, possibleDriveOnStreet);
            if (actualDriveDistanceOnStreet != 0) {
                this.drive(actualDriveDistanceOnStreet);
            }
        }
        int actualDriveDistanceOnNewStreet = 0;
        leftDistanceOnStreet = this.getLeftDistanceOnStreet(street, this.position);
        int leftPossibleDriveDistance = possibleDriveDistance - actualDriveDistanceOnStreet;
        if (leftDistanceOnStreet == 0 && leftPossibleDriveDistance > 0 && !street.didCarOvertake(this)) {
            Street crossingStreet = street.getEndCrossing().cross(this, this.turnPreference);
            if (crossingStreet != null) {
                int possibleDriveOnStreet = Math.min(leftPossibleDriveDistance, crossingStreet.getLength());
                actualDriveDistanceOnNewStreet = crossingStreet.calculateGetOnStreetDistance(possibleDriveOnStreet);
                if (actualDriveDistanceOnNewStreet >= 0) {
                    this.crossStreetTo(crossingStreet);
                    this.drive(actualDriveDistanceOnNewStreet);
                    this.changeTurnPreference();
                } else {
                    actualDriveDistanceOnNewStreet = 0;
                }
            }
        }
        // If the car did not drive any distance, it must have stopped
        if (actualDriveDistanceOnStreet + actualDriveDistanceOnNewStreet <= 0) {
            this.stop();
        }



    }

    /**
     * Gets the id of the car.
     *
     * @return the id of the car
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the speed of the car.
     *
     * @return the speed of the car
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the position of the car.
     *
     * @return the position of the car
     */
    public Position getPosition() {
        return this.position;
    }

    private void changeTurnPreference() {
        this.turnPreference += 1;
        if (turnPreference >= Crossing.MAX_ALLOWED_STREETS) {
            this.turnPreference = 0;
        }
    }

    private int getLeftDistanceOnStreet(Street street, Position position) {
        return street.getLength() - position.getMileage();
    }

    private void stop() {
        this.speed = 0;
    }

    private void drive(int meters) {
        this.position.setMileage(this.position.getMileage() + meters);
    }

    private void crossStreetTo(Street street) {
        this.position.setMileage(0);
        this.position.setStreet(street);
    }
}
