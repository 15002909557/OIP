package com.beyondsoft.expensesystem.action.checker;
   
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.checker.ExpenseDataDao;
import com.beyondsoft.expensesystem.dao.checker.GroupsDao;
import com.beyondsoft.expensesystem.dao.checker.LeaveDataDao;
import com.beyondsoft.expensesystem.dao.checker.PODao;
import com.beyondsoft.expensesystem.dao.checker.ProjectDao;
import com.beyondsoft.expensesystem.dao.checker.RateDao;
import com.beyondsoft.expensesystem.dao.system.AdministratorDao;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.checker.CheckDetails;
import com.beyondsoft.expensesystem.domain.checker.ExpenseData;
import com.beyondsoft.expensesystem.domain.checker.ExpenseDetail;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.checker.LeaveData;
import com.beyondsoft.expensesystem.domain.checker.LeaveDetails;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.Rate;
import com.beyondsoft.expensesystem.domain.system.DateFormat;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.checker.DataCheckerForm;
import com.beyondsoft.expensesystem.time.SendThread;
import com.beyondsoft.expensesystem.util.AnnounceTool;
import com.beyondsoft.expensesystem.util.BaseActionForm;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;
import com.beyondsoft.expensesystem.util.FilterTools;
//@Dancy 2011-10-20
@SuppressWarnings({ "unchecked", "deprecation", "unused" })
public class DataCheckerAction extends BaseDispatchAction 
{

/**
 * ���ؽ���Daily Records
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 * @flag
 */
	public ActionForward selectDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		// ��session��Ķ�����remove��
		request.getSession().removeAttribute("checkdetails");
		request.getSession().removeAttribute("projectId");
		request.getSession().removeAttribute("list2");
		request.getSession().removeAttribute("list3");
		request.getSession().removeAttribute("projectId2");
		request.getSession().removeAttribute("firstDay");
		request.getSession().removeAttribute("list");
		request.getSession().removeAttribute("displaylist");
		request.getSession().removeAttribute("str1");
		request.getSession().removeAttribute("str2");
		request.getSession().removeAttribute("str3");
		request.getSession().removeAttribute("str4");
		request.getSession().removeAttribute("str5");
		request.getSession().removeAttribute("str6");
		request.getSession().removeAttribute("str7");
		request.getSession().removeAttribute("lock");
		request.getSession().removeAttribute("currentId");
		request.getSession().removeAttribute("scrollTop");
		request.getSession().removeAttribute("summaryDay1");
		request.getSession().removeAttribute("summaryDay2");
		request.getSession().removeAttribute("summaryDay3");
		request.getSession().removeAttribute("summaryDay4");
		request.getSession().removeAttribute("summaryDay5");
		request.getSession().removeAttribute("summaryDay6");
		request.getSession().removeAttribute("summaryDay7");
		request.getSession().removeAttribute("ProjectFilter");
		request.getSession().removeAttribute("ProductFilter");
		request.getSession().removeAttribute("OTTpeyFilter");
		request.getSession().removeAttribute("worktypeList");
		request.getSession().removeAttribute("layerList");
		request.getSession().removeAttribute("testtypeList");
		request.getSession().removeAttribute("targetlaunchList");
		request.getSession().removeAttribute("milestoneList");
		request.getSession().removeAttribute("approveday");
		request.getSession().removeAttribute("lockday");
		request.getSession().removeAttribute("start");
		request.getSession().removeAttribute("end");
		request.getSession().removeAttribute("copylist");//hanxiaoyu01 2013-02-01
		request.getSession().removeAttribute("dcd");//hanxiaoyu01 2013-02-16 Ĭ��Case and Defect
		mainForm.setToday(mainForm.getToday());
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * check Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author xiaofei by collie 0505
	 */
	public ActionForward checkDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String projectfilter = (String)	request.getParameter("projectfilter");
		String skillfilter = (String)	request.getParameter("skillfilter");
		String ottypefilter = (String)	request.getParameter("ottypefilter");
		String productfilter = "";
		if(user.getUI()==1)
			productfilter = (String) request.getParameter("productfilter");
		String groupfilter = (String)	request.getParameter("groupfilter");
		
		System.out.println(projectfilter);
		System.out.println(skillfilter);
		System.out.println(ottypefilter);
		System.out.println(projectfilter);
		System.out.println(projectfilter);
		
		String turnpage = "";
		if(user.getUI()==1) //fw ��product
		{
			if("all".equals(projectfilter)&&
					"all".equals(skillfilter)&&
					"all".equals(ottypefilter)&&
					"all".equals(productfilter)&&
					"all".equals(groupfilter)) //���û��ɸѡ���� turnpage=����
				turnpage = "yes";
			else
				turnpage = "no";
		}
		else if(user.getUI()==2)
		{
			if("all".equals(projectfilter)&&
					"all".equals(skillfilter)&&
					"all".equals(ottypefilter)&&
					"all".equals(groupfilter)) //���û��ɸѡ���� turnpage=����
				turnpage = "yes";
			else
				turnpage = "no";
		}
		
		request.setAttribute("projectfilter", projectfilter);
		request.setAttribute("skillfilter", skillfilter);
		request.setAttribute("ottypefilter", ottypefilter);
		if(user.getUI()==1)
			request.setAttribute("productfilter", productfilter);
		request.setAttribute("groupfilter", groupfilter);
		
		List<ExpenseData> uplist = new ArrayList<ExpenseData>();
		// ��Session�л��User����
		

		List<CheckDetails> result = new ArrayList<CheckDetails>();
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			
			Date today = new Date(Date.parse(mainForm.getToday()));
			int theRealDay = today.getDay();
			if (theRealDay == 0)
				theRealDay = 7;
			
