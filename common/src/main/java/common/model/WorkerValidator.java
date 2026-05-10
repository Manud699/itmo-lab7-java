package common.model;


/**
 * WorkerValidator is a utility class that provides validation for Worker objects.
 * It checks if the fields of a Worker object meet the specified constraints and throws an exception if any validation fails.
 */
public class WorkerValidator {

    private static final long MIN_ID = 0;
    private static final float MIN_X = -497.0f;
    private static final double MIN_Y = -764.0;
    private static final long MIN_SALARY = 0;
    private static final int MAX_ORG_NAME_LENGTH = 694;
    private static final float MIN_TURNOVER = 0.0f;
    private static final int MIN_EMPLOYEES = 0;



    /**
     * Rejects a validation rule with the specified message.
     * 
     * @param message the error message
     */
    private static void reject(String message) {
        throw new IllegalArgumentException(message);
    }



    /**
     * Validates a Worker object against the specified constraints.
     * 
     * @param worker the Worker object to validate
     * @throws IllegalArgumentException if any validation fails
     */
    public static void validate(Worker worker) {
        if (worker == null) reject("Worker cannot be null.");
        if (worker.getId() <= MIN_ID) reject("Worker ID must be greater than " + MIN_ID + ".");
        if (worker.getName() == null || worker.getName().trim().isEmpty()) reject("Worker name cannot be null or empty.");
        Coordinates coords = worker.getCoordinates();
        if (coords == null) reject("Coordinates cannot be null.");
        if (coords.getX() <= MIN_X) reject("Coordinate X must be greater than " + MIN_X + ".");
        if (coords.getY() == null || coords.getY() <= MIN_Y) reject("Coordinate Y cannot be null and must be greater than " + MIN_Y + ".");
        if (worker.getCreationDate() == null) reject("Creation date cannot be null.");
        if (worker.getSalary() <= MIN_SALARY) reject("Salary must be greater than " + MIN_SALARY + ".");
        if (worker.getPosition() == null) reject("Position cannot be null.");
        if (worker.getStatus() == null) reject("Status cannot be null.");
        Organization org = worker.getOrganization();
        if (org == null) reject("Organization cannot be null.");
        if (org.getFullName() == null || org.getFullName().trim().isEmpty() || org.getFullName().length() > MAX_ORG_NAME_LENGTH) {
            reject("Organization name cannot be empty or exceed " + MAX_ORG_NAME_LENGTH + " characters.");
        }
        if (org.getAnnualTurnover() <= MIN_TURNOVER) reject("Annual turnover must be greater than " + MIN_TURNOVER + ".");
        if (org.getEmployeesCount() <= MIN_EMPLOYEES) reject("Employees count must be greater than " + MIN_EMPLOYEES + ".");
    }
}