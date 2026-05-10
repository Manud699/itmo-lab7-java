package client.builders;

import client.cli.Console;
import client.cli.InputProvider;
import common.model.Coordinates;

/**
 * Builder for creating Coordinates {@link Coordinates}.
 */
public class CoordinatesBuilder extends AbstractConsoleBuilder<Coordinates> {

    private final float MIN_COORDINATE_X = -497;
    private final double MIN_COORDINATE_Y = -764;


    /**
     * Constructor for the CoordinatesBuilder class.
     * @param inputProvider the input provider for managing input sources
     * @param console the console for input/output operations
     */
    public CoordinatesBuilder(InputProvider inputProvider, Console console){
        super(inputProvider ,console);
    }


    /**
     * @return the created Coordinates instance.
     */
    @Override
    public Coordinates build() {
        return new Coordinates((float)askNumber("Coordenada X", "[must be greater than -497]", x -> x > MIN_COORDINATE_X && x!= null, Float::parseFloat),
                                askNumber("Coordenada Y","[must be greater than -497/It cannot be null]", y -> y > MIN_COORDINATE_Y && y != null, Double::parseDouble));
        }



}
