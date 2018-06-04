/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet.utils;

import gourmet.Config;
import gourmet.Serveur;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
public class ServeursReaderWriter {
    public static ArrayList<Serveur> readServeurs() {
        try {
            File f = new File(Config.get("serveurs_file"));
            
            JAXBContext context = JAXBContext.newInstance(ServeursAdapter.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ServeursAdapter m = (ServeursAdapter)unmarshaller.unmarshal(f);
            
            return m.getServeurs();
        } catch (JAXBException ex) {
            Logger.getLogger(ServeursReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void writeServeurs(ArrayList<Serveur> serveurs) {
        try {
            File f = new File(Config.get("serveurs_file"));
            
            JAXBContext context = JAXBContext.newInstance(ServeursAdapter.class);
            Marshaller marshaller = context.createMarshaller();
            ServeursAdapter adapter = new ServeursAdapter();
            adapter.setServeurs(serveurs);
            marshaller.marshal(adapter, f);
        } catch (JAXBException ex) {
            Logger.getLogger(ServeursReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @XmlRootElement
    private static class ServeursAdapter {
        private ArrayList<Serveur> serveurs;
        
        @XmlElement(name = "serveurs")
        public void setServeurs(ArrayList<Serveur> serveurs) {
            this.serveurs = serveurs;
        }
        
        public ArrayList<Serveur>getServeurs() {
            return serveurs;
        }
    }
}
