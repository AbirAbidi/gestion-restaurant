package services;

import models.Client;
import models.Commande;
import models.Menu;

public class ClientService {

	
	public void creerClient (Client c) {
		//
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
