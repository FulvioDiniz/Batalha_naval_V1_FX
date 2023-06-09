package batalha_naval.controller;

import batalha_naval.dao.ConexaoFactoryPostgreSQL;
import batalha_naval.dao.PessoaDAO;
import batalha_naval.dao.core.DAOFactory;
import batalha_naval.model.Pessoa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TelaCadastrarController {

    @FXML
    private Button ButtonCadastrar;

    @FXML
    private Button ButtonFecharCadastro;

    @FXML
    private TextField TextFieldNomeJogador1;

    @FXML
    private TextField TextFieldSenhaJogador1;

    private DAOFactory daoFactory;

    public void setDAOFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean cadastrarEhValido() {
        if (TextFieldNomeJogador1.getText().isEmpty() || TextFieldSenhaJogador1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Preencha os campos");
            alert.setHeaderText("Preencha os campos");
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return false;
        } else {
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
                daoFactory.getDAO(PessoaDAO.class);
                PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
                if (dao.findByNome(TextFieldNomeJogador1.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nome já cadastrado");
                    alert.setHeaderText("O nome " + TextFieldNomeJogador1.getText() + " Ja foi cadastrado");
                    alert.setContentText("Cadastro invalido");
                    TextFieldNomeJogador1.setText("");
                    TextFieldSenhaJogador1.setText("");
                    alert.showAndWait();
                    return false;
                } else {
                    daoFactory.fecharConexao();
                    return true;
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro");
                alert.setContentText("Erro ao cadastrar!");
                alert.showAndWait();
                return false;
            }
        }

    }

    @FXML
    void ButtonCadastrarClicado(ActionEvent event) {
        if (cadastrarEhValido()) {
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
                PessoaDAO dao = daoFactory.getDAO(PessoaDAO.class);
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(TextFieldNomeJogador1.getText());
                pessoa.setSenha(TextFieldSenhaJogador1.getText());
                dao.cadastrarPessoa(TextFieldNomeJogador1.getText(), TextFieldSenhaJogador1.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro realizado");
                alert.setHeaderText("Cadastro realizado");
                alert.setContentText("Cadastro realizado com sucesso!");
                alert.showAndWait();
                ((Button) event.getSource()).getScene().getWindow().hide();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro");
                alert.setContentText("Erro ao cadastrar!");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void ButtonFecharCadastroClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
