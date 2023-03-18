package edu.kit.kastel.trafficsimulation.io.command.load;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.crossing.Crossing;
import edu.kit.kastel.trafficsimulation.entity.crossing.Intersection;
import edu.kit.kastel.trafficsimulation.entity.crossing.Roundabout;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.io.command.Command;
import edu.kit.kastel.trafficsimulation.simulator.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The type CommandLoadCrossing loads all crossings into the simulation.
 * This command loads the crossing data from a file, parses the input data, validates it,
 * and creates {@link Crossing} objects.
 * The {@link Crossing} objects are stored in a Map, where the key is the ID of the Crossing.
 *
 * @author uuovz
 * @version 1.0
 */
public class CommandLoadCrossing extends CommandLoadEntity {

    private static final String REGEX = "^-?\\d+:-?\\d+t$";
    private static final String SEPERATOR_STREET = ":";
    private static final int ARGUMENT_AFTER_SPLIT = 2;
    private static final int INDEX_GREENPHASE = 1;
    private static final int MIN_GREENPHASE_DURATION = 3;
    private static final int MAX_GREENPHASE_DURATION = 10;
    private static final int ROUNDABOUT_GREENPHASE_DURATION = 0;
    private static final String SUFFIX_TIME = "t";
    private static final String NAME = "crossing";
    private static final String EXCPETION_TEMPLATE_GREEN_PHASE_DURATION = "Green phase duration not in range of id %d.";
    private final Pattern pattern = Pattern.compile(REGEX);
    private final Map<Integer, Crossing> crossings = new HashMap<>();

    /**
     * Instantiates a new CommandLoadCrossing object.
     *
     * @param simulationFileLoader the simulation file loader used to load the street data.
     * @param config the simulation configuration to add the streets and street place order to.
     */
    public CommandLoadCrossing(SimulationFileLoader simulationFileLoader, Config config) {
        super(simulationFileLoader, config);
    }

    @Override
    public void load() {
        this.setUpCrossing();
        this.config.setCrossings(this.crossings);
    }

    private void setUpCrossing() {
        List<String> crossingData;
        try {
            crossingData = this.simulationFileLoader.loadCrossings();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        for (String crossing: crossingData) {
            if (!checkSyntax(pattern, crossing)) {
                throw new SimulationException(String.format(EXCPETION_TEMPLATE_SYNTAX, REGEX));
            }
            String cleanInput = crossing.replace(SUFFIX_TIME, "");
            String[] arguments = cleanInput.split(SEPERATOR_STREET);
            if (arguments.length == ARGUMENT_AFTER_SPLIT) {
                int id =  Command.getPositiveInteger(arguments[INDEX_ID]);
                int greenPhaseDuration = Command.getPositiveInteger(arguments[INDEX_GREENPHASE]);
                addCrossing(id, greenPhaseDuration);
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT, NAME));
            }
        }
    }

    private void addCrossing(int id, int greenPhase) {
        if (crossings.get(id) == null) {
            if (greenPhase == ROUNDABOUT_GREENPHASE_DURATION) {
                this.crossings.put(id, new Roundabout(id));
            } else if (greenPhase < MIN_GREENPHASE_DURATION || greenPhase > MAX_GREENPHASE_DURATION ) {
                throw new SimulationException(String.format(EXCPETION_TEMPLATE_GREEN_PHASE_DURATION, id));
            } else {
                this.crossings.put(id, new Intersection(id, greenPhase));
            }
        }
        else {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_DUPLICATED_ID, NAME));
        }
    }
}
