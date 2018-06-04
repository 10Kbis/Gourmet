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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Config {
    private static final Properties CONFIG = new Properties();
    
    static {
        InputStream strm;
        try {
            strm = new FileInputStream("config.properties");
            CONFIG.load(strm);
        } catch (IOException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
            System.exit(1);
        }
    }
    
    public static String get(String key) {
        return CONFIG.getProperty(key);
    }
    
    public static int getInt(String key) {
        return Integer.decode(get(key));
    }
}
