package Model.Statements.ThreadStatements.CountDownLatch;

import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Expressions.ConstExpression;
import Model.ProgramState;
import Model.Statements.IStatement;

import Exception.ExecutionException;
import Model.Statements.PrintStatement;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStatement implements IStatement {
    private String var;
    private Lock lock;

    public CountDownStatement(String var) {
        this.var = var;
        lock = new ReentrantLock();
    }


    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        IStack<IStatement> executionStack = state.getExecutionStack();
        IDictionary<Integer, Integer> latchTable = state.getLatchTableManager().getLatchTable();

        Integer idx = state.getSymbolsTable().get(var);

        if ( idx == null )
            throw new ExecutionException();
        Integer latchVal = latchTable.get(idx);
        if ( latchVal == null )
            throw new ExecutionException();
        if ( latchVal > 0 ) {
            latchTable.put(idx, latchVal - 1);
            executionStack.push(new PrintStatement(new ConstExpression(state.getLastId())));
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
        return "countDown(" + var + ")\n";
    }
}
