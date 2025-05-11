package interfaces.view.client;
import interfaces.view.components.HeaderPanel;
import interfaces.view.components.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientLoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;


    public ClientLoginView() {
        // Configuration de base
        setTitle("Connexion Client");
        setSize(400, 300);
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
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField(15);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);


       mainPanel.add(Box.createVerticalStrut(10));
       JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
       JLabel passwordLabel = new JLabel("Mot de passe: ");
       passwordField = new JPasswordField(15);
       passwordPanel.add(passwordLabel);
       passwordPanel.add(passwordField);


       mainPanel.add(passwordPanel);
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       loginButton = new CustomButton("Se connecter", "valider");
       registerButton = new CustomButton("S'inscrire", "ajouter");
    // Ajouter les actions
        loginButton.addActionListener(e -> {
        // Vérification simple
        if (emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
        } else {
            JOptionPane.showMessageDialog(this, "Connexion réussie (simulation)");
        }
    });
        registerButton.addActionListener(e -> ouvrirInscription());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel);

    // Ajouter le panneau principal
    add(mainPanel, BorderLayout.CENTER);
}
    private void ouvrirInscription() {
        // Créer et afficher la vue d'inscription
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);

        // Fermer la fenêtre de connexion
        this.dispose();
    }


}
