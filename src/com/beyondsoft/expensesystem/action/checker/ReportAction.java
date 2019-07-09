package com.beyondsoft.expensesystem.action.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.checker.ExpenseDataDao;
import com.beyondsoft.expensesystem.dao.checker.GroupsDao;
import com.beyondsoft.expensesystem.dao.checker.PODao;
import com.beyondsoft.expensesystem.domain.checker.CaseDefect;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.checker.Invoice;
import com.beyondsoft.expensesystem.domain.checker.Monthlyproject;
import com.beyondsoft.expensesystem.domain.checker.NonLaborCost;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.domain.checker.ReportData_Basic;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.checker.ReportForm;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;

public class ReportAction extends BaseDispatchAction {

	/**
	 * ��õ�ǰ�û���ѡ��group��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	@SuppressWarnings({ "unchecked" })
	public ActionForward selectReportDateAndGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportForm mainForm = (ReportForm) form;

		SysUser su = (SysUser) request.getSession().getAttribute("user");
		List<Groups> list = new ArrayList<Groups>();

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			list = GroupsDao.getInstance().load(stmt, su.getGroupID());

		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.getSession().setAttribute("glist", list);
		request.setAttribute("lockday", list.get(0).getLockday());
		request.setAttribute("approveday", list.get(0).getApproveday());
		su.setLockday(list.get(0).getLockday());
		su.setApproveday(list.get(0).getApproveday());
		request.getSession().setAttribute("user", su);
		//Lock����С����  hanxiaoyu01 2013-01-21
		String minDate = null;
		if(3 == su.getLevelID())//�����Approver�û�,����С��Approve��Lock����
		{
			//������Lock���ύ�ģ������All Data�飬Ҳ����С��Approve��Lock����
			minDate=su.getApproveday();
		}
		else
		{//�����checker�û�������С��All Data��Lock����
			try{
				conn = DataTools.getConnection(request);
				minDate = GroupsDao.getInstance().findGroupByGroupId(conn, -1);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(conn!=null){
					conn.close();
				}
			}
		}
		request.getSession().setAttribute("minDate", minDate);
		request.getSession().setAttribute("minDate2", minDate);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ���Report��������Ƿ���Ч
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkReportRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		Connection conn = null;
//		String groupId = request.getParameter("GroupsId");
		//by dancy 2013-3-8
		String groupNames = request.getParameter("groupExcel");
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");

		System.out.println("startDay="+startDay);
		System.out.println("endDay="+endDay);
		
		System.out.println("groups="+groupNames);

		String checkReportRequest = "Initial";

		try 
		{
			
			conn = DataTools.getConnection(request);
			//change groupId to GroupNames by dancy
			List<String> listString = ExpenseDataDao.getInstance().isExpenseDataFullFilled(conn,startDay,endDay, groupNames );
			if (listString.size()!=0)
			{
				checkReportRequest = "Checking is done and you can download reports. But some projects in the selected items are not completed ready. Please be alarmed!";
				for (int i=0;i<listString.size();i++)
				{
					checkReportRequest =checkReportRequest + "\n" + listString.get(i);
				}
			}
			else
			{
				checkReportRequest = "The report is ready. You can click below button to download report with different formats you want.";
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
//		System.out.println("checkReportRequest:"+checkReportRequest);
		PrintWriter out = response.getWriter();
		out.print(checkReportRequest);
		out.close();
		return null;
	}
	
	/**
	 * ��ָ�����ڷ�Χ�ڵ�ָ��Group�µ���ݶ������ɱ���Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 * @flag
	 */
	@SuppressWarnings("deprecation")
	public void excelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;		
		String startDate =  request.getParameter("firstDay");
		String endDate = request.getParameter("endDay");
		
		String curPage = request.getParameter("curPage");
		System.out.println("curPage in action is:"+curPage);
		
		System.out.println("startDate= "+startDate);
		System.out.println("endDate= "+endDate);
		
