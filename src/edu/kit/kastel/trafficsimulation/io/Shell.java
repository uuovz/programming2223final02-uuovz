package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.io.command.CommandParser;
import edu.kit.kastel.trafficsimulation.simulator.Config;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.Scanner;

/**
 * The type Shell.
 *
 * @author uuovz
 * @version 1.0
 */
public class Shell {
    private final Config config = new Config();
    private final Simulation simulation = new Simulation();
    private final CommandParser commandParser = new CommandParser(config, simulation);

    /**
     * Start.
     */
    public void start() {

        Scanner scanner = new Scanner(System.in);
        while (this.commandParser.isActive()) {
            try {
                String commandString = scanner.nextLine();
                String output = this.commandParser.parse(commandString);
                if (output != null) {
                    System.out.println(output);
                }
            } catch (SimulationException exception) {
                System.out.println(exception.getMessage());
            }
        }
        scanner.close();
    }
}
