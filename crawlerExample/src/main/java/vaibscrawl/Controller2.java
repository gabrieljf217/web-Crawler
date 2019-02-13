package vaibscrawl;

import java.util.ArrayList;
import java.util.Map;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import Jama.Matrix;
import java.util.Date;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Controller2 {

    //LISTA DE LA CLASE URLS la cual contiene String url_inicial y ArrayList<String> salida_final
    public static List<URLS> vector_urls = new ArrayList<URLS>();

    public static void main(String[] args) throws Exception {
        //INICIALIZAR TIEMPO  
        System.out.println(new Date());
        //URL INCIAL
        String url = "http://unal.edu.co/";
        File director=new File("datos"); 
        director.mkdir();
        File directorio=new File("claves"); 
        directorio.mkdir();
        
        //CODIGO CRAWLER
        try {
            //CARPETA CONFIGURACION 
            String crawlStorageFolder = "\\analisis";
            //NUMERO DE CRAWLERS
            int numberOfCrawlers = 1;
            //CONFIGURACION
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            int max = config.getMaxTotalConnections();

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
            //AGREGAR SEMILLA URL
            controller.addSeed(url);
            //INICIALIZAR CRAWLER
            //controller.start(MVaibsWecker2.class, numberOfCrawlers);
//------------------------------------------------------------------------------------------------------------------------------      
            //ABRIR TXT CLAVES GENERADOS POR EL CRAWLER 
            File Ffichero = new File("claves\\");
            File[] listArchivos = Ffichero.listFiles();
            String line;
            //GUARDAR LOS DATOS EN UN ARREGLO LLAMADO ArrayList<String> urls_salida1 
            for (int i = 0; i < listArchivos.length; i++) {
                int g = 0;
                URLS urls = new URLS();
                ArrayList<String> urls_salida1 = new ArrayList<String>();
                if (listArchivos[i].isFile()) {
                    System.out.println(listArchivos[i]);
                    BufferedReader fileIn = new BufferedReader(new FileReader(listArchivos[i]));
                    while ((line = fileIn.readLine()) != null) {
                        // TOQUENIZAR DATOS SEGUN [] PARA OBTENER EL ARREGLO
                        StringTokenizer stk = new StringTokenizer(line, "[],");
                        while (stk.hasMoreTokens()) {
                            String word = stk.nextToken();
                            if (g == 0) {
                                //System.out.println("PRIMERO "+word);
                                urls.setUrl_inicial(word.trim());
                            } else {
                                //System.out.println("RESTO "+word);
                                urls_salida1.add(word.trim());
                            }
                            g++;
                        }
                    }
                    urls.setSalida_final(urls_salida1);

                }
                //GUARDAR DATOS
                vector_urls.add(i, urls);
            }
            //IMPRIMIR URLS INICIALES Y VECTOR SALIDA
            /*
            for (int i = 0; i < vector_urls.size(); i++) {
                System.out.println(vector_urls.size() + "\n INICIAL :" + vector_urls.get(i).getUrl_inicial() + "\n" + vector_urls.get(i).getSalida_final().size() + "\n" + "\n SALIDA: " + vector_urls.get(i).getSalida_final());
            }*/
            //CREAR DICCIONARIO DE LINKS LOS CUALES EQUIVALEN A NUMEROS
            HashMap<String, Integer> diccionario_urls = new HashMap<String, Integer>();
            //MATRIZ
            ArrayList<ArrayList<Integer>> matriz = new ArrayList<ArrayList<Integer>>();   
            //CREAR VECTORES POR CADA URL INICIAL
            for (int j = 0; j < vector_urls.size(); j++) {
                ArrayList<Integer> datos = new ArrayList<Integer>();  
                diccionario_urls.put(vector_urls.get(j).getUrl_inicial(), j);
                matriz.add(datos);
                System.out.println(matriz);
            }
//------------------------------------------------------------------------------------------------------------------------------      
            //ORGANIZAR ELEMENTOS DE LLEGADA PARA UNA PAGINA
            for(int j=0;j<vector_urls.size();j++){
                int k=0;
                System.out.println(" \nTAMAÑO VECTOR_URLS "+vector_urls.size());
                System.out.print(""+diccionario_urls.get(vector_urls.get(j).getUrl_inicial())+"{");
                //System.out.println("lado "+vector_urls.get(j).getUrl_inicial());
                for (int i = 0; i <vector_urls.get(j).getSalida_final().size(); i++) {
                    System.out.println(" \nTAMAÑO VECTOR_SALIDAS "+vector_urls.get(j).getSalida_final().size());
                    if(diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i))!=null){
                        /*if(k==0){
                        System.out.print(+diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i)));
                        wr.append(""+diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i)));
                        k++;
                        }else{
                        System.out.print(","+diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i)));
                        wr.append(","+diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i))); 
                        }*/
                        int posM=diccionario_urls.get(vector_urls.get(j).getSalida_final().get(i));
                        int llega=diccionario_urls.get(vector_urls.get(j).getUrl_inicial());
                        System.out.println("----------------------------------------------\n"+posM);
                        System.out.println("----------------------------------------------\n"+llega);
                        if(posM!=llega){
                            matriz.get(posM).add(llega);
                        }
                        System.out.println("MATRIZ "+matriz); 
                    }
                }     
            }
//------------------------------------------------------------------------------------------------------------------------------
            //GUARDAR DATOS PARA PYTHON
            try {
                File t = new File("Matriz.txt");
                FileWriter w = new FileWriter(t, true);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);

                wr.append("" + vector_urls.size() + "\r\n");
                
                for(int j=0;j<vector_urls.size();j++){
                    if (j == 0) {
                    wr.append("{"+j+": "+matriz.get(j)+",");
                    }else{
                        if (j == vector_urls.size() - 1) {
                            wr.append(" "+j+": "+matriz.get(j)+"}\r\n");
                        }else{
                            wr.append(" "+j+": "+matriz.get(j)+",");
                        }
                        
                    }
                }
                
                wr.append("{");
                for (int j = 0; j < vector_urls.size(); j++) {
                    if (j == 0) {
                        wr.append("" + diccionario_urls.get(vector_urls.get(j).getUrl_inicial()) + ": " + vector_urls.get(j).getSalida_final().size() + ",");
                    } else {
                        if (j == vector_urls.size() - 1) {
                            wr.append(" " + diccionario_urls.get(vector_urls.get(j).getUrl_inicial()) + ": " + vector_urls.get(j).getSalida_final().size() + "");
                        } else {
                            wr.append(" " + diccionario_urls.get(vector_urls.get(j).getUrl_inicial()) + ": " + vector_urls.get(j).getSalida_final().size() + ",");
                        }

                    }
                }
                wr.append("}\r\n");
                wr.append("{");
                int k=0;
                for (int p = 0; p < vector_urls.size(); p++) {
                    if (vector_urls.get(p).getSalida_final().size() == 0) {
                        if(k!=0){
                              wr.append(", ");  
                            }
                            wr.append("" + diccionario_urls.get(vector_urls.get(p).getUrl_inicial())+": "+"True");
                            k++;
                        }
                }
                wr.append("}");
                wr.close();
                bw.close();
            } catch (IOException e) {};
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("" + new Date());
        }

    }
}
