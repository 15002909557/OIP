package com.beyondsoft.expensesystem.form.checker;

import com.beyondsoft.expensesystem.util.BaseActionForm;


public class InvoiceForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private int POID = -1;
	private String PoNumber = null;
	private int yearid = -1;
	private String year = null;
	
	public int getYearid() 
	{
		return yearid;
	}
	public void setYearid(int yearid) 
	{
		this.yearid = yearid;
	}
	public String getYear() 
	{
		return year;
	}
	public void setYear(String year) 
	{
		this.year = year;
	}
	public int getPOID() 
	{
		return POID;
	}
	public void setPOID(int poid) 
	{
		POID = poid;
	}
	public String getPoNumber() 
	{
		return PoNumber;
	}
	public void setPoNumber(String poNumber) 
	{
		PoNumber = poNumber;
	}
}