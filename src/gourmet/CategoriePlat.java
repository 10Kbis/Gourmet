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
public class CategoriePlat {
    static final CategoriePlat PLAT_PRINCIPAL = new CategoriePlat("Plat principal");
    static final CategoriePlat DESSERT = new CategoriePlat("Dessert");
    static final CategoriePlat BOISSON = new CategoriePlat("Boisson");
    static final CategoriePlat ALCOOLS = new CategoriePlat("Alcools");
    
    private String nom;
    
    public CategoriePlat(String nom) {
        this.nom = nom;
    }
    
    public String getNom() {
        return nom;
    }
    
    @XmlElement
    public void setNom(String nom) {
        this.nom = nom;
    }
}
