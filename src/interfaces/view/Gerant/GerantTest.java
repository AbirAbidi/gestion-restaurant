package interfaces.view.Gerant;

import javax.swing.*;

/**
 * Classe de test pour les interfaces gérant
 */
public class GerantTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Test de l'interface de connexion du gérant
                //testGerantLoginView();
                //testGerantDashboardView();
                //testGerantProduitView();
                testGerantCommandeView();
            }
        });
    }
    private static void testGerantLoginView() {
        try {
            System.out.println("Test de l'interface de connexion gérant");
            GerantLoginView loginView = new GerantLoginView();
            loginView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de GerantLoginView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testGerantDashboardView() {
        try {
            System.out.println("Test de l'interface du tableau de bord gérant");
            GerantDashboardView dashboardView = new GerantDashboardView();
            dashboardView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de GerantDashboardView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testGerantProduitView() {
        try {
            System.out.println("Test de l'interface de gestion des produits");
            GerantProduitView produitView = new GerantProduitView();
            produitView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de GerantProduitView : " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void testGerantCommandeView() {
        try {
            System.out.println("Test de l'interface de gestion des commandes");
            GerantCommandeView commandeView = new GerantCommandeView();
            commandeView.setVisible(true);
        } catch (Exception e) {
            System.err.println("Erreur lors du test de GerantCommandeView : " + e.getMessage());
            e.printStackTrace();
        }
    }
}