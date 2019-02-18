package Model.Statements.ThreadStatements.Semaphore;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStatement {
    private String var;
    private Expression expression;
    private Lock lock;

    public NewSemaphoreStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();

        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreManager().getSemaphore();

        Integer val = expression.evaluate(symbolsTable, state.getHeap().getContent());
        Integer loc = state.getSemaphoreManager().getSemaphoreAddress();

        semaphoreTable.put(loc, new Pair<>(val, new ArrayList<>()));
        symbolsTable.put(var, loc);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "newSemaphore(" + var + "," + expression.toString() + ")\n";
    }
}
