/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

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
        InputStream strm;
        Properties config = new Properties();
        try {
            strm = new FileInputStream("config.properties");
            config.load(strm);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }
        
        Connexion c = new Connexion(config);
        c.setModal(true);
        c.setVisible(true);
        if (c.getServeur() != null) {
                        
            ApplicationSalle app = new ApplicationSalle(c.getServeur(), config);
            app.setVisible(true);
            Thread Cuisine = new Thread(new Runnable() {
                @Override
                public void run() {
                    // code pour  ApplicationCuisine
                    ApplicationCuisine appc = new ApplicationCuisine(config);
                    appc.setVisible(true);
                }
            });
            Cuisine.start();
        }
    }
    
    
}
