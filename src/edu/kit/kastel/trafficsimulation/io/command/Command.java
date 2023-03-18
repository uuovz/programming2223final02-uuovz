package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;


/**
 * The Command class is an abstract class that provides the basic functionality
 * for all commands in the {@link edu.kit.kastel.trafficsimulation.simulator.Simulation}.
 *
 * It defines some constants and methods that are used across all subclasses.
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class Command {
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
    private static final String ARGUMENT_SEPERATOR = " ";
    private static final int ARGUMENT_INDEX = 1;
    private static final int ARGUMENT_COUNT = 2;
    private static final String EXCEPTION_TEMPLATE_ARGUMENT = "There are just two arguments allowed. Your tried %s";
    private static final String EXCEPTION_TEMPLATE_NUMBER = "%s is in wrong number format.";

    /**
     * This abstract method checks if the given command string matches the command's pattern.
     *
     * @param commandString the command string to check
     * @return true if the command string matches, false otherwise
     */
    public abstract boolean matches(String commandString);

    /**
     * This abstract method executes the command and returns a string with the output message.
     *
     * @param commandString the command string to execute
     * @return a string with the output message
     */
    public abstract String execute(String commandString);

    /**
     * This static method retrieves the second argument from the command string.
     *
     * @param commandString the command string
     * @return the second argument
     * @throws SimulationException if there are more or less than two arguments in the command string
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
     * This static method parses the given string to an integer and checks if it is a positive number.
     *
     * @param stringInteger the string to parse
     * @return the parsed integer
     * @throws SimulationException if the string is not a positive integer
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
