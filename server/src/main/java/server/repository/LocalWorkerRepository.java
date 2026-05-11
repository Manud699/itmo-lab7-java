package server.repository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import common.model.Position;
import common.model.Worker;
import common.network.Result;
import common.repository.WorkerRepository;
import server.multithread.UserContext;


public class LocalWorkerRepository implements WorkerRepository {
    private final Deque<Worker> workers;
    private final ZonedDateTime creationDate;


    public LocalWorkerRepository() {
        this.workers = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }


    @Override
    public synchronized Result<Boolean> add(Worker worker) {
        boolean isAdded = workers.add(worker);
            return Result.success(isAdded);
    }


    @Override
    public synchronized Result<Void> updateWorkerById(Worker workerUpdated) {
        var optionalWorker = workers
                .stream()
                .filter(worker -> worker.getId() == workerUpdated.getId())
                .findFirst();
        if (optionalWorker.isEmpty()) {
            return Result.failure("The worker was not found in the local memory.");
        }

        var oldWorker = optionalWorker.get();
        oldWorker.setName(workerUpdated.getName());
        oldWorker.setCoordinates(workerUpdated.getCoordinates());
        oldWorker.setSalary(workerUpdated.getSalary());
        oldWorker.setPosition(workerUpdated.getPosition());
        oldWorker.setStatus(workerUpdated.getStatus());
        oldWorker.setOrganization(workerUpdated.getOrganization());

        return Result.success();
    }


    public synchronized List<Worker> getWorkersSortedByName() {
        return workers
                .stream()
                .sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toList());
    }


    @Override
    public synchronized Result<List<Worker>> getAllWorkers(){
        return Result.success(getWorkersSortedByName());
    }


    @Override
    public synchronized Result<String> getInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = creationDate.format(formatter);
        String info = String.format(
                "Collection type: %s\n" +
                        "Initialization date: %s\n" +
                        "Number of items: %d",
                workers.getClass().getSimpleName(),
                formattedDate,
                workers.size());
        return Result.success(info);
    }


    @Override
    public synchronized Result<Void> clear() {
        workers.removeIf((worker -> worker.getCreatorName().equals(UserContext.get().name())));
        return Result.success();
    }



    @Override
    public synchronized Result<Boolean> removeById(long id) {
        boolean isSusses = workers.removeIf(x -> x.getId() == id);
        return Result.success(isSusses);
    }


    @Override
    public synchronized Result<Worker> getHead(){
        if(workers.isEmpty()){
            return Result.success(null);
        }
        return Result.success(workers.getFirst());
    }


    @Override
    public synchronized Result<List<Long>> getDescendingSalaries(){
        List<Long> descendingSalaries = workers.stream()
                .map(Worker::getSalary)
                .sorted(java.util.Comparator.reverseOrder())
                .toList();
        return Result.success(descendingSalaries);
    }


    @Override
    public synchronized Result<Worker> removeHead() {
        if(workers.isEmpty()){
            return Result.success(null);
        }
        String nameUser = UserContext.get().name();
        Optional<Worker> optional = workers.stream()
                        .filter(worker -> worker.getCreatorName().equals(nameUser))
                        .sorted(Comparator.comparing(Worker::getName))
                        .findFirst();
        if(optional.isEmpty())
            return Result.success(new Worker());
        workers.remove(optional.get());
        return Result.success(optional.get());
    }


    @Override
    public synchronized Result<Integer> removeAllByPosition(Position position) {
        int initSize = workers.size();
        workers.removeIf(worker -> worker.getPosition() == position && Objects.equals(worker.getCreatorName(), UserContext.get().name()));
        int removed = initSize - workers.size();
        return Result.success(removed);
    }


    @Override
    public synchronized Result<Long> sumOfSalary() {
        long totalSum = workers
                                .stream()
                                .mapToLong(Worker::getSalary)
                                .sum();
        return Result.success(totalSum);
    }


    @Override
    public synchronized Result<Boolean> existById(long workerId) {
        boolean exist = workers
                        .stream()
                        .anyMatch(worker -> worker.getId() == workerId && worker.getCreatorName().equals(UserContext.get().name()));
        return Result.success(exist);
    }


    @Override
    public void load(){
        throw new UnsupportedOperationException("This method is not supported by this class.");
    }
}