			String str1 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 1 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str7 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 7 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			System.out.println(str1+str7);
			result = ExpenseDataDao.getInstance().checkDetails(conn, user.getGroupName(),str1,str7);
			//0 3005 5-2 3011 2011-04-25
			if (0!=result.size()){
				for (int i=0;i<result.size();i++){
					String originalString = result.get(i).getCreatetime();
					String m="";
					String d="";
					m=originalString.split("-")[1];
					d=originalString.split("-")[2];
					if (m.substring(0, 1).equals("0")){
						m=m.substring(1,2);
					}
					if (d.substring(0, 1).equals("0")){
						d=d.substring(1,2);
					}
					String finalString = m + "-" + d;
					//System.out.println("Date "+i+":"+originalString+";"+finalString);
					result.get(i).setCreatetime(finalString);
				}
			}
			request.getSession().setAttribute("checkdetails", result);
			request.setAttribute("page", request.getParameter("page"));
		} catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		request.setAttribute("page",request.getParameter("page"));
		request.setAttribute("turnpage",turnpage);
		System.out.println("turnpage in action="+turnpage);
		request.getSession().setAttribute("scrollTop", request.getParameter("scrollTop"));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ��ѯDataChecker3�б���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		// ��Session�л��User����.
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		
		// �ж�ҳ��ѡ���ui���û�Ĭ��ui�Ƿ���ͬ
		String uifrompage = (String) request.getParameter("UIradio");
		System.out.println("uifrompage="+uifrompage);
		if (null != uifrompage) 
		{
			// ���ͬ�����ȸı��û����ڵ�group��uiĬ�����ã�Ȼ���û���ǰ��ui����µ����� 
			if (user.getUI() != Integer.parseInt(uifrompage)) 
			{
				this.saveGroupUI(user.getGroupName(), Integer.parseInt(uifrompage), request);
				user.setUI(Integer.parseInt(uifrompage));
				request.getSession().setAttribute("user", user);
			}
		}
		//�ж�ҳ��ѡ�����д��λ���û�Ĭ���Ƿ���ͬ headcount or hour
		String headorhour = (String) request.getParameter("headorhour");
		System.out.println("headorhour="+headorhour);
		if(null != headorhour)
		{
			if(user.getHeadorHour() != Integer.parseInt(headorhour));
			{
				this.saveHeadorHour(Integer.parseInt(headorhour), user.getGroupName(), request);
				user.setHeadorHour(Integer.parseInt(headorhour));
				request.getSession().setAttribute("user", user);
			}
		}
		
		List<Map> skillList = new ArrayList<Map>();
		String datestr = (String) request.getParameter("today");
		mainForm.setToday(datestr);
		if (null == datestr || "".equals(datestr)) 
		{
			return mapping.findForward("data_checker_02");
		}
		System.out.println("mainForm.getToday()= "+ mainForm.getToday());
		SysUser list3 = new SysUser();
		// �����������д���ݿ����ѯ�����ļ�¼(����leaveList��projectList),������ר�Ŵ�projectList
		List<ExpenseData> list = new ArrayList<ExpenseData>();
		// leave List
		List<ExpenseData> list2 = new ArrayList<ExpenseData>();
		List<String> projectId = new ArrayList<String>();
		List<String> projectId2 = new ArrayList<String>();
		double summaryDay1 = 0;
		double summaryDay2 = 0;
		double summaryDay3 = 0;
		double summaryDay4 = 0;
		double summaryDay5 = 0;
		double summaryDay6 = 0;
		double summaryDay7 = 0;

		Connection conn = null;
		Statement stmt = null;
		//�õ�����expense data��
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			// �õ�����expense data
			list = ExpenseDataDao.getInstance().load(stmt, mainForm.getToday(),
					user);
			// ��expense data�ֿ�
			// leave ��¼�ŵ�list2��
			// project��¼����list��
			for (int i = 0; i < list.size(); i++) {
				if (null != list.get(i).getLeaveType()
						&& !list.get(i).getLeaveType().trim().equals("")) {
					list2.add(list.get(i));
					list.remove(i);
					i = i - 1;
				} else {
					summaryDay1 = summaryDay1 + Double.parseDouble("0" + list.get(i).getHour1());
					summaryDay2 = summaryDay2 + Double.parseDouble("0" + list.get(i).getHour2());
					summaryDay3 = summaryDay3 + Double.parseDouble("0" + list.get(i).getHour3());
					summaryDay4 = summaryDay4 + Double.parseDouble("0" + list.get(i).getHour4());
					summaryDay5 = summaryDay5 + Double.parseDouble("0" + list.get(i).getHour5());
					summaryDay6 = summaryDay6 + Double.parseDouble("0" + list.get(i).getHour6());
					summaryDay7 = summaryDay7 + Double.parseDouble("0" + list.get(i).getHour7());

				}
				skillList = AdministratorDao.getInstance().searchSkillLevels(stmt);
			}
			// �û�
			list3 = SysUserDao.getInstance().load(stmt, user.getUserId());
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}

		for (int i = 0; i < list.size(); i++) 
		{
			projectId.add(String.valueOf(list.get(i).getProjectId()));
		}
		request.getSession().setAttribute("projectId", projectId);
		request.getSession().setAttribute("list2", list2);
		request.getSession().setAttribute("list3", list3);
		for (int i = 0; i < list2.size(); i++) 
		{
			projectId2.add(String.valueOf(list2.get(i).getProjectId()));
		}
		request.getSession().setAttribute("projectId2", projectId2);
		
		//�½��˸�ʽ�����ڵĶ���DateFormat�������ڸ�ʽΪ'2013-1-2'�����2013-01-02�����ͣ� 2013-1-24 by dancy
		DateFormat dft = new DateFormat();
		List<String> week = new ArrayList<String>();
		week = dft.doWeek(datestr.replaceAll("/", "-"));//ȡ�õ�ǰһ������ by dancy 2013-1-24
		System.out.println("First day is:"+week.get(0));
		System.out.println("Second day is:"+week.get(1));
		
		request.getSession().setAttribute("firstDay", week.get(0));
		request.getSession().setAttribute("endDay", week.get(6));

		// ����˵����lock�������жϵ��������Ƿ����޷��޸ġ�
		// ����˵����approvelock�������жϵ��������Ƿ�approve���޷��޸ġ�
		String lock = "";
		String approvelock = "";
		// SysUser sysUser = (SysUser)
		// request.getSession().getAttribute("user");
		String lockday = user.getLockday();
		String approveday = user.getApproveday();
		int approved = user.getApproved();
		int approvelevel = user.getApproveLevel();
		 System.out.println("approved:"+approved);
		 System.out.println("approvelevel:"+approvelevel);
		 System.out.println("lockday:"+lockday);
		 System.out.println("approveday:"+approveday);
		 
		// ���lockdayΪ�գ�˵��û�б���
		if (null == lockday || "".equals(lockday)
				|| "NULL".equalsIgnoreCase(lockday))
			lock = "1111111";
		else {
			lock = lock + this.ifLock(lockday, week.get(0), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(1), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(2), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(3), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(4), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(5), 1, 0);
			lock = lock + this.ifLock(lockday, week.get(6), 1, 0);
		}
		// ���approvedayΪ�գ�˵��û�б�approve
		if (null == approveday || "".equals(approveday)
				|| "NULL".equalsIgnoreCase(approveday))
			approvelock = "1111111";
		else {
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(0), approved,
						approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(1), approved,
							approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(2), approved,
							approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(3), approved,
							approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(4), approved,
							approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(5), approved,
							approvelevel);
			approvelock = approvelock
					+ this.ifLock(approveday, week.get(6), approved,
							approvelevel);
		}
		 System.out.println("approveday in action="+approveday);
		 System.out.println("approvelock in action="+approvelock);
		 
		// session���list���ܱ?ɸѡ���ܻ������list������Ҫ�ٴβ�ѯ��ݿ�
		request.getSession().setAttribute("list", list);
		// displaylist����ʾ������list����Ϊ��save all���� note0316��Ѿ�ģ��������û���ݺã�����дһ��save
		// all����������������ˣ���������������
		//�����ڸ�ʽ������2012-01-02ת����2012-1-2
		List<String> week2 = dft.doWeek2(datestr.replaceAll("/", "-"));
		
		request.getSession().setAttribute("displaylist", list);	
		request.getSession().setAttribute("str1", week2.get(0));
		request.getSession().setAttribute("str2", week2.get(1));
		request.getSession().setAttribute("str3", week2.get(2));
		request.getSession().setAttribute("str4", week2.get(3));
		request.getSession().setAttribute("str5", week2.get(4));
		request.getSession().setAttribute("str6", week2.get(5));
		request.getSession().setAttribute("str7", week2.get(6));
		
		request.getSession().setAttribute("lock", lock);
		request.getSession().setAttribute("approvelock", approvelock);
		System.out.println("currentId="+request.getSession().getAttribute("currentId"));

		request.setAttribute("currentId", request.getSession().getAttribute("currentId"));
		request.setAttribute("scrollTop", request.getSession().getAttribute("scrollTop")); 
		
		java.text.NumberFormat formate = java.text.NumberFormat.getNumberInstance();
		formate.setMaximumFractionDigits(4);// �趨С�����Ϊ�� ����ô��ʾ���������������
		String m = null;
		m = formate.format(summaryDay1);
		request.getSession().setAttribute("summaryDay1", m);
		m = formate.format(summaryDay2);
		request.getSession().setAttribute("summaryDay2", m);
		m = formate.format(summaryDay3);
		request.getSession().setAttribute("summaryDay3", m);
		m = formate.format(summaryDay4);
		request.getSession().setAttribute("summaryDay4", m);
		m = formate.format(summaryDay5);
		request.getSession().setAttribute("summaryDay5", m);
		m = formate.format(summaryDay6);
		request.getSession().setAttribute("summaryDay6", m);
		m = formate.format(summaryDay7);
		request.getSession().setAttribute("summaryDay7", m);

		/*
		 * note_0324: note_0328����� 1. ��listһ��ȫ��load��session�������
		 * �ô��ǣ���ҳ�����ɸѡ�ȵȣ������Բ�������ݿ⣬����ֱ�Ӷ�sesison���list�������ٶȿ죬 save
		 * all֮��Ҳ����Ҫ����load list��ֱ�Ӱ�session���list�ĳ�ͬ�������ˡ� �����ǣ���һ��load
		 * list��¼ʱ���ٶ������ͻ���ͬʱ����ʱ������ѹ���� ����취��1.�Ż�sql���,��ת���ɴ洢��� 2.������Ҫ�Ĳ�ѯ���ɾ��
		 */
		// �õ�filter
		List filterlist = this.getFilter(list);
		//��list���� hanxiaoyu01 2013-01-28
		List ProjectFilter=(List) filterlist.get(0);
		List SkillFilter=(List) filterlist.get(1);
		List OTTpeyFilter=(List)filterlist.get(2);
		List ProductFilter=(List)filterlist.get(3);
		List GroupFilter=(List)filterlist.get(4);
		Collections.sort(ProjectFilter);
		Collections.sort(SkillFilter);
		Collections.sort(OTTpeyFilter);
		Collections.sort(ProductFilter);
		Collections.sort(GroupFilter);
		
		request.getSession().setAttribute("ProjectFilter",ProjectFilter);
		request.getSession().setAttribute("SkillFilter",SkillFilter);
		request.getSession().setAttribute("OTTpeyFilter", OTTpeyFilter);
		request.getSession().setAttribute("ProductFilter", ProductFilter);
		request.getSession().setAttribute("GroupFilter", GroupFilter);
		
		// ÿҳ��¼����
		int ItemNumber = 10;
		// ��ҳ��
		int TotalPage = 0;
		System.out.println("list=="+list.size());
		TotalPage = list.size() / ItemNumber;
		System.out.println("TotalPage=="+TotalPage);
		if (list.size() % ItemNumber > 0)
			TotalPage++;
		System.out.println("TotalPage"+TotalPage);
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ��
		int page = 0;
		request.setAttribute("page", page);
		System.out.println("list=="+list.size());
		//this.turnPage(mapping, form, request, response, ItemNumber, page);
		// �õ�details��ҳ����Ҫ��list
		// list.get(0)��worktypelist; list.get(1)��layerphaselist
		List detailList = new ArrayList();
		try {
			conn = DataTools.getConnection(request);
			detailList = this.getDetailList(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != conn)
				conn.close();
		}
		request.getSession().setAttribute("worktypeList", detailList.get(0));
		request.getSession().setAttribute("layerList", detailList.get(1));
		request.getSession().setAttribute("testtypeList", detailList.get(2));
		request.getSession().setAttribute("milestoneList", detailList.get(3));
		//skillList,������ʾskillLevel��shortname
		request.getSession().setAttribute("skillList", skillList);
		
		//Added by FWJ on 2013-03-07
		request.getSession().setAttribute("targetlaunchList", detailList.get(4)); 
		//Added by FWJ on 2013-05-20
		request.getSession().setAttribute("budgetList", detailList.get(5)); 
		//Lock����С����  hanxiaoyu01 2013-01-21
		String minDate=null;
		if(3 == user.getLevelID()){//�����Approver�û�������С��Approve��Lock����
			minDate=user.getApproveday();
		}else{//�����checker�û�������С��All Data��Lock����
			try{
				conn=DataTools.getConnection(request);
				Groups g=GroupsDao.getInstance().findGroupByGroupId2(conn, -1);
				minDate=g.getLockday();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(conn!=null){
					conn.close();
				}
			}
			
		}
		request.getSession().setAttribute("minDate", minDate);
		return turnPage(mapping, form, request, response, ItemNumber, page);
	}

	/**
	 * �����û�����group��Ĭ��uiֵ��Ӧ��ݱ�groups��inputUITemplateId�ֶ�
	 * 
	 * @param groupname
	 * @param ui
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	private void saveGroupUI(String groupname, int ui,
			HttpServletRequest request) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			GroupsDao.getInstance().saveGroupUI(groupname, ui, stmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != conn)
				conn.close();
		}
	}

	/**
	 * ��projects�б� �зֽ�� ɸѡ���б� projectName,product,OTType
	 * 
	 * @param list
	 * @return filterlist
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 */
	private List getFilter(List<ExpenseData> list) {
		// ��filter�����������������filter�б�
		// note_0325:���������filter��project, skill, ottype, product, group
		// �����Ҫ�Ӹ���filter��ֻ����뷽��getXXXfilter(List<ExpenseData> list, List
		// XXXfilter)
		// �ڱ������е��þ���
		List filterlist = new ArrayList();
		// ����filter��������filter��
		List projectfilter = new ArrayList();
		List skillfilter = new ArrayList();
		List ottypefilter = new ArrayList();
		List productfilter = new ArrayList();
		List groupfilter = new ArrayList();
		// �õ�����filter
		this.getProjectFilter(list, projectfilter);
		this.getSkillFilter(list, skillfilter);
		this.getOTTpeyFilter(list, ottypefilter);
		this.getProductFilter(list, productfilter);
		this.getGroupFilter(list, groupfilter);
		// ����filter���õ��ˣ�Ȼ��ŵ���filter��
		filterlist.add(projectfilter);
		filterlist.add(skillfilter);
		filterlist.add(ottypefilter);
		filterlist.add(productfilter);
		filterlist.add(groupfilter);
		return filterlist;
	}

	// �õ�projectFilter����getFilter��������
	// @Author=lonzhe
	private List getProjectFilter(List<ExpenseData> list, List projectfilter) {
		int result = 0;
		if(null!=list && list.size()>0)
			projectfilter.add(list.get(0).getProjectName());
		else
			return projectfilter;
		// ˼ά��1. ��list���һ��Ԫ�طŵ�projectfilter��
		// 2. ��list���������Ԫ�ض��ó�����projectfilter���Ԫ����һ�Ƚϣ�
		// ֻҪ������ͬ�����ѭ����������ж�����ͬ��add��projectfilter�б�

		for (int i = 1; i < list.size(); i++)// ѭ��list
		{
			result = 0;
			for (int j = 0; j < projectfilter.size(); j++)// ѭ��p
			{
				if (list.get(i).getProjectName().equals(projectfilter.get(j))) {
					result = 1;
					break;
				}
			}
			if (result != 1)
				projectfilter.add(list.get(i).getProjectName());
		}
		// System.out.println("project filter list:");
		// for(int k=0;k<projectfilter.size();k++)
		// System.out.println(projectfilter.get(k));
		return projectfilter;
	}

	// �õ�productFilter����getFilter��������
	// @Author=lonzhe
	private List getSkillFilter(List<ExpenseData> list, List skillfilter) {
		int result = 0;
		if(null!=list && list.size()>0)
			skillfilter.add(list.get(0).getSkillLevel());
		else
			return skillfilter;
		// ˼ά��1. ��list���һ��Ԫ�طŵ�skillfilter��
		// 2. ��list���������Ԫ�ض��ó�����skillfilter���Ԫ����һ�Ƚϣ�
		// ֻҪ������ͬ�����ѭ����������ж�����ͬ��add��skillfilter�б�

		for (int i = 1; i < list.size(); i++)// ѭ��list
		{
			result = 0;
			for (int j = 0; j < skillfilter.size(); j++)// ѭ��p
			{
				if (list.get(i).getSkillLevel().equals(skillfilter.get(j))) {
					result = 1;
					break;
				}
			}
			if (result != 1)
				skillfilter.add(list.get(i).getSkillLevel());
		}
		// System.out.println("project filter list:");
		// for(int k=0;k<productfilter.size();k++)
		// System.out.println(productfilter.get(k));
		return skillfilter;
	}

	// �õ�ottypeFilter����getFilter��������
	// @Author=lonzhe
	private List getOTTpeyFilter(List<ExpenseData> list, List ottypefilter) {
		int result = 0;
		if(null != list && list.size()>0)
			ottypefilter.add(list.get(0).getOTType());
		else
			return ottypefilter;
		// ˼ά��1. ��list���һ��Ԫ�طŵ�projectfilter��
		// 2. ��list���������Ԫ�ض��ó�����projectfilter���Ԫ����һ�Ƚϣ�
		// ֻҪ������ͬ�����ѭ����������ж�����ͬ��add��projectfilter�б�

		for (int i = 1; i < list.size(); i++)// ѭ��list
		{
			result = 0;
			for (int j = 0; j < ottypefilter.size(); j++)// ѭ��p
			{
				if (list.get(i).getOTType().equals(ottypefilter.get(j))) {
					result = 1;
					break;
				}
			}
			if (result != 1)
				ottypefilter.add(list.get(i).getOTType());
		}
		// System.out.println("project filter list:");
		// for(int k=0;k<ottypefilter.size();k++)
		// System.out.println(ottypefilter.get(k));
		return ottypefilter;
	}

	// �õ�productFilter����getFilter��������
	// @Author=lonzhe
	private List getProductFilter(List<ExpenseData> list, List productfilter) {
		int result = 0;
		if(null != list && list.size()>0)
			productfilter.add(list.get(0).getProduct());
		else
			return productfilter;
		// ˼ά��1. ��list���һ��Ԫ�طŵ�productfilter��
		// 2. ��list���������Ԫ�ض��ó�����productfilter���Ԫ����һ�Ƚϣ�
		// ֻҪ������ͬ�����ѭ����������ж�����ͬ��add��productfilter�б�
		for (int i = 1; i < list.size(); i++)// ѭ��list
		{
			result = 0;
			for (int j = 0; j < productfilter.size(); j++)// ѭ��p
			{
				if (null!=list.get(i).getProduct() && list.get(i).getProduct().equals(productfilter.get(j))) {
					result = 1;
					break;
				}
			}
			if (result != 1)
				productfilter.add(list.get(i).getProduct());
		}
		return productfilter;
	}

	// �õ�GroupFilter����getFilter��������
	// @Author=lonzhe
	private List getGroupFilter(List<ExpenseData> list, List groupfilter) {
		int result = 0;
		
		//��д
		if(null != list && list.size()>0)
		{
			groupfilter.add(list.get(0).getGroupName());
		}
		else
			return groupfilter;
		// ˼ά��1. ��list���һ��Ԫ�طŵ�groupfilter��
		// 2. ��list���������Ԫ�ض��ó�����groupfilter���Ԫ����һ�Ƚϣ�
		// ֻҪ������ͬ�����ѭ����������ж�����ͬ��add��groupfilter�б�
		for (int i = 1; i < list.size(); i++)// ѭ��list
		{
			result = 0;
			for (int j = 0; j < groupfilter.size(); j++)// ѭ��p
			{
				if (list.get(i).getGroupName().equals(groupfilter.get(j))) {
					result = 1;
					break;
				}
			}
			if (result != 1)
				groupfilter.add(list.get(i).getGroupName());
		}
		return groupfilter;
	}

	/**
	 * ����˵Ĳ��� projectName,product,OTType
	 * 
	 * @param mapping,form,request,response
	 * @return ActionForward
	 * @throws Exception
	 * 
	 * @discribe ��session���õ��ܵ�list��Ȼ������ɸѡ���õ���list�ŵ�session���"displaylist"
	 * @Author=Longzhe
	 * @flag
	 */
	public ActionForward searchWithFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("enter");
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		// ��session���õ��ܵ�list Ȼ��ɸѡ������session���display list
		List<ExpenseData> list = (List<ExpenseData>) request.getSession().getAttribute("list");

		// ����˵������templist��ɸѡ�����ֱ����list�޸ģ���һͬ�޸ĵ�session���list
		List<ExpenseData> templist = new ArrayList<ExpenseData>();
		for (int i = 0; i < list.size(); i++)
			templist.add(list.get(i));

		System.out.println("templist.size="+templist.size());
		String componentfilter = request.getParameter("componentfilter");
		String page = request.getParameter("page");
		System.out.println("componentfilter: "+componentfilter);
		if(componentfilter.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (componentfilter.indexOf(templist.get(i).getProjectName())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.setAttribute("componentfilters", componentfilter);
		String productfilter = request.getParameter("productfilter");
		if(productfilter.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (productfilter.indexOf(templist.get(i).getProduct())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.setAttribute("productfilters", productfilter);
		
		String skillfilter = request.getParameter("skillfilter");
		if(skillfilter.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
//				if (skillfilter.indexOf(templist.get(i).getSkillLevel())<0) 
				if(!FilterTools.isElementContained(skillfilter, templist.get(i).getSkillLevel(), ",")) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.setAttribute("skillfilters", skillfilter);
		
		String ottypefilter=request.getParameter("ottypefilter");
		if(ottypefilter.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (ottypefilter.indexOf(templist.get(i).getOTType())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.setAttribute("ottypefilters", ottypefilter);
		if(user.getLevelID()<4)
		{
			String groupfilter=request.getParameter("groupfilter");
			if(groupfilter.indexOf(",")>0)
			{
				for (int i = 0; i < templist.size(); i++) 
				{
					if (groupfilter.indexOf(templist.get(i).getGroupName())<0) 
					{
						templist.remove(i);
						i--;
					}
				}
			}
			request.setAttribute("groupfilters", groupfilter);
		}
		int TotalPage = templist.size()/10 + 1;
		System.out.println("TotalPage in filter="+TotalPage);
		request.getSession().setAttribute("TotalPage", TotalPage);
		request.getSession().setAttribute("displaylist", templist);
		
		int p = 0;
		if(page!=null)
		{
			p = Integer.parseInt(page);
		}

		this.turnPage(mapping, form, request, response, 10, p);
		request.setAttribute("page", p);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ����ݿ��õ�details��woketype,layerphase�б� projectName,product,OTType
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 */
	private List getDetailList(Connection conn) throws Exception {
		// ����˵����DetailList���details������Ҫ��list
		// ����worktypelist, layerlist, testtype, testsession, milestone.
		List DetailList = new ArrayList();

		Statement stmt = null;
		List worktypeList = new ArrayList();
		List LayerphaseList = new ArrayList();
		List TestTypeList = new ArrayList();
		List MilestoneList = new ArrayList();
		/*Added by FWJ on 2013-03-07*/
		List TargetLaunchList = new ArrayList();
		/*Added by FWJ on 2013-05-20*/
		List BudgetList = new ArrayList();

		try {
			stmt = conn.createStatement();
			worktypeList = ExpenseDataDao.getInstance().searchWorktypeList(stmt);
			LayerphaseList = ExpenseDataDao.getInstance().searchLayerphaseList(stmt);
			TestTypeList = ExpenseDataDao.getInstance().searchTestTypeList(stmt);
			MilestoneList = ExpenseDataDao.getInstance().searchMilestoneList(stmt);
			TargetLaunchList = ExpenseDataDao.getInstance().searchTargetLaunchList(stmt);
			BudgetList = ExpenseDataDao.getInstance().searchBudgetList(stmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
			
			if (null != conn)
				conn.close();
		}
		DetailList.add(worktypeList);
		DetailList.add(LayerphaseList);
		DetailList.add(TestTypeList);
		DetailList.add(MilestoneList);
		DetailList.add(TargetLaunchList);
		DetailList.add(BudgetList);
		return DetailList;
	}

	/**
	 * ��ҳ ɸѡ����ҳ
	 * 
	 * @param itemnumber
	 *            ÿҳ��¼����
	 * @param page
	 *            ��ǰҳ��
	 * @return int
	 * 
	 * @Author=Longzhe page�ǵ�ǰҳ���ҳ��
	 * @flag
	 */
	public ActionForward turnPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			int ItemNumber, int page) {
		DataCheckerForm mainForm = (DataCheckerForm) form;
//		List<ExpenseData> list = (List<ExpenseData>) request.getSession().getAttribute("list");
		List<ExpenseData> list = (List<ExpenseData>) request.getSession().getAttribute("displaylist");
		System.out.println("list in turnPage="+list.size());
		List<ExpenseData> templist = new ArrayList<ExpenseData>();
		
		// �ж�ҳ�뷶Χ ���������ҳ�룬�͵����һҳ
		int maxpage = Integer.parseInt(request.getSession().getAttribute("TotalPage").toString());
//		int maxpage = list.size()%page + 1;
		if (page >= maxpage)
		{	
			page = maxpage - 1;
		}
		else
		{	
			page = page-1;	
		}

		// �ж�ҳ�뷶Χ ���С��0���͵���0
		if (page < 0)
			page = 0;
		
		// ��һ����¼
		int firstitem = page * ItemNumber;

		// ���һ����¼
		int lastitem = (page+1) * ItemNumber;
		// �ж����һ����¼�Ƿ񳬹�����
		if (lastitem > list.size())
			lastitem = list.size();
		for (int i = firstitem; i < lastitem; i++) 
		{
			templist.add(list.get(i));
		}
		System.out.println("page2="+page);
		System.out.println("page3="+maxpage);
		if (page >= maxpage)
		{
			request.setAttribute("page", maxpage);
		}
		else
		{
			request.setAttribute("page", page);
		}
		System.out.println("templist.size in turnPage2=" + templist.size());
//		request.getSession().setAttribute("TotalPage",maxpage);
		request.getSession().setAttribute("displaylist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}
/**
 * ��ҳ
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @flag
 */
	public ActionForward turnPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		return this.searchWithFilter(mapping, mainForm, request, response);
	}

	/**
	 * �ж������Ⱥ� ��������yyyy-MM-dd�ַ������ڱȽϴ�С���ж��Ƿ�lock
	 * 
	 * @param mapping
	 * @param form
	 * @return int
	 * 
	 * @Author=Longzhe
	 * 
	 * 0��lock��note0321:������1Ӧ����lock������0������һ����ô? 1��unlock,��Ӧ��1�о����Կ�ס�����
	 * @flag
	 */

	private int ifLock(String lockday, String day, int approved,
			int approvelevel) {
		// result = 1 unlock
		// result = 0 lock
		int result = 0;
		int locky = Integer.parseInt(lockday.substring(0, 4));
		int lockm = Integer.parseInt(lockday.substring(5, 7));
		int lockd = Integer.parseInt(lockday.substring(8, 10));

		int y = Integer.parseInt(day.substring(0, 4));
		day = day.substring(day.indexOf("-") + 1, day.length());// ��-��
		// System.out.println(day);
		int m = Integer.parseInt(day.substring(0, day.indexOf("-")));
		day = day.substring(day.indexOf("-") + 1, day.length());
		// System.out.println(day);
		int d = Integer.parseInt(day);

		// �����lockXС��X,����Ϊ1,unlock
		if (locky < y)// ���lock��С��ʵ���� unlock
		{
			result = 1;
			// return result;
		} else if (locky == y)// ���lock�����ʵ����
		{
			if (lockm < m) // ���lock��С��ʵ���� unlock
			{
				result = 1;
				// return result;
			} else if (lockm == m)// ���lock�µ���ʵ����
			{
				if (lockd < d) {
					result = 1;
					// return result;
				}
			}
		}
		return result;
	}
	
	/**
	 * ��DataChecker01��DataChecker04 �ҵ��û�Ȩ���µ�Groups
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward searchGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;

		SysUser su = (SysUser) request.getSession().getAttribute("user");		
		List<Groups> list = new ArrayList<Groups>();
		List<String> announcementlist = new ArrayList<String>();
		List<String> defaultuilist = new ArrayList<String>();

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			list = GroupsDao.getInstance().load(stmt, su.getGroupID());
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("glist", list);
		String gname;
		String announce;
		String dUI;
		String gid;
		for (int i = 0; i < list.size(); i++) {
			gname = (String) list.get(i).getGname();
			gid = String.valueOf(list.get(i).getGid());
			announce = list.get(i).getAnnouncement();
			
			dUI = String.valueOf(list.get(i).getDefaultUI());
			
			if (null == announce || "".equals(announce))
				announce = "Input Announcement";
			announcementlist.add(gname);
			announcementlist.add(announce);
			announcementlist.add(gid);
			
			defaultuilist.add(gname);
			defaultuilist.add(dUI);
			defaultuilist.add(gid);
		}
		request.setAttribute("alist", announcementlist);
		request.setAttribute("UIlist", defaultuilist);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ����ĳ��Group��Announcement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward saveAnnouncementandUI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String gid = (String) request.getParameter("GroupsId");
		System.out.println("gname="+gid);
		String announcement = (String) request.getParameter("announcement");
		announcement = announcement.replaceAll("'", "<quote>");
		String uifrompage = (String) request.getParameter("UIradio");
		if(null == uifrompage)
			uifrompage = "0";
		System.out.println("group announcement is: " + announcement);
		System.out.println("UI from page is: " + uifrompage);
		Connection conn = null;
		boolean result = false;
		try {
			conn = DataTools.getConnection(request);
			result = AnnounceTool.getInstance().setGroupAnnouncement(conn,Integer.parseInt(gid), announcement,Integer.parseInt(uifrompage));
			System.out.println("set group announcement & UI :" + result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}
		if(gid.equals(user.getGroupID()))
		{
			System.out.println("my group name:"+user.getGroupName());
			user.setDefaultUI(Integer.parseInt(uifrompage));
			request.getSession().setAttribute("user", user);
		}
		request.getSession().setAttribute("groupID", gid);
		return this.searchGroups(mapping, form, request, response);
	}

	/**
	 *
	 *  ��DataChecker04��DataChecker04_edit ����ĳһgroup�µ���Ŀ�ͼ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 * hanxiaoyu01 2013-02-19
	 */
	public ActionForward searchPandL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gid = (String) request.getParameter("GID");
		String gname = (String) request.getParameter("gname");
		String currentPage = (String) request.getParameter("currentPage");
		int pageNum = 1;
		if(currentPage!=null && !currentPage.equals("1")){
			pageNum = Integer.valueOf(currentPage);
		}
		if(gid==null)
		{
			gid = (String) request.getAttribute("GID");
		}
		if(gname==null)
		{
			gname = (String) request.getAttribute("gname");
		}
		System.out.println("to search for groupdID = "+gid+" , and GroupName is: "+gname);
		List<ExpenseData> Projectlist = new ArrayList<ExpenseData>(); // Project List
		List<SysUser> Userlist = new ArrayList<SysUser>(); // User List
		Connection conn = null;
		Statement stmt = null;
		String total = "0";
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			total = ExpenseDataDao.getInstance().searchTotalProjectsByGroupId(stmt, gid);
			Projectlist = ExpenseDataDao.getInstance().searchProjectByGid(stmt,Integer.parseInt(gid), pageNum);
			Userlist = GroupsDao.getInstance().getUserbyGid(stmt, Integer.parseInt(gid));
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) 
			{
				conn.close();
			}			
		}
		//dancy 2012-12-20 ȥ��session
		String ff = (String) request.getParameter("ff");
		System.out.println("ff is :"+ff);
		if(ff==null||ff.equals("back"))
		{
			request.getSession().removeAttribute("modifyresult");
		}
		request.setAttribute("projectlist", Projectlist);
		request.setAttribute("Userlist", Userlist);
		request.getSession().setAttribute("groupID", gid);
		request.getSession().setAttribute("gname", gname);
		request.setAttribute("totalSize", total);
		request.setAttribute("currentPage", String.valueOf(pageNum));
		return searchDetails(mapping, form, request, response);
	}
	

	/**
	 * ��data_checker_04_edit����Leave��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @Author=Longzhe
	 */
	public ActionForward saveLeaveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		LeaveData leavedata = new LeaveData();
		String leavetypevalue = (String) request.getParameter("leavetype");
		String Owner = (String) request.getParameter("Owner");
		String DefaultHours = (String) request.getParameter("DefaultHours");
		String DefaultUsed = (String) request.getParameter("DefaultUsed");
		String comments = (String) request.getParameter("comments");
		
		leavedata.setLeaveType(Integer.parseInt(leavetypevalue));
		leavedata.setOwner(Integer.parseInt(Owner));
		if (DefaultHours.trim().equals("")) DefaultHours="0";
		leavedata.setDefaultHours(Float.parseFloat(DefaultHours));
		if (DefaultUsed.trim().equals("")) DefaultUsed="0";
		leavedata.setDefaultUsed(Float.parseFloat(DefaultUsed));
		leavedata.setComments(comments);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().saveLeaveData(conn, leavedata);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}

	/*
	 * �õ�Sub Group���� @param mapping @param form @param request @param response
	 * @return @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward toAddGname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gid = (String) request.getParameter("GroupsId");
		request.getSession().setAttribute("groupID", gid);
		DataCheckerForm mainForm = (DataCheckerForm) form;
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ���� Group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward addGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String groupName = (String) request.getParameter("gname").trim();
		String comments = (String) request.getParameter("cms");
		String uifrompage = (String) request.getParameter("UIradio");
		comments = comments.replaceAll("'", "<quote>");
		// System.out.println("gname="+groupName+" comments="+comments);
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		Connection conn = null;
		int resultGID = -1;
		try {
			conn = DataTools.getConnection(request);
			resultGID = GroupsDao.getInstance().createGroup(conn, groupName,
					comments, Integer.parseInt(uifrompage),u.getLockday(),u.getApproveday());
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.getSession().setAttribute("groupID",String.valueOf(resultGID) );
		return this.searchGroups(mapping, form, request, response);
	}

	/**
	 * ͨ��id����Project
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward searchProjectByID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("search project by ID!");
		int pid = Integer.parseInt(request.getParameter("projectid"));
		System.out.println("projectID="+pid);
		String gid = (String) request.getParameter("gid");
		String gname = (String) request.getParameter("gname");
		System.out.println("pid="+pid+" gid="+gid+" gname="+gname);
		Project pro = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			pro = ProjectDao.getInstance().load(stmt, pid);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("Project", pro);
		request.setAttribute("gid", gid);
		request.setAttribute("gname", gname);
		return searchDetails(mapping, form, request, response);
	}
	
	/**
	 * ͨ��id����Leavedata
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward searchLeaveByID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String leaveid = request.getParameter("leaveid");
		String gid = (String) request.getParameter("gid");
		String gname = (String) request.getParameter("GroupName");
		
		LeaveData leavedata = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			leavedata = LeaveDataDao.getInstance().searchLeaveDataByID(stmt, leaveid);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("leavedata", leavedata);
		request.setAttribute("gid", gid);
		request.setAttribute("gname", gname);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ͨ��id����Leavedata
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward editLeaveData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String leaveid = request.getParameter("leaveid");
		String gid = (String) request.getParameter("gid");
		String gname = (String) request.getParameter("GroupName");
		String defaultHours = (String) request.getParameter("DefaultHours");
		String defaultUsed = (String) request.getParameter("DefaultUsed");
		String comments = (String) request.getParameter("comments");
		
		LeaveData leavedata = new LeaveData();
		leavedata.setId(Integer.parseInt(leaveid));
		leavedata.setDefaultHours(Float.parseFloat(defaultHours));
		leavedata.setDefaultUsed(Float.parseFloat(defaultUsed));
		leavedata.setComments(comments);
		System.out.println("getDefaultHours="+leavedata.getDefaultHours());
		System.out.println("getUsedHours="+leavedata.getUsedHours());
		
		boolean result = false;
		Connection conn = null;
		try {
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().editLeaveData(conn, leavedata);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", String.valueOf(result));
		request.setAttribute("gid", gid);
		request.setAttribute("gname", gname);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����ݿ��õ�skillLevel,location,OTType��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	private Vector getDetails(HttpServletRequest request) throws Exception {
		Vector v = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			v = ProjectDao.getInstance().searchDetails(stmt);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return v;
	}

	/**
	 * ����ݿ��õ�skillLevel,location,OTType��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Vector vec = new Vector();
		vec = this.getDetails(request);
		
		List<String> skillLevellist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> OTTypelist = new ArrayList<String>();
		List<String> userlist = new ArrayList<String>();
		List<String> componentlist = new ArrayList<String>();
		List<String> productlist = new ArrayList<String>();
		List<String> wbslist = new ArrayList<String>();
		
		skillLevellist = (List) vec.get(0);
		locationlist = (List) vec.get(1);
		OTTypelist = (List) vec.get(2);
		userlist = (List) vec.get(3);
		componentlist = (List) vec.get(4);
		productlist = (List) vec.get(5);
		wbslist = (List) vec.get(6);
		
		request.setAttribute("skillLevellist", skillLevellist);
		request.setAttribute("locationlist", locationlist);
		request.setAttribute("OTTypelist", OTTypelist);
		request.setAttribute("userlist", userlist);
		request.setAttribute("componentlist", componentlist);
		request.setAttribute("productlist", productlist);
		request.setAttribute("wbslist", wbslist);
		
		return searchGroups(mapping, form, request, response);
	}

	/**
	 * �޸�Project��Ϣ name,skillLevel,location,OTType,comments,confirm ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward modifyProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Project pro = new Project();
		String tid = request.getParameter("PID");
		int pid = -1;
		if ("".equals(tid) || null == tid) 
		{
			System.out.println("tid is null");
			return searchPandL(mapping, form, request, response);
		} else {
			pid = Integer.parseInt(request.getParameter("PID"));
		}
		
		String componentid = (String) request.getParameter("Component");
		System.out.println("componentid="+componentid);
		String product = (String) request.getParameter("Product").trim();
		String skillLevel = (String) request.getParameter("SkillLevel");
		String location = (String) request.getParameter("Location");
		String OTType = (String) request.getParameter("OTType");
		String wbs = (String) request.getParameter("wbs");

		// ȥ��comments����Ʊ���з��
		String comments = (String) request.getParameter("Comments").trim();
		Pattern p = Pattern.compile("\\t|\r|\n");
		Matcher m = p.matcher(comments);
		comments = m.replaceAll("");

		String confirm = (String) request.getParameter("Confirm");
		String hidden = (String) request.getParameter("Hidden");
		String moveto = (String) request.getParameter("move");
		String userid = (String) request.getParameter("user");
		pro.setComponentid(Integer.parseInt(componentid));
		pro.setProjectId(pid);
		pro.setProduct(product);
		pro.setSkillLevel(skillLevel);
		pro.setLocation(location);
		pro.setOTType(OTType);
		pro.setComments(comments);
		pro.setGroupId(Integer.parseInt(moveto));
		pro.setWBS(wbs);
		pro.setUserId(Integer.parseInt(userid));
		if (null == confirm)
			pro.setConfirm(0);
		else
			pro.setConfirm(1);
		
		if(null == hidden)
			pro.setHidden(0);
		else
			pro.setHidden(1);

		Connection conn = null;
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int result = 0;
		try {
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().modifyProject(conn, pro, mainForm);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("Modify project result is: " + result);
		request.getSession().setAttribute("modifyresult", String.valueOf(result));
		return searchPandL(mapping, form, request, response);
	}

	/**
	 * ɾ��û��confirm��Project
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward deleteTempProject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		boolean result = false;
		String projectid = (String) request.getParameter("projectid");
		Connection conn = null;
		try {
			conn = DataTools.getConnection(request);
			result = ProjectDao.getInstance().deleteTempProject(conn,Integer.parseInt(projectid));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != conn)
				conn.close();
		}
		System.out.println("delete temp project " + projectid + " " + result);
		List<ExpenseData> list = (List<ExpenseData>) request.getSession().getAttribute("list");
		if (result) {
			request.setAttribute("result", "true");
			for (int i = 0; i < list.size(); i++) {
				if (Integer.parseInt(projectid) == list.get(i).getProjectId())
					list.remove(i);
			}
		} else
			request.setAttribute("result", "false");
		request.getSession().setAttribute("list", list);
		//hanxiaoyu01 2013-01-04 �޸�session ���summaryֵ��
		request.getSession().setAttribute("summaryDay1", mainForm.getT1());
		request.getSession().setAttribute("summaryDay2", mainForm.getT2());
		request.getSession().setAttribute("summaryDay3", mainForm.getT3());
		request.getSession().setAttribute("summaryDay4", mainForm.getT4());
		request.getSession().setAttribute("summaryDay5", mainForm.getT5());
		request.getSession().setAttribute("summaryDay6", mainForm.getT6());
		request.getSession().setAttribute("summaryDay7", mainForm.getT7());
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ɾ��Project
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward deleteProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int pid = Integer.parseInt(request.getParameter("ProjectId"));
		System.out.println("deleting project, pid=" + pid);

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = ProjectDao.getInstance().deleteProject(stmt, pid);
			System.out.println("delete Project " + result);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		searchPandL(mapping, form, request, response);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ɾ��Leave
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward deleteLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int leaveid = Integer.parseInt(request.getParameter("LeaveId"));
		System.out.println("deleting leave, lid=" + leaveid);

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = LeaveDataDao.getInstance().deleteLeave(stmt, leaveid);
			System.out.println("delete leave " + result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		searchPandL(mapping, form, request, response);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ����Expensedata_details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             note_0411: ԭ��window.showModalDialog(sURL ,[vArguments]
	 *             [,sFeatures])�м�� url��������form���action
	 *             "DataCheckerAction.do?operate=toeditDataComments&operPage=data_checker_03_edit2&projectId=" +
	 *             projectId + "&selectedDate=" + selectedDate" Author=Longzhe
	 *@flag
	 */
	public ActionForward searchExpenseDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		System.out.println("enter");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		// ����˵���� selectedDate��ָdetails�������ڵڼ���
		// ��һ��Ϊstr1,�ڶ���Ϊstr2�����ƣ�pid��projectId��
		// ��ͨ������������ȷ��һ��expensedataID
		// hourΪchecker03ҳ���ϵ�hour��������detailsҳ���ж�detailsʱ���hour�Ƿ����
		String selectedDate = (String) request.getParameter("selectedDate");
		String pid = (String) request.getParameter("projectId");
		String hour = (String) request.getParameter("hour");

		// ����˵����firstDay��ʾ��һ�������,���selectedDate����ȷ��ѡ���������
		String firstDay = (String) request.getSession()
				.getAttribute("firstDay"); 
		firstDay = firstDay.replace("-", "/");

		// ����˵�� temp��������ѡ����������һ�����죬����ȷ��ѡ�������
		int temp = 0;
		System.out.println("selectedDate32="+selectedDate);
		//����˵�� islock���������Ƿ���������û������ܸı�workload��¼��
		String islock = (String) request.getParameter("islock");		
		request.setAttribute("islock", islock);

		if ("str1".equals(selectedDate))
			temp = 0;
		else if ("str2".equals(selectedDate))
			temp = 1;
		else if ("str3".equals(selectedDate))
			temp = 2;
		else if ("str4".equals(selectedDate))
			temp = 3;
		else if ("str5".equals(selectedDate))
			temp = 4;
		else if ("str6".equals(selectedDate))
			temp = 5;
		else if ("str7".equals(selectedDate))
			temp = 6;
		// ����ѡ�������
		System.out.println("temp="+temp);
		Date d = new Date(Date.parse(firstDay));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		selectedDate = ""+ df.format(new Date(d.getTime() + temp * 24 * 60 * 60 * 1000));
		String skillLevel = (String) request.getParameter("skillLevel");
		System.out.println("skillLevel is: "+skillLevel);
		
		List<ExpenseDetail> list = new ArrayList<ExpenseDetail>();
		List<Map> wlist = new ArrayList<Map>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			list = ExpenseDataDao.getInstance().loadExpenseDetails(stmt,Integer.parseInt(pid), selectedDate, user.getHeadorHour());
			System.out.println("list.size()="+list.size());
			//change Tester to Tester III, and keep Tester I & Tester II has the same work type list by dancy 2013/04/01
//			if(skillLevel.indexOf("Tester")<0&&list.size()>0)
//			if(skillLevel.indexOf("Tester")<0)
//			{
//				stmt.close();
//			}
//			else
//			{
				wlist = AdministratorDao.getInstance().searchWorkTypeList(stmt, skillLevel, list);
//			}
			System.out.println("wlist.size()="+wlist.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != conn)
				conn.close();
		}
		request.setAttribute("detaillist", list);
		request.setAttribute("wlist", wlist);
		request.setAttribute("projectid", pid);
		request.setAttribute("date", selectedDate);
		request.setAttribute("hour", hour);
		//hanxiaoyu01 2012-01-28
		request.setAttribute("skilllevel",skillLevel);
		System.out.println("out");
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ����Expensedata_details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward saveExpenseDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		//by collie 0510 ����Daily Workload
		String overwrite = (String) request.getParameter("overwrite");
		System.out.println("overwrite="+overwrite);
		double hours=0;
		
		boolean result = false;
		// ����˵����detailsize��ʾdetails������,comsize��ʾ�Ƿ���comments��Ϣ ���ص�submitҳ�棬���ڽ����ʵʱ���¡�
		int detailsize = 0;
		int comsize = 0;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		// ����˵�� pid��ʾprojectid��date��ʾ�������ڣ�
		// 1.��ͨ�������������ҵ���Ӧ��expensedata��2.Ȼ��ɾ����Ӧdetails��3.Ȼ������µ�details
		String pid = (String) request.getParameter("projectId");
		String date = (String) request.getParameter("date");
		// ����˵����list��������µ�ExpenseDetail����,������dao��д����ݿ⣻
		// temped������ÿһ��expensedetail��¼
		List<ExpenseDetail> list = new ArrayList<ExpenseDetail>();
		ExpenseDetail temped = null;
		// ���ҳ����Ϣ
		String[] worktype = (String[]) request.getParameterValues("ActivityType");
		String[] testtype = (String[]) request.getParameterValues("TestType");
		String[] milestone = (String[]) request.getParameterValues("Milestone");
		//Added by FWJ on 2013-03-07
		String[] targetlaunch = (String[]) request.getParameterValues("TargetLaunch");
		//Added by FWJ on 2013-05-22
		String[] budget = (String[]) request.getParameterValues("Budget");
		String[] descriptionofskill = (String[]) request.getParameterValues("descriptionofskill");
		//@Dancy 2011-10-25
		//hanxiaoyu01 2013-01-28
		//String comm =(String) request.getParameter("comm");
		String[]comms=(String[])request.getParameterValues("comms");
		String[] firmware = (String[]) request.getParameterValues("firmware");
		String[] hour = (String[]) request.getParameterValues("hour");
		Connection conn = null;
		// �����expensedetails �ͱ���details
		if (null != hour && hour.length > 0) 
		{
			for (int i = 0; i < hour.length; i++) 
			{
				if(!"".equals(hour[i])&&null!=hour[i]&&!"0".equals(hour[i]))
				{
					temped = new ExpenseDetail();
					temped.setWorktypeid(Integer.parseInt(worktype[i]));
					System.out.println("worktype[i]="+worktype[i]);
					if("comments".equals(comms[i]))
					{
						temped.setComm("");
					}
					else
					{
						temped.setComm(comms[i]);
					}
					//Milestone
					temped.setMilestone(Integer.parseInt(milestone[i]));
					if( null == firmware[i])
					{
						temped.setFirmware("");
					}
					else
					{
						temped.setFirmware(firmware[i]);
					}
					//Added by FWJ on 2013-03-08
					temped.setTargetlaunchid(Integer.parseInt(targetlaunch[i]));
					//Added by FWJ on 2013-05-22
					temped.setBudgetid(Integer.parseInt(budget[i]));
					temped.setDescriptionofskill(descriptionofskill[i]);
					System.out.println("testtype[i]="+testtype[i]);
					temped.setTesttype(Integer.parseInt(testtype[i]));
					temped.setHour(hour[i]);
					list.add(temped);
				}
			}
			try 
			{
				conn = DataTools.getConnection(request);
				result = ExpenseDataDao.getInstance().saveExpenseDetails(conn,list, Integer.parseInt(pid), date, user.getHeadorHour());
			} catch (Exception e) 
			{
				// ���������Ϣ
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
				this.saveErrors(request, errors);
				e.printStackTrace();
			} 

		}
		 else// ���û�оͱ���comments
			{
				try
				{
					conn = DataTools.getConnection(request);
					result = ExpenseDataDao.getInstance().saveDataComments(conn,Integer.parseInt(pid), date);
					
				} catch (Exception e) 
				{
					// ���������Ϣ
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
					this.saveErrors(request, errors);
					e.printStackTrace();
				}
			}
		
		//2013/04/02 by dancy ������ʽ��hours��ͺ��ֵ
		NumberFormat formate = NumberFormat.getNumberInstance();
		formate.setMaximumFractionDigits(4);// �趨С�����Ϊ�� ����ô��ʾ���������������
		
		//by collie 0510 ����Daily Workload
		try{
			if (overwrite.equals("true"))
			{
				ExpenseData expenseData=new ExpenseData();
				expenseData.setUserId(user.getUserId());
				expenseData.setExpenseDataId(0);
				expenseData.setProjectId(Integer.parseInt(pid));
				expenseData.setComments(date);
				
				//by collie 0511 ��;�����һЩС��
				
				for (int i = 0; i < list.size(); i++) 
				{
					String h = list.get(i).getHour();
					hours = hours+ Double.parseDouble(h);
				}

				if(0==user.getHeadorHour())//����û�����ͷ��ʽ��д��Ҫ����� hour ��ݿ���hours
				{
					hours = hours*8;
				}
				if (hours==0)
				{
					expenseData.setHours("0");
				}
				else
				{
					expenseData.setHours(formate.format(hours));
				}
				System.out.println("hours in save detail is:"+formate.format(hours));
				
				ExpenseDataDao.getInstance().saveExpenseData(conn, expenseData, (BaseActionForm) form);
			}
		}catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		if (result)//����ɹ�,��detailsize��comsize���ص�submitҳ�棬ʵ��ҳ��ʵʱ���¡�
		{
			request.setAttribute("saveresult", "true");
			request.setAttribute("detailsize", detailsize+"");
			request.setAttribute("comsize", comsize+"");
			// by collie 0510 Details����ʱ�Զ�����Daily����ݡ�
			if (overwrite.equals("true"))
			{
				
				if (hours==0)
				{
					request.setAttribute("sumofdetails", "0");
				}
				else
				{
					if(0==user.getHeadorHour())//����û�����ͷ��ʽ��д��Ҫ����� hour ��ݿ���hours ��ʾʱ Ҫ������
					{
						hours = hours/8;
					}
					request.setAttribute("sumofdetails", formate.format(hours));
					System.out.println("god bless us, transe hours is:"+formate.format(hours));
				}
			}
		}
		else
		{
			request.setAttribute("saveresult", "false");
			request.setAttribute("detailsize", "0");
			request.setAttribute("comsize", "0");
		}
		//��sessionlist��������ͬ�������£����ֳ־�
		/* ��ô�ҵ���Ӧ��expensedata�أ������ʵĺã�ͨ����������
		 * 1.pid ��Ŀid ��2.hourx ��hour������
		 * */
		String inputid = (String) request.getParameter("inputid");
		inputid = inputid.substring(1);
		int hourx = Integer.parseInt(inputid)%7;
		if(hourx==0)
			hourx=7;
		
		List<ExpenseData> sessionlist = (ArrayList<ExpenseData>)request.getSession().getAttribute("list");
		ExpenseData ed = null;
		
		
		for(int i=0;i<sessionlist.size();i++)
		{
			ed = sessionlist.get(i);
			if(Integer.parseInt(pid)==ed.getProjectId() )//�ҵ�pid��ͬ��ExpenseData,�޸�detailscount��comments
			{
				switch(hourx){ //����Ӧ��detailscount�޸�
					case 1: sessionlist.get(i).setDetailscount1(detailsize);
//							sessionlist.get(i).setComment1(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour1("0");
								}else{
									sessionlist.get(i).setHour1(formate.format(hours));
								}
							}
							break;
					case 2: sessionlist.get(i).setDetailscount2(detailsize);
//							sessionlist.get(i).setComment2(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour2("0");
								}else{
									sessionlist.get(i).setHour2(formate.format(hours));
								}
							}
							break;
					case 3: sessionlist.get(i).setDetailscount3(detailsize);
//							sessionlist.get(i).setComment3(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour3("0");
								}else{
									sessionlist.get(i).setHour3(formate.format(hours));
								}
							}
							break;
					case 4: sessionlist.get(i).setDetailscount4(detailsize);
