package edu.kit.kastel.trafficsimulation;

import edu.kit.kastel.trafficsimulation.io.Shell;

/**
 * The application and entry point of the program.
 *
 * @author uuovz
 * @version 1.0
 */
public final class Main {

    private static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";

    private Main() { throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION); }

    /**
     * Starts the {@link Shell} for user input.
     *
     * @param args have to be empty
     */
    public static void main(String[] args) {

        Shell shell = new Shell();
        shell.start();

    }

}
