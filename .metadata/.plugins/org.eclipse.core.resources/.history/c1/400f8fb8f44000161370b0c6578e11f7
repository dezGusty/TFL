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

import dataAccessLayer.GameDataAccess;
import model.Player;

@ManagedBean(name = "waitingPlayers")
@SessionScoped
public class WaitingPlayers implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Player> players;
	private Player selectedPlayer;
	
	public List<Player> getPlayers() {
		return players;
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
	
	@PostConstruct
	public void init() {
		if(this.players==null)
		{
			this.players=new ArrayList<Player>();
		}
		this.selectedPlayer=new Player();
	}
	
	public void addPlayer(Player player)
	{	
		ELContext context = FacesContext.getCurrentInstance().getELContext();
		NextGamesView nextGamesBean = (NextGamesView) context.getELResolver().getValue(context, null, "nextGamesView");
		if(nextGamesBean.getSelectedGame().hasTeams())
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,null,"You can not add more players! The teams are already setted!"));
		}
		else
		{
			this.players.remove(player);
			GameDataAccess.PlayGame(nextGamesBean.getSelectedGame().getId(), player.getId());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,null,"Player "+player.getUsername()+" added succsessfully!" ));
		}
	}
}