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

        Object[][] products = clientService.getTableMenu();
        if (products.length == 0) {
            JLabel emptyLabel = new JLabel("Aucun produit disponible pour le moment.", JLabel.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            emptyLabel.setForeground(Color.GRAY);
            productsPanel.setLayout(new BorderLayout());
            productsPanel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            for (Object[] product : products) {
                String name = (String) product[0];
                Double prix = (Double) product[1];
                String id = (String) product[2];

                JPanel row = new JPanel(new BorderLayout(10, 0));

                // Texte du produit
                JLabel nameLabel = new JLabel(name + " - " + prix + " TND");
                row.add(nameLabel, BorderLayout.CENTER);

                // Dropdown TypeCommande
                JComboBox<Commande.TypeCommande> typeCommandes = new JComboBox<>(Commande.TypeCommande.values());

                // Panel pour type + bouton
                JPanel panelRight = new JPanel();
                panelRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

                panelRight.add(new JLabel("Type :"));
                panelRight.add(typeCommandes);

                JButton cartButton = new CustomButton("Ajouter", "ajouter");
                cartButton.addActionListener(e -> {
                    Commande.TypeCommande selectedType = (Commande.TypeCommande) typeCommandes.getSelectedItem();
                    Commande commande = new Commande(storedId, Commande.EtatCommande.NON_TRAITEE, selectedType, name);

                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Passer la commande ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        clientService.passerCommande(commande);
                        dispose();
                        new CommandeView(database).setVisible(true);
                    }
                });

                panelRight.add(cartButton);

                row.add(panelRight, BorderLayout.EAST);

                productsPanel.add(row);
                productsPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        JButton commandesButton = new CustomButton("Mes Commandes", "modifier");


        commandesButton.addActionListener(e -> {
            CommandeView commandeView = new CommandeView(database);
            commandeView.setVisible(true);
            dispose();
        });


        buttonPanel.add(commandesButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        setContentPane(mainPanel);
    }



}