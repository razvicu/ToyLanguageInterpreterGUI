package Model.Statements.ThreadStatements.Locks;

import Model.ADTs.IDictionary;
import Model.ProgramState;
import Model.Statements.IStatement;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStatement {
    private String var;
    private Lock lock;

    public NewLockStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IDictionary<Integer, Integer> lockTable = state.getLockTable();
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();

        Integer location = state.getLockAddress();
        lockTable.put(location, -1);
        symbolsTable.put(var, location);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "newLock(" + var.toString() + ")\n";
    }
}
