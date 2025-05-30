package interfaces.Gerant;

import com.mongodb.client.MongoDatabase;
import interfaces.client.ClientLoginView;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import org.bson.Document;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GerantDashboardView extends JFrame {
    private  GerantService gerantService;
    private ClientService clientService;
    private MongoDatabase database;
    public GerantDashboardView(MongoDatabase database) {
        // Configuration de base
        this.database = database;
        gerantService = new GerantService(database);
        clientService = new ClientService(database);
        setTitle("Tableau de Bord - Gérant");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }

    private void createUI() {
        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // En-tête
        HeaderPanel headerPanel = new HeaderPanel("Tableau de Bord - Gérant");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau central avec statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        Map<String, Integer> nbr = gerantService.compterCommandesParStatut();
        int nbrClient = gerantService.consulterLclients().toArray().length;
        int nbrProduit = clientService.getTableMenu().length ;
        addStatCard(statsPanel, "Commandes Non Traitées", String.valueOf(nbr.getOrDefault("NON_TRAITEE", 0)));
        addStatCard(statsPanel, "Commandes Prêtes", String.valueOf(nbr.getOrDefault("PRETE", 0)));
        addStatCard(statsPanel, "Nombres de Clients", String.valueOf(nbrClient));
        addStatCard(statsPanel, "Produits au menu", String.valueOf(nbrProduit));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Panneau de navigation
        JPanel navPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        JButton produitsButton = new CustomButton("Gestion Produits", "modifier");
        JButton commandesButton = new CustomButton("Gestion Commandes", "valider");
        JButton clientsButton = new CustomButton("Gestion Clients", "annuler");

        // Actions des boutons
        produitsButton.addActionListener(e -> {
            GerantProduitView produitView = new GerantProduitView(database);
            produitView.setVisible(true);
            dispose();
        });

        commandesButton.addActionListener(e -> {
            GerantCommandeView commandeView = new GerantCommandeView(database);
            commandeView.setVisible(true);
            dispose();
        });

        clientsButton.addActionListener(e -> {
            GerantClientView clientView = new GerantClientView(database);
            clientView.setVisible(true);
            dispose();
        });

        navPanel.add(produitsButton);
        navPanel.add(commandesButton);
        navPanel.add(clientsButton);

        // Bouton de déconnexion
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new CustomButton("Déconnexion", "annuler");
        logoutButton.addActionListener(e -> {
            ClientLoginView clientLoginView = new ClientLoginView(database);
            clientLoginView.setVisible(true);
            dispose();
        });




        bottomPanel.add(logoutButton);

        // Ajouter les panneaux de navigation et de déconnexion
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(navPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

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
        valueLabel.setForeground(new Color(41, 128, 185));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        panel.add(card);
    }
}