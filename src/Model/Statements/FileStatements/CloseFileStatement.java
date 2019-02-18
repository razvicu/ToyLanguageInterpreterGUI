package Model.Statements.FileStatements;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Utils.FilePair;

import java.io.BufferedReader;
import java.io.IOException;

import Exception.FileException;

public class CloseFileStatement implements IStatement {
    private Expression fileIdExpression;

    public CloseFileStatement(Expression fileIdExpression) {
        this.fileIdExpression = fileIdExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int id = fileIdExpression.evaluate(state.getSymbolsTable(), state.getHeap().getContent());
        IDictionary<Integer, FilePair> fileTable = state.getFileManager().getFileTable();

        FilePair pair = fileTable.get(id);
        if ( pair == null )
            throw new FileException("File does not exist!\n");

        BufferedReader bufferedReader = pair.getBufferedReader();
        try {
            bufferedReader.close();
        }catch(IOException e) {
            throw new FileException("Can not close file!\n");
        }
        fileTable.remove(id);

        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + fileIdExpression + ")\n";
    }
}
