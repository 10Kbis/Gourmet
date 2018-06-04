/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gourmet;

import MyUtils.StringSlicer;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import network.*;

/**
 *
 * @author dodoc
 */
public class ApplicationSalle extends javax.swing.JFrame {

    private Serveur serveur;
    private String tablePrecedente = null;
    private HashMap<String, Table> tables = new HashMap<>();
    private static final List<PlatPrincipal> PLATS = new ArrayList<>();
    private static final List<Dessert> DESSERTS = new ArrayList<>();
    private DefaultListModel<CommandePlat> modelCommandesEnvoyer;
    private DefaultListModel<CommandePlat> modelPlatsServis;
    private final HashMap<String, DefaultListModel<CommandePlat>> modelsCommandesEnvoyer = new HashMap<>();
    private final HashMap<String, DefaultListModel<CommandePlat>> modelsPlatsServis = new HashMap<>();
    private final NetworkBasicClient clientSalle;
    
    static {        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("plats.txt"));
            
            String line;
            while ((line = reader.readLine()) != null) {
                StringSlicer ss = new StringSlicer(line);
                String[] components = ss.listComponents();
                PlatPrincipal p = PlatPrincipal.createFromComponents(components);
                
                PLATS.add(p);
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        DESSERTS.add(new Dessert(5.35, "Mousse au chocolat salé", "D_MC"));
        DESSERTS.add(new Dessert(6.85, "Sorbet au citron courgette Colonel", "D_SC"));
        DESSERTS.add(new Dessert(6.00, "Duo de crêpes", "D_CR"));
        DESSERTS.add(new Dessert(5.55, "Dame grise", "D_DG"));
        DESSERTS.add(new Dessert(7.00, "Crème très brulée Carbone", "D_CB"));
    }
    
    /**
     * Creates new form ApplicationCuisine
     * @param s
     */
    public ApplicationSalle(Serveur s) {
        initComponents();
        load();
        setServeur(s);
        
        for (String num: tables.keySet()) {
            comboBoxTables.addItem(num);
            
            DefaultListModel<CommandePlat> modelEnvoyer = new DefaultListModel();
            tables.get(num).getCommandesAEnvoyer().forEach((cmd) -> {
                modelEnvoyer.addElement(cmd);
            });
            modelsCommandesEnvoyer.put(num, modelEnvoyer);
            
            DefaultListModel<CommandePlat> Servis = new DefaultListModel();
            tables.get(num).getCommandes().forEach((cmd) -> {
                Servis.addElement(cmd);
            });
            modelsPlatsServis.put(num, Servis);
        }
        
        for (PlatPrincipal plat: PLATS) {
            comboBoxPlats.addItem(plat.toString());
        }
        
        for (Dessert dessert: DESSERTS) {
            comboBoxDesserts.addItem(dessert.toString());
        }
        
        changeTable((String)comboBoxTables.getSelectedItem());
        
        clientSalle = new NetworkBasicClient(Config.get("ip"), Config.getInt("port"));
        
 //       servSalle = new NetworkBasicServer(55000,jCheckBox2);
        
        
    }
    
    /**
     * Modifie le serveur actif
     * @param s Le nouveau serveur
     */
    private void setServeur(Serveur s) {
        serveur = s;
        setTitle("Restaurant \"" + Config.get("name") + "\" : " + s.getPrenom());
    }
    
    /**
     * Récupère les models CommandesEnvoyer et PlatsServis d'une table et insère dans les JLists
     * @param table La nouvelle table selectionnée
     */
    private void changeTable(String table) {
        modelCommandesEnvoyer = modelsCommandesEnvoyer.get(table);
        modelPlatsServis = modelsPlatsServis.get(table);
        listCommandesEnvoyer.setModel(modelCommandesEnvoyer);
        listPlatsServis.setModel(modelPlatsServis);
        
        Table t = tables.get(table);
                
        Integer max = t.getMaxCouverts();
        Integer current = t.getCouverts();
        
        labelMaximumCouverts.setText(max.toString());
        labelNombreCouverts.setText(current == 0 ? "?" : current.toString());
    }
    
