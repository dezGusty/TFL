package views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dataAccessLayer.PlayerDataAccess;
import model.Player;

@ManagedBean(name = "playersView")
@ViewScoped
public class PlayersView {

	 private static final long serialVersionUID = 1L;
	    public List<Player> players;
	    
	    @ManagedProperty("#{playerDataAccess}")
	    public PlayerDataAccess playerData;
	    
	    private Player selectedPlayer;

		@PostConstruct
	    public void init() {

			playerData=new PlayerDataAccess();
			players=new ArrayList<Player>();
			players=playerData.listPlayers();
	    	selectedPlayer=new Player();
	    }

		public List<Player> getPlayers() {
			return players;
		}

		public void setPlayers(List<Player> players) {
			this.players = players;
		}

		public PlayerDataAccess getPlayerData() {
			return playerData;
		}

		public void setPlayerData(PlayerDataAccess playerData) {
			this.playerData = playerData;
		}

		public Player getSelectedPlayer() {
			return selectedPlayer;
		}

		public void setSelectedPlayer(Player selectedPlayer) {
			this.selectedPlayer = selectedPlayer;
		}
	    
	
}
