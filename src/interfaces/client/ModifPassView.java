package interfaces.client;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;
public class ModifPassView extends JFrame {

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ModifPassView() {
        // Configuration de base
        setTitle("Changer mot de passe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        HeaderPanel headerPanel = new HeaderPanel("Changer votre mot de passe");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Ancien mot de passe:"));
        oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordField);

        formPanel.add(new JLabel("Nouveau mot de passe:"));
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField);

        formPanel.add(new JLabel("Confirmer mot de passe:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton validerButton = new CustomButton("Valider", "valider");
        JButton annulerButton = new CustomButton("Annuler", "annuler");

        validerButton.addActionListener(e -> {
            // Vérifier que les champs sont remplis
            if (oldPasswordField.getPassword().length == 0 ||
                    newPasswordField.getPassword().length == 0 ||
                    confirmPasswordField.getPassword().length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPass = new String(newPasswordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this,
                        "Les nouveaux mots de passe ne correspondent pas",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Mot de passe modifié avec succès (simulation)",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

            retourMenu();
        });

        annulerButton.addActionListener(e -> retourMenu());

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        setContentPane(mainPanel);
    }
    private void retourMenu() {
        MenuView menuView = new MenuView();
        menuView.setVisible(true);
        dispose();
    }
}