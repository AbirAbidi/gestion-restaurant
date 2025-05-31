package interfaces.client;

import com.mongodb.client.MongoDatabase;
import services.ClientService;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class ClientProfileView extends JFrame {
    private final ClientService clientService;
    private static MongoDatabase database;

    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField addressField;
    Preferences prefs = Preferences.userRoot().node("Ids");
    String storedId = prefs.get("userID", null);

    public ClientProfileView(MongoDatabase database) {
        ClientProfileView.database = database ;
        this.clientService = new ClientService(database);
        setTitle("Modifier mon profil");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();

    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Prenom :"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Nom :"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Adresse :"));
        addressField = new JTextField();
        panel.add(addressField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> saveClientInfo());

        panel.add(new JLabel());
        panel.add(saveButton);

        add(panel);
    }

    private void saveClientInfo() {
        String prenom = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String nom = lastNameField.getText().trim();
        if (prenom.isEmpty()) prenom = null;
        if (email.isEmpty()) email = null;
        if (address.isEmpty()) address = null;
        if (nom.isEmpty()) nom = null;
        boolean success = clientService.modifierInfo(storedId,prenom,nom,email,address);
        if (success) {
            JOptionPane.showMessageDialog(this, "Profil mis à jour avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
