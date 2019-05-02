import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage)throws Exception{
        Class.forName("org.sqlite.JDBC");
        String dbURL = "jdbc:sqlite:test.db";
        Connection connection = DriverManager.getConnection(dbURL);
        if (connection != null){
            System.out.println("Connecting to Database .....");

            DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
        }
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FAM Clinic");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