//							sessionlist.get(i).setComment4(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour4("0");
								}else{
									sessionlist.get(i).setHour4(formate.format(hours));
								}
							}
							break;
					case 5: sessionlist.get(i).setDetailscount5(detailsize);
//							sessionlist.get(i).setComment5(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour5("0");
								}else{
									sessionlist.get(i).setHour5(formate.format(hours));
								}
							}
							break;
					case 6: sessionlist.get(i).setDetailscount6(detailsize);
//							sessionlist.get(i).setComment6(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour6("0");
								}else{
									sessionlist.get(i).setHour6(formate.format(hours));
								}
							}
							break;
					case 7: sessionlist.get(i).setDetailscount7(detailsize);
//							sessionlist.get(i).setComment7(comment);
							//by collie 0510 ����Daily Workload
							if (overwrite.equals("true")){
								if (hours==0){
									sessionlist.get(i).setHour7("0");
								}else{
									sessionlist.get(i).setHour7(formate.format(hours));
								}
							}
							break;
				}
				break;
			}
		}
		request.getSession().setAttribute("list", sessionlist);//������֮�����ݷŵ�session��
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ����details��Ϣ
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
	public ActionForward copyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		DataCheckerForm mainForm = (DataCheckerForm) form;
		System.out.println("copyDetails");
		String projectid = (String)request.getParameter("projectId");
		String date = (String)request.getParameter("date");
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		List<ExpenseDetail> list = new ArrayList<ExpenseDetail>();
		ExpenseDetail temped = null;
		// ���ҳ����Ϣ
		String[] worktype=(String[])request.getParameterValues("worktypeName");//hanxiaoyu01 2013-01-31
		String[] worktypeid = (String[]) request.getParameterValues("ActivityType");
		String[] testtype = (String[]) request.getParameterValues("TestType");
		String[] milestone = (String[]) request.getParameterValues("Milestone");
		String[] firmware = (String[]) request.getParameterValues("firmware");
		String[] hour = (String[]) request.getParameterValues("hour");
		//@Dancy 2011-11-09���description��comm
		String[] comm = (String[]) request.getParameterValues("comms");
		//Added by FWJ on 2013-03-07
		String[] targetlaunch = (String[]) request.getParameterValues("TargetLaunch");
		//add by dancy 2013-04-02
		String skillLevel = (String) request.getParameter("d_skillLevel");
		//Added by FWJ on 2013-05-22
		String[] budget = (String[]) request.getParameterValues("Budget");
		String[] descriptionofskill = (String[]) request.getParameterValues("descriptionofskill");
		
		String hours = "";
		
		// �����expensedetails �ͱ���details
		if (null != hour && hour.length > 0) 
		{
			for (int i = 0; i < hour.length; i++) 
			{
					temped = new ExpenseDetail();
					if(null == worktypeid[i] || "".equals(worktypeid[i]))
					{
						request.setAttribute("detaillist", list);
						request.setAttribute("projectid", projectid);
						request.setAttribute("date", date);
						request.setAttribute("cmd", "Copy Fail!");
						return mapping.findForward(mainForm.getOperPage());
					}
					temped.setWorktypeid(Integer.parseInt(worktypeid[i]));
					temped.setWorktype(worktype[i]);
					//Milestone
					//��֤�Ƿ�����milestone[i]...û�취 ��Ҳ����ҳ����֤������ҳ������jQuery ��֤���˰�������
					if(null == milestone[i] || "".equals(milestone[i]))
					{
						request.setAttribute("detaillist", list);
						request.setAttribute("projectid", projectid);
						request.setAttribute("date", date);
						request.setAttribute("cmd", "Copy Fail!");
						return mapping.findForward(mainForm.getOperPage());
					}
					temped.setMilestone(Integer.parseInt(milestone[i]));
					
					if(null == targetlaunch[i] || "".equals(targetlaunch[i]))
					{
						request.setAttribute("detaillist", list);
						request.setAttribute("projectid", projectid);
						request.setAttribute("date", date);
						request.setAttribute("cmd", "Copy Fail!");
						return mapping.findForward(mainForm.getOperPage());
					}
					
					temped.setTargetlaunchid(Integer.parseInt(targetlaunch[i]));
					//Added by FWJ on 2013-05-22
					if(null == budget[i] || "".equals(budget[i]))
					{
						request.setAttribute("detaillist", list);
						request.setAttribute("projectid", projectid);
						request.setAttribute("date", date);
						request.setAttribute("cmd", "Copy Fail!");
						return mapping.findForward(mainForm.getOperPage());
					}
					temped.setBudgetid(Integer.parseInt(budget[i]));
					temped.setDescriptionofskill(descriptionofskill[i]);
					//firmware version
					temped.setFirmware(firmware[i]);
					//testtype
					if(null == testtype[i] || "".equals(testtype[i]))
					{
						request.setAttribute("detaillist", list);
						request.setAttribute("projectid", projectid);
						request.setAttribute("date", date);
						request.setAttribute("cmd", "Copy Fail!");
						return mapping.findForward(mainForm.getOperPage());
					}
					temped.setTesttype(Integer.parseInt(testtype[i]));
					//hour
					hours = hours + hour[i];
					temped.setHour(hour[i]);
					//commnets
					temped.setComm(comm[i]);
					list.add(temped);
			}
		}
		System.out.println("sum hours in copy = "+hours);
		System.out.println("copylist size="+list.size());
		
		request.setAttribute("detaillist", list);
		request.setAttribute("projectid", projectid);
		request.setAttribute("date", date);
		if(!hours.equals(""))
		{
			request.setAttribute("cmd", "Copy Success!");
			request.getSession().setAttribute("copylist", list);
		}
		else
		{
			request.setAttribute("cmd", "No data in copy list!");
		}
		//hanxiaoyu01 2013-01-31
		request.setAttribute("skilllevel",skillLevel);
		
		System.out.println("###########skillLevel=" + skillLevel);
		//add the copy floag by FWJ 2013-09-03
