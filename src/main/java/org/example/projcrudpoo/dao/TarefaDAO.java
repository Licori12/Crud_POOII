package org.example.projcrudpoo.dao;

import org.example.projcrudpoo.model.Tarefa;

import java.sql.SQLException;
import java.util.List;

public interface TarefaDAO {
    public void insere(Tarefa tarefa) throws SQLException;
    public void atualiza(Tarefa tarefa) throws SQLException;
    public void remove(Tarefa tarefa) throws SQLException;
    public List<Tarefa> listTodos(int idUsuario) throws SQLException;
    public void marcarComoConcluida(Tarefa tarefa) throws SQLException;

}
