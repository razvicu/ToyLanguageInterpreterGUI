package Model.Statements.ThreadStatements.Locks;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Exception.ExecutionException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStatement {
    private String var;
    private Lock lock;

    public LockStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }


    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();

        Integer idx = state.getSymbolsTable().get(var);
        if ( idx == null )
            throw new ExecutionException();

        IDictionary<Integer, Integer> lockTable = state.getLockTable();

        Integer lockVal = lockTable.get(idx);
        if ( lockVal == null )
            throw new ExecutionException();
        if ( lockVal == -1 )
            lockTable.put(idx, state.getId());
        else
            state.getExecutionStack().push(this);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "lock(" + var + ")\n";
    }
}
