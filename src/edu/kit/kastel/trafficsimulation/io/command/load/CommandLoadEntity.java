package edu.kit.kastel.trafficsimulation.io.command.load;

import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.simulator.Config;

import java.util.regex.Pattern;

/**
 * The CommandLoadEntity class is an abstract class that defines the common behavior
 * of entities that can be loaded into the simulation.
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class CommandLoadEntity {

    /**
     * The constant EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT.
     */
    protected static final String EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT = "Invalid argument amount in the %s dataset.";
    /**
     * The constant EXCEPTION_TEMPLATE_DUPLICATED_ID.
     */
    protected static final String EXCEPTION_TEMPLATE_DUPLICATED_ID = "Duplicated IDs in %s.";
    /**
     * The constant EXCEPTION_TEMPLATE_INVALID_ID.
     */
    protected static final String EXCEPTION_TEMPLATE_INVALID_ID = "Invalid id in %s dataset";
    /**
     * The constant EXCPETION_TEMPLATE_SYNTAX.
     */
    protected static final String EXCPETION_TEMPLATE_SYNTAX = "Line does not match regex %s";
    /**
     * The constant INDEX_ID.
     */
    protected static final int INDEX_ID = 0;
    /**
     * The Simulation file loader.
     */
    protected SimulationFileLoader simulationFileLoader;
    /**
     * The Config.
     */
    protected Config config;

    /**
     * Creates a new instance of CommandLoadEntity.
     *
     * @param simulationFileLoader the simulation file loader
     * @param config the configuration object
     */
    CommandLoadEntity(SimulationFileLoader simulationFileLoader, Config config) {
        this.simulationFileLoader = simulationFileLoader;
        this.config = config;
    }

    /**
     * Abstract method that loads the entity data into the simulation.
     */
    public abstract void load();

    /**
     * Checks if the input string matches a regex pattern.
     *
     * @param pattern the regex pattern to match
     * @param input the input string to check
     * @return true if the input string matches the regex pattern, false otherwise
     */
    protected static boolean checkSyntax(Pattern pattern, String input) {
        return pattern.matcher(input).matches();
    }

}
