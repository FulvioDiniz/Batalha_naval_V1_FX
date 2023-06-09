package batalha_naval.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;

import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;

import batalha_naval.model.Filter.PessoaFilter;

public class TelaBatalhaNavalController implements Initializable, Runnable {

    @FXML
    private GridPane GridPane1;

    @FXML
    private GridPane GridPane2;

    @FXML
    private TextField TextFieldAcertos1;

    @FXML
    private TextField TextFieldAcertos2;

    @FXML
    private TextField TextFieldErros1;

    @FXML
    private TextField TextFieldErros2;

    @FXML
    private TextField TextFieldJogador1;

    @FXML
    private TextField TextFieldJogador2;

    @FXML
    private TextField TextFieldPonto1;

    @FXML
    private TextField TextFieldPonto2;

    @FXML
    private TextField TextFieldTempo;

    private Button[][] buttons1;
    private Button[][] buttons2;
    private DAOFactory daoFactory;
    private PessoaFilter Jogadorfilter1;
    private PessoaFilter Jogadorfilter2;
    private int contador = 0;
    private Instant tempoinicial = Instant.now();
    private Duration duracao;

    public TelaBatalhaNavalController() {
    }

    public TelaBatalhaNavalController(PessoaFilter Jogadorfilter1, PessoaFilter Jogadorfilter2) {
        inicializarVariaveis();
        this.Jogadorfilter1 = Jogadorfilter1;
        this.Jogadorfilter2 = Jogadorfilter2;

    }

    private void inicializarVariaveis() {
        TextFieldJogador1 = new TextField();
        TextFieldJogador2 = new TextField();
        TextFieldAcertos1 = new TextField();
        TextFieldAcertos2 = new TextField();
        TextFieldErros1 = new TextField();
        TextFieldErros2 = new TextField();
        TextFieldPonto1 = new TextField();
        TextFieldPonto2 = new TextField();
        TextFieldTempo = new TextField();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons1 = new Button[10][10];
        buttons2 = new Button[10][10];
        EventHandler<ActionEvent> buttonClickHandler = new ButtonClickHandler();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button button1 = new Button();
                button1.setMinSize(50, 50);
                button1.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                buttons1[row][col] = button1;
                button1.setOnAction(buttonClickHandler);
                button1.setText("button1[" + row + "][" + col + "]]");
                button1.setUserData("Agua");
                GridPane1.add(button1, col, row);

                Button button2 = new Button();
                button2.setMinSize(50, 50);
                button2.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                buttons2[row][col] = button2;
                button2.setText("button2[" + row + "][" + col + "]]");
                button2.setUserData("Agua");
                button2.setOnAction(buttonClickHandler);
                GridPane2.add(button2, col, row);
            }
        }
    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            // Faça algo com o botão clicado
            System.out.println("Botão clicado: " + clickedButton.getText() + "valor" + clickedButton.getUserData());
        }
    }

    private void atualizarTela() {
        Platform.runLater(() -> {
            TextFieldJogador1.setText(Jogadorfilter1.getNome());
            TextFieldJogador2.setText(Jogadorfilter2.getNome());
            TextFieldTempo.setText(duracao.getSeconds() + "s");
            // Atualize os outros componentes conforme necessário
        });
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Contador: " + contador);
            if (contador == 0) {
                CompletableFuture<Void> conexaoFuture = CompletableFuture.runAsync(() -> {
                    try {
                        ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL();
                        daoFactory = new DAOFactory(conexaoFactory);
                        daoFactory.abrirConexao();
                        contador++;
                    } catch (Exception e) {
                        System.out.println("Erro ao conectar ao banco de dados");
                    }
                });
                try {
                    conexaoFuture.join(); // Aguarda a conclusão da conexão
                    PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
                    System.out.println("Abriu??");
                    contador++;
                    daoFactory.fecharConexao();
                } catch (Exception e) {
                    System.out.println("Erro ao abrir a conexão");
                }
            }
            try {
                Thread.sleep(1000);
                Duration duracao = Duration.between(tempoinicial, Instant.now());
                this.duracao = duracao;
                atualizarTela(); // Chama o método para atualizar a tela
                System.out.println(TextFieldTempo.getText());
                System.out.println(TextFieldJogador1.getText());// Atualiza a tela
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}