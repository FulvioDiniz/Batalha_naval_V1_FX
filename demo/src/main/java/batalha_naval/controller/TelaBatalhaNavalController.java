package batalha_naval.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Barco;
import batalha_naval.model.Filter.PessoaFilter;

public class TelaBatalhaNavalController implements Initializable, Runnable {

    @FXML
    private Text TituloLabel;

    @FXML
    private Button ButtonCoura1;

    @FXML
    private Button ButtonCoura2;

    @FXML
    private Button ButtonPorta1;

    @FXML
    private Button ButtonPorta2;

    @FXML
    private Button ButtonSub1;

    @FXML
    private Button ButtonSub2;

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
    private Barco barco;
    private String nomeBarco;
    private Stage primaryStage;
    private int contadordeSubmarinos = 0;
    private int contadordePortaAvioes = 0;
    private int contadordeCouracados = 0;
    // private boolean validador = false;

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

    public void setNomeBarco(String text) {
        this.nomeBarco = text;
    }

    public String getNomeBarco() {
        return nomeBarco;
    }

    public void inicializarCampos() {
        LabelPontosJogador1.setText("0");
        LabelPontosJogador2.setText("0");
        LabelAcertoJogador1.setText("0");
        LabelAcertoJogador2.setText("0");
        LabelErrosJogador12.setText("0");
        LabelErrosJogador2.setText("0");
        // TituloLabel.setText("Posicione o seu barco Jogador " + getNomeJogador1());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage(Stage primaryStage) {
        return primaryStage;
    }

