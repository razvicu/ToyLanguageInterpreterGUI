package Model.Statements.ThreadStatements.CountDownLatch;

import Model.ADTs.IStack;
import Model.ProgramState;
import Model.Statements.IStatement;
import Exception.ExecutionException;

public class AwaitStatement implements IStatement {
    private String var;

    public AwaitStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> executionStack = state.getExecutionStack();
        Integer idx = state.getSymbolsTable().get(var);

        if ( idx == null )
            throw new ExecutionException();

        Integer latchIdx = state.getLatchTableManager().getLatchTable().get(idx);
        if ( latchIdx == null )
            throw new ExecutionException();
        if ( latchIdx != 0 )
            executionStack.push(this);

        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "await(" + var + ")\n";
    }
}
