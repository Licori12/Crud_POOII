package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TarefaDBDAO implements IConst, TarefaDAO{
    private String sql;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private void open() throws SQLException {
        connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
    }

    private void close() throws SQLException {
        connection.close();
    }
    @Override
    public void insere(Tarefa tarefa) throws SQLException {
        open();
        sql = "INSERT INTO tarefa (titulo, descricao, status, id_usuario) VALUES (?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1,tarefa.getTitulo());
        statement.setString(2,tarefa.getDescricao());
        statement.setString(3,tarefa.getStatus());
        statement.setInt(4,tarefa.getIdUsuario());

        statement.executeUpdate();

        close();
    }

    @Override
    public void atualiza(Tarefa tarefa) throws SQLException {

    }

    @Override
    public void remove(Tarefa tarefa) throws SQLException {

    }

    @Override
    public List<Tarefa> listTodos(int idUsuario) throws SQLException {
        return List.of();
    }

    @Override
    public void marcarComoConcluida(int id) throws SQLException {

    }
}
