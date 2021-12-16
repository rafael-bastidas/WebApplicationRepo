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
    DataUser dataUserDest = (DataUser) application.getAttribute("dataUserDest");
    boolean validFollow = (boolean) request.getAttribute("validFollow");

    if (dataUserDest == null) {
        dataUserDest = new DataUser(0, "", "", "", "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg");
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body style="height: 100vh; background-image: url('https://cdn.pixabay.com/photo/2020/08/31/20/22/abstract-5533509_960_720.png')">
        <nav class="navbar navbar-light bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand text-white">Repo-Imagenes</a>
                <div class="dropdown">
                    <button class="btn btn-light dropdown-toggle text-black-50" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Usuario
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: auto; right: 0;">
                        <li><a class="dropdown-item" href="RepositoriesController?accion=updateMural">Volver a Mural</a></li>
                        <li><a class="dropdown-item" href="SessionController?accion=cerrarPerfil">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="container-fluid">
            <div class="row align-items-center p-3" style="background-color: rgba(224,224,224,.8)">
                <div class="col-md-4">
                    <img src=<%=dataUserDest.getImagen()%> class="img-fluid rounded-circle" alt="FOTO_USUARIO">
                </div>
                <div class="col-md-8 text-center">
                    <h4 class="text-black-50 text-start">Usuario: <%=dataUserDest.getUser()%></h4>
                    <p class="text-black-50  text-start">Bio: <%=dataUserDest.getBio()%></p>
                    <a href="RepositoriesController?accion=followUser&idusersdest=<%=dataUserDest.getIdusers()%>" class="btn btn-primary mb-3 w-100" style="background-color: #EC4B5F">
                        <% if (validFollow) { out.println("Dejar de seguir"); } else { out.println("Seguir"); } %>
                    </a>
                </div>
            </div>
            <div class="row py-3" style="background-color: #FFBB50">
                <div class="col-12">
                    <h2>Repositorios</h2>
                </div>
            </div>
        </div>

        <div style="height:200px; overflow: hidden auto; padding: 25px;">
            <c:forEach items="${listaRepositories}" var="element">
                <div class="row mb-2 bg-white p-2 rounded-3">
                    <div class="col-6 align-self-center">
                        <h6 class="mb-0">${element.namerepo}</h6>
                    </div>
                    <div class="col-6 text-end">
                        <a href="RepositoriesController?accion=verRepoDest&idrepositories=${element.idrepositories}" class="btn btn-primary btn-sm">
                            ver
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="container-fluid">
            <c:if test="${!empty dataRepositoriesSelect}">
                <div class="row py-3" style="background-color: #FFBB50">
                    <div class="col-12">
                        <h2>${dataRepositoriesSelect.namerepo}</h2>
                    </div>
                </div>
                <div style="display:flex; flex-direction: row; overflow: auto hidden; white-space: nowrap" class="my-3">
                    <c:forEach items="${listaRepositorieSelect}" var="element">
                        <div class="mb-3">
                            <div class="card ms-3" style="width: 18rem">
                                <img src="${element.imagen}" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <p class="card-text text-center">${element.tags}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div> 
            </c:if>  
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script>
                                            const raiz_url = "http://" + window.location.host;
                                            window.history.pushState(null, "", raiz_url + "/WebApplicationRepo/PerfilDest")


            <c:if test="${dataUserDest.getIdusers() == 0}">
                                            window.location.href = raiz_url + "/WebApplicationRepo/"
            </c:if>
        </script>
    </body>
</html>
