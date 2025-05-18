package models;

import org.bson.types.ObjectId;

import java.util.Date;

public class Gerant {

	private ObjectId id;
	private String name;
	private String LastName;
	private Date date_de_naissance;
	private String Email ;
	private String adresse;
	private String password;
	final String role;



	public Gerant(String name, String LastName, Date date_de_naissance, String Email , String adresse, String password ) {
		this.name = name;
		this.LastName = LastName;
		this.date_de_naissance = date_de_naissance;
		this.adresse = adresse;
		this.password = password;
		this.Email = Email ;
		this.role = "Gerant";
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


	public String getLastName() {
		return LastName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	public Date getDate_de_naissance() {
		return date_de_naissance;
	}


	public void setDate_de_naissance(Date date_de_naissance) {
		this.date_de_naissance = date_de_naissance;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
