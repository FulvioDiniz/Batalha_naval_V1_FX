package batalha_naval.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class TelaInicialController {

    @FXML
    private AnchorPane Fundo;

    @FXML
    private Button NovoJogo;

    @FXML
    private Button Ranking;

    @FXML
    private Button Sair;

    @FXML
    void ButtonNovoJogo(ActionEvent event) {

    }

    @FXML
    void ButtonRanking(ActionEvent event) {

    }

    @FXML
    void ButtonSair(ActionEvent event) {
        if (event.getSource() == Sair) {
            System.exit(0);
        }
    }

}
