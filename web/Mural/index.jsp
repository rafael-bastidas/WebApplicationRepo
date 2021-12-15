<%-- 
    Document   : index
    Created on : 13 dic. 2021, 14:51:08
    Author     : Rafael Bastidas
--%>

<%@page import="modelo.DataRepositorie"%>
<%@page import="modelo.DataRepositories"%>
<%@page import="java.util.List"%>
<%@page import="modelo.DataUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    //DataUser dataUser = (DataUser) request.getAttribute("dataUser");
    DataUser dataUser = (DataUser) application.getAttribute("dataUser");
    String message = (String) request.getAttribute("message");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body style="height: 100vh; background-image: url('https://cdn.pixabay.com/photo/2020/08/31/20/22/abstract-5533509_960_720.png')"">
        
        
        <div class="d-flex flex-column h-100 justify-content-between">
        
        <nav class="navbar navbar-light bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand text-white">Repo-Imagenes</a>
                <form action="RepositoriesController" method="post" class="d-flex">
                    <input class="form-control me-2" type="search" name="tags" placeholder="Buscar tags" aria-label="Search">
                    <input hidden type="text" name="accion" value="searchTags">
                    <button class="btn btn-outline-success" type="submit">Buscar</button>
                </form>
                <div class="dropdown">
                    <button class="btn btn-light dropdown-toggle text-black-50" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Usuario
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: auto; right: 0;">
                        <li><a class="dropdown-item" href="Home/index.jsp">Mi perfil</a></li>
                        <li><a class="dropdown-item" href="index.jsp">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        
            
        
        <div style="display:flex; flex-direction: row; overflow: auto hidden; white-space: nowrap">
            <c:forEach items="${listaMural}" var="element">
                <div class="mb-3">
                    <div class="card ms-3" style="width: 20rem">
                        <img src="${element.imagen}" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h6>Autor: <span class="text-primary">${element.user}</span></h6>
                            <h6>Repositorio: <span class="text-primary">${element.namerepo}</span></h6>
                            <p class="card-text">${element.tags}</p>
                            <div class="d-flex justify-content-end">
                                <a href="RepositoriesController?accion=likes&idrepositorie=${element.idrepositorie}" class="btn btn-primary btn-sm">(${element.likes}) Likes</a>
                                <a href="#" class="btn btn-danger btn-sm ms-1">Ver perfil</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
            
            </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script>
            const raiz_url = "http://" + window.location.host
            window.history.pushState(null, "", raiz_url + "/WebApplicationRepo/Home")

            function configLikes() {
                console.log("Dar Likes");
            }

            <c:if test="${dataUser == null}">
            window.location.href = raiz_url + "/WebApplicationRepo/"
            </c:if>
        </script>
    </body>
</html>
