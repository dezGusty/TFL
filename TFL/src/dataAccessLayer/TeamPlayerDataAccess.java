package dataAccessLayer;

import model.Game;
import model.Player;
import model.Team;
import model.TeamPlayer;

public class TeamPlayerDataAccess {

	public TeamPlayerDataAccess() {
		if(!EntityManagerHelper.em.getTransaction().isActive())
		  {
			EntityManagerHelper.em.getTransaction().begin();
		  }
	}
	
	public static boolean createNewTeamPlayer(Team team,Player player) {
		try {
			TeamPlayer tp=new TeamPlayer();
			Player play = EntityManagerHelper.em.find(Player.class, player.getId());
			Team teeam=EntityManagerHelper.em.find(Team.class,team.getId());
			System.out.println("Found team:"+teeam.getId()+" and player"+play.getUsername()+" with id "+play.getId());
			tp.setTeam(teeam);
			tp.setPlayer(play);
			EntityManagerHelper.em.persist(tp);
			EntityManagerHelper.em.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
	
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		GameDataAccess gda=new GameDataAccess();	
		Game g=new Game();
		g=gda.listGames().get(0);
		 
		PlayerDataAccess pda=new PlayerDataAccess();
		Player p=new Player();
		p=pda.listPlayers().get(0);
		
		createNewTeamPlayer(TeamDataAccess.listTeams().get(0),p);
	}
}
