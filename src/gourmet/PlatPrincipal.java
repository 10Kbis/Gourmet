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
public class PlatPrincipal extends Plat {
    
    public PlatPrincipal() {
        
    }
    
    public PlatPrincipal(double prix, String libelle, String code) {
        super(prix, libelle, code);
    }

    @Override
    public String getCategorie() {
        return CategoriePlat.PLAT_PRINCIPAL.getNom();
    }
    
    public static PlatPrincipal createFromComponents(String[] components) {
        String code = components[0];
        String libelle = components[1];
        Double prix = Double.valueOf(components[2]);
        
        return new PlatPrincipal(prix, libelle, code);
    }
}
