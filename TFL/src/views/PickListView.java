package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import dataAccessLayer.PlayerDataAccess;
import model.Player;

@ManagedBean(name="pickListView")
public class PickListView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty("#{themeService}")
	    private PlayerDataAccess service;
	     
	    private DualListModel<Player> players;
	    private static List<Player> selectedPlayers=new ArrayList<Player>();
	    
	    @PostConstruct
	    public void init() { 
	    	
	        service=new PlayerDataAccess();
	        List<Player> themesSource = service.listPlayers();
	        List<Player> themesTarget = new ArrayList<Player>();
	         
	        players = new DualListModel<Player>(themesSource, themesTarget);
	         
	    }

		public void onTransfer(TransferEvent event) {
	        StringBuilder builder = new StringBuilder();
	        for(Object item : event.getItems()) {
	        	//System.out.println(((Player)item).getPassword());
	            builder.append(((Player) item).getUsername()).append("<br />");
	        }
	         
	        FacesMessage msg = new FacesMessage();
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        msg.setSummary("Items Transferred");
	        msg.setDetail(builder.toString());
	         
	        FacesContext.getCurrentInstance().addMessage(null, msg);
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

		public void setPlayers(DualListModel<Player> players) {
			this.players = players;
		}

		public void onSelect(SelectEvent event) {
			selectedPlayers.add(((Player)event.getObject()));
			System.out.println("Selected players are:");
			for(Player p:selectedPlayers)
			{
				System.out.println(p.getUsername());
			}
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	    }
	     
	    public void onUnselect(UnselectEvent event) {
	    	
	    	///nu merge scos
	    	Player p=((Player)event.getObject());
	    	selectedPlayers.remove(selectedPlayers.indexOf(p));
	    	selectedPlayers.remove(1);
	    	for(Player x:selectedPlayers)
			{
				System.out.println(x.getUsername());
			}
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	    }
	     
	    public void onReorder() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	    } 
	    
	    public void generateTeams()
	    {
	    	System.out.println("works!");
	    	for(Player p: players.getTarget())
	    	{
	    		System.out.println(p.getUsername());
	    	}
	    }
	 
}