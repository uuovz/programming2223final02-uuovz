package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.io.command.load.CommandLoadCars;
import edu.kit.kastel.trafficsimulation.io.command.load.CommandLoadCrossing;
import edu.kit.kastel.trafficsimulation.io.command.load.CommandLoadEntity;
import edu.kit.kastel.trafficsimulation.io.command.load.CommandLoadStreet;
import edu.kit.kastel.trafficsimulation.simulator.Config;
import edu.kit.kastel.trafficsimulation.simulator.Network;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This command is responsible for loading a simulation from a file.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandLoad extends Command {

    private static final String REGEX = "load" + REGEX_ALL;
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final Simulation simulation;
    /**
     * Instantiates a new Command load.
     *
     * @param simulation the simulation session
     */

    public CommandLoad(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean matches(String commandString) {
        return PATTERN.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        Config config = new Config();
        SimulationFileLoader simulationFileLoader;
        try {
            simulationFileLoader = new SimulationFileLoader(getArgument(commandString));
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        List<CommandLoadEntity> commandLoadEntities = new ArrayList<>();
        commandLoadEntities.add(new CommandLoadCrossing(simulationFileLoader, config));
        commandLoadEntities.add(new CommandLoadStreet(simulationFileLoader, config));
        commandLoadEntities.add(new CommandLoadCars(simulationFileLoader, config));
        for (CommandLoadEntity commandLoadEntity: commandLoadEntities) {
            commandLoadEntity.load();
        }

        this.simulation.setNetwork(new Network(config));
        return OUTPUT_MESSAGE_READY;

    }

}
