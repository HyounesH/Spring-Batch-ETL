package ma.ensa.younes.hamdane.batch.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Personne {

	@Id
	private String cin;
	private String nom;
	private String prenom;
	private String civilite;
	public Personne() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Personne(String cni, String nom, String prenom, String civilite) {
		super();
		this.cin = cni;
		this.nom = nom;
		this.prenom = prenom;
		this.civilite = civilite;
	}
	
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	
}
