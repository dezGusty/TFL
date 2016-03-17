package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the team_player database table.
 * 
 */
@Entity
@Table(name="team_player")
@NamedQuery(name="TeamPlayer.findAll", query="SELECT t FROM TeamPlayer t")
public class TeamPlayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="id_player")
	private Integer idPlayer;

	public TeamPlayer() {
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