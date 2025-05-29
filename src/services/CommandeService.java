package services;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import models.Commande;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class CommandeService {

	private final MongoDatabase database;
	public CommandeService(MongoDatabase database) {
		this.database = database;
	}
	public void modifierEtatCommande(String id, String nouvelEtat) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		Bson filter = Filters.eq("_id", new ObjectId(id));
		if (nouvelEtat != null) collection.updateOne(filter, Updates.set("EtatCommande", nouvelEtat));
		System.out.println("Commande modifiée");
	}

}
