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
	@Column(name="\"ID\"")
	private Integer id;

	@Column(name="game_id")
	private Integer gameId;

	public GamePlayer() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

}