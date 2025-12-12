/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetoagenda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ROMULOGIOVANINNIDEME
 */
public class DiarioSimples {
    
    private static final Scanner sc = new Scanner(System.in);
    private static final String ARQUIVO = "diario.txt";
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1) Escrever novo conteúdo");
            System.out.println("2) Ler diário");
            System.out.println("3) Editar entrada");
            System.out.println("4) Apagar entrada");
            System.out.println("99) Sair");
            System.out.print("Digite a opção desejada: ");

            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> escreverNovo();
                case 2 -> lerDiario();
                case 3 -> editarEntrada();
                case 4 -> apagarEntrada();
                case 99 -> {
                    System.out.println("Até breve!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static String getDataHora() {
        ZoneId zona = ZoneId.of("America/Recife");
        return LocalDateTime.now(zona).format(formato);
    }

    private static void escreverNovo() {
        System.out.println("\nDigite o texto:");
        String texto = sc.nextLine();

        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(getDataHora() + " - " + texto + "\n");
            System.out.println("Entrada registrada!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no diário.");
        }
    }

    private static void lerDiario() {
        File f = new File(ARQUIVO);
        if (!f.exists()) {
            System.out.println("O diário está vazio.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            boolean vazio = true;

            System.out.println("\n--- Diário ---");

            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                vazio = false;
            }

            if (vazio) System.out.println("O diário está vazio.");

        } catch (IOException e) {
            System.out.println("Erro ao ler diário.");
        }
    }

    private static void editarEntrada() {
        try {
            List<String> linhas = lerLinhas();
            if (linhas.isEmpty()) {
                System.out.println("Diário vazio.");
                return;
            }

            listarEntradas(linhas);

            System.out.print("\nDigite o número da entrada que deseja editar: ");
            int idx = Integer.parseInt(sc.nextLine()) - 1;

            if (idx < 0 || idx >= linhas.size()) {
                System.out.println("Entrada inválida.");
                return;
            }

            System.out.print("Digite o novo conteúdo: ");
            String novo = sc.nextLine();

            String data = linhas.get(idx).split(" - ")[0];
            linhas.set(idx, data + " - " + novo);

            salvarLinhas(linhas);
            System.out.println("Entrada editada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao editar entrada.");
        }
    }

    private static void apagarEntrada() {
        try {
            List<String> linhas = lerLinhas();
            if (linhas.isEmpty()) {
                System.out.println("Diário vazio.");
                return;
            }

            listarEntradas(linhas);

            System.out.print("\nDigite o número da entrada que deseja apagar: ");
            int idx = Integer.parseInt(sc.nextLine()) - 1;

            if (idx < 0 || idx >= linhas.size()) {
                System.out.println("Entrada inválida.");
                return;
            }

            linhas.remove(idx);
            salvarLinhas(linhas);

            System.out.println("Entrada apagada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao apagar entrada.");
        }
    }

    private static List<String> lerLinhas() throws IOException {
        File arquivo = new File(ARQUIVO);
        List<String> linhas = new ArrayList<>();

        if (!arquivo.exists()) return linhas;

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = br.readLine()) != null) {
            linhas.add(linha);
        }
        br.close();
        return linhas;
    }

    private static void salvarLinhas(List<String> linhas) throws IOException {
        FileWriter fw = new FileWriter(ARQUIVO);
        for (String linha : linhas) fw.write(linha + "\n");
        fw.close();
    }

    private static void listarEntradas(List<String> linhas) {
        System.out.println("\nEntradas existentes:");
        for (int i = 0; i < linhas.size(); i++) {
            System.out.println((i + 1) + ") " + linhas.get(i));
        }
    }
    
}
