package org.example.projcrudpoo.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Exportador {

    /*

    2a refatoracao Lucas
    Path da pasta relatorio agora é uma constante
    Objetivo: melhor manutenção
     */

    // Constante para o caminho da pasta de relatórios
    private static final String PASTA_RELATORIOS = "C:\\Users\\Pardal\\Faculdade\\TRABALHO_POOII\\Relatorios";

    // Método para exportar as tarefas para um arquivo TXT
    public boolean exportar(ArrayList<Tarefa> tarefas, Usuario usuarioAtual) throws IOException {
        // Criar um objeto File para a pasta de relatórios
        File pasta = new File(PASTA_RELATORIOS);

        // Verificar se a pasta existe, se não, criar a pasta
        if (!pasta.exists()) {
            pasta.mkdir(); // Cria a pasta se ela não existir
        }

        // Gerar o nome do arquivo com o nome do usuário e a data/hora
        String nomeArquivo = PASTA_RELATORIOS + "/Relatorio_tarefas_" + usuarioAtual.getNome() + "_" +
                obterDataHoraAtual() + ".txt";

        // Escrever no arquivo
        escreverNoArquivo("RELATORIO\n", nomeArquivo);
        escreverNoArquivo("DATA: " + mostrarData() + "\n", nomeArquivo);
        escreverNoArquivo("USUARIO: " + usuarioAtual.getNome() + "\n", nomeArquivo);
        escreverNoArquivo("=====================\n", nomeArquivo);
        escreverNoArquivo("-------------TAREFAS-------------\n", nomeArquivo);


	/** 
	* autor Guilherme Licori 
    * Metodo: Uso de StringBuilder para acumular as strings
	* objetivo: evitar a criação de múltiplos objetos, deixar o código mais legível 
	 */
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
