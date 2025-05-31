package interfaces.Gerant;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import models.User;
import models.Commande;
import org.bson.Document;
import org.bson.types.ObjectId;
import services.ClientService;
import services.CommandeService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class GerantCommandeView extends JFrame {
    private static MongoDatabase database;
    private final GerantService gerantService;
    private final CommandeService commandeService;
    private JPanel commandesPanel;

    public  GerantCommandeView (MongoDatabase database){
        GerantCommandeView.database = database ;
        this.gerantService = new GerantService(database);
        this.commandeService = new CommandeService(database);

        setTitle("Gestion des Commandes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        chargerCommandes();
    }
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Commandes");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filtrer par statut:"));
//------------------------ this like fetches all states fron the enum and in the 2nd line it adds l option TOUTES to it
        String[] statuts = Arrays.stream(Commande.EtatCommande.values()).map(Enum::toString).toArray(String[]::new);
        String[] statutsAvecToutes = new String[statuts.length + 1];
        statutsAvecToutes[0] = "Toutes";
        System.arraycopy(statuts, 0, statutsAvecToutes, 1, statuts.length);
//-------------------------------------------------------------------------------------------------------------------
        JComboBox<String> filtreStatutComboBox = new JComboBox<>(statutsAvecToutes);
        filtreStatutComboBox.addActionListener(e -> {
            String statutChoisi = (String) filtreStatutComboBox.getSelectedItem();
            chargerCommandesParStatut(statutChoisi);
        });

        topPanel.add(filtreStatutComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        commandesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        // Panneau du bas avec bouton retour
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton retourButton = new CustomButton("Retour au Dashboard", "annuler");
        retourButton.addActionListener(e -> {
            GerantDashboardView dashboardView = new GerantDashboardView(database);
            dashboardView.setVisible(true);
            dispose();
        });
        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }
    private void  chargerCommandes() {


        JTable commandesTable = gerantService.afficherCommandeClient();
        // Process each row in the table
        for (int i = 0; i < commandesTable.getRowCount(); i++) {
            String ID = commandesTable.getValueAt(i, 0).toString();
            String client = commandesTable.getValueAt(i, 1).toString();
            String nameClient = gerantService.getClientName(client);
            String etatCommande = commandesTable.getValueAt(i, 3).toString();
            String typeCommande = commandesTable.getValueAt(i, 2).toString();
            List<String> produits ;
                // If products are stored as a comma-separated string, split them
                String produitsStr = commandesTable.getValueAt(i, 4).toString();
                String[] produitsArray = produitsStr.split(",");
                produits = Arrays.asList(produitsArray);


            // Call afficherCommande with the extracted data
            afficherCommande(ID ,String.valueOf(i+1), nameClient, etatCommande, typeCommande, produits);
        }

        // Refresh the UI
        commandesPanel.revalidate();
        commandesPanel.repaint();
    }
    private void afficherCommande(String ID ,String id, String client, String EtatCommande, String TypeCommande,List Produits) {
        JPanel commandePanel = new JPanel(new BorderLayout());
        commandePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        commandePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Informations de la commande
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

// Ligne 1 : Numéro + Client
        JLabel numeroLabel = new JLabel(id + " - " + client);
        numeroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(numeroLabel);

// Ligne 2 : Produits
        JLabel produitsLabel = new JLabel("Produits: " + Produits);
        infoPanel.add(produitsLabel);

        JLabel statutLabel = new JLabel("Statut: " + EtatCommande);

        switch (EtatCommande) {
            case "ANNULE":
                statutLabel.setForeground(Color.RED);
                break;
            case "EN_PREPARATION":
                statutLabel.setForeground(Color.ORANGE);
                break;
            case "PRETE":
                statutLabel.setForeground(Color.GREEN);
                break;
            case "NON_TRAITEE":
                statutLabel.setForeground(Color.BLUE);
                break;
        }
        infoPanel.add(statutLabel);
        commandePanel.add(infoPanel, BorderLayout.CENTER);


        // Bouton pour changer le statut
        JPanel boutonsPanel = new JPanel();
        JButton changerStatutButton = new CustomButton("Changer Statut", "modifier");
        JButton supprimerCommande = new CustomButton("Supprimer Commande", "supprimer");

        JPopupMenu statutMenu = new JPopupMenu();
        String[] statuts = Arrays.stream(Commande.EtatCommande.values())
                .map(Enum::name)
                .filter(s -> !s.equals(EtatCommande)) // this line is responsible on showinf every etat to change to sauf the one we have rn
                .toArray(String[]::new);

        for (String statut : statuts) {
            JMenuItem item = new JMenuItem(statut);
            item.addActionListener(evt -> {
                int choix = JOptionPane.showConfirmDialog(this,
                        "Changer le statut de la commande numéro " + id + " à \"" + statut + "\" ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (choix == JOptionPane.YES_OPTION) {
                    commandeService.modifierEtatCommande(ID, statut);
                    commandesPanel.removeAll();
                    chargerCommandes();
                }
            });
            statutMenu.add(item);
        }

        changerStatutButton.addActionListener(e -> {
            statutMenu.show(changerStatutButton, 0, changerStatutButton.getHeight());
        });

        supprimerCommande.addActionListener(e -> {

            int choix = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous supprimer cette commande ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {
                if (gerantService.suppprimerCommande(ID)) {
                    JOptionPane.showMessageDialog(this, "Commande supprimée !");

                    // Fermer toutes les fenêtres
                    for (Window window : Window.getWindows()) {
                        window.dispose();
                    }

                    GerantCommandeView nouvelleVue = new GerantCommandeView(database);
                    nouvelleVue.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la suppression.");
                }
            }
        });


        boutonsPanel.add(changerStatutButton);
        boutonsPanel.add(supprimerCommande);
        commandePanel.add(boutonsPanel, BorderLayout.EAST);

        commandesPanel.add(commandePanel);
        commandesPanel.add(Box.createVerticalStrut(5));
    }
    private void chargerCommandesParStatut(String statut) {
        commandesPanel.removeAll(); // vider l'ancien contenu

        JTable commandesTable = gerantService.afficherCommandeClient();
        for (int i = 0; i < commandesTable.getRowCount(); i++) {
            String ID = commandesTable.getValueAt(i, 0).toString();
            String client = commandesTable.getValueAt(i, 1).toString();
            String nameClient = gerantService.getClientName(client);
            String etatCommande = commandesTable.getValueAt(i, 3).toString();
            String typeCommande = commandesTable.getValueAt(i, 2).toString();
            String produitsStr = commandesTable.getValueAt(i, 4).toString();
            List<String> produits = Arrays.asList(produitsStr.split(","));

            // Si "Toutes" est sélectionné, ou que le statut correspond, on affiche
            if (statut.equals("Toutes") || etatCommande.equals(statut)) {
                afficherCommande(ID, String.valueOf(i + 1), nameClient, etatCommande, typeCommande, produits);
            }
        }

        commandesPanel.revalidate();
        commandesPanel.repaint();
    }

}
