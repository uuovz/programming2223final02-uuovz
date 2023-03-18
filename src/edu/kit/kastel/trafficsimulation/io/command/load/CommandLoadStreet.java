package edu.kit.kastel.trafficsimulation.io.command.load;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.street.SingleLane;
import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.entity.street.TwoLane;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.io.command.Command;
import edu.kit.kastel.trafficsimulation.simulator.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The type CommandLoadStreet loads all streets into the simulation.
 * Streets are defined by a regular expression matching a specific format and are represented by an id.
 * Streets consist of a length, a maximum speed, and the number of lanes.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandLoadStreet extends CommandLoadEntity {

    private static final String REGEX = "^-?\\d+-->-?\\d+:-?\\d+m,-?\\d+x,-?\\d+max$";
    private static final String SEPERATOR_A = ":";
    private static final String SEPERATOR_B = ",";
    private static final String SEPERATOR_NODES = "-->";
    private static final String REPLACE_LENGTH = "m";
    private static final String REPLACE_LANES = "x";
    private static final String REPLACE_MAX = "max";
    private static final int SINGLE_LANE = 1;
    private static final int MIN_SPEED = 5;
    private static final int MAX_SPEED = 40;
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 10000;
    private static final int ARGUMENT_AFTER_SPLIT = 2;
    private static final int INDEX_NODES = 0;
    private static final int INDEX_PROP = 1;
    private static final int INDEX_START_NODE = 0;
    private static final int INDEX_END_NODE = 1;
    private static final int INDEX_LENGTH = 0;
    private static final int INDEX_LANES = 1;
    private static final int INDEX_MAX_SPEED = 2;
    private static final String NAME = "street";
    private static final String EXCEPTION_TEMPLATE_LENGTH = "Length of street with id %d not in range.";
    private static final String EXCEPTION_TEMPLATE_SPEED = "Speed of street with id %d not in range.";
    private static final String EXCEPTION_TEMPLATE_LANES = "Lane amount to low for street %d.";
    private final Pattern pattern = Pattern.compile(REGEX);
    private final Map<Integer, Street> streets = new HashMap<>();
    private final List<Integer> streetPlaceOrder = new ArrayList<>();

    /**
     * Instantiates a new CommandLoadStreet object.
     *
     * @param simulationFileLoader the simulation file loader used to load the street data.
     * @param config the simulation configuration to add the streets and street place order to.
     */
    public CommandLoadStreet(SimulationFileLoader simulationFileLoader, Config config) {
        super(simulationFileLoader, config);
    }

    @Override
    public void load() {
        this.setUpStreet();
        this.config.setStreets(this.streets);
        this.config.setStreetPlaceOrder(this.streetPlaceOrder);
    }

    private void setUpStreet() {
        List<String> streetData;
        try {
            streetData = this.simulationFileLoader.loadStreets();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        int id = 0;
        int startNode;
        int endNode;
        int length;
        int lanes;
        int maxSpeed;
        for (String street: streetData) {
            if (!checkSyntax(pattern, street)) {
                throw new SimulationException(String.format(EXCPETION_TEMPLATE_SYNTAX, REGEX));
            }
            String[] arguments = street.split(SEPERATOR_A);
            if (arguments.length == ARGUMENT_AFTER_SPLIT) {
                String[] nodeArguments = arguments[INDEX_NODES].split(SEPERATOR_NODES);
                if (nodeArguments.length == ARGUMENT_AFTER_SPLIT) {
                    startNode = Command.getPositiveInteger(nodeArguments[INDEX_START_NODE]);
                    endNode = Command.getPositiveInteger(nodeArguments[INDEX_END_NODE]);
                } else {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT, NAME));
                }
                String[] propArguments = arguments[INDEX_PROP].split(SEPERATOR_B);
                length = Command.getPositiveInteger(propArguments[INDEX_LENGTH].replace(REPLACE_LENGTH, ""));
                if (length < MIN_LENGTH || length > MAX_LENGTH) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_LENGTH, id));
                }
                maxSpeed = Command.getPositiveInteger(propArguments[INDEX_MAX_SPEED].replace(REPLACE_MAX, ""));
                if (maxSpeed < MIN_SPEED || maxSpeed > MAX_SPEED) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SPEED, id));
                }
                lanes = Command.getPositiveInteger(propArguments[INDEX_LANES].replace(REPLACE_LANES, ""));
                this.addStreet(id, startNode, endNode, length, lanes, maxSpeed);
                id++;
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT, NAME));
            }
        }

    }

    private void addStreet(int id, int startNode, int endNode, int length, int lanes, int maxSpeed) {
        Map<Integer, Crossing> crossing = this.config.getCrossings();
        Crossing startCrossing = crossing.get(startNode);
        Crossing endCrossing = crossing.get(endNode);
        if (startCrossing == null || endCrossing == null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_INVALID_ID, NAME));
        }
        streetPlaceOrder.add(id);
        if (lanes < SINGLE_LANE) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_LANES, id));
        }
        if (lanes == SINGLE_LANE) {
            streets.put(id, new SingleLane(id, startCrossing, endCrossing, length, maxSpeed));
        } else {
            streets.put(id, new TwoLane(id, startCrossing, endCrossing, length, maxSpeed));
        }
    }

}
