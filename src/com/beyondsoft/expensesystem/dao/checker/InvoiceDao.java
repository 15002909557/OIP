package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyondsoft.expensesystem.domain.checker.Invoice;
import com.beyondsoft.expensesystem.domain.checker.Monthlyproject;

@SuppressWarnings("unchecked")
public class InvoiceDao 
{
	
	//Invoicedao
	//@auther yanglin
	private static InvoiceDao dao = null;

	public static InvoiceDao getInstance()
	{
		if (dao == null) 
		{
			dao = new InvoiceDao();
		}
		return dao;
	}
/**
 * 取得monthly expense的year list			
 * @param conn
 * @return
 * @throws Exception
 */
	public List searchyr(Connection conn, int i) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		String sql = "select ProductYearId,ProductYear from productyear";
		if(i==0)
		{
			sql=sql+" where hide = 0";
		}

		Map<String,String> map = new HashMap<String, String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				map = new HashMap<String, String>();
				map.put("yearid",rs.getString("ProductYearId"));
				map.put("year",rs.getString("ProductYear"));
				list.add(map);
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
	
/**
 * 取得monthly expense的month list
 * @param conn
 * @return
 * @throws Exception
 */
	public List searchmt(Connection conn) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		String sql = "select monthid,month from month";
		Map<String,String> map = new HashMap<String, String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				map = new HashMap<String, String>();
				map.put("monthid",rs.getString("monthid"));
				map.put("month",rs.getString("month"));
				list.add(map);			
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 	取得monthly expense的category list
 * @param conn
 * @return
 * @throws Exception
 */
	public List searchcg(Connection conn) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		String sql = "select categoryid,category from category where hide =0";

