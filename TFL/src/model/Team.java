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

	private double score;

	private Boolean winner;

	@ManyToMany 
    @JoinTable(name="team_player", 
          joinColumns=@JoinColumn(name="id_team"),
          inverseJoinColumns=@JoinColumn(name="id_player"))
	private List<Player> players;

	public Team() {
		this.score=0.0;
		this.players=new ArrayList<Player>();
		this.winner=false;
	}
	
	public Team(String name)
	{
		this();
		this.name=name;
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

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Boolean getWinner() {
		return this.winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void addNewPlayer(Player p)
	{
		if(this.players==null)
		{
			this.players=new ArrayList<Player>();
		}
		this.players.add(p);
	}

}