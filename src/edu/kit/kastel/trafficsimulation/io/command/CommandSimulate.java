package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.simulator.Network;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.regex.Pattern;

/**
 * A command that simulates the traffic of {@link Network} object for a given number of time steps.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandSimulate extends Command {
    private static final String REGEX = "simulate" + REGEX_ALL;
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final Simulation simulation;

    /**
     * Constructs a new CommandSimulate that operates on the given Simulation object.
     *
     * @param simulation the simulation session
     */
    public CommandSimulate(Simulation simulation) {
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
        String argument = getArgument(commandString);
        int steps = getPositiveInteger(argument);
        for (int i = 0; i < steps; i++) {
            network.simulate();
        }
        return OUTPUT_MESSAGE_READY;
    }
}
