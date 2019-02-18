package Model.Statements.LoopStatements;

import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;

public class ForStatement implements IStatement {
    private IStatement initStatement;
    private Expression loopExpression;
    private IStatement incrementStatement;
    private IStatement statement;

    public ForStatement(IStatement initStatement, Expression loopExpression, IStatement incrementStatement, IStatement statement) {
        this.initStatement = initStatement;
        this.loopExpression = loopExpression;
        this.incrementStatement = incrementStatement;
        this.statement = statement;
    }


    @Override
    public ProgramState execute(ProgramState state) {
        CompoundStatement cs = new CompoundStatement(initStatement,
                new WhileStatement(loopExpression, new CompoundStatement(statement, incrementStatement)));
        state.getExecutionStack().push(cs);
        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "for(" + initStatement.toString() + ";" + loopExpression.toString() + ";" + incrementStatement.toString()
                + ")\n" + statement.toString() + "\n";
    }
}
