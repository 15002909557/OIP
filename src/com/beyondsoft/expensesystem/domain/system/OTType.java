package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class OTType implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int OTTypeId = -1;
	private String OTTpeName = "";
	
	public int getOTTypeId()
	{
		return OTTypeId;
	}
	public void setOTTypeId(int oTTypeId)
	{
		OTTypeId = oTTypeId;
	}
	public String getOTTpeName()
	{
		return OTTpeName;
	}
	public void setOTTpeName(String oTTpeName)
	{
		OTTpeName = oTTpeName;
	}
}
