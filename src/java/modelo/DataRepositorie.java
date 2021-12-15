/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Rafael Bastidas
 */
public class DataRepositorie {
    int idrepositorie;
    int idrepositories;
    String tags;
    String imagen;
    int likes;

    public DataRepositorie(int idrepositorie, int idrepositories, String tags, String imagen) {
        this.idrepositorie = idrepositorie;
        this.idrepositories = idrepositories;
        this.tags = tags;
        this.imagen = imagen;
    }
    public DataRepositorie(int idrepositorie, int idrepositories, String tags, String imagen, int likes) {
        this.idrepositorie = idrepositorie;
        this.idrepositories = idrepositories;
        this.tags = tags;
        this.imagen = imagen;
        this.likes = likes;
    }

    public int getIdrepositorie() {
        return idrepositorie;
    }

    public void setIdrepositorie(int idrepositorie) {
        this.idrepositorie = idrepositorie;
    }

    public int getIdrepositories() {
        return idrepositories;
    }

    public void setIdrepositories(int idrepositories) {
        this.idrepositories = idrepositories;
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
