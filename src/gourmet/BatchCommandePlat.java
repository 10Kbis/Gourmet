/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@XmlRootElement
public class BatchCommandePlat {
    private ArrayList<CommandePlat> commandes;
    private Date date;
    private String numero;
    
    @XmlElement
    public ArrayList<CommandePlat> getCommandes() {
        return commandes;
    }
    
    public void setCommandes(ArrayList<CommandePlat> commandes) {
        this.commandes = commandes;
    }
    
    @XmlElement
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @XmlElement
    public String getNumeroTable() {
        return this.numero;
    }
    
    public void setNumeroTable(String numero) {
        this.numero = numero;
    }
    
}