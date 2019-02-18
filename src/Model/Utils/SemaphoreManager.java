package Model.Utils;

import Model.ADTs.Dictionary;
import Model.ADTs.IDictionary;
import Model.ADTs.ISemaphore;
import javafx.util.Pair;

import java.util.List;

public class SemaphoreManager extends IdManager implements ISemaphore {
    private IDictionary<Integer, Pair<Integer, List<Integer>>> semaphore;

    public SemaphoreManager() {
        semaphore = new Dictionary<>();
    }

    @Override
    public IDictionary<Integer, Pair<Integer, List<Integer>>> getSemaphore() {
        return semaphore;
    }

    @Override
    public Integer getSemaphoreAddress() {
        return super.getFreeIdIndex();
    }

    @Override
    public String toString() {
        return semaphore.toString();
    }
}
