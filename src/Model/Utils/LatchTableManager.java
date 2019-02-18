package Model.Utils;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;

public class LatchTableManager extends IdManager {
    private IDictionary<Integer, Integer> latchTable;

    public LatchTableManager() {
        latchTable = new Dictionary<>();
    }

    public IDictionary<Integer, Integer> getLatchTable() { return latchTable; }

    public Integer getFreeIdIndex() { return super.getFreeIdIndex(); }

    @Override
    public String toString() {
        return latchTable.toString();
    }
}
