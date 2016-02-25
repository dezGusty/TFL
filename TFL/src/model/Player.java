package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the player database table.
 * 
 */
@Entity
@NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p")
public class Player implements Serializable {
    @Override
    public String toString() {
	return "Player id:" + id + " available:" + available + " password:" + password + " rating:" + rating + " type:"
		+ type + " username:" + username;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "\"ID\"")
    private Integer id;

    private Boolean available;

    private String password;

    private double rating;

    private Integer type;

    private String username;

    public Player() {
    }

    public Integer getId() {
	return this.id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Boolean getAvailable() {
	return this.available;
    }

    public void setAvailable(Boolean available) {
	this.available = available;
    }

    public String getPassword() {
	return this.password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public double getRating() {
	return this.rating;
    }

    public void setRating(double rating) {
	this.rating = rating;
    }

    public Integer getType() {
	return this.type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public String getUsername() {
	return this.username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

}