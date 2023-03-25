package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.io.Executable;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * The CommandParser class represents a parser for traffic simulation commands.
 *
 * It is responsible for parsing command strings and executing the corresponding commands
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandParser implements Executable {

    private static final String INVALID_LEADING_ENDING = " ";
    private static final String EXCEPTION_INVALID = "Your command was invalid.";
    private boolean active = true;
    private final List<Command> commands = new ArrayList<>();


    /**
     * Instantiates a new Command parser.
     *
     * @param simulation the simulation session
     */
    public CommandParser(Simulation simulation) {
        this.commands.add(new CommandQuit(this));
        this.commands.add(new CommandLoad(simulation));
        this.commands.add(new CommandPosition(simulation));
        this.commands.add(new CommandSimulate(simulation));
    }


    /**
     * Parse string.
     *
     * @param commandString the command string
     * @return the string
     */
    public String parse(String commandString) {
        if (!validateFormat(commandString)) {
            throw new SimulationException(EXCEPTION_INVALID);
        }

        for (Command command: commands) {
            if (command.matches(commandString)) {
                return command.execute(commandString);
            }
        }

        throw new SimulationException(EXCEPTION_INVALID);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void quit() {
        this.active = false;
    }

    private boolean validateFormat(String commandString) {
        String firstChar = commandString.substring(0, 1);
        String lastChar = commandString.substring(commandString.length() - 1);
        return !(firstChar.equals(INVALID_LEADING_ENDING) || lastChar.equals(INVALID_LEADING_ENDING));
    }
}
