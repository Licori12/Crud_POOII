package org.example.projcrudpoo.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Exportador {

    // Método para exportar as tarefas para um arquivo TXT
    public boolean exportar(ArrayList<Tarefa> tarefas, Usuario usuarioAtual) throws IOException {
        // Definir a pasta onde o arquivo será salvo
        String pastaRelatorios = "C:\\Users\\Pardal\\Faculdade\\TRABALHO_POOII\\Relatorios";
        File pasta = new File(pastaRelatorios);

        // Verificar se a pasta existe, se não, criar a pasta
        if (!pasta.exists()) {
            pasta.mkdir(); // Cria a pasta se ela não existir
        }

        // Gerar o nome do arquivo com o nome do usuário e a data/hora
        String nomeArquivo = pastaRelatorios + "/Relatorio_tarefas_" + usuarioAtual.getNome() + "_" +
                obterDataHoraAtual() + ".txt";

        // Escrever no arquivo
        escreverNoArquivo("RELATORIO\n", nomeArquivo);
        escreverNoArquivo("DATA: " + mostrarData() + "\n", nomeArquivo);
        escreverNoArquivo("USUARIO: " + usuarioAtual.getNome() + "\n", nomeArquivo);
        escreverNoArquivo("=====================\n", nomeArquivo);
        escreverNoArquivo("-------------TAREFAS-------------\n", nomeArquivo);

        for (Tarefa tarefa : tarefas) {
            escreverNoArquivo("=====================\n", nomeArquivo);
            escreverNoArquivo("ID: " + tarefa.getId() + "\n", nomeArquivo);
            escreverNoArquivo("Titulo: " + tarefa.getTitulo() + "\n", nomeArquivo);
            escreverNoArquivo("Descrição: " + tarefa.getDescricao() + "\n", nomeArquivo);
            escreverNoArquivo("Status: " + tarefa.getStatus() + "\n", nomeArquivo);
        }

        escreverNoArquivo("=====================\n", nomeArquivo);
        return true;
    }

    // Método para obter a data e hora atual no formato desejado
    private String obterDataHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        return sdf.format(new Date());
    }

    private String mostrarData() {
        String dataHora = obterDataHoraAtual();
        return dataHora.substring(0, 2) + "/" + dataHora.substring(2, 4) + "/" + dataHora.substring(4, 8);
    }

    /**
     * Método para escrever no arquivo.
     * Autor: Leonardo Caparica
     * Objetivo: Encapsular em um método para melhor leitura do código.
     */
    private void escreverNoArquivo(String mensagem, String nomeArquivo) throws IOException {
        // Usar try-with-resources para fechar automaticamente o FileWriter
        try (FileWriter writer = new FileWriter(nomeArquivo, true)) { // Modo append ativado
            writer.write(mensagem);
        }
    }
}
