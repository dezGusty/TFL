package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Game
	@ManyToOne
	@JoinColumn(name="game")
	private Game gameBean;

	//bi-directional many-to-one association to TeamPlayer
	@OneToMany(mappedBy="team", fetch=FetchType.EAGER)
	private List<TeamPlayer> teamPlayers;

	public Team() {
		this.gameBean=new Game();
		this.teamPlayers=new ArrayList<TeamPlayer>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Game getGameBean() {
		return this.gameBean;
	}

	public void setGameBean(Game gameBean) {
		this.gameBean = gameBean;
	}

	public List<TeamPlayer> getTeamPlayers() {
		return this.teamPlayers;
	}

	public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public TeamPlayer addTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().add(teamPlayer);
		teamPlayer.setTeam(this);

		return teamPlayer;
	}

	public TeamPlayer removeTeamPlayer(TeamPlayer teamPlayer) {
		getTeamPlayers().remove(teamPlayer);
		teamPlayer.setTeam(null);

		return teamPlayer;
	}
	
	public boolean inThisTeam(int playerID)
	{
		for(TeamPlayer tp: this.teamPlayers)
		{
			int a=tp.getPlayer().getId();
			if(a==playerID)
			{
				System.out.println("Player "+tp.getPlayer().getUsername()+" already in this team");
				return false;
			}
		}
		return true;
	}
	
}