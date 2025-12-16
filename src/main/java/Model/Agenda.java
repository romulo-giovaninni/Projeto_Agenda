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
 * Classe unificada Agenda/Diario com persistência em arquivo
 */
public class Agenda {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String ARQUIVO = "agenda.dat"; // arquivo de persistência

    // Entrada da agenda
    public static class Entrada implements Serializable {
        private static final long serialVersionUID = 1L;

        private LocalDateTime data; 
        private String texto;

        public Entrada(String texto) {
            this.texto = texto;
        }

        public Entrada(String texto, LocalDateTime data) {
            this.texto = texto;
            this.data = data;
        }

        public LocalDateTime getData() {
            return data;
        }

        public String getTexto() {
            return texto;
        }

        public void setTexto(String texto) {
            this.texto = texto;
        }

        public void setData(LocalDateTime data) {
            this.data = data;
        }

        @Override
        public String toString() {
            if (data != null) {
                return data.format(FORMATO_DATA) + " - " + texto;
            } else {
                return texto;
            }
        }
    }

    private List<Entrada> entradas = new ArrayList<>();

    public Agenda() {
        carregar(); 
    }

    // -----------------------------
    // ADICIONAR
    // -----------------------------
    public void adicionar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto inválido!");
        }
        entradas.add(new Entrada(texto.trim()));
        salvar();
    }

    public void adicionar(String texto, LocalDateTime data) {
        if (texto == null || texto.trim().isEmpty() || data == null) {
            throw new IllegalArgumentException("Texto ou data inválidos!");
        }
        entradas.add(new Entrada(texto.trim(), data));
        salvar();
    }

    // -----------------------------
    // EDITAR
    // -----------------------------
    public void editar(int index, String novoTexto) {
        validarIndice(index);
        if (novoTexto == null || novoTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto inválido!");
        }
        entradas.get(index).setTexto(novoTexto.trim());
        salvar();
    }

    public void editar(int index, String novoTexto, LocalDateTime novaData) {
        validarIndice(index);
        if (novoTexto == null || novoTexto.trim().isEmpty() || novaData == null) {
            throw new IllegalArgumentException("Texto ou data inválidos!");
        }
        Entrada e = entradas.get(index);
        e.setTexto(novoTexto.trim());
        e.setData(novaData);
        salvar();
    }

    // -----------------------------
    // DELETAR
    // -----------------------------
    public void deletar(int index) {
        validarIndice(index);
        entradas.remove(index);
        salvar();
    }

    // -----------------------------
    // LISTAR
    // -----------------------------
    public List<Entrada> getTodasEntradas() {
        return new ArrayList<>(entradas);
    }

    public int getTamanho() {
        return entradas.size();
    }

    public String getTudoComoString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entradas.size(); i++) {
            sb.append((i + 1)).append(") ").append(entradas.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    // -----------------------------
    // HELPERS
    // -----------------------------
    private void validarIndice(int index) {
        if (index < 0 || index >= entradas.size()) {
            throw new IndexOutOfBoundsException("Índice fora do intervalo!");
        }
    }

    // -----------------------------
    // SALVAR / CARREGAR
    // -----------------------------
    private void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(entradas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void carregar() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            entradas = (List<Entrada>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     public void salvarEmArquivo(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(entradas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDoArquivo(String nomeArquivo) {
        File f = new File(nomeArquivo);
        if (!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            entradas = (List<Entrada>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
