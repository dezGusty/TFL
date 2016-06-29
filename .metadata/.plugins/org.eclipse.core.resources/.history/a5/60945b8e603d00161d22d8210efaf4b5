package views;

import java.io.Serializable;
import java.util.HashSet;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import dataAccessLayer.GameDataAccess;
import dataAccessLayer.PlayerDataAccess;
import dataAccessLayer.PlayerRatingAccess;
import dataAccessLayer.TeamDataAccess;
import model.Game;
import model.Player;
import model.PlayerRating;

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
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null,"Teams updated successfully!"));
	 }
	 
	 public void saveTeamsResult() {
		 System.out.println("save teams result");
		 if(game.getTeam1().getGoals()>game.getTeam2().getGoals())
		 {
			 game.getTeam1().setWinner(true);
			 game.getTeam2().setWinner(false);
			 FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, null,"FirstTeam won!"));
		 }
		 else
			 if(game.getTeam2().getGoals()>game.getTeam1().getGoals())
			 {
				 game.getTeam2().setWinner(true);
				 game.getTeam1().setWinner(false);
				 FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, null,"Secondteam won!"));
				 
			 }
		 TeamDataAccess.UpdateTeam(game.getTeam1());
		 TeamDataAccess.UpdateTeam(game.getTeam2());
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
		int difference=Math.abs(this.game.getTeam1().getGoals()-this.game.getTeam2().getGoals());
		this.game=GameDataAccess.SetDifference(this.game.getId(), difference);
		for(Player pla:this.game.getPlayers())
		{
			UpdateRatingForEachPlayer(pla.getId(),this.game.getId());
		}
		this.game=GameDataAccess.GetGame(this.game.getId());
	 } 
	 
	 public Player UpdateRatingForEachPlayer(int playerId, int gameId)
	 {
		  Player p=PlayerDataAccess.FindPlayer(playerId);
		  Game ga=GameDataAccess.GetGame(gameId);

		  if(PlayerDataAccess.DeleteRatingsBeforeGame(playerId, gameId))
		  {
			  p=PlayerDataAccess.UpdateLastValidRating(playerId);
			  System.out.println("Ratings deleted successfully");
			  System.out.println("last rating is: "+p.getRating());
			  p=PlayerDataAccess.NewRatingForGame(playerId, gameId);
			  PlayerRating newRating=new PlayerRating(ga.getDate(),p,p.getRating());
			  for(Game game:p.getGames())
			  {
				  PlayerRatingAccess.RegisterNewRating(newRating);
				  if(game.getDate().after(ga.getDate()))
				  {
					  p=PlayerDataAccess.NewRatingForGame(p.getId(), game.getId());
					  PlayerRating newwRating=new PlayerRating(game.getDate(),p,p.getRating());
					  PlayerRatingAccess.RegisterNewRating(newwRating);
				  }
			  }
		  }
		  else
		  {
			  System.out.println("No ratings were deleted");
			  p=PlayerDataAccess.NewRatingForGame(playerId, gameId);
			  PlayerRating newRating=new PlayerRating(ga.getDate(),p,p.getRating());
			  PlayerRatingAccess.RegisterNewRating(newRating);
		  }
		  
		  System.out.println("new rating: "+p.getRating());
		  return p;
	 }
}