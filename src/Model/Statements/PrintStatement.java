package Model.Statements;

import Model.Expressions.Expression;
import Model.ADTs.IList;
import Model.ProgramState;

public class PrintStatement implements IStatement {
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public IStatement duplicate() {
        return new PrintStatement(expression);
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")\n";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IList<Integer> array = state.getOutputList();
        array.add(expression.evaluate(state.getSymbolsTable(), state.getHeap().getContent()));
        return null;
    }
}
