package interfaces.view.client;
import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;

import javax.swing.*;
import java.awt.*;


public class RegisterView extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField adresseField;
    private JTextField dateNaissanceField;
    private JButton validerButton;
    private JButton annulerButton;
    public RegisterView() {
        // Configuration de base
        setTitle("Inscription Client");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();
    }
    private void createUI() {
        setLayout(new BorderLayout());
        add(new HeaderPanel("Inscription Nouveau Client"), BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        adresseField = new JTextField(20);
        dateNaissanceField = new JTextField(20);
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Adresse:"));
        formPanel.add(adresseField);

        formPanel.add(new JLabel("Date de naissance (jj/mm/aaaa):"));
        formPanel.add(dateNaissanceField);
        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();

        validerButton = new CustomButton("Valider", "valider");
        annulerButton = new CustomButton("Annuler", "annuler");


        validerButton.addActionListener(e -> {
            // Vérification que tous les champs sont remplis
            if (nomField.getText().isEmpty() ||
                    prenomField.getText().isEmpty() ||
                    emailField.getText().isEmpty() ||
                    passwordField.getPassword().length == 0 ||
                    adresseField.getText().isEmpty() ||
                    dateNaissanceField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez remplir tous les champs du formulaire",
                        "Champs manquants",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!emailField.getText().contains("@")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer une adresse email valide",
                        "Format email incorrect",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            JOptionPane.showMessageDialog(
                    this,
                    "Inscription réussie (simulation)!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Retourner à l'écran de connexion
            ClientLoginView loginView = new ClientLoginView();
            loginView.setVisible(true);
            dispose();
        });

        annulerButton.addActionListener(e -> retourLogin());

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);


        formPanel.add(new JLabel(""));
        formPanel.add(buttonPanel);


        add(formPanel, BorderLayout.CENTER);
    }
    private void retourLogin() {
        // Créer et afficher la vue de connexion
        ClientLoginView loginView = new ClientLoginView();
        loginView.setVisible(true);

        // Fermer la fenêtre d'inscription
        this.dispose();
    }


}
