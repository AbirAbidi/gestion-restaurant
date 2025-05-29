package interfaces.Gerant;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import models.Client;
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
    private GerantService gerantService;
    private CommandeService commandeService;
    private JPanel commandesPanel;

    public  GerantCommandeView (MongoDatabase database){
        GerantCommandeView.database = database ;
        this.gerantService = new GerantService(database);
        this.commandeService = new CommandeService(database);

        setTitle("Gestion des Commandes");
        setSize(700, 500);
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
        String[] statuts = {"Toutes", "En préparation", "Prête", "En livraison", "Livrée"};
        JComboBox<String> filtreStatutComboBox = new JComboBox<>(statuts);
        filtreStatutComboBox.addActionListener(e -> {
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
        commandePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Informations de la commande
        String info = id + " - " + client + " - " + EtatCommande + " - " + TypeCommande + " - " + Produits ;
        JLabel infoLabel = new JLabel(info);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        commandePanel.add(infoLabel, BorderLayout.CENTER);

        // Bouton pour changer le statut
        JPanel boutonsPanel = new JPanel();
        JButton changerStatutButton = new CustomButton("Changer Statut", "modifier");

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
                    commandeService.modifierEtatCommande(ID , statut );
                    String newInfo = id + " - " + client + " - " + statut + " - " + TypeCommande + " - " + Produits;
                    infoLabel.setText(newInfo);

                    commandesPanel.revalidate();
                    commandesPanel.repaint();
                }
            });
            statutMenu.add(item);
        }

        changerStatutButton.addActionListener(e -> {
            statutMenu.show(changerStatutButton, 0, changerStatutButton.getHeight());
        });


        boutonsPanel.add(changerStatutButton);
        commandePanel.add(boutonsPanel, BorderLayout.EAST);

        commandesPanel.add(commandePanel);
        commandesPanel.add(Box.createVerticalStrut(5));
    }
}
