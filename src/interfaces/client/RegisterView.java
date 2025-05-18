package interfaces.client;
import com.mongodb.client.MongoDatabase;
import interfaces.Gerant.GerantCommandeView;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import models.Client;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.ClientService;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Properties;


public class RegisterView extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField adresseField;
    private JDatePickerImpl datePicker;
    private JButton validerButton;
    private JButton annulerButton;
    private ClientService clientService;
    private static MongoDatabase database;
    public RegisterView(MongoDatabase database){

        RegisterView.database = database ;
        this.clientService = new ClientService(database);
        setTitle("Inscription Client");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }
    private void createUI() {
        setLayout(new BorderLayout());
        add(new HeaderPanel("Inscription Nouveau Client"), BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        adresseField = new JTextField(20);
        //dateNaissanceField = new JTextField(20);
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Adresse:"));
        formPanel.add(adresseField);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        formPanel.add(new JLabel("Date de naissance (jj/mm/aaaa):"));
        formPanel.add(dateSpinner);

        JPanel buttonPanel = new JPanel();

        validerButton = new CustomButton("Valider", "valider");
        annulerButton = new CustomButton("Annuler", "annuler");


        validerButton.addActionListener(e -> {
            if (    nomField.getText().isEmpty() ||
                    prenomField.getText().isEmpty() ||
                    emailField.getText().isEmpty() ||
                    passwordField.getPassword().length == 0 ||
                    adresseField.getText().isEmpty() ||
                    dateSpinner.getValue() == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez remplir tous les champs du formulaire",
                        "Champs manquants",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            if (!emailField.getText().contains("@")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer une adresse email valide",
                        "Format email incorrect",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Date dateNaissance = (Date) dateSpinner.getValue();

            Client client1 = new Client(nomField.getText(), prenomField.getText(),dateNaissance,emailField.getText(),adresseField.getText(),prenomField.getText());
            clientService.creerClient(client1);
            // Retourner à l'écran de connexion
            ClientLoginView loginView = new ClientLoginView(database);
            loginView.setVisible(true);
            dispose();
            //TODO : u have to destroy the registerView when u sign in , here still it appears ( m leaving it for now for testing purposes but dont foget to remove it later )
        });

        annulerButton.addActionListener(e -> retourLogin());

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);


        formPanel.add(new JLabel(""));
        formPanel.add(buttonPanel);


        add(formPanel, BorderLayout.CENTER);
    }
    private void retourLogin() {
        // Créer et afficher la vue de connexion
        ClientLoginView loginView = new ClientLoginView(database);
        loginView.setVisible(true);

        // Fermer la fenêtre d'inscription
        this.dispose();
    }


}
