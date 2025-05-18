package interfaces.client;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    public MenuView() {
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

        String[] products = {
                "Pizza Margherita - 8.50 €",
                "Salade César - 7.00 €",
                "Tiramisu - 5.00 €",
                "Sandwich Jambon-Fromage - 4.50 €"
        };

        for (String product : products) {
            JPanel row = new JPanel(new BorderLayout(10, 0));
            row.add(new JLabel(product), BorderLayout.CENTER);

            JButton cartButton = new CustomButton("Ajouter", "ajouter");
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
        commandesButton.addActionListener(e -> {
            CommandeView commandeView = new CommandeView();
            commandeView.setVisible(true);
            dispose();
        });
                panierButton.addActionListener(e -> ouvrirPanier());

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
    private void ouvrirPanier() {
        PanierView panierView = new PanierView();
        panierView.setVisible(true);
        this.dispose();
    }


}