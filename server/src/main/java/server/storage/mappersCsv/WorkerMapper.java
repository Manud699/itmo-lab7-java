package server.storage.mappersCsv;

import common.model.Coordinates;
import common.model.Organization;
import common.model.Position;
import common.model.Status;
import common.model.Worker;
import common.model.WorkerValidator;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;


/**
 * class WorkerMapper
 * WorkerMapper is responsible for converting a CSV line into a Worker object.
 * @author manu_d699
 */
public class WorkerMapper {


    /**
     *  
     * @param csvLine A string representing a line from the CSV file.
     * @return Worker object created from the CSV line.
     * @throws NumberFormatException if there is an error parsing a number.
     * @throws DateTimeParseException if there is an error parsing the date.
     * @throws IllegalArgumentException if there is a validation error with the Worker data.
     */
    public static Worker fromCsvLine(String csvLine) throws Exception {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line cannot be null or empty");
        }
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 11) {
            throw new IllegalArgumentException("CSV line does not have the correct number of columns (expected 11).");
        }
        try {
            long id = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            float x = Float.parseFloat(parts[2].trim()); 
            double y = Double.parseDouble(parts[3].trim());
            Coordinates coordinates = new Coordinates(x, y);
            
            ZonedDateTime creationDate = ZonedDateTime.parse(parts[4].trim());
            long salary = Long.parseLong(parts[5].trim());
            
            Position position = Position.valueOf(parts[6].trim().toUpperCase());   
            Status status = Status.valueOf(parts[7].trim().toUpperCase());
            
            String orgFullName = parts[8].trim();
            float orgAnnualTurnover = Float.parseFloat(parts[9].trim());
            int orgEmployeesCount = Integer.parseInt(parts[10].trim());
            Organization organization = new Organization(orgFullName, orgAnnualTurnover, orgEmployeesCount);
            
            Worker worker = new Worker(id, name, coordinates, creationDate, salary, position, status, organization);

            WorkerValidator.validate(worker);
            return worker;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing a number in line: " + csvLine, e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing the date. It must be in ISO-8601 format: " + parts[4], e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Data error in CSV line: " + e.getMessage(), e);
        }
    }
}