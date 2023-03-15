package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.car.CarCollection;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.street.Street;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Graph.
 */
public class Graph {

    private static final  String CONNECTION_ERROR = "Missing incoming/outgoing crossing in a road %d. ";
    private final Map<Integer, Street> idStreetMap;
    private final List<Integer> streetPlaceOrder;
    private final Map<Integer, Crossing> idCrossingMap;
    private final CarCollection carCollection;

    /**
     * Instantiates a new Graph.
     *
     * @param tick             the tick
     * @param carCollection    the car collection
     * @param idRoadMap        the id road map
     * @param streetPlaceOrder the street place order
     * @param idCrossingMap    the id crossing map
     */
    public Graph(Tick tick, CarCollection carCollection, Map<Integer, Street> idRoadMap, List<Integer> streetPlaceOrder,
                 Map<Integer, Crossing> idCrossingMap) {
        this.carCollection = carCollection;
        this.idStreetMap = new HashMap<>(idRoadMap);
        this.streetPlaceOrder = new ArrayList<>(streetPlaceOrder);
        this.idCrossingMap = new HashMap<>(idCrossingMap);
        for (Crossing crossing: idCrossingMap.values()) {
            crossing.setTick(tick);
        }
    }

    /**
     * Connect.
     */
    public void connect() {
        for (Integer id: streetPlaceOrder) {
            Street street = this.idStreetMap.get(id);
            street.setCarCollection(this.carCollection);
            Crossing outGoingcrossing = street.getStartCrossing();
            Crossing incomingGoingcrossing = street.getEndCrossing();
            if (outGoingcrossing == null || incomingGoingcrossing == null) {
                throw new SimulationException(String.format(CONNECTION_ERROR, id));
            }
            outGoingcrossing.addOutgoingStreet(street);
            incomingGoingcrossing.addIncomingStreet(street);

        }
    }

    /**
     * Debug.
     */
    public void debug() {
        for (Crossing crossing: this.idCrossingMap.values()) {
            crossing.debug();
        }
        for (Street street: idStreetMap.values()) {
            street.debug();
        }
    }

}
