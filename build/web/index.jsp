<%-- 
    Document   : index
    Created on : 13 dic. 2021, 14:51:08
    Author     : Rafael Bastidas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String user = (String) request.getAttribute("user");
    String password = (String) request.getAttribute("password");
    String message = (String) request.getAttribute("message");
    
    if (user == null) {
        user = "";
    }
    if (password == null) {
        password = "";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de sesión</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </head>
    <body style="height: 100vh; background-image: url('https://cdn.pixabay.com/photo/2013/02/15/10/58/wave-81840_960_720.jpg');">
        
            <div class="d-flex justify-content-center align-items-center h-100 w-50 mx-auto">
                <form action="SessionController" method="POST">
                    <div class="row g-3 p-5 rounded-3" style="background-color: rgba(224,224,224,.8)">
                        <%
                            if (message != null ) {
                                 out.println("<div class='alert alert-danger' role='alert'>Usuario/Contraseña incorrecta.</div>"); 
                            }
                        %>
                        <div class="col-12 text-center">
                            <h2 style="color: #6c5ce7">Inicio de sesion</h2>
                        </div>
                        <div class="col-12">
                            <input class="form-control form-control-sm" type="text" name="user" placeholder="Usuario" value=<%=user%>>
                        </div>
                        <div class="col-12">
                            <input class="form-control form-control-sm" type="password" name="password" placeholder="Contraseña" value=<%=password%>>
                        </div>
                        <div class="col-12 align-self-center text-center">
                            <button onclick="secureSession()" class="btn btn-primary mb-3" style="background-color: #EC4B5F">Ingresar</button>
                        </div>
                        <div class="col-12 text-center">
                            <a href="Register/index.jsp" style="color: #6c5ce7">Deseo registrarme</a>
                        </div>
                    </div>
                </form>
            </div>
        
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    </body>
</html>
