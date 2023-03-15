package edu.kit.kastel.trafficsimulation.io.command;

import java.util.regex.Pattern;

/**
 * The type Command quit.
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
