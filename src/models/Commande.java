package models;


public class Commande {

	
	private String id ; 
	private String client_id ;
	private String etat_commande ;
	private String nom_commande ; 
	private String type_commande ;  //Commande à livrer à domicile , Commande sur place,Commande à importer
	
	public Commande(  String id , String client_id , String etat_commande , String nom_commande , String type_commande   ) {
		this.id= id ;
		this.client_id = client_id;
		this.etat_commande = etat_commande;
		this.nom_commande = nom_commande;
		this.type_commande = type_commande;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getEtat_commande() {
		return etat_commande;
	}

	public void setEtat_commande(String etat_commande) {
		this.etat_commande = etat_commande;
	}

	public String getNom_commande() {
		return nom_commande;
	}

	public void setNom_commande(String nom_commande) {
		this.nom_commande = nom_commande;
	}

	public String getType_commande() {
		return type_commande;
	}

	public void setType_commande(String type_commande) {
		this.type_commande = type_commande;
	}

	
	


}
