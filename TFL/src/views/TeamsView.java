package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
 
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import dataAccessLayer.PlayerDataAccess;
import model.Player;
import model.Team;

@ManagedBean(name = "teamsView")
@ViewScoped
public class TeamsView implements Serializable{

	     
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@ManagedProperty("#{playerDataAccess}")
	    private PlayerDataAccess service;

	    private DualListModel<Player> players;
	     
	    private Team teamOne=new Team();
	    
	    private Team teamTwo=new Team();
	    
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

	    	List<Player> themesSource=new ArrayList<Player>();
	       // iau lista de jucatori List<Player> themesSource = service.;
	        List<Player> themesTarget = new ArrayList<Player>();
	        players=new DualListModel<>(themesSource, themesTarget);
	        teamOne.setName("team");
	        teamTwo.setName("fds");
	         
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
	        StringBuilder builder = new StringBuilder();
	        for(Object item : event.getItems()) {
	            builder.append(((Player) item).getUsername()).append("<br />");
	        }
	         
	        FacesMessage msg = new FacesMessage();
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        msg.setSummary("Items Transferred");
	        msg.setDetail(builder.toString());
	         
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    } 
	 
	    public void onSelect(SelectEvent event) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	    }
	     
	    public void onUnselect(UnselectEvent event) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	    }
	     
	    public void onReorder() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	    } 
	
}