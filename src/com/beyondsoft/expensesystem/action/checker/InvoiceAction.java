package com.beyondsoft.expensesystem.action.checker;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.checker.InvoiceDao;
import com.beyondsoft.expensesystem.domain.checker.ExpenseData;
import com.beyondsoft.expensesystem.domain.checker.Invoice;
import com.beyondsoft.expensesystem.domain.checker.Monthlyproject;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.checker.DataCheckerForm;
import com.beyondsoft.expensesystem.form.checker.InvoiceForm;
import com.beyondsoft.expensesystem.time.SendThread3;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;


@SuppressWarnings({"deprecation","unchecked"} )
public class InvoiceAction  extends BaseDispatchAction{
	
	
	
	/**
	 * enter into monthly expense
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * no used
	 */
	//将各个list都放在hash map矩阵中，而不放在实体对象中，更方便读和取以及过滤页面数据，因为是使用java自带的util包
/*	public ActionForward getSomeList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response)throws Exception 
	{ 
		
		InvoiceForm mainForm = (InvoiceForm) form;
		List<Map> polist = new ArrayList<Map>();
		List<Map> yearlist = new ArrayList<Map>();
		List<Map> monthlist = new ArrayList<Map>();
		List<Map> categorylist = new ArrayList<Map>();
		List<String> monthprojectlist = new ArrayList<String>();
		List<Map> clientlist = new ArrayList<Map>();
		List<Map> wbslist = new ArrayList<Map>();
		
		Connection conn = null;
		Statement stmt = null;
		try 
		{
			conn =  DataTools.getConnection(request);
			stmt =  conn.createStatement();
			polist = InvoiceDao.getInstance().seacherpn(conn);
			yearlist = InvoiceDao.getInstance().searchyr(conn);
			monthlist = InvoiceDao.getInstance().searchmt(conn);
			categorylist= InvoiceDao.getInstance().searchcg(conn);
			monthprojectlist = InvoiceDao.getInstance().searchmp(conn);
			clientlist = InvoiceDao.getInstance().searchct(conn);
			wbslist = InvoiceDao.getInstance().searchwbs(conn);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (null != conn)
			{
				conn.close();
			}
			if (null != stmt) 
			{
				stmt.close();
			}
		}
		
		request.getSession().setAttribute("polist", polist);
		request.getSession().setAttribute("yearlist", yearlist);
		request.getSession().setAttribute("monthlist", monthlist);
		request.getSession().setAttribute("categorylist", categorylist);
		request.getSession().setAttribute("monthprojectlist", monthprojectlist);
		request.getSession().setAttribute("clientlist", clientlist);
		request.getSession().setAttribute("wbslist", wbslist);
		
		return mapping.findForward(mainForm.getOperPage());
	}
*/	  
	
/**
 * search monthly expense by PO,添加了monthlyexpenselist的获取 FWJ on 2013-04-22
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward searchbyMonthlyEx(ActionMapping mapping,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response)throws Exception 
	{ 
		InvoiceForm mainForm = (InvoiceForm) form;
		
		String parentpage = (String) request.getParameter("parentpage");
	//	System.out.println("parentpage in the searchbyMonthlyEx webpage="+parentpage);
		String poidstr = (String) request.getParameter("POID");
		//添加了对polist界面中的poname的获取，这样可以默认勾选pofilter中的poname FWJ on 2013-04-22

		if(poidstr!=null&&poidstr.trim()!=""&&parentpage!=null&&(parentpage.equals("polist")||parentpage.equals("po_edit")))
		{
			String ponamestr = (String) request.getParameter("PONAME");
			String pofilters = ponamestr+",";
			request.getSession().setAttribute("pofilters", pofilters);
		}
		//如果filter项各值默认为空说明是从po页面跳转过来的，直接设置初始值为-1即可
		if(poidstr==null||poidstr=="")
		{
			poidstr = "-1";
		}
		
		//如果parentPage不为空需要更新session中的该值
		if(parentpage!=null)
		{
			request.getSession().setAttribute("parentpage", parentpage);
		}
		
		List<Invoice> plist = new ArrayList<Invoice>();
		List<Invoice> monthlyexpenselist = new ArrayList<Invoice>();
		
		List<Map> polist = new ArrayList<Map>();
		List<Map> yearlist = new ArrayList<Map>();
		List<Map> yearselect = new ArrayList<Map>();//FWJ 2013-04-08
		List<Map> monthlist = new ArrayList<Map>();
		List<Map> categorylist = new ArrayList<Map>();
		List<String> monthprojectlist = new ArrayList<String>();
		List<Map> clientlist = new ArrayList<Map>();
		List<Map> wbslist = new ArrayList<Map>();
		List<Map> payment_select = new ArrayList<Map>();//FWJ 2013-06-20
		
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn =  DataTools.getConnection(request);
			stmt =  conn.createStatement();
			
			polist = InvoiceDao.getInstance().seacherpn(conn);
			yearlist = InvoiceDao.getInstance().searchyr(conn,1);
			yearselect = InvoiceDao.getInstance().searchyr(conn,0);//FWJ 2013-04-08,用于增加Monthlyexpense的year选择
			monthlist = InvoiceDao.getInstance().searchmt(conn);
			categorylist= InvoiceDao.getInstance().searchcg(conn);
			monthprojectlist = InvoiceDao.getInstance().searchmp(conn);
			clientlist = InvoiceDao.getInstance().searchct(conn);
			wbslist = InvoiceDao.getInstance().searchwbs(conn);
			payment_select=InvoiceDao.getInstance().searchPayment(conn);
			//添加了对所有monthlyexpense的获取，在筛选的时候用到 FWJ on 2013-04-19
			monthlyexpenselist=InvoiceDao.getInstance().searchbyMonthlyEx(conn,-1);
			plist = InvoiceDao.getInstance().searchbyMonthlyEx(conn,Integer.parseInt(poidstr));

		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
			if (null != stmt) 
			{
				stmt.close();
			}
		}
		//FWJ 2013-05-07 Monthly Expense中筛选用
		List categoryfilter = new ArrayList();
		List clientfilter = new ArrayList();
		List wbsfilter = new ArrayList();
		this.getCategoryFilters(monthlyexpenselist, categoryfilter);
		this.getClientFilters(monthlyexpenselist, clientfilter);
		this.getWbsFilters(monthlyexpenselist, wbsfilter);
		request.getSession().setAttribute("categoryselect", categoryfilter);
		request.getSession().setAttribute("clientselect", clientfilter);
		request.getSession().setAttribute("wbsselect", wbsfilter);
		//FWJ 2013-05-08 增加或者编辑Monthly Expense的时候用
		request.getSession().setAttribute("yearselect", yearselect);
		
		request.getSession().setAttribute("poid", poidstr);
		request.getSession().setAttribute("polist", polist);
		request.getSession().setAttribute("yearlist", yearlist);
		request.getSession().setAttribute("monthlist", monthlist);
		request.getSession().setAttribute("categorylist", categorylist);
		request.getSession().setAttribute("monthprojectlist", monthprojectlist);
		request.getSession().setAttribute("clientlist", clientlist);
		request.getSession().setAttribute("wbslist", wbslist);
		request.getSession().setAttribute("payment_select", payment_select);
		request.getSession().setAttribute("expenselist", plist);
		request.getSession().setAttribute("list", plist);
		request.getSession().setAttribute("melist", monthlyexpenselist);
		
		int ItemNumber = 8;
		int TotalPage = 0;
		TotalPage = plist.size() / ItemNumber;
		if (plist.size() % ItemNumber > 0)
		{
			TotalPage++;
		}
		request.getSession().setAttribute("TotalPage", TotalPage);
		int page = 0;
		request.getSession().setAttribute("page", page);
	//	System.out.println("The page in the searchbyMonthlyEx function is:"+page);
		return turnPage(mapping, mainForm, request, response, ItemNumber, page);
	}

/**
 * remove a monthly expense record
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward DeleteInvoice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		int expenseId = Integer.parseInt(request.getParameter("Expenseid"));
		String poid = request.getParameter("POID");
		
		Connection conn = null;
		boolean result = false;
		try 
		{
			conn = DataTools.getConnection(request);
			result = InvoiceDao.getInstance().deleteInvoice(conn,expenseId,Integer.parseInt(poid));

			//remove monthly expense后应该更新对应的po used,po used发生变化，如果满足了发邮件的条件也要发邮件给pm
			if (result) 
			{
		//		System.out.println("to send email!");
				Thread thread=new SendThread3(Integer.parseInt(poid));
				thread.start();
			}
			
		} catch (Exception e) 
		{
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally 
		{
			if (conn != null) 
			{
				conn.close();
			}
		}

	//	this.getNewMonthlyExpense(mapping, form, request, response);
		this.getNewMonthlyExpense(request);
		return searchWithFilter(mapping, form, request, response);
	}
	
	
/**
 * to add a new monthly expense record
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward toInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		//这里拿到页面上已经选择好的po, year,month
		String poid = (String) request.getParameter("POID");
		
		String yearid = (String) request.getParameter("Year");
		
		String monthid = (String) request.getParameter("Month");
		
	//	System.out.println("poid in the toInsert = "+poid);
		
		request.setAttribute("POID", poid);
		request.setAttribute("yearid", yearid);
		request.setAttribute("monthid", monthid);
		request.setAttribute("oper", "new");
	
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 *  save a new monthly expense record
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 */
	public ActionForward addInvoice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		String invoicenumber = (String) request.getParameter("Invoicenumber");//invoice name
		//Added on 2013-03-25
		String project_t = (String) request.getParameter("project_text");
		//Added on 2013-06-20
		String payment_t = (String) request.getParameter("payment_text");
	//	System.out.println("payment_t in the add invoice="+payment_t);
		String yearid = (String) request.getParameter("Year");
		String monthid = (String) request.getParameter("Month");
		String categoryid = (String) request.getParameter("category");		
		String ponid = (String) request.getParameter("POID");	
		String clientid = (String) request.getParameter("client");	
		String wbsid = (String) request.getParameter("wbs");

