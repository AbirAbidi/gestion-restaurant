package interfaces.Gerant;

import com.mongodb.client.MongoDatabase;
import interfaces.components.HeaderPanel;
import interfaces.components.CustomButton;
import services.GerantService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GerantModifierProduit extends JFrame {

    private final JTextField nameField;
    private final JTextArea descriptionArea;
    private final JTextField prixField;
    private final JComboBox<String> typeCombo;
    private GerantService gerantService;
    private final String[] types = {"", "Plat", "Sandwich", "Salade", "Dessert", "Boisson"};

    public GerantModifierProduit(MongoDatabase database ,String id) {
        this.gerantService = new GerantService(database);

        setTitle("Modifier Produit");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        HeaderPanel header = new HeaderPanel("Modifier un Produit");
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        nameField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        prixField = new JTextField();
        typeCombo = new JComboBox<>(types);

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(5));

        formPanel.add(new JLabel("Description :"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(new JLabel("Prix :"));
        formPanel.add(prixField);
        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(new JLabel("Type :"));
        formPanel.add(typeCombo);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton modifierBtn = new CustomButton("Modifier", "modifier");
        JButton annulerBtn = new CustomButton("Annuler", "annuler");

        modifierBtn.addActionListener((ActionEvent e) -> {
            String newName = nameField.getText().trim();
            String newDesc = descriptionArea.getText().trim();
            String prixText = prixField.getText().trim();
            String newType = (String) typeCombo.getSelectedItem();

            String nameToUpdate = newName.isEmpty() ? null : newName;
            String descToUpdate = newDesc.isEmpty() ? null : newDesc;
            double prixToUpdate = -1;

            if (!prixText.isEmpty()) {
                try {
                    prixToUpdate = Double.parseDouble(prixText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Prix invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String typeToUpdate = (newType == null || newType.isEmpty()) ? null : newType;

            if (id != null) {
                gerantService.modifierProduit(id, nameToUpdate, descToUpdate, prixToUpdate, typeToUpdate);
                JOptionPane.showMessageDialog(this, "Produit modifié !");
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
                GerantProduitView gerantProduitView = new GerantProduitView(database);
                gerantProduitView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Produit non défini !");
            }
        });

        annulerBtn.addActionListener(e -> dispose());

        buttonPanel.add(modifierBtn);
        buttonPanel.add(annulerBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }


}
