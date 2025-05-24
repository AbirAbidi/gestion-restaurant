package interfaces.client;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.ClientService;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class ModifPassView extends JFrame {
    private JTextField emailField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private final ClientService clientService ;
    private static MongoDatabase database;


    public ModifPassView(MongoDatabase database) {
        ModifPassView.database = database ;
        this.clientService = new ClientService(database);
        setTitle("Changer mot de passe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        HeaderPanel headerPanel = new HeaderPanel("Changer votre mot de passe");
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Adresse email:"));
        emailField = new JTextField();
        formPanel.add(emailField);


        formPanel.add(new JLabel("Ancien mot de passe:"));
        oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordField);

        formPanel.add(new JLabel("Nouveau mot de passe:"));
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField);

        formPanel.add(new JLabel("Confirmer mot de passe:"));
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton validerButton = new CustomButton("Valider", "valider");
        JButton annulerButton = new CustomButton("Annuler", "annuler");



        validerButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String oldPassword = oldPasswordField.getText().trim();
            String newPass = newPasswordField.getText().trim();
            String confirmPass = confirmPasswordField.getText().trim();
            String IdUser = clientService.idUser(email);
            if (email.isEmpty() ||
                    oldPassword.isEmpty() ||
                    newPass.isEmpty() ||
                    confirmPass.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (IdUser.equals("null")) {
                JOptionPane.showMessageDialog(this,
                        "Ce email n'exite pas",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }



            if(newPass.equals(oldPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Il faut un nouveau mot de passe différent de l'ancien",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this,
                        "Le nouveau mot de passe ne correspond pas à la confirmation",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }



                boolean state = clientService.changerMp(IdUser,email ,oldPassword, newPass);
            if (!state) {
                JOptionPane.showMessageDialog(this,
                        "mot de passe incorrect",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
                JOptionPane.showMessageDialog(this,
                        "Mot de passe modifié avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

                retourLogin();




        });

        annulerButton.addActionListener(e -> retourLogin());

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        setContentPane(mainPanel);
    }
    private void retourLogin() {
        ClientLoginView clientLoginView = new ClientLoginView(database);
        clientLoginView.setVisible(true);
        dispose();
    }
}