		String cost = (String) request.getParameter("cost");
		cost=cost.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		//System.out.println("cost:"+cost);
		//页面上加了有效判断的话应该是不会为空的，否则add a record是执行不成功的

		String comments = (String) request.getParameter("comment");	
		//请紧挨着他的后面给出相应判断和处理
		if(comments.trim().equals("Input the comments here!"))
		{
			comments="";
		}
		//parentpage这里不需要再去更新了
		
		//这里从session取得month project list去对的
		List<String> monthprojectlist = (List<String>) request.getSession().getAttribute("monthprojectlist");
		List<Map> paymentlist = (List<Map>) request.getSession().getAttribute("payment_select");
		
		Invoice invoice_a = new Invoice();
		invoice_a.setYearid(Integer.parseInt(yearid));
		invoice_a.setMonthid(Integer.parseInt(monthid));
		invoice_a.setCategoryid(Integer.parseInt(categoryid));
		invoice_a.setPOid(Integer.parseInt(ponid));
		invoice_a.setClientid(Integer.parseInt(clientid));
		invoice_a.setWBSid(Integer.parseInt(wbsid));
		invoice_a.setCost(cost);
		invoice_a.setInvoiceNumber(invoicenumber);
		invoice_a.setMonthproject(project_t);
		invoice_a.setComment(comments);
		invoice_a.setPayment(payment_t);
		
		Connection conn = null;
		//result的返回值应该设3个 result=-2 month project名字已存在，result=0 monthly expense已存在 result=1 添加记录成功 ，result=-1添加记录不成功
		int result = -1;
		
