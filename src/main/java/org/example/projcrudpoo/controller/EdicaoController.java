package org.example.projcrudpoo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.projcrudpoo.model.Tarefa;
import org.example.projcrudpoo.dao.TarefaDBDAO;

import java.sql.SQLException;

public class EdicaoController {
    @FXML
    private TextField tituloField;
    @FXML
    private TextArea descricaoField;

    private Tarefa tarefa;

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
        tituloField.setText(tarefa.getTitulo());
        descricaoField.setText(tarefa.getDescricao());
    }

    @FXML
    private void salvarTarefa() {
        tarefa.setTitulo(tituloField.getText());
        tarefa.setDescricao(descricaoField.getText());

        TarefaDBDAO tarefaDB = new TarefaDBDAO();
        try {
            tarefaDB.atualiza(tarefa); // Método para atualizar a tarefa no banco de dados
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fechar a janela de edição
        Stage stage = (Stage) tituloField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void cancelar() {
        // Fechar a janela de edição sem salvar
        Stage stage = (Stage) tituloField.getScene().getWindow();
        stage.close();
    }
}
