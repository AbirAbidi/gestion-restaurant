package services;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import models.Client;
import models.Commande;
import models.Produit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;




public class GerantService {


	private final MongoDatabase database;
	public GerantService(MongoDatabase database) {
		this.database = database;
	}



	
	public void AjoutProduit(Produit p) {
		MongoCollection<Document> collection = database.getCollection("produits");

		Document doc = new Document()
				.append("name", p.getName())
				.append("description", p.getDescription())
				.append("prix", p.getPrix())
				.append("type", p.getType());

		collection.insertOne(doc);
		System.out.println("Produit ajouté dans MongoDB !");
	}

	public void consulterLclients () {
		MongoCollection<Document> collection = database.getCollection("clients");
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while
			(cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		}finally {
			cursor.close();
		}
	}

	public void supprimerClient(String id) {
		MongoCollection<Document> collection = database.getCollection("clients");
		collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
		System.out.println("Client supprimé !");
	}

	public void supprimerPrdouit(String id) {
		MongoCollection<Document> collection = database.getCollection("produits");
		collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
		System.out.println("produit supprimé !");
	}
	
	public void modifierProduit( Produit p ,String name ,String description ,double prix ,String type  ) {
		MongoCollection<Document> collection = database.getCollection("produits");

		Bson filter = Filters.eq("_id", p.getId());

		if (name != null) collection.updateOne(filter, Updates.set("name", name));
		if (description != null) collection.updateOne(filter, Updates.set("description", description));
		if (prix >0) collection.updateOne(filter, Updates.set("prix", prix) );
		if (type != null) collection.updateOne(filter, Updates.set("type", type));
		System.out.println("Produit modifié dans MongoDB !");

	}
	
	public void afficherCommandeClient(){
		MongoCollection<Document> collection = database.getCollection("commandes");

		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while
			(cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		}finally {
			cursor.close();
		}
	}
	
	public void suppprimerCommande(String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		collection.deleteOne(Filters.eq("_id", id));
		System.out.println("Commande supprimée dans MongoDB !");
	}

	
}
