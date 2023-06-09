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
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Pessoa;
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

    public PessoaFilter getJogadorfilter1() {
        return Jogadorfilter1;
    }

    public PessoaFilter getJogadorfilter2() {
        return Jogadorfilter2;
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

    @Override
    public void run() {
        while (true) {
            System.out.println("Contador: " + contador);
            if (contador == 0) {
                CompletableFuture<Void> conexaoFuture = CompletableFuture.runAsync(() -> {
                    try {
                        /*
                         * ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL(
                         * "silly.db.elephantsql.com:5432/oaktlyql", "oaktlyql",
                         * "NUA1m5sBKJWVgSj1rRhPmabFT0-Ayc_u");
                         * daoFactory = new DAOFactory(conexaoFactory);
                         * daoFactory.abrirConexao();
                         */
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
                    Jogadorfilter1 = getJogadorfilter1();
                    Jogadorfilter2 = getJogadorfilter2();
                    TextFieldJogador1.setText(Jogadorfilter1.getNome());
                    TextFieldJogador2.setText(Jogadorfilter2.getNome());
                    System.out.println("Abriu??");
                    contador++;
                } catch (Exception e) {
                    System.out.println("Erro ao abrir a conexão");
                }
            }

            // Prossiga com o código após a abertura da conexão
            if (daoFactory != null) {
                Platform.runLater(() -> {
                    TextFieldTempo.setText(String.valueOf(contador));
                });
            }

            // Restante do código...

        }
    }
}
