package common.model;
import java.util.Deque;



/**
 * class WorkerIdGenerator 
 * @author manu_d699
 */
public class WorkerIdGenerator {

    private static long nextId = 1L; 


    /**
     * Generates a unique ID for a worker.
     * @return the generated ID
     */
    public static long generateID() {
        return nextId++; 
    }



    public static void setId(long lastId) {
        if (lastId >= nextId) {
            nextId = lastId + 1;
        }
    }


    /**
     * Synchronizes the ID generator with existing workers.
     * @param loadedWorkers the deque of loaded workers
     */
    public static void syncWithExistingWorkers(Deque<Worker> loadedWorkers) {
        long maxId = loadedWorkers.stream().mapToLong(Worker::getId).max().orElse(0); 
        nextId = maxId+1;                               
    }


}
