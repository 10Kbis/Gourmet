/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet.utils;

import MyUtils.StringSlicer;
import gourmet.ApplicationSalle;
import gourmet.Plat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class PlatsReaderWriter {
    public static <T extends Plat> ArrayList<T> readPlats(String path, Class<T> classz) {
        ArrayList<T> plats = new ArrayList<>();
        try {
            FileReader f = new FileReader(path);
            BufferedReader reader = new BufferedReader(f);
            
            String line;
            while ((line = reader.readLine()) != null) {
                StringSlicer ss = new StringSlicer(line);
                String[] components = ss.listComponents();
                
                T p = classz.newInstance();
                p.setCode(components[0]);
                p.setLibelle(components[1]);
                p.setPrix(Double.valueOf(components[2]));
                
                plats.add(p);
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PlatsReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return plats;
    }
    
    public static void writePlats(ArrayList<? extends Plat> plats, String path) {
        try {
            FileWriter f = new FileWriter(path);
            
            for (Plat p: plats) {
                String line = String.join("&", p.toComponents()) + System.getProperty("line.separator");
                f.write(line);
            }
            
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
