package Model.Utils;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;

public class FileManager extends IdManager {

    private IDictionary<Integer, FilePair> fileTable;

    public FileManager() {
        fileTable = new Dictionary<>();
    }

    public IDictionary<Integer, FilePair> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return fileTable.toString();
    }

    @Override
    public Integer getFreeIdIndex() {
        return super.getFreeIdIndex();
    }
}
