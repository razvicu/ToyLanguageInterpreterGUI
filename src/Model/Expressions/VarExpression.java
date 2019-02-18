package Model.Expressions;

import Model.ADTs.IDictionary;

public class VarExpression extends Expression {
    private String id;

    public VarExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable) {
        return symbolsTable.get(id);
    }
}
