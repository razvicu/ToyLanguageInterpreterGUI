package Model.Expressions.HeapExpressions;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;

public class ReadExpression extends Expression {
    private String variableName;

    public ReadExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable) {
        Integer heapAddress = symbolsTable.get(variableName);
        return heapTable.get(heapAddress);
    }

    @Override
    public String toString() {
        return "rH(" + variableName + ")";
    }
}
