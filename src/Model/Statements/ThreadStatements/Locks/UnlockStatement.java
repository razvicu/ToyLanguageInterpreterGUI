package Model.Statements.ThreadStatements.Locks;

import Model.ADTs.IDictionary;
import Model.ProgramState;
import Model.Statements.IStatement;
import Exception.ExecutionException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStatement implements IStatement {
    private String var;
    private Lock lock;

    public UnlockStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Integer> lockTable = state.getLockTable();

        Integer idx = symbolsTable.get(var);
        if (idx == null)
            throw new ExecutionException();

        Integer lockVal = lockTable.get(idx);
        if ( lockVal.equals(state.getId()) )
            lockTable.put(idx, -1);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "unlock(" + var + ")\n";
    }
}
