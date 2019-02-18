package Model.Statements;

import Model.Expressions.Expression;
import Model.ProgramState;

public class IfStatement implements IStatement {
    private Expression expression;
    private IStatement thenStatement, elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public IStatement duplicate() {
        return new IfStatement(expression, thenStatement, elseStatement);
    }

    @Override
    public String toString() {
        return "(IF " + expression.toString() + " THEN " + thenStatement.toString() + "ELSE " + elseStatement.toString() + ");\n" ;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        if ( expression.evaluate(state.getSymbolsTable(), state.getHeap().getContent()) > 0 )
            state.getExecutionStack().push(thenStatement);
        else
            state.getExecutionStack().push(elseStatement);
        return null;
    }
}
