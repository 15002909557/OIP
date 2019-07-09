package com.beyondsoft.expensesystem.util; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BaseDispatchAction extends DispatchAction {
    
	@SuppressWarnings("deprecation")
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws java.lang.Exception {

		ActionForward forward = null;
		BaseActionForm mainForm = (BaseActionForm)form;
		
		try {
		    if (request.getSession().isNew()) {
    			System.out.println("[IBS OIP]Page Timeout[forward] : timeout");
                return mapping.findForward("timeout");
		    }

			forward = super.execute(mapping, form, request, response);

		} catch (Exception e) {
			log.info("[IBS OIP]Page Exception[forward] : failed");
			log.error(e);
			e.printStackTrace();
			return mapping.findForward("failed");
		}
		
		if(!mainForm.getErrors().isEmpty()){
			this.saveErrors(request, mainForm.getErrors());
		}

		//可以指定返回错误的页面
		if (forward == null) {
			log.info("[IBS OIP]Page to Blank[forward] : bank");
			return mapping.findForward("bank");
		}

		log.info("[IBS OIP]Page to Normal[forward] : " + forward);
		return forward;
	}
}