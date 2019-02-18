package Model.Statements.ThreadStatements.CyclicBarrier;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.util.Pair;
import Exception.ExecutionException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitBarrierStatement implements IStatement {
    private String var;
    private Lock lock;

    public AwaitBarrierStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IStack<IStatement> executionStack = state.getExecutionStack();
        IDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTableManager().getBarrierTable();

        Integer idx = state.getSymbolsTable().get(var);

        if ( idx == null )
            throw new ExecutionException();

        Pair<Integer, List<Integer>> barrVal = barrierTable.get(idx);
        List<Integer> threads = barrVal.getValue();

        Integer firstN = barrVal.getKey();
        Integer lastN = threads.size();

        if ( firstN > lastN ) {
            if ( threads.contains(state.getId()))
                executionStack.push(this);
            else {
                threads.add(state.getId());
                barrierTable.put(idx, new Pair<>(firstN, threads));
            }
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "awaitBarrier(" + var + ")\n";
    }
}
