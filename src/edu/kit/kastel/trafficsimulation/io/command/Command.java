package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;


/**
 * The type Command.
 */
public abstract class Command {

    private static final String ARGUMENT_SEPERATOR = " ";
    /**
     * The constant REGEX_ALL.
     */
    protected static final String REGEX_ALL = ".*";
    /**
     * The constant OUTPUT_MESSAGE_READY.
     */
    protected static final String OUTPUT_MESSAGE_READY = "READY";
    /**
     * The constant EXCEPTION_CONFIGURED.
     */
    protected static final String EXCEPTION_CONFIGURED = "Simulation has to be loaded first.";
    private static final int ARGUMENT_INDEX = 1;
    private static final int ARGUMENT_COUNT = 2;
    private static final String EXCEPTION_TEMPLATE_ARGUMENT = "There are just two arguments allowed. Your tried %s";
    private static final String EXCEPTION_TEMPLATE_NUMBER = "%s is in wrong number format.";

    /**
     * Matches boolean.
     *
     * @param commandString the command string
     * @return the boolean
     */
    public abstract boolean matches(String commandString);

    /**
     * Execute string.
     *
     * @param commandString the command string
     * @return the string
     */
    public abstract String execute(String commandString);

    /**
     * Gets argument.
     *
     * @param commandString the command string
     * @return the argument
     */
    public static String getArgument(String commandString) {
        String[] splitedArguments = commandString.split(ARGUMENT_SEPERATOR);
        if (splitedArguments.length == ARGUMENT_COUNT) {
            return splitedArguments[ARGUMENT_INDEX];
        } else {
            throw(new SimulationException(String.format(EXCEPTION_TEMPLATE_ARGUMENT, splitedArguments.length)));
        }
    }

    /**
     * Gets positive integer.
     *
     * @param stringInteger the string integer
     * @return the positive integer
     */
    public static int getPositiveInteger(String stringInteger) {
        try {
            int number = Integer.parseInt(stringInteger);
            if (number >= 0) {
                return number;
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE_NUMBER, stringInteger));
            }
        } catch (NumberFormatException exception) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_NUMBER, stringInteger));
        }
    }
}
