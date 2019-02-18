package Model.Utils;

import Model.Statements.CompoundStatement;
import Model.Statements.IStatement;

import java.util.ArrayList;

public class SyntaxTree {
    public static IStatement makeTree(ArrayList<IStatement> statements) {
        if ( statements.size() == 2 )
            return new CompoundStatement(statements.get(0), statements.get(1));
        return new CompoundStatement(statements.get(0), makeTree(new ArrayList<>(statements.subList(1, statements.size()))));
    }
}
