package views;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import dataAccessLayer.PlayerDataAccess;
import model.Player;
	 
	@ManagedBean
	public class AutoCompleteView {
	    
	    public List<Player> selectedPlayers;
	    private List<Player> allPlayers;
	    
	    @PostConstruct
		public void init() {
			System.out.println("Hello from players init");
			this.selectedPlayers=new ArrayList<Player>();
			allPlayers=PlayerDataAccess.ListAllPlayers();
		}
	    
	    public List<Player> completeTheme(String query) {
	        List<Player> filteredPlayers = new ArrayList<Player>();
	         
	        for(Player p:allPlayers)
	        {
	        	//System.out.println(p.toString());
	        	if(p.getUsername().toLowerCase().startsWith(query))
	        	{
	        		filteredPlayers.add(p);
	        	}
	        }	     
	        for(Player p:filteredPlayers)
	        {
	        	System.out.println(p.toString());
	        }
	        return  filteredPlayers;
	    }
	     
	    public List<Player> getSelectedPlayers() {
			return selectedPlayers;
		}

		public void setSelectedPlayers(List<Player> selectedPlayers) {
			this.selectedPlayers = selectedPlayers;
		}

		public void onItemSelect(SelectEvent event) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selected", event.getObject().toString()));
	    }
}