package batalha_naval.controller;

import java.net.URL;
import java.util.ResourceBundle;

import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.core.DAOFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaInicialController implements Initializable {

    @FXML
    private AnchorPane Fundo;

    @FXML
    private Button NovoJogo;

    @FXML
    private Button Cadastrar;

    @FXML
    private Button Ranking;

    @FXML
    private Button Sair;

    private Stage stageIniciarJogo;
    private Stage stageCadastrar;
    private Stage stageRanking;
    private TelaCadastrarController telaCadastrarController;
    private TelaRankingController telaRankingController;

    private DAOFactory daoFactory;

    public TelaInicialController() {
        /*ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL(
                "silly.db.elephantsql.com:5432/oaktlyql", "oaktlyql", "NUA1m5sBKJWVgSj1rRhPmabFT0-Ayc_u");
        daoFactory = new DAOFactory(conexaoFactory);*/
         ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL();
            daoFactory = new DAOFactory(conexaoFactory);
    }

    @FXML
    void ButtonNovoJogo(ActionEvent event) {
        if (stageIniciarJogo.getOwner() == null) {
            stageIniciarJogo.initOwner((Stage) Fundo.getScene().getWindow());
            ((Button) event.getSource()).getScene().getWindow().hide();
        }
        stageIniciarJogo.showAndWait();

    }

    @FXML
    void ButtonCadastrar(ActionEvent event) {
        if (stageCadastrar.getOwner() == null) {
            stageCadastrar.initOwner((Stage) Fundo.getScene().getWindow());
        }
        stageCadastrar.showAndWait();

    }

    @FXML
    void ButtonRanking(ActionEvent event) {
        if(stageRanking.getOwner() == null){
            stageRanking.initOwner((Stage) Fundo.getScene().getWindow());
        }
        stageRanking.showAndWait();

    }

    @FXML
    void ButtonSair(ActionEvent event) {
        if (event.getSource() == Sair) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent parent;
        FXMLLoader fxmlLoader;
        try {
            stageIniciarJogo = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaJogar.fxml"));
            parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            stageIniciarJogo.setScene(scene);
            stageIniciarJogo.setTitle("Batalha Naval");
            stageIniciarJogo.setResizable(false);
            stageIniciarJogo.initModality(Modality.APPLICATION_MODAL);

            stageRanking = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaRanking.fxml"));
            parent = fxmlLoader.load();
            telaRankingController = fxmlLoader.getController();
            telaRankingController.setDAOFactory(daoFactory);
            Scene scene1 = new Scene(parent);
            stageRanking.setScene(scene1);
            stageRanking.setTitle("Ranking");
            stageRanking.setResizable(false);
            stageRanking.initModality(Modality.APPLICATION_MODAL);

            stageCadastrar = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaCadastrar.fxml"));
            parent = fxmlLoader.load();
            telaCadastrarController = fxmlLoader.getController();
            telaCadastrarController.setDAOFactory(daoFactory);
            Scene scene2 = new Scene(parent);
            stageCadastrar.setScene(scene2);
            stageCadastrar.setTitle("Cadastrar Jogador");
            stageCadastrar.setResizable(false);
            stageCadastrar.initModality(Modality.APPLICATION_MODAL);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro carregando a aplicação!");
            alert.showAndWait();
            // Fecha a aplicação JavaFX
            Platform.exit();
        }
    }

}
