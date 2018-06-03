/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author user
 */
public abstract class Plat implements Service {
    private double _prix;
    private String _libelle;
    private String _code;
    
    public Plat() {
        
    }
    
    public Plat(double prix, String libelle, String code) {
        _prix = prix;
        _libelle = libelle;
        _code = code;
    }
   
    @Override
    public double getPrix() {
        return _prix;
    }
    
    @XmlElement
    public void setPrix(double prix) {
        _prix = prix;
    }
    
    @Override
    public String getLibelle() {
        return _libelle;
    }
    
    @XmlElement
    public void setLibelle(String libelle) {
        _libelle = libelle;
    }
    
    public String getCode() {
        return _code;
    }
    
    @XmlElement
    public void setCode(String code) {
        _code = code;
    }
    
    @Override
    public String toString() {
        return String.format("%s : %s (%.2f)", getCode(), getLibelle(), getPrix());
    }
}
