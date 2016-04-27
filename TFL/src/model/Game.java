package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import model.Player;
import java.util.ArrayList;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Integer difference;

	private Boolean archive;
	
	//bi-directional many-to-many association to Player
	@ManyToMany 
    @JoinTable(name="game_players", 
          joinColumns=@JoinColumn(name="game_id"),
          inverseJoinColumns=@JoinColumn(name="player_id"))
	private List<Player> players;
//
//	
//	//bi-directional many-to-one association to Team
//	@OneToMany(mappedBy="gameBean", fetch=FetchType.EAGER)
//	private List<Team> teams;

	public Game() {
		this.date=null;
		this.difference=0;
		this.players=new ArrayList<Player>();
		//this.teams=new ArrayList<Team>();
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

	//bi-directional many-to-one association to Team
		@ManyToOne
		@JoinColumn(name="firstteam")
		private Team team1;

		//bi-directional many-to-one association to Team
		@ManyToOne
		@JoinColumn(name="secondteam")
		private Team team2;
		
	public List<Player> getPlayers() {
		return this.players;
	}


	public void setPlayers(List<Player> players) {
		this.players = players;
	}


	public Team getTeam1() {
		return this.team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return this.team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
	
	public Boolean getArchive() {
		return this.archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
//	public List<Team> getTeams() {
//		return this.teams;
//	}
//
//	public void setTeams(List<Team> teams) {
//		this.teams = teams;
//	}

//	public Team addTeam(Team team) {
//		getTeams().add(team);
//		team.setGameBean(this);
//
//		return team;
//	}
//
//	public Team removeTeam(Team team) {
//		getTeams().remove(team);
//		team.setGameBean(null);
//
//		return team;
//	}
	
	//verifica daca jucatorul joaca deja la joc
	public boolean gameStatus(Player player)
	{
		if(this.players!=null)
		{
			for(Player gamePlayer:this.players)
			{
				
				int a=gamePlayer.getId();
				int b=player.getId();
				if(a==b)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public String dateToDisplay()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(this.date);
	}

}
