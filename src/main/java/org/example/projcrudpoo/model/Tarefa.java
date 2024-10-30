package org.example.projcrudpoo.model;

public class Tarefa {
    private String titulo;
    private String descricao;
    private String status;
    private int idUsuario;

    public Tarefa(String titulo, String descricao, int idUsuario) {
        this.descricao = descricao;
        this.status = "Pendente";
        this.titulo = titulo;
        this.idUsuario = idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getStatus() {
        return status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}