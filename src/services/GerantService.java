package services;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import models.Client;
import models.Commande;
import models.Produit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


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

	public boolean supprimerClient(String email) {
		MongoCollection<Document> collection = database.getCollection("clients");
		//DeleteResult The result of a delete operation (sth new to learn)
		DeleteResult result = collection.deleteOne(eq("email", email));

		if (result.getDeletedCount() > 0) {
			System.out.println("Client supprimé !");
			return true;
		} else {
			System.out.println("Aucun client trouvé avec cet email.");
			return false;
		}
	}


	public boolean supprimerPrdouit(String id) {
		MongoCollection<Document> collection = database.getCollection("produits");
		DeleteResult result  = collection.deleteOne(eq("_id", new ObjectId(id)));
		if (result.getDeletedCount() > 0) {
			System.out.println("Produit supprimé !");
			return true;
		} else {
			System.out.println("Probleme de supprim!.");
			return false;
		}
	}

	public void modifierProduit( String id ,String name ,String description ,double prix ,String type  ) {
		MongoCollection<Document> collection = database.getCollection("produits");

		Bson filter = eq("_id", new ObjectId(id));

		if (name != null) collection.updateOne(filter, Updates.set("name", name));
		if (description != null) collection.updateOne(filter, Updates.set("description", description));
		if (prix >0) collection.updateOne(filter, Updates.set("prix", prix) );
		if (type != null) collection.updateOne(filter, Updates.set("type", type));
		System.out.println("Produit modifié dans MongoDB !");

	}

	public List<Document> consulterLclients () {
		MongoCollection<Document> collection = database.getCollection("clients");
		MongoCursor<Document> cursor = collection.find().iterator();
		List<Document> clients = new ArrayList<>();

		try {
			while
			(cursor.hasNext()) {
				clients.add(cursor.next());
			}
		}finally {
			cursor.close();
		}

		return clients;
	}

	public JTable afficherCommandeClient() {
		MongoCollection<Document> collection = database.getCollection("commandes");
		String[] columnNames = {"ID", "Client", "TypeCommande", "EtatCommande", "Produits"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		try {
			// Use find() to retrieve all documents
			MongoCursor<Document> cursor = collection.find().iterator();
			int docCount = 0;

			while (cursor.hasNext()) {
				Document doc = cursor.next();
				docCount++;
				//System.out.println("Processing document #" + docCount + ": " + doc.toJson());

				try {
					ObjectId objectId = doc.getObjectId("_id");
					String id = objectId != null ? objectId.toString() : "Unknown ID";

					String client = doc.getString("client");
					String typeCommande = doc.getString("TypeCommande");
					String etatCommande = doc.getString("EtatCommande");

					String produitsStr = "";
					try {
						List<String> produits = (List<String>) doc.get("produits");
						if (produits != null && !produits.isEmpty()) {
							produitsStr = String.join(", ", produits);
						}
					} catch (ClassCastException e) {
						System.err.println("Error casting Produits to List<String>: " + e.getMessage());
						Object produitsObj = doc.get("produits");
					}

					// Add row to table model
					model.addRow(new Object[]{id, client, typeCommande, etatCommande, produitsStr});
					//System.out.println(produitsStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JTable table = new JTable(model);
		return table;
	}

	public void suppprimerCommande(String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		collection.deleteOne(eq("_id", id));
		System.out.println("Commande supprimée dans MongoDB !");
	}

	public String getClientName(String id) {
		MongoCollection<Document> collection = database.getCollection("clients");
		Document doc = collection.find(eq("_id", new ObjectId(id))).first();
		return doc.getString("name");
	}

}
