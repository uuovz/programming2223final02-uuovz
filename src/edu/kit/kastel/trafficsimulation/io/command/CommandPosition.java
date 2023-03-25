package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.Position;
import edu.kit.kastel.trafficsimulation.simulator.Network;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.regex.Pattern;

/**
 * The CommandPosition class represents a command that prints the position of a car
 * identified by its ID. The command string should match the regular expression "position.*".
 * If the simulation is not configured, a SimulationException is thrown.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandPosition extends Command {

    private static final String EXCEPTION_INVALID_ID = "Cannot find id %s";
    private static final String OUTPUT_POSITION = "Car %d on street %d with speed %d and position %d";
    private static final String REGEX = "position" + REGEX_ALL;
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final Simulation simulation;

    /**
     * Constructs a new CommandParser object with the given configuration and simulation objects.
     *
     * @param simulation the simulation session
     */
    public CommandPosition(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean matches(String commandString) {
        return PATTERN.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        if (!this.simulation.isActive()) {
            throw new SimulationException(EXCEPTION_CONFIGURED);
        }
        Network network = this.simulation.getNetwork();
        String idStr = getArgument(commandString);
        int idInt = getPositiveInteger(idStr);
        Car car = network.getCarById(idInt);
        if (car == null) {
            throw new SimulationException(String.format(EXCEPTION_INVALID_ID, idStr));
        }
        Position position = car.getPosition();
        return String.format(OUTPUT_POSITION, car.getId(), position.getStreet().getId(),
            car.getSpeed(), position.getMileage());
    }
}
