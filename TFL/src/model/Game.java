package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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
	@Column(name="\"ID\"")
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Integer difference;

	public Game() {
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

}