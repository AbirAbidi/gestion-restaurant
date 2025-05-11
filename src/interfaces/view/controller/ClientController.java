package interfaces.controller;
// Imports des classes de modèle
import models.Client;
import models.Commande;
// Imports pour les services
import services.ClientService;
// Imports pour les collections Java
import java.util.Date;
import java.util.List;

// Imports pour MongoDB (si nécessaire pour certaines méthodes)
import org.bson.types.ObjectId;
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean authentifierClient(String email, String password) {
        // Appeler directement le service d'authentification
        return clientService.authentifierClient(email, password);
    }

    public boolean inscrireClient(String nom, String prenom, Date dateNaissance,
                                  String email, String adresse, String password) {
        try {
            if (nom == null || prenom == null || email == null || password == null) {
                System.out.println("Données manquantes pour l'inscription");
                return false;
            }
            Client nouveauClient = new Client(prenom, nom, dateNaissance, email, adresse, password);
            clientService.creerClient(nouveauClient);
            System.out.println("Client inscrit avec succès: " + email);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'inscription: " + e.getMessage());
            return false;
        }
    }

    public List<String> consulterMenu() {
        // Appeler le service et retourner directement le résultat
        return clientService.consulterMenu();
    }

    public boolean passerCommande(String idClient, List<String> produits, String typeCommande) {
        try {
            // Convertir le type de commande
            Commande.TypeCommande type = Commande.TypeCommande.valueOf(typeCommande.toUpperCase());

            // Créer la commande
            Commande commande = new Commande(idClient, Commande.EtatCommande.NON_TRAITEE, type, produits);

            // Enregistrer la commande
            clientService.passerCommande(commande);
            return true;

        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            return false;
        }
    }

    public Commande.EtatCommande suivreCommande(String idCommande) {
        return clientService.SuivieCommande(idCommande);
    }
}
