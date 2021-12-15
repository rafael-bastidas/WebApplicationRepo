/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Rafael Bastidas
 */
public class DataRepositories {
    
    int idrepositories;
    int idusers;
    String namerepo;

    public DataRepositories(int idrepositories, int idusers, String namerepo) {
        this.idrepositories = idrepositories;
        this.idusers = idusers;
        this.namerepo = namerepo;
    }

    public int getIdrepositories() {
        return idrepositories;
    }

    public void setIdrepositories(int idrepositories) {
        this.idrepositories = idrepositories;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public String getNamerepo() {
        return namerepo;
    }

    public void setNamerepo(String namerepo) {
        this.namerepo = namerepo;
    }
    
    
}
