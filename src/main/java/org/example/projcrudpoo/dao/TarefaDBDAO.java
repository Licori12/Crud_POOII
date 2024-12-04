package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDBDAO implements IConst, TarefaDAO {
    private String sql;

    @Override
    public void insere(Tarefa tarefa) throws SQLException {
        // Substituição do gerenciamento manual por try-with-resources para fechamento automático de recursos. Melhora o desempenho do codigo, (primeira refatoracao) 
        sql = "INSERT INTO tarefa (titulo, descricao, status, id_usuario) VALUES (?,?,?,?)";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setString(3, tarefa.getStatus());
            statement.setInt(4, tarefa.getIdUsuario());
            statement.executeUpdate();
        }
    }

    @Override
    public void atualiza(Tarefa tarefa) throws SQLException {
        // Uso do try-with-resources para liberar automaticamente o PreparedStatement e a conexão.
        sql = "UPDATE tarefa SET titulo=?, descricao=? WHERE id=?";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setInt(3, tarefa.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Tarefa tarefa) throws SQLException {
        // Gerenciamento automático de recursos com try-with-resources.
        sql = "DELETE FROM tarefa WHERE id=?;";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tarefa.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Tarefa> listTodos(int idUsuario) throws SQLException {
        // Fechamento automático de ResultSet, PreparedStatement e Connection.
        sql = "SELECT * FROM tarefa WHERE id_usuario = ?;";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUsuario);
            try (ResultSet result = statement.executeQuery()) {
                List<Tarefa> tarefas = new ArrayList<>(); // Declarado como List, facilita a manutenção do código (segunda refatoracao) 
                while (result.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setTitulo(result.getString("titulo"));
                    tarefa.setDescricao(result.getString("descricao"));
                    tarefa.setStatus(result.getString("status"));
                    tarefa.setId(result.getInt("id"));
                    tarefas.add(tarefa);
                }
                return tarefas;
            }
        }
    }

    @Override
    public void marcarComoConcluida(Tarefa tarefa) throws SQLException {
        // Utilização de try-with-resources para melhor gerenciamento de recursos.(primeira refatoracao) 
        sql = "UPDATE tarefa SET status=? WHERE id=?;";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tarefa.getStatus());
            statement.setInt(2, tarefa.getId());
            statement.executeUpdate();
        }
    }
}
