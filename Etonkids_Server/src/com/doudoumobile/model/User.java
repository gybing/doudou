package com.doudoumobile.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * This VO fits for xmpp user
 * */
@Entity
@Table(name = "APN_User")
public class User implements Serializable {

    private static final long serialVersionUID = 4733464888738356502L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "apn_username", nullable = false, length = 64, unique = true)
    private String username;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name= "eton_id")
    private Long etonUserId;

	@Column(name = "lastLoginTime")
    private Date lastLoginTime;

    @Column(name = "available")
    private boolean available;
    
	@Transient
    private boolean online;
	
	@Transient
    private String realName;
	
	@Transient
	private String status;
	
	@Transient
	private String updateTime;

    public User() {
    	available = true;
    }

    public User(final String username) {
        this.username = username;
        available = true;
    }

    public Long getEtonUserId() {
    	return etonUserId;
    }
    
    public void setEtonUserId(Long etonUserId) {
    	this.etonUserId = etonUserId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}


	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	@Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        final User obj = (User) o;
        if (username != null ? !username.equals(obj.username)
                : obj.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 29 * result + (username != null ? username.hashCode() : 0);
        result = 29 * result
                + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
