package views;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dataAccessLayer.PlayerDataAccess;
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
    
    public int getPlayedGames() {
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

	public String login() {

		PlayerDataAccess pda = new PlayerDataAccess();
		if ((this.username != null) && (this.password != null)) {
			currentPlayer = new Player();
			currentPlayer = pda.loginUser(this.username, this.password);

			if (currentPlayer != null) {
				this.playedGames = this.currentPlayer.getGamePlayers().size();
				this.winner = this.currentPlayer.getGameWinners().size();
				this.looser = this.currentPlayer.getGameLosers().size();
				if (currentPlayer.getType() == null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Warning!", "This username does not have rights!"));
					return "/index";
				} else {
					if (currentPlayer.getType() == 1) {
						return "/resources/user";
					} else {
						return "/resources/adminuser";
					}
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Incorrect username or password!"));
		return "/index";
	}
	
	  public void changePassword() {
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
    
    
}
