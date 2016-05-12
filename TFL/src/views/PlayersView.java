package views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dataAccessLayer.PlayerDataAccess;
import model.Player;

@ManagedBean(name = "playersView")
@SessionScoped
public class PlayersView implements Serializable{

	private static final long serialVersionUID = 1L;

	public List<Player> players;

    private Player selectedPlayer;

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}
	
	public void remove(Player player) {
		if(PlayerDataAccess.removePlayer(player.getId())==true)
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Player "+player.getUsername()+" successfully removed!"));
			players.remove(player);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "WARN!", "Could not remove player "+player.getUsername()));
		}			
	}
	
	public void  addPlayerToChart(Player player)
	{
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ChartView firstBean = (ChartView) elContext.getELResolver().getValue(elContext, null, "chartView");	
		if(firstBean.addPlayerToChart(player))
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO!", "Player "+player.getUsername()+" added to chart!"));
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "WARN!", "Player "+player.getUsername()+" already in chart!"));
		}

		System.out.println("Players from ChartView");
		for(Player p:firstBean.getPlayers())
		{
			System.out.println(p.toString());
		}
		//firstBean.createLineModels();
		//RedirectView.Redirect(login.getCurrentPlayer(),"/faces/resources/userchart.xhtml","/faces/resources/userchart.xhtml");
	}
	
	@PostConstruct
	public void init() {
		this.players=new ArrayList<Player>();
	    this.players=PlayerDataAccess.ListAllPlayers();
	}
}