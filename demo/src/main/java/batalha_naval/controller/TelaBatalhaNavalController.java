package batalha_naval.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Filter.PessoaFilter;

public class TelaBatalhaNavalController implements Initializable, Runnable {

    @FXML
    private Text labelNomeJogador2;

    @FXML
    private Text LabelNomeJogador1;

    @FXML
    private Text LabelTempo;

    @FXML
    private Text LabelAcertoJogador1;

    @FXML
    private Text LabelAcertoJogador2;

    @FXML
    private Text LabelErrosJogador12;

    @FXML
    private Text LabelErrosJogador2;

    @FXML
    private GridPane GridPane1;

    @FXML
    private GridPane GridPane2;

    @FXML
    private Text LabelPontosJogador1;

    @FXML
    private Text LabelPontosJogador2;

    @FXML
    private Text NomeJogador1;

    @FXML
    private Label Labeltext;

    private Button[][] buttons1;
    private Button[][] buttons2;
    private Instant tempoinicial = Instant.now();
    private Duration duracao;
    private PessoaFilter Jogadorfilter1;
    private PessoaFilter Jogadorfilter2;
    private String nomeJogador1;
    private String nomeJogador2;
    private DAOFactory daoFactory;
    private Runnable myRunnable;

    public TelaBatalhaNavalController() {
        // Thread threadVerificaBarcos = new Thread(myRunnable);
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setNomeJogador1(String text) {
        this.nomeJogador1 = text;

    }

    public void setNomeJogador2(String text) {
        this.nomeJogador2 = text;

    }

    public String getNomeJogador1() {
        return nomeJogador1;
    }

    public String getNomeJogador2() {
        return nomeJogador2;
    }

    public void inicializarCampos() {
        LabelPontosJogador1.setText("0");
        LabelPontosJogador2.setText("0");
        LabelAcertoJogador1.setText("0");
        LabelAcertoJogador2.setText("0");
        LabelErrosJogador12.setText("0");
        LabelErrosJogador2.setText("0");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons1 = new Button[10][10];
        buttons2 = new Button[10][10];
        inicializarCampos();
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
                button1.setOnMouseEntered(event -> {
                    Button b = (Button) event.getSource();
                    b.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-text-fill: red");
                });
                button1.setOnMouseExited(event -> {
                    Button b = (Button) event.getSource();
                    b.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                });
                GridPane1.add(button1, col, row);

                Button button2 = new Button();
                button2.setMinSize(50, 50);
                button2.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                buttons2[row][col] = button2;
                button2.setText("button2[" + row + "][" + col + "]]");
                button2.setUserData("Agua");
                if (button2.getUserData().equals("Agua")) {
                    button2.setOnMouseEntered(event -> {
                        Button b = (Button) event.getSource();
                        b.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-text-fill: red");
                    });
                    button2.setOnMouseExited(event -> {
                        Button b = (Button) event.getSource();
                        b.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                    });
                }
                button2.setOnAction(buttonClickHandler);
                GridPane2.add(button2, col, row);
            }
        }
        threadVerificaBarcos verificaBarcos = new threadVerificaBarcos();
        verificaBarcos.start(); 
    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            clickedButton.setUserData("Barco");
            clickedButton.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-text-fill: green");
            System.out.println("Botão clicado: " + clickedButton.getText() + "valor" + clickedButton.getUserData());
        }
    }

    private List<PessoaFilter> dadosBanco(String nome1, String nome2) {
        try {
            ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL();
            daoFactory = new DAOFactory(conexaoFactory);
            daoFactory.abrirConexao();
            PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
            List<PessoaFilter> lista = new ArrayList<>();
            Jogadorfilter1 = new PessoaFilter();
            Jogadorfilter1 = dao.findByname(nome1);
            Jogadorfilter2 = new PessoaFilter();
            Jogadorfilter2 = dao.findByname(nome2);
            lista.add(Jogadorfilter1);
            lista.add(Jogadorfilter2);
            daoFactory.fecharConexao();
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void atualizarTela() {
        Platform.runLater(() -> {
            LabelTempo.setText(duracao.getSeconds() + "s");
            LabelNomeJogador1.setText(getNomeJogador1());
            labelNomeJogador2.setText(getNomeJogador2());

            // Atualize os campos
        });
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            /*
             * for(int i = 0; i < 10; i++){
             * for(int j = 0; j < 10; j++){
             * if(buttons1[i][j].getUserData().equals("Barco")){
             * buttons1[i][j].
             * setStyle("-fx-background-color: green; -fx-border-color: black; -fx-text-fill: green"
             * );
             * }
             * }
             * }
             */
            Duration duracao = Duration.between(tempoinicial, Instant.now());
            this.duracao = duracao;
            System.out.println(duracao.getSeconds() + "s");
            atualizarTela();
        }
    }

    public class threadVerificaBarcos extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (buttons1[i][j].getUserData().equals("Barco")) {
                            buttons1[i][j].setStyle(
                                    "-fx-background-color: green; -fx-border-color: black; -fx-text-fill: green");
                        }
                    }
                }
            }
        }
    }

}