package edu.kit.kastel.trafficsimulation.entity.car;

import java.util.Comparator;

/**
 * The type Car position comparator compares two car positions.
 * The comparison is based on the following criteria:
 * First, compares the street by their IDs.
 * If the IDs are the same, then it compares the positions by their mileages.
 *
 * @author uuovz
 * @version 1.0
 */
public class CarPositionComparator implements Comparator<Car> {

    @Override
    public int compare(Car car1, Car car2) {
        Position pos1 = car1.getPosition();
        Position pos2 = car2.getPosition();
        // Compare roads by their Ids
        int streetIdCmp = Integer.compare(pos1.getStreet().getId(), pos2.getStreet().getId());
        if (streetIdCmp != 0) {
            return streetIdCmp;
        }
        // If the roads are the same, compare positions by their mileages
        return Integer.compare(pos2.getMileage(), pos1.getMileage());
    }
}

