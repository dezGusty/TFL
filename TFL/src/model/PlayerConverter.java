package model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("playerConverter")
public class PlayerConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		System.out.println("Get as object!");
		String[] splitedValues=value.split("##");
		Player newPlayer=new Player();
		try
		{
			newPlayer.setId(Integer.parseInt(splitedValues[0]));
			newPlayer.setUsername(splitedValues[1]);
			newPlayer.setPassword(splitedValues[2]);
			if(Double.parseDouble(splitedValues[3])!=0)
			{
				newPlayer.setRating(Double.parseDouble(splitedValues[3]));
			}
			newPlayer.setAvailable(Boolean.parseBoolean(splitedValues[4]));
			if(Integer.parseInt(splitedValues[5])!=0)
			{
				newPlayer.setType(Integer.parseInt(splitedValues[5]));
			}
			if(splitedValues[6]!=null)
			{
				newPlayer.setPicture(splitedValues[6]);
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		System.out.println(newPlayer.toString());
		return newPlayer;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {

		return value.toString();
	}

}
