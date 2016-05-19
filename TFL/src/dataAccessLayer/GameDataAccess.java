package dataAccessLayer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import helpers.DatabaseConnection;
import model.Game;
import model.Player;
import model.Team;

@ManagedBean(name = "gameDataAccess")
@SessionScoped
public class GameDataAccess implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public static Game GetGame(int gameId)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Game game=em.find(Game.class, gameId);
		em.close();
		return game;
	}
	
	public static List<Game> listPreviousGames() {
		System.out.println("List previous games!");
		EntityManager em = DatabaseConnection.GetConnection();
		TypedQuery<Game> query =em.createQuery("SELECT g FROM Game g where g.date <= current_date", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		em.close();
		return result;
	}

	public List<Game> listGames() {
		EntityManager em = DatabaseConnection.GetConnection();
		TypedQuery<Game> query =em.createQuery("SELECT g FROM Game g", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		em.close();
		return result;
	}

	public static List<Game> listNextGames() {
		EntityManager em = DatabaseConnection.GetConnection();
		TypedQuery<Game> query =em.createQuery("SELECT g FROM Game g where g.date > current_date and g.archive = false", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		em.close();
		return result;
	}
	
	public static Game addToArchive(int gameId)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Game gameToArchive=em.find(Game.class,gameId);	
		if(gameToArchive !=null)
		{
			gameToArchive.setArchive(true);
			em.getTransaction().commit();
			em.refresh(gameToArchive);
		}
		em.close();
		return gameToArchive;
	}
	
	public static Game SetDiff(int gameId, int difference)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Game game=em.find(Game.class, gameId);
		if(game!=null)
		{
			try
			{
				game.setDifference(difference);
				em.getTransaction().commit();
				em.refresh(game);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}	
		em.close();
		return game;
	}
	
	public static Game setDifference(int gameId, int difference,Team firstTeam, Team secondTeam) {
		EntityManager em = DatabaseConnection.GetConnection();
		Game g=em.find(Game.class, gameId);
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

			em.persist(firstTeam);
			em.persist(secondTeam);
			em.getTransaction().commit();
			
			em.refresh(firstTeam);
			em.refresh(secondTeam);
			System.out.println("First team refreshed id:"+firstTeam.getId());
			System.out.println("Second team refreshed id:"+secondTeam.getId());
			
			em.persist(g);
			em.getTransaction().commit();
			em.refresh(g);
			em.close();
			return g;
		}
		return null;
	}
	
	public static Game UpdateTeams(Game game)
	{		
 		TeamDataAccess.UpdateTeam(game.getTeam1());
		TeamDataAccess.UpdateTeam(game.getTeam2());
		return game;
	}
	
	public static Game AddTeams(int gameid, int firstTeamId,int secondTeamId)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Game g=em.find(Game.class, gameid);		
		Team a=em.find(Team.class, firstTeamId);
		Team b=em.find(Team.class, secondTeamId);
		
 		g.setTeam1(a);
		g.setTeam2(b);
		
		em.merge(g);
		em.getTransaction().commit();
		em.refresh(g);
		em.close();
		return g;
	}
	
	public static Team addTeamToGame(Team t,int  gId)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		Game g=em.find(Game.class, gId);
		Team team=em.find(Team.class, t.getId());
		//g.addTeam(team);
		em.persist(g);
		em.getTransaction().commit();
		em.refresh(g);
		em.close();
		return team;
	}
	
    public  static List<Game> listGamesForPlayer(Player player) {
    	EntityManager em = DatabaseConnection.GetConnection();
		Player p=em.find(Player.class, player.getId());
		List<Game> result = new ArrayList<Game>();
		
	    Date currentDate=new Date();
		currentDate=Calendar.getInstance().getTime();	
		if(p.getGames()!=null)
		{
			for(Game g: p.getGames())
			{
				if(g.getDate().after(currentDate) && (g.getArchive()==false))
				{
					result.add(g);
				}
			}
		}
		em.close();
		return result;
	}
	
	public static Player PlayGame(int gameId, int playerId) {		
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		
		Player play =em.find(Player.class,playerId);
		Game findGame=em.find(Game.class, gameId);	
		
		try
		{
			findGame.getPlayers().add(play);
			em.getTransaction().commit();
			em.refresh(findGame);
			em.refresh(play);
			em.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return play;
	}

	public static Game addNewGame(String date)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		try
		{
			Game g=new Game();
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
			Date datee = dt.parse(date); 
			g.setDate(datee);
			g.setDifference(0);
			g.setArchive(false);
			em.persist(g);
			em.getTransaction().commit();
			em.close();
			return g;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;	
	}
}