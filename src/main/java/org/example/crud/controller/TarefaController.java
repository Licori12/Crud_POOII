package org.example.crud.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.crud.model.Tarefa;

import java.time.LocalDate;

public class TarefaController {
    @FXML
    private TableColumn<Tarefa, Integer> idColuna;

    @FXML
    private TableColumn<Tarefa, String> tituloColuna;

    @FXML
    private TableColumn<Tarefa, String> descricaoColuna;

    @FXML
    private TableColumn<Tarefa, LocalDate> dataColuna;

    @FXML
    private TableView<Tarefa> tabelaTarefas;

    @FXML
    private TextField tituloField;
    @FXML
    private TextField descricaoField; // Adicione um campo de descrição se necessário.

    private ObservableList<Tarefa> listaTarefas = FXCollections.observableArrayList();

    @FXML
    void adicionarTarefa(ActionEvent event) {
        String titulo = tituloField.getText();
        String descricao = descricaoField.getText(); // Se você adicionar um campo de descrição

        Tarefa novaTarefa = new Tarefa(listaTarefas.size() + 1, titulo, descricao);
        listaTarefas.add(novaTarefa);
        tabelaTarefas.setItems(listaTarefas);

        // Limpar os campos após adicionar
        tituloField.clear();
        descricaoField.clear(); // Limpar o campo de descrição se necessário
    }

    @FXML
    void editarTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (tarefaSelecionada != null) {
            tarefaSelecionada.setTitulo(tituloField.getText());
            tarefaSelecionada.setDescricao(descricaoField.getText()); // Se você adicionar um campo de descrição
            tabelaTarefas.refresh(); // Atualiza a tabela
        }
    }

    @FXML
    void excluirTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        if (tarefaSelecionada != null) {
            listaTarefas.remove(tarefaSelecionada);
            tabelaTarefas.setItems(listaTarefas);
        }
    }

    @FXML
    void initialize() {
        idColuna.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tituloColuna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        descricaoColuna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        dataColuna.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));
    }

}
