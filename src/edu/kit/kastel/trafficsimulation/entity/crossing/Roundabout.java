package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The Roundabout class represents a type of {@link Crossing} where vehicles move in a circular direction,
 * and the priority of the vehicles is determined by the position on the Roundabout.
 *
 * @author uuovz
 * @version 1.0
 */
public class Roundabout extends Crossing {

    /**
     * Constructs a new Roundabout with the given id.
     *
     * @param id the id of the Roundabout
     */
    public Roundabout(int id) {
        super(id);
    }

    @Override
    public Street cross(Car car, int index) {
        return getStreetOfPreference(index);
    }

}
