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
 * The Graph class represents a traffic network graph that consists of {@link Street} objects
 * and {@link Crossing} objects.
 * It connects the {@link Street} and {@link Crossing} together and allows
 * {@link edu.kit.kastel.trafficsimulation.entity.car.Car} objects to travel through the network.
 *
 * @author uuovz
 * @version 1.0
 */
public class Graph {

    private static final  String CONNECTION_ERROR = "Missing incoming/outgoing crossing in a road %d. ";
    private final Map<Integer, Street> idStreetMap;
    private final List<Integer> streetPlaceOrder;
    private final Map<Integer, Crossing> idCrossingMap;
    private final CarCollection carCollection;

    /**
     * Creates a new Graph object with the given {@link Tick},
     * {@link CarCollection}, road map, street placement order, and crossing map.
     *
     * @param tick the {@link Tick} object to set for the {@link Crossing} objects
     * @param carCollection the collection of cars on the graph
     * @param idRoadMap the mapping of street IDs to {@link Street} objects
     * @param streetPlaceOrder the list of {@link Street} IDs in order of placement
     * @param idCrossingMap the mapping of crossing IDs to {@link Crossing} objects
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
     * Connects the {@link Street} objects and {@link Crossing} objects together.
     * Throws an exception if a {@link Street} object is missing incoming or outgoing {@link Crossing} object.
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
     * Debugs the graph by printing the debug information for all {@link Crossing} and {@link Street} objects.
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
