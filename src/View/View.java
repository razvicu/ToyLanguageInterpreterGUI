package View;

import Controller.Controller;
import Model.*;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.BooleanExpression;
import Model.Expressions.ConstExpression;
import Model.Expressions.HeapExpressions.ReadExpression;
import Model.Expressions.VarExpression;
import Model.Statements.*;
import Model.Statements.FileStatements.CloseFileStatement;
import Model.Statements.FileStatements.OpenFileStatement;
import Model.Statements.FileStatements.ReadFileStatement;
import Model.Statements.HeapStatements.NewStatement;
import Model.Statements.HeapStatements.WriteStatement;
import Model.Statements.LoopStatements.WhileStatement;
import Model.Statements.ThreadStatements.ForkStatement;
import Model.Utils.SyntaxTree;
import Repository.*;
import View.Commands.Command;
import View.Commands.ExitCommand;
import View.Commands.RunExampleCommand;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;

import javax.naming.ldap.Control;
import java.util.ArrayList;


public class View {
    public static void main(String[] args) {

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


        /* openRFile(var_f,"test.in");
           readFile(var_f + 2,var_c);print(var_c);
           (if var_c then readFile(var_f,var_c);print(var_c)
           else print(0));
           closeRFile(var_f) */
        IStatement brokenEx = new CompoundStatement(new OpenFileStatement("testvar", "test.in"),
                new CompoundStatement(new ReadFileStatement(new ArithmeticExpression(ArithmeticExpression.Operation.ADD,
                        new VarExpression("testvar"), new ConstExpression(2)), "var_c"),
                        new CompoundStatement(new PrintStatement(new VarExpression("var_c")),
                        new CompoundStatement(new IfStatement(new VarExpression("var_c"),
                        new CompoundStatement(new ReadFileStatement(new VarExpression("testvar"), "var_c"),
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


        ArrayList<IStatement> a1 = new ArrayList<>();
        a1.add(new AssignmentStatement("v", new ConstExpression(10)));
        a1.add(new NewStatement("v", new ConstExpression(20)));
        a1.add(new NewStatement("a", new ConstExpression(22)));
        a1.add(new PrintStatement(new VarExpression("v")));
        IStatement someex = SyntaxTree.makeTree(a1);
        System.out.print(someex);
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
                                new PrintStatement(new ReadExpression("a"))) ))),
                                new CompoundStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new ReadExpression("a"))))));

        IStatement ex15 = new CompoundStatement(
                new AssignmentStatement("v", new ConstExpression(2)),
                new CompoundStatement (
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
                                        new AssignmentStatement("a", new ConstExpression(0))
                        )
                )
        );

        ProgramState programState1 = new ProgramState(ex1);
        IRepository repository1 = new Repository(programState1, "log1");
        Controller controller1 = new Controller(repository1);

        ProgramState programState2 = new ProgramState(ex2);
        IRepository repository2 = new Repository(programState2, "log2");
        Controller controller2 = new Controller(repository2);

        ProgramState programState3 = new ProgramState(ex3);
        IRepository repository3 = new Repository(programState3, "log3");
        Controller controller3 = new Controller(repository3);

        ProgramState programState4 = new ProgramState(ex4);
        Repository repository4 = new Repository(programState4, "log4");
        Controller controller4 = new Controller(repository4);

        ProgramState programState5 = new ProgramState(ex5);
        Repository repository5 = new Repository(programState5, "log5");
        Controller controller5 = new Controller(repository5);

        ProgramState programState6 = new ProgramState(brokenEx);
        Repository repository6 = new Repository(programState6, "log6");
        Controller controller6 = new Controller(repository6);

        ProgramState programState7 = new ProgramState(ex7);
        IRepository repository7 = new Repository(programState7, "log7");
        Controller controller7 = new Controller(repository7);

        ProgramState programState8 = new ProgramState(ex8);
        IRepository repository8 = new Repository(programState8, "log8");
        Controller controller8 = new Controller(repository8);

        ProgramState programState9 = new ProgramState(ex9);
        IRepository repository9 = new Repository(programState9, "log9");
        Controller controller9 = new Controller(repository9);

        ProgramState programState10 = new ProgramState(ex10);
        IRepository repository10 = new Repository(programState10, "log10");
        Controller controller10 = new Controller(repository10);


        ProgramState programState11 = new ProgramState(ex11);
        IRepository repository11 = new Repository(programState11, "log11");
        Controller controller11 = new Controller(repository11);

        ProgramState programState12 = new ProgramState(ex12);
        IRepository repository12 = new Repository(programState12, "log12");
        Controller controller12 = new Controller(repository12);

        ProgramState programState13 = new ProgramState(ex13);
        IRepository repository13 = new Repository(programState13, "log13");
        Controller controller13 = new Controller(repository13);

        ProgramState programState14 = new ProgramState(ex14);
        IRepository repository14 = new Repository(programState14, "log14");
        Controller controller14 = new Controller(repository14);

        ProgramState programState15 = new ProgramState(ex15);
        IRepository repository15 = new Repository(programState15, "log15");
        Controller controller15 = new Controller(repository15);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit\n"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", brokenEx.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));
        menu.addCommand(new RunExampleCommand("GC", ex10.toString(), controller10));
        menu.addCommand(new RunExampleCommand("LT", ex11.toString(), controller11));
        menu.addCommand(new RunExampleCommand("LT0", ex12.toString(), controller12));
        menu.addCommand(new RunExampleCommand("WHILE", ex13.toString(), controller13));
        menu.addCommand(new RunExampleCommand("FORK", ex14.toString(), controller14));
        menu.addCommand(new RunExampleCommand("PUSCA", ex15.toString(), controller15));
        menu.show();

    }
}
