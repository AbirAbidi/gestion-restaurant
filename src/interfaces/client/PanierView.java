package interfaces.client;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;

public class PanierView extends JFrame {

    public PanierView() {

        setTitle("Panier");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }
    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        HeaderPanel headerPanel = new HeaderPanel("Votre Panier");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel panierPanel = new JPanel();
        panierPanel.setLayout(new BoxLayout(panierPanel, BoxLayout.Y_AXIS));
        panierPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] panierItems = {
                "Pizza Margherita - 8.50 € - Quantité: 1",
                "Salade César - 7.00 € - Quantité: 2",
        };

        for (String item : panierItems) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.add(new JLabel(item), BorderLayout.CENTER);
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JButton modifierButton = new CustomButton("Modifier", "modifier");
            modifierButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, "Modification de la quantité (simulation)")
            );

            JButton supprimerButton = new CustomButton("Supprimer", "supprimer");
            supprimerButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, "Produit supprimé (simulation)")
            );

            actionPanel.add(modifierButton);
            actionPanel.add(supprimerButton);
            row.add(actionPanel, BorderLayout.EAST);

            panierPanel.add(row);
            panierPanel.add(Box.createVerticalStrut(10)); // Espacement
        }

        // najmou nzidou fonction tehseb totale
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total: 27.50 €"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(panierPanel), BorderLayout.CENTER);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton validerButton = new CustomButton("Valider la commande", "valider");
        JButton retourButton = new CustomButton("Retour au menu", "annuler");
        JButton changePasswordButton = new CustomButton("Changer mot de passe", "modifier");

        validerButton.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Commande validée (simulation)")
        );

        retourButton.addActionListener(e -> {
            MenuView menuView = new MenuView();
            menuView.setVisible(true);
            dispose();
        });

        changePasswordButton.addActionListener(e -> ouvrirModif());
        buttonPanel.add(validerButton);
        buttonPanel.add(retourButton);
        buttonPanel.add(changePasswordButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Définir le contenu
        setContentPane(mainPanel);
    }
    private void ouvrirModif() {

        ModifPassView modifpassView = new ModifPassView();
        modifpassView.setVisible(true);

        // Fermer la fenêtre de connexion
        this.dispose();
    }
}