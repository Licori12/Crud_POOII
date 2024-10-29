package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Usuario;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioDBDAO implements UsuarioDAO, IConst {
    @Override
    public void cadastrar(Usuario usuario) throws SQLException {

    }

    @Override
    public void atualizarCadastro(Usuario usuarioAtualizado) throws SQLException {

    }

    @Override
    public void removeCadastro(Usuario usuario) throws SQLException {

    }

    @Override
    public boolean verificarEntrada(Usuario usuario) throws SQLException {
        return false;
    }
}