    private Table getSelectedTable() {
        return tables.get((String)comboBoxTables.getSelectedItem());
    }
    
    private void save() {
        
        try {
            File out = new File(Config.get("tables_file"));
            HashMapTableAdapter adapter = new HashMapTableAdapter();
            adapter.setTables(tables);
            
            JAXBContext context = JAXBContext.newInstance(
                    HashMapTableAdapter.class,
                    HashMap.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            marshaller.marshal(adapter, out);
        } catch (JAXBException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void load() {
        File f = new File(Config.get("tables_file"));
        if (!f.exists()) {
            defaultInit();
            return;
        }
        
        try {
            JAXBContext context = JAXBContext.newInstance(
                    HashMapTableAdapter.class,
                    HashMap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            HashMapTableAdapter adapter = (HashMapTableAdapter) unmarshaller.unmarshal(f);
            tables = adapter.getTables();
        } catch (JAXBException ex) {
            Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void defaultInit() {
        tables.put("D1", new Table("D1", 4));
        tables.put("D2", new Table("D2", 2));
        tables.put("D3", new Table("D3", 2));
        tables.put("D4", new Table("D4", 2));
        tables.put("D5", new Table("D5", 2));

        tables.put("G1", new Table("G1", 4));
        tables.put("G2", new Table("G2", 4));
        tables.put("G3", new Table("G3", 4));

        tables.put("C11", new Table("C11", 4));
        tables.put("C12", new Table("C12", 6));
        tables.put("C13", new Table("C13", 4));
        tables.put("C21", new Table("C21", 5));
        tables.put("C22", new Table("C22", 5));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        comboBoxTables = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboBoxPlats = new javax.swing.JComboBox<>();
        textFieldQuantitePlat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        buttonCommanderPlat = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        comboBoxDesserts = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        textFieldQuantiteDessert = new javax.swing.JTextField();
        buttonCommanderDessert = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        textFieldRemarquePlat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        buttonEncaisser = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        textFieldPrixBoisson = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        buttonAjouterBoisson = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        buttonEnvoyer = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        labelMaximumCouverts = new javax.swing.JLabel();
        labelNombreCouverts = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCommandesEnvoyer = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listPlatsServis = new javax.swing.JList<>();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuServeurs = new javax.swing.JMenu();
        menuItemModifierServeur = new javax.swing.JMenuItem();
        menuItemAjouterServeur = new javax.swing.JMenuItem();
        menuTables = new javax.swing.JMenu();
        menuItemListeTables = new javax.swing.JMenuItem();
        menuItemNombreClients = new javax.swing.JMenuItem();
        menuItemSommeAdditions = new javax.swing.JMenuItem();
        menuPlats = new javax.swing.JMenu();
        menuItemListePlats = new javax.swing.JMenuItem();
        menuItemListeDesserts = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItemCreerPlat = new javax.swing.JMenuItem();
        menuItemSupprimerPlat = new javax.swing.JMenuItem();
        menuParametres = new javax.swing.JMenu();
        menuItemInfoSys = new javax.swing.JMenuItem();
        menuItemParamDateHeure = new javax.swing.JMenuItem();
        menuAide = new javax.swing.JMenu();
        menuItemDebuter = new javax.swing.JMenuItem();
        menuItemAPropos = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Table :");

        comboBoxTables.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxTablesItemStateChanged(evt);
            }
        });

        jLabel2.setText("Plats servis");

        jLabel3.setText("Nombre maximum de couverts :");

        jLabel4.setText("Nombre de couverts :");

        jLabel5.setText("Encodage des commandes :");

        jLabel6.setText("Plats : ");

        textFieldQuantitePlat.setText("?");
        textFieldQuantitePlat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldQuantitePlatFocusGained(evt);
            }
        });

        jLabel7.setText("Quantité : ");

        buttonCommanderPlat.setText("Commander Plat");
        buttonCommanderPlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCommanderPlatActionPerformed(evt);
            }
        });

        jLabel8.setText("Desserts : ");

        jLabel9.setText("Quantité : ");

        textFieldQuantiteDessert.setText("?");
        textFieldQuantiteDessert.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldQuantiteDessertFocusGained(evt);
            }
        });

        buttonCommanderDessert.setText("Commander Dessert");
        buttonCommanderDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCommanderDessertActionPerformed(evt);
            }
        });

        jLabel10.setText("Remarques : ");

        jLabel11.setText("Addition :");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("NON PAYEE");

        buttonEncaisser.setText("Encaisser");
        buttonEncaisser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEncaisserActionPerformed(evt);
            }
        });

        jLabel13.setText("Boissons (bar) :");

        textFieldPrixBoisson.setText("?");
        textFieldPrixBoisson.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldPrixBoissonFocusGained(evt);
            }
        });

        jLabel14.setText("EUR");

        buttonAjouterBoisson.setText("Ajouter");
        buttonAjouterBoisson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAjouterBoissonActionPerformed(evt);
            }
        });

        jLabel15.setText("Commandes à envoyer :");

        jCheckBox1.setText("Plats prêts");

        jCheckBox2.setText("Commande envoyée");

        buttonEnvoyer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        buttonEnvoyer.setText("Envoyer");
        buttonEnvoyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEnvoyerActionPerformed(evt);
            }
        });

        jButton6.setText("Lire les plats disponibles");

        labelMaximumCouverts.setText("0");

        labelNombreCouverts.setText("?");

        jScrollPane1.setViewportView(listCommandesEnvoyer);

        jScrollPane2.setViewportView(listPlatsServis);

        menuServeurs.setText("Serveurs");

        menuItemModifierServeur.setText("Modifier le serveur");
        menuServeurs.add(menuItemModifierServeur);

        menuItemAjouterServeur.setText("Ajouter un nouveau serveur");
        menuServeurs.add(menuItemAjouterServeur);

        jMenuBar2.add(menuServeurs);

        menuTables.setText("Tables");

        menuItemListeTables.setText("Liste des tables");
        menuTables.add(menuItemListeTables);

        menuItemNombreClients.setText("Nombre total de clients");
        menuTables.add(menuItemNombreClients);

        menuItemSommeAdditions.setText("Somme des additions");
        menuTables.add(menuItemSommeAdditions);

        jMenuBar2.add(menuTables);

        menuPlats.setText("Plats");

        menuItemListePlats.setText("Liste des plats");
        menuPlats.add(menuItemListePlats);

        menuItemListeDesserts.setText("Liste des desserts");
        menuPlats.add(menuItemListeDesserts);
        menuPlats.add(jSeparator2);

        menuItemCreerPlat.setText("Creer un plat");
        menuPlats.add(menuItemCreerPlat);

        menuItemSupprimerPlat.setText("Supprimer un plat");
        menuPlats.add(menuItemSupprimerPlat);

        jMenuBar2.add(menuPlats);

        menuParametres.setText("Parametres");

        menuItemInfoSys.setText("Informations système");
        menuParametres.add(menuItemInfoSys);

        menuItemParamDateHeure.setText("Parametre Date-Heure");
        menuParametres.add(menuItemParamDateHeure);

        jMenuBar2.add(Box.createHorizontalGlue());

        jMenuBar2.add(menuParametres);

        menuAide.setText("Aide");

        menuItemDebuter.setText("Pour débuter");
        menuAide.add(menuItemDebuter);

        menuItemAPropos.setText("À propos...");
        menuAide.add(menuItemAPropos);

        jMenuBar2.add(menuAide);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(comboBoxTables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(textFieldQuantiteDessert, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonCommanderDessert))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textFieldRemarquePlat, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(textFieldQuantitePlat, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonCommanderPlat))))
                                    .addComponent(comboBoxDesserts, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 324, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(144, 144, 144)
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(labelNombreCouverts, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(labelMaximumCouverts, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(70, 70, 70)))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(comboBoxPlats, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(85, 85, 85)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(textFieldPrixBoisson, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel14))
                                            .addComponent(buttonAjouterBoisson)))))
                            .addComponent(jLabel3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonEncaisser)
                                .addGap(53, 53, 53))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox2)
                                .addGap(59, 59, 59)
                                .addComponent(jButton6))
                            .addComponent(buttonEnvoyer))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(comboBoxTables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(labelMaximumCouverts)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(buttonEncaisser)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(labelNombreCouverts)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboBoxPlats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(textFieldRemarquePlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPrixBoisson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldQuantitePlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(buttonCommanderPlat)
                    .addComponent(buttonAjouterBoisson))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboBoxDesserts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(textFieldQuantiteDessert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCommanderDessert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox2)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonEnvoyer))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCommanderPlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCommanderPlatActionPerformed
        int quantite = 0;
        boolean error = false;
        PlatPrincipal plat = PLATS.get(comboBoxPlats.getSelectedIndex());
        
        try {
            quantite = Integer.parseInt(textFieldQuantitePlat.getText());
            if (quantite <= 0) {
                error = true;
            }
        } catch (NumberFormatException e) {
            error = true;
        }
        
        if (error) {
            textFieldQuantitePlat.setForeground(Color.red);
        } else {
            Table t = getSelectedTable();
            try {
                t.trySetCouverts(t.getCouverts() + quantite);
                CommandePlat cmd = new CommandePlat(plat, quantite);
                modelCommandesEnvoyer.addElement(cmd);
                t.ajoutCommandeAEnvoyer(cmd);
            } catch (TooManyCoversException e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Le nombre de couverts dépasse le nombre maximum de couverts pour cette table.\nVoulez-vous quand même ajouter les couverts ?", "Que faire ?", JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    // button pressed = Yes
                    t.setCouverts(t.getCouverts() + quantite);
                    CommandePlat cmd = new CommandePlat(plat, quantite);
                    modelCommandesEnvoyer.addElement(cmd);
                    t.ajoutCommandeAEnvoyer(cmd);
                }
            }
            Integer couverts = t.getCouverts();
            labelNombreCouverts.setText(couverts.toString());
        }
    }//GEN-LAST:event_buttonCommanderPlatActionPerformed

    private void buttonAjouterBoissonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAjouterBoissonActionPerformed
        try {
            Boisson b = new Boisson(Double.parseDouble(textFieldPrixBoisson.getText()));
            CommandePlat cmd = new CommandePlat(b, 1);
            modelPlatsServis.addElement(cmd);
            Table t = getSelectedTable();
            t.ajoutCommande(cmd);
        } catch (NumberFormatException e) {
            textFieldPrixBoisson.setForeground(Color.red);
        }
    }//GEN-LAST:event_buttonAjouterBoissonActionPerformed

    private void comboBoxTablesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxTablesItemStateChanged
        if (evt.getStateChange() != ItemEvent.SELECTED) {
            return;
        }
        
        if (tablePrecedente == null) {
            tablePrecedente = (String)comboBoxTables.getSelectedItem();
            return;
        }
        
        if (comboBoxTables.getSelectedItem() == tablePrecedente) {
            // La table selectionnée est toujours la même, pas la peine d'aller
            // plus loin
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Le serveur actuel est " + serveur.getLogin() + "\n Voulez vous changer ?",
                "Choix serveur",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == 0) {
            // button pressed = Yes
            Connexion c = new Connexion();
            c.setModal(true);
            c.setVisible(true);
            if (c.getServeur() != null) {
                setServeur(c.getServeur());
            }
        }
        
        Table t = getSelectedTable();
        String selectedTable = t.getNumero();
        
        // Update de l'interface avec la nouvelle table
        changeTable(selectedTable);
        
        tablePrecedente = selectedTable;
    }//GEN-LAST:event_comboBoxTablesItemStateChanged

    private void buttonCommanderDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCommanderDessertActionPerformed
        int quantite = 0;
        boolean error = false;
        Dessert dessert = DESSERTS.get(comboBoxDesserts.getSelectedIndex());
        
        try {
            quantite = Integer.parseInt(textFieldQuantiteDessert.getText());
            if (quantite <= 0) { 
                error = true;
            }
        } catch (NumberFormatException e) {
            error = true;
        }
        
        if (error) {
            textFieldQuantiteDessert.setForeground(Color.red);
        } else {
            CommandePlat cmd = new CommandePlat(dessert, quantite);
            modelCommandesEnvoyer.addElement(cmd);
            Table t = getSelectedTable();
            t.ajoutCommandeAEnvoyer(cmd);
        }
    }//GEN-LAST:event_buttonCommanderDessertActionPerformed

    private void textFieldQuantitePlatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldQuantitePlatFocusGained
        textFieldQuantitePlat.setForeground(Color.black);
    }//GEN-LAST:event_textFieldQuantitePlatFocusGained

    private void textFieldQuantiteDessertFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldQuantiteDessertFocusGained
        textFieldQuantiteDessert.setForeground(Color.black);
    }//GEN-LAST:event_textFieldQuantiteDessertFocusGained

    private void buttonEnvoyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEnvoyerActionPerformed

        String msg = "coucou";
        String test = clientSalle.sendString(msg);
        
        for (Object cmd: modelCommandesEnvoyer.toArray()) {
            modelPlatsServis.addElement((CommandePlat)cmd);          
        }
        modelCommandesEnvoyer.clear();
        getSelectedTable().envoyerCommandes();
    }//GEN-LAST:event_buttonEnvoyerActionPerformed

    private void buttonEncaisserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEncaisserActionPerformed
        Table t = getSelectedTable();
        double prix = 0;
        
        for (Object o: modelPlatsServis.toArray()) {
            CommandePlat cmd = (CommandePlat)o;
            prix += cmd.getQuantite() * cmd.getPlat().getPrix();
        }
        
        EncaisserAddition ea = new EncaisserAddition(t, prix);
        ea.setModal(true);
        ea.setVisible(true);
    }//GEN-LAST:event_buttonEncaisserActionPerformed

    private void textFieldPrixBoissonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldPrixBoissonFocusGained
        textFieldPrixBoisson.setForeground(Color.black);
    }//GEN-LAST:event_textFieldPrixBoissonFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        save();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAjouterBoisson;
    private javax.swing.JButton buttonCommanderDessert;
    private javax.swing.JButton buttonCommanderPlat;
    private javax.swing.JButton buttonEncaisser;
    private javax.swing.JButton buttonEnvoyer;
    private javax.swing.JComboBox<String> comboBoxDesserts;
    private javax.swing.JComboBox<String> comboBoxPlats;
    private javax.swing.JComboBox<String> comboBoxTables;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel labelMaximumCouverts;
    private javax.swing.JLabel labelNombreCouverts;
    private javax.swing.JList<CommandePlat> listCommandesEnvoyer;
    private javax.swing.JList<CommandePlat> listPlatsServis;
    private javax.swing.JMenu menuAide;
    private javax.swing.JMenuItem menuItemAPropos;
    private javax.swing.JMenuItem menuItemAjouterServeur;
    private javax.swing.JMenuItem menuItemCreerPlat;
    private javax.swing.JMenuItem menuItemDebuter;
    private javax.swing.JMenuItem menuItemInfoSys;
    private javax.swing.JMenuItem menuItemListeDesserts;
    private javax.swing.JMenuItem menuItemListePlats;
    private javax.swing.JMenuItem menuItemListeTables;
    private javax.swing.JMenuItem menuItemModifierServeur;
    private javax.swing.JMenuItem menuItemNombreClients;
    private javax.swing.JMenuItem menuItemParamDateHeure;
    private javax.swing.JMenuItem menuItemSommeAdditions;
    private javax.swing.JMenuItem menuItemSupprimerPlat;
    private javax.swing.JMenu menuParametres;
    private javax.swing.JMenu menuPlats;
    private javax.swing.JMenu menuServeurs;
    private javax.swing.JMenu menuTables;
    private javax.swing.JTextField textFieldPrixBoisson;
    private javax.swing.JTextField textFieldQuantiteDessert;
    private javax.swing.JTextField textFieldQuantitePlat;
    private javax.swing.JTextField textFieldRemarquePlat;
    // End of variables declaration//GEN-END:variables

    @XmlRootElement
    private static class HashMapTableAdapter {
        private HashMap<String, Table> tables;
        
        public HashMapTableAdapter() {
            
        }
        
        public void setTables(HashMap<String, Table> tables) {
            this.tables = tables;
        }
        
        public HashMap<String, Table> getTables() {
            return tables;
        }
    }
}
