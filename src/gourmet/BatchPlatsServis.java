/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@XmlRootElement
public class BatchPlatsServis {
    private ArrayList<CommandePlat> commandes;
    private ArrayList<String> tables;
    
    @XmlElement
    public ArrayList<CommandePlat> getCommandes() {
        return commandes;
    }
    
    public void setCommandes(ArrayList<CommandePlat> commandes) {
        this.commandes = commandes;
    }

    @XmlElement
    public ArrayList<String> getTables() {
        return tables;
    }
    
    public void setTables(ArrayList<String> tables) {
        this.tables = tables;
    }
}
