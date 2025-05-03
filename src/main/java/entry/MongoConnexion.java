package main.java.entry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnexion {
    public static void main(String[] args) {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

            MongoDatabase database = mongoClient.getDatabase("restoDB");

            System.out.println(" Connexion à MongoDB réussie !");

            mongoClient.close();
        } catch (Exception e) {
            System.out.println(" Erreur de connexion : " + e.getMessage());
        }
    }
}
