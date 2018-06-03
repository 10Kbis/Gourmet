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
    private Plat _plat;
    private int _quantite;
    
    public CommandePlat() {
        
    }
    
    public CommandePlat(Plat plat, int quantite) {
        _plat = plat;
        _quantite = quantite;
    }
    
    public Plat getPlat() {
        return _plat;
    }
    
    @XmlElements({
            @XmlElement(name = "platPrincipal", type = PlatPrincipal.class),
            @XmlElement(name = "dessert", type = Dessert.class),
            @XmlElement(name = "boisson", type = Boisson.class)
    })
    public void setPlat(Plat plat) {
        _plat = plat;
    }
    
    public int getQuantite() {
        return _quantite;
    }
    
    @XmlElement
    public void setQuantite(int quantite) {
        _quantite = quantite;
    }
    
    @Override
    public String toString() {
        return String.format("%d %s", getQuantite(), getPlat().toString());
    }
}
