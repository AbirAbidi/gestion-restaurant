package interfaces.view.Gerant;

import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;

import javax.swing.*;
import java.awt.*;
public class GerantCommandeView extends JFrame {
    // private GerantController gerantController;
    private JPanel commandesPanel;
    /**
     * public GerantCommandeView(GerantController gerantController) {
     *     this.gerantController = gerantController;
     *     ...
     * }
     */
    public GerantCommandeView() {
        // Configuration de base
        setTitle("Gestion des Commandes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();

        // Charger les commandes
        chargerCommandes();
    }
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Commandes");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filtrer par statut:"));
        String[] statuts = {"Toutes", "En préparation", "Prête", "En livraison", "Livrée"};
        JComboBox<String> filtreStatutComboBox = new JComboBox<>(statuts);
        filtreStatutComboBox.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // String statut = (String) filtreStatutComboBox.getSelectedItem();
            // if ("Toutes".equals(statut)) {
            //     chargerCommandes();
            // } else {
            //     chargerCommandesParStatut(statut);
            // }
            JOptionPane.showMessageDialog(this,
                    "Filtrage par: " + filtreStatutComboBox.getSelectedItem() + " (simulation)");
        });
        topPanel.add(filtreStatutComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        // Liste des commandes
        commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        commandesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        // Panneau du bas avec bouton retour
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton retourButton = new CustomButton("Retour au Dashboard", "annuler");
        retourButton.addActionListener(e -> {
            GerantDashboardView dashboardView = new GerantDashboardView();
            dashboardView.setVisible(true);
            dispose();
        });
        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        // Définir le contenu
        setContentPane(mainPanel);
    }
    private void chargerCommandes() {
        // Dans la version avec contrôleur :
        // List<Commande> commandes = gerantController.getAllCommandes();
        // for (Commande commande : commandes) {
        //     afficherCommande(commande);
        // }

        // Pour l'instant, on simule avec des données fixes
        afficherCommande("CMD001", "Client: Jean Dupont", "25.50 €", "En préparation");
        afficherCommande("CMD002", "Client: Marie Martin", "18.00 €", "Prête");
        afficherCommande("CMD003", "Client: Paul Bernard", "32.75 €", "En livraison");
        afficherCommande("CMD004", "Client: Sophie Durand", "15.20 €", "En préparation");
    }
    private void afficherCommande(String numero, String client, String montant, String statut) {
        // Version avec contrôleur :
        // private void afficherCommande(Commande commande) {
        //     String numero = commande.getNumero();
        //     String client = commande.getClient().getNom();
        //     String montant = commande.getMontant() + " €";
        //     String statut = commande.getStatut();

        JPanel commandePanel = new JPanel(new BorderLayout());
        commandePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        commandePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Informations de la commande
        String info = numero + " - " + client + " - " + montant + " - " + statut;
        JLabel infoLabel = new JLabel(info);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        commandePanel.add(infoLabel, BorderLayout.CENTER);

        // Bouton pour changer le statut
        JPanel boutonsPanel = new JPanel();

        JButton changerStatutButton = new CustomButton("Changer Statut", "modifier");
        changerStatutButton.addActionListener(e -> {
            // Avec contrôleur :
            // String[] statuts = {"En préparation", "Prête", "En livraison", "Livrée"};
            // String nouveauStatut = (String) JOptionPane.showInputDialog(
            //     this, "Nouveau statut:", "Modifier Statut",
            //     JOptionPane.QUESTION_MESSAGE, null, statuts, statut);
            // if (nouveauStatut != null) {
            //     gerantController.modifierStatutCommande(commande.getId(), nouveauStatut);
            //     chargerCommandes();
            // }

            JOptionPane.showMessageDialog(this,
                    "Changer statut de: " + numero + " (simulation)");
        });

        boutonsPanel.add(changerStatutButton);
        commandePanel.add(boutonsPanel, BorderLayout.EAST);

        commandesPanel.add(commandePanel);
        commandesPanel.add(Box.createVerticalStrut(5));
    }
}
