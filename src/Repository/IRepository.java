package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStateList);
    ProgramState getProgramStateById(int id);
    ProgramState getCurrentProgramState();
    void logProgramStateExecution(ProgramState programState) throws IOException;
}
