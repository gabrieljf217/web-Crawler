/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaibscrawl;

import java.util.ArrayList;

/**
 *
 * @author Estudiante
 */
public class URLS {
    String url_inicial;
    ArrayList<String> salida_final;
    

    public String getUrl_inicial() {
        return url_inicial;
    }

    public void setUrl_inicial(String url_inicial) {
        this.url_inicial = url_inicial;
    }

    public ArrayList<String> getSalida_final() {
        return salida_final;
    }

    public void setSalida_final(ArrayList<String> salida_final) {
        this.salida_final = salida_final;
    }

    public URLS() {
    }

    public URLS(String url_inicial, ArrayList<String> salida_final) {
        this.url_inicial = url_inicial;
        this.salida_final = salida_final;
    }
    
}
