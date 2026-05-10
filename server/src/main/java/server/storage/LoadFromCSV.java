//package server.storage;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//import java.util.logging.Logger;
//import java.util.logging.Level;
//import common.model.Worker;
//import common.repository.WorkerRepository;
//import server.storage.mappersCsv.WorkerMapper;
//import server.repository.LocalWorkerRepository;
//
///**
// * Handles the initial data loading process from a CSV file into the server's repository.
// * This process occurs locally before the network server starts accepting client requests.
// */
//public class LoadFromCSV implements FormLoad {
//
//    private static final Logger logger = Logger.getLogger(LoadFromCSV.class.getName());
//
//    private final File file;
//    private final WorkerRepository localWorkerRepository;
//
//
//
//    public LoadFromCSV(File file, LocalWorkerRepository localWorkerRepository) {
//        this.file = file;
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//
//
//    @Override
//    public void load() {
//        if (!FileValidator.isValidForRead(file)) {
//            logger.severe("Error. Invalid file for reading at: " + file.getAbsolutePath());
//            return;
//        }
//        boolean isSuccess = executeLoadProtocol();
//        if (isSuccess) {
////            logger.info("Workers loaded successfully. Total count: " + localWorkerRepository.getSize());
//        }
//    }
//
//
//
//    private boolean executeLoadProtocol() {
//        int lineNumber = 0;
//
//        try (Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//                lineNumber++;
//                String line = scanner.nextLine().trim();
//
//                if (line.isEmpty()) {
//                    continue;
//                }
//                processSingleLine(line, lineNumber);
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            logger.log(Level.SEVERE, "File access error: {0}", e.getMessage());
//            return false;
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "Unexpected error during the load process: {0}", e.getMessage());
//            return false;
//        }
//    }
//
//
//
//    private void processSingleLine(String line, int lineNumber) {
//        try {
//            Worker worker = WorkerMapper.fromCsvLine(line);
//            if (localWorkerRepository.existById(worker.getId()).getValue()) {
//                logger.warning("Warning on line " + lineNumber + ": Duplicate ID found '" + worker.getId() + "'. Skipping entry.");
//                return;
//            }
//            localWorkerRepository.add(worker);
//        } catch (IllegalArgumentException e) {
//            logger.severe("Validation error on line " + lineNumber + ": " + e.getMessage());
//        } catch (Exception e) {
//            logger.severe("Data parsing error on line " + lineNumber + ": " + e.getMessage());
//        }
//    }
//}