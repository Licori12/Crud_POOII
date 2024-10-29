package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;

import java.sql.SQLException;
import java.util.List;

public interface TarefaDAO {
    public void insere(Tarefa tarefa, int idUsuario) throws SQLException;
    public void atualiza(Tarefa tarefa, int idUsuario) throws SQLException;
    public void remove(Tarefa tarefa, int idUsuario) throws SQLException;
    public List<Tarefa> listTodos(int idUsuario) throws SQLException;
    public void marcarComoConcluida(int id) throws SQLException;

}
