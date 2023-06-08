package batalha_naval.controller;

import java.net.URL;
import java.util.ResourceBundle;

import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TelaLoginController implements Initializable {

    @FXML
    private Button ButtonFecharLogin;

    @FXML
    private Button ButtonJogar;

    @FXML
    private TextField TextFieldNomeJogador1;

    @FXML
    private TextField TextFieldNomeJogador2;

    @FXML
    private TextField TextFieldSenhaJogador1;

    @FXML
    private TextField TextFieldSenhaJogador2;

    private Stage stageIniciarJogo;
    private Stage stageTelaCadastrar;
    private DAOFactory daoFactory;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    

    private boolean loginEhValido() {
        if(TextFieldNomeJogador1.getText().isEmpty() || TextFieldSenhaJogador1.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return false;
        }
        else{
            try {
                daoFactory.abrirConexao();
                PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
                if(dao.findByNameAndPassword(TextFieldNomeJogador1.getText(),TextFieldSenhaJogador1.getText())){
                    return true;
                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.setTitle("ERRO");
                    alert.setHeaderText("Erro");
                    alert.setContentText("Usuário ou senha incorretos!");
                    alert.showAndWait();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.setTitle("ERRO");
                alert.setHeaderText("Erro");
                alert.setContentText("Erro ao conectar com o banco de dados!");
                alert.showAndWait();
                return false;
            }
        }
    }

    @FXML
    void ButtonFecharLoginClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
void ButtonJogarClicado(ActionEvent event) {
    if (loginEhValido()) {
        if (stageIniciarJogo.getOwner() == null) {
            stageIniciarJogo.initOwner((Stage) ButtonJogar.getScene().getWindow());
        }
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.hide();
        stageIniciarJogo.show();

    } else {
        // problema em abrir a tela
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaCadastrar.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene2 = new Scene(parent);
            stageTelaCadastrar.setScene(scene2); 
            stageTelaCadastrar.setTitle("Batalha Naval");
            stageTelaCadastrar.setResizable(false);
            stageTelaCadastrar.initModality(Modality.APPLICATION_MODAL);

            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.hide();
            stageTelaCadastrar.show();
        } 
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro ao carregar a aplicação!");
            alert.showAndWait();
            // fecha a aplicação JavaFX
            Platform.exit();
        }        
    }
}

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent parent;
        FXMLLoader fxmlLoader;
        try {
            stageIniciarJogo = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaBatalhaNaval.fxml"));
            parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            stageIniciarJogo.setScene(scene);
            stageIniciarJogo.setTitle("Batalha Naval");
            stageIniciarJogo.setResizable(false);
            stageIniciarJogo.initModality(Modality.APPLICATION_MODAL);


        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro carregando a aplicação!");
            alert.showAndWait();
            // closes the javafx application
            Platform.exit();

        }

    }

}
