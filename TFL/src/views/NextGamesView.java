package views;

import model.Game;
import model.Player;
import model.Team;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import dataAccessLayer.GameDataAccess;
import helpers.TeamGenerator;

@ManagedBean(name = "nextGamesView")
@SessionScoped
public class NextGamesView implements Serializable{

	 private static final long serialVersionUID = 1L;

	    public static List<Game> games;
	     
	    private Game selectedGame;
	    
	    public Game getSelectedGame() {
			return selectedGame;
		}

		public void setSelectedGame(Game selectedGame) {
			this.selectedGame = selectedGame;
		}

		private Date gameDate;
	    
	    public Date getGameDate() {
			return gameDate;
		}

		public void setGameDate(Date gameDate) {
			this.gameDate = gameDate;
		}

	    public List<Game> getGames() {
	        return games;
	    }
	    
	    public void setGames(List<Game> game) {
	        games=game;
	    }
	 		
		public void play(Game game) {
			System.out.println("PlayGame!");
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
			if(game.gameStatus(firstBean.getCurrentPlayer()))
			{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "You are already playing on "+game.dateToDisplay()));
			}
			else
			{
				firstBean.setCurrentPlayer(GameDataAccess.PlayGame(game.getId(), firstBean.getCurrentPlayer().getId()));
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!","You are now playing on "+game.dateToDisplay()));
				NextGamesView.games=GameDataAccess.listNextGames();
			}		
			System.out.println("Done");
		}
		
		public void newGame()
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(	format.format(gameDate));
			GameDataAccess.addNewGame(format.format(gameDate));
		    FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "New game on "+format.format(gameDate)));
		}
		 
		public void remove(Game game) {
			game.setArchive(true);
			GameDataAccess.addToArchive(game.getId());
			games.remove(game);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Game successfully canceled! "));
		}
		
		public void teamsView(Game game)
		{
			this.selectedGame=GameDataAccess.GetGame(game.getId());
			System.out.println("Method!");
			Game selectedGame=GameDataAccess.GetGame(game.getId());
			
			ELContext context = FacesContext.getCurrentInstance().getELContext();
			LoginView firstBean = (LoginView) context.getELResolver().getValue(context, null, "loginView");
			TeamsView teamsBean = (TeamsView) context.getELResolver().getValue(context, null, "teamsView");
			teamsBean.setShowNextPrevious(false);
			
			if(selectedGame.getTeam1()!=null && selectedGame.getTeam2()!=null)
			{
				System.out.println("Game has teams");
				teamsBean.setFirstTeam(selectedGame.getTeam1());
				System.out.println("FirstTeamscore" +selectedGame.getTeam1().getScore());
				
				teamsBean.setSecondTeam(selectedGame.getTeam2());
				System.out.println("SecondTeamscore" +selectedGame.getTeam2().getScore());
				teamsBean.setSecondTeam(selectedGame.getTeam2());
			}
			else
			{
				System.out.println("Game has no teams yet!");
				if(selectedGame.getPlayers().isEmpty())
				{
					System.out.println("There are no player subscribers");
					teamsBean.setFirstTeam(new Team("Team name"));
					teamsBean.setSecondTeam(new Team("team name"));
				}
				else
				{
					System.out.println("There are player subscribers");
					List <Player> newList=new ArrayList<Player>();
					newList.addAll(selectedGame.getPlayers());					
					if(selectedGame.getPlayers().size()<=3)
					{
						teamsBean.setFirstTeam(new Team("Team name",newList));
						teamsBean.setSecondTeam(new Team("Team name"));
					}
					else
					{
						System.out.println("There are more then 3 player subscribers!Subscribed players:");
						
						for(Player p:selectedGame.getPlayers())
						{
							System.out.println(p.toString());
						}
						teamsBean.setShowNextPrevious(true);
						List<List<Player>> listed=this.generateTeams(newList);
						for(List<Player> l:listed)
						{
							System.out.println("Team");
							for(Player p:l)
							{
								System.out.println(p.toString());
							}
						}
						teamsBean.setFirstTeam(new Team("Team name",listed.get(0)));
						teamsBean.setSecondTeam(new Team("Team name",listed.get(1)));
					}
				}	
			}	
			teamsBean.setPlayers( new DualListModel<>(teamsBean.getFirstTeam().getPlayers(), teamsBean.getSecondTeam().getPlayers()));
			RedirectView.Redirect(firstBean.getCurrentPlayer(),  "/faces/resources/teamsuser.xhtml", "/faces/resources/teamsadmin.xhtml");
		}

		public List<List<Player>> generateTeams(List<Player> players)
		{	
			List<List<Player>> list=new ArrayList<List<Player>>();
			TeamGenerator.list=players;
			TeamGenerator.generateTeams();
			TeamGenerator.printMap(TeamGenerator.map);
			Object key = TeamGenerator.map.keySet().toArray(new Object[TeamGenerator.map.size()])[0];
			
			List<Player> firstList = TeamGenerator.map.get(key);
			list.add(firstList);
			
			System.out.println("First team:");
			for(Player p:firstList)
			{
				System.out.println(p.getUsername());
			}
			
			List<Player> secondList = new ArrayList<Player>();
			
			//poate fi scoasa intr-o alta metoda in care dintr-o lista de jucatori elimin alta lista de jucatori
			boolean existsInList=false;
			for(Player p:players)
			{
				existsInList=false;
				
				for(Player pl:firstList)
				{
					if(p.getId()==pl.getId())
					{
						existsInList=true;
						break;
					}
				}
				if(!existsInList)
				{
					secondList.add(p);
				}
				
			}
			System.out.println("Second list:");
			for(Player p:secondList)
			{
				System.out.println(p.getUsername());
			}
			list.add(secondList);

			return list;
		}	
		
		public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	        GameDataAccess.SetDiff(Integer.parseInt(event.getRowKey()), Integer.parseInt(newValue.toString()));
	        if(newValue != null && !newValue.equals(oldValue)) {
	        FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Update difference from " + oldValue + " to " + newValue+"!"));
	        }
	    }
		
		public void onRowSelect(SelectEvent event) {
          this.selectedGame=(Game) event.getObject();
	    }
	 
	    public void onRowUnselect(UnselectEvent event) {

	    }
}