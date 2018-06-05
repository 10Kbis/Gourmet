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
    private double prix;
    private String libelle;
    private String code;
    
    public Plat() {
        
    }
    
    public Plat(double prix, String libelle, String code) {
        this.prix = prix;
        this.libelle = libelle;
        this.code = code;
    }
    
    @Override
    public double getPrix() {
        return prix;
    }
    
    @XmlElement
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    @Override
    public String getLibelle() {
        return libelle;
    }
    
    @XmlElement
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public String getCode() {
        return code;
    }
    
    @XmlElement
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return String.format("%s : %s (%.2f)", getCode(), getLibelle(), getPrix());
    }
    
    public String[] toComponents() {
        return new String[] {
            getCode(),
            getLibelle(),
            Double.toString(getPrix()),
        };
    }
}
