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
public abstract class Plat implements Service {
    private final double _prix;
    private final String _libelle;
    private final String _code;
    
    public Plat(double prix, String libelle, String code) {
        _prix = prix;
        _libelle = libelle;
        _code = code;
    }
   
    public double getPrix() {
        return _prix;
    }
    
    public String getLibelle() {
        return _libelle;
    }
    
    public String getCode() {
        return _code;
    }
    
    public String toString() {
        return String.format("%s : %s (%.2f)", getCode(), getLibelle(), getPrix());
    }
}
