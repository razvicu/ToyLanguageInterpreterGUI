package Model.Statements.LoopStatements;

import Model.ADTs.IStack;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;

public class WhileStatement implements IStatement {
    private Expression expression;
    private IStatement statement;

    public WhileStatement(Expression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "(while(" + expression.toString() + ")\n" + statement.toString() + ")\n";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Integer expressionValue = expression.evaluate(state.getSymbolsTable(), state.getHeap().getContent());
        if ( !expressionValue.equals(0) ) {
            IStack<IStatement> stack = state.getExecutionStack();
            stack.push(this);
            statement.execute(state);
        }
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }
}
