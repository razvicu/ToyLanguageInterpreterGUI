package Model;

import Model.ADTs.*;
import Model.Statements.IStatement;
import Model.Utils.*;
import Exception.ExecutionException;

public class ProgramState extends IdManager {

    private IStack<IStatement> executionStack;
    private IDictionary<String, Integer> symbolsTable;
    private Heap<Integer, Integer> heap;
    private LockTableManager lockTableManager;
    private SemaphoreManager semaphoreManager;
    private LatchTableManager latchTableManager;
    private BarrierTableManager barrierTableManager;
    private IList<Integer> outputList;
    private FileManager fileManager;
    private IStatement originalProgramStatement;
    private Integer currentId;
    private Integer lastId = 1;

    public ProgramState(Integer stateId, IStack<IStatement> stack, IDictionary<String, Integer> sTable, Heap<Integer, Integer> hp,
                        LockTableManager lkTableManager, LatchTableManager ltm, BarrierTableManager btm,
                        SemaphoreManager sM, IList<Integer> outList, FileManager fTable, IStatement programStatement) {
        executionStack = stack;
        symbolsTable = sTable;
        heap = hp;
        lockTableManager = lkTableManager;
        latchTableManager = ltm;
        semaphoreManager = sM;
        barrierTableManager = btm;
        outputList = outList;
        fileManager = fTable;
        originalProgramStatement = programStatement.duplicate();
        executionStack.push(programStatement);
        currentId = stateId;
        lastId = currentId;
    }

    public ProgramState(IStatement programStatement) {
        executionStack = new Stack<>();
        symbolsTable = new Dictionary<>();
        outputList = new Array<>();
        fileManager = new FileManager();
        heap = new Heap<>();
        lockTableManager = new LockTableManager();
        latchTableManager = new LatchTableManager();
        semaphoreManager = new SemaphoreManager();
        originalProgramStatement = programStatement.duplicate();
        executionStack.push(programStatement);
        currentId = super.getFreeIdIndex();
    }

    public ProgramState oneStepExecution() {
        if ( executionStack.isEmpty() )
            throw new ExecutionException();
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IDictionary<String, Integer> getSymbolsTable() {
        return symbolsTable;
    }

    public Heap<Integer, Integer> getHeap() { return heap; }

    public IDictionary<Integer, Integer> getLockTable() { return lockTableManager.getLockTable(); }

    public LockTableManager getLockTableManager() { return lockTableManager; }

    public SemaphoreManager getSemaphoreManager() { return semaphoreManager; }

    public LatchTableManager getLatchTableManager() { return latchTableManager; }

    public BarrierTableManager getBarrierTableManager() { return barrierTableManager; }

    public FileManager getFileManager() { return fileManager; }

    public IList<Integer> getOutputList() {
        return outputList;
    }

    public Integer getId() {
        return currentId;
    }

    public void setLastId(Integer id) { lastId = id; }

    public Integer getLastId() { return lastId; }

    public Boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public Integer getLockAddress() { return lockTableManager.getFreeIdIndex(); }

    @Override
    public String toString() {
        return "ID: " + currentId.toString() + " \n" + "Execution stack:\n" + executionStack.toString() + "\nSymbols Table:\n" + symbolsTable.toString() +
                "\nHeap Table:\n" + heap.toString() + "\nSemaphore: " + semaphoreManager.toString() +
                 "\nOutput list:\n" + outputList.toString() + "\nFile table:\n"
                + fileManager.toString() + "\n----------------------------------------------------------------";
    }
}
