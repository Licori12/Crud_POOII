package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Usuario;

import java.sql.SQLException;

public interface UsuarioDAO {
    public void cadastrar(Usuario usuario) throws SQLException;
    public void atualizarCadastro(Usuario usuarioAtualizado) throws SQLException;
    public void removeCadastro(Usuario usuario) throws SQLException;
    public boolean verificarEntrada(Usuario usuario) throws SQLException;
}
