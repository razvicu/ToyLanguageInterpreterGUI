package GUI;

import Controller.Controller;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.BooleanExpression;
import Model.Expressions.ConstExpression;
import Model.Expressions.HeapExpressions.ReadExpression;
import Model.Expressions.VarExpression;
import Model.ProgramState;
import Model.Statements.*;
import Model.Statements.FileStatements.CloseFileStatement;
import Model.Statements.FileStatements.OpenFileStatement;
import Model.Statements.FileStatements.ReadFileStatement;
import Model.Statements.HeapStatements.NewStatement;
import Model.Statements.HeapStatements.WriteStatement;
import Model.Statements.LoopStatements.ForStatement;
import Model.Statements.LoopStatements.WhileStatement;
import Model.Statements.ThreadStatements.ForkStatement;
import Model.Statements.ThreadStatements.Locks.LockStatement;
import Model.Statements.ThreadStatements.Locks.NewLockStatement;
import Model.Statements.ThreadStatements.Locks.UnlockStatement;
import Model.Statements.ThreadStatements.Semaphore.AcquireStatement;
import Model.Statements.ThreadStatements.Semaphore.NewSemaphoreStatement;
import Model.Statements.ThreadStatements.Semaphore.ReleaseStatement;
import Model.Utils.SyntaxTree;
import Repository.Repository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectController implements Initializable {
    private ArrayList<IStatement> statements;
    private MainController mainWindowController;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button selectButton;

    public void setMainController(MainController mc) {
        mainWindowController = mc;
    }


    private void initializeStatements() {
        /* v=2;Print(v) */
        IStatement ex1 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(2)),
                new PrintStatement(new VarExpression("v")));



        /* a=2+3*5;b=a+1;Print(b) */
        IStatement ex2 = new CompoundStatement(new AssignmentStatement("a",
                new ArithmeticExpression(ArithmeticExpression.Operation.ADD, new ConstExpression(2),
                        new ArithmeticExpression(ArithmeticExpression.Operation.MULTIPLY, new ConstExpression(3),
                                new ConstExpression(5)))),
                new CompoundStatement(new AssignmentStatement("b",
                        new ArithmeticExpression(ArithmeticExpression.Operation.ADD, new VarExpression("a"),
                                new ConstExpression(1))), new PrintStatement(new VarExpression("b"))));

        /* a=2-2;(If a Then v=2 Else v=3);Print(v) */
        IStatement ex3 = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(
                ArithmeticExpression.Operation.SUBTRACT,
                new ConstExpression(2), new ConstExpression(2))),
                new CompoundStatement(new IfStatement(new VarExpression("a"),
                        new AssignmentStatement("v", new ConstExpression(2)),
                        new AssignmentStatement("v", new ConstExpression(3))),
                        new PrintStatement(new VarExpression("v"))));

    /* openRFile(var_f,"test.in");
       readFile(var_f,var_c);print(var_c);
       (if var_c then readFile(var_f,var_c);print(var_c)
       else print(0));
       closeRFile(var_f) */
        IStatement ex4 = new CompoundStatement(new OpenFileStatement("tesut", "testut.in"),
                new CompoundStatement(new OpenFileStatement("testvar", "test.in"),
                        new CompoundStatement(new ReadFileStatement(
                                new VarExpression("testvar"), "var_c"),
                                new CompoundStatement(new PrintStatement(new VarExpression("var_c")),
                                        new CompoundStatement(new IfStatement(new VarExpression("var_c"),
                                                new CompoundStatement(new ReadFileStatement(new VarExpression("testvar"), "var_c"),
                                                        new PrintStatement(new VarExpression("var_c"))),
                                                new PrintStatement(new ConstExpression(0))),
                                                new CloseFileStatement(new VarExpression("testvar")))))));
    /* openRFile(var_f,"t.in");
       readFile(var_f,var_c);print(var_c);
       (if var_c then readFile(var_f,var_c);print(var_c)
       else print(0));
       closeRFile(var_f) */
        IStatement ex5 = new CompoundStatement(new OpenFileStatement("testvar", "t.in"),
                new CompoundStatement(new ReadFileStatement(new VarExpression("testvar"), "var_c"),
                        new CompoundStatement(new PrintStatement(new VarExpression("var_c")),
                                new CompoundStatement(new IfStatement(new VarExpression("var_c"),
                                        new CompoundStatement(new ReadFileStatement(
                                                new VarExpression("testvar"), "var_c"),
                                                new PrintStatement(new VarExpression("var_c"))),
                                        new PrintStatement(new ConstExpression(0))),
                                        new CloseFileStatement(new VarExpression("testvar"))))));



    /* v = 10;
       new(v,20);
       new(a,22);
       print(v);
     */
        IStatement ex7 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstExpression(22)),
                                new PrintStatement(new VarExpression("v")))));

    /* v=10
       new(v, 20)
       new(a, 22)
       print(100+rH(v))
       print(100+rH(a))
    */
        IStatement ex8 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstExpression(22)),
                                new CompoundStatement(
                                        new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.ADD,
                                                new ConstExpression(100),
                                                new ReadExpression("v"))),
                                        new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.ADD,
                                                new ConstExpression(100),
                                                new ReadExpression("a")))))));

    /* v = 10;
       new(v,20);
       new(a,22);
       wH(a,30);
       print(a);
       print(rH(a));
     */
        IStatement ex9 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstExpression(22)),
                                new CompoundStatement(new WriteStatement("a", new ConstExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VarExpression("a")),
                                                new PrintStatement(new ReadExpression("a")))))));

    /* v = 10;
       new(v,20);
       new(a,22);
       wH(a,30);
       print(a);
       a=0;
     */
        IStatement ex10 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstExpression(22)),
                                new CompoundStatement(new WriteStatement("a", new ConstExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VarExpression("a")), new
                                                CompoundStatement(new PrintStatement(new ReadExpression("a")),
                                                new AssignmentStatement("a", new ConstExpression(0))))))));


        /* print(10+2<6)
         */
        IStatement ex11 = new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.ADD,
                new ConstExpression(10),
                new BooleanExpression(BooleanExpression.Operator.LT,
                        new ConstExpression(2),
                        new ConstExpression(6))));

        /* print((10+2)<6)
         */
        IStatement ex12 = new PrintStatement(new BooleanExpression(BooleanExpression.Operator.LT, new ArithmeticExpression(ArithmeticExpression.Operation.ADD,
                new ConstExpression(10), new ConstExpression(2)), new ConstExpression(6)));


    /*  v=6
       (while(v-4)
        print(v)
        v=v-1
        )
        print(v)
     */
        IStatement ex13 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(6)),
                new CompoundStatement(new WhileStatement(new ArithmeticExpression(
                        ArithmeticExpression.Operation.SUBTRACT,
                        new VarExpression("v"),
                        new ConstExpression(4)),
                        new CompoundStatement(new PrintStatement(new VarExpression("v")), new AssignmentStatement("v", new
                                ArithmeticExpression(ArithmeticExpression.Operation.SUBTRACT, new VarExpression("v"),
                                new ConstExpression(1))))),
                        new PrintStatement(new VarExpression("v"))));

        /* v=10;new(a,22);
           fork(wH(a,30);v=32;print(v);print(rH(a)));
           print(v);print(rH(a))
        */

        IStatement ex14 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(10)),
                new CompoundStatement(new NewStatement("a", new ConstExpression(22)),
                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteStatement("a",
                                new ConstExpression(30)), new CompoundStatement(new AssignmentStatement("v",
                                new ConstExpression(32)), new CompoundStatement(new PrintStatement(new VarExpression("v")),
                                new PrintStatement(new ReadExpression("a")))))),
                                new CompoundStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new ReadExpression("a"))))));

        IStatement ex15 = new CompoundStatement(
                new AssignmentStatement("v", new ConstExpression(2)),
                new CompoundStatement(
                        new NewStatement("a", new ConstExpression(2)),
                        new CompoundStatement(
                                new ForkStatement(new CompoundStatement(
                                        new AssignmentStatement("v", new ConstExpression(4)),
                                        new CompoundStatement(
                                                new WriteStatement("a", new ConstExpression(3)),
                                                new CompoundStatement(
                                                        new ForkStatement(new CompoundStatement(
                                                                new AssignmentStatement("a", new ConstExpression(0)),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ConstExpression(6)),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VarExpression("v")),
                                                                                new PrintStatement(new VarExpression("v")))
                                                                )
                                                        )),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VarExpression("v")),
                                                                new PrintStatement(new ReadExpression("a"))
                                                        )
                                                )
                                        )
                                )),
                                new CompoundStatement(new AssignmentStatement("a", new ConstExpression(0)),
                                        new AssignmentStatement("a", new ConstExpression(1)))
                        )
                )
        );

        IStatement ex16 = new CompoundStatement(
                new AssignmentStatement("v",
                        new ConstExpression(20)),
                new CompoundStatement(
                        new ForStatement(
                                new AssignmentStatement("v",
                                        new ConstExpression(0)),
                                new BooleanExpression(
                                        BooleanExpression.Operator.LT,
                                        new VarExpression("v"),
                                        new ConstExpression(3)),
                                new AssignmentStatement(
                                        "v",
                                        new ArithmeticExpression(
                                                ArithmeticExpression.Operation.ADD,
                                                new VarExpression("v"),
                                                new ConstExpression(1))),
                                new ForkStatement(
                                        new CompoundStatement(
                                                new PrintStatement(new VarExpression("v")),
                                                new AssignmentStatement(
                                                        "v",
                                                        new ArithmeticExpression(
                                                                ArithmeticExpression.Operation.ADD,
                                                                new VarExpression("v"),
                                                                new ConstExpression(1)))
                                ))), new PrintStatement(
                                        new ArithmeticExpression(
                                                ArithmeticExpression.Operation.MULTIPLY,
                                                new VarExpression("v"),
                                                new ConstExpression(10)))));




        IStatement ex17 = new CompoundStatement(new NewStatement("v1", new ConstExpression(20)),
                new CompoundStatement(new NewStatement("v2", new ConstExpression(30)),
                        new CompoundStatement(new NewLockStatement("x"), new CompoundStatement(
                                new ForkStatement( new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"),
                                        new CompoundStatement(new WriteStatement("v1", new ArithmeticExpression(ArithmeticExpression.Operation.SUBTRACT, new ReadExpression("v1"), new ConstExpression(1))),
                                                new UnlockStatement("x")))),
                                        new CompoundStatement(new LockStatement("x"),
                                                new CompoundStatement(new WriteStatement("v1", new ArithmeticExpression(ArithmeticExpression.Operation.ADD, new ReadExpression("v1"), new ConstExpression(1))),
                                                        new UnlockStatement("x"))))),
                                new CompoundStatement(new NewLockStatement("q"),
                                        new CompoundStatement(new ForkStatement(
                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("q"),
                                                        new CompoundStatement(new WriteStatement("v2", new ArithmeticExpression(ArithmeticExpression.Operation.ADD, new ReadExpression("v2"), new ConstExpression(5))), new UnlockStatement("q")))),
                                                        new CompoundStatement(new AssignmentStatement("m", new ConstExpression(100)),
                                                                new CompoundStatement(new LockStatement("q"),new CompoundStatement(new WriteStatement("v2", new ArithmeticExpression(ArithmeticExpression.Operation.ADD, new ReadExpression("v2"), new ConstExpression(1))),new UnlockStatement("q")))))),
                                                new CompoundStatement(new AssignmentStatement("z", new ConstExpression(200)),
                                                        new CompoundStatement(new AssignmentStatement("z", new ConstExpression(300)),
                                                                new CompoundStatement(new AssignmentStatement("z", new ConstExpression(400)),
                                                                        new CompoundStatement(new LockStatement("x"),
                                                                                new CompoundStatement(new PrintStatement(new ReadExpression("v1")),
                                                                                        new CompoundStatement(new UnlockStatement("x"),
                                                                                                new CompoundStatement(new LockStatement("q"),
                                                                                                        new CompoundStatement(new PrintStatement(new ReadExpression("v2")),
                                                                                                                new UnlockStatement("q")))))))))))))));

        IStatement ex18 = new CompoundStatement(new AssignmentStatement("a", new ConstExpression(1)),
                          new CompoundStatement(new AssignmentStatement("b", new ConstExpression(2)),
                          new CompoundStatement(new ConditionalAssignmentStatement("c", new VarExpression("a"), new ConstExpression(100), new ConstExpression(200)),
                                  new CompoundStatement(new PrintStatement(new VarExpression("c")),
                                  new CompoundStatement(new ConditionalAssignmentStatement("c", new ArithmeticExpression(
                                          ArithmeticExpression.Operation.SUBTRACT,
                                          new VarExpression("b"),
                                          new ConstExpression(2)
                                  ), new ConstExpression(100), new ConstExpression(200)), new PrintStatement(new VarExpression("c")))))));

        IStatement ex19 = new CompoundStatement(new NewStatement("v1", new ConstExpression(1)),
                new CompoundStatement(new NewSemaphoreStatement("cnt", new ReadExpression("v1")),
                new CompoundStatement(new ForkStatement(
                        new CompoundStatement(new AcquireStatement("cnt"),
                                new CompoundStatement(new WriteStatement("v1", new ArithmeticExpression(ArithmeticExpression.Operation.MULTIPLY, new ReadExpression("v1"), new ConstExpression(10))),
                                new CompoundStatement(new PrintStatement(new ReadExpression("v1")), new ReleaseStatement("cnt"))))),
                   new CompoundStatement(new ForkStatement(
                   new CompoundStatement(new AcquireStatement("cnt"),
                   new CompoundStatement(new WriteStatement("v1", new ArithmeticExpression(
                           ArithmeticExpression.Operation.MULTIPLY,
                           new ReadExpression("v1"),
                           new ConstExpression(10))),
                   new CompoundStatement(new WriteStatement("v1", new ArithmeticExpression(
                           ArithmeticExpression.Operation.MULTIPLY,
                           new ReadExpression("v1"),
                           new ConstExpression(2))),
                   new CompoundStatement(new PrintStatement(new ReadExpression("v1")), new ReleaseStatement("cnt")))))),
                   new CompoundStatement(new AcquireStatement("cnt"),
                   new CompoundStatement(new PrintStatement(new ArithmeticExpression(ArithmeticExpression.Operation.SUBTRACT, new ReadExpression("v1"), new ConstExpression(1))), new ReleaseStatement("cnt")))))));

        IStatement ex20 = new CompoundStatement(new AssignmentStatement("v", new ConstExpression(1)),
                new CompoundStatement(new ForkStatement(new AssignmentStatement("v", new ConstExpression(2))),
                        new ForkStatement(new AssignmentStatement("v", new ConstExpression(3)))));

        statements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex7, ex8, ex9, ex10, ex11,
                                     ex12, ex13, ex14, ex15, ex16, ex17, ex18, ex19, ex20));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeStatements();
        List<String> stringStatements = statements.stream().map(Object::toString).collect(Collectors.toList());
        listView.setItems(FXCollections.observableArrayList(stringStatements));

        selectButton.setOnAction(event -> {
            int idx = listView.getSelectionModel().getSelectedIndex();

            ProgramState programState = new ProgramState(statements.get(idx));
            Repository repository = new Repository(programState, "fromGUI");
            Controller controller = new Controller(repository);

            mainWindowController.setController(controller);
            Stage st = (Stage)selectButton.getScene().getWindow();
            st.close();
        });
    }
}
