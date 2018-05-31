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
public class CommandePlat {
    private final Plat _plat;
    private final int _quantite;
    
    public CommandePlat(Plat plat, int quantite) {
        _plat = plat;
        _quantite = quantite;
    }
    
    public Plat getPlat() {
        return _plat;
    }
    
    public int getQuantite() {
        return _quantite;
    }
    
    public String toString() {
        return String.format("%d %s", getQuantite(), getPlat().toString());
    }
}
