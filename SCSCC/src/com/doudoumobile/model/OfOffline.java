package com.doudoumobile.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "ofOffline")
public class OfOffline implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -6737346597068027334L;

	@Id
    @Column(name = "username", nullable = false, length = 64)
    private String username;

	@Id
    @Column(name = "messageID", nullable = false)
    private long messageID;

    @Column(name= "creationDate", length = 15)
    private String creationDate;

    @Lob  
    @Basic(fetch=FetchType.LAZY)
	@Column(columnDefinition = "TEXT",name = "stanza")
    private String stanza;

    @Column(name = "sended", columnDefinition="boolean NOT NULL DEFAULT FALSE")
    private boolean sended;
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getStanza() {
		return stanza;
	}

	public void setStanza(String stanza) {
		this.stanza = stanza;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}
	
}