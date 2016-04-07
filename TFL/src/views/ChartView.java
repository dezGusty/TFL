package views;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;

import model.Player;
import model.PlayerRating;


@ManagedBean(name = "chartView")
@SessionScoped
public class ChartView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public LineChartModel getLineModel2() {
		return lineModel;
	}
	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel = lineModel2;
	}
	
	private List<Player> players;

    private LineChartModel lineModel;
    
    @PostConstruct
    public void init() {
        this.players=new ArrayList<Player>();
    	createLineModels();
    } 
    
    public void addPlayerToChart(Player player)
    {
    	this.players.add(player);
    }
    
    public void createLineModels() {      
    	
        lineModel = initCategoryModel();
        lineModel.setTitle("Rating chart");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        
        //setez axa X sa fie de tip Date
        DateAxis axis = new DateAxis("Dates");
        lineModel.getAxes().put(AxisType.X, axis);
        
        axis.setTickFormat("%b %#d, %y");
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Rating");
//        if(this.players.size()!=0)
//        {
//        	System.out.println("There are players");
//        }
//        else
//        {
//        	System.out.println("There are no players");
//        }
    }
    
    private LineChartModel initCategoryModel() {
    	
        LineChartModel model = new LineChartModel();
 
        for(Player p:this.players)
        {
        	System.out.println(p.getUsername());
        	ChartSeries playerLineChart = new ChartSeries();
        	playerLineChart.setLabel(p.getUsername());
        	if(!p.getPlayerRatings().isEmpty())
        	{
        		//System.out.println(p.getUsername()+"has ratings");
        		for(PlayerRating pl:p.getPlayerRatings())
            	{
        			//System.out.println(pl.getDate().toString());
        			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            		playerLineChart.set(sdf.format(pl.getDate()), pl.getRating());
            	}
            	model.addSeries(playerLineChart);
            	//System.out.println(p.getUsername());
        	}
        	else
        	{
        		System.out.println("This player has no ratings!");
        	}
        } 
        
        return model;
    }
	
}
