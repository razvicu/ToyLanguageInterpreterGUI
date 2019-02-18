package Model.Statements.HeapStatements;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Exception.UndefinedVariableException;

public class WriteStatement implements IStatement {
    private String variableName;
    private Expression expression;

    public WriteStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Integer> heapTable = state.getHeap().getContent();

        Integer addressIndex = symbolsTable.get(variableName);
        int value = expression.evaluate(symbolsTable, heapTable);
        heapTable.put(addressIndex, value);

        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "wh(" + variableName + ", " + expression + ")\n";
    }
}
