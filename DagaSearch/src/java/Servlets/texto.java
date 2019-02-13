/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import AccesoDatos.AccesoCalidad;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author darub
 */
@WebServlet(name = "texto", urlPatterns = {"/texto"})
public class texto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       processRequest(request, response);
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
         try {
            ResultSet res;
            AccesoCalidad calidad = new AccesoCalidad();
            String titulo="",linea="",texto="";
            out.println("<html>");
            out.println("<head> <meta charset=\"ISO-8859-1\"> ");
            out.println("<title>DagaSearch</title>");
            out.println(" <link href=Estilo.css rel=stylesheet type=text/css> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
            out.println("</head>");
            out.println("<body background=img/6fondo.jpg >");
            res = calidad.ImprimirTexto(request.getParameter("documento"));
            while (res.next()) {
                titulo = res.getString("titulo");
                /*try {
                    FileReader entrada=new FileReader("C:/Users/darub/Dropbox/ing/Semestre #8/Procesos estocasticos y simulacion/Proyecto/Libros/"+titulo);
                    BufferedReader mi=new BufferedReader(entrada);
            while(linea!=null){
                linea=mi.readLine();
                texto=texto+linea+"\n";
            }
            //System.out.println(texto);                       
            } catch (FileNotFoundException ex) {
                Logger.getLogger(texto.class.getName()).log(Level.SEVERE, null, ex);
            }*/
                
                //out.println("<center><div><h1 style='color:#FFFFFF'><a href='"<+titulo+"</h1></center></div><br><div class=\"col-sm-2\"></div><div><h4 style='color:#FFFFFF'class=\"col-sm-8\">"+texto+"</h4></div><div class=\"col-sm-4\"></div>");
        }
            out.println("</table>");
            out.println("</html>");
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(SrvCalidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    String doPost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
