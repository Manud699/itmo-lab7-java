package client.builders;

import client.cli.Console;
import client.cli.InputProvider;
import common.model.Organization;

/**
 * Builder for creating Organization {@link Organization}.
 */
public class OrganizationBuilder extends AbstractConsoleBuilder<Organization>  {

    private final int MAX_CHARACTERS = 694;
    private final int MIN_ANUALT = 0;
    private final int MIN_EMPLOYESS = 0;


    /**
     * Constructor for the OrganizationBuilder class.
     * @param inputProvider the input provider for managing input sources
     * @param console the console for input/output operations
     */
    public OrganizationBuilder(InputProvider inputProvider, Console console) {
        super(inputProvider,console);
    }



    @Override
    public Organization build() {
        return new Organization(askString("fullName organization","[it cannot have more than 694 characters/it cannot be empty or null]", fullName-> fullName.length() <= MAX_CHARACTERS && fullName != null && !fullName.isEmpty()),
                                (float)askNumber("annualTurnover","[must be greater than zero]",   annualT -> annualT > MIN_ANUALT && annualT !=null, Float::parseFloat),
                                (int)askNumber("employeesCount","[must be greater than zero]", employessC -> employessC > MIN_EMPLOYESS && employessC != null, Integer::parseInt));

    }
}
