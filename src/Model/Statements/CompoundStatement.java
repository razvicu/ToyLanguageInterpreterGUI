package Model.Statements;

import Model.ADTs.IStack;
import Model.ProgramState;

public class CompoundStatement implements IStatement {
    private IStatement firstStatement, secondStatement;

    public CompoundStatement(IStatement first, IStatement second) {
        this.firstStatement = first;
        this.secondStatement = second;
    }

    @Override
    public IStatement duplicate() {
        return new CompoundStatement(firstStatement.duplicate(), secondStatement.duplicate());
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public String toString() {
        return firstStatement.toString()  + secondStatement.toString();
    }
}
