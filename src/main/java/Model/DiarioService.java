/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ...
 */
public class DiarioService {
    
    private static final String ARQUIVO = "diario.txt";
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Salva um novo evento no arquivo
     * Formato salvo: 2025-02-01 13:30|Texto do evento
     */
    public void escreverNovo(String texto, LocalDateTime dataEvento) throws IOException {
        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(dataEvento.format(formatoData) + "|" + texto + "\n");
        }
    }

    /**
     * Lê todas as linhas do arquivo
     */
    public List<String> lerLinhas() throws IOException {
        File arquivo = new File(ARQUIVO);
        List<String> linhas = new ArrayList<>();

        if (!arquivo.exists()) return linhas;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }

        return linhas;
    }

    /**
     * Salva todas as linhas informadas sobrescrevendo o arquivo
     */
    public void salvarLinhas(List<String> linhas) throws IOException {
        try (FileWriter fw = new FileWriter(ARQUIVO)) {
            for (String linha : linhas) {
                fw.write(linha + "\n");
            }
        }
    }

    /**
     * Editar um evento: mantém a data original e altera o texto
     */
    public void editarEntrada(int index, String novoTexto) throws IOException {
        List<String> linhas = lerLinhas();
        if (index < 0 || index >= linhas.size()) return;

        String linha = linhas.get(index);
        String[] partes = linha.split("\\|");

        String data = partes[0];  // mantém a data original
        linhas.set(index, data + "|" + novoTexto);

        salvarLinhas(linhas);
    }

    /**
     * Apagar um evento
     */
    public void apagarEntrada(int index) throws IOException {
        List<String> linhas = lerLinhas();
        if (index < 0 || index >= linhas.size()) return;

        linhas.remove(index);
        salvarLinhas(linhas);
    }
}
