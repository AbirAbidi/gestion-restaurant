package interfaces.client;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.prefs.Preferences;

public class CommandeView extends JFrame {
    private GerantService clientService;
    private static MongoDatabase database;
    Preferences prefs = Preferences.userRoot().node("Ids");
    String storedId = prefs.get("userID", null);
    public CommandeView(MongoDatabase database){

        CommandeView.database = database ;
        this.clientService = new GerantService(database);
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
        JPanel headerRow = new JPanel(new GridLayout(1, 5));
        headerRow.add(new JLabel("Numéro", JLabel.CENTER));
        headerRow.add(new JLabel("Montant", JLabel.CENTER));
        headerRow.add(new JLabel("Statut", JLabel.CENTER));
        headerRow.add(new JLabel("Type", JLabel.CENTER));
        headerRow.add(new JLabel("Liste Produits", JLabel.CENTER));
        commandesPanel.add(headerRow);
        commandesPanel.add(Box.createVerticalStrut(10));
        JTable table = clientService.afficherCommandeClient();

        int clientCol = table.getColumnModel().getColumnIndex("Client");
        int typeCol = table.getColumnModel().getColumnIndex("TypeCommande");
        int etatCol = table.getColumnModel().getColumnIndex("EtatCommande");
        int produitsCol = table.getColumnModel().getColumnIndex("Produits");
        ArrayList<Object[]> commandes  = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            String clientIdObj = table.getValueAt(i, clientCol).toString();
            if (clientIdObj != null && clientIdObj.equals(storedId)) {
                Object[] row = new Object[4];
                row[0] = i;        // Numéro
                row[1] = table.getValueAt(i, typeCol);      // TypeCommande
                row[2] = table.getValueAt(i, etatCol);      // EtatCommande
                row[3] = table.getValueAt(i, produitsCol);  // Produits
                commandes.add(row);
            }
        }
        System.out.println(commandes);

        for (Object[] commande : commandes) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            row.add(new JLabel(commande[0].toString(), JLabel.CENTER));
            row.add(new JLabel(commande[1].toString(), JLabel.CENTER));
            JLabel statutLabel = new JLabel(commande[2].toString(), JLabel.CENTER);
            String statut = commande[2].toString();
            if (statut.equals("NON_TRAITEE")) {
                statutLabel.setForeground(new Color(0, 0, 255)); // Bleu
            } else if (statut.equals("PRETE")) {
                statutLabel.setForeground(new Color(0, 128, 0)); // Vert
            } else if (statut.equals("EN_PREPARATION")) {
                statutLabel.setForeground(new Color(255, 165, 0)); // Orange
            } else if (statut.equals("ANNULE")) {
                statutLabel.setForeground(new Color(255, 0, 102)); // rouge
            }
            row.add(statutLabel);
            row.add(new JLabel(commande[3].toString(), JLabel.CENTER));






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
           /* MenuView menuView = new MenuView();
            menuView.setVisible(true);*/
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