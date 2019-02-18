package Model.Statements.ThreadStatements.CountDownLatch;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStatement implements IStatement {
    private String var;
    private Expression expression;
    private Lock lock;

    public NewLatchStatement(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
        lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Integer> heapTable = state.getHeap().getContent();
        IDictionary<Integer, Integer> latchTable = state.getLatchTableManager().getLatchTable();

        Integer latchAddr = state.getLatchTableManager().getFreeIdIndex();
        Integer latchVal = expression.evaluate(symbolsTable, heapTable);
        latchTable.put(latchAddr, latchVal);
        symbolsTable.put(var, latchAddr);

        lock.unlock();
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + "," + expression.toString() + ")\n";
    }
}
