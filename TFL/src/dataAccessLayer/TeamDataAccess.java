package dataAccessLayer;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import model.Game;
import model.Player;
import model.Team;

public class TeamDataAccess {

	public TeamDataAccess() {
		if(!EntityManagerHelper.em.getTransaction().isActive())
		  {
			EntityManagerHelper.em.getTransaction().begin();
		  }
	}
	
	public  static List<Team> listTeams() {
	    TypedQuery<Team> query =EntityManagerHelper.em.createQuery("SELECT t FROM Team t",Team.class);
		List<Team> result = new ArrayList<Team>();
		result = query.getResultList();
		for(Team p:result)
		{
			System.out.println(p.getName());
		}
		return result;
	}
	
	public Team updateTeam(Team teamToSave) {

		Team t= EntityManagerHelper.em.find(Team.class, teamToSave.getId());
		t.setName(teamToSave.getName());
		t.setPlayers(teamToSave.getPlayers());
		
		EntityManagerHelper.em.merge(teamToSave);
		EntityManagerHelper.em.getTransaction().commit();
		EntityManagerHelper.em.refresh(teamToSave);
		return teamToSave;
		
	}
	
	public Team createNewTeam(Team team)
	{
		EntityManagerHelper.em.persist(team);
		EntityManagerHelper.em.getTransaction().commit();
		EntityManagerHelper.em.refresh(team);
		return team;
	}
	
	public Team addNewPlayer(int playerId, int teamId) {
		Player player = EntityManagerHelper.em.find(Player.class, playerId);
		System.out.println(player.getId()+" "+player.getUsername());
		Team team= EntityManagerHelper.em.find(Team.class, teamId);
		System.out.println(team.getId()+" "+team.getName());
//		if(team.inThisTeam(player.getId()))
//		{
//			TeamPlayer tp=new TeamPlayer();
//			tp.setPlayer(player);
//			tp.setTeam(team);
//			
//			team.addTeamPlayer(tp);
//			EntityManagerHelper.em.persist(tp);
//			EntityManagerHelper.em.merge(team);
//			EntityManagerHelper.em.getTransaction().commit();
//		}
		return team;
	}
	
	public static void main(String[] args) {
		TeamDataAccess tda=new TeamDataAccess();
		Team t=new Team();
		t.setName("name");
		t.setScore(0);
		t.setWinner(false);
	
		Player player = EntityManagerHelper.em.find(Player.class, 5);
		t.addNewPlayer(player);
		Player playertwo = EntityManagerHelper.em.find(Player.class, 16);
		t.addNewPlayer(playertwo);
		t=tda.createNewTeam(t);
		System.out.println("Team id: "+t.getId());
		
	}
}
