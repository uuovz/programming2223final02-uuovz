package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The type Roundabout.
 */
public class Roundabout extends Crossing {

    /**
     * Instantiates a new Roundabout.
     *
     * @param id the id
     */
    public Roundabout(int id ) {
        super(id);
    }

    @Override
    public Street cross(Car car, int index) {
        return getStreetOfPreference(index);
    }


}
