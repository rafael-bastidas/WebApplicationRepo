<%-- 
    Document   : index
    Created on : 13 dic. 2021, 14:51:08
    Author     : Rafael Bastidas
--%>

<%@page import="modelo.DataUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DataUser dataUser = (DataUser) application.getAttribute("dataUser");
    String message = (String) request.getAttribute("message");
    if (dataUser == null) {
        dataUser = new DataUser(0, "", "", "", "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area de usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </head>
    <body style="height: 100vh; background-image: url('https://cdn.pixabay.com/photo/2013/02/15/10/58/wave-81840_960_720.jpg');">

        <div class="d-flex justify-content-center align-items-center h-100 w-50 mx-auto">
            <form action="../DataUserController" method="POST">
                <div class="container-fluid p-3 rounded-3" style="background-color: rgba(224,224,224,.8)">
                <div class="row">
                    <%
                        if (message != null) {
                            out.println("<div class='alert alert-danger' role='alert'>" + message + "</div>");
                        }
                    %>
                    <div class="col-6 offset-3 text-center">
                        <img src="<%=dataUser.getImagen()%>" class="img-fluid rounded-circle" alt="FOTO_USUARIO" id="PROFILE_IMG">
                        <input id="file" type="file" accept="image/*" style="width: 0px; height: 0px;"/>
                        <input id="imagen" name="imagen" type="text" hidden/>
                        <button type="button" onclick="obtenerImagen()" class="btn btn-warning mb-3">Cambiar</button>
                    </div>
                </div>
                <div class="row g-3">
                    <div class="col-12 text-center">
                        <h2>Datos de usuario</h2>
                    </div>
                    <div class="col-12 text-center">
                        <input class="form-control form-control-sm" type="text" name="bio" placeholder="Biografia" value="<%=dataUser.getBio()%>">
                    </div>
                    <div class="col-12 text-center">
                        <input class="form-control form-control-sm" type="text" name="user" placeholder="Usuario" value="<%=dataUser.getUser()%>">
                    </div>
                    <div class="col-12">
                        <input class="form-control form-control-sm" type="password" name="password" placeholder="Contraseña" value="<%=dataUser.getPassword()%>">
                    </div>
                    <div class="col-12 align-self-center text-center">
                        <input hidden type="text" name="idusers" value=<%=dataUser.getIdusers()%>>
                        <button type="submit" class="btn btn-danger mb-3">Guardar</button>
                    </div>
                </div>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
        <script>
                            const raiz_url = "http://" + window.location.host
                            if (window.location.pathname.includes("WebApplicationRepo/Register")) {
                                window.history.pushState(null, "", raiz_url + "/WebApplicationRepo/Register/")
                            } else if (window.location.pathname.includes("DataUserController")) {
                                window.history.pushState(null, "", raiz_url + "/WebApplicationRepo/Register/")
                            }

                            const file = document.getElementById("file")
                            const PROFILE_IMG = document.getElementById("PROFILE_IMG")
                            const imagen = document.getElementById("imagen")
                            imagen.value = "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg";
                            let FILE_PHOTO_PROFILE = "";
                            function obtenerImagen() {
                                file.click()
                            }
                            file.addEventListener('change', (event) => photoProfile(event));
                            async function photoProfile(event) {
                                if (event.target.files.length > 0) {
                                    FILE_PHOTO_PROFILE = event.target.files[0]

                                    try {
                                        let FORM_DATA = new FormData()
                                        FORM_DATA.append('file_imagen', FILE_PHOTO_PROFILE)

                                        const REQUEST = await fetch('https://rafaelbastidas.com/apis/api-repo/app.php', {
                                            method: 'POST',
                                            body: FORM_DATA,
                                            headers: {
                                                'KEY_REPO': 'Z9AQBQXUWDHRN5GYE3DUG52BTSFT1NMA'
                                            }
                                        })
                                        const RESULT = await REQUEST.json()
                                        console.log("Reponse backend-image", RESULT)
                                        if (RESULT.file_imagen_url.length > 0) {
                                            var promise = getBase64(FILE_PHOTO_PROFILE)
                                            promise.then(function (result) {
                                                PROFILE_IMG.setAttribute('src', result)
                                            })
                                            imagen.value = RESULT.file_imagen_url;
                                        } else {
                                            imagen.value = "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg";
                                        }
                                    } catch (err) {
                                        console.error(`Hubo un problema con la petición Fetch: Error ${err.status} - ${err.statusText}`)
                                        imagen.value = "https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg";
                                    }
                                }
                            }
                            function getBase64(file) {
                                return new Promise(function (resolve, reject) {
                                    var reader = new FileReader()
                                    reader.onload = function () {
                                        resolve(reader.result)
                                    }
                                    reader.onerror = reject
                                    reader.readAsDataURL(file)
                                })
                            }
        </script>
    </body>
</html>
