/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 *
 * @author user
 */
public class StringSlicer {
    String[] components;
    LinkedHashSet<String> set = new LinkedHashSet<>();
    
    public StringSlicer (String str) {
        components = str.split("&");
        set.addAll(Arrays.asList(components));
    }
    
    public int getComponents(boolean afficher) {
        return components.length;
    }
    
    public String[] listComponents() {
        return components;
    }
    
    public LinkedHashSet<String> listUniqueComponents() {
        return set;
    }
    
    
}
