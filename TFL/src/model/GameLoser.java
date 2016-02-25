package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the game_losers database table.
 * 
 */
@Entity
@Table(name="game_losers")
@NamedQuery(name="GameLoser.findAll", query="SELECT g FROM GameLoser g")
public class GameLoser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private Integer id;

	@Column(name="id_player")
	private Integer idPlayer;

	public GameLoser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPlayer() {
		return this.idPlayer;
	}

	public void setIdPlayer(Integer idPlayer) {
		this.idPlayer = idPlayer;
	}

}