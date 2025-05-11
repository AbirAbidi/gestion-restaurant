package interfaces.view.employe;

import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Vue de gestion des commandes pour les employés
 */
public class EmployeCommandeView extends JFrame {

    // Dans la version avec contrôleur :
    // private EmployeController employeController;

    private JPanel commandesPanel;

    // Pour gérer l'état local des commandes (simulation sans contrôleur)
    private Map<String, String> commandesEtat = new HashMap<>();

    /**
     * Constructeur
     * Dans la version avec contrôleur :
     * public EmployeCommandeView(EmployeController employeController) {
     *     this.employeController = employeController;
     *     ...
     * }
     */
    public EmployeCommandeView() {
        // Configuration de base
        setTitle("Gestion des Commandes - Employé");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();

        // Charger les commandes
        chargerCommandes();
    }

    /**
     * Création de l'interface utilisateur
     */
    private void createUI() {
        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // En-tête
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Commandes");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau du haut avec filtre par statut
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Filtrer par statut:"));
        String[] statuts = {"Toutes", "Non traitée", "En préparation", "Prête", "En livraison"};
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
            EmployeDashboardView dashboardView = new EmployeDashboardView();
            dashboardView.setVisible(true);
            dispose();
        });

        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Définir le contenu
        setContentPane(mainPanel);
    }

    /**
     * Charge et affiche les commandes
     */
    private void chargerCommandes() {
        // Initialiser les états simulés
        commandesEtat.put("CMD001", "Non traitée");
        commandesEtat.put("CMD002", "En préparation");
        commandesEtat.put("CMD003", "Prête");
        commandesEtat.put("CMD004", "Non traitée");

        // Dans la version avec contrôleur :
        // List<Commande> commandes = employeController.getCommandesATraiter();
        // for (Commande commande : commandes) {
        //     commandesEtat.put(commande.getNumero(), commande.getStatut());
        //     afficherCommande(commande);
        // }

        // Pour l'instant, on simule avec des données fixes
        commandesPanel.removeAll();
        afficherCommande("CMD001", "Jean Dupont", "Pizza, Salade");
        afficherCommande("CMD002", "Marie Martin", "Burger, Frites");
        afficherCommande("CMD003", "Paul Bernard", "Plat du jour");
        afficherCommande("CMD004", "Sophie Durand", "Sandwich, Boisson");

        commandesPanel.revalidate();
        commandesPanel.repaint();
    }

    /**
     * Affiche une commande dans la liste
     */
    private void afficherCommande(String numero, String client, String produits) {
        // Version avec contrôleur :
        // private void afficherCommande(Commande commande) {
        //     String numero = commande.getNumero();
        //     String client = commande.getClient().getNom();
        //     String produits = commande.getProduitsAsString();
        //     String statut = commande.getStatut();

        // Récupérer le statut actuel de la commande
        String statut = commandesEtat.get(numero);

        JPanel commandePanel = new JPanel(new BorderLayout());
        commandePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        commandePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Informations de la commande
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel numeroLabel = new JLabel(numero + " - " + client);
        numeroLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel produitsLabel = new JLabel("Produits: " + produits);
        JLabel statutLabel = new JLabel("Statut: " + statut);

        // Colorier le statut
        if (statut.equals("Non traitée")) {
            statutLabel.setForeground(Color.RED);
        } else if (statut.equals("En préparation")) {
            statutLabel.setForeground(Color.ORANGE);
        } else if (statut.equals("Prête")) {
            statutLabel.setForeground(Color.GREEN);
        } else if (statut.equals("En livraison")) {
            statutLabel.setForeground(Color.BLUE);
        }

        infoPanel.add(numeroLabel);
        infoPanel.add(produitsLabel);
        infoPanel.add(statutLabel);

        commandePanel.add(infoPanel, BorderLayout.CENTER);

        // Boutons pour changer le statut
        JPanel boutonsPanel = new JPanel();
        boutonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Créer les boutons selon le statut actuel
        if (statut.equals("Non traitée")) {
            JButton preparerButton = new CustomButton("Commencer préparation", "modifier");
            preparerButton.addActionListener(e -> {
                // Avec contrôleur :
                // boolean success = employeController.changerStatutCommande(numero, "En préparation");
                // if (success) {
                //     chargerCommandes();
                //     JOptionPane.showMessageDialog(this,
                //         "Commande " + numero + " mise en préparation");
                // }

                // Sans contrôleur (simulation)
                commandesEtat.put(numero, "En préparation");
                chargerCommandes();
                JOptionPane.showMessageDialog(this,
                        "Commande " + numero + " mise en préparation");
            });
            boutonsPanel.add(preparerButton);

        } else if (statut.equals("En préparation")) {
            JButton preteButton = new CustomButton("Marquer prête", "valider");
            preteButton.addActionListener(e -> {
                // Avec contrôleur :
                // boolean success = employeController.changerStatutCommande(numero, "Prête");
                // if (success) {
                //     chargerCommandes();
                //     JOptionPane.showMessageDialog(this,
                //         "Commande " + numero + " marquée comme prête");
                // }

                // Sans contrôleur (simulation)
                commandesEtat.put(numero, "Prête");
                chargerCommandes();
                JOptionPane.showMessageDialog(this,
                        "Commande " + numero + " marquée comme prête");
            });
            boutonsPanel.add(preteButton);

        } else if (statut.equals("Prête")) {
            JButton livraisonButton = new CustomButton("En livraison", "valider");
            livraisonButton.addActionListener(e -> {
                // Avec contrôleur :
                // boolean success = employeController.changerStatutCommande(numero, "En livraison");
                // if (success) {
                //     chargerCommandes();
                //     JOptionPane.showMessageDialog(this,
                //         "Commande " + numero + " partie en livraison");
                // }

                // Sans contrôleur (simulation)
                commandesEtat.put(numero, "En livraison");
                chargerCommandes();
                JOptionPane.showMessageDialog(this,
                        "Commande " + numero + " partie en livraison");
            });
            boutonsPanel.add(livraisonButton);

        } else if (statut.equals("En livraison")) {
            JButton termineeButton = new CustomButton("Terminée", "valider");
            termineeButton.addActionListener(e -> {
                // Avec contrôleur :
                // boolean success = employeController.changerStatutCommande(numero, "Livrée");
                // if (success) {
                //     commandesEtat.remove(numero); // Retirer de la liste
                //     chargerCommandes();
                //     JOptionPane.showMessageDialog(this,
                //         "Commande " + numero + " terminée");
                // }

                // Sans contrôleur (simulation)
                commandesEtat.put(numero, "Livrée");
                chargerCommandes();
                JOptionPane.showMessageDialog(this,
                        "Commande " + numero + " terminée");
            });
            boutonsPanel.add(termineeButton);
        }

        commandePanel.add(boutonsPanel, BorderLayout.EAST);

        commandesPanel.add(commandePanel);
        commandesPanel.add(Box.createVerticalStrut(5));
    }
}