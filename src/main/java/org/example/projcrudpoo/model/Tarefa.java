package org.example.projcrudpoo.model;

public class Tarefa {
    private String titulo;
    private String descricao;
    private String status;
    private int idUsuario;

    public Tarefa(String descricao, String status, String titulo, int idUsuario ) {
        this.descricao = descricao;
        this.status = status;
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
}