package Repository;

import Model.*;

import Exception.FileException;

import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private List<ProgramState> programStateList;
    private String logFilePath = "default";

    public Repository(ProgramState programState, String logFilePath) {
        this.programStateList = new ArrayList<>();
        this.programStateList.add(programState);
        this.logFilePath = logFilePath;
        eraseFileContent();
    }

    private void eraseFileContent() {
        try(PrintWriter writer = new PrintWriter(logFilePath)) {
            writer.print("");
        }catch(FileNotFoundException e) {
            throw new FileException("File not found");
        }
    }

    public List<ProgramState> getProgramStateList() { return programStateList; }

    @Override
    public ProgramState getProgramStateById(int id) {
        for ( ProgramState ps : programStateList )
            if ( ps.getId() == id )
                return ps;
        return null;
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return programStateList.get(programStateList.size() - 1);
    }


    @Override
    public void setProgramStateList(List<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws IOException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(programState.toString());
        }
    }
}
