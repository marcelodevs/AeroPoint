package Model;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InitController extends Application implements Initializable {

    static Stage stage1 = new Stage();

    public static Stage getStage() { // Entrega a janela
        return stage1;
    }

    public static void setStage(Stage stage) {
        InitController.stage1 = stage;
    }

    @FXML
    private AnchorPane archorpane;

    @FXML
    private Button btnstart;

    @FXML
    private TextArea inputNomeUser;

    @FXML
    private TextArea inputSenhaUser;

    @FXML
    void initi(ActionEvent event) throws Exception {

        // Recebe os valores que o usuário colocou nos Inputs
        String nomeUser = inputNomeUser.getText();
        String senhaUser = inputSenhaUser.getText();

        // Verifica se os valores que o usuário colocou são coerentes à alguém dos funcionários (nossa equipe)
        if (nomeUser.equals("Paulo") && senhaUser.equals("aeropoint1") ||
            nomeUser.equals("Expedito") && senhaUser.equals("aeropoint2") ||
            nomeUser.equals("Mariana") && senhaUser.equals("aeropoint3") ||
            nomeUser.equals("Marcelo") && senhaUser.equals("aeropoint4")) {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null); // Título nulo
            alert.setContentText("Bem vindo " +nomeUser+ "!"); //Dizer o conteúdo do alerta
            alert.showAndWait(); // Mostra o alerta
            
            TelaPrincipalController home = new TelaPrincipalController(); // chama a TelaPrincipalController
            Stage stageHome = new Stage();
            home.start(stageHome);
            stage1.close(); // 'mata' a tela atual

        } else { // Caso um dos valores que o usuário colocou estja errado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("DADOS INVÁLIDOS!\nTENTE NOVAMENTE");
            alert.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Init.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Home");
        stage.setResizable(false);
        stage.setScene(scene);
        setStage(stage);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
