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
public class Dessert extends Plat {
    public Dessert(double prix, String libelle, String code) {
        super(prix, libelle, code);
    }

    @Override
    public String getCategorie() {
        return CategoriePlat.DESSERT.getNom();
    }
}
