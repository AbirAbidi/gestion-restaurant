package services;

import com.mongodb.client.model.Filters;
import models.Client;
import models.Commande;
import models.Produit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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
	
	public void modifierProduit(Produit p) {}
	
	public List<Commande> afficherCommandeClient(Client c){
		return null ;
	}
	
	public void suppprimerCommande(Commande c) {}

	
}
