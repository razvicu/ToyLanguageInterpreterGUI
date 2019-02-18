package Model.ADTs;

import javafx.util.Pair;

import java.util.List;

public interface ISemaphore {
    IDictionary<Integer, Pair<Integer, List<Integer>>> getSemaphore();
    Integer getSemaphoreAddress();
}