/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import gourmet.ui.ApplicationSalle;
import gourmet.ui.Connexion;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author user
 */
public class Gourmet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connexion c = new Connexion();
        c.setModal(true);
        c.setVisible(true);
        if (c.getServeur() != null) {
                        
            ApplicationSalle app = new ApplicationSalle(c.getServeur());
            app.setVisible(true);
            
        }
    }
}
