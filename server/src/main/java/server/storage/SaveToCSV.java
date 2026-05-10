package server.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.storage.mappersCsv.WorkerToCsvLine;
import common.model.Worker;
import server.repository.LocalWorkerRepository;

/**
 * Class SaveToCSV
 * This class is responsible for saving the collection of Worker objects to a CSV file.
 */
public class SaveToCSV implements FormSave {

    private final File file;
    private final LocalWorkerRepository localWorkerRepository;
    private final static Logger logger = Logger.getLogger(SaveToCSV.class.getName());

    public SaveToCSV(File file, LocalWorkerRepository localWorkerRepository) {
        this.file = file;
        this.localWorkerRepository = localWorkerRepository;
    }



    @Override
    public void save(){
        if(!FileValidator.isValidForWrite(this.file)) return;
        if(!this.file.exists()){
            boolean isCreated = createFile(this.file);
            if (!isCreated) return;
        }
        executeSaveProtocol(this.file);
    }



    public boolean createFile(File targetFile){
        try {
            File parentDirectory = targetFile.getParentFile();
            if(parentDirectory != null && !parentDirectory.exists()){
                parentDirectory.mkdirs();
            }
            if(!targetFile.createNewFile()) {
                logger.log(Level.WARNING,"File already exists at: " + targetFile.getAbsolutePath());
                return true;
            }
            logger.log(Level.INFO,"Notice: Created save file at: " + targetFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Failed to create file at '" + targetFile.getAbsolutePath() + "': " + e.getMessage());
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Unexpected error while attempting to save at '" + targetFile.getAbsolutePath() + "'.");
            return false;
        }
    }



    public void executeSaveProtocol(File file) {
    try {
        writeWorkersToFile(file);
        logger.log(Level.INFO,"Workers successfully saved to file: " + file.getAbsolutePath());
    } catch (IOException e) {
        logger.log(Level.SEVERE,"I/O error while saving to '" + file.getAbsolutePath() + "': " + e.getMessage());
    } catch(Exception e) {
        logger.log(Level.SEVERE,"An unexpected error occurred while saving.");
        }
    }



    public void writeWorkersToFile(File file) throws IOException{
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(Worker worker : localWorkerRepository.getWorkers()){
                String lineToSave = WorkerToCsvLine.toCsvLine(worker);
                bufferedWriter.write(lineToSave);
                bufferedWriter.newLine();
            }
        }
    }
}