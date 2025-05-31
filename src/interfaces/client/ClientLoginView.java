package interfaces.client;
import com.mongodb.client.MongoDatabase;
import interfaces.Gerant.GerantCommandeView;
import interfaces.Gerant.GerantDashboardView;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import org.bson.types.ObjectId;
import services.ClientService;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.prefs.Preferences;

public class ClientLoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton changeMpButton;
    private ClientService clientService;
    private static MongoDatabase database;

    public ClientLoginView(MongoDatabase database){

        ClientLoginView.database = database ;
        this.clientService = new ClientService(database);
        setTitle("Connexion Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Créer l'interface
        createUI();
    }
    private void createUI() {
        setLayout(new BorderLayout(10, 10));
        add(new HeaderPanel("Connexion Client"), BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField = new JPasswordField(20);

// Ajouter à la grille
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

// Ajouter la grille au panel principal
        mainPanel.add(formPanel);

// Espacement après le formulaire
        mainPanel.add(Box.createVerticalStrut(20));

       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       loginButton = new CustomButton("Se connecter", "valider");
       registerButton = new CustomButton("S'inscrire", "ajouter");
       changeMpButton = new CustomButton("Changer mot de passe","modifier");
        loginButton.addActionListener(e -> {
        if (emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
        } else if(clientService.signin(emailField.getText().trim(),passwordField.getText())){
            String role = clientService.roleUser(emailField.getText().trim()) ;
            String userID = clientService.idUser(emailField.getText().trim());
            Preferences prefs = Preferences.userRoot().node("Ids"); // this right here is for local storage using prefernces api in java ( 9rina local storage f frontend)
            prefs.put("userID", userID.toString());
            if (Objects.equals(role, "Gerant")){ // no it's not chat here it was ==  but then i get this recom form the IDE to handle null results so i tab tab tab
                GerantDashboardView gerantDashboardView = new GerantDashboardView(database);
                gerantDashboardView.setVisible(true);
                dispose();
            }else{
               // JOptionPane.showMessageDialog(this, "Connexion réussie (simulation)");
            CommandeView commandeView = new CommandeView(database);
            commandeView.setVisible(true);
            dispose();
            }
        }else {
            JOptionPane.showMessageDialog(this, "Mot de passe incorrect ou utilisateur non valide");
        }
    });
        registerButton.addActionListener(e -> ouvrirInscription());
        changeMpButton.addActionListener(e -> changeMpView());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(changeMpButton);
        mainPanel.add(buttonPanel);

    // Ajouter le panneau principal
    add(mainPanel, BorderLayout.CENTER);
}
    private void ouvrirInscription() {
        RegisterView registerView = new RegisterView(database);
        registerView.setVisible(true);
        this.dispose();
    }
    private void changeMpView() {
        ModifPassView mpView = new ModifPassView(database);
        mpView.setVisible(true);

        this.dispose();
    }


}
