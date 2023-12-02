package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    /*
     * Crio uma variável estática do tipo Connection, ou seja, posso acessar essa
     * variável em qlq parte do código sem ter q declará-la novamente
     */
    public static Connection con;

    public static Connection conectar() throws ClassNotFoundException, SQLException {
        if (con == null) {
            // O método forName() é utilizado para carregar dinamicamente uma classe em tempo de execução.
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/AeroPoint"; // jdbc -> conexão do banco com o java, tipo o HTTP
            String user = "postgres";
            String password = "Root";
            con = (Connection) DriverManager.getConnection(url, user, password);
            return (Connection) con;
        } else {
            return (Connection) con;
        }
    }
}
