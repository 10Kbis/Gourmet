/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 *
 * @author user
 */
public class CommandePlat {
    private Plat plat;
    private int quantite;
    
    public CommandePlat() {
        
    }
    
    public CommandePlat(Plat plat, int quantite) {
        this.plat = plat;
        this.quantite = quantite;
    }
    
    public Plat getPlat() {
        return plat;
    }
    
    @XmlElements({
            @XmlElement(name = "platPrincipal", type = PlatPrincipal.class),
            @XmlElement(name = "dessert", type = Dessert.class),
            @XmlElement(name = "boisson", type = Boisson.class)
    })
    public void setPlat(Plat plat) {
        this.plat = plat;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    @XmlElement
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    @Override
    public String toString() {
        return String.format("%d %s", getQuantite(), getPlat().toString());
    }
}
