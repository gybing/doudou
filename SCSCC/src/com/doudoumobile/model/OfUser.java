package com.doudoumobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This VO fits for openfire user
 * */
@Entity
@Table(name = "ofUser")
public class OfUser implements Serializable {

    private static final long serialVersionUID = 4733464888738356502L;

    @Id
    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "plainPassword", length = 32)
    private String plainPassword;

    @Column(name= "encryptedPassword", length = 255)
    private String encryptedPassword;

	@Column(name = "name", length = 100)
    private String name;

    @Column(name = "email")
    private String email;
    
    @Column(name = "creationDate", nullable = false, length = 15)
    private String creationDate;
    
    @Column(name = "modificationDate", nullable = false, length = 15)
    private String modificationDate;
    

    public OfUser() {
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPlainPassword() {
		return plainPassword;
	}


	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}


	public String getEncryptedPassword() {
		return encryptedPassword;
	}


	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getModificationDate() {
		return modificationDate;
	}


	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

    
}
