/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

/**
 *
 * @author mt
 */
public class Boisson extends Plat {
    
    public Boisson() {
        
    }
    
    public Boisson(double prix) {
        super(prix, "Boisson", "B");
    }

    @Override
    public String getCategorie() {
        return CategoriePlat.BOISSON.getNom();
    }
    
    @Override
    public String toString() {
        return "Boissons avec repas (" + getPrix() + ")";
    }
}
