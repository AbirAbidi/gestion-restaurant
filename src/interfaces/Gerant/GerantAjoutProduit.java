package interfaces.Gerant;

import com.mongodb.client.MongoDatabase;
import interfaces.components.CustomButton;
import interfaces.components.HeaderPanel;
import models.Produit;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;

public class GerantAjoutProduit extends JFrame {
    private static MongoDatabase database;
    private  GerantService gerantService;
    public GerantAjoutProduit(MongoDatabase database) {
        GerantAjoutProduit.database = database ;
        this.gerantService = new GerantService(database);
        setTitle("Ajout d’un Produit");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        HeaderPanel headerPanel = new HeaderPanel("Ajouter un Produit");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Champs du formulaire
        JLabel nameLabel = new JLabel("Nom:");
        JTextField nameField = new JTextField(20);

        JLabel descLabel = new JLabel("Description:");
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);

        JLabel prixLabel = new JLabel("Prix:");
        JTextField prixField = new JTextField(10);

        JLabel typeLabel = new JLabel("Type:");
        String[] types = {"Plat", "Sandwich", "Salade", "Dessert", "Boisson"};
        JComboBox<String> typeCombo = new JComboBox<>(types);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(descLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descScroll, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(prixLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(prixField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(typeCombo, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Bas avec les boutons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ajouterButton = new CustomButton("Ajouter Produit", "ajouter");
        JButton annulerButton = new CustomButton("Annuler", "annuler");

        ajouterButton.addActionListener(e -> {
            String nom = nameField.getText().trim();
            String desc = descArea.getText().trim();
            String prix = prixField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();

            if (nom.isEmpty() || desc.isEmpty() || prix.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double p = Double.parseDouble(prix);
                JOptionPane.showMessageDialog(this, "Produit ajouté: " + nom + " (" + type + ", " + p + " TND)");
                Produit produit = new Produit(nom, desc, p, type);
                gerantService.AjoutProduit(produit);
                // this closes everything a reopens like the produitsview to refresh it
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
                GerantProduitView view = new GerantProduitView(database);
                view.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prix invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        annulerButton.addActionListener(e -> dispose());

        bottomPanel.add(ajouterButton);
        bottomPanel.add(annulerButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}
