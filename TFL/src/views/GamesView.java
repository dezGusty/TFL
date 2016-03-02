package views;
import model.Game;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dataAccessLayer.GameDataAccess;


@ManagedBean(name = "gamesView")
@ViewScoped
public class GamesView implements Serializable{
	
    private static final long serialVersionUID = 1L;
    public List<Game> games;
    
    @ManagedProperty("#{gameDataAccess}")
    public GameDataAccess gamesData;
    
    @PostConstruct
    public void init() {
    	//GameDataAccess gda=new GameDataAccess();
    	//GameDataAccess gamesData;
    	gamesData=new GameDataAccess();
    	games=gamesData.listGames();
    	
      //  games = service.createCars(100);
    }
    
    public List<Game> getGames() {
        return games;
    }
    
    public void setCars(List<Game> game) {
        this.games=game;
    }
 
	public GameDataAccess getGamesData() {
		return gamesData;
	}

	public void setGamesData(GameDataAccess gamesData) {
		this.gamesData = gamesData;
	}
}
