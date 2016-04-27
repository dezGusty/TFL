package dataAccessLayer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.TypedQuery;

import model.Game;
import model.Player;
import model.Team;

@ManagedBean(name = "gameDataAccess")
@ApplicationScoped
public class GameDataAccess implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public GameDataAccess() {
		if (EntityManagerHelper.em.getTransaction().isActive() == false) {
			EntityManagerHelper.em.getTransaction().begin();
		}
	}

	public List<Game> listPreviousGames() {
		System.out.println("List previous games!");
		TypedQuery<Game> query =EntityManagerHelper.em.createQuery("SELECT g FROM Game g where g.date < current_date", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		return result;
	}

	public List<Game> listGames() {
		TypedQuery<Game> query =EntityManagerHelper.em.createQuery("SELECT g FROM Game g", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
			System.out.println("Game players:");
//			for (GamePlayer p : g.getGamePlayers()) {
//				System.out.println(p.getPlayer().getUsername());
//			}
//			for (GameLoser gl : g.getGameLosers()) {
//				System.out.println(gl.getPlayer().getUsername());
//			}
//			for (GameWinner gw : g.getGameWinners()) {
//				System.out.println(gw.getPlayer().getUsername());
//			}
		}
		return result;
	}

	
	public List<Game> listNextGames() {
		TypedQuery<Game> query = EntityManagerHelper.em.createQuery("SELECT g FROM Game g where g.date > current_date and g.archive = false", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		return result;
	}
	
	public Game updateGame(Game game)
	{
		Game gameToUpdate=EntityManagerHelper.em.find(Game.class,game.getId());
		
		if(gameToUpdate !=null)
		{
			EntityManagerHelper.em.merge(gameToUpdate);
			EntityManagerHelper.em.getTransaction().commit();
			EntityManagerHelper.em.refresh(gameToUpdate);
		}
		return gameToUpdate;
	}
	
	public Game setDifference(int gameId, int difference,Team firstTeam, Team secondTeam) {

		Game g=EntityManagerHelper.em.find(Game.class, gameId);
		System.out.println("First team:");
		for(Player p:firstTeam.getPlayers())
		{
			System.out.println(p.getUsername());
		}
		System.out.println("Second team:");
		for(Player p:secondTeam.getPlayers())
		{
			System.out.println(p.getUsername());
		}
		
		if(g !=null)
		{		
			g.setDifference(difference);
			g.setTeam1(firstTeam);
			g.setTeam2(secondTeam);

			EntityManagerHelper.em.persist(firstTeam);
			EntityManagerHelper.em.persist(secondTeam);
			EntityManagerHelper.em.getTransaction().commit();
			
			EntityManagerHelper.em.refresh(firstTeam);
			EntityManagerHelper.em.refresh(secondTeam);
			System.out.println("First team refreshed id:"+firstTeam.getId());
			System.out.println("Second team refreshed id:"+secondTeam.getId());
			
			EntityManagerHelper.em.persist(g);
			EntityManagerHelper.em.getTransaction().commit();
			EntityManagerHelper.em.refresh(g);
			return g;
		}
		return null;
	}
	
	public Team addTeamToGame(Team t,int  gId)
	{
		Game g=EntityManagerHelper.em.find(Game.class, gId);
		Team team=EntityManagerHelper.em.find(Team.class, t.getId());
		//g.addTeam(team);
		EntityManagerHelper.em.persist(g);
		EntityManagerHelper.em.getTransaction().commit();
		EntityManagerHelper.em.refresh(g);
		return team;
	}
	
    public  List<Game> listGamesForPlayer(Player player) {
		
		Player p=EntityManagerHelper.em.find(Player.class, player.getId());
		List<Game> result = new ArrayList<Game>();
		
	    Date currentDate=new Date();
		currentDate=Calendar.getInstance().getTime();	
		
		for(Game g: p.getGames())
		{
			if(g.getDate().after(currentDate) && (g.getArchive()==false))
			{
				result.add(g);
			}
		}
		return result;
	}
	
	public void playGame(int gameId, int playerId) {		
		
		Player play =EntityManagerHelper.em.find(Player.class,playerId);
		Game findGame=EntityManagerHelper.em.find(Game.class, gameId);	

		if(findGame.gameStatus(play))
		{
			System.out.println("Player "+play.getId()+" already playing game "+findGame.getId());
		}
		else
		{
			findGame.getPlayers().add(play);
			EntityManagerHelper.em.persist(findGame);
			EntityManagerHelper.em.getTransaction().commit();
			EntityManagerHelper.em.refresh(findGame);
			EntityManagerHelper.em.refresh(play);
			System.out.println("Player "+play.getUsername()+" is now playing game "+ findGame.dateToDisplay());	
		}
		
	}
	  
	public void addGameWinner(Game game, Player player) {		
		///Player play =EntityManagerHelper.em.find(Player.class, player.getId());
		//Game findGame=EntityManagerHelper.em.find(Game.class, game.getId());	
		//GameWinner gw=new GameWinner();
		
//		if(gw.isGameWinner(player, game)==false)
//		{
//			gw.setGame(findGame);
//			gw.setPlayer(play);	
//			gw.setId(4);
//			findGame.addGameWinner(gw);
//			play.addGameWinner(gw);
//
//			EntityManagerHelper.em.persist(gw);
//			EntityManagerHelper.em.persist(findGame);
//			EntityManagerHelper.em.persist(play);
//			EntityManagerHelper.em.getTransaction().commit();
//		}
	}
	
	public Game addNewGame(String date)
	{
		try
		{
			Game g=new Game();
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			Date datee = dt.parse(date); 
			g.setDate(datee);
			EntityManagerHelper.em.persist(g);
			EntityManagerHelper.em.getTransaction().commit();
			return g;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		GameDataAccess gda=new GameDataAccess();
		Game gameToUpdate=EntityManagerHelper.em.find(Game.class,4);

		gameToUpdate.setArchive(false);
		
		gameToUpdate=gda.updateGame(gameToUpdate);

	}
}
