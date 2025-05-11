package interfaces.view.employe;

import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;

import javax.swing.*;
import java.awt.*;

/**
 * Vue du tableau de bord pour les employés
 */
public class EmployeDashboardView extends JFrame {

    // Dans la version avec contrôleur :
    // private EmployeController employeController;

    /**
     * Constructeur
     * Dans la version avec contrôleur :
     * public EmployeDashboardView(EmployeController employeController) {
     * this.employeController = employeController;
     * ...
     * }
     */
    public EmployeDashboardView() {
        // Configuration de base
        setTitle("Tableau de Bord - Employé");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();
    }

    /**
     * Création de l'interface utilisateur
     */
    private void createUI() {
        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // En-tête
        HeaderPanel headerPanel = new HeaderPanel("Tableau de Bord - Employé");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau central avec statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Dans la version avec contrôleur :
        // int commandesEnAttente = employeController.getNombreCommandesEnAttente();
        // int commandesEnPreparation = employeController.getNombreCommandesEnPreparation();
        // int commandesPretes = employeController.getNombreCommandesPretes();

        // Pour l'instant, on utilise des valeurs simulées
        addStatCard(statsPanel, "Commandes en attente", "8");
        addStatCard(statsPanel, "En préparation", "5");
        addStatCard(statsPanel, "Prêtes", "3");
        addStatCard(statsPanel, "Total du jour", "16");

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Panneau de navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton gestionCommandesButton = new CustomButton("Gestion des Commandes", "valider");

        // Action du bouton
        gestionCommandesButton.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // EmployeCommandeView commandeView = new EmployeCommandeView(employeController);
            // commandeView.setVisible(true);
            // dispose();

            // Ouvrir la vue de gestion des commandes
            EmployeCommandeView commandeView = new EmployeCommandeView();
            commandeView.setVisible(true);
            dispose();
        });

        navPanel.add(gestionCommandesButton);

        // Bouton de déconnexion
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new CustomButton("Déconnexion", "annuler");

        logoutButton.addActionListener(e -> {
            // Retour à l'écran de connexion
            EmployeLoginView loginView = new EmployeLoginView();
            loginView.setVisible(true);
            dispose();
        });

        bottomPanel.add(logoutButton);

        // Ajouter les panneaux
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(navPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Définir le contenu
        setContentPane(mainPanel);
    }

    /**
     * Ajoute une carte de statistique au panneau
     */
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
        valueLabel.setForeground(new Color(52, 152, 219)); // Bleu
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        panel.add(card);
    }
}