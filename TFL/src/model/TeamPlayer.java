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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Player
	@ManyToOne
	@JoinColumn(name="id_player")
	private Player player;

	//bi-directional many-to-one association to Team
	@ManyToOne
	@JoinColumn(name="id_team")
	private Team team;

	public TeamPlayer() {
	}

	public TeamPlayer(Player player)
	{
		this.setTeam(null);
		this.player=player;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


}