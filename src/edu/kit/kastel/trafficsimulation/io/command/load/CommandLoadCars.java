package edu.kit.kastel.trafficsimulation.io.command.load;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.car.Car;
import edu.kit.kastel.trafficsimulation.entity.car.Position;
import edu.kit.kastel.trafficsimulation.entity.street.Street;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.io.command.Command;
import edu.kit.kastel.trafficsimulation.simulator.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The type Command load cars.
 */
public class CommandLoadCars extends CommandLoadEntity {

    private static final String REGEX = "^-?\\d+,-?\\d+,-?\\d+,-?\\d+$";
    private static final int ARGUMENTS_AFTER_SPLIT = 4;
    private static final String SEPERATOR = ",";
    private static final int INDEX_STREET_ID = 1;
    private static final int INDEX_MAX_SPEED = 2;
    private static final int INDEX_ACCELERATION = 3;
    private static final int MIN_SPEED = 20;
    private static final int MAX_SPEED = 40;
    private static final int MIN_ACCELERATION = 1;
    private static final int MAX_ACCELERATION = 10;
    private static final String NAME = "car";
    private static final String EXCEPTION_TEMPLATE_SPEED_RANGE = "Speed of car with id %d not in range.";
    private static final String EXCEPTION_TEMPLATE_ACCELERATION_RANGE = "Accelertion of car with id %d not in range.";
    private final Pattern pattern = Pattern.compile(REGEX);
    private final Map<Integer, Car> cars = new HashMap<>();
    private final List<Integer> carPlaceOrder = new ArrayList<>();

    /**
     * Instantiates a new Command load cars.
     *
     * @param simulationFileLoader the simulation file loader
     * @param config               the config
     */
    public CommandLoadCars(SimulationFileLoader simulationFileLoader, Config config) {
        super(simulationFileLoader, config);
    }

    @Override
    public void load() {
        this.setUpCars();
        this.config.setCars(this.cars);
        this.config.setCarPlaceOrder(this.carPlaceOrder);
    }

    private void setUpCars() {
        List<String> carData;
        try {
            carData = this.simulationFileLoader.loadCars();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        for (String car: carData) {
            if (!checkSyntax(pattern, car)) {
                throw new SimulationException(String.format(EXCPETION_TEMPLATE_SYNTAX, REGEX));
            }
            String[] arguments = car.split(SEPERATOR);
            if (arguments.length == ARGUMENTS_AFTER_SPLIT) {
                int id = Command.getPositiveInteger(arguments[INDEX_ID]);
                int roadId = Command.getPositiveInteger(arguments[INDEX_STREET_ID]);
                int maxSpeed = Command.getPositiveInteger(arguments[INDEX_MAX_SPEED]);
                if (maxSpeed < MIN_SPEED || maxSpeed > MAX_SPEED ) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SPEED_RANGE, id));
                }
                int acceleration = Command.getPositiveInteger(arguments[INDEX_ACCELERATION]);
                if (acceleration < MIN_ACCELERATION || acceleration > MAX_ACCELERATION ) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ACCELERATION_RANGE, id));
                }
                this.addCar(id, roadId, maxSpeed, acceleration);
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ARGUMENT_AMOUNT, NAME));
            }
        }
    }

    private void addCar(int id, int streetId, int maxSpeed, int accelaration) {
        Car car = cars.get(id);
        if (car != null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_DUPLICATED_ID, NAME));
        }
        Map<Integer, Street> streetMap = this.config.getStreets();
        Street street = streetMap.get(streetId);
        if (street == null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_INVALID_ID, NAME));
        }
        this.carPlaceOrder.add(id);
        Position position = new Position(street);
        this.cars.put(id, new Car(id, position, maxSpeed, accelaration));
    }
}
