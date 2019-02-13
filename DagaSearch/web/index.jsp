<%-- 
    Document   : listado
    Created on : 7/10/2017, 08:15:46 PM
    Author     : darub
    http://designhooks.com/freebies/nava-clean-responsive-html-template/
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <head>
      <title>DagaSearch</title>
      <meta charset="ISO-8859-1">
      <link href="css/screen.css" rel="stylesheet" />
   </head>
   <body >
      <div>
            <div class="hero-box">
                  <div class="hero-text align-center">
                     <a href="index.jsp">
                        <img src="img/iconos/Dagasearch.png"width="800" height="600" />
                     </a>
                  </div>
                  <form class="destinations-form" method="post" action="SrvCalidad" >
                     <div class="input-line">
                        <input type="text" name="termino" value="" class="form-input" check-value placeholder="Termino" id="entrada"  onclick="validacion()" required/>
                        <button type="sumbit" name="buscar" class="form-submit btn btn-special">BUSCAR</button>
                     </div>
                  </form>
            </div>
      <script src="js/jquery.js"></script>
      <script src="js/functions.js"></script>
   </body>
</html>