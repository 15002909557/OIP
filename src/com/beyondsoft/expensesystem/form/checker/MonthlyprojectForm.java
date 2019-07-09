package com.beyondsoft.expensesystem.form.checker;

import com.beyondsoft.expensesystem.util.BaseActionForm;


public class MonthlyprojectForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private int locationid = -1;
	
	public int getLocationid() {
		return locationid;
	}
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

}