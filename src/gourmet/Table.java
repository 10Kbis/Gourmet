/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Table {
    private final String _num;
    private final ArrayList<CommandePlat> _commandes = new ArrayList<CommandePlat>();
    private final int _max_couverts;
    private int _couverts = 0;
    private final Boolean _addition_payee = false;
    private String _nom_serveur;
    
    public Table(String num, int max_couvers) {
        _num = num;
        _max_couverts = max_couvers;
    }
    
    public String getNumero() {
        return _num;
    }
    
    public int getMaxCouverts() {
        return _max_couverts;
    }
    
    public int getCouverts() {
        return _couverts;
    }
    
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
}
