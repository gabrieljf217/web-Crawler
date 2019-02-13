/*
Aunque los servlets pueden responder a cualquier tipo de solicitudes, éstos son utilizados comúnmente para extender las aplicaciones alojadas por servidores web, de tal manera que pueden ser vistos como applets de Java que se ejecutan en servidores en vez de navegadores web
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import AccesoDatos.AccesoCalidad;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author darub
 */
@WebServlet(name = "SrvCalidad", urlPatterns = {"/SrvCalidad"})
public class SrvCalidad extends HttpServlet {

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
            texto tex =new texto();
            int documento = 0;
            String termino = "";
            int frecuencia=0;
            String texto="";
            String titulo="";
            res = calidad.Listado();
            if((request.getParameter("termino")!="%")){
                out.println("<html>");
                out.println("<head> <meta charset=\"ISO-8859-1\">");
                out.println("<title>DagaSearch</title>");
                out.println(" <link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css\"/> <link rel=\"stylesheet\" href=\"css/style.css\">");
                out.println("</head>");
                out.println("<body background=img/6fondo.jpg >");
                out.println("<table align=center border=0>");
                //out.println("<tr style='color:#FFFFFF' align=center ><td>Termino</td><td>Titulo documento</td></tr>");//<td>Frecuencia</td>
                res = calidad.MostrarDocumento(request.getParameter("termino"));
                
                while (res.next()) {
                termino = res.getString("termino");
                frecuencia = res.getInt("frecuencia");
                titulo =res.getString("titulo");
                texto = res.getString("texto");
                //out.println("<tr style='color:#FFFFFF'align=center > <td> <form method=\"post\" action=\"texto\"><button style='color:#FA5858' class=\"btn btn-link\" type='submit' name='documento' value='"+res.getInt("documento")+"'><h4>"+ titulo+"</h4></button></br><h5  style='color:#FFFFFF'>"+texto+"</h5></form></td></tr>");
                out.println("<tr style='color:#FFFFFF'align=left > <td> <a href="+titulo+" style='color:#FA5858' ><h4>"+ titulo+"</h4></a><h5  style='color:#FFFFFF'>"+texto+"</h5></td></tr>");
                }
                out.println("</table>");
                out.println("<center><a href=\"index.jsp\"><img src='img/volver.png' width='15%' height='13%'/> </a></center> </body>");
                out.println("</html>");
                out.close();
            }
            
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
    }

}
