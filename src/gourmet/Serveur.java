/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */

@XmlRootElement
public class Serveur {
    private String nom;
    private String prenom;
    private String login;
    private String carteID;
    private String password;
    
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getCarteID() {
        return carteID;
    }
    
    public String getPassword() {
        return password;
    }
    
    @XmlElement
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    @XmlElement
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    @XmlElement
    public void setCarteID(String carteID) {
        this.carteID = carteID;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }
}
