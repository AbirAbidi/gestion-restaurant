package models;

import org.bson.types.ObjectId;

import java.util.List;

public class Commande {




	//etat de commande
	public enum EtatCommande {NON_TRAITEE , EN_PREPARATION , PRETE , ANNULE }
	 //Commande à livrer à domicile , Commande sur place,Commande à importer
	public enum TypeCommande { LIVRAISON, SUR_PLACE , A_IMPORTER }

	private String id_client ;
	private EtatCommande etat_commande ;
	private TypeCommande type_commande ; 
	private String produits ; // what is there in the command
	
	public Commande(String id_client , EtatCommande etat_commande ,  TypeCommande type_commande  ,String produits) {
		this.id_client = id_client;
		this.etat_commande = EtatCommande.NON_TRAITEE;
		this.type_commande = type_commande;
		this.produits = produits ;
	}


	public String getId_client() {
		return id_client;
	}

	public void setId_client(String id_client) {
		this.id_client = id_client;
	}

	public EtatCommande getEtat_commande() {
		return etat_commande;
	}

	public void setEtat_commande(EtatCommande etat_commande) {
		this.etat_commande = etat_commande;
	}

	public TypeCommande getType_commande() {
		return type_commande;
	}

	public void setType_commande(TypeCommande type_commande) {
		this.type_commande = type_commande;
	}

	public String getProduits() {
		return produits;
	}

	public void setProduits(String produits) {
		this.produits = produits;
	}



	
	


}
