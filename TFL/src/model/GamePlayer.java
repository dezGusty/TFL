package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the game_players database table.
 * 
 */
@Entity
@Table(name="game_players")
@NamedQuery(name="GamePlayer.findAll", query="SELECT g FROM GamePlayer g")
public class GamePlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Game
	@ManyToOne
	private Game game;

	//bi-directional many-to-one association to Player
	@ManyToOne
	private Player player;

	public GamePlayer() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}