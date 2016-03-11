package model;

import java.io.Serializable;

import javax.faces.convert.FacesConverter;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the player database table.
 * 
 */
@Entity
@FacesConverter("player")
@NamedQuery(name="Player.findAll", query="SELECT p FROM Player p")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private Boolean available;

	private String password;

	private String picture;

	private double rating;

	private Integer type;

	private String username;

	//bi-directional many-to-one association to GameLoser
	@OneToMany(mappedBy="player")
	private List<GameLoser> gameLosers;

	//bi-directional many-to-one association to GamePlayer
	@OneToMany(mappedBy="player")
	private List<GamePlayer> gamePlayers;

	//bi-directional many-to-one association to GameWinner
	@OneToMany(mappedBy="player")
	private List<GameWinner> gameWinners;

	//bi-directional many-to-one association to PlayerRating
	@OneToMany(mappedBy="player")
	private List<PlayerRating> playerRatings;

	public Player() {
         playerRatings=new ArrayList<PlayerRating>();
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

	public List<GameLoser> getGameLosers() {
		return this.gameLosers;
	}

	public void setGameLosers(List<GameLoser> gameLosers) {
		this.gameLosers = gameLosers;
	}

	public GameLoser addGameLoser(GameLoser gameLoser) {
		getGameLosers().add(gameLoser);
		gameLoser.setPlayer(this);

		return gameLoser;
	}

	public GameLoser removeGameLoser(GameLoser gameLoser) {
		getGameLosers().remove(gameLoser);
		gameLoser.setPlayer(null);

		return gameLoser;
	}

	public List<GamePlayer> getGamePlayers() {
		return this.gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public GamePlayer addGamePlayer(GamePlayer gamePlayer) {
		getGamePlayers().add(gamePlayer);
		gamePlayer.setPlayer(this);

		return gamePlayer;
	}

	public GamePlayer removeGamePlayer(GamePlayer gamePlayer) {
		getGamePlayers().remove(gamePlayer);
		gamePlayer.setPlayer(null);

		return gamePlayer;
	}

	public List<GameWinner> getGameWinners() {
		return this.gameWinners;
	}

	public void setGameWinners(List<GameWinner> gameWinners) {
		this.gameWinners = gameWinners;
	}

	public GameWinner addGameWinner(GameWinner gameWinner) {
		getGameWinners().add(gameWinner);
		gameWinner.setPlayer(this);

		return gameWinner;
	}

	public GameWinner removeGameWinner(GameWinner gameWinner) {
		getGameWinners().remove(gameWinner);
		gameWinner.setPlayer(null);

		return gameWinner;
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
	
	@Override
	public String toString() {
		return id + "##" + username+ "##" + password+ "##" + this.rating + "##" + this.available + "##" +this.type+"##"+this.picture;
	}

}