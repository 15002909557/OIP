package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class Location implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int locationId = -1;
	private String locationName = "";
	
	public int getLocationId()
	{
		return locationId;
	}
	public void setLocationId(int locationId)
	{
		this.locationId = locationId;
	}
	public String getLocationName()
	{
		return locationName;
	}
	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}
}
