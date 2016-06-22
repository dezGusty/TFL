package views;

import java.io.Serializable;
import java.util.HashSet;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import dataAccessLayer.TeamDataAccess;
import model.Game;
import model.Player;

@ManagedBean(name = "teamsView")
@SessionScoped
public class TeamsView implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public int indexOfMap=0;
	private boolean existTeams;
	private Game game;
	private boolean showNextPrevious;
	private DualListModel<Player> players;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isShowNextPrevious() {
		return showNextPrevious;
	}

	public void setShowNextPrevious(boolean showNextPrevious) {
		this.showNextPrevious = showNextPrevious;
	}

	public boolean isExistTeams() {
		return this.existTeams;
	}

	public void setExistTeams(boolean existTeams) {
		this.existTeams = existTeams;
	}
	
	@PostConstruct
	public void init() {
		if(players==null)
		{
			players=new DualListModel<Player>();
		}	
		this.game=new Game();
	}

	public DualListModel<Player> getPlayers() {
		return players;
	}

	public void setPlayers(DualListModel<Player> themes) {
		this.players = themes;
	}

	public void onTransfer(TransferEvent event) {
		
		 System.out.println("Source:");
		 this.game.getTeam1().setPlayers( new HashSet<Player>(this.players.getSource()));
		
		 for(Player p: this.players.getSource())
		 {
		 System.out.println(p.getUsername());
		 }

		System.out.println("Target:");
		this.game.getTeam2().setPlayers( new HashSet<Player>(this.players.getTarget()));

		 for(Player p: this.players.getTarget())
		 {
		 System.out.println(p.getUsername());
		 }
	}

	 public void addMessage(ActionEvent actionEvent) {
	       System.out.println("Welcome to Primefaces!!");
	    }
	 
	 public void saveTeams() {
		   
		System.out.println("Hello from save teams!");
		System.out.println("first team winner: "+this.game.getTeam1().getWinner());
		System.out.println("second team winner: "+this.game.getTeam2().getWinner());
		
		this.game.setTeam1(TeamDataAccess.SaveTeamName(this.game.getTeam1().getId(), this.game.getTeam1().getName()));
		this.game.setTeam2(TeamDataAccess.SaveTeamName(this.game.getTeam2().getId(), this.game.getTeam2().getName()));
		
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
     	NextGamesView firstBean = (NextGamesView) elContext.getELResolver().getValue(elContext, null, "nextGamesView");
     	
     	Game game=firstBean.getSelectedGame();
     	System.out.println("save teams for game: "+game.getId());
     	TeamDataAccess.RemoveAllPlayers(game.getTeam1().getId());
		TeamDataAccess.RemoveAllPlayers(game.getTeam2().getId());
		for(Player play: this.players.getSource())
		{
			System.out.println(play.getUsername());
			game.setTeam1(TeamDataAccess.AddNewPlayer(game.getTeam1().getId(),play.getId()));
		}
		for(Player play: this.players.getTarget())
		{
			System.out.println(play.getUsername());
			game.setTeam2(TeamDataAccess.AddNewPlayer(game.getTeam2().getId(),play.getId()));
		}
	 }
}