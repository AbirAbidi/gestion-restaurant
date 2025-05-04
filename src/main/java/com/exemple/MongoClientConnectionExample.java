package com.exemple;

import com.exemple.models.Client;
import com.exemple.services.ClientService;
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

                Client c = new Client("123", "Ali", "Ben Ali", new Date(), "ali@email.com", "Tunis", "azerty");
                ClientService service = new ClientService(database);
                service.creerClient(c);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}

