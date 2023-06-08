package batalha_naval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaCadastrarController {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private Button ButtonFechar;

    @FXML
    private TextField TextFieldNomeJogador;

    @FXML
    private TextField TextFieldSenhaJogador;

    @FXML
    void ButtonCadastrarcClicado(ActionEvent event) {

    }

    @FXML
    void ButtonFecharClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
