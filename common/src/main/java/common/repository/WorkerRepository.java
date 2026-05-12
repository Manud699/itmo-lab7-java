package common.repository;
import common.model.Position;
import common.model.Worker;
import common.network.Result;

import java.util.List;


public interface WorkerRepository {

    Result<Boolean> add(Worker worker);

    Result<List<Worker>> getAllWorkers();

    Result<Void> clear();

    Result<Boolean> existById(long id);

    Result<Void> updateWorkerById(Worker workerUpdated);

    Result<Boolean> removeById(long id);

    Result<Worker> getHead();

    Result<Integer> removeAllByPosition(Position position);

    Result<Long> sumOfSalary();

    Result<List<Long>> getDescendingSalaries();

    Result<Worker> removeHead();

    Result<String> getInfo();

    Result<Void> load();


}