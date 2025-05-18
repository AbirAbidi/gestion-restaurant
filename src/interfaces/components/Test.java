package interfaces.components;
import javax.swing.*;
import java.awt.*;
public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("restaurant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);  // Définir une taille
        frame.setLocationRelativeTo(null);  // Centrer sur l'écran
        HeaderPanel headerPanel = new HeaderPanel("Gestion Restaurant");
        frame.add(headerPanel, BorderLayout.NORTH);
        // Créer le panneau central pour les boutons
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        // Créer les boutons
        CustomButton btnAjouter = new CustomButton("Ajouter", "ajouter");
        CustomButton btnModifier = new CustomButton("Modifier", "modifier");
        CustomButton btnSupprimer = new CustomButton("Supprimer", "supprimer");

        // IMPORTANT : Ajouter les boutons au panneau
        panel.add(btnAjouter);
        panel.add(btnModifier);
        panel.add(btnSupprimer);

        // Ajouter le panneau au centre de la fenêtre
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

    }
}
