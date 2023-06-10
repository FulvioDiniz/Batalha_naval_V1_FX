package batalha_naval.controller;



import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Filter.PessoaFilter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaLoginController {

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
    private PessoaFilter Jogadorfilter1;
    private PessoaFilter Jogadorfilter2;
    private String nomeJogador1;
    private String nomeJogador2;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public DAOFactory getDAOFactory() {
        return daoFactory;
    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void setNomeJogador1(String nomeJogador1) {
        this.nomeJogador1 = nomeJogador1;
    }

    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }

    private boolean loginEhValido() {
        if (TextFieldNomeJogador1.getText().isEmpty() || TextFieldSenhaJogador1.getText().isEmpty()
                || TextFieldNomeJogador2.getText().isEmpty() || TextFieldSenhaJogador2.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return false;
        } else {
            try {
                ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL();
                daoFactory = new DAOFactory(conexaoFactory);
                daoFactory.abrirConexao();
                PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
                if (dao.findByNameAndPassword(TextFieldNomeJogador1.getText(), TextFieldSenhaJogador1.getText()) && dao
                        .findByNameAndPassword(TextFieldNomeJogador2.getText(), TextFieldSenhaJogador2.getText())) {
                    // System.out.println("Login válido");  
                    Jogadorfilter1 = new PessoaFilter();
                    Jogadorfilter1.setNome(TextFieldNomeJogador1.getText());
                    Jogadorfilter2 = new PessoaFilter();
                    Jogadorfilter2.setNome(TextFieldNomeJogador2.getText());
                    daoFactory.fecharConexao();
                    return true;
                } else {
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
            abrirTelaJogo();
            if (stageIniciarJogo.getOwner() == null) {
                try {
                    stageIniciarJogo.initOwner((Stage) ButtonJogar.getScene().getWindow());
                    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    currentStage.hide();
                    stageIniciarJogo.show();
                    //daoFactory.fecharConexao();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Login inválido");
            Parent parent;
            FXMLLoader fxmlLoader;
            try {
                stageTelaCadastrar = new Stage();
                fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaCadastrar.fxml"));
                parent = fxmlLoader.load();
                Scene scene2 = new Scene(parent);
                stageTelaCadastrar.setScene(scene2);
                stageTelaCadastrar.setTitle("Cadastrar Jogador");
                stageTelaCadastrar.setResizable(false);
                stageTelaCadastrar.initModality(Modality.APPLICATION_MODAL);
                if (stageTelaCadastrar.getOwner() == null) {
                    stageTelaCadastrar.initOwner((Stage) ButtonJogar.getScene().getWindow());
                    Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    currentStage.hide();
                    stageTelaCadastrar.show();
                }
            } catch (Exception e) {
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

    public void abrirTelaJogo() {
        Parent parent;
        FXMLLoader fxmlLoader;
        try {
            stageIniciarJogo = new Stage();
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/poov/telas/TelaBatalhaNaval.fxml"));
            parent = fxmlLoader.load();
            TelaBatalhaNavalController controller = fxmlLoader.getController();
            controller.setNomeJogador1(TextFieldNomeJogador1.getText());
            controller.setNomeJogador2(TextFieldNomeJogador2.getText());
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
