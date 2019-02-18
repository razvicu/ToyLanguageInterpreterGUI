package Controller;

import Model.ADTs.Dictionary;
import Model.ADTs.Heap;
import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Expressions.VarExpression;
import Model.Statements.FileStatements.CloseFileStatement;
import Model.Statements.IStatement;
import Model.ProgramState;
import Model.Utils.FilePair;
import Repository.IRepository;
import Exception.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private ExecutorService executorService;
    private boolean displayFlag = true;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public void allStepsExecution() throws IOException {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStateList());

        while (programStateList.size() > 0) {
            oneStepForAllProgramsExecution(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramStateList());
        }

        executorService.shutdown();
        closeAllFiles(repository.getProgramStateList().get(0));
        repository.logProgramStateExecution(repository.getProgramStateList().get(0)); // check if all files are closed
        repository.setProgramStateList(programStateList);
    }

    public void oneStepExecution() {
        executorService = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStateList());
        if ( programStateList.size() > 0)
            oneStepForAllProgramsExecution(programStateList);

        executorService.shutdown();
        repository.setProgramStateList(programStateList);

    }

    private void oneStepForAllProgramsExecution(List<ProgramState> programStateList) {
        programStateList.forEach(p -> {
            try {
                repository.logProgramStateExecution(p);
            }catch(IOException e) {
                throw new FileException("Could not log to file!\n");
            }
        });

        try {
            List<Callable<ProgramState>> callList = programStateList.stream().map((ProgramState p) ->
                    (Callable<ProgramState>)(p::oneStepExecution)).collect(Collectors.toList());

            List<ProgramState> newProgramStatesList = executorService.invokeAll(callList).stream().map(future -> {
                try {
                    return future.get();
                } catch ( InterruptedException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                catch (ExecutionException e) {
                    System.out.println(e.getMessage());
                    //System.exit(0);
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

            HashSet<Integer> mergedSet = new HashSet<>();

            programStateList.forEach(p -> {
                p.getSymbolsTable().toMap().entrySet().forEach(entry -> {
                    mergedSet.add(entry.getValue());
                });
            });

            Heap<Integer, Integer> heap = programStateList.get(0).getHeap();

            heap.setContent(conservativeGarbageCollector(mergedSet, heap.getContent().toMap()));

            programStateList.addAll(newProgramStatesList);

        }catch(InterruptedException e) {
             System.out.println(e.getMessage());
        }


        programStateList.forEach(p -> {
            try {
                repository.logProgramStateExecution(p);
            }catch(IOException e) {
                throw new FileException("Could not log to file!\n");
            }
        });

        repository.setProgramStateList(programStateList);
    }

    private Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symbolsTableValues,
                                                               Map<Integer, Integer> heap) {
        return heap.entrySet().stream().filter(e -> symbolsTableValues.contains(e.getKey())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void closeAllFiles(ProgramState programState) {
        IDictionary<Integer, FilePair> fileTable = programState.getFileManager().getFileTable();

        fileTable.values().stream().map(FilePair::getBufferedReader).forEach(fileBuffer -> {
            try {
                fileBuffer.close();
            }catch (IOException e) {
                throw new FileException("Could not close file!\n");
            }
        });
        fileTable.clear();

    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList) {
        return programStateList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public void setDisplayFlag() {
        displayFlag = true;
    }

    public void resetDisplayFlag() {
        displayFlag = false;
    }

}
