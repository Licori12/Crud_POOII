package org.example.projcrudpoo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projcrudpoo.dao.TarefaDBDAO;
import org.example.projcrudpoo.dao.UsuarioDBDAO;
import org.example.projcrudpoo.model.Tarefa;
import org.example.projcrudpoo.model.Usuario;

import java.io.IOException;
import java.sql.SQLException;

public class TarefaController {
    private Usuario usuarioLogado;
    private ObservableList<Tarefa> listaTarefa;

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
    private TableColumn<Tarefa,String> statusColuna;


    @FXML
    public void initialize() throws SQLException {
        // Configura as colunas da tabela para mostrar os dados da Tarefa
        idColuna.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloColuna.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descricaoColuna.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        statusColuna.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Inicializa a lista de tarefas e carrega dados
        listaTarefa = FXCollections.observableArrayList();
        tabelaTarefas.setItems(listaTarefa);
    }
    @FXML
    void adicionarTarefa(ActionEvent event) throws SQLException {
        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();
        Tarefa tarefa = new Tarefa(tituloField.getText(), descricaoField.getText(), usuarioDB.buscaId(usuarioLogado));
        TarefaDBDAO tarefaDB = new TarefaDBDAO();

        tarefaDB.insere(tarefa);
        limparCampos();
        carregar();
    }

    @FXML
    void editarTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projcrudpoo/view/EdicaoView.fxml"));
                Parent root = loader.load();

                EdicaoController controller = loader.getController();
                controller.setTarefa(tarefaSelecionada); // Passando a tarefa selecionada para o controlador

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Editar Tarefa");
                stage.showAndWait();

                // Atualizar a tabela após a edição
                carregar(); // Chame o método para recarregar as tarefas

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);}
        } else {
            // Exibir um alerta se nenhuma tarefa estiver selecionada
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma Tarefa Selecionada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione uma tarefa para editar.");
            alert.showAndWait();
        }
    }


    @FXML
    void excluirTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if(tarefaSelecionada != null){
            TarefaDBDAO tarefaDB = new TarefaDBDAO();
            try {
                tarefaDB.remove(tarefaSelecionada);
                carregar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Selecione uma tarefa para excluir.");
        }
    }

    public void setUsuarioLogado(Usuario usuarioLogado) throws SQLException {
        this.usuarioLogado = usuarioLogado;
        carregar();
    }

    public void carregar() throws SQLException {
        TarefaDBDAO tarefaDB = new TarefaDBDAO();
        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();
        listaTarefa.setAll(tarefaDB.listTodos(usuarioDB.buscaId(usuarioLogado)));
    }

    public void alterarStatus(ActionEvent event) throws SQLException {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();
        tarefaSelecionada.setStatus();

        TarefaDBDAO tarefaDB = new TarefaDBDAO();
        tarefaDB.marcarComoConcluida(tarefaSelecionada);

        carregar();
    }

    public void limparCampos(){
        tituloField.clear();
        descricaoField.clear();
    }


}
