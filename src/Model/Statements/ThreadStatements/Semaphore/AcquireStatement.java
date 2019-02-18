package Model.Statements.ThreadStatements.Semaphore;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.util.Pair;
import Exception.ExecutionException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement {
    private String var;
    private Lock lock;

    public AcquireStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();

        IDictionary<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreManager().getSemaphore();
        IStack<IStatement> executionStack = state.getExecutionStack();

        Integer idx = state.getSymbolsTable().get(var);

        if ( idx == null )
            throw new ExecutionException();

        Pair<Integer, List<Integer>> semaphoreValue = semaphoreTable.get(idx);
        List<Integer> threads = semaphoreValue.getValue();
        Integer max = semaphoreValue.getKey();

        if ( max != threads.size() ) {
            if ( threads.contains(state.getId()))
                throw new ExecutionException();
            threads.add(state.getId());
            semaphoreTable.put(idx, new Pair<>(max, threads));
        }
        else
            executionStack.push(this);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")\n";
    }
}
