package Model.Utils;

public class IdManager {
    private Integer freeIdIndex = 1;

    protected synchronized Integer getFreeIdIndex() {
        return freeIdIndex++;
    }
}
