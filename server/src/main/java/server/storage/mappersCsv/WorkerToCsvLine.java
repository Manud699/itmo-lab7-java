package server.storage.mappersCsv;

import common.model.Worker;


/**
 * class WorkerToCsvLine
 * WorkerToCsvLine is responsible for converting a Worker object into a CSV line.
 * @author manu_d699
 */
public class WorkerToCsvLine {
    


    /**
     * Converts a Worker object into a CSV line.
     *      
     * @param worker
     * @return A CSV line representing the Worker object.
     */
    public static String toCsvLine(Worker worker) {               
        StringBuilder sb = new StringBuilder();
        sb.append(worker.getId()).append(",");
        sb.append(worker.getName()).append(",");
        sb.append(worker.getCoordinates().getX()).append(",");
        sb.append(worker.getCoordinates().getY()).append(",");
        sb.append(worker.getCreationDate().toString()).append(",");
        sb.append(worker.getSalary()).append(",");
        sb.append(worker.getPosition().name()).append(","); 
        sb.append(worker.getStatus().name()).append(",");
        sb.append(worker.getOrganization().getFullName()).append(",");
        sb.append(worker.getOrganization().getAnnualTurnover()).append(",");
        sb.append(worker.getOrganization().getEmployeesCount()); 
        return sb.toString();
    }
}
