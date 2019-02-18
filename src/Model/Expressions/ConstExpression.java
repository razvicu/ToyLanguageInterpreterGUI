package Model.Expressions;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;

public class ConstExpression extends Expression {
    private int number;

    public ConstExpression(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable) {
        return number;
    }
}
