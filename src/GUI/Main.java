package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = mainLoader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();

        MainController mainController = mainLoader.getController();


        FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("secondWindow.fxml"));
        Parent child = secondLoader.load();

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select a program");
        secondaryStage.setScene(new Scene(child, 500, 500));
        secondaryStage.show();

        SelectController selectController = secondLoader.getController();

        selectController.setMainController(mainController);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
