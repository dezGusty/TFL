package views;
import model.Game;
import model.Player;
import model.Team;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import dataAccessLayer.GameDataAccess;
import dataAccessLayer.TeamGenerator;

@ManagedBean(name = "nextGamesView")
@SessionScoped
public class NextGamesView implements Serializable{

	 private static final long serialVersionUID = 1L;

	 
	    public static List<Game> games;
	    
	    private Date gameDate;
	    
	    public Date getGameDate() {
			return gameDate;
		}

		public void setGameDate(Date gameDate) {
			this.gameDate = gameDate;
		}

		@ManagedProperty("#{gameDataAccess}")
	    public GameDataAccess gamesData;
	    
	    private Game selectedGame;

	    public List<Game> getGames() {
	        return games;
	    }
	    
	    public void setGames(List<Game> game) {
	        games=game;
	    }
	 
		public GameDataAccess getGamesData() {
			return gamesData;
		}

		public void setGamesData(GameDataAccess gamesData) {
			this.gamesData = gamesData;
		}
		
		public Game getSelectedGame() {
	        return selectedGame;
	    }
	 
		public void setSelectedGame(Game selectedGame) {
			this.selectedGame = selectedGame;
		}
		
		public void play(Game game) {
			
			System.out.println("PlayGame!");
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
			
			GameDataAccess gda=new GameDataAccess();
			gda.playGame(game.getId(), firstBean.getCurrentPlayer().getId());		
			System.out.println("Done");
		}
		
		public void addResult(Game game) {
		
			System.out.println("show");
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			TeamsView firstBean = (TeamsView) elContext.getELResolver().getValue(elContext, null, "teamsView");
			
			
			System.out.println("Winners:");
			Team winnersTeam=new Team();
			for(Player p:firstBean.themesSource)
			{
				System.out.println(p.getUsername());
				//winnersTeam.getPlayers().addAll(firstBean.themesSource);
			}
			winnersTeam.getPlayers().addAll(firstBean.themesSource);
			
			Team loserTeam=new Team();
			System.out.println("Losers:");
			
			for(Player p:firstBean.themesTarget)
			{	
				System.out.println(p.getUsername());
			}
			loserTeam.getPlayers().addAll(firstBean.themesTarget);
			
			//game.addTeam(winnersTeam);
			//game.addTeam(loserTeam);
			
			System.out.println("Game: "+game.dateToDisplay()+" difference "+game.getDifference());

			GameDataAccess gda=new GameDataAccess();
			gda.setDifference(this.selectedGame.getId(),this.selectedGame.getDifference(),winnersTeam,loserTeam);
		}
			
		public void newGame()
		{
			System.out.println("New game");
			GameDataAccess gda=new GameDataAccess();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(	format.format(gameDate));
		    gda.addNewGame(format.format(gameDate));
		    this.setGames(gda.listGames());
			System.out.println("Done!");
		}
		 
		public void remove(Game game) {
			System.out.println("Remove game "+game.getId());
			game.setArchive(true);
			GameDataAccess gda=new GameDataAccess();
			gda.updateGame(game);
			games.remove(game);
		}
		
		public void showMessage(Game game) {
//			this.selectedGame=game;
//
//			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//			TeamsView teamsBean = (TeamsView) elContext.getELResolver().getValue(elContext, null, "teamsView");
//			teamsBean.themesSource=new ArrayList<Player>();
//			teamsBean.themesTarget=new ArrayList<Player>();
//			
//			if(game.getTeams()!=null)
//			{
//				System.out.println("There are teams");
//				//setez echipele
//				teamsBean.setExistTeams(true);
//				if(game.getTeams().size()==2)
//				{
//					teamsBean.themesSource=game.getTeams().get(0).getPlayers();
//					teamsBean.themesTarget=game.getTeams().get(1).getPlayers();
//				}
//			}
//			teamsBean.setPlayers( new DualListModel<>(teamsBean.themesSource, teamsBean.themesTarget));
//			System.out.println("Show message");
		}
		
		public void viewTeams(Game game)
		{		
			System.out.println("View teams method!");
        	this.setSelectedGame(game);
        	
        	//iau userul pentru a verifica ce tip este(normal user sau admin)
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
			firstBean.setClick(false);
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();		
			TeamsView teamsBean = (TeamsView) elContext.getELResolver().getValue(elContext, null, "teamsView");
			
//			//verific daca jocul are echipe
			if(this.selectedGame.getTeam1()!=null && this.selectedGame.getTeam2()!=null)
			{
				System.out.println("Game has teams!");
				teamsBean.setExistTeams(true);
				teamsBean.themesSource=new ArrayList<Player>();
				teamsBean.themesTarget=new ArrayList<Player>();
				
				teamsBean.setTeamOne(this.selectedGame.getTeam1());
				System.out.println("Team one:"+this.selectedGame.getTeam1().getName());
				teamsBean.themesSource.addAll(this.selectedGame.getTeam1().getPlayers());
				
				teamsBean.setTeamTwo(this.selectedGame.getTeam2());
				System.out.println("Second team:"+this.selectedGame.getTeam2().getName());
				teamsBean.themesTarget.addAll(this.selectedGame.getTeam2().getPlayers());
				
				teamsBean.setPlayers( new DualListModel<>(teamsBean.themesSource, teamsBean.themesTarget));
			}
			else
			{
				System.out.println("Game has no teams!");
				if(!this.selectedGame.getPlayers().isEmpty())
				{
					teamsBean.setExistTeams(true);
					System.out.println("This game has players subscribed!");
					if(this.selectedGame.getPlayers().size()<=3)
					{
						teamsBean.themesSource.removeAll(teamsBean.themesSource);
						teamsBean.themesSource.addAll(this.selectedGame.getPlayers());
						teamsBean.themesTarget.removeAll(teamsBean.themesTarget);
						teamsBean.setTeamOne(new Team("Team name"));
						teamsBean.setTeamTwo(new Team("Team name"));
						teamsBean.setPlayers( new DualListModel<>(teamsBean.themesSource, teamsBean.themesTarget));
					}
					else
					{
						teamsBean.setShowNextPrevious(true);
						List<Player> list=new ArrayList<Player>();
						
						for(Player player:this.selectedGame.getPlayers())
						{
							list.add(player);
						}
						List<List<Player>> listed=this.generateTeams(list);
						
						for(List<Player> l:listed)
						{
							System.out.println("Team");
							for(Player p:l)
							{
								System.out.println(p.toString());
							}
						}
						teamsBean.themesSource=listed.get(0);
						teamsBean.themesTarget=listed.get(1);
						teamsBean.setPlayers( new DualListModel<>(teamsBean.themesSource, teamsBean.themesTarget));
					}
				}
				else
				{
					System.out.println("This game has no players subscribed!");
					teamsBean.setExistTeams(false);
					System.out.println("This game has no game players");
					teamsBean.setExistTeams(false);
					teamsBean.themesSource.removeAll(teamsBean.themesSource);
					teamsBean.themesTarget.removeAll(teamsBean.themesTarget);
					teamsBean.setTeamOne(new Team("Team name"));
					teamsBean.setTeamTwo(new Team("Team name"));
					teamsBean.setPlayers( new DualListModel<>(teamsBean.themesSource, teamsBean.themesTarget));
				}
			}
	
//			//redirectionez catre TeamsView in functie de tipul de user
			if(firstBean.getCurrentPlayer().getType()==1)
			{
				try {
					context.redirect(context.getRequestContextPath() + "/faces/resources/teamsuser.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					context.redirect(context.getRequestContextPath() + "/faces/resources/teamsadmin.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
}