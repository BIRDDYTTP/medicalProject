import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ExaminalController {
    @FXML TextField p_id;
    @FXML TextField date;
    @FXML TextArea detail;
    @FXML Button submit;
    @FXML Button cancle;

    public void onClickCancleBtn (ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) cancle.getScene().getWindow();
        Scene scene = new Scene((Parent) loader.load());
        stage.setScene(scene);
    }

    public void onClickSubmitBtn (ActionEvent e) throws IOException {
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
                boolean flag = false;

                while (resultSet.next()){
                    String iddb = resultSet.getString("id");
                    if (p_id.getText().equals(iddb)){
                        flag = true;
                    }
                }

                if (flag){
                    query = "Select h_id from History";
                    statement = conn.createStatement();
                    resultSet = statement.executeQuery(query);
                    int max = 0;
                    while (resultSet.next()){
                        if (resultSet.getInt("h_id") > max){
                            max = resultSet.getInt("h_id");
                        }
                    }
                    String sql = "INSERT INTO History (p_id, date, detail, h_id)"+
                            " VALUES ('"+p_id.getText()+"','" + date.getText() + "', '" +detail.getText()+"','"+ Integer.parseInt(String.valueOf(max+1)) +"')";
                    statement.execute(sql);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                    Stage stage = (Stage) submit.getScene().getWindow();
                    Scene scene = new Scene((Parent) loader.load());
                    stage.setScene(scene);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ID not found!");
                    alert.setContentText("This ID isn't have in Clinic");
                    alert.showAndWait();
                }
            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
