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

public class AddPatientController {
    @FXML TextField name, lastname, age, id, address;
    @FXML Button regis, cancle;

    public void onClickCancleBtn (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) cancle.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }

    public void onClickRegisBtn(ActionEvent event) throws IOException {
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

                String query = "Select * from Patient";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                boolean flag = true;

                while (resultSet.next()){
                    String iddb = resultSet.getString("id");
                    if (id.getText().equals(iddb)){
                        flag = false;
                    }
                }

                if (flag){
                    String n = name.getText();
                    String ln = lastname.getText();
                    int a = Integer.parseInt(age.getText());
                    String ad = address.getText();
                    String i = id.getText();
                    String sql = "INSERT INTO Patient (name, lastname, age, address, id)"+
                            " VALUES ('"+ n +"' , '"+ ln +"', "+ a +" , '"+ad+"', '"+i+"')";
                    statement.execute(sql);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                    Stage stage = (Stage) regis.getScene().getWindow();
                    Scene scene = new Scene((Parent) loader.load());
                    stage.setScene(scene);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Regis Fail!");
                    alert.setContentText("This ID is have in Clinic");
                    alert.showAndWait();
                }
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
