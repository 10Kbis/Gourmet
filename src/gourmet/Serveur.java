/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.nom);
        hash = 71 * hash + Objects.hashCode(this.prenom);
        hash = 71 * hash + Objects.hashCode(this.login);
        hash = 71 * hash + Objects.hashCode(this.carteID);
        hash = 71 * hash + Objects.hashCode(this.password);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != Serveur.class) {
            return false;
        }
        
        Serveur s = (Serveur)obj;
        return s.getLogin().equals(getLogin()) &&
                s.getNom().equals(getNom()) &&
                s.getPrenom().equals(getPrenom()) &&
                s.getCarteID().equals(getCarteID()) &&
                s.getPassword().equals(getPassword());
    }
}
