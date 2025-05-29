package interfaces.Gerant;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Vue de gestion des produits pour le gérant (version préparée pour le contrôleur)
 */
public class GerantProduitView extends JFrame {
    private ClientService clientService;
    private static MongoDatabase database;
    private  GerantService gerantService;



    private JPanel produitsPanel;

    public GerantProduitView(MongoDatabase database) {
        GerantProduitView.database = database ;
        this.clientService = new ClientService(database);
        this.gerantService = new GerantService(database);
        setTitle("Gestion des Produits");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        chargerProduits();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Produits");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ajouterButton = new CustomButton("Ajouter Produit", "ajouter");
        ajouterButton.addActionListener(e -> {
            GerantAjoutProduit gerantAjoutProduit = new GerantAjoutProduit(database);
            gerantAjoutProduit.setVisible(true);
        });
        topPanel.add(ajouterButton);
        JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.add(headerPanel);
        headerContainer.add(topPanel);

        mainPanel.add(headerContainer, BorderLayout.NORTH);
        // Liste des produits
        produitsPanel = new JPanel();
        produitsPanel.setLayout(new BoxLayout(produitsPanel, BoxLayout.Y_AXIS));
        produitsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(produitsPanel);
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

    private void chargerProduits() {
        produitsPanel.removeAll();
        String[] produits = clientService.getTableMenu();

        for (String ligne : produits) {
            String[] parts = ligne.split(" - ");
            String name = parts[0];
            String prix = parts[1];
            String id = parts[2];

            afficherProduit(name, prix,id);
        }

        produitsPanel.revalidate();
        produitsPanel.repaint();
    }

    private void afficherProduit(String name , String prix,String id ) {
        JPanel produitPanel = new JPanel(new BorderLayout());
        produitPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        produitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel infoLabel = new JLabel(name + " - " + prix );
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        produitPanel.add(infoLabel, BorderLayout.CENTER);

        JPanel boutonsPanel = new JPanel();

        JButton modifierButton = new CustomButton("Modifier", "modifier");
        modifierButton.addActionListener(e -> {
           GerantModifierProduit gerantModifierProduit = new GerantModifierProduit(database,id);
           gerantModifierProduit.setVisible(true);

        });

        JButton supprimerButton = new CustomButton("Supprimer", "supprimer");
        supprimerButton.addActionListener(e -> {

            int choix = JOptionPane.showConfirmDialog(this,
                    "Supprimer " + name + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if(choix == JOptionPane.YES_OPTION){
                if (gerantService.supprimerPrdouit(id)) {
                    produitsPanel.remove(produitPanel);
                    produitsPanel.revalidate();
                    produitsPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                }
            }

        });

        boutonsPanel.add(modifierButton);
        boutonsPanel.add(supprimerButton);
        produitPanel.add(boutonsPanel, BorderLayout.EAST);

        produitsPanel.add(produitPanel);
        produitsPanel.add(Box.createVerticalStrut(5));
    }
}