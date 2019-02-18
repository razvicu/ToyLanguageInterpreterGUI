package Model.Statements.FileStatements;

import Model.Utils.FileManager;
import Model.ADTs.IDictionary;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Utils.FilePair;
import Exception.FileException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

public class OpenFileStatement implements IStatement {
    private String fileId;
    private String fileName;

    public OpenFileStatement(String fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            FilePair filePair = new FilePair(fileName, bufferedReader);
            FileManager fileManager = state.getFileManager();
            IDictionary<Integer, FilePair> fileTable = fileManager.getFileTable();
            Set<Integer> keys = fileTable.keys();

            for (Integer key : keys) {
                FilePair fp = fileTable.get(key);
                if ( fileName.equals(fp.getFileName()) )
                    throw new FileException("File already exists\n");
            }

            Integer id = fileManager.getFreeIdIndex();
            fileTable.put(id, filePair);
            IDictionary<String, Integer> symbolsTable = state.getSymbolsTable();
            symbolsTable.put(fileId, id);

        }catch(FileNotFoundException e) {
            throw new FileException("Error opening the file\n");
        }

        return null;
    }

    @Override
    public IStatement duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + fileId + ", " + fileName + ")\n";
    }
}
