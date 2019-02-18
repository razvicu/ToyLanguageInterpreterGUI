package Model.Expressions;

import Exception.*;
import Model.ADTs.IDictionary;

public class ArithmeticExpression extends Expression {
    private Expression firstExpression, secondExpression;
    private Operation operation;

    public enum Operation {
        ADD('+'), SUBTRACT('-'), DIVIDE('/'), MULTIPLY('*');

        private Character character;

        Operation(char ch) {
            this.character = ch;
        }

        public Character getCharacter() {
            return character;
        }
    }

    public ArithmeticExpression(Operation operation, Expression firstExpression, Expression secondExpression) {
        this.operation = operation;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public String toString() {
        return "(" + firstExpression.toString() + operation.getCharacter() + secondExpression.toString() + ")";
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable) {
        switch(operation) {
            case ADD:
                return (firstExpression.evaluate(symbolsTable, heapTable) + secondExpression.evaluate(symbolsTable,
                                                                                                      heapTable));
            case SUBTRACT:
                return (firstExpression.evaluate(symbolsTable, heapTable) - secondExpression.evaluate(symbolsTable,
                                                                                                      heapTable));
            case MULTIPLY:
                return firstExpression.evaluate(symbolsTable, heapTable) * secondExpression.evaluate(symbolsTable,
                                                                                                      heapTable);
            case DIVIDE:
                if (secondExpression.evaluate(symbolsTable, heapTable) != 0)
                    return firstExpression.evaluate(symbolsTable, heapTable) / secondExpression.evaluate(symbolsTable,
                                                                                                      heapTable);
                throw new DivisionByZeroException();
            default:
                throw new UndefinedOperationException();
        }
    }
}
