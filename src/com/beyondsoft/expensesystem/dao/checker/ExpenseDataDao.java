package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyondsoft.expensesystem.domain.checker.CheckDetails;
import com.beyondsoft.expensesystem.domain.checker.ExpenseData;
import com.beyondsoft.expensesystem.domain.checker.ExpenseDetail;
import com.beyondsoft.expensesystem.domain.checker.Invoice;
import com.beyondsoft.expensesystem.domain.checker.Monthlyproject;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.ReportData_Basic;
import com.beyondsoft.expensesystem.domain.checker.ReportData_PayChex;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.checker.DataCheckerForm;
import com.beyondsoft.expensesystem.util.BaseActionForm;



public class ExpenseDataDao {
	private static ExpenseDataDao dao = null;

	public static ExpenseDataDao getInstance() {
		if (dao == null) {
			dao = new ExpenseDataDao();
		}

		return dao;
	}

	/**
	 * ���details��ݺ�daily����Ƿ�һ�²����ؼ����
	 * 
	 * @param conn
	 * @param groupname
	 * @throws Exception
	 * @author xiaofei by collie 0505
	 */
	public List<CheckDetails> checkDetails(Connection conn, String groupname, String str1, String str7)
			throws Exception {
		List<CheckDetails> result = new ArrayList<CheckDetails>();
		
		String sql = "select" +
				" expensedata.expensedataid" +
				",groups.groupname" +
				",projects.projectid" +
				",projects.projectname" +
				",expensedata.createtime" +
				",expensedata.hours 'dailyworkload'" +
				",sum(expensedata_details.hours) 'sumofdetails'" +
				" from" +
				" expensedata" +
				" left join expensedata_details on expensedata.expensedataid=expensedata_details.expenseid" +
				" join projects on projects.projectid=expensedata.projectid" +
				" join groups on groups.groupid=projects.groupid" +
				" where locate(?,groups.groupname)=1" +
				" and projects.hidden=0 and projects.remove=0 " +
				" and expensedata.createtime>='" +
				str1 +
				"' and expensedata.createtime<='" +
				str7 +
				"'" +
				" group by projects.projectid,expensedata.expensedataid,expensedata.createtime" + // by collie 0506 ��Dailyû����Details��Ҳ��ʾ����
				" having abs(expensedata.hours-sum(expensedata_details.hours))>0.0001" + // by collie 0518 check��ʱ�������һ���̶ȲŻᱻ���������
				" or (sum(expensedata_details.hours) is null and expensedata.hours<>0)" + // by collie 0506 ��Dailyû����Details��Ҳ��ʾ����  
				" order by groups.groupname,projects.projectname,expensedata.createtime";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// �õ�statement
			pstmt = conn
					.prepareStatement(sql);
			pstmt.setString(1, groupname);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				CheckDetails checkDetails = new CheckDetails();
				checkDetails.setExpensedataid(rs.getInt("expensedataid"));
				checkDetails.setGroupname(rs.getString("groupname"));
				checkDetails.setProjectname(rs.getString("projectname"));
				checkDetails.setCreatetime(rs.getString("createtime"));
				checkDetails.setDailyworkload(rs.getDouble("dailyworkload"));
				checkDetails.setSumofdetails(rs.getDouble("sumofdetails"));
				checkDetails.setProjectid(rs.getInt("projectid"));
				result.add(checkDetails);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ��ѯExpense Data��Ϣ
	 * @param stmt
	 * @param today ����
	 * @return
	 * @throws Exception
	 * @update by dancy 2012-11-19
	 */
	public List<ExpenseData> load(Statement stmt, String today, SysUser u)
			throws Exception {
		ExpenseData expenseData = null;
		List<ExpenseData> list = new ArrayList<ExpenseData>();
		String filter = "";
		if(u.getGroupID()!=-1)
		{
			filter = "and p.groupId = "+u.getGroupID();
		}
		if(u.getLevelID()>4)
		{
			filter = filter + " and p.userId = "+u.getUserId();
		}
//		System.out.println("filter="+filter);
		String sql = "select u.username"
			+ ",p.*"
			+ ",g.groupName, "
			+ " e1.hours as hour1,"
//			+ " (select count(*) from expensedata_details ed1 where e1.expenseDataId=ed1.expenseid) as detailscount1,"
			+ " e2.hours as hour2,"
//			+ " (select count(*) from expensedata_details ed2 where e2.expenseDataId=ed2.expenseid) as detailscount2,"
			+ " e3.hours as hour3,"
//			+ " (select count(*) from expensedata_details ed3 where e3.expenseDataId=ed3.expenseid) as detailscount3,"
			+ " e4.hours as hour4,"
//			+ " (select count(*) from expensedata_details ed4 where e4.expenseDataId=ed4.expenseid) as detailscount4,"
			+ " e5.hours as hour5,"
//			+ " (select count(*) from expensedata_details ed5 where e5.expenseDataId=ed5.expenseid) as detailscount5,"
			+ " e6.hours as hour6,"
//			+ " (select count(*) from expensedata_details ed6 where e6.expenseDataId=ed6.expenseid) as detailscount6,"
			+ " e7.hours as hour7"
//			+ " (select count(*) from expensedata_details ed7 where e7.expenseDataId=ed7.expenseid) as detailscount7"
			+ " from projects p" 
			+" left join (select ed1.expenseDataId,ed1.hours,ed1.projectId,ed1.createTime from expensedata ed1 where ed1.createTime=date_add('" 
			+today +"', interval -weekday('" +today +"') day)) e1 on p.projectId=e1.projectId"
			+" left join (select ed2.expenseDataId,ed2.hours,ed2.projectId,ed2.createTime from expensedata ed2 where ed2.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-1) day)) e2 on p.projectId=e2.projectId" 
			+" left join (select ed3.expenseDataId,ed3.hours,ed3.projectId,ed3.createTime from expensedata ed3 where ed3.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-2) day)) e3 on p.projectId=e3.projectId" 
			+" left join (select ed4.expenseDataId,ed4.hours,ed4.projectId,ed4.createTime from expensedata ed4 where ed4.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-3) day)) e4 on p.projectId=e4.projectId" 
			+" left join (select ed5.expenseDataId,ed5.hours,ed5.projectId,ed5.createTime from expensedata ed5 where ed5.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-4) day)) e5 on p.projectId=e5.projectId" 
			+" left join (select ed6.expenseDataId,ed6.hours,ed6.projectId,ed6.createTime from expensedata ed6 where ed6.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-5) day)) e6 on p.projectId=e6.projectId" 
			+" left join (select ed7.expenseDataId,ed7.hours,ed7.projectId,ed7.createTime from expensedata ed7 where ed7.createTime=date_add('" 
			+today +"', interval -(weekday('" +today +"')-6) day)) e7 on p.projectId=e7.projectId"
			+" left join groups g on p.groupId=g.groupId" 
			+" left join user_table u on p.userId=u.user_id"
			+ " where p.hidden=0 "+filter
			+ " order by "
			+"u.username, "
			+ "g.groupName ASC, "
			+ "p.componentName,"
			+ "p.product,"
			+ "p.location,"
			+ "p.skillLevel,"
			+ "p.OTType,"
			+ "p.HPManager"; 
		ResultSet rs = null;
		System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				expenseData = new ExpenseData();
				expenseData.setProjectId(rs.getInt("projectId"));
				expenseData.setProjectName(rs.getString("componentName"));
				expenseData.setProduct(rs.getString("product"));
				expenseData.setConfirm(rs.getInt("confirm"));
				expenseData.setCreateTime(rs.getString("createTime"));
				String hour1 = rs.getString("hour1");
				if (hour1 == null)
					expenseData.setHour1("");
				else
					expenseData.setHour1(hour1);
				String hour2 = rs.getString("hour2");
				if (hour2 == null)
					expenseData.setHour2("");
				else
					expenseData.setHour2(hour2);
				String hour3 = rs.getString("hour3");
				if (hour3 == null)
					expenseData.setHour3("");
				else
					expenseData.setHour3(hour3);
				String hour4 = rs.getString("hour4");
				if (hour4 == null)
					expenseData.setHour4("");
				else
					expenseData.setHour4(hour4);
				String hour5 = rs.getString("hour5");
				if (hour5 == null)
					expenseData.setHour5("");
				else
					expenseData.setHour5(hour5);
				String hour6 = rs.getString("hour6");
				if (hour6 == null)
					expenseData.setHour6("");
				else
					expenseData.setHour6(hour6);
				String hour7 = rs.getString("hour7");
				if (hour7 == null)
					expenseData.setHour7("");
				else
					expenseData.setHour7(hour7);

				// by collie 0428: ��details��Ϣ����Daily Recordsҳ��
//				expenseData.setDetailscount1(rs.getInt("detailscount1"));
//				expenseData.setDetailscount2(rs.getInt("detailscount2"));
//				expenseData.setDetailscount3(rs.getInt("detailscount3"));
//				expenseData.setDetailscount4(rs.getInt("detailscount4"));
//				expenseData.setDetailscount5(rs.getInt("detailscount5"));
//				expenseData.setDetailscount6(rs.getInt("detailscount6"));
//				expenseData.setDetailscount7(rs.getInt("detailscount7"));
				
				expenseData.setLocation(rs.getString("location"));
				expenseData.setOTType(rs.getString("OTType"));
				expenseData.setSkillLevel(rs.getString("skillLevel"));
				expenseData.setUserId(rs.getInt("userId"));
				expenseData.setGroupId(rs.getInt("groupId"));
				expenseData.setIsSummary(rs.getInt("isSummary"));
				expenseData.setGroupName(rs.getString("groupName"));
				expenseData.setUserName(rs.getString("username"));
				list.add(expenseData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		//����û�Ĭ������ͷ��hour,��ת��
		if(0 == u.getHeadorHour())
		{
			double h1 = 0;double h2 = 0;double h3 = 0;double h4 = 0;
			double h5 = 0;double h6 = 0;double h7 = 0;
			for(int i=0;i<list.size();i++)
			{
				ExpenseData ed = list.get(i);
				h1 = Double.parseDouble("0"+ed.getHour1())/8;
				h2 = Double.parseDouble("0"+ed.getHour2())/8;
				h3 = Double.parseDouble("0"+ed.getHour3())/8;
				h4 = Double.parseDouble("0"+ed.getHour4())/8;
				h5 = Double.parseDouble("0"+ed.getHour5())/8;
				h6 = Double.parseDouble("0"+ed.getHour6())/8;
				h7 = Double.parseDouble("0"+ed.getHour7())/8;
				
				list.get(i).setHour1(String.valueOf(h1));
				list.get(i).setHour2(String.valueOf(h2));
				list.get(i).setHour3(String.valueOf(h3));
				list.get(i).setHour4(String.valueOf(h4));
				list.get(i).setHour5(String.valueOf(h5));
				list.get(i).setHour6(String.valueOf(h6));
				list.get(i).setHour7(String.valueOf(h7));
				
			}
		}
		
		return list;
	}

	/**
	 * ��ѯ��Ŀ��Ϣ
	 * 
	 * @param stmt
	 * @param projectId
	 *            �û����
	 * @return
	 * @throws Exception
	 */
	public Project loadProject(Statement stmt, int projectId) throws Exception {
		Project project = null;
		String sql = "select p.projectId,p.userId,p.createTime,p.componentName,p.skillLevel,p.location,p.OTType,p.confirm,p.remove, p.comments from projects p"
				+ " where p.projectId = " + projectId;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt("projectId"));
				project.setComments(rs.getString("comments"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return project;
	}
/**
 * 
 * @param stmt
 * @param projectId
 * @param createTime
 * @return
 * @throws Exception
 * @flag
 */
	public String loadDataComments(Statement stmt, int projectId,
			String createTime) throws Exception {
		String comments = "";
		String sql = "select e.comments from expenseData e"
				+ " where e.projectId = " + projectId + " and e.createTime = '"
				+ createTime.trim() + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				comments = rs.getString("comments");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return comments;
	}

	/**
	 * ����ExpenseDetails��Ϣ
	 * 
	 * @param conn
	 * @param expenseData
	 * @param errors
	 * @throws Exception
	 * @flag
	 */
	
	@SuppressWarnings("unchecked")
	public List loadExpenseDetails(Statement stmt, int projectId,
			String createTime, int headorhour) throws Exception {
		/*String sql = "select e.comments, ed.*,(select worktype from worktype w where w.id=ed.worktypeid) as 'worktype',"
			+ "(select testtype from testtypes t where t.testtypeid=ed.testtypeid) as 'testtype',"
			+"(select description from descriptions d where d.descriptionid=ed.descriptionid) as 'description',"
			+"(select milestone from milestones m where m.milestoneid=ed.milestoneid) as 'milestone'"
				+ " from expensedata e, expensedata_details ed "
				+ "where e.projectId=" + projectId + " " + "and e.createTime='"
				+ createTime + "' " + "and e.expenseDataId=ed.expenseid";*/
		
		//hanxiaoyu01 2012-12-19 ��from testtypes �ĳ�from testtype
		String sql = "select e.comments, ed.*,(select worktype from worktype w where w.id=ed.worktypeid) as 'worktype',"
			+ "(select testtype from testtype t where t.testtypeid=ed.testtypeid) as 'testtype',"
			// Added the TargetLaunch by FWJ on 2013-03-07
			+ "(select targetlaunch from targetlaunch tl where tl.targetLaunchid=ed.targetLaunchid) as 'targetlaunch',"
			+ "(select budget from budgettracking bu where bu.budgetid=ed.budgetid) as 'budget',"
			+"(select milestone from milestones m where m.milestoneid=ed.milestoneid) as 'milestone'"
				+ " from expensedata e, expensedata_details ed "
				+ "where e.projectId=" + projectId + " " + "and e.createTime='"
				+ createTime + "' " + "and e.expenseDataId=ed.expenseid";
		System.out.println("sql for wlist="+sql);
		ResultSet rs = null;
		List<ExpenseDetail> list = new ArrayList<ExpenseDetail>();
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ExpenseDetail ed = new ExpenseDetail();
				ed.setComments(rs.getString("comments"));
				ed.setFirmware(rs.getString("firmware"));
				ed.setId(rs.getInt("detailid"));
				ed.setExpenseid(rs.getInt("expenseid"));
				ed.setWorktypeid(rs.getInt("worktypeid"));
				ed.setWorktype(rs.getString("worktype"));//new
				ed.setLayerphareid(rs.getInt("layerphaseid"));
				ed.setTesttype(rs.getInt("testtypeid"));
				ed.setTesttypeName(rs.getString("testtype"));//new
				String h = rs.getString("hours");
				ed.setComm(rs.getString("memo"));
				
				if(0==headorhour)//����û�����ͷ��hours
				{
					h = String.valueOf(Double.parseDouble(h)/8);
				}
				ed.setHour(h);
				ed.setMilestone(rs.getInt("milestoneid"));
				ed.setMilestoneName(rs.getString("milestone"));
				// Added the Targetlaunchid by FWJ on 2013-03-07
				ed.setTargetlaunchid(rs.getInt("targetlaunchid"));
				ed.setTargetlaunch(rs.getString("targetlaunch"));
				ed.setBudgetid(rs.getInt("budgetid"));//FWJ 2013-05-20
				ed.setBudget(rs.getString("budget"));//FWJ 2013-05-20
				ed.setDescriptionofskill(rs.getString("descriptionofskill"));//FWJ 2013-05-20
				ed.setSkillcategory(rs.getInt("skillcategoryid"));
				list.add(ed);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) 
			{
				rs.close();
			}
		}
		return list;
	}

	/**
	 * ����Comments��Ϣ
	 * 
	 * @param conn
	 * @param expenseData
	 * @param errors
	 * @throws Exception
	 */
	public boolean saveDataComments(Connection conn, int projectid,
			String date) throws Exception {
		/*
		 * �洢����������£� CREATE DEFINER=`root`@`localhost` PROCEDURE
		 * `saveExpenseComments`(com varchar(100), pid int, createt varchar(20))
		 * begin update expensedata set comments=com where projectId=pid and
		 * createTime=createt; delete from expensedata_details where expenseid=(
		 * select expenseDataId from expensedata where projectId=pid and
		 * createTime=createt ); end;
		 */
		String sql = "call saveExpenseComments(?,?)";
		boolean result = false;
		PreparedStatement pstmt = null;
		System.out.println("save data comments");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, projectid);
			pstmt.setString(2, date);
			pstmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}

