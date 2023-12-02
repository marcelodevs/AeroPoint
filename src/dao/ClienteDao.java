package dao;

import Model.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class ClienteDao {

    private Connection con;
    private Statement stm; // Variável que executa comandos SQL

    public ClienteDao() {
        try {
            con = (Connection) Conexao.conectar(); // Tá 'trazendo' a conexão do banco
            stm = con.createStatement(); // Tá criando um Statement, para que seja capaz de executar comandos SQL
        } catch (ClassNotFoundException | SQLException e) {

        }
    }

    /**
     * Código responsável pela insersão do usuário
     */
    public boolean inserir(Cliente c) {
        try {
            stm.executeUpdate("INSERT INTO cliente(passaporte, nome, cpf, rg) VALUES ('" + c.getPassaporte() + "', '" + c.getNome() + "', '" + c.getCPF() + "', '" + c.getRG() + "')");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /*
     * Código responsável pela consulta do usuário
     */
    public Cliente consultar(String cpf) throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM cliente WHERE cpf='" + cpf + "'");
        /*
         * O ResultSet é aquele que vair armazernar os dados da consulta realizada
         */
        Cliente cli = new Cliente();
        while (rs.next()) {
            /*
             * o método rs.next() é utilizado para percorrer cada linha retornada pela
             * consulta, verificando se há mais linhas disponíveis e movendo o cursor para a
             * próxima linha caso exista.
             */
            cli.setNome(rs.getString("nome"));
            cli.setPassaporte(rs.getString("passaporte"));
            cli.setRG(rs.getString("rg"));
            cli.setCPF(rs.getString("cpf"));
        }
        return cli;
    }

    /*
     * Código responsável pela exclusão do usuário
     */
    public boolean excluir(String cpf) throws SQLException {
        try {
            stm.executeUpdate("DELETE FROM cliente WHERE cpf = '" + cpf + "'");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /*
     * Código responsável pela atualização do usuário
     */
    public boolean atualizar(Cliente c) throws SQLException {
        try {
            stm.executeUpdate("UPDATE cliente SET nome ='" + c.getNome() + "' WHERE cpf ='" + c.getCPF() + "'");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /*
     * Código responsável pela listagem do usuário
     */
    public List<Cliente> listar_clientes() throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM cliente");
        List<Cliente> cliList = new ArrayList<>();

        while (rs.next()) {
            /*
             * o método rs.next() é utilizado para percorrer cada linha retornada pela
             * consulta, verificando se há mais linhas disponíveis e movendo o cursor para a
             * próxima linha caso exista.
             */
            Cliente cli = new Cliente();

            cli.setNome(rs.getString("nome"));
            cli.setPassaporte(rs.getString("passaporte"));
            cli.setRG(rs.getString("rg"));
            cli.setCPF(rs.getString("cpf"));

            // Adiciona os valores do 'cli' na lista 'cliList'
            cliList.add(cli);
        }
        return cliList;
    }
}
