package Model.Expressions;

import Model.ADTs.IDictionary;
import Exception.UndefinedOperationException;

public class BooleanExpression extends Expression {
    private Expression lhsExpression, rhsExpression;
    private Operator operator;

    public enum Operator {
        LT("<"), LET("<="), EQ("="), DIFF("!="), GT(">"), GET(">");

        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    public BooleanExpression(Operator operator, Expression lhsExpression, Expression rhsExpression) {
        this.operator = operator;
        this.lhsExpression = lhsExpression;
        this.rhsExpression = rhsExpression;
    }

    @Override
    public String toString() {
        return lhsExpression.toString() + operator.getOperator() + rhsExpression.toString();
    }

    @Override
    public int evaluate(IDictionary<String, Integer> symbolsTable, IDictionary<Integer, Integer> heapTable) {
        Integer lhsValue = lhsExpression.evaluate(symbolsTable, heapTable);
        Integer rhsValue = rhsExpression.evaluate(symbolsTable, heapTable);

        switch(operator) {
            case LT:
                return lhsValue < rhsValue ? 1 : 0;
            case LET:
                return lhsValue <= rhsValue ? 1 : 0;
            case EQ:
                return lhsValue.equals(rhsValue) ? 1 : 0;
            case DIFF:
                return !lhsValue.equals(rhsValue) ? 1 : 0;
            case GT:
                return lhsValue > rhsValue ? 1 : 0;
            case GET:
                return lhsValue >= rhsValue ? 1 : 0;
            default:
                throw new UndefinedOperationException();
        }
    }
}
