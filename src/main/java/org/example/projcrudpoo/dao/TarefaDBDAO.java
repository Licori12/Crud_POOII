package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;

import java.sql.SQLException;
import java.util.List;

public class TarefaDBDAO implements IConst, TarefaDAO{
    @Override
    public void insere(Tarefa tarefa, int idUsuario) throws SQLException {

    }

    @Override
    public void atualiza(Tarefa tarefa, int idUsuario) throws SQLException {

    }

    @Override
    public void remove(Tarefa tarefa, int idUsuario) throws SQLException {

    }

    @Override
    public List<Tarefa> listTodos(int idUsuario) throws SQLException {
        return List.of();
    }

    @Override
    public void marcarComoConcluida(int id) throws SQLException {

    }
}