		String scrollTop = request.getParameter("scrollTop");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String gid = "0";
		String groupNames = "";
		if(null == curPage || "".equals(curPage))//���û��curPage���� ˵����05ҳ����ø÷���
		{
			groupNames = (String) request.getParameter("groupExcel");
		}
		else
		{
			
			gid = (String) request.getParameter("GroupsId");
		}
		
		ReportData_Basic reportData = new ReportData_Basic();
		
		try 
		{
			conn = DataTools.getConnection(request);
			if(null == curPage || "".equals(curPage))
			{
				reportData = ExpenseDataDao.getInstance().searchDetailExpenseData2(conn, startDate, endDate,groupNames,sysUser);
			}
			else
			{
				
				reportData = ExpenseDataDao.getInstance().searchDetailExpenseData(conn, startDate, endDate,gid,sysUser);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}

		if (null != scrollTop && !scrollTop.equals("")) 
		{
			request.getSession().setAttribute("scrollTop", scrollTop);
		}
		request.setAttribute("checkReportRequest", "The excel report is being exported.");
		
		//Save the Excel file
		
		if(reportData.getListDate().size()<=0)
		{
			System.out.println("size=0");
		}
		String reportName="FWExcel";

		File file = new File(request.getSession().getServletContext().getRealPath("/documents/ExpenseReport_FW.xls"));;
		
		//@Dancy
		reportName=reportName+"_"+startDate;
		reportName=reportName+"_"+endDate;
		//reportName=reportName+"_"+request.getParameter("GroupsName");
		
		response.setHeader("Content-disposition","attchment; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
	//	response.setContentType("application/msexcel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
//		HSSFCellStyle css = workbook.createCellStyle();
//		css.setBorderBottom((short)1);
//		css.setBorderLeft((short)1);
//		css.setBorderRight((short)1);
//		css.setBorderTop((short)1);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		//Set the excel file content
		//The first row
		
		HSSFRow row = sheet.getRow((short) 0);

		List<Date> listDate = reportData.getListDate();
		List<Project> listProject = reportData.getListProject();
		List<Double> listHours = reportData.getListHours();
		List<String> listHoursComment = reportData.getListHoursComment();
		List<String> listFirmware = reportData.getListFirmware();
		List<String> listWorkType = reportData.getListWorkType();
		List<String> listMilestone = reportData.getListMilestone();
		List<String> listTestType = reportData.getListTestType();
		//FWJ 2013-05-07
		List<String> listTargetLaunch = reportData.getListTargetLaunch();
		List<String> listDes= reportData.getListDes();
		List<String> listBudget= reportData.getListBudget();
		int iRow=0;
		
		for (int iRecord = 0; iRecord < listProject.size(); iRecord++) 
		{
			row = sheet.createRow(iRow + 1);
			HSSFCell cell = null;				
			cell = row.createCell((short) 0);
			/* ת�����ڸ�ʽ
			 *	ת����mm/dd/yyyy 
			 */
			String tempm = "";String tempd = "";
			if((listDate.get(iRecord).getMonth()+1)>=10)
				tempm = String.valueOf((listDate.get(iRecord).getMonth()+1));
			else
				tempm = "0"+(listDate.get(iRecord).getMonth()+1);
			if(listDate.get(iRecord).getDate()>=10)
				tempd = String.valueOf(listDate.get(iRecord).getDate());
			else
				tempd = "0"+listDate.get(iRecord).getDate();
//			cell.setCellValue(( tempm + "/" + tempd +"/"+(listDate.get(iRecord).getYear()+1900)));
			cell.setCellValue(listDate.get(iRecord));
			//product
			cell = row.createCell((short) 1);
			cell.setCellValue(listProject.get(iRecord).getProduct());
			//component
			cell = row.createCell((short) 2);
			cell.setCellValue(listProject.get(iRecord).getComponent());
			//activity type
			cell = row.createCell((short) 3);
			cell.setCellValue(listWorkType.get(iRecord));
			//target milestone
			cell = row.createCell((short) 4);
			cell.setCellValue(listMilestone.get(iRecord));
			//target launch
			cell = row.createCell((short) 5);
			cell.setCellValue(listTargetLaunch.get(iRecord));
			//firmware version
			cell = row.createCell((short) 6);
			System.out.println("listFirmware.get(iRecord)="+listFirmware.get(iRecord));
			if (listFirmware.get(iRecord)==null||listFirmware.get(iRecord).trim().equals(""))
			{}
			else
			{
				cell.setCellValue(listFirmware.get(iRecord).trim());
			}
			//Responsible Manager
			//Purchase Order
			//TAR / Project ID
			//location
			cell = row.createCell((short) 10);
			cell.setCellValue(listProject.get(iRecord).getLocation());
			//skill level
			cell = row.createCell((short) 11);
			cell.setCellValue(listProject.get(iRecord).getSkillLevel());
			//Description of Skill Level
			cell = row.createCell((short) 12);
			cell.setCellValue(listDes.get(iRecord));
			//Days
			cell = row.createCell((short) 13);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);	
			cell.setCellValue(listHours.get(iRecord)/8);
			//System.out.println("listHours.get(iRecord)/8="+listHours.get(iRecord)/8);
			//System.out.println("listHours.get(iRecord)%8="+listHours.get(iRecord)%8);
			//non-labor cost
			
			//rate type
			cell = row.createCell((short) 15);
			cell.setCellValue(listProject.get(iRecord).getOTType());
			
			//Budget Tracking
			cell = row.createCell((short) 16);
			cell.setCellValue(listBudget.get(iRecord));
			//comments
			cell = row.createCell((short) 17); 
			cell.setCellValue(listHoursComment.get(iRecord));
			//hours
			cell = row.createCell((short) 18);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);	
			cell.setCellValue(listHours.get(iRecord)); 
			//wbs
			cell = row.createCell((short) 19);
			cell.setCellValue(listProject.get(iRecord).getWBS());
			//test type
			cell = row.createCell((short) 20);
			cell.setCellValue(listTestType.get(iRecord));
			//data filler
			cell = row.createCell((short) 21); 
			cell.setCellValue(listProject.get(iRecord).getUsername());
				
			iRow=iRow+1;
		}
		
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
//        response.flushBuffer();
		
	}
	
