package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Player;

@ManagedBean(name = "waitingPlayers")
@SessionScoped
public class WaitingPlayers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> players;
	private Player selectedPlayer;

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

	@PostConstruct
	public void init() {
		if (this.players == null) {
			this.players = new ArrayList<Player>();
		}
		this.selectedPlayer = new Player();
	}
}