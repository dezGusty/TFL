package model;

import java.io.Serializable;
import javax.persistence.*;
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

	//bi-directional many-to-one association to Game
	@ManyToOne
	@JoinColumn(name="game")
	private Game gameBean;

	//bi-directional many-to-many association to Player
	//@ManyToMany(mappedBy="teams", fetch=FetchType.EAGER)
	private List<Player> players;

	public Team() {
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

	public Game getGameBean() {
		return this.gameBean;
	}

	public void setGameBean(Game gameBean) {
		this.gameBean = gameBean;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}