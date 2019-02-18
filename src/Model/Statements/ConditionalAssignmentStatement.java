package Model.Statements;

import Model.Expressions.Expression;
import Model.ProgramState;

public class ConditionalAssignmentStatement implements IStatement {
    String var;
    Expression expression1, expression2, expression3;

    public ConditionalAssignmentStatement(String var, Expression expression1, Expression expression2, Expression expression3) {
        this.var = var;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IfStatement newIfStatement = new IfStatement(expression1, new AssignmentStatement(var, expression2), new AssignmentStatement(var, expression3));
        state.getExecutionStack().push(newIfStatement);
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return var + "=" + expression1.toString() + "?" + expression2.toString() + ":" + expression3.toString() + "\n";
    }
}
