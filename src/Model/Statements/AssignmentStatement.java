package Model.Statements;
import Model.Expressions.Expression;
import Model.ADTs.IDictionary;
import Model.ProgramState;

public class AssignmentStatement implements IStatement {
    private String id;
    private Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IStatement duplicate() {
        return new AssignmentStatement(id, expression);
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString() + "\n";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        int value = expression.evaluate(symbolsTable, state.getHeap().getContent());
        symbolsTable.put(id, value);
        return null;
    }
}