		try
		{
			conn = DataTools.getConnection(request);
			boolean re = false;
			boolean project_exist = false;
			project_exist = InvoiceDao.getInstance().ifExistProject(conn, project_t.trim());
			
		//	System.out.println("project_exist="+project_exist);
			if(!project_exist)
			{
				monthprojectlist.add(project_t);
				re = InvoiceDao.getInstance().newMonthProject(conn, project_t);
			}
		//	System.out.println("a new monthproject name add result is:"+re);
			
			boolean re_2 = false;
			boolean payment_exist= false;
			String sql ="select * from payment where payment=?";
			payment_exist= InvoiceDao.getInstance().ifExistRecord(conn, payment_t.trim(), sql);
			if(payment_t.trim().equals(""))
			{
				payment_exist=true;
			}
			if(!payment_t.trim().equals("")&&!payment_exist){
				sql="insert into payment(payment) values(?)";
				System.out.println("Run?");
				re_2=InvoiceDao.getInstance().newRecord(conn, payment_t,sql);
				Map<String,String> map= new HashMap<String,String>();
				map.put("payment", payment_t);
				paymentlist.add(map);
			}
			
			boolean expense_exist = false;
			boolean re3 = false;
			expense_exist = InvoiceDao.getInstance().ifExist(conn, invoice_a, "add");
			if(!expense_exist)
			{
				re3 = InvoiceDao.getInstance().addInvoice(conn, invoice_a);
				if(re3)
				{
					result = 1;
					//这里判断如果满足发邮件条件，monthly expense也更新成功，则发送邮件，不需要去jsp页面再调用发邮件的方法 by dancy
					//创建多线程执行发邮件任务，不影响系统其它操作效率
					Thread thread = new SendThread3(Integer.parseInt(ponid));
					thread.start();
				}
				else
				{
					result = -1;
				}
			}
			else
			{
				result = 0;
			}
		//	System.out.println("add new invoice result in action="+result);

		}catch (Exception e) 
		{
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}  finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
		}
		
		request.getSession().setAttribute("po", ponid);
//		request.getSession().setAttribute("yr", yearid);
//		request.getSession().setAttribute("mt", monthid);
		request.getSession().setAttribute("monthprojectlist", monthprojectlist);
		request.getSession().setAttribute("payment_select", paymentlist);
		request.setAttribute("result", String.valueOf(result));
//		this.getNewMonthlyExpense(mapping, form, request, response);
		this.getNewMonthlyExpense(request);
		return mapping.findForward(mainForm.getOperPage());
	}
	

/**
 * to edit a monthly expense record
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		String expenseid = (String) request.getParameter("expenseid");
		String poid = (String) request.getParameter("POID");
//		String yearid = (String) request.getParameter("Year");
//		String monthid = (String) request.getParameter("Month");
		//添加了对copy的取值 FWJ 2013-04-18
		String operate = (String) request.getParameter("oper");

		Connection conn = null;
		Statement stmt = null;
		Invoice invoice = null;
		
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			invoice = InvoiceDao.getInstance().searchExpenseById(conn, Integer.parseInt(expenseid));
			
		}catch (Exception e) 
		{
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
			if (null != stmt) 
			{
				stmt.close();
			}
		}
		//增加了对copy的设置属性 FWJ 2013-04-18
		request.setAttribute("oper_new", operate);
		request.setAttribute("oper", "edit");
		request.setAttribute("invoice", invoice);
		request.setAttribute("POID", poid);
		return mapping.findForward(mainForm.getOperPage());

	}
	/**
	 *  save a monthly expense record after being edited 
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 * @author longzhe
	 */
	public ActionForward editInvoice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvoiceForm mainForm = (InvoiceForm) form;
		String expenseId = (String) request.getParameter("invoiceid");
		String invoicenumber = (String) request.getParameter("Invoicenumber");//invoice name
		//Added on 2013-03-25
		String project_t = (String) request.getParameter("project_text");		
		String payment_t = (String) request.getParameter("payment_text");	//FWJ 2013-06-20
		String yearid = (String) request.getParameter("Year");
		String monthid = (String) request.getParameter("Month");
		String categoryid = (String) request.getParameter("category");		
		String ponid = (String) request.getParameter("POID");	
		String clientid = (String) request.getParameter("client");	
		String wbsid = (String) request.getParameter("wbs");	
		
		String cost = (String) request.getParameter("cost");
		cost=cost.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		//页面上加了有效判断的话应该是不会为空的，否则add a record是执行不成功的

		String comments = (String) request.getParameter("comment");	
		//请紧挨着他的后面给出相应判断和处理
		if(comments.trim().equals("Input the comments here!"))
		{
			comments="";
		}
		//parentpage这里不需要再去更新了
		
		//这里从session取得month project list去对的
		List<String> monthprojectlist = (List<String>) request.getSession().getAttribute("monthprojectlist");
		List<Map> paymentlist = (List<Map>) request.getSession().getAttribute("payment_select");
		Invoice invoice_a = new Invoice();
		invoice_a.setMonthlyexpenseid(Integer.parseInt(expenseId));
		invoice_a.setYearid(Integer.parseInt(yearid));
		invoice_a.setMonthid(Integer.parseInt(monthid));
		invoice_a.setCategoryid(Integer.parseInt(categoryid));
		invoice_a.setPOid(Integer.parseInt(ponid));
		invoice_a.setClientid(Integer.parseInt(clientid));
		invoice_a.setWBSid(Integer.parseInt(wbsid));
		invoice_a.setCost(cost);
		invoice_a.setInvoiceNumber(invoicenumber);
		invoice_a.setMonthproject(project_t);
		invoice_a.setComment(comments);
		invoice_a.setPayment(payment_t);
		
		Connection conn = null;
		//result的返回值应该设3个 result=-2 month project名字已存在，result=0 monthly expense已存在 result=1 添加记录成功 ，result=-1添加记录不成功
		int result = -1;
		try
		{
			conn = DataTools.getConnection(request);
			boolean re = false;
			boolean project_exist = false;
			project_exist = InvoiceDao.getInstance().ifExistProject(conn, project_t.trim());
			
		//	System.out.println("project_exist="+project_exist);
			if(!project_exist)
			{
				monthprojectlist.add(project_t);
				re = InvoiceDao.getInstance().newMonthProject(conn, project_t);
		//		System.out.println("a new monthproject name add result is:"+re);
			}
			
			boolean re_2 = false;
			boolean payment_exist= false;
			String sql ="select * from payment where payment=?";
			payment_exist= InvoiceDao.getInstance().ifExistRecord(conn, payment_t.trim(), sql);
			if(payment_t.trim().equals("")){payment_exist=true;}
			if(!payment_t.trim().equals("")&&!payment_exist){
				sql="insert into payment(payment) values(?)";
				re_2=InvoiceDao.getInstance().newRecord(conn, payment_t,sql);
				Map<String,String> map= new HashMap<String,String>();
				map.put("payment", payment_t);
				paymentlist.add(map);
			}
			
			boolean expense_exist = false;
			boolean re3 = false;
			expense_exist = InvoiceDao.getInstance().ifExist(conn, invoice_a, "edit");
			
		//	System.out.println("expense_exist="+expense_exist);
			if(!expense_exist)
			{
				re3 = InvoiceDao.getInstance().editInvoice(conn, invoice_a);
				if(re3)
				{
					result = 1;
					//这里判断如果满足发邮件条件，monthly expense也更新成功，则发送邮件，不需要去jsp页面再调用发邮件的方法 by dancy
					//创建多线程执行发邮件任务，不影响系统其它操作效率
					Thread thread = new SendThread3(Integer.parseInt(ponid));
					thread.start();
				}
				else
				{
					result = -1;
				}
			}
			else
			{
				result = 0;
			}	
		//	System.out.println("add new invoice result in action="+result);
		}catch (Exception e) 
		{
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}  finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
		}

		//添加对monthproject的存储，如果添加新的project的时候，返回到页面的时候会用到。
		request.getSession().setAttribute("monthprojectlist", monthprojectlist);
		request.getSession().setAttribute("payment_select", paymentlist);
		request.setAttribute("result", String.valueOf(result));
