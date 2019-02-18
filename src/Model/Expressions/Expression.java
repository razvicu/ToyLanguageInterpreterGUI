package Model.Expressions;

import Model.ADTs.IDictionary;

public abstract class Expression {
    public abstract int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable);
}
