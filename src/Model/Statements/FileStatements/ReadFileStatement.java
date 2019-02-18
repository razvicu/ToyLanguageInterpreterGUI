package Model.Statements.FileStatements;

import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Utils.FilePair;
import Exception.FileException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private Expression fileIdExpression;
    private String variableName;

    public ReadFileStatement(Expression fileIdExpression, String variableName) {
        this.fileIdExpression = fileIdExpression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        int id = fileIdExpression.evaluate(state.getSymbolsTable(), state.getHeap().getContent());
        IDictionary<Integer, FilePair> fileTable = state.getFileManager().getFileTable();
        IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
        FilePair pair = fileTable.get(id);

        if ( pair == null )
            throw new FileException("File does not exist!\n");

        BufferedReader bufferedReader = pair.getBufferedReader();
        String line;

        try {
            line = bufferedReader.readLine();
        }catch(IOException e) {
            throw new FileException("Cannot read from file!\n");
        }

        int value;

        if ( line == null )
            value = 0;
        else {
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                value = 0;
            }
        }

        symbolsTable.put(variableName, value);
        return null;

    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + fileIdExpression.toString() + "," + variableName + ")\n";
    }
}
