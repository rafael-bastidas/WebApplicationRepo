/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Rafael Bastidas
 */
public class DataFollow {
    
    int idfollow;
    int idusers;
    String user;

    public DataFollow(int idfollow, int idusers, String user) {
        this.idfollow = idfollow;
        this.idusers = idusers;
        this.user = user;
    }

    public int getIdfollow() {
        return idfollow;
    }

    public void setIdfollow(int idfollow) {
        this.idfollow = idfollow;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    
    
    
}
