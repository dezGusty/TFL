package views;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import dataAccessLayer.GameDataAccess;
import dataAccessLayer.PlayerDataAccess;
import model.Game;
import model.Player;

@ManagedBean(name = "loginView")
@SessionScoped
public class LoginView implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private int playedGames;
    private int winner;
    private int looser;
    private boolean value;
    private boolean click;

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public boolean getValue() {
        return value;
    }
 
    public void setValue(boolean value) {
        this.value = value;
    }
    
    public int getPlayedGames() {
		if(this.currentPlayer.getGames()!=null)
		{
			playedGames= this.currentPlayer.getGames().size();
		}
		return playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getLooser() {
		return looser;
	}

	public void setLooser(int looser) {
		this.looser = looser;
	}
	
	private String oldPass;
	private String newPass;
	private String confirmPass;
	
	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	private Player currentPlayer;
    
    @PostConstruct
    public void init() {
    	currentPlayer=new Player();
    }
    
    public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String getUsername() {
	return this.username;
    }

    public void setUsername(String value) {
	this.username = value;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String value) {
	this.password = value;
    }

	public void logout(ActionEvent event)  {

	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		System.out.println("Logout pressed!");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + "/faces/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenu(ActionEvent event)  {
		System.out.println("Show menu");
	   if(this.click)
	   {
		   this.click=false;
	   }
	   else
	   {
		   this.click=true;
	   }
	}
	
	public String login() throws IOException {
		PlayerDataAccess pda = new PlayerDataAccess();
		if ((this.username != null) && (this.password != null)) {
			currentPlayer = new Player();
			currentPlayer = pda.loginUser(this.username, this.password);
			if (currentPlayer != null) {
				ELContext elContext = FacesContext.getCurrentInstance().getELContext();
				NextGamesView firstBean = (NextGamesView) elContext.getELResolver().getValue(elContext, null, "nextGamesView");
				GameDataAccess gda=new GameDataAccess();
				firstBean.setGames(gda.listGamesForPlayer(this.currentPlayer));
				for(Game gg:firstBean.getGames())
				{
					System.out.println(gg.getDate());
				}
					if (currentPlayer.getType() == 1) {
						firstBean.setGames(gda.listGamesForPlayer(this.currentPlayer));
						ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
						
						context.redirect(context.getRequestContextPath() + "/faces/resources/userview.xhtml");
					} else {
						ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
						context.redirect(context.getRequestContextPath() + "/faces/resources/adminuser.xhtml");
					}
			}
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Incorrect username or password!"));
		return "/index";
	}
	
	public void redirectToGames(ActionEvent actionEvent)
	{
		this.click=false;
		System.out.println("Hello from redirect to games!");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		NextGamesView firstBean = (NextGamesView) elContext.getELResolver().getValue(elContext, null, "nextGamesView");
		GameDataAccess gda=new GameDataAccess();
		firstBean.setGames(gda.listNextGames());
		
		if(this.currentPlayer.getType()==1)
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/nextusergames.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/nextadmingames.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void redirectToPersonalDates(ActionEvent actionEvent)
	{
		this.click=false;
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
				if(this.currentPlayer.getGames()!=null)
				{
					this.playedGames=this.currentPlayer.GetTotalPlayedGames();
				}
				if(this.currentPlayer.getTeams()!=null)
				{
					this.winner=this.currentPlayer.GetGames(true);
					this.looser=this.currentPlayer.GetGames(false);
				}
			context.redirect(context.getRequestContextPath() + "/faces/resources/viewpersonaldates.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void redirectToChangePass(ActionEvent actionEvent)
	{
		this.click=false;
		this.oldPass="";
		this.newPass="";
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + "/faces/resources/changepassword.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void redirectToCharts(ActionEvent actionEvent)
	{
		this.click=false;
		System.out.println("Hello from redirect to charts!");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();	
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		ChartView firstBean = (ChartView) elContext.getELResolver().getValue(elContext, null, "chartView");
		
		firstBean.addPlayerToChart(this.currentPlayer);
		firstBean.createLineModels();
		
		if(this.currentPlayer.getType()==1)
		{
			
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/userchart.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/adminchart.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void redirectToPlayers(ActionEvent actionEvent)
	{
		this.click=false;
		System.out.println("Hello from redirect to players!");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();		
		if(this.currentPlayer.getType()==1)
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/viewplayers.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/adminplayersview.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void redirectToHistory(ActionEvent actionEvent)
	{
		this.click=false;
		System.out.println("Hello from redirect to history!");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		NextGamesView firstBean = (NextGamesView) elContext.getELResolver().getValue(elContext, null, "nextGamesView");
		GameDataAccess gda=new GameDataAccess();
		firstBean.setGames(gda.listPreviousGames());
		System.out.println(gda.listPreviousGames());
		System.out.println("Done");
		
		if(this.currentPlayer.getType()==1)
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/historyuser.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				context.redirect(context.getRequestContextPath() + "/faces/resources/historyadmin.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	  public void changePassword() {
		  this.click=false;
		  System.out.println("Old pass"+this.oldPass);
		  System.out.println("new pass"+this.newPass);
		  System.out.println("Confimr new pass"+this.confirmPass);
		  if(this.oldPass.compareTo(this.currentPlayer.getPassword())==0)
		  {
			  System.out.println("Old password is correct!");
			  if(this.newPass.compareTo(this.confirmPass)==0)
			  {
				  PlayerDataAccess pda=new PlayerDataAccess();
				  pda.changePasswordForPlayer(this.currentPlayer.getId(), this.newPass);
			  }
		  }
		  else
		  {
			  System.out.println("Old password does not match!");
		  }
	}
	  
	  public void changeAvailability() {
		    this.click=false;
	        String summary = this.currentPlayer.getAvailable() ? "Available" : "Unavailable";
	        System.out.println("Change availability to "+this.currentPlayer.getAvailable());
			PlayerDataAccess playerAccess=new PlayerDataAccess();
			 playerAccess.changeAvailable(this.currentPlayer);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
	    }  
}
