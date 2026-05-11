package common.util;

import java.util.Optional;
import java.util.function.Function;


/**
 * Class NumberParseSafe
 * Provides a safe way to parse numbers from strings without throwing exceptions.
 */
public class NumberParseSafe {


    /**
     * Attempts to parse a string into a number using the provided parsing function.
     */
    public static <N> Optional<N>  parse(String toNumber, Function<String, N> parse) { 
        try {
            return Optional.of(parse.apply(toNumber.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    } 
    
}
