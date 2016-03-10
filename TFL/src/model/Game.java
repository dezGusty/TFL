package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the games database table.
 * 
 */
@Entity
@Table(name="games")
@NamedQuery(name="Game.findAll", query="SELECT g FROM Game g")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Integer difference;

	//bi-directional many-to-one association to GameLoser
	@OneToMany(mappedBy="game")
	private List<GameLoser> gameLosers;

	//bi-directional many-to-one association to GamePlayer
	@OneToMany(mappedBy="game")
	private List<GamePlayer> gamePlayers;

	//bi-directional many-to-one association to GameWinner
	@OneToMany(mappedBy="game")
	private List<GameWinner> gameWinners;

	public Game() {
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

	public Integer getDifference() {
		return this.difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public List<GameLoser> getGameLosers() {
		return this.gameLosers;
	}

	public void setGameLosers(List<GameLoser> gameLosers) {
		this.gameLosers = gameLosers;
	}

	public GameLoser addGameLoser(GameLoser gameLoser) {
		getGameLosers().add(gameLoser);
		gameLoser.setGame(this);

		return gameLoser;
	}

	public GameLoser removeGameLoser(GameLoser gameLoser) {
		getGameLosers().remove(gameLoser);
		gameLoser.setGame(null);

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
		gamePlayer.setGame(this);

		return gamePlayer;
	}

	public GamePlayer removeGamePlayer(GamePlayer gamePlayer) {
		getGamePlayers().remove(gamePlayer);
		gamePlayer.setGame(null);

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
		gameWinner.setGame(this);

		return gameWinner;
	}

	public GameWinner removeGameWinner(GameWinner gameWinner) {
		getGameWinners().remove(gameWinner);
		gameWinner.setGame(null);

		return gameWinner;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id + ", date=" + date + ", difference=" + difference + ", gameLosers=" + gameLosers
				+ ", gamePlayers=" + gamePlayers + ", gameWinners=" + gameWinners + "]";
	}


}