package com.exemple;

import com.exemple.models.Client;
import com.exemple.models.Produit;
import com.exemple.services.ClientService;
import com.exemple.services.GerantService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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
                */


            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}