//Export the MonthlyExpense Excel	added on 2013-04-08
	@SuppressWarnings("deprecation")
	public void expenseReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{

		Connection conn = null;	
		//从页面取得PO number id by dancy
//		int poid = Integer.parseInt((String)request.getParameter("POID"));
//		int yearvid = Integer.parseInt((String)request.getParameter("Year"));
//		int monthid = Integer.parseInt((String)request.getParameter("Month"));
		//直接从session里面获取Monthly Expense List FWJ on 2013-04-18
		List<Invoice> reportList = new ArrayList<Invoice>();
		reportList=(List<Invoice>) request.getSession().getAttribute("list");
		
		try 
		{
			conn = DataTools.getConnection(request);
			//不需要每个列都存入list，所有的数据都放在monthly expense实体类中，然后建一个monthly expense的list存其实体值
//			reportList = ExpenseDataDao.getInstance().seachermonthlyexpense(conn, poid, yearvid, monthid);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (conn != null) 
			{
				conn.close();
			}
		}

		//Save the Excel file
		String reportName="MonthlyExpense_ReportExcel";

		File file = new File(request.getSession().getServletContext().getRealPath("/documents/MonthlyExpense.xls"));;
		
		response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		//Set the excel file content
		//The first row
		
		HSSFRow row = sheet.getRow((short) 0);
		int iRow=0;
		
		for (int i = 0; i < reportList.size(); i++) 
		{
			row = sheet.createRow(iRow + 1);
			HSSFCell cell = null;				
			//Year
			cell = row.createCell((short) 0);
			cell.setCellValue(reportList.get(i).getYear());
			//Month
			cell = row.createCell((short) 1);
			cell.setCellValue(reportList.get(i).getMonth());
			//Month project
			cell = row.createCell((short) 2);
			cell.setCellValue(reportList.get(i).getMonthproject());	
			//Category
			cell = row.createCell((short) 3);
			cell.setCellValue(reportList.get(i).getCategory());
			//PONumber
			cell = row.createCell((short) 4);
			cell.setCellValue(reportList.get(i).getPONum());
			//Client
			cell = row.createCell((short) 5);
			cell.setCellValue(reportList.get(i).getClient());
			//Invoice/Downpayment
			cell = row.createCell((short) 6);
			cell.setCellValue(reportList.get(i).getPayment());
			//Cost
			cell = row.createCell((short) 7);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(reportList.get(i).getCost()));//强制转换cost的类型，导出格式为美元格式 FWJ 2013-05-21
			//WBS
			cell = row.createCell((short) 8);
			cell.setCellValue(reportList.get(i).getWBSNumber());
			//InvoiceNumber
			cell = row.createCell((short) 9);
			cell.setCellValue(reportList.get(i).getInvoiceNumber()); 
			//Comments
			cell = row.createCell((short) 10);
			cell.setCellValue(reportList.get(i).getComment());
			
			iRow=iRow+1;
		}
		
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
//        response.flushBuffer();
		
	}
	
	//Export the PO Excel	added on 2013-04-27
	@SuppressWarnings("deprecation")
	public void poReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		List<ProjectOrder> reportList = new ArrayList<ProjectOrder>();
		reportList=(List<ProjectOrder>) request.getSession().getAttribute("list_po");

		//Save the Excel file
		String reportName="PO_ReportExcel";

		File file = new File(request.getSession().getServletContext().getRealPath("/documents/po.xls"));;
		
		response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		//Set the excel file content
		//The first row
		
		HSSFRow row = sheet.getRow((short) 0);
		int iRow=0;
		
		for (int i = 0; i < reportList.size(); i++) 
		{
			if(!reportList.get(i).getPONumber().equals("")){
			row = sheet.createRow(iRow + 1);
			HSSFCell cell = null;				
			//PO Number
			cell = row.createCell((short) 0);
			cell.setCellValue(reportList.get(i).getPONumber());
			//PO Issue Date
			cell = row.createCell((short) 1);
			cell.setCellValue(reportList.get(i).getPOStartDate());
			//PO Budget
			cell = row.createCell((short) 2);
			cell.setCellValue(reportList.get(i).getPOAmount());
			//PO Used
			cell = row.createCell((short) 3);
			cell.setCellValue(reportList.get(i).getPoUsed());
			//PO Remaining
			cell = row.createCell((short) 4);
			cell.setCellValue(reportList.get(i).getPoBalance());
			//PO State
			cell = row.createCell((short) 5);
			cell.setCellValue(reportList.get(i).getPOStatus());
			//PO End Date
			cell = row.createCell((short) 6);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(reportList.get(i).getPOEndDate());
			//PO Lock
			cell = row.createCell((short) 7);
			cell.setCellValue(reportList.get(i).getLock());
			//PO Manager
			cell = row.createCell((short) 8);
			cell.setCellValue(reportList.get(i).getPOManager()); 

			iRow=iRow+1;
		}
		}
		OutputStream ops=response.getOutputStream();
		if(null == ops)
		System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
