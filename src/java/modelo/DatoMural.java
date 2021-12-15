/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Rafael Bastidas
 */
public class DatoMural {
    
    int idusers;
    int idrepositorie;
    String user;
    String namerepo;
    String tags;
    String imagen;
    int likes;

    
    public DatoMural(int idusers, int idrepositorie, String user, String namerepo, String tags, String imagen, int likes) {
        this.idusers = idusers;
        this.idrepositorie = idrepositorie;
        this.user = user;
        this.namerepo = namerepo;
        this.tags = tags;
        this.imagen = imagen;
        this.likes = likes;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public int getIdrepositorie() {
        return idrepositorie;
    }

    public void setIdrepositorie(int idrepositorie) {
        this.idrepositorie = idrepositorie;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNamerepo() {
        return namerepo;
    }

    public void setNamerepo(String namerepo) {
        this.namerepo = namerepo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
}
