import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SearchController {
    @FXML TextField input;
    @FXML Button search, back;
    @FXML TableView<History> tableView;
    @FXML TableColumn<History, Integer> h_id;
    @FXML TableColumn<History, String> date;
    @FXML TableColumn<History, String> detail;
    ObservableList<History> observableList = FXCollections.observableArrayList();

    public void onClickBackBtn(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }

    public void onClickSearchBtn(ActionEvent e){
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
                String id = input.getText();
                String query = "Select * from History WHERE p_id == " + id;
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    observableList.add(new History(resultSet.getString("p_id"), resultSet.getString("h_id"), resultSet.getString("detail"),resultSet.getString("date")));
                }

                conn.close();
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        h_id.setCellValueFactory(new PropertyValueFactory<History, Integer>("h_id"));
        date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        detail.setCellValueFactory(new PropertyValueFactory<History, String>("detail"));
        tableView.setItems(observableList);
    }
}