		Map<String,String> map = new HashMap<String, String>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				map = new HashMap<String, String>();
				map.put("categoryid",rs.getString("categoryid"));
				map.put("category",rs.getString("category"));
				list.add(map);		
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 取得monthly expense的project name list			
 * @param conn
 * @return
 * @throws Exception
 */
	public List<String> searchmp(Connection conn) throws Exception
	{
		List<String> list = new ArrayList<String>();
		String sql = "select monthprojectid,monthproject from monthproject";
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getString("monthproject"));		
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 取得monthly expense的client list		
 * @param conn
 * @return
 * @throws Exception
 */
	public List searchct(Connection conn) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		//添加了hide=‘0’的条件 FWJ 2013-05-03
		String sql = "select fwqeownerid,fwqeowner from fwqeowner where hide=0";
		Map<String,String> map = new HashMap<String, String>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				map = new HashMap<String, String>();
				map.put("clientid",rs.getString("fwqeownerid"));
				map.put("client",rs.getString("fwqeowner"));
				list.add(map);			
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 取得wbs list	
 * @param conn
 * @return
 * @throws Exception
 */
	public List searchwbs(Connection conn) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		//添加了Hide=‘0’的条件 FWJ 2013-05-03
		String sql = "select wbsid,wbs from wbs where hide=0";
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		Map<String,String> map = new HashMap<String, String>();
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{

				map = new HashMap<String, String>();
				map.put("wbsid",rs.getString("wbsid"));
				map.put("wbs",rs.getString("wbs"));
				list.add(map);			
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 取得PO number list			
 * @param conn
 * @return
 * @throws Exception
 */
	
	public List seacherpn(Connection conn) throws Exception
	{
		
		List<Map> list = new ArrayList<Map>();
		Map<String,String> map = new HashMap<String, String>();
		String sql="select PONumber,POID from po";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try 
		{
			pstmt =conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			
			while (rs.next()) 
			{
				map = new HashMap<String, String>();
				map.put("POID",rs.getString("POID"));
				map.put("PONumber",rs.getString("PONumber"));
				list.add(map);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return list;
	
	}
/**
 * 搜索MonthlyExpense结果。通过POID搜索出结果 FWJ on 2013-04-22 
 * @param conn
 * @param POID
 * @param yearidstr
 * @param monthidstr
 * @return
 * @throws Exception
 */
	public List<Invoice> searchbyMonthlyEx(Connection conn,int POID) throws Exception
	{
		List<Invoice> plist = new ArrayList<Invoice>();
		
		String sql = "select e. *,m.month,y.ProductYear,c.category,po.PONumber, "
			+ "f.FWQEOwner,w.wbs from monthlyexpense e "
			+ "left join month m on m.monthid = e.monthid "
			+ "left join productyear y on y.ProductYearID= e.yearid "
			+ "left join category c on c.categoryid = e.categoryid "
			+ "left join po on po.POID = e.ponumberid "
			+ "left join fwqeowner f on f.FWQEOwnerID = e.clientid "
			+ "left join wbs w on w.wbsid = e.wbsid "
			+ "where e.monthproject != ''";
		
		if(POID!=-1)
		{
			sql=sql+ " and e.ponumberid = "+POID;
		}
		
		sql=sql+" order by e.monthlyexpenseid desc";
/*		if (yearidstr!=-1)
		{
			sql = sql + " and e.yearid = "+yearidstr;
		} 
		
		if (monthidstr!=-1)
		{
			sql = sql + " and e.monthid = "+monthidstr;
		} */
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			while (rs.next()) 
			{
				Invoice ic = new Invoice();
				ic.setMonthlyexpenseid(rs.getInt("monthlyExpenseid"));
				ic.setMonthproject(rs.getString("monthproject"));
				ic.setYear(rs.getString("Productyear"));
				ic.setMonth(rs.getString("month"));
				ic.setCategory(rs.getString("category"));
				ic.setPONum(rs.getString("ponumber"));
				ic.setClient(rs.getString("fwqeowner"));
				ic.setCost(rs.getString("cost"));
				ic.setWBSNumber(rs.getString("WBS"));
				ic.setInvoiceNumber(rs.getString("invoice"));
				ic.setComment(rs.getString("Comment"));
				//增加了payment FWJ 2013-06-20
				ic.setPayment(rs.getString("payment"));
				plist.add(ic);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return plist;
	}
/**
 * 	remove a monthly expense record
 * @param stmt
 * @param a
 * @return
 * @throws Exception
 */
	public boolean deleteInvoice(Connection conn,int expenseId, int poid) throws Exception 
	{
		boolean result = false;
		String sql = "delete from monthlyexpense where monthlyexpenseid=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, expenseId);
			int i = pstmt.executeUpdate();
			result = i>0;
			//删除一条monthly expense后必须更新对应po的poused值 by dancy
			if(result)
			{
				this.updatepob(conn, poid);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return result;
	}

	/**
	 *  add a new monthly expense
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean addInvoice(Connection conn, Invoice expense) throws Exception
	{
		boolean result = false;
		
		//sql中，尽量不要用字符串拼接（web安全），各字段类型要对应，数值类型的不要加''
		String sql = "insert into monthlyexpense (monthproject,yearid, monthid, categoryid," 
			+ "ponumberid, clientid,cost,wbsid,invoice,comment,payment) values(?,?,?,?,?,?,?,?,?,?,?);";
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expense.getMonthproject());
			pstmt.setInt(2, expense.getYearid());
			pstmt.setInt(3, expense.getMonthid());
			pstmt.setInt(4, expense.getCategoryid());
			pstmt.setInt(5, expense.getPOid());
			pstmt.setInt(6, expense.getClientid());
			pstmt.setString(7,expense.getCost());
			pstmt.setInt(8, expense.getWBSid());
			pstmt.setString(9, expense.getInvoiceNumber());
			pstmt.setString(10, expense.getComment());
			pstmt.setString(11, expense.getPayment());
			int i = pstmt.executeUpdate();
			result = i>0;
			//这里判断如果存储monthly expense cost成功直接去更新po的po used
			if(result)
			{
				this.updatepob(conn, expense.getPOid());
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
/**
 * 判断是否当前添加的monthlyExpense已经存在	
 * @param conn
 * @param expense
 * @return
 * @throws Exception
 */
	public boolean ifExist(Connection conn, Invoice expense, String flag)throws Exception
	{
		boolean result = false;
		String sql = "select * from monthlyexpense where monthproject=? and yearid=? and monthid=?" 
				+ " and categoryid=? and ponumberid=? and clientid=? and wbsid=? and invoice=? and payment=?";
		if(flag.equals("edit"))
		{
			sql = sql + "and monthlyExpenseid!=?";
		}
		System.out.println("sql in dao is="+sql);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, expense.getMonthproject());
			pstmt.setInt(2, expense.getYearid());
			pstmt.setInt(3,expense.getMonthid());
			pstmt.setInt(4,expense.getCategoryid());
			pstmt.setInt(5,expense.getPOid());
			pstmt.setInt(6,expense.getClientid());
			pstmt.setInt(7,expense.getWBSid());
			pstmt.setString(8,expense.getInvoiceNumber());
			pstmt.setString(9, expense.getPayment());
			if(flag.equals("edit"))
			{
				pstmt.setInt(10,expense.getMonthlyexpenseid());
			}
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(rs!=null)
			{
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}

	
/**
 * 判断是否当前添加的monthlyExpense已经存在	
 * @param conn
 * @param expense
 * @return
 * @throws Exception
 */
	public boolean ifExistProject(Connection conn, String project)throws Exception
	{
		boolean result = false;
		String sql = "select * from monthproject where monthproject=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, project);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(rs!=null)
			{
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
		
/**
 * insert a new month project name
 * @param conn
 * @param objName
 * @return
 * @throws Exception
 */
	public boolean newMonthProject(Connection conn,String objName)throws Exception 
	{
		boolean result = false;
		String sql = "insert into monthproject(monthproject) values(?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, objName);
			int i = pstmt.executeUpdate();
			result = i>0;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
/**
 * 	
 * @param stmt
 * @param poid
 * @return
 * @throws Exception
 */
	public boolean updatepob(Connection conn, int poid) throws Exception 
	{
		boolean result = false;
		String sql = "update po p set POUsed=(select sum(m.cost) from monthlyexpense m where m.ponumberid=p.POID)"
				+ ",p.POBalance=p.POAmount-p.POUsed where p.POID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, poid);
			int i = pstmt.executeUpdate();
			result = i>0;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return result;	
	}
	
	/**
	 *  search monthly expense by Id 
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 * @author 
	 */
	public Invoice searchExpenseById(Connection conn, int monthlyexpenseid) throws Exception
	{
		Invoice expense = new Invoice();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select e. *,m.month,y.ProductYear,c.category,po.PONumber, "
			+ "f.FWQEOwner,w.wbs from monthlyexpense e "
			+ "left join month m on m.monthid = e.monthid "
			+ "left join productyear y on y.ProductYearID= e.yearid "
			+ "left join category c on c.categoryid = e.categoryid "
			+ "left join po on po.POID = e.ponumberid "
			+ "left join fwqeowner f on f.FWQEOwnerID = e.clientid "
			+ "left join wbs w on w.wbsid = e.wbsid "
			+ "where e.monthlyExpenseid = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, monthlyexpenseid);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				expense.setMonthlyexpenseid(rs.getInt("monthlyExpenseid"));
				expense.setMonthproject(rs.getString("monthproject"));
				expense.setYear(rs.getString("ProductYear"));
				expense.setMonth(rs.getString("month"));
				expense.setCategory(rs.getString("category"));
				expense.setPONum(rs.getString("PONumber"));
				expense.setClient(rs.getString("FWQEOWner"));
				expense.setCost(rs.getString("cost"));
				expense.setWBSNumber(rs.getString("wbs"));
				expense.setInvoiceNumber(rs.getString("invoice"));
				expense.setComment(rs.getString("comment"));
				//Added by FWJ 2013-06-20
				expense.setPayment(rs.getString("payment"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			if (rs != null) 
			{
				rs.close();
			}
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return expense;
	}
	/**
	 *  update a monthly expense record
	 * 
	 * @param mapping
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 * @author longzhe
	 */
	public boolean editInvoice(Connection conn, Invoice expense) throws Exception
	{
		
		boolean result = false;
		String sql = "update monthlyexpense set yearid=?, monthid=?, categoryid=?, ponumberid=?, "
			+ " clientid=?, cost=?, wbsid=?, monthproject=?, invoice=?, Comment=?, payment=?"
			+ " where monthlyexpenseid=?;";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,expense.getYearid());
			pstmt.setInt(2,expense.getMonthid());
			pstmt.setInt(3,expense.getCategoryid());
			pstmt.setInt(4,expense.getPOid());
			pstmt.setInt(5,expense.getClientid());
			pstmt.setString(6,expense.getCost());
			pstmt.setInt(7,expense.getWBSid());
			pstmt.setString(8,expense.getMonthproject());
			pstmt.setString(9,expense.getInvoiceNumber());
			pstmt.setString(10, expense.getComment());
			pstmt.setString(11, expense.getPayment());
			pstmt.setInt(12,expense.getMonthlyexpenseid());
			
			int i = pstmt.executeUpdate();
			result = i>0;
			if(result)
			{
				this.updatepob(conn, expense.getPOid());
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return result;
	}
	/*---------------------Monthly Project-----------------------------------------------*/
	
	/**
	 * 搜索MonthlyExpense结果。通过POID搜索出结果 FWJ on 2013-04-22 
	 * @param conn
	 * @param POID
	 * @param yearidstr
	 * @param monthidstr
	 * @return
	 * @throws Exception
	 */
		public List<Monthlyproject> searchbyMonthlyproject(Connection conn) throws Exception
		{
			List<Monthlyproject> mplist = new ArrayList<Monthlyproject>();
			
			String sql = "select m.*,mp.monthproject, l.locationName, b.category, p.payment from monthlyproject_details m " +
					"left join monthproject mp on m.projectid=mp.monthprojectid "+
					"left join locations l on m.locationid=l.locationid " +
					"left join businesscategory b on m.businesscategoryid=b.id "+
					"left join payment p on m.downpaymentid=p.id "+
					"order by m.id desc";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = conn.prepareStatement(sql);
				rs =pstmt.executeQuery();
				while (rs.next()) 
				{
					Monthlyproject mp = new Monthlyproject();
					mp.setMonthprojectid(rs.getInt("id"));
					mp.setMonthproject(rs.getString("monthproject"));
					mp.setLocationid(rs.getInt("locationid"));
					mp.setLocation(rs.getString("locationName"));
					mp.setBusinesscategory(rs.getString("category"));
					mp.setPayment(rs.getString("payment"));
					mp.setBudget(rs.getDouble("budget"));
					mp.setUsedBudegt(rs.getDouble("usedbudget"));
					mp.setRemainBalance(rs.getDouble("remainningbudget"));
				//Added by FWJ 2013-12-18	
					mp.setCostInLatestMonth(rs.getDouble("costinlatestmonth"));
					mplist.add(mp);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			} finally
			{
				if (rs != null)
				{
					rs.close();
				}
				if (pstmt != null)
				{
					pstmt.close();
				}
			}
			return mplist;
		}
	
	public List<Map> searchLocation (Connection con) throws Exception{
		List<Map> list = new ArrayList<Map>();
		Map<String, String> map;
		String sql = "select * from locations where hide!=1";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try{
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				map= new HashMap<String,String>();
				map.put("locationid", rs.getString("locationid"));
				map.put("location", rs.getString("locationName"));
				list.add(map);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return list;
	}
	
	public List<Map> searchMonthProject (Connection con) throws Exception{
		List<Map> list= new ArrayList<Map>();
		Map<String,String> map;
		String sql="select * from monthproject";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				map.put("monthprojectid", rs.getString("monthprojectid"));
				map.put("monthproject", rs.getString("monthproject"));
				list.add(map);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
		
		return list;
	}
	
	public List<Map> searchPayment(Connection con) throws Exception{
		List<Map> list = new ArrayList<Map>();
		Map<String,String> map;
		String sql = "Select * from payment";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				map=new HashMap<String,String>();
				map.put("paymentid", rs.getString("id"));
				map.put("payment", rs.getString("payment"));
				list.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return list;
	}
	
	public List<Map> searchBcategory(Connection con) throws Exception{
		List<Map> list = new ArrayList<Map>();
		Map<String,String> map;
		String sql = "select * from businesscategory";
		PreparedStatement pstmt =null;
		ResultSet rs= null;
		try{
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				map = new HashMap<String,String>();
				map.put("id", rs.getString("id"));
				map.put("category", rs.getString("category"));
				list.add(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return list;
	}
	
	public boolean ifExistRecord(Connection conn, String category, String sql)throws Exception
	{
		boolean result = false;
		//String sql = "select * from businesscategoy where category=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(rs!=null)
			{
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public int getRecordid(Connection conn,String objName, String sql)throws Exception 
	{
		int i=-1;
	//	String sql = "select m.monthprojectid from monthproject m where monthproject=?";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, objName);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(sql.indexOf("monthproject")>=0){
					i=Integer.parseInt(rs.getString("monthprojectid"));
					}else if(sql.indexOf("businesscategory")>=0){
						i=Integer.parseInt(rs.getString("id"));
						}else if(sql.indexOf("payment")>=0){
							i=Integer.parseInt(rs.getString("id"));
						}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return i;
	}
	
	public boolean newRecord(Connection conn,String objName, String sql)throws Exception 
	{
		boolean result = false;
	//	String sql = "insert into businesscategory(category) values(?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, objName);
			int i = pstmt.executeUpdate();
			result = i>0;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public boolean ifExistProject(Connection conn, Monthlyproject mp, String str)throws Exception
	{
		boolean result = false;
		
//		String sql="select * from monthlyproject_details where projectid=? and locationid=? and " +
//				"businesscategoryid=? and downpaymentid=?";
		String sql="select * from monthlyproject_details where projectid=?";
		if(str.equals("edit"))
		{
			sql=sql+" and id!=?";
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mp.getMonthprojectid());
			
			if(str.equals("edit"))
			{
				pstmt.setInt(2, mp.getId());
			}
//			pstmt.setInt(2, mp.getLocationid());
//			pstmt.setInt(3, mp.getBusinesscategoryid());
//			pstmt.setInt(4, mp.getPaymentid());

			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(rs!=null)
			{
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public boolean addProject(Connection conn, Monthlyproject mp) throws Exception
	{
		boolean result = false;
		
		String sql = "insert into monthlyproject_details (projectid,locationid, businesscategoryid, downpaymentid, " 
			+ "budget, usedbudget,remainningbudget) values" +
			"(?,?,?,?,?,(select sum(m.cost) from monthlyexpense m where m.monthproject=?),(monthlyproject_details.budget-monthlyproject_details.usedbudget));";
//		String sql = "insert into monthlyproject_details (projectid,locationid, businesscategoryid, downpaymentid, " 
//			+ "budget) values" +
//			"(?,?,?,?,?);";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mp.getMonthprojectid());
			pstmt.setInt(2, mp.getLocationid());
			pstmt.setInt(3, mp.getBusinesscategoryid());
			pstmt.setInt(4, mp.getPaymentid());
			pstmt.setDouble(5, mp.getBudget());
			pstmt.setString(6, mp.getMonthproject());
			System.out.println();
			int i = pstmt.executeUpdate();
			result = i>0;
			if(result)
			{
				this.updateMonthlyCostInLatestMonthByProject(conn,mp.getMonthproject());
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{	
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public boolean editProject(Connection conn, Monthlyproject mp) throws Exception
	{
		
		boolean result = false;

		String sql = "update monthlyproject_details mp set projectid=?, locationid=?,businesscategoryid=?, " +
				"downpaymentid=?, budget=?, usedbudget=(select sum(m.cost) from monthlyexpense m where m.monthproject=?), " +
				"remainningbudget=(mp.budget-mp.usedbudget) where id=?;";
		PreparedStatement pstmt = null;
		System.out.println(sql);
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mp.getMonthprojectid());
			pstmt.setInt(2, mp.getLocationid());
			pstmt.setInt(3, mp.getBusinesscategoryid());
			pstmt.setInt(4, mp.getPaymentid());
			pstmt.setDouble(5, mp.getBudget());
			pstmt.setString(6, mp.getMonthproject());
			pstmt.setInt(7, mp.getId());
			
			int i = pstmt.executeUpdate();
			result = i>0;
			if(result)
			{
				this.updateMonthlyCostInLatestMonthByProject(conn,mp.getMonthproject());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally 
		{
			if (pstmt != null) 
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public boolean deleteProject(Connection con, int id) throws Exception
	{
		boolean result=false;
		String sql = "delete from monthlyproject_details where id=?";
		PreparedStatement pstmt = null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			int i=pstmt.executeUpdate();
			result=i>0;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return result;
	}
	
	public boolean updateMonthlyCost(Connection con) throws Exception
	{
		boolean result = false;
		String sql="update monthlyproject_details mp set usedbudget=(select sum(m.cost) from monthlyexpense m " +
				"where m.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)), " +
				"remainningbudget=(mp.budget-mp.usedbudget);";
		PreparedStatement pstmt = null;
		try
		{
			pstmt=con.prepareStatement(sql);
			int i= pstmt.executeUpdate();
			result=i>0;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		
		return result;
	}
	
	public List<Monthlyproject> toEditproject(Connection conn, int id) throws Exception
	{
		List<Monthlyproject> mplist = new ArrayList<Monthlyproject>();
		
		String sql = "select m.*,mp.monthproject, l.locationName, b.category, p.payment from monthlyproject_details m " +
				"left join monthproject mp on m.projectid=mp.monthprojectid "+
				"left join locations l on m.locationid=l.locationid " +
				"left join businesscategory b on m.businesscategoryid=b.id "+
				"left join payment p on m.downpaymentid=p.id "+
				"where m.id =?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs =pstmt.executeQuery();
			while (rs.next()) 
			{
				Monthlyproject mp = new Monthlyproject();
				mp.setMonthprojectid(rs.getInt("id"));
				mp.setMonthproject(rs.getString("monthproject"));
				mp.setLocationid(rs.getInt("locationid"));
				mp.setLocation(rs.getString("locationName"));
				mp.setBusinesscategory(rs.getString("category"));
				mp.setPayment(rs.getString("payment"));
				mp.setBudget(rs.getDouble("budget"));
				mp.setUsedBudegt(rs.getDouble("usedbudget"));
				mp.setRemainBalance(rs.getDouble("remainningbudget"));
				
				mplist.add(mp);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}
		return mplist;
	}
	/**
	 * Added by FWJ 2013-12-18
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public boolean updateMonthlyCostInLatestMonth(Connection con) throws Exception
	{
		boolean result = false;
//		String sql="update monthlyproject_details mp set usedbudget=(select sum(m.cost) from monthlyexpense m " +
//				"where m.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)), " +
//				"remainningbudget=(mp.budget-mp.usedbudget);";
		String sql = "update monthlyproject_details mp set costinlatestmonth =" +
				"(select sum(cost) as costinlatestmonth from monthlyexpense as m " +
				"LEFT join productyear as y on y.ProductYearID = m.yearid and y.ProductYear !='NA' " +
				"where m.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid) " +
				"GROUP BY CONCAT(y.ProductYear,lpad(m.monthid,2,0)) order by CONCAT(y.ProductYear,lpad(m.monthid,2,0)) desc limit 1);";
		PreparedStatement pstmt = null;
		try
		{
			pstmt=con.prepareStatement(sql);
			int i= pstmt.executeUpdate();
			result=i>0;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		
		return result;
	}
	
	/**
	 * Added by FWJ 2013-12-18
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public boolean updateMonthlyCostInLatestMonthByProject(Connection con,String str) throws Exception
	{
		boolean result = false;
//		String sql="update monthlyproject_details mp set usedbudget=(select sum(m.cost) from monthlyexpense m " +
//				"where m.monthproject=(select mt.monthproject from monthproject mt where mt.monthprojectid=mp.projectid)), " +
//				"remainningbudget=(mp.budget-mp.usedbudget);";
		String sql = "update monthlyproject_details mp set costinlatestmonth =" +
				"(select sum(cost) as costinlatestmonth from monthlyexpense as m " +
				"LEFT join productyear as y on y.ProductYearID = m.yearid and y.ProductYear !='NA' " +
				"where m.monthproject=? " +
				"GROUP BY CONCAT(y.ProductYear,lpad(m.monthid,2,0)) order by CONCAT(y.ProductYear,lpad(m.monthid,2,0)) desc limit 1) " +
				"where mp.projectid=(select mt.monthprojectid from monthproject mt where mt.monthproject=?);";
		PreparedStatement pstmt = null;
		try
		{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, str);
			pstmt.setString(2, str);
			int i= pstmt.executeUpdate();
			result=i>0;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		
		return result;
	}
}

