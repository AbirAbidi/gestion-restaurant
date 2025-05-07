package services;


import models.Client;
import models.Commande;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ClientService {

	private final MongoDatabase database;
	public ClientService(MongoDatabase database) {
		this.database = database;
	}


	public void creerClient(Client c) {
		MongoCollection<Document> collection = database.getCollection("clients");

		Document doc = new Document()
				.append("name", c.getName())
				.append("lastName", c.getLastName())
				.append("dateNaissance", c.getDate_de_naissance())
				.append("email", c.getEmail())
				.append("adresse", c.getAdresse())
				.append("password", c.getPassword());

		collection.insertOne(doc);
		System.out.println("Client ajouté dans MongoDB !");
	}

	//only changes the name till now
	public void modifierInfo(Client c , String name) {
		MongoCollection<Document> collection = database.getCollection("clients");

		collection.updateOne(Filters.eq(("name"), c.getName()), Updates.set("name", name));
		System.out.println("Client modifié dans MongoDB !");
	}
	
	public void consulterMenu() {
		MongoCollection<Document> collection = database.getCollection("produits");
		/* in this we can either use
		findIterate<Document> findIterable = collection.find(new Document());
		then put finditerate in a list and print it but its only adviced for small amount of data (which can be the case ) cuz we wont have a thousand item in the menu
				List<Document> results = new ArrayList<>() ;
		findIterable.into(results) ;
		System.out.println(results);

		and the .into() do the putting in an array job
		*/

		// the try finally to close the cursor (to free up the cursor's consumption od resources both in the app and the mongodb deployment)
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while
				(cursor.hasNext()) {
				System.out.println(cursor.next().getString("name"));
			}
		}finally {
			cursor.close();
		}
	}

	public void changerMp (String id,String nouvelleMp) {
		MongoCollection<Document> collection = database.getCollection("clients");
		//filters used to filter and Updates to find the updating field ( they re queries like)
		collection.updateOne(Filters.eq(("_id"), new ObjectId(id)), Updates.set("password", nouvelleMp));
		System.out.println("Client modifié son mp dans MongoDB !");
	}
	
	public void passerCommande(Commande cm) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		Document doc = new Document()
				.append("client", cm.getClient())
				.append("EtatCommande",cm.getEtat_commande())
				.append("TypeCommande",cm.getType_commande())
				.append("produits",cm.getProduits());

		collection.insertOne(doc);
		System.out.println("Client passe commande dans MongoDB !");

	}
	
	
	///idk m gonna link em 
	public String TypeCommande (Commande c) {
		return null ;
	} 
	
	public void modifierCommande (Commande c ) {}

	public void supprimerCommande (Commande c ) {}
	
	public String SuivieCommande (Commande c ) {
		return null ; // return etat commande 
	}


	
}
