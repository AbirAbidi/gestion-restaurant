package interfaces.client;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import models.Commande;
import services.ClientService;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.prefs.Preferences;

public class MenuView extends JFrame {
private final ClientService clientService ;
private static MongoDatabase database;
    Preferences prefs = Preferences.userRoot().node("Ids");
    String storedId = prefs.get("userID", null);

    public MenuView(MongoDatabase database) {
        MenuView.database = database ;
        this.clientService = new ClientService(database);
        // Configuration de base
        setTitle("Menu du Restaurant");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }


    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Menu du Restaurant");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] products = clientService.getTableMenu();
        for (String product : products) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.add(new JLabel(product), BorderLayout.CENTER);
            // drop down menu
            JComboBox<Commande.TypeCommande> typeCommandes = new JComboBox<>(Commande.TypeCommande.values());
            Commande.TypeCommande selectedType = (Commande.TypeCommande) typeCommandes.getSelectedItem();
            JPanel panel = new JPanel();
            panel.add(new JLabel("Type de commande :"));
            panel.add(typeCommandes);

            JButton cartButton = new CustomButton("Ajouter", "ajouter");
            cartButton.addActionListener(e -> {
                List<String> produitListe = Collections.singletonList(product);
                Commande commande = new Commande(storedId, Commande.EtatCommande.NON_TRAITEE,selectedType,produitListe);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Passer la commande ?"  , "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    clientService.passerCommande(commande);
                    dispose();
                    new CommandeView(database).setVisible(true);
                }
            });
            row.add(cartButton, BorderLayout.EAST);
            productsPanel.add(row);
            productsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton panierButton = new CustomButton("Voir Panier", "valider");
        JButton retourButton = new CustomButton("Retour", "annuler");
        JButton commandesButton = new CustomButton("Mes Commandes", "modifier");


        retourButton.addActionListener(e -> {
           /* ClientLoginView loginView = new ClientLoginView();
            loginView.setVisible(true);*/
            dispose();
        });

        buttonPanel.add(panierButton);
        buttonPanel.add(retourButton);
        buttonPanel.add(commandesButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        setContentPane(mainPanel);
    }



}