package com.exemple.services;


import com.exemple.models.Client;
import com.exemple.models.Menu;
import com.exemple.models.Commande;
import com.exemple.models.Produit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class ClientService {

	private final MongoDatabase database;
	public ClientService(MongoDatabase database) {
		this.database = database;
	}


	public void creerClient(Client c) {
		MongoCollection<Document> collection = database.getCollection("clients");

		Document doc = new Document("id", c.getId())
				.append("name", c.getName())
				.append("lastName", c.getLastName())
				.append("dateNaissance", c.getDate_de_naissance())
				.append("email", c.getEmail())
				.append("adresse", c.getAdresse())
				.append("password", c.getPassword());

		collection.insertOne(doc);
		System.out.println("Client ajouté dans MongoDB !");
	}
	
	public void modifierInfo(Client c) {}
	
	public Menu consulterMenu() {
		return null ;

	}
	
	public void changerMp (Client c , String nouvelleMp) {
		c.setPassword(nouvelleMp);
	}
	
	public void passerCommande(Client c , Commande cm) {
		
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
