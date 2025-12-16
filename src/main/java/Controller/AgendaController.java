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
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class AgendaController {

    private Agenda model;
    private TelaInicial view;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public AgendaController(Agenda model, TelaInicial view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    
    StyledDocument doc = view.getTextoAgenda().getStyledDocument();

    // Carrega os dados do arquivo
    model.carregarDoArquivo("agenda.txt");

    atualizarView();
    }

    // -----------------------------
    // ADICIONAR NOVA ENTRADA
    // -----------------------------
    public void adicionar() {
         // Pergunta o texto ao usuário
    String texto = JOptionPane.showInputDialog(null, "Digite o texto do evento:");
    if (texto == null || texto.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "O texto está vazio!");
        return;
    }
        //Pede a data e hora ao usuario.
    String dataStr = JOptionPane.showInputDialog(null, "Digite a data do evento (dd/MM/yyyy HH:mm) ou deixe vazio:");
    if (dataStr == null || dataStr.trim().isEmpty()) {
        model.adicionar(texto); // Se não colocar data e hora, ele taca só o texto mesmo.
    } else {
        try {
            LocalDateTime data = LocalDateTime.parse(dataStr, FORMATO_DATA);
            model.adicionar(texto, data); // com data é melhor né...
            
        } catch (Exception e) {//coisa o erro na data pra nao travar tudo.
            JOptionPane.showMessageDialog(null, "Data inválida!");
            return;
        }
    }

    atualizarView();
    }

    // -----------------------------
    // EDITAR ENTRADA EXISTENTE
    // -----------------------------
    public void editar() {
        if (model.getTamanho() == 0) {
            JOptionPane.showMessageDialog(null, "Agenda vazia!");
            return;
        }

        String input = JOptionPane.showInputDialog(null,
                "Qual linha deseja editar? (1 a " + model.getTamanho() + ")");
        if (input == null) return;

        int index;
        try {
            index = Integer.parseInt(input) - 1; // exibição começa em 1 entáo tem que descontar.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido!");
            return;
        }

        if (index < 0 || index >= model.getTamanho()) {
            JOptionPane.showMessageDialog(null, "Índice fora do intervalo!");
            return;
        }

        String novoTexto = JOptionPane.showInputDialog("Novo texto:");
        if (novoTexto == null || novoTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Texto inválido!");
            return;
        }

        String novaDataStr = JOptionPane.showInputDialog("Nova data do evento (dd/MM/yyyy HH:mm) ou deixe vazio:");
        if (novaDataStr == null || novaDataStr.trim().isEmpty()) {
            model.editar(index, novoTexto); // Se deixar a parte de inserir data vazio, mantém a data que foi registrada antes. 
        } else {
            try {
                LocalDateTime novaData = LocalDateTime.parse(novaDataStr, FORMATO_DATA);
                model.editar(index, novoTexto, novaData);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data inválida!");
                return;
            }
        }

        atualizarView();
    }

    // -----------------------------
    // DELETAR ENTRADA
    // -----------------------------
    public void deletar() {
        if (model.getTamanho() == 0) {
            JOptionPane.showMessageDialog(null, "Agenda vazia!");
            return;
        }

        String input = JOptionPane.showInputDialog(null,
                "Qual linha deseja deletar? (1 a " + model.getTamanho() + ")");
        if (input == null) return;

        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido!");
            return;
        }

        if (index < 0 || index >= model.getTamanho()) {
            JOptionPane.showMessageDialog(null, "Índice fora do intervalo!");
            return;
        }

        model.deletar(index);
        atualizarView();
    }
    
    public void atualizarTela() {
    StyledDocument doc = view.getTextoAgenda().getStyledDocument();
    try {
        doc.remove(0, doc.getLength());

        for (int i = 0; i < model.getTamanho(); i++) {
            Agenda.Entrada e = model.getTodasEntradas().get(i);
            String texto = e.getTexto();
            String dataStr = e.getData() != null ? e.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
            // aqui ele coloca +1 no indice para mostrar mais bonitinho para o usuário. Mas é descontado quando for editar ou apagar.
            String exibicao = (i + 1) + ") " + (dataStr.isEmpty() ? texto : dataStr + " - " + texto) + "\n";

            SimpleAttributeSet estilo = new SimpleAttributeSet();
            if (e.getData() != null) {
                long minutosRest = java.time.temporal.ChronoUnit.MINUTES.between(LocalDateTime.now(), e.getData());
                if (minutosRest <= 30 && minutosRest >= 0) {
                    StyleConstants.setForeground(estilo, Color.RED);
                } else if (minutosRest < 0) {
                    StyleConstants.setForeground(estilo, Color.BLACK);
                } else {
                    StyleConstants.setForeground(estilo, Color.BLUE);
                }
            } else {
                StyleConstants.setForeground(estilo, Color.BLUE);
            }

            doc.insertString(doc.getLength(), exibicao, estilo);
        }
    } catch (Exception ex) {
        view.getTextoAgenda().setText("Erro ao carregar a agenda.");
    }
}

    // -----------------------------
    // ATUALIZA A VIEW
    // -----------------------------
    public void atualizarView() {
        view.setTexto(model.getTudoComoString());
    }
    
    public Agenda getModel() {
    return model;
}

}

