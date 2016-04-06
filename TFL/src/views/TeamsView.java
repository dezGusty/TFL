package views;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import dataAccessLayer.PlayerDataAccess;
import dataAccessLayer.TeamDataAccess;
import dataAccessLayer.TeamGenerator;
import dataAccessLayer.TeamPlayerDataAccess;

import model.Player;
import model.Team;

@ManagedBean(name = "teamsView")
@ApplicationScoped
public class TeamsView implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{playerDataAccess}")
	private PlayerDataAccess service;

	private boolean existTeams;

	public boolean isExistTeams() {
		return existTeams;
	}

	public Map<String, List<List<Player>>> map = new HashMap<String, List<List<Player>>>();
	
	public void setExistTeams(boolean existTeams) {
		this.existTeams = existTeams;
	}

	public List<Player> themesSource = new ArrayList<Player>();

	public List<Player> themesTarget = new ArrayList<Player>();

	private DualListModel<Player> players;

	private Team teamOne;

	private Team teamTwo;

	public Team getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(Team teamOne) {
		this.teamOne = teamOne;
	}

	public Team getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(Team teamTwo) {
		this.teamTwo = teamTwo;
	}

	@PostConstruct
	public void init() {
		if(players==null)
		{
			players=new DualListModel<Player>();
		}	
	}

	public PlayerDataAccess getService() {
		return service;
	}

	public void setService(PlayerDataAccess service) {
		this.service = service;
	}

	public DualListModel<Player> getPlayers() {
		return players;
	}

	public void setPlayers(DualListModel<Player> themes) {
		this.players = themes;
	}

	public void onTransfer(TransferEvent event) {
		
		 System.out.println("Source:");
		this.themesSource = this.players.getSource();
		
		 for(Player p: this.themesSource)
		 {
		 System.out.println(p.getUsername());
		 }

		System.out.println("Target:");
		this.themesTarget = this.players.getTarget();
		 for(Player p: this.themesTarget)
		 {
		 System.out.println(p.getUsername());
		 }

		// FacesMessage msg = new FacesMessage();
		// msg.setSeverity(FacesMessage.SEVERITY_INFO);
		// msg.setSummary("Items Transferred");
		// msg.setDetail(builder.toString());
		//
		// FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent event) {
		//FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		//FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		//FacesContext context = FacesContext.getCurrentInstance();
		// context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "List Reordered", null));
	}

	 public int indexOfMap=0;
	 

	 //nu sterge
	public void nextTeam() {

	}

	 public void getNextTeam(ActionEvent actionEvent) {
			System.out.println("Next Team");
			System.out.println("Index of map:"+indexOfMap);
			//System.out.println(TeamGenerator.map.size());
			System.out.println("Inex to comapre: "+(TeamGenerator.map.size()-1));
			if(indexOfMap==(TeamGenerator.map.size()-1))
			{
				indexOfMap=0;
				System.out.println("new Index of map:"+indexOfMap);
			}
			else
			{
				indexOfMap=indexOfMap+1;
				System.out.println("new Index of map:"+indexOfMap);
			}
			Object key = TeamGenerator.map.keySet().toArray(new Object[TeamGenerator.map.size()])[indexOfMap];
			
			List<Player> allPlayers=new ArrayList<Player>();
			allPlayers.addAll(themesSource);
			allPlayers.addAll(themesTarget);
			
			List<Player> firstList = TeamGenerator.map.get(key);
			System.out.println("First  list:");
			for(Player p:firstList)
			{
				System.out.println(p.getUsername());
			}
			
			
			this.themesSource=firstList;
			
			
			List<Player> secondList = new ArrayList<Player>();
			
			//poate fi scoasa intr-o alta metoda in care dintr-o lista de jucatori elimin alta lista de jucatori
			boolean existsInList=false;
			for(Player p:allPlayers)
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
			this.themesTarget=secondList;
			
			System.out.println("SecondList list:");
			
			for(Player p:secondList)
			{
				System.out.println(p.getUsername());
			}
			
			players=new DualListModel<>(this.themesSource,this.themesTarget);
	    }
	 
	 public void getPreviousTeam(ActionEvent actionEvent) {
			System.out.println("Previous Team");
			System.out.println("Index of map:"+indexOfMap);
			//System.out.println(TeamGenerator.map.size());
			System.out.println("Inex to comapre: "+(TeamGenerator.map.size()-1));
			if(indexOfMap==0)
			{
				indexOfMap=9;
				System.out.println("new Index of map:"+indexOfMap);
			}
			else
			{
				indexOfMap=indexOfMap-1;
				System.out.println("new Index of map:"+indexOfMap);
			}
			Object key = TeamGenerator.map.keySet().toArray(new Object[TeamGenerator.map.size()])[indexOfMap];
			
			List<Player> allPlayers=new ArrayList<Player>();
			allPlayers.addAll(themesSource);
			allPlayers.addAll(themesTarget);
			
			List<Player> firstList = TeamGenerator.map.get(key);
			System.out.println("First  list:");
			for(Player p:firstList)
			{
				System.out.println(p.getUsername());
			}
			
			
			this.themesSource=firstList;
			
			
			List<Player> secondList = new ArrayList<Player>();
			
			//poate fi scoasa intr-o alta metoda in care dintr-o lista de jucatori elimin alta lista de jucatori
			boolean existsInList=false;
			for(Player p:allPlayers)
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
			this.themesTarget=secondList;
			
			System.out.println("SecondList list:");
			
			for(Player p:secondList)
			{
				System.out.println(p.getUsername());
			}
			
			players=new DualListModel<>(this.themesSource,this.themesTarget);
	    }
	 
	public void saveTeams() {
		
		System.out.println("Hello from save teams!");
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
     	NextGamesView firstBean = (NextGamesView) elContext.getELResolver().getValue(elContext, null, "nextGamesView");
     	TeamDataAccess tda=new TeamDataAccess();
     	Team a=tda.createNewTeam("firstTeam", firstBean.getSelectedGame());
     	System.out.println(a.getId());
     	tda=new TeamDataAccess();
     	Team b=tda.createNewTeam("secondTeam", firstBean.getSelectedGame());
     	System.out.println(b.getId());
     	
     	System.out.println("Done adding teams!");
     	System.out.println("Players to add:");
     	 System.out.println("Source:");
 		 for(Player p: this.themesSource)
 		 {
 			 TeamPlayerDataAccess tpda=new TeamPlayerDataAccess();
 			 tpda.createNewTeamPlayer(a.getId(), p.getId());
 			 System.out.println(p.getUsername());
 		 }

 		System.out.println("Target:");
 		 for(Player p: this.themesTarget)
 		 {
 			 TeamPlayerDataAccess tpda=new TeamPlayerDataAccess();
			 tpda.createNewTeamPlayer(b.getId(), p.getId());
			 System.out.println(p.getUsername());
 		 }
	}
		
	
	public void backToHistory() {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		LoginView firstBean = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if(firstBean.getCurrentPlayer().getType()==1)
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/userview.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/adminuser.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
