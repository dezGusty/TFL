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
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import dataAccessLayer.PlayerDataAccess;
import dataAccessLayer.PlayerRatingAccess;
import model.Player;
import model.PlayerRating;

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
	    
	    private LineChartModel dateModel;
	    
	    @PostConstruct
	    public void init() { 
	    	
	        service=new PlayerDataAccess();
	        List<Player> themesSource = service.listPlayers();
	        List<Player> themesTarget = new ArrayList<Player>();
	         
	        players = new DualListModel<Player>(themesSource, themesTarget);
	        createDateModel();
	         
	    }

	    public LineChartModel getDateModel() {
	        return dateModel;
	    }
	    
	    private void createDateModel() {
	        dateModel = new LineChartModel();
	        if(selectedPlayers.size()!=0)
	        {
	        	System.out.println(selectedPlayers.size());
	        	System.out.println("There are selected players!");
	        	System.out.println("List of selected players:");
	        	PlayerRatingAccess pla=new PlayerRatingAccess();


	        	 for(Player p: selectedPlayers)
 	           {
	        		 List<PlayerRating> list=new ArrayList<PlayerRating>();
	        		 list=pla.getPlayerRatings(p);
	        		 if(list.size()>1)
	        		 {
	        			 LineChartSeries playerLine=new LineChartSeries();
	        			 playerLine.setLabel(p.getUsername());
	        			 for(PlayerRating pl: pla.getPlayerRatings(p))
		        		 {
	        				 System.out.println("Data pentru chart:"+pl.getDate()+"rating pentru chart:"+pl.getRating());
	        				 playerLine.set(pl.getDate(), pl.getRating());
		        		 }
	        			 dateModel.addSeries(playerLine);
	        		 }
	        		 
	        		 //creez o linie din chart
//	        		 LineChartSeries playerLine=new LineChartSeries();
//	        		 series1.setLabel(p.getUsername());
//	        		  
////	        		 for(PlayerRating pl: pla.getPlayerRatings(p))
////	        		 {
////	        			 series1.set(pl.getDate(), pl.getRating());
////	        		 }
//	        		 
//	        		 dateModel.addSeries(playerLine);
	        		 //System.out.println(p.getId()+" "+p.getUsername());
	        		 
	        		//pla.getPlayerRatings(p);
	 	        }
	       }
	        else
	        {
	        	System.out.println("There are no selected players!");
	        }
	        
//	        LineChartSeries series1 = new LineChartSeries();
//	        series1.setLabel("Series 1");
//	        series1.set("2014-01-01", 0.1);
//	       
//	        series1.set("2014-01-06", 0.2);
//	        series1.set("2014-01-12", 0.4);
//	        series1.set("2014-01-18", 0.2);
//	        series1.set("2014-01-24", 0.6);
//	        series1.set("2014-01-30", 0.8);
	 
//	        LineChartSeries series2 = new LineChartSeries();
//	        series2.setLabel("Series 2");
//	 
//	        series2.set("2014-01-01", 32);
//	        series2.set("2014-01-06", 73);
//	        series2.set("2014-01-12", 24);
//	        series2.set("2014-01-18", 12);
//	        series2.set("2014-01-24", 74);
//	        series2.set("2014-01-30", 62);
	 
	       // dateModel.addSeries(series1);
	       // dateModel.addSeries(series2);
	         
	        dateModel.setTitle("Rating Chart");
	        dateModel.setZoom(true);
	        dateModel.getAxis(AxisType.Y).setLabel("Values");
	        DateAxis axis = new DateAxis("Dates");
	        axis.setTickAngle(-50);
	        axis.setMax("2016-03-03");
	      
	        axis.setTickFormat("%b %#d, %y");
	         
	        dateModel.getAxes().put(AxisType.X, axis);
	        
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
	    	Player p=((Player)event.getObject());
	    	for(Player x:selectedPlayers)
			{
	    		if(x.getId()==p.getId())
	    		{
	    			selectedPlayers.remove(x);
	    			break;
	    		}
			}
//	    	for(Player x:selectedPlayers)
//			{
//				System.out.println(x.getUsername());
//			}
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
	    
	    
	    public void createChart()
	    {
	    	
	    }
	 
}