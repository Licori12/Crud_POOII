package org.example.projcrudpoo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TarefaController {
    @FXML
    private TableColumn<?, ?> descricaoColuna;

    @FXML
    private TextField descricaoField;

    @FXML
    private TableColumn<?, ?> idColuna;

    @FXML
    private TableView<?> tabelaTarefas;

    @FXML
    private TableColumn<?, ?> tituloColuna;

    @FXML
    private TextField tituloField;

    @FXML
    void adicionarTarefa(ActionEvent event) {

    }

    @FXML
    void editarTarefa(ActionEvent event) {

    }

    @FXML
    void excluirTarefa(ActionEvent event) {

    }

}
