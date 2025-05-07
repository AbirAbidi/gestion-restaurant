package models;

import java.util.List;

public class Commande {
	
	//etat de commande 
	public enum EtatCommande {NON_TRAITEE , EN_PREPARATION , PRETE ,EN_ROUTE } 
	 //Commande à livrer à domicile , Commande sur place,Commande à importer
	public enum TypeCommande { LIVRAISON, SUR_PLACE , A_IMPORTER }

	private Client client ;
	private EtatCommande etat_commande ;
	private TypeCommande type_commande ; 
	private List<Produit> produits ; // what is there in the command
	
	public Commande(  Client client , EtatCommande etat_commande ,  TypeCommande type_commande  ,List<Produit> produits) {
		this.client = client;
		this.etat_commande = etat_commande;
		this.type_commande = type_commande;
		this.produits = produits ;
	}



	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}



	
	


}
