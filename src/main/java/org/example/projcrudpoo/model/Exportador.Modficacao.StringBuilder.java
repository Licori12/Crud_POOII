package org.example.projcrudpoo.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Exportador {

    public boolean exportar(ArrayList<Tarefa> tarefas, Usuario usuarioAtual) throws IOException {
        String pastaRelatorios = "C:\\Users\\Pardal\\Faculdade\\TRABALHO_POOII\\Relatorios";
        File pasta = new File(pastaRelatorios);

        if (!pasta.exists()) {
            pasta.mkdir();
        }

        String nomeArquivo = pastaRelatorios + "/Relatorio_tarefas_" + usuarioAtual.getNome() + "_" +
                obterDataHoraAtual() + ".txt";

        escreverNoArquivo("RELATORIO\n", nomeArquivo);
        escreverNoArquivo("DATA: " + mostrarData() + "\n", nomeArquivo);
        escreverNoArquivo("USUARIO: " + usuarioAtual.getNome() + "\n", nomeArquivo);
        escreverNoArquivo("=====================\n", nomeArquivo);
        escreverNoArquivo("-------------TAREFAS-------------\n", nomeArquivo);

        // Uso de StringBuilder para acumular as strings
        StringBuilder conteudoTarefas = new StringBuilder();
        for (Tarefa tarefa : tarefas) {
            conteudoTarefas.append("=====================\n");
            conteudoTarefas.append("ID: ").append(tarefa.getId()).append("\n");
            conteudoTarefas.append("Titulo: ").append(tarefa.getTitulo()).append("\n");
            conteudoTarefas.append("Descrição: ").append(tarefa.getDescricao()).append("\n");
            conteudoTarefas.append("Status: ").append(tarefa.getStatus()).append("\n");
        }
        escreverNoArquivo(conteudoTarefas.toString(), nomeArquivo);

        escreverNoArquivo("=====================\n", nomeArquivo);
        return true;
    }

    private String obterDataHoraAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        return sdf.format(new Date());
    }

    private String mostrarData() {
        String dataHora = obterDataHoraAtual();
        return dataHora.substring(0, 2) + "/" + dataHora.substring(2, 4) + "/" + dataHora.substring(4, 8);
    }

    private void escreverNoArquivo(String mensagem, String nomeArquivo) throws IOException {
        try (FileWriter writer = new FileWriter(nomeArquivo, true)) {
            writer.write(mensagem);
        }
    }
}
