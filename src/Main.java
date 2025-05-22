import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.event.CommandEvent;
import interfaces.Gerant.GerantCommandeView;
import interfaces.client.ClientLoginView;
import interfaces.client.CommandeView;
import interfaces.client.MenuView;
import interfaces.client.RegisterView;
import org.bson.Document;
import services.ClientService;
import services.CommandeService;
import services.GerantService;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties prop = ConfigLoader.loadConfig() ;
        if(prop == null){
            System.err.println("No config found");
        }


        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(prop.getProperty("MONGO_URI")))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try {
                MongoClient mongoClient = MongoClients.create(settings);

                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase(prop.getProperty("DATABASE_NAME"));
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                SwingUtilities.invokeLater(() -> {
                    /*GerantCommandeView gerantCommandeView = new GerantCommandeView(database);
                    gerantCommandeView.setVisible(true);
                    RegisterView registerView = new RegisterView(database);
                    registerView.setVisible(true);

                    MenuView menuView = new MenuView(database);
                    menuView.setVisible(true);


                    */
                    ClientLoginView clientLoginView = new ClientLoginView(database);
                    clientLoginView.setVisible(true);
                });


            } catch (MongoException e) {
                e.printStackTrace();
            }



    }
		

}
