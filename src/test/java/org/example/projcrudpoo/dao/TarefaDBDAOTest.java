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

                assertEquals(tarefaTeste.getTitulo(), resultado.getTitulo());
            } else {
                fail("Tarefa não encontrada no banco de dados.");
            }
        }
    }
}