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

	public void modifierInfo(Client c ,String name, String LastName, Date date_de_naissance, String Email , String adresse, String password ) {
		MongoCollection<Document> collection = database.getCollection("clients");

		Bson filter = eq("_id", c.getId()); //

		if (name != null) collection.updateOne(filter, Updates.set("name", name));
		if (LastName != null) collection.updateOne(filter, Updates.set("lastName", LastName));
		if (date_de_naissance != null) collection.updateOne(filter, Updates.set("dateNaissance", date_de_naissance));
		if (Email != null) collection.updateOne(filter, Updates.set("email", Email));
		if (adresse != null) collection.updateOne(filter, Updates.set("adresse", adresse));
		if (password != null) collection.updateOne(filter, Updates.set("password", password));
		System.out.println("Client modifié dans MongoDB !");
	}

	public String[] getTableMenu() {
		MongoCollection<Document> collection = database.getCollection("produits");
		List<Document> documents = collection.find().into(new ArrayList<>());

		String[] data = new String[documents.size()];

		for (int i = 0; i < documents.size(); i++) {
			Document doc = documents.get(i);
			String name = doc.getString("name");
			Double prix = doc.getDouble("prix");
			data[i] = name + " - " + prix + " €";
		}
		return data;
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

	public void modifierCommande (Commande c , Commande.EtatCommande etat_commande , Commande.TypeCommande type_commande  , List<String> produits) {
	/*	MongoCollection<Document> collection = database.getCollection("commandes");
		Bson filter = eq("_id", c.getId());

		if (etat_commande != null) collection.updateOne(filter, Updates.set("EtatCommande", etat_commande));
		if (type_commande != null) collection.updateOne(filter, Updates.set("TypeCommande", type_commande));
		if (produits != null) collection.updateOne(filter, Updates.set("produits", produits));
*/
	}

	public void supprimerCommande (String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		collection.deleteOne(eq("_id", new ObjectId(id)));
		System.out.println("commande supprimée dans MongoDB !");

	}

	public Commande.EtatCommande SuivieCommande(String id) {
		MongoCollection<Document> collection = database.getCollection("commandes");
		Bson filter = eq("_id", new ObjectId(id));

		Document doc = collection.find(filter).first();

		if (doc != null) {
			String etatStr = doc.getString("EtatCommande");
			if (etatStr != null) {
				return Commande.EtatCommande.valueOf(etatStr);
			}
		}

		return null;
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