//        response.flushBuffer();
		
	}
	/**
	 *����non-Labor cost report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void excelExport2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		String startDate = (String) request.getParameter("firstDay");
		String endDate = (String) request.getParameter("endDay");
				
		System.out.println("startDate= "+startDate);
		System.out.println("endDate= "+endDate);
		
//		String gid = (String) request.getParameter("GroupsId");
		//change gid to groupNames by dancy 2013-03-08
		String groupNames = (String) request.getParameter("groupExcel");
		String scrollTop = (String) request.getParameter("scrollTop");
		//add by dancy 2013-03-08
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		
		List<NonLaborCost> list = new ArrayList<NonLaborCost>();
		try {
			conn = DataTools.getConnection(request);
			list = PODao.getInstance().searchNonLaborReport(conn, startDate, endDate, groupNames, sysUser);
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}

		if (null != scrollTop && !scrollTop.equals("")) 
		{
			request.getSession().setAttribute("scrollTop", scrollTop);
		}
		request.setAttribute("checkReportRequest", "The excel report is being exported.");
		
		String reportName="NonLaborCostExcel";

		File file = new File(request.getSession().getServletContext().getRealPath("/documents/NonLaborCost.xls"));
		//@Dancy
		reportName=reportName+"_"+startDate;
		reportName=reportName+"_"+endDate;
		//reportName=reportName+"_"+request.getParameter("GroupsName");
		
		response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFCellStyle css = workbook.createCellStyle();
		css.setBorderBottom((short)1);
		css.setBorderLeft((short)1);
		css.setBorderRight((short)1);
		css.setBorderTop((short)1);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		for(int i=0;i<list.size();i++)
		{
			row = sheet.createRow(i+1);
			cell = row.createCell((short) 0);
			cell.setCellValue(list.get(i).getNdate());
			
			cell = row.createCell((short) 1);
			cell.setCellValue(list.get(i).getGroupName());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(list.get(i).getProduct());
			
			cell = row.createCell((short) 3);
			cell.setCellValue(list.get(i).getComponent());
			
			cell = row.createCell((short) 4);
			cell.setCellValue(list.get(i).getWBS());
			
			cell = row.createCell((short) 5);
			cell.setCellValue(list.get(i).getLocale());
			
			cell = row.createCell((short) 6);
			cell.setCellValue(list.get(i).getQuantity());
			
			cell = row.createCell((short)7);
			cell.setCellValue(list.get(i).getComments());
			
			cell = row.createCell((short) 8);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list.get(i).getNonLaborCost());
			
			cell = row.createCell((short) 9);
			cell.setCellValue(list.get(i).getNotes());
			
			cell = row.createCell((short) 10);
			cell.setCellValue(list.get(i).getCreator());
			
		}
			
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
		
	}
	/**
	 * ����case defect report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void excelExport3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn = null;
		String startDate = (String) request.getParameter("firstDay");
		String endDate = (String) request.getParameter("endDay");
				
		System.out.println("startDate= "+startDate);
		System.out.println("endDate= "+endDate);
		
//		String gid = (String) request.getParameter("GroupsId");
		//change gid to groupNames by dancy 2013-03-08
		String groupNames = (String) request.getParameter("groupExcel");
		String scrollTop = (String) request.getParameter("scrollTop");
		//add by dancy 2013-03-08
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		
		List<CaseDefect> list2 = new ArrayList<CaseDefect>();
		try {
			conn = DataTools.getConnection(request);
			list2 = PODao.getInstance().searchCaseReport(conn, startDate, endDate, groupNames, sysUser);
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}

		if (null != scrollTop && !scrollTop.equals("")) 
		{
			request.getSession().setAttribute("scrollTop", scrollTop);
		}
		request.setAttribute("checkReportRequest", "The excel report is being exported.");
		
		String reportName="CaseDefectExcel";
		File file = new File(request.getSession().getServletContext().getRealPath("/documents/CaseDefect.xls"));
		//@Dancy
		reportName=reportName+"_"+startDate;
		reportName=reportName+"_"+endDate;
		//reportName=reportName+"_"+request.getParameter("GroupsName");
		
		response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFCellStyle css = workbook.createCellStyle();
		css.setBorderBottom((short)1);
		css.setBorderLeft((short)1);
		css.setBorderRight((short)1);
		css.setBorderTop((short)1);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		for(int i=0;i<list2.size();i++)
		{
			row = sheet.createRow(i+1);
			cell = row.createCell((short) 0);
			cell.setCellValue(list2.get(i).getProduct());
			
			cell = row.createCell((short) 1);
			cell.setCellValue(list2.get(i).getComponentName());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(list2.get(i).getSDate());
			
			cell = row.createCell((short) 3);
			cell.setCellValue(list2.get(i).getEDate());
			
			cell = row.createCell((short) 4);
			cell.setCellValue(list2.get(i).getCases());
			
			cell = row.createCell((short) 5);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list2.get(i).getUrgentdefect());
			
			cell = row.createCell((short) 6);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list2.get(i).getHighdefect());
			
			cell = row.createCell((short) 7);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list2.get(i).getNormaldefect());
			
			cell = row.createCell((short) 8);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list2.get(i).getLowdefect());
			
			cell = row.createCell((short) 9);
			cell.setCellValue(list2.get(i).getMilestone());
			
			cell = row.createCell((short) 10);
			cell.setCellValue(list2.get(i).getCreator());
			
		}
			
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
		
	}
	
	@SuppressWarnings("deprecation")
	public void excelExport4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm mainForm = (ReportForm) form;
		Connection conn = null;
		
		this.selectReportDateAndGroup(mapping, mainForm, request, response);
		
		String startDate = "";
		String endDate = "";
		
		startDate = mainForm.getStartDay();
		endDate = mainForm.getEndDay();
				
		System.out.println("startDate= "+startDate);
		System.out.println("endDate= "+endDate);
		
		String gid = (String) request.getParameter("GroupsId");
		String scrollTop = (String) request.getParameter("scrollTop");
		
		List<Project> list3 = new ArrayList<Project>();
		try {
			conn = DataTools.getConnection(request);
			list3 = PODao.getInstance().searchPOCostByProject(conn, startDate, endDate, Integer.parseInt(gid));
					
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}

		if (null != scrollTop && !scrollTop.equals("")) 
		{
			request.getSession().setAttribute("scrollTop", scrollTop);
		}
		request.setAttribute("checkReportRequest", "The excel report is being exported.");
		
		String reportName="POBalanceExcel";
		File file = new File(request.getSession().getServletContext().getRealPath("/documents/Balance_Report.xls"));
		//@Dancy
		reportName=reportName+"_"+startDate;
		reportName=reportName+"_"+endDate;
		//reportName=reportName+"_"+request.getParameter("GroupsName");
		
		response.setHeader("Content-disposition","inline; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFSheet sheet = workbook.getSheetAt(2);
		HSSFRow row = null;
		HSSFCell cell = null;
		for(int i=0;i<list3.size();i++)
		{
			row = sheet.createRow(i+1);
			cell = row.createCell((short) 0);
			cell.setCellValue(list3.get(i).getPOName());
			
			cell = row.createCell((short) 1);
			cell.setCellValue(list3.get(i).getComponent());
			
			
			
			cell = row.createCell((short) 2);
			cell.setCellValue(list3.get(i).getProduct());
			
			
			cell = row.createCell((short) 3);
			cell.setCellValue(list3.get(i).getSkillLevel());
			
			
			cell = row.createCell((short) 4);
			cell.setCellValue(list3.get(i).getOTType());
			
			cell = row.createCell((short) 5);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list3.get(i).getSumHours());
			
			
			cell = row.createCell((short) 8);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(list3.get(i).getSumNonLaborCost());
			
		}
			
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
		
	}
	/**
	 * Lock���ܣ���checker���expense��ʱ�򣬲�����filler�޸���ݣ����Ա�֤��ݵ�һ���ԣ�
	 * ����checker�߿�filler�߸���ɵ���ݲ�һ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 * @flag
	 */
	public ActionForward lockExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm mainForm = (ReportForm) form;
		String lockDate = (String) request.getParameter("today3");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		System.out.println("lockDate="+lockDate);
		String groupId = (String) request.getParameter("GroupsId2");
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ExpenseDataDao.getInstance().lock(conn, Integer.parseInt(groupId), lockDate);
			System.out.println("lock group "+groupId +" untill "+lockDate+" "+result);
			//���lock�ɹ����͸���session���lockDate��Ϣ
			if(result)
			{
				request.getSession().setAttribute("lockday", lockDate);
				if(sysUser.getGroupID()==Integer.parseInt(groupId))
				{
					sysUser.setLockday(lockDate);
					request.getSession().setAttribute("user",sysUser);
				}
				request.getSession().setAttribute("approveday", sysUser.getApproveday());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (conn != null) 
			{
				conn.close();
			}
		}
		request.setAttribute("groupId2",groupId);
		//return mapping.findForward(mainForm.getOperPage());
		return this.resetMindate(mapping, mainForm, request, response);
