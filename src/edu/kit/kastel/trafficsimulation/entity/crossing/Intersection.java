package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The type Intersection.
 */
public class Intersection extends Crossing {
    private final int greenphaseDuration;

    /**
     * Instantiates a new Intersection.
     *
     * @param id                 the id
     * @param greenphaseDuration the greenphase duration
     */
    public Intersection(int id, int greenphaseDuration){
        super(id);
        this.greenphaseDuration = greenphaseDuration;
    }

    @Override
    public Street cross(Car car, int preference) {
        int greeLightStreetIndex = greenLightStreetIndex();
        if (car.getPosition().getStreet().equals(this.incomingRoads.get(greeLightStreetIndex))) {
            return this.getStreetOfPreference(preference);
        }
        return null;
    }

    private int greenLightStreetIndex() {
        return (this.tick.getTick() / this.greenphaseDuration) % this.incomingRoads.size();
    }


}
