/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 public ResultSet BuscarPorTermino(String Termino) throws Exception
    {
        try{
             getStmt();
	     resultado= stmt.executeQuery("SELECT * FROM termino_frecuencia_documento WHERE (termino LIKE '" + Termino + "%') order by frecuencia desc");
             return resultado;
              } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return null;
           }
    }
 */
package AccesoDatos;

import AccesoDatos.Conexion;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccesoCalidad extends Conexion{

    private ResultSet resultado;
    
     public AccesoCalidad()
    {
        Conectar();
    }
    public ResultSet Listado()throws Exception
    {
            try{
             getStmt();
	     resultado= stmt.executeQuery("SELECT * FROM termino_frecuencia_documento order by frecuencia desc");
             return resultado;
              } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return null;
           }
    }

    public ResultSet MostrarDocumento(String Termino) throws Exception
    {
        try{
             getStmt();
	     resultado= stmt.executeQuery("SELECT documento,termino,frecuencia,titulo,texto FROM termino_frecuencia_documento INNER JOIN  documentos ON termino_frecuencia_documento.documento = documentos.document WHERE (termino LIKE '" + Termino + "%')order by frecuencia desc");
             return resultado;
              } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return null;
           }
    }
    public ResultSet ImprimirTexto(String document) throws Exception
    {
        try{
             getStmt();
	     resultado= stmt.executeQuery("SELECT * FROM documentos where document LIKE "+ document+"" );
             return resultado;
              } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return null;
           }
    }
    
 
}
