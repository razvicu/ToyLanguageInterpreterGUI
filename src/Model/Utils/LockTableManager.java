package Model.Utils;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;

public class LockTableManager extends IdManager {
    private IDictionary<Integer, Integer> lockTable;

    public LockTableManager() {
        lockTable = new Dictionary<>();
    }

    public IDictionary<Integer, Integer> getLockTable() { return lockTable; }

    @Override
    public String toString() {
        return lockTable.toString();
    }

    public Integer getFreeIdIndex() {
        return super.getFreeIdIndex();
    }
}
