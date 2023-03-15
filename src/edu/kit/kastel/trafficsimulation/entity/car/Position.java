package edu.kit.kastel.trafficsimulation.entity.car;

import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The type Position.
 */
public class Position {

    private int mileage;
    private Street street;

    /**
     * Instantiates a new Position.
     *
     * @param street the street
     */
    public Position(Street street) {
        this.street = street;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public Street getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(Street street) {
        this.street = street;
    }

    /**
     * Gets mileage.
     *
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets mileage.
     *
     * @param mileage the mileage
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
