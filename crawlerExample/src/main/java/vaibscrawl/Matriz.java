/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaibscrawl;

import java.util.ArrayList;


public class Matriz {
    
    Integer indice;
    ArrayList<Integer> llegadas;

    public Matriz() {
    }

    public Matriz(Integer indice, ArrayList<Integer> llegadas) {
        this.indice = indice;
        this.llegadas = llegadas;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public ArrayList<Integer> getLlegadas() {
        return llegadas;
    }

    public void setLlegadas(ArrayList<Integer> llegadas) {
        this.llegadas = llegadas;
    }
    
    
    
    
}
