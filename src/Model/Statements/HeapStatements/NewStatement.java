package Model.Statements.HeapStatements;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;

public class NewStatement implements IStatement {
    private String variableName;
    private Expression expression;

    public NewStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, Integer> heapTable = state.getHeap().getContent();

        int value = expression.evaluate(symbolsTable, heapTable);
        Integer addressIndex = state.getHeap().getFreeAddressIndex();

        heapTable.put(addressIndex, value);
        symbolsTable.put(variableName, addressIndex);
        return null;

    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression + ")\n";
    }
}
