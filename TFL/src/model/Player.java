package model;

import java.io.Serializable;
import javax.persistence.*;

import model.PlayerRating;

import java.util.List;


/**
 * The persistent class for the player database table.
 * 
 */
@Entity
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return id+"##"+ username+"##"  + password +"##" + rating +"##"+ this.available+"##"+this.type+"##"+this.picture;
	}
	
	public double getMinRating()
	{
		double min=this.rating;
		for(PlayerRating playerRating: this.playerRatings)
		{
			if(min>playerRating.getRating())
			{
				min=playerRating.getRating();
			}
		}
		return min;
	}
	
	public double getMaxRating()
	{
		double max=this.rating;
		for(PlayerRating playerRating: this.playerRatings)
		{
			if(max<playerRating.getRating())
			{
				max=playerRating.getRating();
			}
		}
		return max;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean available;

	private String password;

	private String picture;

	private double rating;

	private Integer type;

	private String username;

	//bi-directional many-to-many association to Game
	//@ManyToMany(mappedBy="players", fetch=FetchType.EAGER)
	private List<Game> games;

	//bi-directional many-to-one association to PlayerRating
	@OneToMany(mappedBy="player", fetch=FetchType.EAGER)
	private List<PlayerRating> playerRatings;

	//bi-directional many-to-many association to Team
	
	private List<Team> teams;

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

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<PlayerRating> getPlayerRatings() {
		return this.playerRatings;
	}

	public void setPlayerRatings(List<PlayerRating> playerRatings) {
		this.playerRatings = playerRatings;
	}

	public PlayerRating addPlayerRating(PlayerRating playerRating) {
		getPlayerRatings().add(playerRating);
		playerRating.setPlayer(this);

		return playerRating;
	}

	public PlayerRating removePlayerRating(PlayerRating playerRating) {
		getPlayerRatings().remove(playerRating);
		playerRating.setPlayer(null);

		return playerRating;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="id")
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

}