package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the player_ratings database table.
 * 
 */
@Entity
@Table(name="player_ratings")
@NamedQuery(name="PlayerRating.findAll", query="SELECT p FROM PlayerRating p")
public class PlayerRating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Override
	public String toString() {
		return "ID: " + id + " Date:" + date + "Rating:" + rating + " Player=" + player.getUsername() ;
	}

	@Temporal(TemporalType.DATE)
	private Date date;

	private double rating;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="id_player")
	private Player player;

	public PlayerRating() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getRating() {
		return this.rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}