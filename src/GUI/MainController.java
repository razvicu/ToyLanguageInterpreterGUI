package GUI;

import Controller.Controller;
import Model.ADTs.IDictionary;
import Model.ADTs.IList;
import Model.ADTs.IStack;
import Model.ProgramState;
import Model.Statements.IStatement;
import Model.Utils.FilePair;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    private Controller controller;

    @FXML
    private Label programStatesNo;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapValue;

    @FXML
    private ListView<String> executionStackView;

    @FXML
    private TableView<Map.Entry<String, Integer>> symbolsTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> variableName;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> variableValue;

    @FXML
    private ListView<Integer> programStateView;

    @FXML
    private ListView<Integer> outputListView;

    @FXML
    private TableView<Map.Entry<Integer, FilePair>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, FilePair>, Integer> fileDescriptor;

    @FXML
    private TableColumn<Map.Entry<Integer, FilePair>, String> fileName;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> semaphoreAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> noThreads;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>> threadsList;

    @FXML
    private Button executionButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populateProgramStateView();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        executionButton.setOnAction(event -> executeOneStep());
        programStateView.setOnMouseClicked(event -> {
            populateExecutionStack();
            populateSymbolsTableView();
        });

        variableName.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getKey()));
        variableValue.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getValue()).asObject());

        heapAddress.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getKey()).asObject());
        heapValue.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getValue()).asObject());

        fileDescriptor.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getKey()).asObject());
        fileName.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getValue().getFileName()));

        semaphoreAddress.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getKey()).asObject());
        noThreads.setCellValueFactory(val -> new SimpleIntegerProperty(val.getValue().getValue().getKey()).asObject());
        threadsList.setCellValueFactory(val -> new SimpleObjectProperty<>(val.getValue().getValue().getValue()));
    }

    private void executeOneStep() {
        try {
            controller.oneStepExecution();
            System.out.println(controller.getRepository().getCurrentProgramState().toString());
            populateAll();
            if (executionEnded()) {
                new Alert(Alert.AlertType.INFORMATION, "Execution finished").showAndWait();
                executeOneStep();
            }
        }catch(ArrayIndexOutOfBoundsException ignored) {
            populateAll();
        }
    }

    private boolean executionEnded() {
        for (ProgramState ps : controller.getRepository().getProgramStateList() )
            if ( !ps.getExecutionStack().isEmpty() )
                return false;
        return true;
    }

    private void populateAll() {
        populateProgramStateView();
        populateProgramStatesNumber();
        if (controller.getRepository().getProgramStateList().isEmpty() ||
            controller.getRepository().getCurrentProgramState() == null)
            return;
        populateExecutionStack();
        populateOutputListView();
        populateSymbolsTableView();
        populateHeapTableView();
        populateFileTableView();
        populateSemaphoreTableView();
    }

    private void populateProgramStatesNumber() {
        programStatesNo.setText("Program States: " + Integer.toString(programStateView.getItems().size()));
    }

    private void populateExecutionStack() {
        try {
            int id = programStateView.getSelectionModel().getSelectedItem();

            IStack<IStatement> executionStack = controller.getRepository().getProgramStateById(id).getExecutionStack();

            List<String> executionStackList = new ArrayList<>();
            for (IStatement s : executionStack.toList()) {
                executionStackList.add(s.toString());
            }

            executionStackView.setItems(FXCollections.observableList(executionStackList));
            executionStackView.refresh();
        }catch(NullPointerException ignored) {};
    }

    private void populateOutputListView() {
        try {
            IList<Integer> values = controller.getRepository().getCurrentProgramState().getOutputList();
            outputListView.setItems(FXCollections.observableList(values.toList()));
            outputListView.refresh();
        }catch(ArrayIndexOutOfBoundsException ignored) {};
    }

    private void populateProgramStateView() {
        try {
            List<ProgramState> programStates = controller.getRepository().getProgramStateList();
            programStateView.setItems(FXCollections.observableList(getProgramStatesId(programStates)));
            programStateView.getSelectionModel().selectFirst();
            programStateView.refresh();
        }catch(NullPointerException ignored) {}
    }

    private List<Integer> getProgramStatesId(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void populateSymbolsTableView() {
        try {
            int id = programStateView.getSelectionModel().getSelectedItem();
            IDictionary<String, Integer> symbolsTable = controller.getRepository().getProgramStateById(id).getSymbolsTable();
            ArrayList<Map.Entry<String, Integer>> symbolsList = new ArrayList<>();

            symbolsList.addAll(symbolsTable.toMap().entrySet());

            symbolsTableView.setItems(FXCollections.observableList(symbolsList));
            symbolsTableView.refresh();
        }catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    private void populateHeapTableView() {
        try {
            IDictionary<Integer, Integer> heap = controller.getRepository().getCurrentProgramState().getHeap().getContent();
            ArrayList<Map.Entry<Integer, Integer>> heapList = new ArrayList<>();

            heapList.addAll(heap.toMap().entrySet());

            heapTableView.setItems(FXCollections.observableList(heapList));
            heapTableView.refresh();
        }catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    private void populateFileTableView() {
        try {
            IDictionary<Integer, FilePair> fileTable = controller.getRepository().getCurrentProgramState().getFileManager().
                    getFileTable();
            ArrayList<Map.Entry<Integer, FilePair>> fileList = new ArrayList<>();

            fileList.addAll(fileTable.toMap().entrySet());

            fileTableView.setItems(FXCollections.observableList(fileList));
            fileTableView.refresh();
        }catch(ArrayIndexOutOfBoundsException ignored) {}
    }

    private void populateSemaphoreTableView() {
        try {
            IDictionary<Integer, Pair<Integer, List<Integer>>> semaphoreTable = controller.getRepository().getCurrentProgramState().getSemaphoreManager().getSemaphore();
            ArrayList<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreThreadsList = new ArrayList<>();

            semaphoreThreadsList.addAll(semaphoreTable.toMap().entrySet());

            semaphoreTableView.setItems(FXCollections.observableList(semaphoreThreadsList));
            semaphoreTableView.refresh();
        }catch(ArrayIndexOutOfBoundsException ignored) {}
    }


}
