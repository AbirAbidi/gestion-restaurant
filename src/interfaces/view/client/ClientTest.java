package interfaces.view.client;
import javax.swing.*;

/**
 * Classe de test pour les interfaces client
 */
public class ClientTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                // Test de l'interface de connexion
                //testLoginView();

                // Test de l'interface d'inscription
               // testRegisterView();
                //testMenuView();
               // testPanierView();
                testCommandeView();
            }
        });
    }

    /**
     * Méthode pour tester l'interface de connexion
     */
    private static void testLoginView() {
        try {
            System.out.println("Test de l'interface de connexion client");
            ClientLoginView loginView = new ClientLoginView();
            loginView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de LoginView : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour tester l'interface d'inscription
     */
    private static void testRegisterView() {
        try {
            System.out.println("Test de l'interface d'inscription client");
            RegisterView registerView = new RegisterView();
            registerView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de RegisterView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testMenuView() {
        try {
            System.out.println("Test de l'interface du menu client");
            MenuView menuView = new MenuView();
            menuView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de MenuView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testPanierView() {
        try {
            System.out.println("Test de l'interface du panier client");
            PanierView panierView = new PanierView();
            panierView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de PanierView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testCommandeView() {
        try {
            System.out.println("Test de l'interface des commandes client");
            CommandeView commandeView = new CommandeView();
            commandeView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de CommandeView : " + e.getMessage());
            e.printStackTrace();
        }
    }
}