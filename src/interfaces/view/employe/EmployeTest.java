package interfaces.view.employe;

import javax.swing.*;

/**
 * Classe de test pour les interfaces employé
 */
public class EmployeTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Test de l'interface de connexion de l'employé
                testEmployeLoginView();
            }
        });
    }

    /**
     * Méthode pour tester l'interface de connexion de l'employé
     */
    private static void testEmployeLoginView() {
        try {
            System.out.println("Test de l'interface de connexion employé");
            EmployeLoginView loginView = new EmployeLoginView();
            loginView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de EmployeLoginView : " + e.getMessage());
            e.printStackTrace();
        }
    }
}