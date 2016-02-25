package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the game_winners database table.
 * 
 */
@Entity
@Table(name="game_winners")
@NamedQuery(name="GameWinner.findAll", query="SELECT g FROM GameWinner g")
public class GameWinner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private Integer id;

	@Column(name="id_player")
	private Integer idPlayer;

	public GameWinner() {
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