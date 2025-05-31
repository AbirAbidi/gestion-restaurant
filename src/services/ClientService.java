package services;


import com.mongodb.client.result.DeleteResult;
import models.User;
import models.Commande;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import models.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class ClientService {

	private final MongoDatabase database;
	public ClientService(MongoDatabase database) {
		this.database = database;
	}

//TODO CHECK BEFORE ADDING IF USER ALREADY EXIST
	public void creerClient(User c) {
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

	public boolean modifierInfo(String id, String name, String lastName, String email, String adresse) {
		try {
			MongoCollection<Document> collection = database.getCollection("clients");
			Bson filter = eq("_id", new ObjectId(id));

			if (name != null ) collection.updateOne(filter, Updates.set("name", name));
			if (lastName != null) collection.updateOne(filter, Updates.set("lastName", lastName));
			if (email != null) collection.updateOne(filter, Updates.set("email", email));
			if (adresse != null) collection.updateOne(filter, Updates.set("adresse", adresse));

			System.out.println("Client modifié dans MongoDB !");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public Object[][] getTableMenu() {
		MongoCollection<Document> collection = database.getCollection("produits");
		List<Document> documents = collection.find().into(new ArrayList<>());

		Object[][] data = new Object[documents.size()][3];

		for (int i = 0; i < documents.size(); i++) {
			Document doc = documents.get(i);
			data[i][0] = doc.getString("name");
			data[i][1] = doc.getDouble("prix");
			data[i][2] = doc.getObjectId("_id").toString();
		}
		return data;
	}

	public double getProduitPrice(String name) {
		MongoCollection<Document> collection = database.getCollection("produits");
		Bson filter = eq("name", name);
		Document doc = collection.find(filter).first();

		if (doc != null && doc.containsKey("prix")) {
			try {
				return doc.getDouble("prix");
			} catch (ClassCastException e) {
				// Si le prix est mal formaté (ex: entier au lieu de double)
				try {
					return doc.get("prix") instanceof Number ? ((Number) doc.get("prix")).doubleValue() : 0;
				} catch (Exception ex) {
					return 0;
				}
			}
		}
		return 0; // si le document est null ou ne contient pas "prix"
	}


	public boolean changerMp (String id,String email ,String oldPass ,String nouvelleMp) {
		MongoCollection<Document> collection = database.getCollection("clients");
		//filters used to filter and Updates to find the updating field ( they re queries like)
		Document filter = collection.find(eq("_id",new ObjectId(id))).first();
        String emailIndb = filter.getString("email");
		String passwordIndb = filter.getString("password");
		if (email.equals(emailIndb)&&passwordIndb.equals(oldPass)) {
			collection.updateOne(eq(("_id"), new ObjectId(id)), Updates.set("password", nouvelleMp));
			System.out.println("Client modifié son mp dans MongoDB !");
			return true;
		}
		return false;

	}
	
	public void passerCommande(Commande cm) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		Document doc = new Document()
				.append("client", cm.getId_client())
				.append("EtatCommande",cm.getEtat_commande())
				.append("TypeCommande",cm.getType_commande())
				.append("produits",cm.getProduits());

		collection.insertOne(doc);
		System.out.println("Client passe commande dans MongoDB !");

	}

	public void modifierCommande (String id, Commande.TypeCommande type_commande   ) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		Bson filter = eq("_id", new ObjectId(id));

		if (type_commande != null) collection.updateOne(filter, Updates.set("TypeCommande", type_commande));

	}

	public boolean supprimerCommande (String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		DeleteResult result = collection.deleteOne(eq("_id", new ObjectId(id)));
		if (result.getDeletedCount() > 0) {
			System.out.println("Commande supprimé !");
			return true;
		} else {
			System.out.println("Proble suppression commande.");
			return false;
		}

	}

	public boolean signin(String email, String password) {
		MongoCollection<Document> collection = database.getCollection("clients");
		Document client = collection.find(and(eq("email", email), eq("password", password))).first();

		if (client != null) {
			return true;
		} else {
			return false;
		}
	}

	public String roleUser(String email) {
		MongoCollection<Document> collection = database.getCollection("clients");
		Document doc = collection.find(eq("email", email)).first();
		return doc.getString("role");
	}

	public String idUser(String email) {
		MongoCollection<Document> collection = database.getCollection("clients");
		Document doc = collection.find(eq("email", email)).first();
		if(doc == null) {
			return "null";
		}
		return doc.getObjectId("_id").toString();
	}

	/*public JTable getTableCommande(String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		String[] columnNames = {"statut", "type","list_produits"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		try {
			MongoCursor<Document> cursor = collection.find().iterator();
			int docCount = 0;
			while (cursor.hasNext() && id.equals(cursor.next().getString("client"))) {
				Document doc = cursor.next();
				docCount++;
				System.out.println("Processing document #" + docCount + ": " + doc.toJson());

				try {
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
*/


}
