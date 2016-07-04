package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
	private Game game;
	private DualListModel<Player> players;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@PostConstruct
	public void init() {
		if (players == null) {
			players = new DualListModel<Player>();
		}
		this.game = new Game();
	}

	public DualListModel<Player> getPlayers() {
		return players;
	}

	public void setPlayers(DualListModel<Player> players) {
		this.players = players;
	}

	public void onTransfer(TransferEvent event) {
		this.game.getTeam1().setPlayers(new HashSet<Player>(this.players.getSource()));
		this.game.getTeam2().setPlayers(new HashSet<Player>(this.players.getTarget()));
	}

	public void saveTeams() {

		this.game.setTeam1(TeamDataAccess.SaveTeamName(this.game.getTeam1().getId(), this.game.getTeam1().getName()));
		this.game.setTeam2(TeamDataAccess.SaveTeamName(this.game.getTeam2().getId(), this.game.getTeam2().getName()));

		TeamDataAccess.RemoveAllPlayers(this.game.getTeam1().getId());
		TeamDataAccess.RemoveAllPlayers(this.game.getTeam2().getId());
		for (Player play : this.players.getSource()) {
			game.setTeam1(TeamDataAccess.AddNewPlayer(game.getTeam1().getId(), play.getId()));
		}
		for (Player play : this.players.getTarget()) {
			game.setTeam2(TeamDataAccess.AddNewPlayer(game.getTeam2().getId(), play.getId()));
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Teams updated successfully!"));
	}

	public void saveTeamsResult() {

		int difference = Math.abs(this.game.getTeam1().getGoals() - this.game.getTeam2().getGoals());
		this.game = GameDataAccess.SetDifference(this.game.getId(), difference);

		this.game.getTeam1().getPlayers().removeAll(this.game.getTeam1().getPlayers());
		this.game.getTeam2().getPlayers().removeAll(this.game.getTeam2().getPlayers());
		this.game.getTeam1().setScore(0);
		this.game.getTeam2().setScore(0);
		System.out.println("first team players" + this.game.getTeam1().getPlayers() + "team rating: "
				+ this.game.getTeam1().getScore());
		System.out.println("second team players " + this.game.getTeam2().getPlayers() + "team rating: "
				+ this.game.getTeam2().getScore());
		if (game.getTeam1().getGoals() > game.getTeam2().getGoals()) {
			game.getTeam1().setWinner(true);
			game.getTeam2().setWinner(false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Team "+game.getTeam1().getName()+" won this game! Players ratings were updated!"));
		} else if (game.getTeam2().getGoals() > game.getTeam1().getGoals()) {
			game.getTeam2().setWinner(true);
			game.getTeam1().setWinner(false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Team "+game.getTeam2().getName()+" won this game! Players ratings were updated!"));

		}

		for (Player play : this.players.getSource()) {
			this.game.setTeam1(TeamDataAccess.AddNewPlayer(this.game.getTeam1().getId(), play.getId()));
		}
		for (Player play : this.players.getTarget()) {
			this.game.setTeam2(TeamDataAccess.AddNewPlayer(this.game.getTeam2().getId(), play.getId()));
		}
		System.out.println("first team players" + this.game.getTeam1().getPlayers() + "team rating: "
				+ this.game.getTeam1().getScore());
		System.out.println("second team players " + this.game.getTeam2().getPlayers() + "team rating: "
				+ this.game.getTeam1().getScore());
		UpdateRatingsForGame(this.game.getId());
		UpdateRatingsForGame(this.game.getId());
	}

	public void UpdateRatingsForGame(int gameId) {
		Game g = GameDataAccess.GetGame(gameId);
		System.out.println("Will be updated for diff: " + g.getDifference());
		if (g.getPlayers() != null) {
			for (Player p : g.getPlayers()) {
				System.out.println("Rating updated for player: " + p.toString());
				System.out.println(p.getPlayerRatings());
				if (p.hasRatingForGame(g)) {
					System.out.println("Already has rating for game: " + g.getDate());
					double lastRating = p.getRatingBefore(g);
					System.out.println("Rating before this game" + p.getRatingBefore(g));
					System.out.println("Rating id to update: " + p.getRatingForGame(g).getId());
					ActualizareRating(g.getId(), p.getId(), lastRating);
				} else {
					System.out.println("Has no rating for game: " + g.getDate());
					AddRatingForGame(g.getId(),p.getId());
				}
				UpdateRatingForNextGames(p.getId(), game);
			}
		}
	}

	private void UpdateRatingForNextGames(int playerId, Game game) {
		Player p = PlayerDataAccess.FindPlayer(playerId);
		List<Game> list = new ArrayList<Game>(p.getGames());
		for (Game playerGame : list) {
			if (playerGame.getDate().after(game.getDate())) {
				System.out.println("game after: " + playerGame.toString());
				if (p.hasRatingForGame(playerGame)) {
					double lastRating = p.getRatingBefore(playerGame);
					System.out.println(lastRating);
					ActualizareRating(playerGame.getId(), p.getId(), lastRating);
				}
			}
		}
	}

	private void ActualizareRating(int gameId, int playerId, double oldRating) {
		Game game = GameDataAccess.GetGame(gameId);
		Player player = PlayerDataAccess.FindPlayer(playerId);
		double newRating = 0;
		if (game.getTeam1().containsPlayer(player)) {
			if (game.getTeam1().getWinner()) {
				newRating = oldRating + (0.01 * game.getDifference());
				System.out.println("Rating updated from " + oldRating + "to " + newRating);
			} else {
				newRating = oldRating - (0.01 * game.getDifference());
				System.out.println("Rating updated from " + oldRating + "to " + newRating);
			}
		} else {
			if (game.getTeam2().containsPlayer(player)) {
				if (game.getTeam2().getWinner()) {
					newRating = oldRating + (0.01 * game.getDifference());
					System.out.println("Rating updated from " + oldRating + "to " + newRating);
				} else {
					newRating = oldRating - (0.01 * game.getDifference());
					System.out.println("Rating updated from " + oldRating + "to " + newRating);
				}
			}
		}
		PlayerRatingAccess.UpdateRating(player.getRatingForGame(game).getId(), newRating);
		PlayerDataAccess.UpdateRating(playerId, newRating);
	}
	
	private void AddRatingForGame(int gameId, int playerId)
	{
		Game game = GameDataAccess.GetGame(gameId);
		Player player = PlayerDataAccess.FindPlayer(playerId);
		double newRating = 0;
		if (game.getTeam1().containsPlayer(player)) {
			if (game.getTeam1().getWinner()) {
				newRating = player.getRating() + (0.01 * game.getDifference());
				System.out.println("Rating updated from "  + newRating);
			} else {
				newRating = player.getRating()  - (0.01 * game.getDifference());
				System.out.println("Rating updated from " +"to " + newRating);
			}
		} else {
			if (game.getTeam2().containsPlayer(player)) {
				if (game.getTeam2().getWinner()) {
					newRating = player.getRating()  + (0.01 * game.getDifference());
					System.out.println("Rating updated from " + "to " + newRating);
				} else {
					newRating = player.getRating()  - (0.01 * game.getDifference());
					System.out.println("Rating updated from " + "to " + newRating);
				}
			}
		}
		PlayerRatingAccess.RegisterNewRating(new PlayerRating(game.getDate(), player, newRating));
		PlayerDataAccess.UpdateRating(playerId, newRating);
	}
	
	public void addPlayerToGame() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		AutoCompleteView autoCompleteBean = (AutoCompleteView) elContext.getELResolver().getValue(elContext, null, "autoCompleteView");
		NextGamesView nextGamesView = (NextGamesView) elContext.getELResolver().getValue(elContext, null,
				"nextGamesView");
		
		if (this.game.getPlayers().size() >= nextGamesView.MAXNUMBEROFPLAYERS) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null,
					"There are already " + nextGamesView.MAXNUMBEROFPLAYERS + " players! You can not add new player!"));
		} else {
			if(this.game.playingThisGame(autoCompleteBean.getSelectedPlayer()))
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null,
						"Player " +autoCompleteBean.getSelectedPlayer().getUsername() + " already subscribed to this game!"));
			}
			else
			{
				GameDataAccess.PlayGame(this.game.getId(), autoCompleteBean.getSelectedPlayer().getId());
				nextGamesView.generateTeams(this.game.getId());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null,
						"Player " + autoCompleteBean.getSelectedPlayer().getUsername() + " added to game!"));
				this.game=GameDataAccess.GetGame(this.game.getId());
				this.players.setSource(this.game.getTeam1().getListPlayers());
				this.players.setTarget(this.game.getTeam2().getListPlayers());
			}
		}
	}
}