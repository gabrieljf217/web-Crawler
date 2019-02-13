package vaibscrawl;

import java.util.logging.LogManager;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import org.apache.http.Header;
import java.util.*;

/**
 * @author Vaibs
 *
 */
public class MVaibsWecker2 extends WebCrawler {

    public final static Pattern FILTERS = Pattern
            .compile(".*(\\.(css|js|bmp|gif|jpe?g"
                    + "|png|tiff?|mid|mp2|mp3|mp4|ttf"
                    + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                    + "|rm|smil|wmv|swf|wma|zip|rar|gz|svg|php|ico))$");

    public MVaibsWecker2() {
        
    }

    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        //System.out.print("Pagina :"+!FILTERS.matcher(href).matches()+"\n");
        // String hrefir = url.getRedirectedToUrl();
        int validar = href.indexOf("unal.edu.co");
        int php = href.indexOf("php");
        int css = href.indexOf("css");
        if (validar > 0 && php < 0 && css < 0) {
            return !FILTERS.matcher(href).matches();
        } else {
            return false;
        }
    }

    public int i = 0;

    public void visit(Page page) {

        String href = page.getWebURL().getURL();
        href=href.trim();
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            ArrayList<WebURL> salidas_links = new ArrayList<WebURL>(links);
            ArrayList<String> salida_final = new ArrayList<String>();
            //elementos.add("html");elementos.add("jsp");elementos.add("xml");
            for (int i = 0; i < salidas_links.size(); i++) {
                String h = salidas_links.get(i).toString();
                int validar = h.indexOf("unal.edu.co");
                int php = h.indexOf("php");
                int css = h.indexOf("css");
                if (!FILTERS.matcher(h).matches() && validar > 0 &&  php < 0 && css < 0) {
                    salida_final.add(h);
                }
            }
            datos(text, href);
            arreglo(href);
            claves(href,salida_final);
            //System.out.println(""+);
            System.out.println("INCIO #"+i+""+href+"\n SALIDA "+salida_final+" Tamaño "+salida_final.size()+"\n");
        }
        
        i++;   
    }

    public int j = 0;
    public void datos(String texto, String href) {
        try {
            File t = new File("./datos/"+j + ".txt");
            FileWriter w = new FileWriter(t, true);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.append(href + "\r\n");//AGREGAR LA URL
            wr.write(texto);// AGREGAR EL TEXTO
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
        j++;
    }
    
    public int k = 0;
    public void arreglo(String href) {
        try {
            File t = new File("Claves.txt");
            FileWriter w = new FileWriter(t, true);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.append("" + k + " " + href + "\r\n");//AGREGAR LA URL
            //wr.append("" + vector_urls.get(i).getSalida_final() + "\r\n");
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
        k++;
    }
public int f=0;
    public void claves(String href,ArrayList<String> salida_final){
        try {
            File t = new File("./claves/"+f+" clave.txt");
            FileWriter w = new FileWriter(t, true);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.append(""+href + "\r\n");//AGREGAR LA URL
            wr.append("" + salida_final + "\r\n");
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
        f++;
    }    
}
