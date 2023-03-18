package edu.kit.kastel.trafficsimulation.io.command;

import java.util.regex.Pattern;

/**
 * This command matches the "quit" regular expression and is used
 * to exit the {@link edu.kit.kastel.trafficsimulation.simulator.Simulation}.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandQuit extends Command {

    private static final String REGEX = "quit";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private final CommandParser commandParser;

    /**
     * Instantiates a new Command quit.
     *
     * @param commandParser the command parser
     */
    public CommandQuit(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public boolean matches(String commandString) {
        return PATTERN.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        this.commandParser.quit();
        return null;
    }
}
