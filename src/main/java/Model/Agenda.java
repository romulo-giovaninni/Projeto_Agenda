/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ROMULOGIOVANINNIDEME
 */
import java.util.ArrayList;
import java.util.List;
public class Agenda {
    
    private List<String> lista = new ArrayList<>();

    public void adicionar(String texto) {
        lista.add(texto);
    }

    public void editar(int index, String novoTexto) {
        lista.set(index, novoTexto);
    }

    public void deletar(int index) {
        lista.remove(index);
    }

    public String getTudo() {
        StringBuilder sb = new StringBuilder();
        for (String s : lista) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public int getTamanho() {
        return lista.size();
    }
    
}
