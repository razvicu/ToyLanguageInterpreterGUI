package Model.Statements.ThreadStatements.Semaphore;

import Model.ADTs.IDictionary;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.List;
import Exception.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStatement implements IStatement {
    private String var;
    private Lock lock;

    public ReleaseStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();

        IDictionary<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreManager().getSemaphore();

        Integer idx = state.getSymbolsTable().get(var);

        if ( idx == null )
            throw new ExecutionException();

        Pair<Integer, List<Integer>> semaphoreValue = semaphoreTable.get(idx);
        List<Integer> threads = semaphoreValue.getValue();
        Integer max = semaphoreValue.getKey();

        threads.remove(state.getId());
        semaphoreTable.put(idx, new Pair<>(max, threads));

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "release(" + var + ")\n";
    }
}
