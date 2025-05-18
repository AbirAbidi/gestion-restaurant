package interfaces.employe;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de connexion pour les employés
 */
public class EmployeLoginView extends JFrame {

    // Dans la version avec contrôleur :
    // private EmployeController employeController;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    /**
     * Constructeur
     * Dans la version avec contrôleur :
     * public EmployeLoginView(EmployeController employeController) {
     * this.employeController = employeController;
     * ...
     * }
     */
    public EmployeLoginView() {
        // Configuration de base
        setTitle("Connexion Employé");
        setSize(400, 300);
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
        HeaderPanel headerPanel = new HeaderPanel("Espace Employé - Connexion");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Email
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // Mot de passe
        formPanel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        loginButton = new CustomButton("Connexion", "valider");
        cancelButton = new CustomButton("Annuler", "annuler");

        // Action du bouton de connexion
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Vérification basique
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Dans la version avec contrôleur :
            // boolean authentifie = employeController.authentifierEmploye(email, password);
            // if (authentifie) {
            //     // Ouvrir le tableau de bord
            //     EmployeDashboardView dashboardView = new EmployeDashboardView(employeController);
            //     dashboardView.setVisible(true);
            //     dispose();
            // } else {
            //     JOptionPane.showMessageDialog(this,
            //             "Email ou mot de passe incorrect",
            //             "Erreur d'authentification",
            //             JOptionPane.ERROR_MESSAGE);
            // }

            // Pour l'instant, on simule une connexion réussie
            JOptionPane.showMessageDialog(this,
                    "Connexion réussie (simulation)",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

            // Ouvrir le tableau de bord
            EmployeDashboardView dashboardView = new EmployeDashboardView();
           dashboardView.setVisible(true);
           dispose();
        });

        // Action du bouton d'annulation
        cancelButton.addActionListener(e -> {
            // Fermer la fenêtre
            dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Définir le contenu
        setContentPane(mainPanel);
    }
}