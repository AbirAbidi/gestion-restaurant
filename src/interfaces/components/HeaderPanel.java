package interfaces.components;
import javax.swing.*;
import java.awt.*;
public class HeaderPanel extends JPanel {

    // i changed colors thats all
    public HeaderPanel(String titre) {
        setBackground(new Color(173, 216, 230));
        setPreferredSize(new Dimension(600, 100));
        setLayout(new GridBagLayout());
        JLabel titreLabel = new JLabel(titre);
        titreLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titreLabel.setForeground(new Color(25, 25, 112));
        add(titreLabel);
    }
}
