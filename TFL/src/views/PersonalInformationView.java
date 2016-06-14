package views;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Player;

@ManagedBean(name = "personalInfo")
@SessionScoped
public class PersonalInformationView {
	public  int playedGames;
    private int winner;
    private int looser;
    
    public PersonalInformationView()
    {
    	this.playedGames=0;
    	this.winner=0;
    	this.looser=0;
    }
    
    public PersonalInformationView(Player player)
    {
    	if(player!=null)
    	{
    		if(player.getGames()!=null)
    		{
    			this.playedGames=player.GetTotalPlayedGames();
    		}
    		if(player.getTeams()!=null)
    		{
    			this.winner=player.GetGames(true);
    			this.looser=player.GetGames(false);
    		}
    	}
    }
    
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
}
