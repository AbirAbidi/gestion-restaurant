package interfaces.components;
import javax.swing.JButton;
import java.awt.Color;

        public class CustomButton extends JButton {

            public CustomButton(String text, String type) {
                super(text);

                if (type.equals("ajouter")) {
                    setBackground(Color.GREEN);
                }
                else if (type.equals("modifier")) {
                    setBackground(Color.BLUE);
                }
                else if (type.equals("supprimer")) {
                    setBackground(Color.RED);
                }
                else if (type.equals("valider")) {
                    setBackground(new Color(46, 134, 193));  // Bleu foncé
                }
                else if (type.equals("annuler")) {
                    setBackground(new Color(149, 165, 166)); // Gris
                }
                else {
                setForeground(Color.WHITE);
            }
    }

}
