<%-- 
    Document   : index
    Created on : 13 dic. 2021, 14:51:08
    Author     : Rafael Bastidas
--%>

<%@page import="modelo.DataFollow"%>
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
    List<DataFollow> listFollow = (List<DataFollow>) request.getAttribute("listFollow");
    List<DataFollow> listFollowers = (List<DataFollow>) request.getAttribute("listFollowers");

    if (dataUser == null) {
        dataUser = new DataUser(0, "", "", "", "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg");
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
                <a href="RepositoriesController?accion=updateMural" class="btn btn-light text-danger">Ir a Mural</a>
                <div class="dropdown">
                    <button class="btn btn-light dropdown-toggle text-black-50" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Usuario
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: auto; right: 0;">
                        <li><a class="dropdown-item" href="SessionController?accion=deleteUser&idusers=<%=dataUser.getIdusers()%>">Eliminar cuenta</a></li>
                        <li><a class="dropdown-item" href="SessionController?accion=cerrarPerfil">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="container-fluid">
            <div class="row align-items-center p-3" style="background-color: rgba(224,224,224,.8)">
                <div class="col-md-4">
                    <img src=<%=dataUser.getImagen()%> class="img-fluid rounded-circle" alt="FOTO_USUARIO">
                </div>
                <div class="col-md-8 text-center">
                    <h4 class="text-black-50 text-start">Usuario: <%=dataUser.getUser()%></h4>
                    <p class="text-black-50  text-start">Bio: <%=dataUser.getBio()%></p>
                    <a href="DataUserController?idusers=<%=dataUser.getIdusers()%>" class="btn btn-primary mb-3" style="background-color: #EC4B5F">Modificar</a>
                    <button  class="btn btn-primary mb-3" style="background-color: #EC4B5F" data-bs-toggle="modal" data-bs-target="#exampleModal3">
                        (<% if (listFollow == null) {
                                out.println("0");
                            } else {
                                out.print(listFollow.size());
                            } %>) Seguidos</button>
                    <button class="btn btn-primary mb-3" style="background-color: #EC4B5F" data-bs-toggle="modal" data-bs-target="#exampleModal4">
                        (<% if (listFollowers == null) {
                                out.println("0");
                            } else {
                                out.print(listFollowers.size());
                            }%>) Seguidores</button>
                </div>
            </div>
            <div class="row py-3" style="background-color: #FFBB50">
                <div class="col-6">
                    <h2>Repositorios</h2>
                </div>
                <div class="col-6 text-end align-self-center">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        Crear repositorio
                    </button>
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
                        <a href="RepositoriesController?accion=verrepo&idrepositories=${element.idrepositories}" class="btn btn-primary btn-sm">
                            ver
                        </a>
                        <a href="RepositoriesController?accion=delete&idusers=${element.idusers}&idrepositories=${element.idrepositories}" class="btn btn-danger btn-sm">
                            Borrar
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="container-fluid">
            <c:if test="${!empty dataRepositoriesSelect}">
                <div class="row py-3" style="background-color: #FFBB50">
                    <div class="col-6">
                        <h2>${dataRepositoriesSelect.namerepo}</h2>
                    </div>
                    <div class="col-6 text-end">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal2">
                            Agregar imagen
                        </button>
                    </div>
                </div>
                <div style="display:flex; flex-direction: row; overflow: auto hidden; white-space: nowrap" class="my-3">
                    <c:forEach items="${listaRepositorieSelect}" var="element">
                        <div class="mb-3">
                            <div class="card ms-3" style="width: 18rem">
                                <img src="${element.imagen}" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <p class="card-text text-center">${element.tags}</p>
                                    <div class="d-flex justify-content-end">
                                        <button onclick="configModalEditRepositorie('${element.idrepositorie}', '${element.tags}', '${element.imagen}')" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal2">editar</button>
                                        <a href="RepositoriesController?accion=deleteImgFromRepositorie&idusers=<%=dataUser.getIdusers()%>&idrepositorie=${element.idrepositorie}&idrepositories=${element.idrepositories}" class="btn btn-danger btn-sm ms-1">borrar</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div> 
            </c:if>  
        </div>

        <!-- Modal Repositories -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <form action="RepositoriesController" method="POST">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Crear repositorio</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <div class="row g-3">
                                <div class="col-12 text-center">
                                    <h2>Datos del repositorio</h2>
                                </div>
                                <div class="col-12 text-center">
                                    <input required="true" class="form-control form-control-sm" type="text" name="namerepo" placeholder="Nombre del repositorio">
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <input hidden type="text" name="idusers" value=<%=dataUser.getIdusers()%>>
                            <input hidden type="text" name="accion" value="crear repo">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal Repositorie -->
        <div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <form action="RepositoriesController" method="POST">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel2">Agregar/editar imagen/tags</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <div class="row g-3">
                                <img src="https://cdn.pixabay.com/photo/2017/01/25/17/35/picture-2008484_960_720.png" class="img-fluid" alt="..." id="PROFILE_IMG">
                                <input id="imagen" name="imagen" type="text" hidden/>
                                <div class="col-12 text-center">
                                    <div class="input-group mb-3">
                                        <input type="file" class="form-control" id="file">
                                    </div>
                                </div>
                                <div class="col-12 text-center">
                                    <input required="true" class="form-control form-control-sm" type="text" id="tagsEdit" name="tags" placeholder="Tag, tag, tag....">
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <input hidden type="text" name="idusers" value=<%=dataUser.getIdusers()%>>
                            <input hidden type="text" name="accion" value="addImagenToRepositorie">
                            <input hidden type="text" id="idrepositorieEdit" name="idrepositorie" value="0">
                            <input hidden type="text" name="idrepositories" value=<c:out value="${dataRepositoriesSelect.idrepositories}"/>>
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal Follow -->
        <div class="modal fade" id="exampleModal3" tabindex="-1" aria-labelledby="exampleModalLabel3" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel3">Seguidos</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <div class="row g-3">
                            <c:forEach items="${listFollow}" var="element">
                                <div class="col-12 text-center">
                                    <a href="RepositoriesController?accion=verPerfilDest&idusersdest=${element.idusers}" class="btn btn-outline-success w-100 text-center">
                                        ${element.user}
                                    </a>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Followers -->
        <div class="modal fade" id="exampleModal4" tabindex="-1" aria-labelledby="exampleModalLabel4" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel4">Seguidores</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <div class="row g-3 align-self-center">
                            <c:forEach items="${listFollowers}" var="element">
                                <div class="col-12 text-center">
                                    <a href="RepositoriesController?accion=verPerfilDest&idusersdest=${element.idusers}" class="btn btn-outline-success w-100 text-center">
                                        ${element.user}
                                    </a>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script>
                                            const raiz_url = "http://" + window.location.host;
                                            window.history.pushState(null, "", raiz_url + "/WebApplicationRepo/Home")

                                            const file = document.getElementById("file");
                                            const PROFILE_IMG = document.getElementById("PROFILE_IMG");
                                            const imagen = document.getElementById("imagen");
                                            imagen.value = "https://cdn.pixabay.com/photo/2017/01/25/17/35/picture-2008484_960_720.png";
                                            let FILE_PHOTO_PROFILE = "";

                                            file.addEventListener('change', (event) => photoProfile(event));
                                            async function photoProfile(event) {
                                                if (event.target.files.length > 0) {
                                                    FILE_PHOTO_PROFILE = event.target.files[0];

                                                    try {
                                                        let FORM_DATA = new FormData();
                                                        FORM_DATA.append('file_imagen', FILE_PHOTO_PROFILE);

                                                        const REQUEST = await fetch('https://rafaelbastidas.com/apis/api-repo/app.php', {
                                                            method: 'POST',
                                                            body: FORM_DATA,
                                                            headers: {
                                                                'KEY_REPO': 'Z9AQBQXUWDHRN5GYE3DUG52BTSFT1NMA'
                                                            }
                                                        })
                                                        const RESULT = await REQUEST.json();
                                                        console.log("Reponse backend-image", RESULT);
                                                        if (RESULT.file_imagen_url.length > 0) {
                                                            var promise = getBase64(FILE_PHOTO_PROFILE);
                                                            promise.then(function (result) {
                                                                PROFILE_IMG.setAttribute('src', result);
                                                            });
                                                            imagen.value = RESULT.file_imagen_url;
                                                        } else {
                                                            imagen.value = "https://cdn.pixabay.com/photo/2017/01/25/17/35/picture-2008484_960_720.png";
                                                        }
                                                    } catch (err) {
                                                        console.error(`Hubo un problema con la petición Fetch: Error ${err.status} - ${err.statusText}`);
                                                        imagen.value = "https://cdn.pixabay.com/photo/2017/01/25/17/35/picture-2008484_960_720.png";
                                                    }
                                                } else {
                                                    imagen.value = "https://cdn.pixabay.com/photo/2017/01/25/17/35/picture-2008484_960_720.png";
                                                }
                                            }
                                            function getBase64(file) {
                                                return new Promise(function (resolve, reject) {
                                                    var reader = new FileReader();
                                                    reader.onload = function () {
                                                        resolve(reader.result);
                                                    }
                                                    reader.onerror = reject;
                                                    reader.readAsDataURL(file);
                                                });
                                            }

                                            function configModalEditRepositorie(id, tags, url) {
                                                console.log("idrepositorie", id, tags, url);
                                                document.getElementById("idrepositorieEdit").value = id
                                                document.getElementById("tagsEdit").value = tags
                                                PROFILE_IMG.setAttribute('src', url);
                                                imagen.value = url
                                            }

            <c:if test="${dataUser.getIdusers() == 0}">
                                            console.log("Entro")
                                            window.location.href = raiz_url + "/WebApplicationRepo/"
            </c:if>
        </script>
    </body>
</html>
