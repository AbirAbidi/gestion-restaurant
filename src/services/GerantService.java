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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
		collection.deleteOne(eq("_id", new ObjectId(id)));
		System.out.println("Client supprimé !");
	}

	public void supprimerPrdouit(String id) {
		MongoCollection<Document> collection = database.getCollection("produits");
		collection.deleteOne(eq("_id", new ObjectId(id)));
		System.out.println("produit supprimé !");
	}
	
	public void modifierProduit( Produit p ,String name ,String description ,double prix ,String type  ) {
		MongoCollection<Document> collection = database.getCollection("produits");

		Bson filter = eq("_id", p.getId());

		if (name != null) collection.updateOne(filter, Updates.set("name", name));
		if (description != null) collection.updateOne(filter, Updates.set("description", description));
		if (prix >0) collection.updateOne(filter, Updates.set("prix", prix) );
		if (type != null) collection.updateOne(filter, Updates.set("type", type));
		System.out.println("Produit modifié dans MongoDB !");

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
				System.out.println("Processing document #" + docCount + ": " + doc.toJson());

				try {
					ObjectId objectId = doc.getObjectId("_id");
					String id = objectId != null ? objectId.toString() : "Unknown ID";

					String client = doc.getString("client");
					if (client == null) client = "Unknown Client";

					String typeCommande = doc.getString("TypeCommande");
					if (typeCommande == null) typeCommande = "Unknown Type";

					String etatCommande = doc.getString("EtatCommande");
					if (etatCommande == null) etatCommande = "Unknown Status";

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
					System.out.println(produitsStr);
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
