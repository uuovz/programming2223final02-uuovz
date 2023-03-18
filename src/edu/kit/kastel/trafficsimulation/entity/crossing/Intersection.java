package edu.kit.kastel.trafficsimulation.entity.crossing;

import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

/**
 * The Intersection class represents a type of Crossing where multiple roads meet at a point and
 * traffic flow is controlled by traffic signals. The green light phase for each incoming
 * road is determined by the greenphaseDuration.
 *
 * @author uuovz
 * @version 1.0
 */
public class Intersection extends Crossing {
    private final int greenphaseDuration;

    /**
     * Constructs a new Intersection with the given id and green phase duration.
     *
     * @param id the id of the Intersection
     * @param greenphaseDuration the duration of the green light phase for each incoming road
     */
    public Intersection(int id, int greenphaseDuration) {
        super(id);
        this.greenphaseDuration = greenphaseDuration;
    }

    @Override
    public Street cross(Car car, int index) {
        int greeLightStreetIndex = greenLightStreetIndex();
        if (car.getPosition().getStreet().equals(this.incomingStreets.get(greeLightStreetIndex))) {
            return this.getStreetOfPreference(index);
        }
        return null;
    }

    private int greenLightStreetIndex() {
        return (this.tick.getTick() / this.greenphaseDuration) % this.incomingStreets.size();
    }
}
