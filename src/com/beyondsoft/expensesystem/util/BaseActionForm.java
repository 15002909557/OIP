package com.beyondsoft.expensesystem.util;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import tools.bean.PageModel;

public class BaseActionForm extends ActionForm
{
	private static final long	serialVersionUID	= -9175625294562100189L;

	private PageModel			pageModel			= new PageModel();			// ��ҳ��Ϣ

	private ActionErrors		errors				= new ActionErrors();		// ��Ŵ������Ϣ

	private String				operPage;										// ����ҳ���ת��

	private String				strErrors;										// ���ƴ�����Ϣ

	private String				recid;

	private String				query_starttime;

	private String				query_endtime;

	private String				search;

	public ActionErrors getErrors()
	{
		return errors;
	}

	public void setErrors(ActionErrors errors)
	{
		this.errors = errors;
	}

	public String getStrErrors()
	{
		return strErrors;
	}

	public void setStrErrors(String strErrors)
	{
		this.strErrors = strErrors;
	}

	public String getOperPage()
	{
		return operPage;
	}

	public void setOperPage(String operPage)
	{
		this.operPage = operPage;
	}

	public String getRecid()
	{
		return recid;
	}

	public void setRecid(String recid)
	{
		this.recid = recid;
	}

	public PageModel getPageModel()
	{
		return pageModel;
	}

	public void setPageModel(PageModel pageModel)
	{
		this.pageModel = pageModel;
	}

	public String getSearch()
	{
		return search;
	}

	public void setSearch(String search)
	{
		this.search = search;
	}

	public String getQuery_endtime()
	{
		return query_endtime;
	}

	public void setQuery_endtime(String query_endtime)
	{
		this.query_endtime = query_endtime;
	}

	public String getQuery_starttime()
	{
		return query_starttime;
	}

	public void setQuery_starttime(String query_starttime)
	{
		this.query_starttime = query_starttime;
	}
}