	/**
	 * ����ExpenseData��Ϣ
	 * 
	 * @param conn
	 * @param expenseData
	 * @param errors
	 * @throws Exception
	 */
	public void saveExpenseData(Connection conn, ExpenseData expenseData,
			BaseActionForm form) throws Exception {
		String sql = "";
		PreparedStatement pstmt = null;
		int expenseDataId = expenseData.getExpenseDataId();

		System.out.println("expensedata in DAO:");
		System.out.println("expenseid="+expenseData.getExpenseDataId());
		System.out.println("userid="+expenseData.getUserId());
		System.out.println("hours="+expenseData.getHours());
		System.out.println("projectid="+expenseData.getProjectId());
		System.out.println("createtime="+expenseData.getComments());

		try {
			if (expenseDataId != -1) {
				sql = "update expensedata set hours = ?  where projectId = ? and createTime = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, expenseData.getHours());
				pstmt.setInt(2, expenseData.getProjectId());
				//by collie 0510  ����Daily Workload
				pstmt.setString(3, expenseData.getComments());
				pstmt.executeUpdate();
				form.setStrErrors("");
			} else {
				sql = "insert into expensedata(userId,projectId,createTime,hours,comments,approved,remove) "
						+ "values (?,?,?,?,null,0,0)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, expenseData.getUserId());
				pstmt.setInt(2, expenseData.getProjectId());
				pstmt.setString(3, expenseData.getCreateTime().toString());
				pstmt.setString(4, expenseData.getHours().trim());
				pstmt.executeUpdate();
				form.setStrErrors("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * save all ExpenseData��Ϣ
	 * 
	 * @param conn
	 * @param expenseData
	 * @param errors
	 * @throws Exception
	 * @author longzhe
	 */
	@SuppressWarnings("unchecked")
	public boolean saveAllExpenseData(Connection conn, List uplist)
			throws Exception {
		boolean re = false;
		String sql = "update expensedata set hours=? where projectId=? and createTime=?";
		PreparedStatement pstmt = null;
		try {
			// ����Ϊ�ֶ��ύ
			conn.setAutoCommit(false);
			// �õ�statement
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < uplist.size(); i++) {
				ExpenseData expense = (ExpenseData) uplist.get(i);
				pstmt.setString(1, expense.getHours());
				pstmt.setInt(2, expense.getProjectId());
				pstmt.setString(3, expense.getComments());
				pstmt.addBatch();
			}
			@SuppressWarnings("unused")
			int[] result = pstmt.executeBatch();
			// ��Ҫ�ֶ��ύ
			conn.commit();
			// ���û���
			conn.setAutoCommit(true);
			re = true;
		} catch (Exception e) {
			e.printStackTrace();
			re = false;
			try {
				// ������κ�SQL�쳣����Ҫ���лع�,������ΪϵͳĬ�ϵ��ύ��ʽ,��ΪTRUE
				if (conn != null) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (Exception se1) {
				se1.printStackTrace();
			}

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return re;
	}

	/**
	 * ����ExpenseData��Ϣ
	 * 
	 * @param conn
	 * @param form
	 * @param sysUser
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void fillBlank(Connection conn, DataCheckerForm form,
			SysUser sysUser, String fill) throws Exception {
		String sql = "";
		PreparedStatement pstmt = null;
		DataCheckerForm mainForm = (DataCheckerForm) form;

		try {
			Date today = new Date(Date.parse(mainForm.getToday()));
			int theRealDay = today.getDay();
			if (theRealDay == 0)
				theRealDay = 7;

			String str1 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 1 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str2 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 2 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str3 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 3 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str4 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 4 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str5 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 5 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str6 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 6 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			String str7 = new Date(Date.parse(mainForm.getToday()) - theRealDay
					* 60 * 1000 * 60 * 24 + 7 * 60 * 1000 * 60 * 24)
					.toLocaleString().replaceAll("0:00:00", "");
			// System.out.println(str1);

			/*
			 * �ж�ȫ����0����ȫ����� ��0��Ϊ�˷���ͻ� �����Ϊ�˷������Ա��
			 * û�����֮ǰ��group�����е���group��ݶ�Ҫ��������ܵ���group��ݡ�
			 * ����������䣬group����Щ��group���û����䣬Ҳ���Ե�����
			 */
			// ambiguous
			String sql2 = "";
			if ("0".equals(fill)) {
				sql = "update expensedata,projects,groups set expensedata.hours='0' where  expensedata.hours='' and expensedata.projectId=projects.projectId and expensedata.createTime=? and locate('"
						+ sysUser.getGroupName()
						+ "',groups.groupName)=1 and projects.groupId=groups.groupId and (leaveType is null or leaveType='')";// projectId=projects.projectId
				// and
				// createTime=?)
				// and
				// locate('"+sysUser.getGroupName()+"',groups.groupName)=1
				// and
				// projects.groupId=groups.groupId
				// and
				// (leaveType
				// is
				// null
				// or
				// leaveType='')
				System.out.println("str1=" + str1);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str1);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str2);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str3);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str4);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str5);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str6);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str7);
				pstmt.executeUpdate();

				sql2 = "INSERT INTO expensedata (userId, projectId, createTime,hours,approved,remove) SELECT "
						+ sysUser.getUserId()
						+ ",projects.projectId,?,0,0,0 from groups,projects where not exists (select * from expensedata where projectId=projects.projectId and createTime=?) and locate('"
						+ sysUser.getGroupName()
						+ "',groups.groupName)=1 and projects.groupId=groups.groupId and (leaveType is null or leaveType='')";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str1);
				pstmt.setString(2, str1);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str2);
				pstmt.setString(2, str2);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str3);
				pstmt.setString(2, str3);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str4);
				pstmt.setString(2, str4);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str5);
				pstmt.setString(2, str5);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str6);
				pstmt.setString(2, str6);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, str7);
				pstmt.setString(2, str7);
				pstmt.executeUpdate();
			} else if ("null".equals(fill)) {
				sql = "INSERT INTO expensedata (userId, projectId, createTime,hours,approved,remove) SELECT "
						+ sysUser.getUserId()
						+ ",projects.projectId,?,'',0,0 from groups,projects where not exists (select * from expensedata where projectId=projects.projectId and createTime=?) and locate('"
						+ sysUser.getGroupName()
						+ "',groups.groupName)=1 and projects.groupId=groups.groupId and (leaveType is null or leaveType='')";
				System.out.println("str1=" + str1);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str1);
				pstmt.setString(2, str1);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str2);
				pstmt.setString(2, str2);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str3);
				pstmt.setString(2, str3);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str4);
				pstmt.setString(2, str4);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str5);
				pstmt.setString(2, str5);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str6);
				pstmt.setString(2, str6);
				pstmt.executeUpdate();

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, str7);
				pstmt.setString(2, str7);
				pstmt.executeUpdate();
			}

			form.setStrErrors("");

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

   /**
    * @author hanxiaoyu01
    * ͨ����id���� 2013-02-19
    * @param stmt
    * @param gid
    * @return
    * @throws Exception
    */
	@SuppressWarnings("unchecked")
	public List searchProjectByGid(Statement stmt, String gid) throws Exception {
		ExpenseData expense = null;
		String sql = "select projectId,componentName,product,skillLevel,location,"
			+"(select username from user_table where user_id=projects.userId) as 'uname',"
			+"(select PONumber from po where po.POID=projects.PONumberid) as 'pno',"
			+"OTType,wbs,confirm,comments,hidden from projects where groupId="+gid;
		List<ExpenseData> list = new ArrayList<ExpenseData>();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) 
			{
				expense = new ExpenseData();
				expense.setProjectId(rs.getInt("projectId"));
				expense.setConfirm(rs.getInt("confirm"));
				expense.setSkillLevel(rs.getString("skillLevel"));
				expense.setLocation(rs.getString("location"));
				expense.setOTType(rs.getString("OTType"));
				expense.setComments(rs.getString("comments"));
				expense.setProduct(rs.getString("product"));
				expense.setHidden(rs.getInt("hidden"));
				expense.setComponentname(rs.getString("componentName"));
				expense.setUserName(rs.getString("uname"));
				expense.setWbs(rs.getString("wbs"));
				expense.setPONumber(rs.getString("pno"));
				list.add(expense);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}finally
		{
			if(null!=rs)
			{
				rs.close();
			}
		}
		return list;
	}
	
	public String searchTotalProjectsByGroupId(Statement stmt, String gid) throws Exception
	{
		String total = "0";
		String sql = "select count(projectId) as 'total' from projects where groupId=" +gid;
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				total = rs.getString("total");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}finally
		{
			if(null!=rs)
			{
				rs.close();
			}
		}
		return total;
	}
	
	/**
	 * ͨ��id��ѯproject��ϸ��Ϣ
	 * 
	 * @param conn
	 * @param GroupId
	 * @return
	 * @throws Exception
	 * @author longzhe
	 * @update by dancy 2012-11-15
	 */
	public List<ExpenseData> searchProjectByGid(Statement stmt, int gid, int currentPage) throws Exception {
		ExpenseData expense = null;
		int startNum = (currentPage-1) * 15;
		
		String sql = "select projectId,componentName,product,skillLevel,location,"
			+"(select username from user_table where user_id=projects.userId) as 'uname',"
			+"(select PONumber from po where po.POID=projects.PONumberid) as 'pno',"
			+"OTType,wbs,confirm,comments,hidden from projects where groupId=" + gid
			+" order by projectId desc limit "+startNum+",15";
		System.out.println("******sql=" + sql);
		List<ExpenseData> list = new ArrayList<ExpenseData>();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) 
			{
				expense = new ExpenseData();
				expense.setProjectId(rs.getInt("projectId"));
				expense.setConfirm(rs.getInt("confirm"));
				expense.setSkillLevel(rs.getString("skillLevel"));
				expense.setLocation(rs.getString("location"));
				expense.setOTType(rs.getString("OTType"));
				expense.setComments(rs.getString("comments"));
				expense.setProduct(rs.getString("product"));
				expense.setHidden(rs.getInt("hidden"));
				expense.setComponentname(rs.getString("componentName"));
				expense.setUserName(rs.getString("uname"));
				expense.setWbs(rs.getString("wbs"));
				expense.setPONumber(rs.getString("pno"));
				list.add(expense);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}finally
		{
			if(null!=rs)
			{
				rs.close();
			}
		}
		return list;
	}

	/**
	 * ��ָ�����ڷ�Χ�ڵ�ָ��Group�µ���ݵ�������FW���¸�ʽ��2011.3.1��
	 * 
	 * @param conn
	 * @param startDate
	 * @param endDate
	 * @param groupName
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 */
	@SuppressWarnings("deprecation")
	public ReportData_Basic searchFWExpenseData(Connection conn,
			String startDate, String endDate, String groupName)
			throws Exception {
		ReportData_Basic reportData = new ReportData_Basic();

		List<Project> listProject = new ArrayList<Project>();
		List<Date> listDate = new ArrayList<Date>();
		List<Double> listHours = new ArrayList<Double>();
		List<String> listHoursComment = new ArrayList<String>();
		int countOfRecord = 0;

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		// Get projects
		sql = "SELECT "
				+ "projects.*, "
				+ "groups.groupName, "
				+ "groups.groupId "
				+ "from groups,projects "
				+ "where locate(?,groups.groupName)=1 and "
				+ "projects.groupId=groups.groupId and "
				+ "projects.confirm=1 and "
				+ "projects.remove=0 and "
				+ "(projects.leavetype is null or projects.leaveType='') "
				+ "order by length(groups.groupName),groups.groupName,projects.userId,projects.projectName";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Project project = new Project();
//				project.setComments(rs.getString("comments"));
				project.setConfirm(rs.getInt("confirm"));
				project.setCreateTime(rs.getDate("createTime"));
				project.setGroupId(rs.getInt("groupId"));
				project.setGroupName(rs.getString("groupName"));
				project.setLocation(rs.getString("location"));
				project.setOTType(rs.getString("OTType"));
				project.setProjectId(rs.getInt("projectId"));
				project.setComponent(rs.getString("componentName"));
				project.setRemove(rs.getInt("remove"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setUserId(rs.getInt("userId"));
				project.setHPManager(rs.getString("HPManager"));
				project.setIsSummary(rs.getInt("isSummary"));
				project.setLocationId(rs.getString("locationId"));
				project.setProduct(rs.getString("product"));
				project.setProgramme(rs.getString("programme"));
				project.setRate(rs.getDouble("rate"));
				project.setStructureLevel(rs.getInt("structureLevel"));
				project.setWBS(rs.getString("WBS"));
				project.setReportGroup(rs.getString("reportGroup"));
				project.setProgram(rs.getString("program"));
				project.setTestAsset(rs.getString("testAsset"));
				project.setActivityType(rs.getString("activityType"));
				project.setTargetMilestone(rs.getString("targetMilestone"));
//				project.setDescription(rs.getString("description"));

				listProject.add(project);
				// reportData.addSingleListProject(project);

				// System.out.println(i + ":
				// "+reportData.getSingleListProject(i++).getProjectId());
			}
			// System.out.println("check 1:
			// "+reportData.getSingleListProject(55).getProjectId());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// get Hours and Comment
		sql = "SELECT "
				+ "expensedata.projectId,	"
				+ "expensedata.createTime, "
				+ "expensedata.hours, "
				+ "expensedata.comments "
				+ "FROM "
				+ "expensedata,projects,groups "
				+ "WHERE "
				+ "expensedata.approved = 10 AND "
				+ "expensedata.remove = 0 AND "
				+ "expensedata.createTime >=  '"
				+ startDate
				+ "' AND "
				+ "expensedata.createTime <=  '"
				+ endDate
				+ "' AND "
				+ "projects.projectId=expensedata.projectId AND "
				+ "(projects.leavetype is null or projects.leaveType='') AND "
				+ "groups.groupId=projects.groupId AND "
				+ "locate(?,groups.groupName)=1 "
				// + "order by
				// length(groups.groupName),groups.groupName,projects.userId,projects.projectName,expensedata.createTime";
				+ "order by length(groups.groupName),groups.groupName,projects.projectName,expensedata.projectId,expensedata.createTime";
		System.out.println("dao sql=" + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Double hours = 0.0;
				String comment = null;
				hours = rs.getDouble("hours");
				if (rs.getString("comments") == ""
						|| null == rs.getString("comments")) {
					comment = null;
				} else {
					comment = rs.getString("comments");
				}

				listHours.add(hours);
				listHoursComment.add(comment);
				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// get Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		Date date = null;
		date = new Date(Date.parse(startDate));
		Date date1 = new Date(Date.parse(endDate));
		calendar.setTime(date);
		calendar1.setTime(date1);

		try {

			while (!calendar1.equals(calendar)) {
				listDate.add(date);
				// System.out.println(date.toString());
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
				date = calendar.getTime();
			}
			if (calendar1.equals(calendar))
				listDate.add(date);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		reportData.setListProject(listProject);
		reportData.setListDate(listDate);
		reportData.setListHours(listHours);
		reportData.setListHoursComment(listHoursComment);
		reportData.setCountOfRecord(countOfRecord);

		return reportData;
	}
	
	/**
	 * ͨ��ָ��ʱ��Σ�ָ��GroupName����leavedata
	 * 
	 * @param conn
	 * @param StartDate
	 * @param EndDate
	 * @param GroupName
	 * @throws Exception by collie 0720
	 */
	public ArrayList<ReportData_PayChex> searchExpenseDataForReport5(Connection conn,
			String startDate, String endDate, String groupName) throws Exception
	{
		ArrayList<ReportData_PayChex> listReportData = new ArrayList<ReportData_PayChex>();

		int countOfRecord = 0;

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;// by collie 0503

		// Get projects,hours,comments,details,dates // by collie 0503
		sql = "select user_table.user_id,user_table.username,expensedata.createTime,groups.groupId,groups.groupName,expensedata.createTime,sum(expensedata.hours) as hours," +
				"user_table.workloadRate" +
				", user_table.HPEmployeeNumber" +
				" from user_table" +
				" left join groups on user_table.workloadgroupId=groups.groupId" +
				" left join projects on groups.groupId=projects.groupId" +
				" left join expensedata on projects.projectId=expensedata.projectid" +
				" where expensedata.createTime>='" +
				startDate +
				"'" +
				" and expensedata.createTime<='" +
				endDate +
				"'" +
				" and locate(?,groups.groupName)=1" +
				//" and expensedata.hours<>0" +
				" and user_table.HPEmployeeNumber is not null<>0 " +
				" and user_table.HPEmployeeNumber <> '' " +
				" group by user_table.user_id,expensedata.createTime" +
				" order by user_table.username,expensedata.createTime";
		//System.out.println("searchExpenseDataForReport5 sql:"+sql);
		
		try {
			pstmt = conn.prepareStatement(sql);	// by collie 0503
			//pstmt = conn.createStatement();
			pstmt.setString(1, groupName);// by collie 0503
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReportData_PayChex ReportData = new ReportData_PayChex();
				ReportData.setIntUserID(rs.getInt("user_id"));
				ReportData.setDatDate(rs.getDate("createTime"));
				System.out.println("searchExpenseDataForReport5 createTime:"+rs.getDate("createTime"));
				
				ReportData.setDouRate(rs.getDouble("workloadRate"));
				ReportData.setStrHours8(Double.toString(Double.parseDouble(rs.getString("hours"))*8));//��headcount�����hours
				if (null!=rs.getString("HPEmployeeNumber")){
					ReportData.setStrEmployNumber6(rs.getString("HPEmployeeNumber"));
				}else{
					ReportData.setStrEmployNumber6("      ");
				}
				
								
				listReportData.add(ReportData);

				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return listReportData;
	}
	/**
	 * ��ָ�����ڷ�Χ�ڵ�ָ��Group�µ���ݵ���
	 * 
	 * @param conn
	 * @param startDate
	 * @param endDate
	 * @param groupName
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 */
	@SuppressWarnings("deprecation")
	public ReportData_Basic searchExpenseData(Connection conn,String startDate, String endDate, int gid,SysUser u,String re)
			throws Exception {
		ReportData_Basic reportData = new ReportData_Basic();

		List<Project> listProject = new ArrayList<Project>();

		List<Date> listDate = new ArrayList<Date>();

		List<Double> listHours = new ArrayList<Double>();

		List<String> listHoursComment = new ArrayList<String>();

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		int countOfRecord = 0;

		String flag = "";
		if(u.getLevelID()>3|| re!=null)
		{
			flag = " and projects.groupId="+gid;			
		}
		if(u.getLevelID()>4)
		{
			flag = flag + " and projects.user_id="+u.getUserId();
		}

		sql = "SELECT "
				+ "projects.*, "
				+ "groups.groupName, "
				+ "groups.groupId as groupsId "
				+ "from groups,projects "
				+ "where projects.groupId=groups.groupId"+flag
				+ " and projects.remove=0 "
				+ "order by projects.componentName,projects.product,groups.groupName,projects.skillLevel,projects.projectId";
		try 
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				Project project = new Project();
				project.setComments(rs.getString("comments"));
				project.setConfirm(rs.getInt("confirm"));
				project.setCreateTime(rs.getDate("createTime"));
				project.setGroupId(rs.getInt("groupsId"));
				project.setGroupName(rs.getString("groupName"));
				project.setLocation(rs.getString("location"));
				project.setOTType(rs.getString("OTType"));
				project.setProjectId(rs.getInt("projectId"));
				project.setComponent(rs.getString("componentName"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setUserId(rs.getInt("userId"));
				project.setHPManager(rs.getString("HPManager"));
				project.setIsSummary(rs.getInt("isSummary"));
				project.setLocationId(rs.getString("locationId"));
				project.setProduct(rs.getString("product"));
				project.setRate(rs.getDouble("rate"));
				project.setWBS(rs.getString("WBS"));
				listProject.add(project);

			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		System.out.println("sql1="+sql);
		// get Hours and Comment
		sql = "SELECT "
				+ "expensedata.projectId,	"
				+ "expensedata.createTime, "
				+ "expensedata.hours, "
				+ "expensedata.comments "
				+ "FROM "
				+ "expensedata,projects,groups "
				+ "WHERE "
				+ "expensedata.remove = 0 AND "
				+ "expensedata.createTime >=  '"
				+ startDate
				+ "' AND "
				+ "expensedata.createTime <=  '"
				+ endDate
				+ "' AND "
				+ "projects.projectId=expensedata.projectId AND "
				+ "groups.groupId=projects.groupId"+flag
				+ "order by projects.componentName,projects.product,groups.groupName,projects.skillLevel,projects.projectId,expensedata.createTime";
		try 
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Double hours = 0.0;
				String comment = null;
				hours = rs.getDouble("hours");
				if (rs.getString("comments") == ""|| null == rs.getString("comments")) 
				{
					comment = null;
				} 
				else 
				{
					comment = rs.getString("comments");
				}

				listHours.add(hours);
				listHoursComment.add(comment);
				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println("sql2="+sql);
		// get Date
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		Date date = null;
		date = new Date(Date.parse(startDate));
		Date date1 = new Date(Date.parse(endDate));
		calendar.setTime(date);
		calendar1.setTime(date1);

		try 
		{

			while (!calendar1.equals(calendar))
			{
				listDate.add(date);
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
				date = calendar.getTime();
			}
			if (calendar1.equals(calendar))
				listDate.add(date);

		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
			{
				pstmt.close();
			}
		}

		System.out.println("reportdata.size() in dao=" + listDate.size());

		reportData.setListProject(listProject);
		reportData.setListDate(listDate);
		reportData.setListHours(listHours);
		reportData.setListHoursComment(listHoursComment);
		reportData.setCountOfRecord(countOfRecord);

		return reportData;
	}

	/**
	 * �ж�ָ�����ڷ�Χ�ڵ�ָ��Group������Ƿ�����
	 * 
	 * @param conn
	 * @param startDate
	 * @param endDate
	 * @param groupName
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 */
	public List<String> isExpenseDataFullFilled(Connection conn,
			String startDate, String endDate, String groupNames)
			throws Exception {
		String result = "";
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "";
//		System.out.println("here groups is: "+ groupNames);
		String flag = "";
		if(groupNames.indexOf(",")>0)
		{
			groupNames = groupNames.substring(0,groupNames.length()-1);
			flag = " and groups.groupName in ("+groupNames+")";			
		}
		System.out.println("here groups 3 is: "+ groupNames);
		try 
		{
			int countOfProject = 0;
			sql = "select projects.projectId from groups,projects where projects.groupId=groups.groupId" + flag + " and projects.hidden=0";
			pstmt = conn.prepareStatement(sql);
//			System.out.println("sql1=" + sql);
			rs = pstmt.executeQuery();

			rs.last();
			countOfProject = rs.getRow();
			
			if (countOfProject == 0) 
			{
				result = "No project!";
				list.add(result);
			} 
			else 
			{
				sql = "select groups.groupName,projects.projectId,projects.groupId,projects.userId,user_table.username"
					+" from projects,groups,user_table" 
					+" where groups.groupId=projects.groupId"
					+" and user_table.user_id=projects.userId"
					+" and projects.confirm=0 and projects.hidden=0"+flag+" order by groups.groupName";
				pstmt = conn.prepareStatement(sql);
//				System.out.println("sql=" + sql);
				rs = pstmt.executeQuery();
				while (rs.next()) 
				{
					result = rs.getString("groupName") + "("+rs.getString("username")+"): the project "
							+ "(ID:"+rs.getInt("projectId")+")"+ " is not confirmed by approver.";
					list.add(result);
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return list;
	}

	/**
	 * Lock���ܣ���checker���expense��ʱ�򣬲�����filler�޸���ݣ����Ա�֤��ݵ�һ���ԣ�
	 * ����checker�߿�filler�߸���ɵ���ݲ�һ��
	 * 
	 * ��һ��������ǰ��������ݶ�Lock�������޸ġ� ����һ�鲣���������������ǰ����ݶ���������ֻ�ܿ�����������
	 * 
	 * @param gname
	 * @param date
	 * @throws Exception
	 * @author longzhe
	 * @flag
	 */
	public boolean lock(Connection conn, int gid, String date)
			throws Exception {
		boolean result = false;
		String flag = "";
		if(gid>0)
		{
			flag=" where groupId="+gid;
		}
		
		String sql = "update groups set lockday=? "+flag;
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}

	/**
	 * Approve���ܣ���ݿ������������ֶ�: approveday����approve�� approveday��һ�����ڡ�
	 * ��lock����һ���һ��������ǰ��������ݶ�approve�������޸ġ� ����һ�鲣���������������ǰ����ݶ���������ֻ�ܿ�����������
	 * 
	 * approve�Ǽ��𡣼���ߵ�approve֮�󣬼���͵ľͲ��ܸ��ˡ�
	 * 
	 * @param gname
	 * @param date
	 * @param approve
	 * @throws Exception
	 * @author longzhe
	 * @flag
	 */
	public boolean approve(Connection conn, int gid, String date,
			int approve) throws Exception {
		boolean result = false;
		String flag = "";
		if(gid>0)
		{
			flag = " where groupId="+gid;
		}
		String sql = "update groups set approveday=? , approve=? "+flag;
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setInt(2, approve);
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}

	/**
	 * save expensedata details ���̣���ɾ����Ӧ��details��Ȼ���ٲ����µ�details
	 * 
	 * @param gname
	 * @param date
	 * @param approve
	 * @throws Exception
	 * @author longzhe
	 */
	public boolean saveExpenseDetails(Connection conn,
			List<ExpenseDetail> list, int projectid, String date, int headorhour)
			throws Exception {
		boolean result = false;
		String sql = "insert expensedata_details(expenseid, worktypeid, layerphaseid, testtypeid,  hours, milestoneid, skillcategoryid, firmware, memo, targetlaunchid, budgetid,descriptionofskill)"
			+ "values ((select expenseDataId from expensedata where projectId="
			+ projectid + " and createTime='" + date + "'),?,?,?,?,?,?,?,?,?,?,?)";

	System.out.println("projectid in dao="+projectid+", date="+date);
	PreparedStatement deletepstmt = null;

	// �ȱ���comments �� ɾ����Ӧ������details
	try {
		result = this.saveDataComments(conn, projectid, date);
	} catch (Exception e) {
		e.printStackTrace();
		conn.close();
	} finally {
		if (deletepstmt != null) {
			deletepstmt.close();
		}
	}
	// Ȼ�󱣴��µ�details
	PreparedStatement pstmt = null;
	try {
		// ��connection��������Ϊ�ֶ��ύ
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(sql);
		// ��PreparedStatement��������
		for (int i = 0; i < list.size(); i++) 
		{
			pstmt.setInt(1, list.get(i).getWorktypeid());
			pstmt.setInt(2, list.get(i).getLayerphareid());
			pstmt.setInt(3, list.get(i).getTesttype());
			String h = list.get(i).getHour();
			if(0==headorhour)//����û���hour��ʽ��д��Ҫ�������ͷ
			{
				h = String.valueOf(Double.parseDouble(h)*8);
			}
			pstmt.setString(4, h);
			pstmt.setInt(5, list.get(i).getMilestone());
			pstmt.setInt(6, list.get(i).getSkillcategory());
			pstmt.setString(7, list.get(i).getFirmware());
			pstmt.setString(8, list.get(i).getComm());
			
			//Added by FWJ on 2013-03-07
			pstmt.setInt(9, list.get(i).getTargetlaunchid());
			
			//Added by FWJ on 2013-05-22
			pstmt.setInt(10, list.get(i).getBudgetid());
			pstmt.setString(11, list.get(i).getDescriptionofskill());
		//	System.out.println("firmware444:"+list.get(i).getFirmware());
		//	System.out.println("getMilestone()=:"+list.get(i).getMilestone());

			pstmt.addBatch();
		}
			int[] re = pstmt.executeBatch();
			// ��Ҫ�ֶ��ύ
			conn.commit();
			// ���û���
			conn.setAutoCommit(true);
			// �ж�sql�Ƿ�ȫ�����гɹ�
			result = true;
			for (int i = 0; i < re.length; i++) {
			//	System.out.println(re[i]);
				if (re[i] == 0)
					result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// ������κ�SQL�쳣����Ҫ���лع�,������ΪϵͳĬ�ϵ��ύ��ʽ,��ΪTRUE
				if (conn != null) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (Exception se1) {
				se1.printStackTrace();
			}

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}


	/**
	 * ����ݿ��õ�details��woketype�б�
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public List searchWorktypeList(Statement stmt) throws Exception {
		List<String> WorktypeList = new ArrayList<String>();
		String sql = "select id, worktype,groupnum from worktype order by groupnum, worktype";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				WorktypeList.add(rs.getString("id"));
				WorktypeList.add(rs.getString("worktype"));
				WorktypeList.add(rs.getString("groupnum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
//			if (rs != null)
//			{
//				rs.close();
//			}
		}
		return WorktypeList;
	}

	/**
	 * ����ݿ��õ�details��layerphase�б�
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public List searchLayerphaseList(Statement stmt) throws Exception {
		List<String> LayerphaseList = new ArrayList<String>();
		String sql = "select id, layerphase, groupnum from layerphasetable order by groupnum,layerphase";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				LayerphaseList.add(rs.getString("id"));
				LayerphaseList.add(rs.getString("layerphase"));
				LayerphaseList.add(rs.getString("groupnum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if (null != stmt)
				//stmt.close();
		}
		return LayerphaseList;
	}

	/**
	 * ����ݿ��õ�details��TestTypeList�б�
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public List searchTestTypeList(Statement stmt) throws Exception {
		List<String> TestTypeList = new ArrayList<String>();
		//String sql = "select testtypeid, testtype, groupnum from testtypes order by groupnum,testtype";
		//hanxiaoyu01 2012-12-18
		//增加了hide=‘0’的条件 FWJ 2013-05-09 

		String sql="select * from testtype where hide=0 order by testtype";
		
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TestTypeList.add(rs.getString("testtypeid"));
				TestTypeList.add(rs.getString("testtype"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if (null != stmt)
				//stmt.close();
		}
		return TestTypeList;
	}

	/**
	 * ����ݿ��õ�details��TestSessionList�б�
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 */
	@SuppressWarnings("unchecked")
	public List searchTestSessionList(Statement stmt) throws Exception {
		List<String> TestSessionList = new ArrayList<String>();
		String sql = "select id, testsessionname, groupnum from testsession order by groupnum,length(testsessionname),testsessionname";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				TestSessionList.add(rs.getString("id"));
				TestSessionList.add(rs.getString("testsessionname"));
				TestSessionList.add(rs.getString("groupnum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if (null != stmt)
				//stmt.close();
		}
		return TestSessionList;
	}
	
	/**
	 * ����ݿ��õ�details��MilestoneList�б�
	 * 
	 * @param conn
	 * @return DetailList
	 * @throws Exception
	 * 
	 * @Author=Longzhe 添加了hide语句 FWJ 2013-05-09
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public List searchMilestoneList(Statement stmt) throws Exception {
		List<String> milestoneList = new ArrayList<String>();

		String sql = "select milestoneid, milestone, groupnum from milestones where hide=0 order by groupnum,milestone";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				milestoneList.add(rs.getString("milestoneid"));
				milestoneList.add(rs.getString("milestone"));
				milestoneList.add(rs.getString("groupnum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if (null != stmt)
				//stmt.close();
		}
		return milestoneList;
	}
	/**
	 * @author Dancy 2011-10-26 ��ȡdescription�б�
	 * @flag
	 */
	
	@SuppressWarnings("unchecked")
	public List searchDescriptionList(Statement stmt) throws Exception {
		List<String> descriptionList = new ArrayList<String>();
		String sql = "select descriptionid, description, groupnum from descriptions order by groupnum,description";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				descriptionList.add(rs.getString("descriptionid"));
				descriptionList.add(rs.getString("description"));
				descriptionList.add(rs.getString("groupnum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
			//if (null != stmt)
				//stmt.close();
		}
		return descriptionList;
	}

	/**
	 * ��ָ�����ڷ�Χ�ڵ�ָ��Group�µ���ݵ�������Detail��ݵ��¸�ʽ��2011.4.12��
	 * 
	 * @param conn
	 * @param startDate
	 * @param endDate
	 * @param groupName
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 * @flag
	 */
	public ReportData_Basic searchDetailExpenseData(Connection conn,String startDate, String endDate, String gid, SysUser u)
			throws Exception {
		ReportData_Basic reportData = new ReportData_Basic();
		List<Project> listProject = new ArrayList<Project>();
		List<Date> listDate = new ArrayList<Date>();
		List<Double> listHours = new ArrayList<Double>();
		List<String> listHoursComment = new ArrayList<String>();
		List<String> listFirmware = new ArrayList<String>();
		List<String> listWorkType = new ArrayList<String>();
		List<String> listMilestone = new ArrayList<String>();
		List<String> listTestType = new ArrayList<String>();
		//FWJ on 2013-05-07
		List<String> listTargetLaunch = new ArrayList<String>();
		//FWJ on 2013-05-23
		List<String> listDes= new ArrayList<String>();
		List<String> listBudget= new ArrayList<String>();
		int countOfRecord = 0;

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;// by collie 0503
		String flag = "";
		if(!gid.equals("-1"))
		{
			flag = " and projects.groupId in ("+gid+")";
		}
		if(u.getLevelID()>4)
		{
			flag = flag + " and projects.userid="+u.getUserId();
		}
 
		// Get projects,hours,comments,details,dates // by collie 0503
		sql = "select expensedata.createTime 'ecreateTime'"
				+ ",case when expensedata_details.WorkTypeId is not null then worktype.worktype else '' end  as 'WorkType'"
				+ ",case when expensedata_details.testtypeid is not null then testtype.testtype else '' end  as 'TestType'"
				+ ",case when expensedata_details.targetlaunchid is not null then targetlaunch.targetlaunch else '' end  as 'TargetLaunch'"// FWJ 2013-05-07
				+ ",case when expensedata_details.budgetid is not null then budgettracking.budget else '' end  as 'Budget'" //by fwj 2013-05-23
				+ ",case when expensedata_details.milestoneid is not null then milestones.milestone else '' end  as 'MileStone'"
				+ ",expensedata_details.hours as 'workload'"
				+ ",case when expensedata_details.firmware is not null then expensedata_details.firmware else '' end as 'firmware'"// by jason 0701 
				+ ",case when expensedata_details.descriptionofskill is not null then expensedata_details.descriptionofskill else '' end as 'descriptionofskill'"// by fwj 2013-05-23
				+ ",expensedata_details.memo 'comments' "//�޸�by Dancy 20111109
				+ ",groups.groupname "
				+ ",po.PONumber"
				+ ",projects.* "
				+ ",user_table.username, user_table.user_id "
				+ "from user_table, projects "
				+ "left join expensedata on projects.projectId=expensedata.projectId "
				+ "left join expensedata_details on expensedata.expensedataid=expensedata_details.ExpenseId "
				+ "left join worktype on worktype.id=expensedata_details.worktypeId "
				+ "left join testtype on testtype.testtypeid=expensedata_details.testtypeid "
				+ "left join targetlaunch on targetlaunch.targetlaunchid=expensedata_details.targetlaunchid "//FWJ on 2013-05-07
				+ "left join budgettracking on budgettracking.budgetid=expensedata_details.budgetid "
				+ "left join milestones on expensedata_details.milestoneid=milestones.milestoneid "
				+ "left join po on po.POID=projects.PONumberid "
				+ "left join groups on projects.groupId=groups.groupId "
				+ "where expensedata.createTime>='"
				+ startDate
				+ "' "
				+ "and expensedata.createTime<='"
				+ endDate
				+ "' "
				+ "and expensedata.hours>0"
				+ flag
				+ " and projects.userId = user_table.user_id "
				+ "order by expensedata.createTime,projects.componentName,projects.LocationID,projects.location,projects.OTType,worktype.worktype,projects.skillLevel";
//		System.out.println("sql is:"+sql);
		try {
			pstmt = conn.prepareStatement(sql);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Project project = new Project();
				project.setCreateTime(rs.getDate("createTime"));
				project.setGroupName(rs.getString("groupName"));
				project.setLocation(rs.getString("location"));
				project.setOTType(rs.getString("OTType"));
				project.setProjectId(rs.getInt("projectId"));
				project.setComponent(rs.getString("componentName"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setProduct(rs.getString("product"));
				project.setWBS(rs.getString("WBS"));
				project.setUsername(rs.getString("username"));
				project.setPOName(rs.getString("PONumber"));
				listProject.add(project);

				if(rs.getString("workload")==null)
				{
					listHours.add(0.0);
				}
				else
				{
					listHours.add(rs.getDouble("workload"));
				}
				String comments = rs.getString("comments");
				listHoursComment.add(comments);
				
				listFirmware.add(rs.getString("firmware"));

				listDate.add(rs.getDate("ecreateTime"));
				
				listWorkType.add(rs.getString("WorkType"));
				listMilestone.add(rs.getString("MileStone"));
				listTestType.add(rs.getString("TestType"));
				listTargetLaunch.add(rs.getString("TargetLaunch"));
				//FWJ on 2013-05-23
				listDes.add(rs.getString("descriptionofskill"));
				listBudget.add(rs.getString("Budget"));
				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(rs!=null)
			{
				rs.close();
			}
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}

		reportData.setListProject(listProject);
		reportData.setListDate(listDate);
		reportData.setListHours(listHours);
		reportData.setListHoursComment(listHoursComment);
		reportData.setListFirmware(listFirmware);
		reportData.setCountOfRecord(countOfRecord);
		reportData.setListWorkType(listWorkType);
		reportData.setListMilestone(listMilestone);
		reportData.setListTestType(listTestType);
		reportData.setListTargetLaunch(listTargetLaunch);
		//FWJ on 2013-05-23
		reportData.setListDes(listDes);
		reportData.setListBudget((listBudget));
		return reportData;
	}
	
	/**
	 * ��ָ�����ڷ�Χ�ڵ�ָ��Group�µ���ݵ�������Detail��ݵ��¸�ʽ��2011.4.12��
	 * 
	 * @param conn
	 * @param startDate
	 * @param endDate
	 * @param groupName
	 * @return
	 * @throws Exception
	 * @author xiaofei
	 * @flag
	 */
	public ReportData_Basic searchDetailExpenseData2(Connection conn,String startDate, String endDate, String groupNames, SysUser u)
			throws Exception {
		ReportData_Basic reportData = new ReportData_Basic();
		List<Project> listProject = new ArrayList<Project>();
		List<Date> listDate = new ArrayList<Date>();
		List<Double> listHours = new ArrayList<Double>();
		List<String> listHoursComment = new ArrayList<String>();
		List<String> listFirmware = new ArrayList<String>();
		List<String> listWorkType = new ArrayList<String>();
		List<String> listMilestone = new ArrayList<String>();
		List<String> listTestType = new ArrayList<String>();
		//FWJ on 2013-05-07
		List<String> listTargetLaunch = new ArrayList<String>();
		//FWJ on 2013-05-23
		List<String> listDes= new ArrayList<String>();
		List<String> listBudget= new ArrayList<String>();
		int countOfRecord = 0;

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;// by collie 0503
		String flag = "";
		if(groupNames.indexOf(",")>0)
		{
			groupNames = groupNames.substring(0,groupNames.length()-1);
			flag = " and groups.groupName in ("+groupNames+")";			
		}
		System.out.println("here groups 2 is: "+ groupNames);
		if(u.getLevelID()==4)
		{
			flag = " and groups.groupName='"+groupNames+"'";	
		}
		System.out.println("here groups 3 is: "+ groupNames);
		// Get projects,hours,comments,details,dates // by collie 0503
		sql = "select expensedata.createTime 'ecreateTime'"
				+ ",case when expensedata_details.WorkTypeId is not null then worktype.worktype else '' end  as 'WorkType'"
				+ ",case when expensedata_details.testtypeid is not null then testtype.testtype else '' end  as 'TestType'"
				+ ",case when expensedata_details.targetlaunchid is not null then targetlaunch.targetlaunch else '' end  as 'TargetLaunch'"
				+ ",case when expensedata_details.budgetid is not null then budgettracking.budget else '' end  as 'Budget'" //by fwj 2013-05-23
				+ ",case when expensedata_details.milestoneid is not null then milestones.milestone else '' end  as 'MileStone'"
				+ ",expensedata_details.hours as 'workload'"
				+ ",case when expensedata_details.firmware is not null then expensedata_details.firmware else '' end as 'firmware'"// by jason 0701 
				+ ",case when expensedata_details.descriptionofskill is not null then expensedata_details.descriptionofskill else '' end as 'descriptionofskill'"// by fwj 2013-05-23 
				+ ",expensedata_details.memo 'comments' "//�޸�by Dancy 20111109
				+ ",groups.groupname "
				+ ",po.PONumber"
				+ ",projects.* "
				+ ",user_table.username, user_table.user_id "
				+ "from user_table, projects "
				+ "left join expensedata on projects.projectId=expensedata.projectId "
				+ "left join expensedata_details on expensedata.expensedataid=expensedata_details.ExpenseId "
				+ "left join worktype on worktype.id=expensedata_details.worktypeId "
				+ "left join testtype on testtype.testtypeid=expensedata_details.testtypeid "
				+ "left join targetlaunch on targetlaunch.targetlaunchid=expensedata_details.targetlaunchid "
				+ "left join budgettracking on budgettracking.budgetid=expensedata_details.budgetid "
				+ "left join milestones on expensedata_details.milestoneid=milestones.milestoneid "
				+ "left join po on po.POID=projects.PONumberid "
				+ "left join groups on projects.groupId=groups.groupId "
				+ "where expensedata.createTime>='"
				+ startDate
				+ "' "
				+ "and expensedata.createTime<='"
				+ endDate
				+ "' "
				+ "and expensedata.hours>0"
				+ flag
				+ " and projects.userId = user_table.user_id "
				+ "order by expensedata.createTime,projects.componentName,projects.LocationID,projects.location,projects.OTType,worktype.worktype,projects.skillLevel";
//		System.out.println("sql is:"+sql);
		try {
			pstmt = conn.prepareStatement(sql);	
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Project project = new Project();
				project.setCreateTime(rs.getDate("createTime"));
				project.setGroupName(rs.getString("groupName"));
				project.setLocation(rs.getString("location"));
				project.setOTType(rs.getString("OTType"));
				project.setProjectId(rs.getInt("projectId"));
				project.setComponent(rs.getString("componentName"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setProduct(rs.getString("product"));
				project.setWBS(rs.getString("WBS"));
				project.setUsername(rs.getString("username"));
				project.setPOName(rs.getString("PONumber"));
				listProject.add(project);

				if(rs.getString("workload")==null)
				{
					listHours.add(0.0);
				}
				else
				{
					listHours.add(rs.getDouble("workload"));
				}
				String comments = rs.getString("comments");
				listHoursComment.add(comments);
				
				listFirmware.add(rs.getString("firmware"));

				listDate.add(rs.getDate("ecreateTime"));
				
				listWorkType.add(rs.getString("WorkType"));
				listMilestone.add(rs.getString("MileStone"));
				listTestType.add(rs.getString("TestType"));
				//FWJ on 2013-05-07
				listTargetLaunch.add(rs.getString("TargetLaunch"));
				//FWJ on 2013-05-23
				listDes.add(rs.getString("descriptionofskill"));
				listBudget.add(rs.getString("Budget"));

				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(rs!=null)
			{
				rs.close();
			}
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}

		reportData.setListProject(listProject);
		reportData.setListDate(listDate);
		reportData.setListHours(listHours);
		reportData.setListHoursComment(listHoursComment);
		reportData.setListFirmware(listFirmware);
		reportData.setCountOfRecord(countOfRecord);
		reportData.setListWorkType(listWorkType);
		reportData.setListMilestone(listMilestone);
		reportData.setListTestType(listTestType);
		//FWJ on 2013-05-07
		reportData.setListTargetLaunch(listTargetLaunch);
		//FWJ on 2013-05-23
		reportData.setListDes(listDes);
		reportData.setListBudget((listBudget));
		return reportData;
	}
	
	/**
	 * ά��date2������expensedata createtime, ά��date2��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 */
	@SuppressWarnings("unchecked")
	public List searchDate2(Statement stmt ) throws Exception
	{
		List<String> dateList = new ArrayList<String>();
		String sql = "select Min(dateb) as f, Max(dateb) as t from date2;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dateList.add(rs.getString("f"));
				dateList.add(rs.getString("t"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return dateList;
	}
	/**
	 * ά��date2��
	 * 
	 * @author longzhe
	 */
	public boolean updateDate2(Statement stmt ) throws Exception
	{
		String sql = "call updateDate2();";
		boolean result = false;
		try {
			int i = stmt.executeUpdate(sql);
			result = i>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List searchExpensedataCreatetime(Statement stmt ) throws Exception
	{
		List<String> dateList = new ArrayList<String>();
		String sql = "select Min( createtime ) as f, Max( createtime ) as t from expensedata;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dateList.add(rs.getString("f"));
				dateList.add(rs.getString("t"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return dateList;
	}
	/**
	 * ά��date2��
	 * 
	 * @author longzhe
	 */
	public boolean updateExpensedataCreatetime(Statement stmt ) throws Exception
	{
		String sql = "call updateExpensedataCreatetime();";
		boolean result = false;
		try {
			int i = stmt.executeUpdate(sql);
			result = i>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List searchdate(Statement stmt ) throws Exception
	{
		List<String> dateList = new ArrayList<String>();
		String sql = "select Min( datea ) as f, Max( datea ) as t from date;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dateList.add(rs.getString("f"));
				dateList.add(rs.getString("t"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return dateList;
	}
	/**
	 * ά��date��
	 * 
	 * @author longzhe
	 */
	public boolean updateDate(Statement stmt ) throws Exception
	{
		String sql = "call updateDate();";
		boolean result = false;
		try {
			int i = stmt.executeUpdate(sql);
			result = i>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return result;
	}
	/**
	 * weekly paychex reprot
	 * 
	 * @author longzhe
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public List weeklyPaychexExport(Connection conn , String startDate , String endDate) throws Exception
	{
		String sql = "select U.user_id , U.HPEmployeeNumber , U.username , U.leaveTime , E.projectId , E.hours ,  ( select projects.OTType from projects where projects.projectId=E.projectId ) as ottype "
						+" from user_table as U , expensedata as E "
						+" where ( U.account_disabled='f' or U.leaveTime>= '"+startDate+"')"
						+" and  E.userId = U.user_id "
						+" and E.createTime>='"+startDate+"' and E.createTime<='"+endDate+"' "
						+" and U.HPEmployeeNumber is not null "
						+" order by  U.username ;";
		
		
		List<Map> resultlist = new ArrayList();
		Statement stmt= null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			int i = -1;
			while(rs.next())
			{
				String userid = rs.getString("user_id");
				String HPEmployeeNumber = rs.getString("HPEmployeeNumber");
				String username = rs.getString("username");
				String hours = rs.getString("hours");
				String ottype = rs.getString("ottype");
				String leaveTime = rs.getString("leaveTime");
					i = resultlist.size()-1;
				if(i>=0&&userid.equals(resultlist.get(i).get("user_id"))) //user_id��ͬ��
				{
					if("OT".equals(ottype.substring(0,2)))//����ottypeΪOT��ͷ 
					{
						double temp = Double.parseDouble(resultlist.get(i).get("othours").toString()) + Double.parseDouble(hours);
						resultlist.get(i).put("othours", String.valueOf(temp));
					}
					else//not OT
					{
						double temp = Double.parseDouble(resultlist.get(i).get("regulerhours").toString()) + Double.parseDouble(hours);
						resultlist.get(i).put("regulerhours", String.valueOf(temp));
					}
				}
				else  //user_id��ͬ
				{
					Map record = new HashMap();
					record.put("user_id", userid);
					record.put("HPEmployeeNumber", HPEmployeeNumber);
					record.put("username", username);
					//@Dancy 2011-11-14
					record.put("leaveTime", leaveTime);
					
					if("OT".equals(ottype.substring(0, 2))) // Regular and OT hours
					{
						record.put("regulerhours", "0");
						record.put("othours", hours);
					}else{
						record.put("regulerhours", hours);
						record.put("othours", "0");
					}
					resultlist.add(record);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return resultlist;
	}
/**
 * 
 * @param conn
 * @param startDate
 * @param endDate
 * @return
 * @throws Exception
 * @flag
 */	
	@SuppressWarnings("unchecked")
	public List dailyExport(Connection conn , String startDate , String endDate) throws Exception
	{
		String sql = "select U.user_id , U.HPEmployeeNumber , U.username , E.projectId  , E.createTime ,  E.hours, U.leaveTime,  ( select projects.OTType from projects where projects.projectId=E.projectId ) as ottype "
						+"from user_table as U , expensedata as E "
						+"where E.userId = U.user_id "
						+"and E.createTime>='"+startDate+"' and E.createTime<='"+endDate+"' "
						+"and U.HPEmployeeNumber is not null and ( U.account_disabled='f' or U.leavetime >='"+startDate+"') "
						+"order by  U.username , E.createTime;";
		
		List<Map> resultlist = new ArrayList();
		Statement stmt= null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			int i = -1;
			while(rs.next())
			{
				String userid = rs.getString("user_id");
				String HPEmployeeNumber = rs.getString("HPEmployeeNumber");
				String username = rs.getString("username");
				String date = rs.getString("createTime");
				String hours = rs.getString("hours");
				String ottype = rs.getString("ottype");
				String leaveTime = rs.getString("leaveTime");
				
				if(resultlist.size()>0)
				{i = resultlist.size()-1;}
				// i���ڵ���0���û�����ͬ��������ͬ �ͺϲ�
				if(i>=0 && userid.equals(resultlist.get(i).get("user_id")) && date.equals(resultlist.get(i).get(("date"))))
				{
					if("OT".equals(ottype.substring(0,2)))//����ottypeΪOT��ͷ 
					{
						double temp = Double.parseDouble(resultlist.get(i).get("othours").toString()) + Double.parseDouble(hours);
						resultlist.get(i).put("othours", String.valueOf(temp));
					}
					else//not OT
					{
						double temp = Double.parseDouble(resultlist.get(i).get("regulerhours").toString()) + Double.parseDouble(hours);
						resultlist.get(i).put("regulerhours", String.valueOf(temp));
					}
				}
				else
				{
					Map record = new HashMap();
					record.put("date", date);
					record.put("user_id", userid);
					record.put("HPEmployeeNumber", HPEmployeeNumber);
					record.put("username", username);
					//@Dancy 2011-11-14
					record.put("leaveTime", leaveTime);
					
					if("OT".equals(ottype.substring(0, 2))) // Regular and OT hours
					{
						record.put("regulerhours", "0");
						record.put("othours", hours);
					}
					else
					{
						record.put("regulerhours", hours);
						record.put("othours", "0");
						resultlist.add(record);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != stmt)
				stmt.close();
		}
		return resultlist;
	}

	/**
	 * *Added by FWJ on 2013-03-07
	 * @param stmt
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public List searchTargetLaunchList(Statement stmt) throws Exception {
		List<String> TargetLaunchList = new ArrayList<String>();
		//增加了hide=‘0’的条件 FWJ 2013-05-09 

		String sql = "select * from targetlaunch where hide=0 order by targetLaunch";
		
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				TargetLaunchList.add(rs.getString("targetLaunchid"));
				TargetLaunchList.add(rs.getString("targetLaunch"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			
		}
		return TargetLaunchList;
	}
	/**
	 * *Added by FWJ on 2013-03-07
	 * @param stmt
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public List searchBudgetList(Statement stmt) throws Exception {
		List<String> BudgetLaunchList = new ArrayList<String>();
		//增加了hide=‘0’的条件 FWJ 2013-05-09 

		String sql = "select * from budgettracking where hide=0 order by budget";
		
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				BudgetLaunchList.add(rs.getString("budgetid"));
				BudgetLaunchList.add(rs.getString("budget"));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally 
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		return BudgetLaunchList;
	}
	//added on 2013-04-08
	public List<Invoice> seachermonthlyexpense(Connection conn, int POID, 
			int yearidstr, int monthidstr) throws Exception 
	{
		List<Invoice> listMonthExpense = new ArrayList<Invoice>();
		Invoice invoice = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String sql = "select e. *,e.monthproject,m.month,y.ProductYear,c.category,"
				+ "e.invoice,po.PONumber,f.FWQEOwner,w.wbs from monthlyexpense e "
				+ "left join month m on m.monthid = e.monthid "
				+ "left join productyear y on y.ProductYearID= e.yearid "
				+ "left join category c on c.categoryid = e.categoryid "
				+ "left join po on po.POID = e.ponumberid "
				+ "left join fwqeowner f on f.FWQEOwnerID = e.clientid "
				+ "left join wbs w on w.wbsid = e.wbsid "
				+ "where e.monthproject != ''";
		if (POID!=-1)
		{
			sql = sql + " and e.ponumberid = " + POID;
		}
		if (yearidstr!=-1)
		{
			sql = sql + " and e.yearid="+yearidstr;
		} 
		
		if (monthidstr!=-1)
		{
			sql = sql + " and e.monthid="+monthidstr;
			System.out.println(sql);
		} 
		sql += " order by e.monthproject";
//		System.out.print(sql);
		try 
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				invoice = new Invoice();
				invoice.setMonthproject(rs.getString("monthproject"));
				invoice.setYear(rs.getString("ProductYear"));
				invoice.setMonth(rs.getString("month"));
				invoice.setCategory(rs.getString("category"));
				invoice.setPONum(rs.getString("PONumber"));
				invoice.setClient(rs.getString("FWQEOwner"));
				invoice.setCost(rs.getString("cost"));
				invoice.setWBSNumber(rs.getString("wbs"));
				invoice.setInvoiceNumber(rs.getString("invoice"));
				invoice.setComment(rs.getString("comment"));
				listMonthExpense.add(invoice);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		
		return listMonthExpense;
	}	
	
	//added on 2013-06-20
	public List<Monthlyproject> seacherMonthly(Connection conn, List<Monthlyproject> monthproject) throws Exception 
	{
		List<Monthlyproject> listMonthly = new ArrayList<Monthlyproject>();
		Monthlyproject mp;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String str="''";
		
		for (int i=0;i<monthproject.size();i++){
			str=str+",'"+monthproject.get(i).getMonthproject()+"'";
		}
		
		str="("+str+")";
//		System.out.println(str);
		String sql = "select e.monthproject,l.locationName, bc.category,payment.payment, m.*,y.ProductYear,po.PONumber," +
				"e.cost from monthlyexpense e " +
				"left join month m on m.monthid = e.monthid " +
				"left join productyear y on y.ProductYearID= e.yearid  " +
				"left join po on po.POID = e.ponumberid " +
				"left join locations l on l.locationId=(select mp.locationid from monthlyproject_details mp where mp.projectid=" +
				"(select mt.monthprojectid from monthproject mt where mt.monthproject=e.monthproject))" +
				"left join businesscategory bc on bc.id=(select mp.businesscategoryid from monthlyproject_details mp where mp.projectid=" +
				"(select mt.monthprojectid from monthproject mt where mt.monthproject=e.monthproject))" +
				"left join payment on payment.id=(select mp.downpaymentid from monthlyproject_details mp where mp.projectid=" +
				"(select mt.monthprojectid from monthproject mt where mt.monthproject=e.monthproject))" +
				" where e.monthproject in "+str+"order by e.monthproject, y.productyear, m.monthid;";
//		String sql ="select mt.monthproject,l.locationName, bc.category,p.payment,y.ProductYear, m.month,po.PONumber,e.cost from monthlyproject_details mp " +
//				"left join monthproject mt on mt.monthprojectid=mp.projectid " +
//				"left join locations l on l.locationid = mp.locationid " +
//				"left join businesscategory bc on bc.id = mp.businesscategoryid " +
//				"left join payment p on p.id = mp.downpaymentid " +
//				"left join month m on m.monthid = (select e.monthid from monthlyexpense e where e.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)) " +
//				"left join productyear y on y.ProductYearID = (select e.yearid from monthlyexpense e where e.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)) " +
//				"left join po on po.POID = (select e.ponumberid from monthlyexpense e where e.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)) " +
//				"left join monthlyexpense e on e.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid) " +
//				"where mp.projectid in"+str+" order by mt.monthproject, y.ProductYear;";
		try 
		{
			pstmt = conn.prepareStatement(sql);
		//	pstmt.setString(1, str);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				mp = new Monthlyproject();
				mp.setMonthproject(rs.getString("monthproject"));
				mp.setLocation(rs.getString("locationName"));
				mp.setBusinesscategory(rs.getString("category"));
				mp.setPayment(rs.getString("payment"));
				mp.setYear(rs.getString("productyear"));
				mp.setMonth(rs.getString("month"));
				mp.setPo(rs.getString("ponumber"));
				mp.setCost(rs.getString("cost"));
				listMonthly.add(mp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if (pstmt!=null){
				pstmt.close();
			}
		}
	//	System.out.println("listMonthly="+listMonthly.size());
		return listMonthly;
	}
}
