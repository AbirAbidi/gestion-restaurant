package com.exemple.models;

import java.math.BigDecimal;

public class Produit {

	private String id ;
	private String name ; 
	private String description ;
	private BigDecimal prix ; // this is better for price storage better than float  or double used in financial calculus
	private String type ;  // plat, un sandwich, une salade, un dessert, une viennoiserie, une boisson… 
	
	
	public Produit(  String id , String name , String description , BigDecimal prix , String type   ) {
		this.id= id ;
		this.name = name;
		this.description = description;
		this.prix = prix;
		this.type = type;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public BigDecimal getPrix() {
		return prix;
	}


	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
