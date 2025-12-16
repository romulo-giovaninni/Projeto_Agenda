/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author ROMULOGIOVANINNIDEME
 */
import Model.Agenda;
import View.TelaInicial;
import javax.swing.JOptionPane;

public class AgendaController {

    private Agenda model;
    private TelaInicial view;

    public AgendaController(Agenda model, TelaInicial view) {
        this.model = model;
        this.view = view;

        // Conecta a tela ao controller
        this.view.setController(this);
    }

    // -----------------------------
    // MÉTODO: ADICIONAR NOVA LINHA
    // -----------------------------
    public void adicionar() {
        String texto = view.getTexto().trim();  // Agora pega do JTextPane

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O texto está vazio!");
            return;
        }

        model.adicionar(texto);
        view.setTexto("");  // limpa depois de salvar
        atualizarView();
    }

    // -----------------------------
    // MÉTODO: EDITAR
    // -----------------------------
    public void editar() {
        if (model.getTamanho() == 0) {
            JOptionPane.showMessageDialog(null, "Agenda vazia!");
            return;
        }

        String input = JOptionPane.showInputDialog(null,
                "Qual linha deseja editar? (0 a " + (model.getTamanho() - 1) + ")");

        if (input == null) return; // cancelou

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido!");
            return;
        }

        String novoTexto = JOptionPane.showInputDialog("Novo texto:");
        if (novoTexto == null || novoTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto inválido!");
            return;
        }

        model.editar(index, novoTexto.trim());
        atualizarView();
    }

    // -----------------------------
    // MÉTODO: DELETAR
    // -----------------------------
    public void deletar() {
        if (model.getTamanho() == 0) {
            JOptionPane.showMessageDialog(null, "Agenda vazia!");
            return;
        }

        String input = JOptionPane.showInputDialog(null);

        if (input == null) return;

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido!");
            return;
        }

        model.deletar(index);
        atualizarView();
    }

    // -----------------------------
    // ATUALIZA A TELA
    // -----------------------------
    public void atualizarView() {
        view.setTexto(model.getTudo());
    }
    
}
