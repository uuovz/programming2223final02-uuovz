package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The Position class represents a position of a {@link Car} on a {@link Street} in the traffic simulation.
 *
 * @author uuovz
 * @version 1.0
 */
public class Position {

    private int mileage;
    private Street street;

    /**
     * Constructs a new Position object with the given {@link Street}.
     *
     * @param street the {@link Street} on which the position is located
     */
    public Position(Street street) {
        this.street = street;
    }

    /**
     * Returns the {@link Street} on which the position is located.
     *
     * @return the {@link Street} on which the position is located
     */
    public Street getStreet() {
        return street;
    }

    /**
     * Sets the {@link Street} on which the position is located.
     *
     * @param street the {@link Street} on which the position is located
     */
    public void setStreet(Street street) {
        this.street = street;
    }

    /**
     * Returns the mileage, which is the position of the {@link Car} on the {@link Street}.
     *
     * @return the mileage, which is the position of the {@link Car} on the {@link Street}
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the mileage, which is the position of the {@link Car} on the {@link Street}.
     *
     * @param mileage the mileage, which is the position of the {@link Car} on the {@link Street}
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
