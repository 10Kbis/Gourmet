/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author user
 */
public class Table {
    private String num;
    private ArrayList<CommandePlat> commandes = new ArrayList<>();
    private ArrayList<CommandePlat> commandes_envoyer = new ArrayList<>();
    private int max_couverts = 0;
    private int couverts = 0;
    private Boolean addition_payee = false;
    private String nom_serveur;
    
    public Table() {
        
    }
    
    public Table(String num, int max_couverts) {
        this.num = num;
        this.max_couverts = max_couverts;
    }
    
    public String getNumero() {
        return num;
    }
    
    @XmlElement
    public void setNumero(String num) {
        this.num = num;
    }
    
    public int getMaxCouverts() {
        return max_couverts;
    }
    
    @XmlElement
    public void setMaxCouverts(int max_couverts) {
        this.max_couverts = max_couverts;
    }
    
    public int getCouverts() {
        return couverts;
    }
    
    @XmlElement
    public void setCouverts(int quantite) {
        this.couverts = quantite;
    }
    
    public void trySetCouverts(int quantite) throws TooManyCoversException {
        if (quantite > getMaxCouverts()) {
            throw new TooManyCoversException();
        }
        this.couverts = quantite;
    }
    
    public Boolean getAdditionPayee() {
        return addition_payee;
    }
    
    @XmlElement
    public void setAdditionPayee(Boolean addition_payee) {
        this.addition_payee = addition_payee;
    }
    
    public ArrayList<CommandePlat> getCommandes() {
        return commandes;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "commande")
    public void setCommandes(ArrayList<CommandePlat> commandes) {
        this.commandes = commandes;
    }
    
    public void ajoutCommande(CommandePlat commande) {
        commandes.add(commande);
    }
    
    public ArrayList<CommandePlat> getCommandesAEnvoyer() {
        return commandes_envoyer;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "commandeEnvoyer")
    public void setCommandesAEnvoyer(ArrayList<CommandePlat> commandes) {
        commandes_envoyer = commandes;
    }
    
    public void ajoutCommandeAEnvoyer(CommandePlat commande) {
        commandes_envoyer.add(commande);
    }
    
    public void envoyerCommandes() {
        commandes.addAll(commandes_envoyer);
        commandes_envoyer.clear();
    }
}
