package batalha_naval.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import batalha_naval.dao.core.ConnectionFactory;


public class ConexaoFactoryPostgreSQL implements ConnectionFactory {
    
    /*
    local: 

    private String dbURL = "jdbc:postgresql://localhost:5432/Teste2";
    private String user = "postgres";
    private String password = "123";
        
    private String dbURL = "jdbc:postgresql://silly.db.elephantsql.com:5432/oaktlyql";
    private String user = "oaktlyql";
    private String password = "NUA1m5sBKJWVgSj1rRhPmabFT0-Ayc_u";*/

    
    private String dbURL = "jdbc:postgresql://silly.db.elephantsql.com:5432/oaktlyql";
    private String user = "oaktlyql";
    private String password = "NUA1m5sBKJWVgSj1rRhPmabFT0-Ayc_u";
    private static final String classeDriver = "org.postgresql.Driver";    
 

    public ConexaoFactoryPostgreSQL() {
    }


    public ConexaoFactoryPostgreSQL(String dbURL, String user, String password) {
        this.dbURL = dbURL;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        Connection conexao = null;
        try {
            System.out.println("Conectando com o banco de dados.");
            Class.forName(classeDriver);
            conexao = DriverManager.getConnection(dbURL, user, password);
            System.out.println("Conexão com o banco de dados estabelecida.");
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro obtendo uma conexão com o banco de dados.");
            e.printStackTrace();
            return null;
        }
    }

}