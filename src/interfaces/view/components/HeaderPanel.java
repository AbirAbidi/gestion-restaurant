package interfaces.view.components;
import javax.swing.*;
import java.awt.*;
public class HeaderPanel extends JPanel {
    public HeaderPanel(String titre) {
        setBackground(new Color(200, 163, 164));
        setPreferredSize(new Dimension(600, 100));
        JLabel titreLabel = new JLabel(titre);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titreLabel.setForeground(new Color(96, 68, 34));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(titreLabel);
    }
}
