package Model.Statements.ThreadStatements;

import Model.ADTs.Stack;
import Model.ProgramState;
import Model.Statements.IStatement;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        ProgramState ps = new ProgramState(state.getLastId() * 10, new Stack<>(),
                state.getSymbolsTable().duplicate(), state.getHeap(), state.getLockTableManager(), state.getLatchTableManager(),
                state.getBarrierTableManager(),
                state.getSemaphoreManager(), state.getOutputList(), state.getFileManager(),
                this.statement);
        state.setLastId(state.getLastId() * 10);
        return ps;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")\n";
    }

    @Override
    public IStatement duplicate() {
        return null;
    }
}
