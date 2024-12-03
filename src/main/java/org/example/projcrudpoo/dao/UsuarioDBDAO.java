package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Usuario;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
1a refatoração Lucas Cogrossi
Antes os campos sql, statement e result eram atributos da classe, compartilhados entre os métodos.
Agora esses campos foram transformados em variáveis locais nos métodos, usando tratamento de exceções.
Objetivo: tornar o código mais modular, melhorar legibilidade.
*/

/*
Refatoração Atual: Substituição do Retorno de -1 por Exceção
Foi criada uma exceção personalizada `UsuarioNaoEncontradoException` para lidar com casos onde
o método `buscaId` não encontra o usuário no banco de dados.
Objetivo: Melhorar a clareza do código e evitar valores mágicos.
*/

public class UsuarioDBDAO implements UsuarioDAO, IConst {
    private Connection connection;

    private void open() throws SQLException {
        connection = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
    }

    private void close() throws SQLException {
        connection.close();
    }

    @Override
    public void cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome,email,senha) VALUES (?,?,?);";
        open();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.executeUpdate();
        } finally {
            close();
        }
    }

    @Override
    public boolean verificarEntrada(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nome = ? AND senha = ?;";
        open();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } finally {
            close();
        }
    }

    public int buscaId(Usuario usuario) throws SQLException {
        String sql = "SELECT id FROM usuario WHERE nome = ? AND senha = ?;";
        open();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("id");
                }
            }
        } finally {
            close();
        }

        // Lança exceção personalizada se o usuário não for encontrado
        throw new UsuarioNaoEncontradoException("Usuário não encontrado para nome: " + usuario.getNome());
    }

    public UsuarioDBDAO() {
    }
}

// Exceção personalizada para lidar com casos de usuário não encontrado
class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