//		return selectReportDateAndGroup(mapping, mainForm, request, response);
	}
	/**
	 * Approve����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 */
	public ActionForward approveExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm mainForm = (ReportForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String approveDate = (String) request.getParameter("today4");
		String groupId = (String) request.getParameter("GroupsId2");
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			boolean result = ExpenseDataDao.getInstance().approve(conn, Integer.parseInt(groupId), approveDate,user.getApproveLevel());
			System.out.println("set approve "+user.getApproveLevel()+" on group "+groupId +" untill "+approveDate+" "+result);
			//���approve�ɹ����͸���session���approveDate��Ϣ
			if(result)
			{
				request.getSession().setAttribute("approveday",approveDate);
				if(user.getGroupID()==Integer.parseInt(groupId))
				{
					user.setApproveday(approveDate);
					request.getSession().setAttribute("user",user);
				}
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (conn != null) 
			{
				conn.close();
			}
		}
		request.setAttribute("groupId2",groupId);
//		return mapping.findForward(mainForm.getOperPage());
		return this.resetMindate(mapping, mainForm, request, response);
//		return selectReportDateAndGroup(mapping, mainForm, request, response);
	}
	/**
	 * ҳ�滻group֮���ҵ���group��lock��approve��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public ActionForward changeGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm mainForm = (ReportForm) form;
		String groupId = (String) request.getParameter("GroupsId2");
		List<Groups> list = (List<Groups>) request.getSession().getAttribute("glist");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String lockday = "";
		String approveday = "";
		Connection conn=null;
		try
		{
			conn = DataTools.getConnection(request);
			Statement stmt = conn.createStatement();
			list = GroupsDao.getInstance().load(stmt, Integer.parseInt(groupId));
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null)
			{
				conn.close();
			}
		}
		//�ҵ���Ӧ��group
		for(int i=0;i<list.size();i++)
		{
			if(Integer.parseInt(groupId)==list.get(i).getGid())
			{
				lockday = list.get(i).getLockday();
				approveday = list.get(i).getApproveday();
				request.setAttribute("approve", list.get(i).getApprove());
				user.setApproveday(approveday);
				request.getSession().setAttribute("user", user);
			}
		}
		
		request.getSession().setAttribute("lockday", lockday);
		request.getSession().setAttribute("approveday", approveday);
		
		request.setAttribute("groupId2",groupId);
		return this.resetMindate(mapping, mainForm, request, response);
	}
	
	public ActionForward resetMindate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportForm mainForm = (ReportForm) form;
		String groupId = (String) request.getParameter("GroupsId2");
		
		request.setAttribute("groupId2",groupId);
		//hanxiaoyu01 2012-01-22
		String minDate = null;
		String minDate2 = null;
		Connection conn=null;
		SysUser su=(SysUser)request.getSession().getAttribute("user");
		int gid=Integer.parseInt(groupId);
		if(gid==-1)
		{//�����All Data���,����С��Approve��Lock����
			minDate = su.getApproveday(); //lock min value
			
		}
		else
		{//�������������ݣ�����С��All Data��Lock����
			try{
				conn = DataTools.getConnection(request);
				minDate = GroupsDao.getInstance().findGroupByGroupId(conn, -1);
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(conn!=null)
				{
					conn.close();
				}
			}
		}
		minDate2 = su.getApproveday();//approve min value
		request.getSession().setAttribute("minDate", minDate);
		request.getSession().setAttribute("minDate2", minDate2);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	//Export the Monthly Project Excel	added on 2013-06-19 fengwenjing
	@SuppressWarnings("deprecation")
	public void projectReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{

		Connection conn = null;	
		List<Monthlyproject> reportList = new ArrayList<Monthlyproject>();
		List<Monthlyproject> reportList2 = new ArrayList<Monthlyproject>();
		reportList=(List<Monthlyproject>) request.getSession().getAttribute("templist");
		
		try 
		{
			conn = DataTools.getConnection(request);
			//不需要每个列都存入list，所有的数据都放在monthly expense实体类中，然后建一个monthly expense的list存其实体值
		//	reportList = ExpenseDataDao.getInstance().seachermonthlyexpense(conn, poid, yearvid, monthid);
			reportList2 = ExpenseDataDao.getInstance().seacherMonthly(conn,reportList);
		//	System.out.println("reportList2.size()="+reportList2.size());
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (conn != null) 
			{
				conn.close();
			}
		}

		//Save the Excel file
		String reportName="Monthly_Project_ReportExcel";

		File file = new File(request.getSession().getServletContext().getRealPath("/documents/ProjectManagement.xls"));;
		
		response.setHeader("Content-disposition","attachment; filename="+reportName+".xls");
		response.setContentType("application/vnd.ms-excel"); 
		
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(fis));
		fis.close();
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFSheet sheet2 = workbook.getSheetAt(1);
		//Set the excel file content
		//The first row
		
		HSSFRow row = sheet.getRow((short) 0);
		HSSFRow row2 = sheet2.getRow((short) 0);
		int iRow=0;
		int iRow2=0;
		
		for (int i = 0; i < reportList.size(); i++) 
		{
			row = sheet.createRow(iRow + 1);
			HSSFCell cell = null;				
			//Project
			cell = row.createCell((short) 0);
			cell.setCellValue(reportList.get(i).getMonthproject());
			//Location
			cell = row.createCell((short) 1);
			cell.setCellValue(reportList.get(i).getLocation());
			//Businesscategory
			cell = row.createCell((short) 2);
			cell.setCellValue(reportList.get(i).getBusinesscategory());	
			//Payment
			cell = row.createCell((short) 3);
			cell.setCellValue(reportList.get(i).getPayment());
			//Initial Quotation/Budget
			cell = row.createCell((short) 4);
			cell.setCellValue(reportList.get(i).getBudget());
			//Used Amount
			cell = row.createCell((short) 5);
			cell.setCellValue(reportList.get(i).getUsedBudegt());
			//Remaining Amount 
			cell = row.createCell((short) 6);
			cell.setCellValue(reportList.get(i).getRemainBalance());
			//Cost in Latest Month
			cell = row.createCell((short) 7);
			cell.setCellValue(reportList.get(i).getCostInLatestMonth());	
			iRow=iRow+1;
		}
		
		for (int i = 0; i < reportList2.size(); i++) 
		{
			row2 = sheet2.createRow(iRow2 + 1);
			HSSFCell cell = null;				
			//Project
			cell = row2.createCell((short) 0);
			cell.setCellValue(reportList2.get(i).getMonthproject());
			//Location
			cell = row2.createCell((short) 1);
			cell.setCellValue(reportList2.get(i).getLocation());
			//Businesscategory
			cell = row2.createCell((short) 2);
			cell.setCellValue(reportList2.get(i).getBusinesscategory());	
			//Payment
			cell = row2.createCell((short) 3);
			cell.setCellValue(reportList2.get(i).getPayment());
			//Year
			cell = row2.createCell((short) 4);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			cell.setCellValue(Integer.parseInt(reportList2.get(i).getYear()));
			cell.setCellValue(reportList2.get(i).getYear());
			//Month
			cell = row2.createCell((short) 5);
			cell.setCellValue(reportList2.get(i).getMonth());
			//PO 
			cell = row2.createCell((short) 6);
			cell.setCellValue(reportList2.get(i).getPo());
			//cost
			cell = row2.createCell((short) 7);
			cell.setCellValue(Double.parseDouble(reportList2.get(i).getCost()));
					
			iRow2=iRow2+1;
		}
		OutputStream ops=response.getOutputStream();
		if(null == ops)
			System.out.println("null ops");
		workbook.write(ops);
		ops.flush();
        ops.close();
        workbook = null;
//        response.flushBuffer();
		
	}
}
