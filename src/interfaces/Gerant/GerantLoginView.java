package interfaces.Gerant;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;
public class GerantLoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    /**

     * public GerantLoginView(GerantController gerantController) {
     *     this.gerantController = gerantController;
     */
    public GerantLoginView() {
        setTitle("Connexion Gérant");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Espace Gérant - Connexion");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
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
            // boolean authentifie = gerantController.authentifierGerant(email, password);
            // if (authentifie) {
            //     // Ouvrir le tableau de bord
            //     GerantDashboardView dashboardView = new GerantDashboardView(gerantController);
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
            GerantDashboardView dashboardView = new GerantDashboardView();
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
        add(mainPanel);

    }
}
