package interfaces.Gerant;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;

public class GerantDashboardView extends JFrame {
    // private GerantController gerantController;
    /**
     * public GerantDashboardView(GerantController gerantController) {
     * this.gerantController = gerantController;..
     * }
     */
    public GerantDashboardView() {
        // Configuration de base
        setTitle("Tableau de Bord - Gérant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();
    }

    private void createUI() {
        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // En-tête
        HeaderPanel headerPanel = new HeaderPanel("Tableau de Bord - Gérant");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau central avec statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Dans la version avec contrôleur :
        // int nombreCommandes = gerantController.getNombreCommandes();
        // double revenusJour = gerantController.getRevenusJour();
        // int nombreClients = gerantController.getNombreClients();
        // int nombreProduits = gerantController.getNombreProduits();
        addStatCard(statsPanel, "Commandes aujourd'hui", "15");
        addStatCard(statsPanel, "Revenus du jour", "750,50 €");
        addStatCard(statsPanel, "Clients actifs", "42");
        addStatCard(statsPanel, "Produits au menu", "28");

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Panneau de navigation
        JPanel navPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        JButton produitsButton = new CustomButton("Gestion Produits", "modifier");
        JButton commandesButton = new CustomButton("Gestion Commandes", "valider");
        JButton clientsButton = new CustomButton("Gestion Clients", "annuler");

        // Actions des boutons
        produitsButton.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // GerantProduitView produitView = new GerantProduitView(gerantController);
            // produitView.setVisible(true);
            // dispose();

            // CORRECTION: Ouverture directe de la vue des produits
            GerantProduitView produitView = new GerantProduitView();
            produitView.setVisible(true);
            dispose();
        }); // CORRECTION: Fermeture de l'accolade du listener

        commandesButton.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // GerantCommandeView commandeView = new GerantCommandeView(gerantController);
            // commandeView.setVisible(true);
            // dispose();

            // Ouvrir la vue de gestion des commandes
           // GerantCommandeView commandeView = new GerantCommandeView();
           // commandeView.setVisible(true);
            dispose();
        });

        clientsButton.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // GerantClientView clientView = new GerantClientView(gerantController);
            // clientView.setVisible(true);
            // dispose();

            // Ouvrir la vue de gestion des clients
            GerantClientView clientView = new GerantClientView();
            clientView.setVisible(true);
            dispose();
        });

        navPanel.add(produitsButton);
        navPanel.add(commandesButton);
        navPanel.add(clientsButton);

        // Bouton de déconnexion
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new CustomButton("Déconnexion", "annuler");

        logoutButton.addActionListener(e -> {
            // Retour à l'écran de connexion
            GerantLoginView loginView = new GerantLoginView();
            loginView.setVisible(true);
            dispose();
        });

        bottomPanel.add(logoutButton);

        // Ajouter les panneaux de navigation et de déconnexion
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(navPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    } // CORRECTION: Ajout de l'accolade fermante pour createUI()

    private void addStatCard(JPanel panel, String title, String value) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(new Color(41, 128, 185));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        panel.add(card);
    }
}