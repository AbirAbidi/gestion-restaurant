package interfaces.Gerant;

import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de gestion des clients pour le gérant
 */
public class GerantClientView extends JFrame {

    // Dans la version avec contrôleur :
    // private GerantController gerantController;

    private JPanel clientsPanel;

    /**
     * Constructeur
     * Dans la version avec contrôleur :
     * public GerantClientView(GerantController gerantController) {
     * this.gerantController = gerantController;
     * ...
     * }
     */
    public GerantClientView() {
        // Configuration de base
        setTitle("Gestion des Clients");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();

        // Charger les clients
        chargerClients();
    }

    /**
     * Création de l'interface utilisateur
     */
    private void createUI() {
        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // En-tête
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Clients");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Liste des clients
        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(clientsPanel);
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

    /**
     * Charge et affiche les clients
     */
    private void chargerClients() {
        // Dans la version avec contrôleur :
        // List<Client> clients = gerantController.getAllClients();
        // for (Client client : clients) {
        //     afficherClient(client);
        // }

        // Pour l'instant, on simule avec des données fixes
        afficherClient("Jean Dupont", "jean.dupont@email.com", "0612345678");
        afficherClient("Marie Martin", "marie.martin@email.com", "0687654321");
        afficherClient("Paul Bernard", "paul.bernard@email.com", "0654321098");
        afficherClient("Sophie Durand", "sophie.durand@email.com", "0698765432");
    }

    /**
     * Affiche un client dans la liste
     */
    private void afficherClient(String nom, String email, String telephone) {
        // Version avec contrôleur :
        // private void afficherClient(Client client) {
        //     String nom = client.getNom() + " " + client.getPrenom();
        //     String email = client.getEmail();
        //     String telephone = client.getTelephone();

        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        clientPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Informations du client
        String info = nom + " - " + email + " - " + telephone;
        JLabel infoLabel = new JLabel(info);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        clientPanel.add(infoLabel, BorderLayout.CENTER);

        // Boutons d'action
        JPanel boutonsPanel = new JPanel();

        JButton voirHistoriqueButton = new CustomButton("Historique", "modifier");
        voirHistoriqueButton.addActionListener(e -> {
            // Avec contrôleur :
            // List<Commande> historique = gerantController.getHistoriqueClient(client.getId());
            // afficherHistorique(historique);

            JOptionPane.showMessageDialog(this,
                    "Historique des commandes de: " + nom + " (simulation)");
        });

        JButton supprimerButton = new CustomButton("Supprimer", "supprimer");
        supprimerButton.addActionListener(e -> {
            // Avec contrôleur :
            // int choix = JOptionPane.showConfirmDialog(this,
            //     "Supprimer le client " + nom + " ?",
            //     "Confirmation",
            //     JOptionPane.YES_NO_OPTION);
            // if (choix == JOptionPane.YES_OPTION) {
            //     gerantController.supprimerClient(client.getId());
            //     chargerClients();
            // }

            int choix = JOptionPane.showConfirmDialog(this,
                    "Supprimer le client " + nom + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {
                clientsPanel.remove(clientPanel);
                clientsPanel.revalidate();
                clientsPanel.repaint();
                JOptionPane.showMessageDialog(this, "Client supprimé (simulation)");
            }
        });

        boutonsPanel.add(voirHistoriqueButton);
        boutonsPanel.add(supprimerButton);
        clientPanel.add(boutonsPanel, BorderLayout.EAST);

        clientsPanel.add(clientPanel);
        clientsPanel.add(Box.createVerticalStrut(5));
    }
}