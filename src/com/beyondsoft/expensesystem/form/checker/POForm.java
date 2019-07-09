package com.beyondsoft.expensesystem.form.checker;

import com.beyondsoft.expensesystem.domain.checker.CaseDefect;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.util.BaseActionForm;
/*
 * 2011-11-18 by Dancy
 */

public class POForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;

	private CaseDefect casedefect = new CaseDefect();
	private ProjectOrder po = new ProjectOrder();
	
	public ProjectOrder getPo() {
		return po;
	}
	public void setPo(ProjectOrder po) {
		this.po = po;
	}
	public CaseDefect getCasedefect() {
		return casedefect;
	}
	public void setCasedefect(CaseDefect casedefect) {
		this.casedefect = casedefect;
	}

}
