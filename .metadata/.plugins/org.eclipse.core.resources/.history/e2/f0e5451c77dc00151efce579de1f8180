package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the player_type database table.
 * 
 */
@Entity
@Table(name="player_type")
@NamedQuery(name="PlayerType.findAll", query="SELECT p FROM PlayerType p")
public class PlayerType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private Integer id;

	private String type;

	public PlayerType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}