package models;

import java.math.BigDecimal;

public class Produit {

	private String name ;
	private String description ;
	private double prix ;
	private String type ;  // plat, un sandwich, une salade, un dessert, une viennoiserie, une boisson… 
	
	
	public Produit(  String name , String description , double prix , String type   ) {
		this.name = name;
		this.description = description;
		this.prix = prix;
		this.type = type;
	}





	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
