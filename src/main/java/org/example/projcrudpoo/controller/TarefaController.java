package org.example.projcrudpoo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projcrudpoo.dao.TarefaDBDAO;
import org.example.projcrudpoo.dao.UsuarioDBDAO;
import org.example.projcrudpoo.model.Exportador;
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
    public void initialize() {
        // Configura as colunas da tabela para mostrar os dados da Tarefa
        idColuna.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloColuna.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descricaoColuna.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        statusColuna.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Inicializa a lista de tarefas e carrega dados
        listaTarefa = FXCollections.observableArrayList();
        tabelaTarefas.setItems(listaTarefa);
    }

    /*
        4 refatoracao: validação de campos obrigatórios
        Autor: Guilherme Licori 
	Implementado como parte da refatoração para validar se os campos
        Título e Descrição estão preenchidos antes de adicionar ou editar tarefas.
    */
    private boolean validarCampos() {
        if (tituloField.getText().isBlank() || descricaoField.getText().isBlank()) {
            alertaErro("Os campos Título e Descrição são obrigatórios!");
            return false;
        }
        return true;
    }

    @FXML
    void adicionarTarefa(ActionEvent event) throws SQLException {
        /*
            MODIFICAÇÃO: Adicionada validação de campos obrigatórios.
            Objetivo: Garantir que Título e Descrição estejam preenchidos antes de continuar.
        */
        if (!validarCampos()) {
            return; // Interrompe a execução caso a validação falhe
        }

        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();
        Tarefa tarefa = new Tarefa(tituloField.getText(), descricaoField.getText(), usuarioDB.buscaId(usuarioLogado));
        TarefaDBDAO tarefaDB = new TarefaDBDAO();

        tarefaDB.insere(tarefa);
        alertaSucesso("Tarefa adicionada com sucesso");
        limparCampos();
        carregar();
    }

    @FXML
    void editarTarefa(ActionEvent event) {
        /*
            MODIFICAÇÃO: Adicionada validação de campos obrigatórios.
            Objetivo: Garantir que Título e Descrição estejam preenchidos antes de continuar.
        */
        if (!validarCampos()) {
            return; // Interrompe a execução caso a validação falhe
        }

        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(retornaCaminho());
                Parent root = loader.load();

                EdicaoController controller = loader.getController();
                controller.setTarefa(tarefaSelecionada); // Passando a tarefa selecionada para o controlador

                carregarStage(root, "Editar Tarefa", true);
                alertaSucesso("Tarefa modificada com sucesso");
                // Atualizar a tabela após a edição
                carregar(); // Chame o método para recarregar as tarefas

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            alertaErro("Por favor, selecione uma tarefa para editar.");
        }
    }

    /*
        1 Refatoração
        Autor: Leonardo Caparica
        Uso de método para retornar caminho da pasta para carregamento do LoginView.fxml
        Objetivo: Facilitar mudanças do código caso necessário
     */
    private java.net.URL retornaCaminho() {
        return getClass().getResource("/org/example/projcrudpoo/view/EdicaoView.fxml");
    }

    @FXML
    void excluirTarefa(ActionEvent event) {
        Tarefa tarefaSelecionada = tabelaTarefas.getSelectionModel().getSelectedItem();

        if (tarefaSelecionada != null) {
            TarefaDBDAO tarefaDB = new TarefaDBDAO();
            try {
                tarefaDB.remove(tarefaSelecionada);
                alertaSucesso("Tarefa excluída com sucesso");
                carregar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            alertaErro("Por favor, selecione uma tarefa para editar.");
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
        if (tarefaSelecionada != null) {
            tarefaSelecionada.setStatus();

            TarefaDBDAO tarefaDB = new TarefaDBDAO();
            tarefaDB.marcarComoConcluida(tarefaSelecionada);

            carregar();
        } else {
            alertaErro("Por favor, selecione uma tarefa.");
        }
    }

    public void limparCampos() {
        tituloField.clear();
        descricaoField.clear();
    }

    public void gerarRelatorio(ActionEvent event) throws SQLException, IOException {
        TarefaDBDAO tarefaDB = new TarefaDBDAO();
        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();
        Exportador exportador = new Exportador();

        if (exportador.exportar(tarefaDB.listTodos(usuarioDB.buscaId(usuarioLogado)), usuarioLogado)) {
            alertaSucesso("Relatório gerado com sucesso!\nEle se localiza na pasta Relatórios");
        } else {
            alertaErro("Relatório atual não pode ser gerado");
        }
    }

    public void sair() {
        Alert mensagem = new Alert(Alert.AlertType.CONFIRMATION);
        mensagem.setTitle("Voltar ao login");
        mensagem.setHeaderText(null);
        mensagem.setContentText("Deseja voltar ao Login?");

        mensagem.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Carrega a tela de login
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projcrudpoo/view/LoginView.fxml"));
                    Parent loginView = loader.load();

                    // Obtém o stage atual e fecha a janela
                    Stage stageAtual = (Stage) tabelaTarefas.getScene().getWindow();
                    stageAtual.close();

                    carregarStage(loginView, "Login - Gerenciador de Tarefas", false);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
        3 Refatoração
        Autor: Leonardo Caparica
        Uso de método para imprimir a mensagem de erro
        Objetivo: Facilitar leitura do código
     */
    private void alertaSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void alertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /*
        4 Refatoração
        Autor: Leonardo Caparica
        Uso de método para criação de stage
        Objetivo: Facilitar leitura do código
     */
    private void carregarStage(Parent view, String titulo, boolean aguardar) {
        Stage stage = new Stage();
        stage.setScene(new Scene(view));
        stage.setTitle(titulo);
        if (aguardar) stage.showAndWait();
        else stage.show();
    }
}