    private void configurarEncerramentoJanela() {
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
            Thread.currentThread().interrupt();
            threadVerificaBarcos.currentThread().interrupt();
        });
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
                button2.disableProperty().set(true);
                ButtonSub2.disableProperty().set(true);
                ButtonPorta2.disableProperty().set(true);
                ButtonCoura2.disableProperty().set(true);
                ButtonSub2.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-text-fill: black");
                ButtonPorta2.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-text-fill: black");
                ButtonCoura2.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-text-fill: black");
                ButtonSub1.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-text-fill: black");
                ButtonPorta1.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-text-fill: black");
                ButtonCoura1.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-text-fill: black");
                button2.setOnAction(buttonClickHandler);
                GridPane2.add(button2, col, row);
            }
        }
        Thread thread = new Thread(this);
        thread.start();
        threadVerificaBarcos verificaBarcos = new threadVerificaBarcos();
        verificaBarcos.start();
    }

    @FXML
    void ButtonCoura1Clicado(ActionEvent event) {
        nomeBarco = "Couracado";
        setNomeBarco("Couracado");
        System.out.println("Couracado clicado" + contadordeCouracados + "vezes");
        if (contadordeCouracados == 1) {
            ButtonCoura1.disableProperty().set(true);
        } else {
            ButtonCoura1.disableProperty().set(false);
        }
        contadordeCouracados++;
    }

    @FXML
    void ButtonCoura2Clicado(ActionEvent event) {

    }

    @FXML
    void ButtonPorta1Clicado(ActionEvent event) {
        nomeBarco = "PortaAvioes";
        setNomeBarco("PortaAvioes");
        if (contadordePortaAvioes == 0) {
            ButtonPorta1.disableProperty().set(true);
        }
        contadordePortaAvioes++;
    }

    @FXML
    void ButtonPorta2Clicado(ActionEvent event) {

    }

    @FXML
    void ButtonSub1Clicado(ActionEvent event) {
        nomeBarco = "Submarino";
        setNomeBarco("Submarino");
        System.out.println("Submarino clicado" + contadordeSubmarinos + "vezes");
        if (contadordeSubmarinos == 5) {
            ButtonSub1.disableProperty().set(true);
        }
        contadordeSubmarinos++;

    }

    @FXML
    void ButtonSub2Clicado(ActionEvent event) {

    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            int row = GridPane.getRowIndex(clickedButton);
            int col = GridPane.getColumnIndex(clickedButton);
            boolean validador = false;
            if (nomeBarco.equals("Submarino") && clickedButton.getUserData().equals("Agua")) {
                if (row != 0) {
                    Button botaoAcima = buttons1[row - 1][col];
                    if (botaoAcima.getUserData().equals("Agua")) {
                        clickedButton.setUserData("Submarino");
                        botaoAcima.setUserData("Submarino");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro ao colocar o submarino");
                        alert.setContentText("O submarino não pode ser colocado aqui");
                        alert.showAndWait();
                    }
                }
                nomeBarco = "";
            }
            if (nomeBarco.equals("PortaAvioes") && clickedButton.getUserData().equals("Agua")) {
                boolean validadorCouracado = true;
                boolean validador3 = true;
                for (int i = 0; i < 5; i++) {
                    if (row - i >= 0) { // Verifica se o índice é válido
                        Button botaoAcima = buttons1[row - i][col];
                        if (!botaoAcima.getUserData().equals("Agua")) {
                            validador3 = false;
                            break;
                        }
                    } else {
                        validador3 = false;
                        break;
                    }
                }

                if (validador3) {
                    for (int j = 0; j < 5; j++) {
                        if (row - j >= 0 && col >= 0) { // Verifica se os índices são válidos
                            Button botaoAcima2 = buttons1[row - j][col];
                            clickedButton.setUserData("PortaAvioes");
                            botaoAcima2.setUserData("PortaAvioes");
                        } else {
                            validadorCouracado = false;
                            break;
                        }
                    }
                }

                if (!validadorCouracado || !validador3) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao posicionar o barco");
                    alert.setContentText("O Porta Avioes não pode ser posicionado aqui");
                    if (contadordePortaAvioes > 0) {
                        contadordePortaAvioes--;
                        ButtonPorta1.disableProperty().set(false);
                    }

                    alert.showAndWait();
                }

                nomeBarco = "";
            }

            if (nomeBarco.equals("Couracado") && clickedButton.getUserData().equals("Agua")) {
                boolean validadorCouracado = true;
                boolean validador2 = true;

                for (int i = 0; i < 4; i++) {
                    if (row - i >= 0) { // Verifica se o índice é válido
                        Button botaoAcima = buttons1[row - i][col];
                        if (!botaoAcima.getUserData().equals("Agua")) {
                            validador2 = false;
                            break;
                        }
                    } else {
                        validador2 = false;
                        break;
                    }
                }

                if (validador2) {
                    for (int j = 0; j < 4; j++) {
                        if (row - j >= 0 && col >= 0) { // Verifica se os índices são válidos
                            Button botaoAcima2 = buttons1[row - j][col];
                            clickedButton.setUserData("Couracado");
                            botaoAcima2.setUserData("Couracado");
                        } else {
                            validadorCouracado = false;
                            break;
                        }
                    }
                }

                if (!validadorCouracado || !validador2) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao posicionar o barco");
                    alert.setContentText("O Couraçado não pode ser posicionado aqui");
                    if (contadordeCouracados > 0) {
                        contadordeCouracados--;
                        ButtonCoura1.disableProperty().set(false);
                    }

                    alert.showAndWait();
                }

                nomeBarco = "";
            }

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
            // TituloLabel.setText("Posicione o seu barco Jogador " + getNomeJogador1());

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
            configurarEncerramentoJanela();
            Duration duracao = Duration.between(tempoinicial, Instant.now());
            this.duracao = duracao;
            atualizarTela();
        }
    }

    public class threadVerificaBarcos extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (buttons1[i][j].getUserData().equals("Submarino")) {
                            buttons1[i][j].setStyle(
                                    "-fx-background-color: green; -fx-border-color: black; -fx-text-fill: green");
                        }
                        if (buttons1[i][j].getUserData().equals("PortaAvioes")) {
                            buttons1[i][j].setStyle(
                                    "-fx-background-color: black; -fx-border-color: black; -fx-text-fill: black");

                        }
                        if (buttons1[i][j].getUserData().equals("Couracado")) {
                            buttons1[i][j].setStyle(
                                    "-fx-background-color: grey; -fx-border-color: black; -fx-text-fill: grey");
                        }

                    }
                }
            }
        }
    }
}
