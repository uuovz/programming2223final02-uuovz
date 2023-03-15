package edu.kit.kastel.trafficsimulation.io.command.load;

import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.simulator.Config;

import java.util.regex.Pattern;

/**
 * The type Command load entity.
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
     * Instantiates a new Command load entity.
     *
     * @param simulationFileLoader the simulation file loader
     * @param config               the config
     */
    CommandLoadEntity(SimulationFileLoader simulationFileLoader, Config config) {
        this.simulationFileLoader = simulationFileLoader;
        this.config = config;
    }

    /**
     * Load.
     */
    public abstract void load();

    /**
     * Check syntax boolean.
     *
     * @param pattern the pattern
     * @param input   the input
     * @return the boolean
     */
    protected static boolean checkSyntax(Pattern pattern, String input) {
        return pattern.matcher(input).matches();
    }

}
