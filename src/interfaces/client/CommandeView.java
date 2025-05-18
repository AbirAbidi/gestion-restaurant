package interfaces.client;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CommandeView extends JFrame {

    public CommandeView() {
        // Configuration de base
        setTitle("Mes Commandes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }
    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        HeaderPanel headerPanel = new HeaderPanel("Mes Commandes");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        commandesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En-tête du tableau
        JPanel headerRow = new JPanel(new GridLayout(1, 4));
        headerRow.add(new JLabel("Numéro", JLabel.CENTER));
        headerRow.add(new JLabel("Date", JLabel.CENTER));
        headerRow.add(new JLabel("Montant", JLabel.CENTER));
        headerRow.add(new JLabel("Statut", JLabel.CENTER));
        commandesPanel.add(headerRow);
        commandesPanel.add(Box.createVerticalStrut(10));
        // dans la version finale on va utiliser List<Commande> commandes = clientController.getCommandesEnCours();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // a supprime apres
       Object[][] commandes = {
                {"CMD001", dateFormat.format(new Date()), "25.50 €", "En livraison"},
                {"CMD002", dateFormat.format(new Date()), "18.00 €", "Prête"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD001", dateFormat.format(new Date()), "25.50 €", "En livraison"},
                {"CMD002", dateFormat.format(new Date()), "18.00 €", "Prête"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD001", dateFormat.format(new Date()), "25.50 €", "En livraison"},
                {"CMD002", dateFormat.format(new Date()), "18.00 €", "Prête"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"},
                {"CMD003", dateFormat.format(new Date()), "32.75 €", "En préparation"}
        };



        // Ajouter les commandes au panneau
        for (Object[] commande : commandes) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            row.add(new JLabel(commande[0].toString(), JLabel.CENTER));
            row.add(new JLabel(commande[1].toString(), JLabel.CENTER));
            row.add(new JLabel(commande[2].toString(), JLabel.CENTER));

            // Colorier le statut selon son type
            JLabel statutLabel = new JLabel(commande[3].toString(), JLabel.CENTER);
            String statut = commande[3].toString();

            if (statut.equals("En livraison")) {
                statutLabel.setForeground(new Color(0, 0, 255)); // Bleu
            } else if (statut.equals("Prête")) {
                statutLabel.setForeground(new Color(0, 128, 0)); // Vert
            } else if (statut.equals("En préparation")) {
                statutLabel.setForeground(new Color(255, 165, 0)); // Orange
            } else if (statut.equals("Annulée")) {
            statutLabel.setForeground(new Color(255, 0, 102)); // rouge
        }

            row.add(statutLabel);

            commandesPanel.add(row);
            commandesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton menuButton = new CustomButton("Retour au menu", "annuler");
        JButton panierButton = new CustomButton("Voir panier", "valider");
        menuButton.addActionListener(e -> {
            MenuView menuView = new MenuView();
            menuView.setVisible(true);
            dispose();
        });
        panierButton.addActionListener(e -> {
            PanierView panierView = new PanierView();
            panierView.setVisible(true);
            dispose();
        });
        buttonPanel.add(menuButton);
        buttonPanel.add(panierButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }
}