package models;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

public class Produit {


	private ObjectId id;
	private String name ;
	private String description ;
	private double prix ;
	private String type ;  // plat, un sandwich, une salade, un dessert, une viennoiserie, une boisson… 
	
	
	public Produit(  ObjectId id ,String name , String description , double prix , String type   ) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.prix = prix;
		this.type = type;
	}



	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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
