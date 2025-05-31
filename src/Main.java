import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import interfaces.Gerant.GerantCommandeView;
import interfaces.Gerant.GerantDashboardView;
import interfaces.client.ClientLoginView;
import interfaces.client.CommandeView;
import interfaces.client.MenuView;
import org.bson.Document;
import services.GerantService;

import javax.swing.*;
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


                   ClientLoginView clientLoginView = new ClientLoginView(database);
                    clientLoginView.setVisible(true);


                });


            } catch (MongoException e) {
                e.printStackTrace();
            }



    }
		

}
