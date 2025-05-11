package interfaces.view.Gerant;

import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de gestion des produits pour le gérant (version préparée pour le contrôleur)
 */
public class GerantProduitView extends JFrame {

    // Dans la version avec contrôleur :
    // private GerantController gerantController;

    private JPanel produitsPanel;

    /**
     * Constructeur
     */
    public GerantProduitView() {
        // Configuration de base
        setTitle("Gestion des Produits");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();

        // Charger les produits
        chargerProduits();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Produits");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ajouterButton = new CustomButton("Ajouter Produit", "ajouter");

        ajouterButton.addActionListener(e -> {
            // Dans la version avec contrôleur :
            // ouvrirFormulaireAjout();

            JOptionPane.showMessageDialog(this, "Ajout d'un produit (simulation)");
        });

        topPanel.add(ajouterButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

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
        // Dans la version avec contrôleur :
        // List<Produit> produits = gerantController.getAllProduits();
        // for (Produit produit : produits) {
        //     afficherProduit(produit);
        // }
        afficherProduit("Pizza Margherita", "8.50 €", "Plat");
        afficherProduit("Salade César", "7.00 €", "Salade");
        afficherProduit("Coca-Cola", "2.50 €", "Boisson");
        afficherProduit("Tiramisu", "5.00 €", "Dessert");
    }
    private void afficherProduit(String nom, String prix, String categorie) {
        // Version avec contrôleur :
        // private void afficherProduit(Produit produit) {
        //     String nom = produit.getNom();
        //     String prix = produit.getPrix() + " €";
        //     String categorie = produit.getCategorie();

        JPanel produitPanel = new JPanel(new BorderLayout());
        produitPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        produitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel infoLabel = new JLabel(nom + " - " + prix + " - " + categorie);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        produitPanel.add(infoLabel, BorderLayout.CENTER);

        JPanel boutonsPanel = new JPanel();

        JButton modifierButton = new CustomButton("Modifier", "modifier");
        modifierButton.addActionListener(e -> {
            // Avec contrôleur :
            // ouvrirFormulaireModification(produit);

            JOptionPane.showMessageDialog(this, "Modifier: " + nom);
        });

        JButton supprimerButton = new CustomButton("Supprimer", "supprimer");
        supprimerButton.addActionListener(e -> {
            // Avec contrôleur :
            // if (gerantController.supprimerProduit(produit.getId())) {
            //     produitsPanel.remove(produitPanel);
            //     produitsPanel.revalidate();
            //     produitsPanel.repaint();
            //     JOptionPane.showMessageDialog(this, "Produit supprimé");
            // }

            int choix = JOptionPane.showConfirmDialog(this,
                    "Supprimer " + nom + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {
                produitsPanel.remove(produitPanel);
                produitsPanel.revalidate();
                produitsPanel.repaint();
            }
        });

        boutonsPanel.add(modifierButton);
        boutonsPanel.add(supprimerButton);
        produitPanel.add(boutonsPanel, BorderLayout.EAST);

        produitsPanel.add(produitPanel);
        produitsPanel.add(Box.createVerticalStrut(5));
    }
}