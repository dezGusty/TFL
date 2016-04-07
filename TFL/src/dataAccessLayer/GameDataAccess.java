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
		TypedQuery<Game> query = EntityManagerHelper.em.createQuery("SELECT g FROM Game g where g.date > current_date", Game.class);
		List<Game> result = new ArrayList<Game>();
		result = query.getResultList();

		for (Game g : result) {
			System.out.println("ID" + g.getId() + " Date:" + g.getDate() + " Difference" + g.getDifference());
		}
		return result;
	}

	public boolean removeGame(int gameId) {
		
		Game g=EntityManagerHelper.em.find(Game.class, gameId);
		if(g!=null)
		{			
			EntityManagerHelper.em.remove(g);
			System.out.println("Game found and removed");
			EntityManagerHelper.em.getTransaction().commit();
			return true;
		}
		else
		{
			System.out.println("Game not found");
		}
		return false;
	}
	
	public Game setDifference(int gameId, int difference) {

		Game g=EntityManagerHelper.em.find(Game.class, gameId);

		if(g !=null)
		{
			g.setDifference(difference);
			EntityManagerHelper.em.persist(g);
			EntityManagerHelper.em.getTransaction().commit();
			EntityManagerHelper.em.refresh(g);
			return g;
		}
		return null;
	}
	
	public  List<Game> listGamesForPlayer(Player player) {
		
		Player p=EntityManagerHelper.em.find(Player.class, player.getId());
		List<Game> result = new ArrayList<Game>();
		//result=p.getGames();
		//return result;
		
	    Date currentDate=new Date();
		currentDate=Calendar.getInstance().getTime();	
		
		for(Game g: p.getGames())
		{
			if(g.getDate().after(currentDate))
			{
				result.add(g);
			}
		}

		
		//System.out.println("Games seted!");
		return result;
	}
	
	public void playGame(Game game, Player player) {		
		Player play =EntityManagerHelper.em.find(Player.class, player.getId());
		Game findGame=EntityManagerHelper.em.find(Game.class, game.getId());	
		//GamePlayer gp=new GamePlayer();
		
//		if(gp.isPlayingGame(play, findGame)==false)
//		{
//			gp.setGame(findGame);
//			gp.setPlayer(player);		
//			game.addGamePlayer(gp);
//			player.addGamePlayer(gp);
//			EntityManagerHelper.em.persist(gp);
//			EntityManagerHelper.em.persist(findGame);
//			EntityManagerHelper.em.persist(play);
//			EntityManagerHelper.em.getTransaction().commit();
//		}
	}
	
	public void addGameWinner(Game game, Player player) {		
		Player play =EntityManagerHelper.em.find(Player.class, player.getId());
		Game findGame=EntityManagerHelper.em.find(Game.class, game.getId());	
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
	
	public static void main(String[] args) {
		GameDataAccess gda=new GameDataAccess();
		Player play =EntityManagerHelper.em.find(Player.class,12);
		Game findGame=EntityManagerHelper.em.find(Game.class, 13);
		gda.addGameWinner(findGame, play);
		//gda.addNewGame(null);
	}
	

	public List<Team> listGameTeams(Game game)
	{
		Game g=new Game();
		g=EntityManagerHelper.em.find(Game.class, game.getId());
		if(g.getTeams()!=null)
		{
			return g.getTeams();
		}
		return null;
	}
	
	public Game addNewGame(String date)
	{
		try
		{
			Game g=new Game();
			//String date_s = "2016-03-14"; 
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
}
