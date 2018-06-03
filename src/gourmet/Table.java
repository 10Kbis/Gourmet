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
    private String _num;
    private ArrayList<CommandePlat> _commandes = new ArrayList<>();
    private ArrayList<CommandePlat> _commandes_envoyer = new ArrayList<>();
    private int _max_couverts = 0;
    private int _couverts = 0;
    private Boolean _addition_payee = false;
    private String _nom_serveur;
    
    public Table() {
        
    }
    
    public Table(String num, int max_couverts) {
        _num = num;
        _max_couverts = max_couverts;
    }
    
    public String getNumero() {
        return _num;
    }
    
    @XmlElement
    public void setNumero(String num) {
        _num = num;
    }
    
    public int getMaxCouverts() {
        return _max_couverts;
    }
    
    @XmlElement
    public void setMaxCouverts(int max_couverts) {
        _max_couverts = max_couverts;
    }
    
    public int getCouverts() {
        return _couverts;
    }
    
    @XmlElement
    public void setCouverts(int quantite) {
        _couverts = quantite;
    }
    
    public void trySetCouverts(int quantite) throws TooManyCoversException {
        if (quantite > getMaxCouverts()) {
            throw new TooManyCoversException();
        }
        _couverts = quantite;
    }
    
    public Boolean getAdditionPayee() {
        return _addition_payee;
    }
    
    @XmlElement
    public void setAdditionPayee(Boolean addition_payee) {
        _addition_payee = addition_payee;
    }
    
    public ArrayList<CommandePlat> getCommandes() {
        return _commandes;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "commande")
    public void setCommandes(ArrayList<CommandePlat> commandes) {
        _commandes = commandes;
    }
    
    public void ajoutCommande(CommandePlat commande) {
        _commandes.add(commande);
    }
    
    public ArrayList<CommandePlat> getCommandesAEnvoyer() {
        return _commandes_envoyer;
    }
    
    @XmlElementWrapper
    @XmlElement(name = "commandeEnvoyer")
    public void setCommandesAEnvoyer(ArrayList<CommandePlat> commandes) {
        _commandes_envoyer = commandes;
    }
    
    public void ajoutCommandeAEnvoyer(CommandePlat commande) {
        _commandes_envoyer.add(commande);
    }
    
    public void envoyerCommandes() {
        _commandes.addAll(_commandes_envoyer);
        _commandes_envoyer.clear();
    }
}
