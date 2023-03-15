package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.simulator.Simulatable;

/**
 * The type Car.
 */
public class Car implements Simulatable {

    private final int id;
    private final Position position;
    private int speed = 0;
    private final int maxSpeed;
    private final int acceleration;
    private int turnPreference = 0;

    /**
     * Instantiates a new Car.
     *
     * @param id           the id
     * @param position     the position
     * @param maxSpeed     the max speed
     * @param acceleration the acceleration
     */
    public Car(int id, Position position, int maxSpeed, int acceleration) {
        this.id = id;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    /**
     * Simulate.
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

        if (actualDriveDistanceOnStreet + actualDriveDistanceOnNewStreet <= 0) {
            this.stop();
        }



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
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets position.
     *
     * @return the position
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
