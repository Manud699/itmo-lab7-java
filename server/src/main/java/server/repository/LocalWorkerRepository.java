package server.repository;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import common.model.Worker;
import common.network.Result;
import common.repository.WorkerRepository;
import server.storage.FormLoad;
import server.storage.FormSave;

import static common.model.WorkerIdGenerator.*;


/**
 * WorkerRepository is responsible for CRUD operations on the collection of Worker objects.
 */
public class LocalWorkerRepository implements WorkerRepository {
    private final Deque<Worker> workers;
    private final ZonedDateTime creationDate; 
    private  FormSave formSave;
    private  FormLoad formLoad; 



    public LocalWorkerRepository() {
        this.workers = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }



    @Override
    public Result<Boolean> add(Worker worker) {
        boolean isAdded = workers.add(worker);
            return Result.success(isAdded);
    }



//    @Override
//    public Result<Void> updateWorkerById(Worker workerUpdated) {
//        var optionalWorker = workers
//                .stream()
//                .filter(worker -> worker.getId() == workerUpdated.getId())
//                .findFirst();
//        var oldWorker = optionalWorker.get();
//        oldWorker.setName(workerUpdated.getName());
//        oldWorker.setCoordinates(workerUpdated.getCoordinates());
//        oldWorker.setSalary(workerUpdated.getSalary());
//        oldWorker.setPosition(workerUpdated.getPosition());
//        oldWorker.setStatus(oldWorker.getStatus());
//        oldWorker.setOrganization(oldWorker.getOrganization());
//        return Result.success();
//    }



    public Deque<Worker> getWorkers() {
        return workers;
    }



    public ZonedDateTime getCreationDate() {
        return creationDate; 
    } 



    public List<Worker> getWorkersSortedByName() {
        return workers
                .stream()
                .sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toList());
    }



    @Override
    public Result<List<Worker>> getAllWorkers(){
        return Result.success(getWorkersSortedByName());
    }



//    @Override
//    public Result<String> getInfo() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        String formattedDate = creationDate.format(formatter);
//        String info = String.format(
//                "Collection type: %s\n" +
//                        "Initialization date: %s\n" +
//                        "Number of items: %d",
//                workers.getClass().getSimpleName(),
//                formattedDate,
//                workers.size());
//        return Result.success(info);
//    }
//
//
//
    @Override
    public Result<Integer> clear() {
        workers.clear();
        return Result.success();
    }
//
//
//
//    @Override
//    public Result<Boolean> removeById(long id) {
//        boolean isSusses = workers.removeIf(x -> x.getId() == id);
//        return Result.success(isSusses);
//    }
//
//
//
//    @Override
//    public Result<Worker> getHead(){
//        if(workers.isEmpty()){
//            return Result.success(null);
//        }
//        return Result.success(workers.getFirst());
//    }
//
//
//
//    @Override
//    public Result<List<Long>> getDescendingSalaries(){
//        List<Long> descendingSalaries = workers.stream()
//                .map(Worker::getSalary)
//                .sorted(java.util.Comparator.reverseOrder())
//                .toList();
//        return Result.success(descendingSalaries);
//    }
//
//
//
//    @Override
//    public Result<Worker> removeHead() {
//        if(workers.isEmpty()){
//            return Result.success(null);
//        }
//        return Result.success(workers.removeFirst());
//    }
//
//
//
//    @Override
//    public Result<Integer> removeAllByPosition(Position position) {
//        int initSize = workers.size();
//        workers.removeIf(worker -> worker.getPosition() == position);
//        int removed = initSize - workers.size();
//        return Result.success(removed);
//    }
//
//
//
//    @Override
//    public Result<Long> sumOfSalary() {
//        long totalSum = workers
//                                .stream()
//                                .mapToLong(Worker::getSalary)
//                                .sum();
//        return Result.success(totalSum);
//    }
//
//
//    public int getSize(){
//        return workers.size();
//    }
//
//
//
    public boolean isCollectionEmpty(){
        return workers.isEmpty();
    }
//
//
//
    @Override
    public Result<Boolean> existById(long workerId) {
        boolean exist = workers
                        .stream()
                        .anyMatch(worker -> worker.getId()== workerId);
        return Result.success(exist);
    }



    public void load() {
        formLoad.load();
        syncWithExistingWorkers(workers);
    }



    public void save() {
        formSave.save();
    }



    public void setFormLoad(FormLoad formLoad) {
        this.formLoad = formLoad;
    }



    public void setFormSave(FormSave formSave) {
        this.formSave= formSave;
    }
}






