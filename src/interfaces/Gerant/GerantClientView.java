package interfaces.Gerant;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import org.bson.Document;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GerantClientView extends JFrame {

    private GerantService gerantService;
    private JPanel clientsPanel;
    private static MongoDatabase database;

    public GerantClientView(MongoDatabase database) {

        GerantClientView.database = database ;
        this.gerantService = new GerantService(database);
        setTitle("Gestion des Clients");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        HeaderPanel headerPanel = new HeaderPanel("Gestion des Clients");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Liste des clients
        clientsPanel = new JPanel();
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        clientsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        List<Document> clients = gerantService.consulterLclients();
        if (clients == null || clients.isEmpty()) {
            JLabel emptyLabel = new JLabel("Aucun client trouvé.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            clientsPanel.add(emptyLabel);
        } else {
            for (Document doc : clients) {
                String nom = doc.getString("name");
                String lastName = doc.getString("lastName");
                String email = doc.getString("email");

                // 🟢 Affiche le client avec bouton
                afficherClient(nom, lastName, email);
            }
        }
        JScrollPane scrollPane = new JScrollPane(clientsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton retourButton = new CustomButton("Retour au Dashboard", "annuler");
        retourButton.addActionListener(e -> {
            GerantDashboardView dashboardView = new GerantDashboardView(database);
            dashboardView.setVisible(true);
            dispose();
        });

        bottomPanel.add(retourButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Définir le contenu
        setContentPane(mainPanel);
    }


    private void afficherClient(String name,  String lastName,String email) {
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        clientPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Informations du client
        String info = name + " " + lastName + " - " +  email ;
        JLabel infoLabel = new JLabel(info);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        clientPanel.add(infoLabel, BorderLayout.CENTER);


        JPanel boutonsPanel = new JPanel();
        JButton supprimerButton = new CustomButton("Supprimer", "supprimer");
        supprimerButton.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(this,
                    "Supprimer le client " + name + ' '+ lastName + " ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (choix == JOptionPane.YES_OPTION) {
                if (gerantService.supprimerClient(email)) {
                    clientsPanel.remove(clientPanel);
                    clientsPanel.revalidate();
                    clientsPanel.repaint();
                    JOptionPane.showMessageDialog(this, "Client supprimé");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                }
            }
        });

        boutonsPanel.add(supprimerButton);
        clientPanel.add(boutonsPanel, BorderLayout.EAST);

        clientsPanel.add(clientPanel);
        clientsPanel.add(Box.createVerticalStrut(5));
    }
}