import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AllPatientController implements Initializable {
    @FXML TableView<Patient> tableView;
    @FXML TableColumn<Patient, String> id;
    @FXML TableColumn<Patient, String> name;
    @FXML TableColumn<Patient, String> lastname;
    @FXML TableColumn<Patient, String> address;
    @FXML TableColumn<Patient, Integer> age;
    @FXML Button back;
    ObservableList<Patient> observableList = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:test.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("connected to database");
                DatabaseMetaData dm = conn.getMetaData();

                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Prodect  name: " + dm.getDatabaseProductName());

                System.out.println("------- Data in table --------");

                String query = "Select * from Patient";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    observableList.add(new Patient(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("lastname"), resultSet.getString("address"),resultSet.getInt("age")));
                }
                conn.close();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id.setCellValueFactory(new PropertyValueFactory<Patient, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        lastname.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastname"));
        age.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("age"));
        address.setCellValueFactory(new PropertyValueFactory<Patient, String>("address"));
        tableView.setItems(observableList);
    }

    public void onClickBackBtn (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }
}
