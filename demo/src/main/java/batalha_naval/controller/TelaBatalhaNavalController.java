package batalha_naval.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.ResourceBundle;

public class TelaBatalhaNavalController implements Initializable {

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
                GridPane1.add(button1, col, row);

                Button button2 = new Button();
                button2.setMinSize(50, 50);
                button2.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-text-fill: blue");
                buttons2[row][col] = button2;
                button2.setText("button2[" + row + "][" + col + "]]");
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
            System.out.println("Botão clicado: " + clickedButton.getText());
        }
    }

}



