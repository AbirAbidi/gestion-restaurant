package interfaces.client;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class CommandeView extends JFrame {
    private final GerantService gerantService;
    private final ClientService clientService;
    private static MongoDatabase database;
    Preferences prefs = Preferences.userRoot().node("Ids");
    String storedId = prefs.get("userID", null);
    public CommandeView(MongoDatabase database){

        CommandeView.database = database ;
        this.gerantService = new GerantService(database);
        this.clientService = new ClientService(database);
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
        JPanel headerRow = new JPanel(new GridLayout(1, 6));
        headerRow.add(new JLabel("", JLabel.CENTER));
        headerRow.add(new JLabel("Numéro", JLabel.CENTER));
        headerRow.add(new JLabel("Montant", JLabel.CENTER));
        headerRow.add(new JLabel("Statut", JLabel.CENTER));
        headerRow.add(new JLabel("Type", JLabel.CENTER));
        headerRow.add(new JLabel("Liste Produits", JLabel.CENTER));
        commandesPanel.add(headerRow);
        commandesPanel.add(Box.createVerticalStrut(10));
        JTable table = gerantService.afficherCommandeClient();
        int idCol = table.getColumnModel().getColumnIndex("ID");
        int clientCol = table.getColumnModel().getColumnIndex("Client");
        int typeCol = table.getColumnModel().getColumnIndex("TypeCommande");
        int etatCol = table.getColumnModel().getColumnIndex("EtatCommande");
        int produitsCol = table.getColumnModel().getColumnIndex("Produits");
        ArrayList<Object[]> commandes  = new ArrayList<>();
// TODO : add the prize column , its not now cuz the prize is in collection produits only
        for (int i = 0; i < table.getRowCount(); i++) {
            String clientIdObj = table.getValueAt(i, clientCol).toString();
            if (clientIdObj != null && clientIdObj.equals(storedId)) {
                Object[] row = new Object[5];
                row[0] = i;        // Numéro
                row[1] = "0 TND";
                row[2] = table.getValueAt(i, typeCol);      // TypeCommande
                row[3] = table.getValueAt(i, etatCol);      // EtatCommande
                row[4] = table.getValueAt(i, produitsCol);  // Produits
                commandes.add(row);
            }
        }

        for (Object[] commande : commandes) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            JButton supprimerBtn = new JButton("❌");
            supprimerBtn.setForeground(Color.RED);
            supprimerBtn.addActionListener(e -> {
                String idCommande = table.getValueAt(0, idCol).toString(); // récupérer l'ID réel
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Supprimer la commande " + idCommande + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    clientService.supprimerCommande(idCommande);
                    dispose();
                    new CommandeView(database).setVisible(true);
                }
            });
            row.add(supprimerBtn);
            row.add(new JLabel(commande[0].toString(), JLabel.CENTER));
            row.add(new JLabel(commande[1].toString(), JLabel.CENTER));
            row.add(new JLabel(commande[2].toString(), JLabel.CENTER));
            JLabel statutLabel = new JLabel(commande[3].toString(), JLabel.CENTER);
            String statut = commande[3].toString();
            switch (statut) {
                case "NON_TRAITEE" -> statutLabel.setForeground(new Color(0, 0, 255)); // Bleu
                case "PRETE" -> statutLabel.setForeground(new Color(0, 128, 0)); // Vert
                case "EN_PREPARATION" -> statutLabel.setForeground(new Color(255, 165, 0)); // Orange
                case "ANNULE" -> statutLabel.setForeground(new Color(255, 0, 102)); // rouge
            }
            row.add(statutLabel);
            row.add(new JLabel(commande[4].toString(), JLabel.CENTER));
            commandesPanel.add(row);
            commandesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton AjouterProduit = new CustomButton("Ajouter Produit", "valider");
        AjouterProduit.addActionListener(e -> {
            MenuView menuView = new MenuView(database);
            menuView.setVisible(true);
            dispose();
        });
        buttonPanel.add(AjouterProduit);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

    }
}