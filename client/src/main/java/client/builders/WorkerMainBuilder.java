package client.builders;

import client.cli.Console;
import client.cli.InputProvider;
import common.model.Coordinates;
import common.model.WorkerIdGenerator;
import common.model.Organization;
import common.model.Position;
import common.model.Status;
import common.model.Worker;
import java.time.ZonedDateTime;



/**
 * Builder for creating Worker {@link Worker}.
 */
public class WorkerMainBuilder extends AbstractConsoleBuilder<Worker> {


    private OrganizationBuilder organization;
    private CoordinatesBuilder coordinates;


    private final long MIN_SALARY = 0;
    private final long temporalyId = 0;
    private final ZonedDateTime temporalyZoned = ZonedDateTime.now();

    /**
     * Constructor for the WorkerMainBuilder class.
     * @param inputProvider the input provider for managing input sources
     * @param console the console for input/output operations
     */
    public WorkerMainBuilder(InputProvider inputProvider, Console console) {
        super(inputProvider, console);
    }

    /**
     * Builds a new Worker instance{@link Worker}.
     * @return the created Worker instance{@link Worker}.
     */
    public Worker build() {
            return new Worker(
                            temporalyId,
                            askName(),
                            askCoordinates(),
                            temporalyZoned,
                            askSalary(),
                            askPosition(),
                            askStatus(),
                            askOrganization());
    }



    public String askName() {
        return askString("name worker", "[it cannot be empty or null]", name -> name != null && !name.isEmpty());
    }



    public long askSalary(){
        return askNumber("salary","[must be greater than zero]", salaryLamb ->   salaryLamb!=null && salaryLamb > MIN_SALARY, Long::parseLong);
    }



    public Status askStatus() {
        return askEnum("type of Status", (Status.values()));
    }



    public Position askPosition() {
        return askEnum("position", Position.values());
    }



    public Organization askOrganization() {
        return organization.build();
    }



    public Coordinates askCoordinates() {
        return coordinates.build();
    }



    public void setOrganizationBuilder(OrganizationBuilder organizationBuild) {
        this.organization = organizationBuild;
    }



    public void setCoordinatesBuild(CoordinatesBuilder coordinatesBuild) {
        this.coordinates = coordinatesBuild;
    }

}
