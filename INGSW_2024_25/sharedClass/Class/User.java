package Class;

import java.util.Date;

public class User {
	
    private String mail;
	private String password;
	private String nome;
	private String cognome;
	private String numeroTelefono;
	private String dataNascita;
	private boolean isAgente;
    
	
	public User(String mail, String password, String nome, String cognome, String numeroTelefono, String dataNascita, boolean isAgente) {
    	this.mail = mail;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.dataNascita = dataNascita;
        this.isAgente = isAgente;
	}
	
	
	
	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}

	
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	
	
	public String getDataNascita() {
		return dataNascita;
	}
	
	
	public String getMail() {
        return mail;
    }

	
    public String getPassword() {
        return password;
    }
	
    public boolean getIsAgente() {
    	return isAgente;
    }
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}


	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setIsAgente(boolean isAgente) {
		this.isAgente = isAgente;
	}

  
}


