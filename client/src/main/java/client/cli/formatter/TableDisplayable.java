package client.cli.formatter;

import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.List;
import client.cli.Console;
import common.model.Worker;
import common.model.Coordinates;
import common.model.Organization;

/**
 * Interface TableDisplayable
 * Provides default methods for displaying Worker objects in a formatted table in the console.
 */
public interface TableDisplayable {

    int MAX_NAME_LEN = 10;
    int MAX_COORD_PART_LEN = 7;
    int MAX_SALARY_LEN = 8;
    int MAX_POS_LEN = 9;
    int MAX_STATUS_LEN = 12;
    int MAX_ORG_NAME_LEN = 9;
    int MAX_TURNOVER_LEN = 5;
    int MAX_EMP_LEN = 5;
    int MAX_OWNER_LEN = 10;


    String ROW_FORMAT = "%-4d | %-10s | %-15s | %-16s | %-8s | %-9s | %-12s | %-9s | %-6s | %-5s | %-10s";
    String HEADER_FORMAT = "%-4s | %-10s | %-15s | %-16s | %-8s | %-9s | %-12s | %-9s | %-6s | %-5s | %-10s";

    /**
     * Prints a separator line in the console to visually separate different sections of the table.
     * @param console the console to print the separator to
     */
    default void printSeparator(Console console) {
        console.println("-".repeat(135));
    }


    /**
     * Prints the header of the worker table to the console.
     * @param console the console to print the header to
     */
    default void printHeader(Console console) {
        console.println(String.format(HEADER_FORMAT,
                "ID", "NAME", "COORD[X-Y]", "CREATION DATE", "SALARY", "POSITION", "STATUS", "ORG. NAME", "TURN.O", "EMP.","OWNER"));
    }

    /**
     * Prints a list of Worker objects in a formatted table.
     */
    default void printWorkerTable(List<Worker> workers, Console console) {
        if (workers == null || workers.isEmpty()) {
            console.println("No workers to display.");
            return;
        }
        printSeparator(console);
        printHeader(console);
        printSeparator(console);

        for (Worker worker : workers) {
            console.println(formatWorkerRow(worker));
        }
        printSeparator(console);
    }

    /**
     * Overloaded method to print a single Worker object in a formatted table.
     */
    default void printWorkerTable(Worker worker, Console console) {
        if (worker == null) {
            console.printError("Worker is null.");
            return;
        }
        printWorkerTable(List.of(worker), console);
    }

    /**
     * Formats a Worker object into a string that represents a row in the table.
     */
    default String formatWorkerRow(Worker worker) {

        String safeName = worker.getName();
        if (safeName.length() > MAX_NAME_LEN) {
            safeName = safeName.substring(0, MAX_NAME_LEN - 1) + ".";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dateStr = worker.getCreationDate().format(formatter);

        Coordinates coordinates = worker.getCoordinates();
        String xStr = String.format(Locale.US, "%.1f", (double) coordinates.getX());
        if (xStr.length() > MAX_COORD_PART_LEN) xStr = xStr.substring(0, 4);

        String yStr = String.format(Locale.US, "%.1f", coordinates.getY().doubleValue());
        if (yStr.length() > MAX_COORD_PART_LEN) yStr = yStr.substring(0, 4);

        String coordsStr = xStr + "; " + yStr;

        String salaryStr = String.valueOf(worker.getSalary());
        if (salaryStr.length() > MAX_SALARY_LEN) salaryStr = salaryStr.substring(0, 5);

        String safePos = worker.getPosition().name();
        if (safePos.length() > MAX_POS_LEN) {
            safePos = safePos.substring(0, MAX_POS_LEN - 1) + ".";
        }

        String safeStatus = worker.getStatus().name();
        if (safeStatus.length() > MAX_STATUS_LEN) {
            safeStatus = safeStatus.substring(0, MAX_STATUS_LEN - 1) + ".";
        }

        Organization org = worker.getOrganization();
        String orgName = org.getFullName();
        if (orgName.length() > MAX_ORG_NAME_LEN) {
            orgName = orgName.substring(0, MAX_ORG_NAME_LEN - 1) + ".";
        }

        String orgTurnover = String.format(Locale.US, "%.1f", org.getAnnualTurnover());
        if (orgTurnover.length() > MAX_TURNOVER_LEN) {
            orgTurnover = orgTurnover.substring(0, MAX_TURNOVER_LEN);
        }

        String orgEmp = String.valueOf(org.getEmployeesCount());
        if (orgEmp.length() > MAX_EMP_LEN) {
            orgEmp = orgEmp.substring(0, 2);
        }

        String safeOwner = worker.getCreatorName();
        if (safeOwner == null) safeOwner = "UNKNOWN";
        if (safeOwner.length() > MAX_OWNER_LEN) {
            safeOwner = safeOwner.substring(0, MAX_OWNER_LEN - 1) + ".";
        }


        return String.format(Locale.US, ROW_FORMAT,
                worker.getId(), safeName, coordsStr, dateStr, salaryStr,
                safePos, safeStatus, orgName, orgTurnover, orgEmp, safeOwner);
    }
}