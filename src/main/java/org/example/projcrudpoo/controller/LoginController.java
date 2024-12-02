package org.example.projcrudpoo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.projcrudpoo.dao.UsuarioDBDAO;
import org.example.projcrudpoo.model.Usuario;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void abrirCadastro(ActionEvent event) throws IOException {
        carregarTela("/org/example/projcrudpoo/view/CadastroView.fxml", "Cadastro - Gerenciador de Tarefas");
    }

    @FXML
    void entrar(ActionEvent event) throws SQLException, IOException {
        Usuario usuario = new Usuario(usernameField.getText(), passwordField.getText());
        UsuarioDBDAO usuarioDB = new UsuarioDBDAO();

        if (usuarioDB.verificarEntrada(usuario)) {
            irCrud(usuario);
        } else {
           alertaErro("Usuario não encontrado");
        }
    }

    void irCrud(Usuario usuario) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(retornaCaminho());
        Parent root = loader.load();

        // Passando o usuário para o controlador da tela CRUD
        TarefaController tarefaController = loader.getController();
        tarefaController.setUsuarioLogado(usuario);

        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Gerenciador de Tarefas");
        stage.show();
    }

    private void carregarTela(String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }
    /*
        1 Refatoração
        Autor: Leonardo Caparica
        Uso de metodo para retornar caminho da pasta para carregamento do LoginView.fxml
        Objetivo: Facilitar mudanças do código caso necessário
     */
    private java.net.URL retornaCaminho(){
        return this.getClass().getResource("/org/example/projcrudpoo/view/TarefaView.fxml");
    }
    /*
        3 Refatoração
        Autor: Leonardo Caparica
        Uso de metodo para imprimir a mensagem de erro
        Objetivo: Facilitar leitura do codigo
     */
    private void alertaErro(String mensagem){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
