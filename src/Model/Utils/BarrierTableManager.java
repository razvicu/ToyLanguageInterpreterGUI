package Model.Utils;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;
import javafx.util.Pair;

import java.util.List;

public class BarrierTableManager extends IdManager {
    private IDictionary<Integer, Pair<Integer, List<Integer>>> barrierTable;

    public BarrierTableManager() {
        barrierTable = new Dictionary<>();
    }

    public IDictionary<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return barrierTable;
    }

    @Override
    public String toString() {
        return barrierTable.toString();
    }

    public Integer getFreeIdIndex() { return super.getFreeIdIndex(); }
}
