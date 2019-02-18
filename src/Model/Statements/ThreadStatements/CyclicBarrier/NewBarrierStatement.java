package Model.Statements.ThreadStatements.CyclicBarrier;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements IStatement {
    private String var;
    private Expression expression;
    private Lock lock;

    public NewBarrierStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable = state.getBarrierTableManager().getBarrierTable();

        Integer val = expression.evaluate(symbolsTable, state.getHeap().getContent());
        Integer location = state.getBarrierTableManager().getFreeIdIndex();

        barrierTable.put(location, new Pair<>(val, new ArrayList<>()));
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
        return "newBarrier(" + var + "," + expression.toString() + ")\n";
    }
}
