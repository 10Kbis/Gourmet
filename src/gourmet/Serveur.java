/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

/**
 *
 * @author user
 */
public class Serveur {
    private final String _nom;
    private final String _prenom;
    private final String _login;
    private final String _carte_id;
    
    public Serveur(String nom, String prenom, String login, String carteID) {
        _nom = nom;
        _prenom = prenom;
        _login = login;
        _carte_id = carteID;
    }
    
    public String getNom() {
        return _nom;
    }
    
    public String getPrenom() {
        return _prenom;
    }
    
    public String getLogin() {
        return _login;
    }
    
    public String getCarteID() {
        return _carte_id;
    }
}