//		String copyflag="Other";
//		if(skillLevel.indexOf("Tester")>=0)
//		{
//			copyflag="Tester";
//		}
//		else if(skillLevel.indexOf("Engineer")>=0)
//		{
//			copyflag="Engineer";
//		}
//		else if(skillLevel.indexOf("Lead")>=0)
//		{
//			copyflag="Lead";
//		}
//		else if(skillLevel.indexOf("Manager")>=0)
//		{
//			copyflag="Manager";
//		}
		String copyflag = skillLevel;
		//ui��ʾcopy����������ĸ�UI����ͬUI֮�䲻���໥paste
		String ui = (String) request.getParameter("ui");
		System.out.println("ui="+ui);
		request.getSession().setAttribute("copyUI", ui);
		request.getSession().setAttribute("copyflag", copyflag);
		return mapping.findForward(mainForm.getOperPage());	
	}
	/**
	 * ճ��details��Ϣ
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
	public ActionForward pasteDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		DataCheckerForm mainForm = (DataCheckerForm) form;
		System.out.println("pasteDetails");
		
		String projectid = (String)request.getParameter("projectId");
		String date = (String)request.getParameter("date");

		//add by dancy 2013-04-02
		String skillLevel = (String) request.getParameter("d_skillLevel");
		List<ExpenseDetail> list = new ArrayList<ExpenseDetail>();
		ExpenseDetail temped = null;
		// ���ҳ����Ϣ
		String[] worktype=(String[])request.getParameterValues("worktypeName");//hanxiaoyu01 2013-01-31
		String[] worktypeid = (String[]) request.getParameterValues("ActivityType");
		String[] testtype = (String[]) request.getParameterValues("TestType");
		String[] milestone = (String[]) request.getParameterValues("Milestone");
		String[] firmware = (String[]) request.getParameterValues("firmware");
		String[] hour = (String[]) request.getParameterValues("hour");
		//@Dancy 2011-11-09���description��comm
		String[] comm = (String[]) request.getParameterValues("comms");
		//Added by FWJ on 2013-03-07
		String[] targetlaunch = (String[]) request.getParameterValues("TargetLaunch");
		//Added by FWJ on 2013-05-22
		String[] budget = (String[]) request.getParameterValues("Budget");
		String[] descriptionofskill = (String[]) request.getParameterValues("descriptionofskill");
		
		if (null != hour && hour.length > 0) 
		{
			for (int i = 0; i < hour.length; i++) 
			{
					temped = new ExpenseDetail();
					temped.setWorktypeid(Integer.parseInt(worktypeid[i]));
					temped.setWorktype(worktype[i]);
					temped.setMilestone(Integer.parseInt(milestone[i]));
					temped.setTargetlaunchid(Integer.parseInt(targetlaunch[i]));
					//firmware version
					temped.setFirmware(firmware[i]);
					//testtype
					temped.setTesttype(Integer.parseInt(testtype[i]));
					//hour
					temped.setHour(hour[i]);
					//commnets
					temped.setComm(comm[i]);
					//Added by FWJ on 2013-05-22
					temped.setBudgetid(Integer.parseInt(budget[i]));
					temped.setDescriptionofskill(descriptionofskill[i]);
					list.add(temped);
			}
		}
		
		List<ExpenseDetail> copylist = (List<ExpenseDetail>)request.getSession().getAttribute("copylist");
		//FWJ 2013-09-03
		String copyflag = (String)request.getSession().getAttribute("copyflag");
		 //copylistΪ�����Ƶ��������
		if(copylist==null||!FilterTools.isUseSameTemplate(copyflag, skillLevel))
		{
			request.setAttribute("cmd", "Not data in paste list!");
			request.setAttribute("detaillist", list);
		}
		else
		{
			request.setAttribute("cmd", "Paste Success!");
			request.setAttribute("detaillist", copylist);
		}
		
		request.setAttribute("projectid", projectid);
		request.setAttribute("date", date);
		request.setAttribute("skilllevel",skillLevel);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @update by dancy 2012-11-15
	 * @flag
	 */
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String gid = (String) request.getParameter("GID");
		String gname = (String) request.getParameter("GroupsName");
		String UserId = (String) request.getParameter("UserId");
		//@Dancy 2011-10-10
		String leaveTime = request.getParameter("leaveTime");
		//System.out.println("leavDate"+leaveTime);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = SysUserDao.getInstance().deleteUL(stmt, leaveTime, UserId);
			System.out.println("delete User " + result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.delete"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}
		request.setAttribute("GID", gid);
		request.setAttribute("gname", gname);
		return searchPandL(mapping, form, request, response);
//		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ��ѯ����rates�б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward searchRates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Vector vec = new Vector();
		vec = this.getDetails(request);
		List<String> skillLevellist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> OTTypelist = new ArrayList<String>();
		skillLevellist = (List) vec.get(0);
		locationlist = (List) vec.get(1);
		OTTypelist = (List) vec.get(2);

		Connection conn = null;
		Statement stmt = null;
		List<String> ratelist = new ArrayList<String>();
		try {
			conn = DataTools.getConnection(request);
			ratelist = RateDao.getInstance().searchRates(conn);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		request.setAttribute("skillLevellist", skillLevellist);
		request.setAttribute("locationlist", locationlist);
		request.setAttribute("OTTypelist", OTTypelist);
		request.setAttribute("ratelist", ratelist);

		return mapping.findForward(mainForm.getOperPage());
	}

	/*
	 * ͨ��groupId�ҵ�group�µ�user
	 */
	public ActionForward searchUserByGID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String gid = (String) request.getParameter("gid");
		List<SysUser> Userlist = new ArrayList<SysUser>(); // User List
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Userlist = GroupsDao.getInstance().getUserbyGid(stmt, Integer.parseInt(gid));
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("Userlist", Userlist);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ����һ��rate��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward addRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String skillLevel = (String) request.getParameter("skillLevel");
		String location = (String) request.getParameter("location");
		String OTType = (String) request.getParameter("OTType");
		Double rate = Double.parseDouble(request.getParameter("rate"));
		System.out.println("skillLevel=" + skillLevel + "location=" + location
				+ "" + "OTType=" + OTType + "rate=" + rate);
		String result = "";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = RateDao.getInstance().addRate(stmt, skillLevel, location,
					OTType, rate);
			System.out.println(result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		return searchRates(mapping, form, request, response);
	}

	/**
	 * ɾ��һ��rate��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward deleteRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int rateid = Integer.parseInt(request.getParameter("rateId"));
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = RateDao.getInstance().deleteRate(stmt, rateid);
			// System.out.println("delete rate "+rateid+" "+result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null) {
				conn.close();
			}
		}
		return searchRates(mapping, form, request, response);
	}

	/**
	 * ����һ��rate��ϸ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward searchRatebyId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int rateid = Integer.parseInt(request.getParameter("rateId"));
		System.out.println(rateid);
		Rate rate = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			rate = RateDao.getInstance().searchRatebyId(stmt, rateid);
			if (null == rate)
				return mapping.findForward(mainForm.getOperPage());
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null) {
				conn.close();
			}
		}
		Vector vec = this.getDetails(request);
		List<String> skillLevellist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> OTTypelist = new ArrayList<String>();
		skillLevellist = (List) vec.get(0);
		locationlist = (List) vec.get(1);
		OTTypelist = (List) vec.get(2);

		request.setAttribute("skillLevellist", skillLevellist);
		request.setAttribute("locationlist", locationlist);
		request.setAttribute("OTTypelist", OTTypelist);
		request.setAttribute("rate", rate);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * �޸ı���һ��rate��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 */
	public ActionForward saveRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int rateid = Integer.parseInt(request.getParameter("rateId"));
		String skillLevel = (String) request.getParameter("skillLevel");
		String location = (String) request.getParameter("location");
		String OTType = (String) request.getParameter("OTType");
		Double rate = Double.parseDouble(request.getParameter("rate"));
		// System.out.println("skillLevel="+skillLevel+"location="+location+""+"OTType="+OTType+"rate="+rate);

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = RateDao.getInstance().saveRate(stmt, rateid,
					skillLevel, location, OTType, rate);
			System.out.println("modify rate " + rateid + " " + result);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null) {
				conn.close();
			}
		}
		return searchRates(mapping, form, request, response);
	}

	/**
	 * ��DataChecker11����DataChecker01
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Author=Longzhe
	 * @flag
	 */
	public ActionForward backPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		request.getSession().removeAttribute("groupID");
		request.getSession().removeAttribute("modifyresult");
		//移除session中的值。 FWJ on 2013-04-26
		request.getSession().removeAttribute("statefilters");
		request.getSession().removeAttribute("pofilters2");
		request.getSession().removeAttribute("lockstatusfilters");
		request.getSession().removeAttribute("managerfilters");
		request.getSession().removeAttribute("polist");
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ���³�ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * Modified on 2011-03-01 by ���� �п��ܴ�request��������String header
	 * ������jsp���ж��Ƿ���ʾMyInforHead.jsp
	 */
	public ActionForward toeditProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;

		String header = "";
		header = (String) request.getParameter("header");

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			mainForm.setProject(ExpenseDataDao.getInstance().loadProject(stmt,
					Integer.parseInt(mainForm.getRecid())));
			mainForm.setComments(mainForm.getProject().getComments());
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("header", header);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ���³�ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toeditDataComments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		System.out.println(mainForm.getExpenseData().getComments());
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();

			String selectedDate = (String) request.getParameter("selectedDate");
			String pid = (String) request.getParameter("projectId");
			System.out.println(pid + ":" + selectedDate);

			Date today = new Date(Date.parse(mainForm.getToday()));

			int theRealDay = today.getDay();
			System.out.println("today=" + theRealDay);
			if (theRealDay == 0)
				theRealDay = 7;

			if (selectedDate.equalsIgnoreCase("str1")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 1 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str2")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 2 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str3")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 3 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str4")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 4 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str5")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 5 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str6")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 6 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str7")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 7 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			}
			System.out.println(selectedDate);
			// mainForm.setExpenseData(ExpenseDataDao.getInstance()
			// .loadDataComments(stmt, mainForm.getProjectId(),
			// selectedDate));
			/*
			 * if (null == mainForm.getExpenseData().getComments()) {
			 * mainForm.setComments(""); } else {
			 * mainForm.setComments(mainForm.getExpenseData().getComments()); }
			 */
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Connection conn = null;
		try {
			conn = DataTools.getConnection(request);
			ProjectDao.getInstance().saveProject(conn, mainForm.getProject(),
					mainForm);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		if (mainForm.getErrors().isEmpty()
				&& mainForm.getStrErrors().length() == 0) {
			mainForm.setStrErrors("true");
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDataComments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Connection conn = null;
		try {
			conn = DataTools.getConnection(request);
			// ExpenseDataDao.getInstance().saveDataComments(conn,
			// mainForm.getExpenseData(), mainForm);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		if (mainForm.getErrors().isEmpty()
				&& mainForm.getStrErrors().length() == 0) {
			mainForm.setStrErrors("true");
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * save headorhour��Ϣ ��ҳ��headorhour�����˵��ı䲢���save all ��ť��ʱ����ø÷���
	 * 
	 * @param headorhour
	 * @return
	 * @throws Exception
	 * @author longzhe
	 * @flag
	 */
	private void saveHeadorHour(int headorhour, String groupname, 
			HttpServletRequest request) throws Exception
	{
		System.out.println("set group "+groupname+" headorhour to "+headorhour);
		Connection con = null;
		Statement stmt = null;
		try{
			con = DataTools.getConnection(request);
			stmt = con.createStatement();
			GroupsDao.getInstance().saveGroupHeadorHour(groupname, headorhour, stmt);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null != con){
				con.close();
			}
		}
		
	}
	
	/**
	 * save all��Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 */
	public ActionForward saveAllExpenseData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		//����Ƿ���HeadORHour ���ı�ͱ���
		int headorhour = Integer.parseInt(request.getParameter("HeadORHour"));
		String page = (String) request.getParameter("page");
		System.out.println("page="+page);
		request.setAttribute("page", page);
		
		// ��Ŀ���б�
		List<ExpenseData> list = new ArrayList<ExpenseData>();
		list = (ArrayList<ExpenseData>) request.getSession().getAttribute(
				"displaylist");
		//System.out.println("list.size()=" + list.size());

		// ��Ҫ���µ�expensedata�б�
		List<ExpenseData> uplist = new ArrayList<ExpenseData>();

		// ѭ��ÿ����Ŀ���Ƚ�ÿ���hours�Ƿ���ͬ�����ͬ���ͷŽ�uplist����ݿ����
		// ����˵����expensename_ҳ����hoursԪ�ص����֣�x_����ҳ��Ԫ�����ֵ���
		// ����˵����h1~h7_����ҳ��Ԫ�ص�����
		// ����˵����firstDay_����expense�����ڣ����ں�projectIdΨһָ��һ��expensedata��¼
		String expensename = "";
		int x = 0;
		String h1 = "";
		String h2 = "";
		String h3 = "";
		String h4 = "";
		String h5 = "";
		String h6 = "";
		String h7 = "";
		String firstDay = (String) request.getParameter("firstDay");
		firstDay = firstDay.replace("-", "/");
		// �����ʼ��� tmd��̫�����ˣ�Ҫ��2011-2-28����2011/2/28֮�󣬲��ܱ�Date.parse(firstDay)

		for (int i = 0; i < list.size(); i++) {
			// �õ�ÿ����Ŀ��expense
			ExpenseData expense = list.get(i);
			// 1 �õ�ҳ���7��expensedata
			x = i * 7 + 1;
			expensename = String.valueOf(x);
			h1 = (String) request.getParameter(expensename).trim();
			// ȥ���ַ�ǰ���0����04���4���ۣ����䡱���õú�����ѽ��
			// modify0315:�����ж�.(С���)����Ȼ0.5����.5
			// modify0317:�����ж�h1.length()>1���ȣ���Ȼ0ΪתΪ��
			while (h1.indexOf("0") == 0 && h1.indexOf(".") != 1
					&& h1.length() > 1) {
				h1 = h1.substring(1);
			}

			x = i * 7 + 2;
			expensename = String.valueOf(x);
			h2 = (String) request.getParameter(expensename).trim();
			while (h2.indexOf("0") == 0 && h2.indexOf(".") != 1
					&& h2.length() > 1) {
				h2 = h2.substring(1);
			}

			x = i * 7 + 3;
			expensename = String.valueOf(x);
			h3 = (String) request.getParameter(expensename).trim();
			while (h3.indexOf("0") == 0 && h3.indexOf(".") != 1
					&& h3.length() > 1) {
				h3 = h3.substring(1);
			}

			x = i * 7 + 4;
			expensename = String.valueOf(x);
			h4 = (String) request.getParameter(expensename).trim();
			while (h4.indexOf("0") == 0 && h4.indexOf(".") != 1
					&& h4.length() > 1) {
				h4 = h4.substring(1);
			}

			x = i * 7 + 5;
			expensename = String.valueOf(x);
			h5 = (String) request.getParameter(expensename).trim();
			while (h5.indexOf("0") == 0 && h5.indexOf(".") != 1
					&& h5.length() > 1) {
				h5 = h5.substring(1);
			}

			x = i * 7 + 6;
			expensename = String.valueOf(x);
			h6 = (String) request.getParameter(expensename).trim();
			while (h6.indexOf("0") == 0 && h6.indexOf(".") != 1
					&& h6.length() > 1) {
				h6 = h6.substring(1);
			}

			x = i * 7 + 7;
			expensename = String.valueOf(x);
			h7 = (String) request.getParameter(expensename).trim();
			while (h7.indexOf("0") == 0 && h7.indexOf(".") != 1
					&& h7.length() > 1) {
				h7 = h7.substring(1);
			}

			// 2 �Ƚ�ԭ������ֵ��ҳ�����ֵ�����ͬ&��Ϊ�վͼ���uplist
			if (!expense.getHour1().equals(h1)) {
				System.out.println("��" + i + "����Ŀh1�ı�Ϊ��" + h1);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());

				e.setComments(firstDay.replace("/", "-"));

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h1)/8));
					e.setHours(String.valueOf((Double.parseDouble(h1)/8)));
				}
				else
					e.setHours(h1);
				uplist.add(e);
				list.get(i).setHour1(h1);
			}
			if (!expense.getHour2().equals(h2)) {
				System.out.println("��" + i + "����Ŀh2�ı�Ϊ��" + h2);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 1 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				e.setCreateTime(expense.getCreateTime());
				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h2)/8));
					e.setHours(String.valueOf((Double.parseDouble(h2)/8)));
				}
				else
					e.setHours(h2);
				uplist.add(e);
				list.get(i).setHour2(h2);
			}
			if (!expense.getHour3().equals(h3)) {
				System.out.println("��" + i + "����Ŀh3�ı�Ϊ��" + h3);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				e.setCreateTime(expense.getCreateTime());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 2 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h3)/8));
					e.setHours(String.valueOf((Double.parseDouble(h3)/8)));
				}
				else
					e.setHours(h3);
				uplist.add(e);
				list.get(i).setHour3(h3);
			}
			if (!expense.getHour4().equals(h4)) {
				System.out.println("��" + i + "����Ŀh4�ı�Ϊ��" + h4);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				e.setCreateTime(expense.getCreateTime());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 3 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h4)/8));
					e.setHours(String.valueOf((Double.parseDouble(h4)/8)));
				}
				else
					e.setHours(h4);
				uplist.add(e);
				list.get(i).setHour4(h4);
			}
			if (!expense.getHour5().equals(h5)) {
				System.out.println("��" + i + "����Ŀh5�ı�Ϊ��" + h5);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				e.setCreateTime(expense.getCreateTime());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 4 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h5)/8));
					e.setHours(String.valueOf((Double.parseDouble(h5)/8)));
				}
				else
					e.setHours(h5);
				uplist.add(e);
				list.get(i).setHour5(h5);
			}
			if (!expense.getHour6().equals(h6)) {
				System.out.println("��" + i + "����Ŀh6�ı�Ϊ��" + h6);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				e.setCreateTime(expense.getCreateTime());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 5 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h6)/8));
					e.setHours(String.valueOf((Double.parseDouble(h6)/8)));
				}
				else
					e.setHours(h6);
				uplist.add(e);
				list.get(i).setHour6(h6);
			}
			if (!expense.getHour7().equals(h7)) {
				System.out.println("��" + i + "����Ŀh7�ı�Ϊ��" + h7);
				ExpenseData e = new ExpenseData();
				e.setProjectId(expense.getProjectId());
				e.setCreateTime(expense.getCreateTime());
				// ͨ���һ�������firstDay���õ���һ�������
				String selectedDate = new Date(Date.parse(firstDay) + 6 * 60
						* 1000 * 60 * 24).toLocaleString().replaceAll(
						"0:00:00", "");
				System.out.println("�����ǣ�" + selectedDate);
				// ��comments����ʱ������ ��Ȼ��Ҫ�ַ�ת�� ̫������̫�����ˡ�����
				e.setComments(selectedDate);

				//�����жϣ������1(hour)����Ҫ����8 �õ���ͷ֮�����ݿ�
				if(headorhour==1)
				{
					System.out.println((Double.parseDouble(h7)/8));
					e.setHours(String.valueOf((Double.parseDouble(h7)/8)));
				}
				else
					e.setHours(h7);
				uplist.add(e);
				list.get(i).setHour7(h7);
			}
		}
		/*
		 * test area{
		 * 
		 * for(int i=0;i<uplist.size();i++) {
		 * System.out.println("**************************************");
		 * System.out.println("projectID="+uplist.get(i).getProjectId());
		 * System.out.println("createTime="+uplist.get(i).getComments());
		 * System.out.println("hours="+uplist.get(i).getHours());
		 * System.out.println("**************************************"); }
		 * //test area}
		 */
		boolean result = false;
		Connection conn = null;
		try {
			conn = DataTools.getConnection(request);
			Statement stmt = conn.createStatement();
			result = ExpenseDataDao.getInstance().saveAllExpenseData(conn,
					uplist);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			result = false;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		List<ExpenseData> sessionlist = (ArrayList<ExpenseData>) request
				.getSession().getAttribute("list");
		if (result) {
			request.setAttribute("result", "ok");
			// �����ݿ��޸ĳɹ�����ô��session������Ҳ�޸ģ�ʹ֮Ҳ��ݿ��������Ǻ�
			double summaryDay1 = 0;
			double summaryDay2 = 0;
			double summaryDay3 = 0;
			double summaryDay4 = 0;
			double summaryDay5 = 0;
			double summaryDay6 = 0;
			double summaryDay7 = 0;
			
			int y = 0; //list���±�
			for (int i = 0; i < sessionlist.size(); i++)
			{
				if(y<list.size() && sessionlist.get(i).getProjectId() == list.get(y).getProjectId())
				{
					sessionlist.set(i, list.get(y));
					y++;
				}
				else
				{
					sessionlist.get(i).getHour1();
				}
			}
			// ���¼���summary
			for (int i = 0; i < sessionlist.size(); i++) {
				summaryDay1 = summaryDay1
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour1().replaceAll(",", ""));
				summaryDay2 = summaryDay2
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour2().replaceAll(",", ""));
				summaryDay3 = summaryDay3
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour3().replaceAll(",", ""));
				summaryDay4 = summaryDay4
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour4().replaceAll(",", ""));
				summaryDay5 = summaryDay5
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour5().replaceAll(",", ""));
				summaryDay6 = summaryDay6
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour6().replaceAll(",", ""));
				summaryDay7 = summaryDay7
						+ Double.parseDouble("0"
								+ sessionlist.get(i).getHour7().replaceAll(",", ""));
			}
			java.text.NumberFormat formate = java.text.NumberFormat
			.getNumberInstance();
			formate.setMaximumFractionDigits(4);// �趨С�����Ϊ�� ����ô��ʾ���������������
			request.getSession().setAttribute("summaryDay1",
					formate.format(summaryDay1));
			request.getSession().setAttribute("summaryDay2",
					formate.format(summaryDay2));
			request.getSession().setAttribute("summaryDay3",
					formate.format(summaryDay3));
			request.getSession().setAttribute("summaryDay4",
					formate.format(summaryDay4));
			request.getSession().setAttribute("summaryDay5",
					formate.format(summaryDay5));
			request.getSession().setAttribute("summaryDay6",
					formate.format(summaryDay6));
			request.getSession().setAttribute("summaryDay7",
					formate.format(summaryDay7));
		} else {
			request.setAttribute("result", "no");
			// �����ݿ��޸�ʧ�ܣ���ô��displaylist�����ó�sessionlist
			request.getSession().setAttribute("displaylist", sessionlist);
		}
		request.getSession().setAttribute("scrollTop", request.getParameter("scrollTop"));//by collie 0505 �����ҳ���λ
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveExpenseData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Connection conn = null;
		Statement stmt = null;
		ExpenseData expenseData = new ExpenseData();
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			String selectedDate = (String) request.getParameter("selectedDate");
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			String hours = (String) request.getParameter("hours");
			// ȥ���ַ�ǰ���0����04���4���ۣ����䡱���õú�����ѽ��
			// modify0315:�����ж�.(С���)����Ȼ0.5����.5
			while (hours.indexOf("0") == 0 && hours.indexOf(".") != 1) {
				hours = hours.substring(1);
			}

			// System.out.println("projectId:"+projectId);
			// System.out.println("selectedDate:"+selectedDate);
			// System.out.println("hours:"+hours);

			Date today = new Date(Date.parse(mainForm.getToday()));
			int theRealDay = today.getDay();
			if (theRealDay == 0)
				theRealDay = 7;

			if (selectedDate.equalsIgnoreCase("str1")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 1 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str2")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 2 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str3")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 3 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str4")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 4 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str5")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 5 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str6")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 6 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			} else if (selectedDate.equalsIgnoreCase("str7")) {
				selectedDate = new Date(Date.parse(mainForm.getToday())
						- theRealDay * 60 * 1000 * 60 * 24 + 7 * 60 * 1000 * 60
						* 24).toLocaleString().replaceAll("0:00:00", "");
			}
			String[] temp = selectedDate.trim().split("-");
			String year = temp[0];
			String month = temp[1];
			String day = temp[2];
			month = Integer.parseInt(month) + 100 + "";
			month = month.substring(1);
			day = Integer.parseInt(day) + 100 + "";
			day = day.substring(1);
			selectedDate = year + "-" + month + "-" + day;
			expenseData.setCreateTime(selectedDate);
			expenseData.setProjectId(projectId);
			expenseData.setHours(hours);
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			expenseData.setUserId(user.getUserId());
			if (ExpenseDataDao.getInstance().loadDataComments(stmt, projectId,
					selectedDate) != null) {
			} else {
				expenseData.setExpenseDataId(-1);
			}
			ExpenseDataDao.getInstance().saveExpenseData(conn, expenseData,
					mainForm);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		if (mainForm.getErrors().isEmpty()
				&& mainForm.getStrErrors().length() == 0) {
			mainForm.setStrErrors("true");
		}

		String id = request.getParameter("id");
		String scrollTop = request.getParameter("scrollTop");

		if (null != id && !id.equals("")) {
			request.getSession().setAttribute("currentId", id);
			request.getSession().setAttribute("scrollTop", scrollTop);
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public ActionForward toinsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String gid = (String) request.getParameter("gid");
		String gname = (String) request.getParameter("gname");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Connection conn = null;
		Statement stmt = null;
		Project dproject=new Project();
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			mainForm.getProject().setCreateTime(new Date(System.currentTimeMillis()));
			
			mainForm.setSkillLevels_fw(AdministratorDao.getInstance().searchSkillLevels_fw(stmt));			
			mainForm.setLocations_fw(AdministratorDao.getInstance().searchLocations_fw(stmt));			
			mainForm.setOTTypes_fw(AdministratorDao.getInstance().searchOTTypes_fw(stmt));
			// author=longzhe �ҵ� componentNames, po �� productnames
			mainForm.setComponentNames_fw(AdministratorDao.getInstance().searchcomponentNames_fw(stmt));
//			mainForm.setPO_fw(AdministratorDao.getInstance().searchPO_fw(stmt));
			mainForm.setProductNames_fw(AdministratorDao.getInstance().searchProductNames_fw(stmt));
			mainForm.setWbsNames(AdministratorDao.getInstance().searchWBS(stmt));
			//hanxiaoyu01 2013-02-18 �鵽��ǰuser��project��Ĭ��ֵ
			dproject=ProjectDao.getInstance().getDefaultProject(conn,user.getUserId());
			
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("gid", gid);
		request.setAttribute("gname", gname);
		request.setAttribute("dproject", dproject);
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * ������Ŀ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNewProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		System.out.println("project.getUserId()="+ mainForm.getProject().getUserId());
		System.out.println("project.getComponentid()="+ mainForm.getProject().getComponentid());
		System.out.println("project.getProduct()="+ mainForm.getProject().getProduct());
		System.out.println("project.getGroupId="+ mainForm.getProject().getGroupId());
		System.out.println("project.getWBS()="+ mainForm.getProject().getWBS());
		// ��̨�жϱ�����project name�Ƿ�Ϊ��
		if (-1 == mainForm.getProject().getComponentid() ) {
			System.out.println("Component name is null in datacheckeraction");
			return mapping.findForward("new_project_edit");
		}
		Connection conn = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
		
	    int pid=0;
		try {
			conn = DataTools.getConnection(request);
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			mainForm.getProject().setUserId(user.getUserId());
			mainForm.getProject().setUsername(user.getUserName());
			System.out.println("project.getUserId()22="+ mainForm.getProject().getUserId());
			pid=ProjectDao.getInstance().saveNewProject(conn,mainForm.getProject(), mainForm);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		if (mainForm.getErrors().isEmpty()&& mainForm.getStrErrors().length() == 0) 
		{
			mainForm.setStrErrors("true");
			Thread thread=new SendThread(pid);
			thread.start();
		}
		return mapping.findForward(mainForm.getOperPage());
	}

	/**
	 * δ��д��expenseData���涼��0����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 */
	public ActionForward fillBlank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Connection conn = null;
		Statement stmt = null;

		String startDate = (String) request.getParameter("startDate");
		String endDate = (String) request.getParameter("endDate");
		// �ж�����0�������
		String fill = (String) request.getParameter("fill");

		ExpenseData expenseData = new ExpenseData();
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();

			ExpenseDataDao.getInstance().fillBlank(conn, mainForm,
					(SysUser) request.getSession().getAttribute("user"), fill);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.update"));
			this.saveErrors(request, errors);
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		String id = request.getParameter("id");
		String scrollTop = request.getParameter("scrollTop");

		if (null != id && !id.equals("")) {
			request.getSession().setAttribute("currentId", id);
			request.getSession().setAttribute("scrollTop", scrollTop);
		}

		if (mainForm.getErrors().isEmpty()
				&& mainForm.getStrErrors().length() == 0) {
			mainForm.setStrErrors("true");
		}

		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����leavedata���˷���������Daily Recordsҳ���leave����
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public ActionForward searchLeaveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		String selectedDate = (String) request.getParameter("selectedDate");
		
		//String updateDate = (String) request.getParameter("updateDate");
		
		String islock = (String) request.getParameter("islock");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		int userid = user.getUserId();
		String groupname = user.getGroupName();
		System.out.println("action groupname="+groupname);
		
		// ����˵����firstDay��ʾ��һ�������,���selectedDate����ȷ��ѡ���������
		String firstDay = (String) request.getSession()
				.getAttribute("firstDay");
		firstDay = firstDay.replace("-", "/");
		// ����˵�� temp��������ѡ����������һ�����죬����ȷ��ѡ�������
		int temp = 0;
		if ("str1".equals(selectedDate))
			temp = 0;
		else if ("str2".equals(selectedDate))
			temp = 1;
		else if ("str3".equals(selectedDate))
			temp = 2;
		else if ("str4".equals(selectedDate))
			temp = 3;
		else if ("str5".equals(selectedDate))
			temp = 4;
		else if ("str6".equals(selectedDate))
			temp = 5;
		else if ("str7".equals(selectedDate))
			temp = 6;
		// ����ѡ�������
		Date d = new Date(Date.parse(firstDay));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		selectedDate = ""
				+ df.format(new Date(d.getTime() + temp * 24 * 60 * 60 * 1000));
		//System.out.println("firstDay="+firstDay);
		//System.out.println("selectedDate="+selectedDate);
		
		//leave types
		List<Map> LeaveTypes = new ArrayList<Map>();
		//user leavedataList ��ǰ�û��ļ�����Ϣ
		List<LeaveData> leavedataList = new ArrayList<LeaveData>();
		//user leavedetails  ��ǰ�û�����ټ�¼
		List<LeaveDetails> leavedetailslist = new ArrayList<LeaveDetails>();
		//group leavedetails ��ǰgroup������groups����ټ�¼
		List<LeaveDetails> groupleavedetailslist = new ArrayList<LeaveDetails>();
		//group leavedata ��ǰgroup������groups�ļ��ڼ�¼ ����leader����˼�leave request
		List<LeaveData> groupleavedataList = new ArrayList<LeaveData>();
		
		
		Connection conn = null;
		Statement stmt = null;
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			LeaveTypes = LeaveDataDao.getInstance().loadLeaveTypes(stmt,user.getUserId());
			leavedataList = LeaveDataDao.getInstance().searchLeaveDataByUserID(stmt, String.valueOf(userid));
			leavedetailslist = LeaveDataDao.getInstance().loadLeaveDataDetailsByUserID(stmt, String.valueOf(userid));
			groupleavedetailslist = LeaveDataDao.getInstance().searchLeaveDataDetailsByGroupName(stmt, groupname, selectedDate);
			
			//@Dancy 2011-10-20 mainForm.getLeaveDetails()
			String updateDate = LeaveDataDao.getInstance().getupdateGridDate(conn);
			request.setAttribute("updateGridDate", updateDate);
			
			//���ǰ�û���reviewer ���� checker ����Ҫload groupleavedataList ����Ϊ��Ȩ�޸���˼���leave request
		try{
			if(3 == user.getLevelID() || 4 == user.getLevelID())
			{
				groupleavedataList = LeaveDataDao.getInstance().searchGroupLeavedataListByGroupName(stmt, groupname);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
			if(null != stmt)
				stmt.close();
		}
		//System.out.println("LeaveTypes.size()="+LeaveTypes.size());
		//System.out.println("leavedataList.size()="+leavedataList.size());
		//System.out.println("leavedetailslist.size()="+leavedetailslist.size());
		//System.out.println("groupleavedetailslist.size()="+groupleavedetailslist.size());
		
		//�ӹ�leavedataList�� ��Ҫ�ǵó�ʣ����õ�leaveʱ��
		//���õ�ʱ��=��ʱ��-Ĭ��ʧЧ��ʱ��-��ټ�¼��ʱ��
		
		//����Ӧ��leavedata details��ӵ�leavedata
		for(int i=0;i<leavedataList.size();i++)
		{
			List<LeaveDetails> tempdetails = new ArrayList<LeaveDetails>();
			float temphours = 0f;
			for(int j=0;j<leavedetailslist.size();j++)
			{
				if(leavedetailslist.get(j).getLeaveDataId()==leavedataList.get(i).getId())
				{
					tempdetails.add(leavedetailslist.get(j));
					temphours += leavedetailslist.get(j).getHours();
				}
			}
			leavedataList.get(i).setDetails(tempdetails);
			leavedataList.get(i).setUsedHours(temphours);
			if (leavedataList.get(i).getLeaveTypeName().equals("PTO")){
				java.util.Date dateNow=new java.util.Date();
				int PTOleave = 0;
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date  date;
				date = sdf.parse("2011-01-01");
				
				date = leavedataList.get(i).getEmployDate();   
				
				if (null==date){
					date= new java.util.Date();
				}
				
				//PTOleave = (int)((((dateNow.getTime()-date.getTime())/86400000.0) / 365.0) *15*8);
				//System.out.println("dateNow.compareTo(date):"+(dateNow.getTime()-date.getTime())/86400000);
				//System.out.println("PTOleave:"+PTOleave);
				leavedataList.get(i).setTotalHours(leavedataList.get(i).getDefaultHours()+leavedataList.get(i).getIncrementHours());
			}
			
			float temp1 = leavedataList.get(i).getTotalHours()-leavedataList.get(i).getUsedHours()-leavedataList.get(i).getDefaultUsed();
			leavedataList.get(i).setAvailableHours(temp1);
			

						
			
		}
		/*test area
		for(int i=0;i<leavedataList.size();i++)
		{
			System.out.println("�ܹ�ʱ�䣺"+leavedataList.get(i).getDefaultHours());
			System.out.println("Ĭ��ʧЧ��"+leavedataList.get(i).getDefaultUsed());
			System.out.println("���ʱ�䣺"+leavedataList.get(i).getUsedHours());
			System.out.println("***************************************");
			System.out.println("��ټ�¼��");
			for(int j=0;j<leavedataList.get(i).getDetails().size();j++)
			{
				System.out.println(leavedataList.get(i).getDetails().get(j).getHours());
			}
		}
		//test area
		*/
		request.setAttribute("islock", islock);
		request.setAttribute("selectedDate", selectedDate);
		if(null != user.getEmploydate())
		{
			request.setAttribute("employDate", user.getEmploydate().toString());
		}else{
			request.setAttribute("employDate", " ");
		}
		System.out.println("employDate"+user.getEmploydate().toString());
		request.setAttribute("LeaveTypes", LeaveTypes);
		request.setAttribute("leavedataList", leavedataList);
		request.setAttribute("leavedetailslist", leavedetailslist);
		request.setAttribute("groupleavedetailslist", groupleavedetailslist);
		//���ǰ�û���reviewer ���� checker ����Ҫload groupleavedataList ����Ϊ��Ȩ�޸���˼���leave request
		if(3 == user.getLevelID() || 4 == user.getLevelID())
		{
			request.setAttribute("groupleavedatalist", groupleavedataList);
		}
		System.out.println(groupleavedataList.size());
		
		//System.out.println("Start!!!!!!");
		//System.out.println(""+request.getSession().getAttributeNames());

		//System.out.println("End!!!!!!");
		
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����leavedatadetails���˷��������ڴ򿪵�leave�������
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public ActionForward saveNewLeaveRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		String leavetype = (String) request.getParameter("newLeave_Type");
		String uvs = (String) request.getParameter("newLeave_UVS");
		String hours = (String) request.getParameter("newLeave_Hour");
		String islock = (String) request.getParameter("islock");
		String date = (String) request.getParameter("selectedDate");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
				
		LeaveDetails leavedetails = new LeaveDetails();
		leavedetails.setLeavetypeid(Integer.parseInt(leavetype));
		leavedetails.setPTOsubtype(uvs);
		leavedetails.setHours(Float.parseFloat(hours));
		leavedetails.setDate(date);
		leavedetails.setOwner(user.getUserId());
		
		Connection conn = null;
		boolean result = false;
		
		try{
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().saveLeaveDataDetails(conn, leavedetails, "Myself");
			System.out.println("save leace data details "+result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����leavedatadetails���˷��������ڴ򿪵�leave����Ϊ�������
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public ActionForward saveNewLeaveRecordforOther(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		String leaveid = (String) request.getParameter("groupleavedata");
		String uvs = (String) request.getParameter("addOthersLeave_UVS");
		String hours = (String) request.getParameter("addOthersLeave_Hour");
		String date = (String) request.getParameter("selectedDate");
		
		LeaveDetails leavedetails = new LeaveDetails();
		leavedetails.setLeaveDataId(Integer.parseInt(leaveid));
		leavedetails.setPTOsubtype(uvs);
		leavedetails.setHours(Float.parseFloat(hours));
		leavedetails.setDate(date);
		
		Connection conn = null;
		boolean result = false;
		
		try{
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().saveLeaveDataDetails(conn, leavedetails, "other");
			System.out.println("save leace data details "+result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * �޸�leavedatadetails���˷��������ڴ򿪵�leave�����޸������Ϣ
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public ActionForward modifyLeaveRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		String leavedetailsid = (String) request.getParameter("leaevedetailsID");
		String leavetype = (String) request.getParameter("leavetype"+leavedetailsid);
		String uvs = (String) request.getParameter("UVS"+leavedetailsid);
		String hours = (String) request.getParameter("hours"+leavedetailsid);
		String tempH = (String) request.getParameter("hoursBeforeModify"+leavedetailsid);
		System.out.println("tempH="+tempH);
		String ownerid = (String) request.getParameter("owner");
		
		System.out.println(leavedetailsid);
		System.out.println("owner="+ownerid);
		System.out.println(leavetype);
		System.out.println(uvs);
		System.out.println(hours);
		
		LeaveDetails leavedetails = new LeaveDetails();
		leavedetails.setId(Integer.parseInt(leavedetailsid));
		leavedetails.setLeavetypeid(Integer.parseInt(leavetype));
		leavedetails.setPTOsubtype(uvs);
		leavedetails.setHours(Float.parseFloat(hours));
		leavedetails.setOwner(Integer.parseInt(ownerid));
		
		Connection conn = null;
		boolean result = false;
		
		try{
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().modifyLeaveRecord(conn, leavedetails);
			System.out.println("modify leave records "+result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ��leavedatadetails���˷��������ڴ򿪵�leave����ɾ�������Ϣ
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public ActionForward deleteLeaveRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		String leavedetailsid = (String) request.getParameter("leaevedetailsID");
		
		LeaveDetails leavedetails = new LeaveDetails();
		leavedetails.setId(Integer.parseInt(leavedetailsid));
		
		Connection conn = null;
		boolean result = false;
		
		try{
			conn = DataTools.getConnection(request);
			result = LeaveDataDao.getInstance().deleteLeaveRecord(conn, leavedetails);
			System.out.println("delete leave records "+result);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
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
		
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String lockDate = request.getParameter("day").replace("/", "-");
		System.out.println("enter");
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ExpenseDataDao.getInstance().lock(conn, sysUser.getGroupID(), lockDate);
			System.out.println("lock group "+sysUser.getGroupID() +" untill "+lockDate+" "+result);
			//���lock�ɹ����͸���session���lockDate��Ϣ
			if(result)
			{
				sysUser.setLockday(lockDate);
				request.getSession().setAttribute("user",sysUser);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		String datestr = (String) mainForm.getToday();
		
		System.out.println("datestr="+datestr+" //lock result is:"+result);
		
		request.setAttribute("result", String.valueOf(result));
		return search(mapping, form, request, response);
		
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
	 * @flag
	 */
	public ActionForward approveExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		
		String approveDate = request.getParameter("day").replace("/", "-");
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		int approve = user.getApproveLevel();
		
		//׼������2011/03/21ת��2011-03-21
		System.out.println("approveDate="+approveDate);
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = ExpenseDataDao.getInstance().approve(conn, user.getGroupID(), approveDate,user.getApproveLevel());
			System.out.println("set approve="+approve+" on groupid="+user.getGroupID() +" untill "+approveDate+"// "+result);
			//���lock�ɹ����͸���session���lockDate��Ϣ
			if(result)
			{
				user.setApproveday(approveDate);
				request.getSession().setAttribute("user",user);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		String datestr = (String) mainForm.getToday();
		
		System.out.println("datestr="+datestr);
		
		request.setAttribute("result", String.valueOf(result));
		return search(mapping, form, request, response);
	}
	/**
	 * searchLeavePerWeek
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
	public ActionForward searchLeavePerWeek(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		int groupId = user.getGroupID();
		List<Groups> grouplist = new ArrayList<Groups>();
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			grouplist = GroupsDao.getInstance().searchLockDayByGroupName(stmt, groupId);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
			if(null != stmt)
				stmt.close();
		}
		request.getSession().setAttribute("glist", grouplist);
		return mapping.findForward(mainForm.getOperPage());
		
	}
	
	/**
	 *@author hanxiaoyu01 2012-12-13
	 *�޸�WorkLoad���Details֮��ҲҪ�޸�TotalSummary
	 *
	 */
	public ActionForward saveSummaryDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		DataCheckerForm mainForm = (DataCheckerForm) form;
		request.getSession().setAttribute("summaryDay1", mainForm.getT1());
		request.getSession().setAttribute("summaryDay2", mainForm.getT2());
		request.getSession().setAttribute("summaryDay3", mainForm.getT3());
		request.getSession().setAttribute("summaryDay4", mainForm.getT4());
		request.getSession().setAttribute("summaryDay5", mainForm.getT5());
		request.getSession().setAttribute("summaryDay6", mainForm.getT6());
		request.getSession().setAttribute("summaryDay7", mainForm.getT7());
		return null;
	}
	
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * ��֤Ҫɾ���group�����projectlist��userlist �Ƿ�Ϊ��,���Ϊ�ղ���ɾ��
	 */
	public ActionForward checkGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
//		String gid = (String) request.getSession().getAttribute("groupID");
		//FWJ 2013-12-13
		String gid = (String) request.getParameter("gid");
		List<ExpenseData> Projectlist = new ArrayList<ExpenseData>(); // Project List
		List<SysUser> Userlist = new ArrayList<SysUser>(); // User List
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Projectlist = ExpenseDataDao.getInstance().searchProjectByGid(stmt,Integer.parseInt(gid), 1);
			Userlist = GroupsDao.getInstance().getUserbyGid(stmt, Integer.parseInt(gid));
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) 
			{
				conn.close();
			}			
		}
		PrintWriter out=response.getWriter();
		if(Projectlist.size()>0||Userlist.size()>0){
			out.print("false");
		}else{
			out.print("true");
		}
		out.close();
		return null;
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * ִ��ɾ��Group
	 */
	public ActionForward deleteGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
//		int gid = Integer.valueOf(((String) request.getSession().getAttribute("groupID")));
		//FWJ 2013-12-13
		int gid = Integer.valueOf((String) request.getParameter("gid"));
		Connection  conn=DataTools.getConnection(request);
		try{
			conn=DataTools.getConnection(request);
			GroupsDao.getInstance().deleteGroup(conn,gid);
		}catch(Exception e){
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		//�Ƴ�Ĭ��ֵ
		request.getSession().removeAttribute("groupID");
		return this.searchGroups(mapping, form, request, response);
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * �޸�һ��Group
	 */
	public ActionForward updateGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		int gid = Integer.valueOf(((String) request.getSession().getAttribute("groupID")));
		Connection conn = null;
		Groups group=null;
		try {
			conn = DataTools.getConnection(request);
			group = GroupsDao.getInstance().findGroupByGroupId2(conn,gid);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("group", group);
		request.getSession().setAttribute("groupID",String.valueOf(gid) );
		return mapping.findForward(mainForm.getOperPage());
		
	}
	
	
	
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-25
	 * �ж��޸ĵ�group name�Ƿ��������
	 */
	public ActionForward checkGroupName (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Connection conn=null;
		int result=0;
		try {
			conn = DataTools.getConnection(request);
			int gid=Integer.valueOf(request.getParameter("gid"));
			String gname=request.getParameter("gname");
			result=GroupsDao.getInstance().checkGroupName(conn,gname,gid);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		PrintWriter out=response.getWriter();
		if(result>0){
			out.print("false");
		}else{
			out.print("true");
		}
		out.close();
		return null;
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-25
	 * ִ���޸�
	 */
	public ActionForward doUpdateGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Connection conn=null;
		DataCheckerForm mainForm = (DataCheckerForm) form;
		Groups group=new Groups();
		group.setGid(mainForm.getGroupId());
		String gname=request.getParameter("gname").trim();
		String cms=request.getParameter("cms");
		group.setGname(gname);
		group.setComments(cms);
		try {
			conn = DataTools.getConnection(request);
			GroupsDao.getInstance().updateGroup(conn, group);
		} catch (Exception e) {
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.getSession().setAttribute("groupID",String.valueOf(mainForm.getGroupId()) );
		return this.searchGroups(mapping, form, request, response);
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2013-02-18
	 * ����Project��Ĭ��ֵ
	 */
	public ActionForward setDefaultProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		DataCheckerForm mainForm = (DataCheckerForm) form;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Connection conn=null;
		int result=0;
		try{
			conn=DataTools.getConnection(request);
			//�Ȱ����е�Ĭ��ֵɾ��
			ProjectDao.getInstance().deleteDefaultProject(conn,user.getUserId());
			result=ProjectDao.getInstance().setDefaultProject(conn,user.getUserId(),mainForm.getProject());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		if(result>0){
			PrintWriter out=response.getWriter();
			out.print("success!");
			out.close();
		}
		return null;
	}
	
	public ActionForward checkNewAddGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		String gname= request.getParameter("gname");
		Connection conn=null;
		boolean result = false;
	    try{
		   conn = DataTools.getConnection(request);
		   result=GroupsDao.getInstance().checkNewAddGroup(conn,gname);
	    }catch(Exception e){
		   e.printStackTrace();
	    }finally{
		   if(conn!=null){
			   conn.close();
		   }
	   }
		PrintWriter out=response.getWriter();
		if(result){
			out.print("1");
		}else {
			//没有被使用
			out.print("0");
		}
		return null;
	}
}
