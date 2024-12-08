package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TarefaDBDAOTest {
    /*
        1° Teste
        Autor: Leonardo Caparica
        Teste para verificar o funcionamento do metodo de inseção do banco de dados
        na tabela Tarefa.
     */
    @Test
    void insere() throws SQLException {
        Tarefa tarefaTeste = new Tarefa("teste", "descricao1", 15);
        TarefaDBDAO tarefaDBDAO = new TarefaDBDAO();

        tarefaDBDAO.insere(tarefaTeste);

        String sql = "SELECT * FROM tarefa WHERE titulo = ? AND descricao = ?";

        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Define os parâmetros para a consulta SQL
            statement.setString(1, "teste");
            statement.setString(2, "descricao1");

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Tarefa resultado = new Tarefa(result.getString("titulo"), result.getString("descricao"), result.getInt("id_usuario"));

                tarefaDBDAO.remove(tarefaTeste);
                assertEquals(tarefaTeste.getTitulo(), resultado.getTitulo());
            } else {
                fail("Tarefa não encontrada no banco de dados.");
            }
        }
    }

    @Test
    void marcarComoConcluida() throws SQLException {
        // Inicializa a tarefa e o DAO
        Tarefa tarefaTeste2 = new Tarefa("teste2", "descricao2", 15);
        TarefaDBDAO tarefaDBDAO = new TarefaDBDAO();

        // Insere a tarefa no banco
        tarefaDBDAO.insere(tarefaTeste2);

        // Recupera o ID da tarefa inserida
        String sqlRecuperaId = "SELECT * FROM tarefa WHERE titulo='teste2' AND descricao='descricao2'";
        int idTarefaTeste = 0;
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sqlRecuperaId)) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idTarefaTeste = resultSet.getInt("id");
            } else {
                fail("Tarefa não encontrada após inserção.");
            }
        }

        // Atualiza o ID e o status da tarefa
        tarefaTeste2.setId(idTarefaTeste);
        tarefaTeste2.setStatus(); // Certifique-se de que este método define "concluído" corretamente

        // Marca a tarefa como concluída no banco
        tarefaDBDAO.marcarComoConcluida(tarefaTeste2);

        // Valida se o status foi atualizado corretamente
        String sqlValidaStatus = "SELECT * FROM tarefa WHERE titulo=? AND descricao=?";
        try (Connection connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement statement = connection.prepareStatement(sqlValidaStatus)) {

            statement.setString(1, "teste2");
            statement.setString(2, "descricao2");

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Tarefa resultado = new Tarefa(
                        result.getString("titulo"),
                        result.getString("descricao"),
                        result.getInt("id_usuario"),
                        result.getString("status")
                );

                // Remove a tarefa para limpar o banco após o teste
                tarefaDBDAO.remove(tarefaTeste2);

                // Valida se o status foi marcado como concluído
                assertEquals(tarefaTeste2.getStatus(), resultado.getStatus(),
                        "O status da tarefa no banco não corresponde ao esperado.");
            } else {
                fail("Tarefa não encontrada no banco de dados após marcar como concluída.");
            }
        }
    }

}