import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Button login;
    public void onClickLoginBtn (ActionEvent e){
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:test.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null){
                System.out.println("connected to database");
                DatabaseMetaData dm = conn.getMetaData();

                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Prodect  name: " + dm.getDatabaseProductName());

                System.out.println("------- Data in table --------");

                String query = "Select * from Admin";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    String usernamedb = resultSet.getString("username");
                    String passworddb = resultSet.getString("password");
                    System.out.println(usernamedb.equals(username.getText()));
                    System.out.println(passworddb.equals(password.getText()));
                    if (username.getText().equals(usernamedb) && password.getText().equals(passworddb)){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                        Stage stage = (Stage) login.getScene().getWindow();
                        Scene scene = new Scene((Parent) loader.load());
                        stage.setScene(scene);
                    }
                    else{
                        username.setText("");
                        password.setText("");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Fail!");
                        alert.setContentText("Wrong username or password");
                        alert.showAndWait();
                    }
                }

                conn.close();
            }


        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
