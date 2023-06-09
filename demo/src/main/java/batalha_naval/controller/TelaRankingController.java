package batalha_naval.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaRankingController implements Initializable {

    @FXML
    private Button ButtonVoltar;

    @FXML
    private TableView<Pessoa> TableRanking;
    @FXML
    private TableColumn<Pessoa, Integer> TableColuna;

    @FXML
    private TableColumn<Pessoa, String> TableNome;

    private DAOFactory daoFactory;
    private List<Pessoa> pessoas;

    @FXML
    void ButtonVoltarClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
        
    }

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColuna.setCellValueFactory(new PropertyValueFactory<Pessoa,Integer>("ponto"));
        TableNome.setCellValueFactory(new PropertyValueFactory<Pessoa,String>("nome"));
        try {
            ConexaoFactoryPostgreSQL conexaoFactory = new ConexaoFactoryPostgreSQL(
                    "silly.db.elephantsql.com:5432/oaktlyql", "oaktlyql", "NUA1m5sBKJWVgSj1rRhPmabFT0-Ayc_u");
            daoFactory = new DAOFactory(conexaoFactory);
            daoFactory.abrirConexao();
            PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
            pessoas = dao.orderByPontos();
            TableRanking.getItems().addAll(pessoas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        daoFactory.fecharConexao();
    }

}
