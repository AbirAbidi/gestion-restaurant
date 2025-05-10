
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import models.Commande;
import org.bson.Document;
import services.ClientService;
import services.GerantService;

import java.util.ArrayList;
import java.util.Date;

public class MongoClientConnectionExample {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://contactabirabidi:NSNEBlyWlXZwnzvz@cluster0.zwvdw2m.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("gestion_restaurant");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                ClientService clientservice = new ClientService(database);
                GerantService gerantservice = new GerantService(database);
                // test zone


                /*Client c = new Client("123", "Ali", "Ben Ali", new Date(), "ali@email.com", "Tunis", "azerty");
                //service.creerClient(c);
                service.modifierInfo(c,"abir");
                Produit p = new Produit("123", "dro3", "what khairi eats", 20, "plat");
                gerantservice.AjoutProduit(p);
                 clientservice.consulterMenu() ;
                 clientservice.changerMp("6817976d4e41c60a88ecf7ad","new");
                  gerantservice.consulterLclients();
                  gerantservice.supprimerClient("681b921f3393df10ec36dd87");
                ArrayList<String> pr = new ArrayList<>();
                pr.add("patte");
                pr.add("salade");
                Commande cm = new Commande("6817976d4e41c60a88ecf7ad", Commande.EtatCommande.NON_TRAITEE,Commande.TypeCommande.SUR_PLACE ,pr);
                clientservice.passerCommande(cm);

                gerantservice.afficherCommandeClient();
                                Commande.EtatCommande etat = clientservice.SuivieCommande("681ba53f96cef7529a481860");
                System.out.println("État de la commande : " + etat);
                */



            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}