//		this.getNewMonthlyExpense(mapping, form, request, response);
		this.getNewMonthlyExpense(request);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	
	public ActionForward turnPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,int ItemNumber, int page) 
	{
		InvoiceForm mainForm = (InvoiceForm) form;		
		List<Invoice> list = (List<Invoice>) request.getSession().getAttribute("list");
		List<Invoice> templist = new ArrayList<Invoice>();
		
		int maxpage = Integer.parseInt(request.getSession().getAttribute("TotalPage").toString());
		if (page >= maxpage)
		{	
			page = maxpage - 1;
		}
		else
		{	
			page = page-1;	
		//	page = page-0;
		}
		if (page < 0)
			page = 0;
		int firstitem = page * ItemNumber;
		int lastitem = (page + 1) * ItemNumber;
		if (lastitem > list.size())
		{
			lastitem = list.size();
		}

		for (int i = firstitem; i < lastitem; i++) 
		{
			templist.add(list.get(i));
		}
		if (page >= maxpage)
		{
			request.getSession().setAttribute("page", maxpage);
		}
		else
		{
			request.getSession().setAttribute("page", page);
		}
	//	System.out.println("The page in the trunpage function is:"+page);
		request.getSession().setAttribute("expenselist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}

	public ActionForward turnPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		int page = Integer.parseInt(request.getParameter("page").toString());
		this.turnPage(mapping, form, request, response, 8, page);
	//	System.out.println("The page in the turnPageJSP function is:"+page);
		request.getSession().setAttribute("page", page);
		return mapping.findForward(mainForm.getOperPage());
	}

	//添加了Monthly Expense中的filter的搜索功能 FWJ 2013-04-19
	public ActionForward searchWithFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	//	System.out.println("enter");
		InvoiceForm mainForm = (InvoiceForm) form;
		
		List<Invoice> melist = (List<Invoice>) request.getSession().getAttribute("melist");
		List<Invoice> templist = new ArrayList<Invoice>();
		
		for (int i = 0; i < melist.size(); i++)
			{
				templist.add(melist.get(i));
			}
	
//		String page = request.getParameter("currentpage");
		String page = request.getSession().getAttribute("page").toString();
		
		String back = request.getParameter("back");
		String yearfilters = null;
		String monthfilters = null;
		String pofilters = null;
		String categoryfilters = null;
		String clientfilters = null;
		String wbsfilters = null;
		
		if(back!=null&&back.equals("invoice_edit")){
			yearfilters=(String)request.getSession().getAttribute("yearfilters");
			monthfilters=(String)request.getSession().getAttribute("monthfilters");
			pofilters=(String)request.getSession().getAttribute("pofilters");
			categoryfilters=(String)request.getSession().getAttribute("categoryfilters");
			clientfilters=(String)request.getSession().getAttribute("clientfilters");
			wbsfilters=(String)request.getSession().getAttribute("wbsfilters");
		}
		else
		{
			yearfilters = request.getParameter("yearfilters");
			monthfilters = request.getParameter("monthfilters");
			pofilters = request.getParameter("pofilters");
			categoryfilters = request.getParameter("categoryfilters");
			clientfilters = request.getParameter("clientfilters");
			wbsfilters = request.getParameter("wbsfilters");
		}
	//	System.out.println("yearfilters that got from the page="+yearfilters);
		
		if(yearfilters!=null&&yearfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (yearfilters.indexOf(templist.get(i).getYear())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("yearfilters", yearfilters);
		
		
	//	System.out.println("monthfilters that got from the page="+monthfilters);
		
		if(monthfilters!=null&&monthfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (monthfilters.indexOf(templist.get(i).getMonth())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("monthfilters", monthfilters);
		
		
	//	System.out.println("pofilters that got from the page="+pofilters);

		if(pofilters!=null&&pofilters.indexOf(",")>0)
		{
			
			for (int i = 0; i < templist.size(); i++) 
			{

				if (templist.get(i).getPONum()!=null&&pofilters.indexOf(templist.get(i).getPONum().trim())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}

		//这个地方，pofilters的setAttribute会出错

		request.getSession().setAttribute("pofilters", pofilters);
		
		
	//	System.out.println("categoryfilters that got from the page="+categoryfilters);
		
		if(categoryfilters!=null&&categoryfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (categoryfilters.indexOf(templist.get(i).getCategory())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("categoryfilters", categoryfilters);
		
		
	//	System.out.println("clientfilters that got from the page="+clientfilters);
		
		if(clientfilters!=null&&clientfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (clientfilters.indexOf(templist.get(i).getClient())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("clientfilters", clientfilters);
		
		
	//	System.out.println("wbsfilters that got from the page="+wbsfilters);
		
		if(wbsfilters!=null&&wbsfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if(templist.get(i).getWBSNumber()==null){
					templist.get(i).setWBSNumber("null");//防止null异常 FWJ 2013-05-21
				}
				if (wbsfilters.indexOf(templist.get(i).getWBSNumber())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("wbsfilters", wbsfilters);
		
		int TotalPage = templist.size()/8;
		if(templist.size()%8>0)
		{
			TotalPage++;
		}
		request.getSession().setAttribute("TotalPage", TotalPage);
		request.getSession().setAttribute("list", templist);

		int p = 0;
		if(page!=null)
		{
			p = Integer.parseInt(page);
		}
		
//		request.getSession().removeAttribute("page");
		this.turnPage(mapping, form, request, response, 8, p);
	//	System.out.println("The page in the searchWithFilter function is:"+page);
		request.getSession().setAttribute("page", p);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	//在add和edit monthlyexpense的时候添加对monthly expense的更新
	public ActionForward getNewMonthlyExpense (HttpServletRequest request) throws Exception
	{
		Connection conn = DataTools.getConnection(request);
		List<Invoice> monthlyexpenselist = new ArrayList<Invoice>();
		try{
			monthlyexpenselist=InvoiceDao.getInstance().searchbyMonthlyEx(conn,-1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		List categoryfilter = new ArrayList();
		List clientfilter = new ArrayList();
		List wbsfilter = new ArrayList();
		this.getCategoryFilters(monthlyexpenselist, categoryfilter);
		this.getClientFilters(monthlyexpenselist, clientfilter);
		this.getWbsFilters(monthlyexpenselist, wbsfilter);
		
		request.getSession().setAttribute("categoryselect", categoryfilter);
		request.getSession().setAttribute("clientselect", clientfilter);
		request.getSession().setAttribute("wbsselect", wbsfilter);

		request.getSession().setAttribute("melist", monthlyexpenselist);
//		return mapping.findForward(mainForm.getOperPage());
		return null;
		}
	//FWJ 2013-05-07
	public List getCategoryFilters(List<Invoice> melist,List categoryfilter)
	{

		int result = 0;
		if(null != melist && melist.size()>0)
			categoryfilter.add(melist.get(0).getCategory());
			
		else
			return categoryfilter;
		
		for (int i = 1; i < melist.size(); i++)
		{

			for (int j = 0; j < categoryfilter.size(); j++)
			{
				result=0;
				if (melist.get(i).getCategory().equals(categoryfilter.get(j))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				categoryfilter.add(melist.get(i).getCategory());
		}

		return categoryfilter;
	}
	
	//FWJ 2013-05-07
	public List getClientFilters(List<Invoice> melist,List clientfilter)
	{

		int result = 0;
		if(null != melist && melist.size()>0)
			clientfilter.add(melist.get(0).getClient());
		else
			return clientfilter;
		
		for (int i = 1; i < melist.size(); i++)
		{

			for (int j = 0; j < clientfilter.size(); j++)
			{
				result=0;
				if (melist.get(i).getClient().equals(clientfilter.get(j))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				clientfilter.add(melist.get(i).getClient());
		}

		return clientfilter;
	}
	
	//FWJ 2013-05-07
	public List getWbsFilters(List<Invoice> melist,List wbsfilter)
	{

		int result = 0;
		if(null != melist && melist.size()>0){
			if(melist.get(0).getWBSNumber()==null){
				melist.get(0).setWBSNumber("null");
			}
			wbsfilter.add(melist.get(0).getWBSNumber());
		}
		else{
			return wbsfilter;
		}
		
		for (int i = 1; i < melist.size(); i++)
		{
			for (int j = 0; j < wbsfilter.size(); j++)
			{
				if(melist.get(i).getWBSNumber()==null){
					melist.get(i).setWBSNumber("null");//防止null FWJ 2013-05-21
				}
				result=0;	
				if (melist.get(i).getWBSNumber().equals(wbsfilter.get(j))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				wbsfilter.add(melist.get(i).getWBSNumber());
		}
		return wbsfilter;
	}
	/*----------------------------Monthly Project added by FWJ 2013-06-13----------------------------*/
	
	
	public ActionForward searchbyMonthlyproject(ActionMapping mapping,ActionForm form,
			HttpServletRequest request ,HttpServletResponse response)throws Exception 
	{ 
		InvoiceForm mainForm = (InvoiceForm) form;
		List<Monthlyproject> list = new ArrayList<Monthlyproject>();
		
		List monthlyprojectlist = new ArrayList();//MonthlyProject_List界面中过滤用的字段
		List locationlist = new ArrayList();//过滤用的字段
		List businesscategorylist = new ArrayList();//过滤用的字段
		
	//	List paymentlist = new ArrayList();//选择用的字段
		
		List<Map> location_select = new ArrayList<Map>();
		List<Map> monthlyproject_select = new ArrayList<Map>();
		List<Map> payment_select = new ArrayList<Map>();
		List<Map> businesscategory_select = new ArrayList<Map>();
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn =  DataTools.getConnection(request);
			stmt =  conn.createStatement();
			//每次进入Monthly Project的页面的时候，更新一下cost
			boolean result=InvoiceDao.getInstance().updateMonthlyCost(conn);
			//added by FWJ 2013-12-18
			boolean result2=InvoiceDao.getInstance().updateMonthlyCostInLatestMonth(conn);
	//		System.out.println("New Update about the cost:"+result);
			monthlyproject_select = InvoiceDao.getInstance().searchMonthProject(conn);
			location_select=InvoiceDao.getInstance().searchLocation(conn);
			payment_select=InvoiceDao.getInstance().searchPayment(conn);
			businesscategory_select=InvoiceDao.getInstance().searchBcategory(conn);
			list=InvoiceDao.getInstance().searchbyMonthlyproject(conn);

		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
			if (null != stmt) 
			{
				stmt.close();
			}
		}

		this.getMonthlyprojectFilters(list, monthlyprojectlist);
		this.getLocationFilters(list, locationlist);
		this.getBcategoryFilters(list, businesscategorylist);
	//	this.getPaymentFilters(list, paymentlist);
		
		request.getSession().setAttribute("monthlyprojectlist", monthlyprojectlist);
		request.getSession().setAttribute("monthlyproject_select", monthlyproject_select);
		request.getSession().setAttribute("locationlist", locationlist);
		request.getSession().setAttribute("location_select", location_select);
		request.getSession().setAttribute("businesscategorylist", businesscategorylist);
		request.getSession().setAttribute("businesscategory_select", businesscategory_select);
	//	request.getSession().setAttribute("paymentlist", paymentlist);
		request.getSession().setAttribute("payment_select", payment_select);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("templist", list);
		
		int ItemNumber = 8;
		int TotalPage = 0;
		TotalPage = list.size() / ItemNumber;
		if (list.size() % ItemNumber > 0)
		{
			TotalPage++;
		}
		request.getSession().setAttribute("TotalPage", TotalPage);
		int page = 0;
		request.getSession().setAttribute("page", page);
		return turnMPPage(mapping, mainForm, request, response, ItemNumber, page);
	}
	
	public ActionForward turnMPPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,int ItemNumber, int page) 
	{
		InvoiceForm mainForm = (InvoiceForm) form;		
		List<Monthlyproject> list = (List<Monthlyproject>) request.getSession().getAttribute("templist");
		List<Monthlyproject> templist = new ArrayList<Monthlyproject>();
		
		int maxpage = Integer.parseInt(request.getSession().getAttribute("TotalPage").toString());
		if (page >= maxpage)
		{	
			page = maxpage - 1;
		}
		else
		{	
			page = page-1;	
		}
		if (page < 0)
			page = 0;
		int firstitem = page * ItemNumber;
		int lastitem = (page + 1) * ItemNumber;
		if (lastitem > list.size())
		{
			lastitem = list.size();
		}

		for (int i = firstitem; i < lastitem; i++) 
		{
			templist.add(list.get(i));
		}
		if (page >= maxpage)
		{
			request.getSession().setAttribute("page", maxpage);
		}
		else
		{
			request.getSession().setAttribute("page", page);
		}
	//	System.out.println("The page in the trunMPpage function is:"+page);
		request.getSession().setAttribute("showlist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	
	public List getMonthlyprojectFilters(List<Monthlyproject> list,List mpfilter)
	{

		int result = 0;
		if(null != list && list.size()>0)
			mpfilter.add(list.get(0).getMonthproject());
		else
			return mpfilter;
		
		for (int i = 1; i < list.size(); i++)
		{

			for (int j = 0; j < mpfilter.size(); j++)
			{
				result=0;
				if (list.get(i).getMonthproject()!=null&&list.get(i).getMonthproject().equals(mpfilter.get(j))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				mpfilter.add(list.get(i).getMonthproject());
		}

		return mpfilter;
	}
	
	public List getLocationFilters(List<Monthlyproject> list,List ltfilter)
	{

		int result = 0;
		if(null != list && list.size()>0)
			ltfilter.add(list.get(0).getLocation());
		else
			return ltfilter;
		
		for (int i = 1; i < list.size(); i++)
		{

			for (int j = 0; j < ltfilter.size(); j++)
			{
				result=0;
				if (list.get(i).getLocation()==null||(ltfilter.get(j)!=null&&list.get(i).getLocation().equals(ltfilter.get(j)))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				ltfilter.add(list.get(i).getLocation());
		}
		return ltfilter;
	}
	public List getBcategoryFilters(List<Monthlyproject> list,List bcfilter)
	{
		

		int result = 0;
		if(null != list && list.size()>0)
			bcfilter.add(list.get(0).getBusinesscategory());
		else
			return bcfilter;
		
		for (int i = 1; i < list.size(); i++)
		{
			for (int j = 0; j < bcfilter.size(); j++)
			{
				result=0;
				if (list.get(i).getBusinesscategory()==null||(bcfilter.get(j)!=null&&list.get(i).getBusinesscategory().equals(bcfilter.get(j)))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				bcfilter.add(list.get(i).getBusinesscategory());
		}

		return bcfilter;
	}
	
	public List getPaymentFilters(List<Monthlyproject> list,List pmfilter)
	{

		int result = 0;
		if(null != list && list.size()>0)
			pmfilter.add(list.get(0).getPayment());
		else
			return pmfilter;
		
		for (int i = 1; i < list.size(); i++)
		{

			for (int j = 0; j < pmfilter.size(); j++)
			{
				result=0;
				if (list.get(i).getPayment()==null||(pmfilter.get(j)!=null&&list.get(i).getPayment().equals(pmfilter.get(j)))) 
				{
					result = 1;
					break;
				}
			}
			if (result != 1)
				pmfilter.add(list.get(i).getPayment());
		}

		return pmfilter;
	}
	public ActionForward searchWithMpFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	//	System.out.println("enter");
		InvoiceForm mainForm = (InvoiceForm) form;
		
		List<Monthlyproject> list = (List<Monthlyproject>) request.getSession().getAttribute("list");
		List<Monthlyproject> templist = new ArrayList<Monthlyproject>();
		
		for (int i = 0; i < list.size(); i++)
			{
				templist.add(list.get(i));
			}
	
		String page = request.getSession().getAttribute("page").toString();
		
		String back = request.getParameter("back");
		String monthprojectfilters = null;
		String locationfilters = null;
		String bcategoryfilters = null;
		
		if(back!=null&&back.equals("monthlyproject_edit")){
			monthprojectfilters=(String)request.getSession().getAttribute("monthprojectfilters");
			locationfilters=(String)request.getSession().getAttribute("locationfilters");
			bcategoryfilters=(String)request.getSession().getAttribute("bcategoryfilters");
		}
		else
		{
			monthprojectfilters = request.getParameter("monthprojectfilters");
			locationfilters = request.getParameter("locationfilters");
			bcategoryfilters = request.getParameter("bcategoryfilters");
		}
	//	System.out.println("monthprojectfilters that got from the page="+monthprojectfilters);
		
		if(monthprojectfilters!=null&&monthprojectfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (templist.get(i).getMonthproject()!=null&&monthprojectfilters.indexOf(templist.get(i).getMonthproject())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("monthprojectfilters", monthprojectfilters);
		
		
	//	System.out.println("locationfilters that got from the page="+locationfilters);
		
		if(locationfilters!=null&&locationfilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if(templist.get(i).getLocation()==null){
					if(locationfilters.indexOf("null")<0){
					templist.remove(i);
					i--;
					}
				}else{
					if (locationfilters.indexOf(templist.get(i).getLocation())<0)
					{
						templist.remove(i);
						i--;
					}
				}
			}
		}
		request.getSession().setAttribute("locationfilters", locationfilters);
		
		
	//	System.out.println("bcategoryfilters that got from the page="+bcategoryfilters);

		if(bcategoryfilters!=null&&bcategoryfilters.indexOf(",")>0)
		{
			
			for (int i = 0; i < templist.size(); i++) 
			{
				if(templist.get(i).getBusinesscategory()==null){
					if(bcategoryfilters.indexOf("null")<0){
						templist.remove(i);
						i--;
					}
				}else{
				if (bcategoryfilters.indexOf(templist.get(i).getBusinesscategory().trim())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
			}
				
		}

		request.getSession().setAttribute("bcategoryfilters", bcategoryfilters);
		
		int TotalPage = templist.size()/8;
		if(templist.size()%8>0)
		{
			TotalPage++;
		}
		request.getSession().setAttribute("TotalPage", TotalPage);
		request.getSession().setAttribute("templist", templist);

		int p = 0;
		if(page!=null)
		{
			p = Integer.parseInt(page);
		}
		
		this.turnMPPage(mapping, form, request, response, 8, p);
		request.getSession().setAttribute("page", p);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	public ActionForward turnMPPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		int page = Integer.parseInt(request.getParameter("page").toString());
		this.turnMPPage(mapping, form, request, response, 8, page);
		request.getSession().setAttribute("page", page);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	public ActionForward toInsertMonthlyproject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;

		request.setAttribute("oper", "new");
	
		return mapping.findForward(mainForm.getOperPage());
	}
	
	public ActionForward addProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//InvoiceForm mainForm = (InvoiceForm) form;
		String oper=(String) request.getParameter("oper");
		String project_t = (String) request.getParameter("project_text");
		String category_t = (String) request.getParameter("category_text");	
		String payment_t = (String) request.getParameter("payment_text");	
		String locationid = (String) request.getParameter("location");
		String budget = (String) request.getParameter("budget");
		budget=budget.replaceAll(",", "");
		
		List<Map> monthprojectlist = (List<Map>) request.getSession().getAttribute("monthlyproject_select");
		List<Map> businesscategorylist = (List<Map>) request.getSession().getAttribute("businesscategory_select");
		List<Map> paymentlist = (List<Map>) request.getSession().getAttribute("payment_select");
		
		Monthlyproject project_a = new Monthlyproject();
		project_a.setMonthproject(project_t);
		project_a.setBusinesscategory(category_t);
		project_a.setPayment(payment_t);
		project_a.setLocationid(Integer.parseInt(locationid));
		project_a.setBudget(Double.parseDouble(budget));
		
		Connection conn = null;
		//result的返回值应该设3个 result=-2 month project名字已存在，result=0 monthly expense已存在 result=1 添加记录成功 ，result=-1添加记录不成功
		int result = -1;
		
		try
		{
			String sql="";
			conn = DataTools.getConnection(request);
			boolean re = false;
			boolean project_exist = false;
			sql="select * from monthproject where monthproject=?";
			project_exist = InvoiceDao.getInstance().ifExistRecord(conn, project_t.trim(), sql);
			
		//	System.out.println("project_exist="+project_exist);
			if(!project_exist)
			{
				sql="insert into monthproject(monthproject) values(?)";
				re = InvoiceDao.getInstance().newRecord(conn, project_t, sql);
				Map<String,String> map= new HashMap<String,String>();
				map.put("monthproject", project_t);
				monthprojectlist.add(map);
			}
		//	System.out.println("a new monthproject name add result is:"+re);
			//返回MonthlyProject的ID号
			int i;
			sql = "select m.monthprojectid from monthproject m where monthproject=?";
			i=InvoiceDao.getInstance().getRecordid(conn, project_t, sql);
			project_a.setMonthprojectid(i);
			
			if(category_t.trim()==""){
				project_a.setBusinesscategoryid(-1);
			}else{
				boolean re_1 = false;
				boolean category_exist= false;
				sql ="select * from businesscategory where category=?";
				category_exist= InvoiceDao.getInstance().ifExistRecord(conn, category_t.trim(), sql);
				if(!category_exist){
					sql="insert into businesscategory(category) values(?)";
					re_1=InvoiceDao.getInstance().newRecord(conn, category_t,sql);
					Map<String,String> map= new HashMap<String,String>();
					map.put("category", category_t);
					businesscategorylist.add(map);
				}
				sql = "select id from businesscategory where category=?";
				i=InvoiceDao.getInstance().getRecordid(conn, category_t, sql);
				project_a.setBusinesscategoryid(i);
			}
			
			if(payment_t==null||payment_t.trim()==""){
				project_a.setPaymentid(-1);
			}else{
				boolean re_2 = false;
				boolean payment_exist= false;
				sql ="select * from payment where payment=?";
				payment_exist= InvoiceDao.getInstance().ifExistRecord(conn, payment_t.trim(), sql);
				if(!payment_exist){
					sql="insert into payment(payment) values(?)";
					re_2=InvoiceDao.getInstance().newRecord(conn, payment_t,sql);
					Map<String,String> map= new HashMap<String,String>();
					map.put("payment", payment_t);
					paymentlist.add(map);
				}
				sql = "select id from payment where payment=?";
				i=InvoiceDao.getInstance().getRecordid(conn, payment_t, sql);
				project_a.setPaymentid(i);
			}
			
			boolean monthlyproject_exist = false;
			boolean re3 = false;
			
			if(oper.equals("edit"))
			{
				String projectid = (String) request.getParameter("projectid");
				project_a.setId(Integer.parseInt(projectid));
			}
			monthlyproject_exist = InvoiceDao.getInstance().ifExistProject(conn, project_a, oper);

			if(!monthlyproject_exist)
			{
				if(oper.equals("edit"))
				{
					re3 = InvoiceDao.getInstance().editProject(conn, project_a);
				}
				else
				{
					re3 = InvoiceDao.getInstance().addProject(conn, project_a);
				}
				if(re3)
				{
					result = 1;
			//如果到时候需要开发提醒邮件的功能，在此仿照SendThread3更改代码  FWJ 2013-06-24
			//		Thread thread = new SendThread3(Integer.parseInt(ponid));
			//		thread.start();
				}
				else
				{
					result = -1;
				}
			}
			
			else
			{
				result = 0;
			}
			
		//	System.out.println("add new invoice result in action="+result);

		}catch (Exception e) 
		{
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}  finally 
		{
			if (null != conn) 
			{
				conn.close();
			}
		}
		
		request.getSession().setAttribute("monthproject_select", monthprojectlist);
		request.getSession().setAttribute("businesscategory_select", businesscategorylist);
		request.getSession().setAttribute("payment_select", paymentlist);
		request.setAttribute("result", String.valueOf(result));
		request.getSession().setAttribute("page","0");
		this.getNewMonthlyProject(request);
		return searchWithMpFilter(mapping, form, request, response);
	}
	/**
	 * 此段编辑功能，没有访问数据库，减少对数据库的压力，如果不是即时操作，肯定会导致数据不是即时，
	 * 暂时用此方法，如果在实际应用中会产生导致此种误差的场合，再查询数据库作修改(放弃了此方法)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toEditProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		InvoiceForm mainForm = (InvoiceForm) form;
		String id = (String) request.getParameter("num");
		System.out.println("index="+id);
		String operate = (String) request.getParameter("oper");
	//	List<Monthlyproject> monthlyproject = (List<Monthlyproject>) request.getSession().getAttribute("list");
		List<Monthlyproject> editMonthlyproject = new ArrayList<Monthlyproject>();
		
		Connection conn= null;
		try
		{
			conn = DataTools.getConnection(request);
			editMonthlyproject=InvoiceDao.getInstance().toEditproject(conn,Integer.parseInt(id));
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		
		request.setAttribute("oper_new", operate);
		request.setAttribute("oper", "edit");
		request.setAttribute("editMonthlyproject", editMonthlyproject);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	public ActionForward deleteProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		int projectid = Integer.parseInt(request.getParameter("projectid"));
		Connection conn=null;

		try{
			conn=DataTools.getConnection(request);
			boolean result=InvoiceDao.getInstance().deleteProject(conn, projectid);
	//		System.out.println("delete the Monthly Project:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		this.getNewMonthlyProject(request);
		return searchWithMpFilter(mapping, form, request, response);
	}
	
	public void getNewMonthlyProject (HttpServletRequest request) throws Exception
	{
		Connection conn = DataTools.getConnection(request);
		List<Monthlyproject> list = new ArrayList<Monthlyproject>();
		try{
			list=InvoiceDao.getInstance().searchbyMonthlyproject(conn);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		List monthlyprojectlist = new ArrayList();
		List locationlist = new ArrayList();
		List businesscategorylist = new ArrayList();
		
		this.getMonthlyprojectFilters(list, monthlyprojectlist);
		this.getLocationFilters(list, locationlist);
		this.getBcategoryFilters(list, businesscategorylist);
		
		request.getSession().setAttribute("monthlyprojectlist", monthlyprojectlist);
		request.getSession().setAttribute("locationlist", locationlist);
		request.getSession().setAttribute("businesscategorylist", businesscategorylist);
		request.getSession().setAttribute("list", list);
		}
}
