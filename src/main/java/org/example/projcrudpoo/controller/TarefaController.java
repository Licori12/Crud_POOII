package org.example.projcrudpoo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.projcrudpoo.dao.TarefaDBDAO;
import org.example.projcrudpoo.dao.UsuarioDBDAO;
import org.example.projcrudpoo.model.Tarefa;
import org.example.projcrudpoo.model.Usuario;

import java.sql.SQLException;

public class TarefaController {
    private Usuario usuarioLogado;

    @FXML
    private TableColumn<Tarefa, String> descricaoColuna;

    @FXML
    private TextField descricaoField;

    @FXML
    private TableColumn<Tarefa, Integer> idColuna;

    @FXML
    private TableView<Tarefa> tabelaTarefas;

    @FXML
    private TableColumn<Tarefa, String> tituloColuna;

    @FXML
    private TextField tituloField;

    @FXML
    void adicionarTarefa(ActionEvent event) throws SQLException {
        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();
        Tarefa tarefa = new Tarefa(tituloField.getText(), descricaoField.getText(), usuarioDB.buscaId(usuarioLogado));
        TarefaDBDAO tarefaDB = new TarefaDBDAO();

        tarefaDB.insere(tarefa);
    }

    @FXML
    void editarTarefa(ActionEvent event) {

    }

    @FXML
    void excluirTarefa(ActionEvent event) {

    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
