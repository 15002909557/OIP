package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyondsoft.expensesystem.domain.checker.CaseDefect;
import com.beyondsoft.expensesystem.domain.checker.DefaultCaseDefect;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.checker.NonLaborCost;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.domain.checker.StatusChangeLog;
import com.beyondsoft.expensesystem.domain.system.SysUser;

public class PODao {
	private static PODao dao = null;

	public static PODao getInstance() {
		if (dao == null) {
			dao = new PODao();
		}
		return dao;

	}

	/** *************************Case & Defect ********************************* */
	/**
	 * ���һ��case&defect��¼
	 * 
	 * @author dancy
	 * @param conn
	 * @param CaseDefect
	 * @return true:�ɹ�,false:ʧ��
	 * @throws Exception
	 */
	public boolean addCaseDefect(Connection conn, CaseDefect cd)
			throws Exception {
		boolean result = false;
		String sql = "insert into caseanddefect(productID,componentID,sDate,eDate,cases,urgentdefect,"
				+ "highdefect,normaldefect,lowdefect,milestoneid,creator,createTime) values (?,?,?,?,?,?,?,?,?,?,?,curdate());";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cd.getProductID());
			pstmt.setInt(2, cd.getComponentID());
			pstmt.setString(3, cd.getSDate());
			pstmt.setString(4, cd.getEDate());
			pstmt.setInt(5, cd.getCases());
			pstmt.setInt(6, cd.getUrgentdefect());
			pstmt.setInt(7, cd.getHighdefect());
			pstmt.setInt(8, cd.getNormaldefect());
			pstmt.setInt(9, cd.getLowdefect());
			pstmt.setInt(10, cd.getMilestoneid());
			pstmt.setString(11, cd.getCreator());

			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * ��ʼ��Case&Defectҳ�棬��ѯȡ�����е�porduct��component��milestone��casedefect
	 * 
	 * @author Dancy
	 * @param stmt
	 * @return arr(Productlist,Componentlist,Milestonelist)
	 * @throws Exception
	 * @comment NO USED!��δ�õ���
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<List> searchDetails(Statement stmt) throws Exception {
		ArrayList<List> arr = new ArrayList<List>();
		List<String> Productlist = new ArrayList<String>();
		List<String> Componentlist = new ArrayList<String>();
		List<String> Milestonelist = new ArrayList<String>();
		List<String> Caselist = new ArrayList<String>();
		String sql1 = "select product from products;";
		String sql2 = "select componentName from components;";
		String sql3 = "select milestone from milestones;";
		String sql4 = "select id,sDate,eDate,cases,urgentdefect,highdefect,normaldefect,lowdefect from caseanddefect;";
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		try {
			rs1 = stmt.executeQuery(sql1);
			while (rs1.next())
				Productlist.add(rs1.getString("products"));
			rs2 = stmt.executeQuery(sql2);
			while (rs2.next())
				Componentlist.add(rs2.getString("componentName"));
			rs3 = stmt.executeQuery(sql3);
			while (rs3.next())
				Milestonelist.add(rs3.getString("milestone"));
			rs4 = stmt.executeQuery(sql4);
			while (rs4.next()) {
				Caselist.add(rs4.getString("id"));
				Caselist.add(rs4.getString("sDate"));
				Caselist.add(rs4.getString("eDate"));
				Caselist.add(rs4.getString("cases"));
				Caselist.add(rs4.getString("urgentdefect"));
				Caselist.add(rs4.getString("highdefect"));
				Caselist.add(rs4.getString("normaldefect"));
				Caselist.add(rs4.getString("lowdefect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs1)
				rs1.close();
			if (null != rs2)
				rs2.close();
			if (null != rs3)
				rs3.close();
			if (null != rs4)
				rs4.close();
			if (null != stmt)
				stmt.close();
		}

		arr.add(0, Productlist);
		arr.add(1, Componentlist);
		arr.add(2, Milestonelist);
		arr.add(3, Caselist);
		return arr;
	}

	/**
	 * ȡ�����е�casedefect
	 * 
	 * @author Dancy
	 * @param stmt
	 * @return CaseList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCaseList(Statement stmt, SysUser u) throws Exception {
		String flag = "";
		if(u.getGroupID()!=-1)
		{
			flag = " where creator in (select username from user_table where workloadgroupId="+u.getGroupID()+") ";
		}
		if(u.getLevelID()>4)
		{
			flag = " where creator='"+u.getUserName()+"'";
		}
		List<CaseDefect> CaseList = new ArrayList<CaseDefect>();
		String sql = "select id, (select product from products p where p.productid = c.productID) as 'product',"
				+ " (select componentName from components s where s.componentid = c.componentID) as 'component', "
				+ "sDate, eDate, cases, urgentdefect, highdefect, normaldefect, lowdefect,"
				+ " (select milestone from milestones m where m.milestoneid = c.milestoneid ) as 'milestone',creator,createTime"
				+ " from caseanddefect c"+flag+" order by id desc;";
//		System.out.println("sql = "+sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CaseDefect cd = new CaseDefect();
				cd.setId(rs.getInt("id"));
				cd.setProduct(rs.getString("product"));
				cd.setComponentName(rs.getString("component"));
				cd.setSDate(rs.getString("sdate"));
				cd.setEDate(rs.getString("eDate"));
				cd.setCases(rs.getInt("cases"));
				cd.setUrgentdefect(rs.getInt("urgentdefect"));
				cd.setHighdefect(rs.getInt("highdefect"));
				cd.setNormaldefect(rs.getInt("normaldefect"));
				cd.setLowdefect(rs.getInt("lowdefect"));
				cd.setMilestone(rs.getString("milestone"));
				cd.setCreator(rs.getString("creator"));
				cd.setCreateTime(rs.getString("createTime"));
				CaseList.add(cd);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
			if (null != stmt)
				stmt.close();
		}
		return CaseList;

	}

	/**
	 * ͨ���ѯ �õ�casedefect list
	 * 
	 * @author Dancy
	 * @param stmt
	 * @param str
	 * @return CaseList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchCase(Statement stmt, StringBuffer str) throws Exception {
		List<CaseDefect> CaseList = new ArrayList<CaseDefect>();
		String sql = "select id, (select product from products p where p.productid = c.productID) as 'product',"
				+ " (select componentName from components s where s.componentid = c.componentID) as 'component', "
				+ "sDate, eDate, cases, urgentdefect, highdefect, normaldefect, lowdefect,creator,createTime,"
				+ " (select milestone from milestones m where m.milestoneid = c.milestoneid ) as 'milestone'"
				+ "from caseanddefect c" + str;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CaseDefect cd = new CaseDefect();
				cd.setId(rs.getInt("id"));
				cd.setProduct(rs.getString("product"));
				cd.setComponentName(rs.getString("component"));
				cd.setSDate(rs.getString("sdate"));
				cd.setEDate(rs.getString("eDate"));
				cd.setCases(rs.getInt("cases"));
				cd.setUrgentdefect(rs.getInt("urgentdefect"));
				cd.setHighdefect(rs.getInt("highdefect"));
				cd.setNormaldefect(rs.getInt("normaldefect"));
				cd.setLowdefect(rs.getInt("lowdefect"));
				cd.setMilestone(rs.getString("milestone"));
				cd.setCreator(rs.getString("creator"));
				cd.setCreateTime(rs.getString("createTime"));
				CaseList.add(cd);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
			if (null != stmt)
				stmt.close();
		}
		return CaseList;

	}

	/**
	 * ɾ��Case&Defect
	 * 
	 * @author Dancy
	 * @param stmt
	 * @param cid
	 * @return true��ɾ��ɹ���false��ɾ��ʧ��
	 * @throws Exception
	 */

	public boolean deleteCase(Statement stmt, int cid) throws Exception {
		String sql = "delete from caseanddefect where id=" + cid + ";";
		boolean result = false;
		try {
			int re = stmt.executeUpdate(sql);
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	/**
	 * Ԥ�޸�ĳ��casedefect��¼��ȡ�����������Ϣ��
	 * 
	 * @author Dancy
	 * @param stmt
	 * @param cid
	 * @return CaseDefect
	 * @throws Exception
	 */
	public CaseDefect editCase(Statement stmt, int cid) throws Exception {
		CaseDefect cd = null;
		String sql = "select id, productID, componentID,milestoneid, (select product from products p where p.productid = c.productID) as 'product',"
				+ " (select componentName from components s where s.componentid = c.componentID) as 'component', "
				+ "sDate, eDate, cases, urgentdefect, highdefect, normaldefect, lowdefect,"
				+ " (select milestone from milestones m where m.milestoneid = c.milestoneid ) as 'milestone'"
				+ " from caseanddefect c where id=" + cid;
		ResultSet rs = null;
		// System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				cd = new CaseDefect();
				cd.setId(rs.getInt("id"));
				cd.setProductID(rs.getInt("productID"));
				cd.setComponentID(rs.getInt("componentID"));
				cd.setProduct(rs.getString("product"));
				cd.setComponentName(rs.getString("component"));
				cd.setSDate(rs.getString("sdate"));
				cd.setEDate(rs.getString("eDate"));
				cd.setCases(rs.getInt("cases"));
				cd.setUrgentdefect(rs.getInt("urgentdefect"));
				cd.setHighdefect(rs.getInt("highdefect"));
				cd.setNormaldefect(rs.getInt("normaldefect"));
				cd.setLowdefect(rs.getInt("lowdefect"));
				cd.setMilestoneid(rs.getInt("milestoneid"));
				cd.setMilestone(rs.getString("milestone"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return cd;
	}

	/**
	 * �����޸���ϵ�casedefect
	 * 
	 * @author Dancy
	 * @param conn
	 * @param CaseDefect
	 * @param cid
	 * @return true:����ɹ���false:����ʧ��
	 * @throws Exception
	 */
	public boolean saveCaseDefect(Connection conn, CaseDefect cd, int cid)
			throws Exception {
		boolean result = false;
		String sql = "update caseanddefect set productID=?, componentID=?, sDate=?, eDate=?, urgentdefect=?, "
				+ "highdefect=?, normaldefect=?, lowdefect=?, milestoneid=?,cases=? where id="
				+ cid;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cd.getProductID());
			pstmt.setInt(2, cd.getComponentID());
			pstmt.setString(3, cd.getSDate());
			pstmt.setString(4, cd.getEDate());
			pstmt.setInt(5, cd.getUrgentdefect());
			pstmt.setInt(6, cd.getHighdefect());
			pstmt.setInt(7, cd.getNormaldefect());
			pstmt.setInt(8, cd.getLowdefect());
			pstmt.setInt(9, cd.getMilestoneid());
			pstmt.setInt(10, cd.getCases());
			int re = pstmt.executeUpdate();
			result = re > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != conn) {
				conn.close();

			}

		}
		return result;
	}

	/**
	 * ***********************Set Name List
	 * (Component)*************************************
	 */

	/**
	 * ���Component
	 * 
	 * @author Dancy
	 * @param conn
	 * @param ctname
	 * @param fwsw
	 * @return true:��ӳɹ���false:���ʧ��
	 * @throws Exception
	 */
	public boolean addComponentName(Connection conn, String ctname, int fwsw)
			throws Exception {
		boolean result = false;
		// System.out.println("productname="+productname+",fwsw="+fwsw);
		String sql = "insert into components( componentName, groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}

	/**
	 * �޸�omponent
	 * 
	 * @author Dancy
	 * @param conn
	 * @param componentname
	 * @param componentid
	 * @return true:�޸ĳɹ���false:�޸�ʧ��
	 * @throws Exception
	 */
	public boolean modifyComponentName(Connection conn, String componentname,
			int componentid) throws Exception {
		boolean result = false;
		String sql = "update components set componentName=? where componentid=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, componentname);
			pstmt.setInt(2, componentid);
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}

	/**
	 * �h��omponent
	 * 
	 * @author Dancy
	 * @param conn
	 * @param componentname
	 * @param componentid
	 * @return true:�h��ɹ���false:�h��ʧ��
	 * @throws Exception
	 */
	public boolean removeComponentName(Connection conn, int ctid)
			throws Exception {
		boolean result = false;
		String sql = "delete from components where componentid=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ctid);
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}

	/**
	 * *************************PO
	 * Assignment********************************************************
	 */

	/**
	 * @author dancy ��ѯ�õ����е�PO ��POID,PONumber,CostLocationID,poManager��ɵ�list
	 * @param stmt
	 * @return Polist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPOlist(Statement stmt) throws Exception {
		List<ProjectOrder> POlist = new ArrayList<ProjectOrder>();
		String sql = "select POID, PONumber"
				+ ", (select POManager from pomanager where pomanager.POManagerID=po.POManagerid) as 'poManager' from po";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProjectOrder po = new ProjectOrder();
				po.setPOID(rs.getInt("POID"));
				po.setPONumber(rs.getString("PONumber"));
				po.setPOManager(rs.getString("poManager"));
				POlist.add(po);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return POlist;

	}

	/**
	 * @author dancy ���product��component��ѯ�õ����Ӧ��PONumber list
	 * @param stmt��proid��comid
	 * @return numlist(��PONumber list)
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	public List<Map> getPONumberlist(Statement stmt, int proid, int comid)
			throws Exception {
		List<Map> list = new ArrayList<Map>();
		String sql = "select poid,(select PONumber from po where po.POID=pcp.poid) as 'num'"
				+ " from pcp where productid="
				+ proid
				+ " and componentid="
				+ comid;
		ResultSet rs = null;
		Map<String, String> map = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("poid", rs.getString("poid"));
				map.put("pno", rs.getString("num"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return list;

	}

	/**
	 * @author dancy ���Ӧ��product��component���һ��PONumber
	 * @param po
	 * @return 0=ʧ�ܣ�1=�ɹ���-1=��¼�Ѵ���
	 * @throws Exception
	 */
	public boolean addPONumber(Connection conn, int poid,int prid,int coid) throws Exception {
		boolean result = false;
		String sql0 ="select poid from pcp where poid="+poid+" and productid="+prid+" and componentid="+coid;
		String sql1 = "insert into pcp (poid,productid,componentid) values(?,?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql0);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, poid);
				pstmt.setInt(2, prid);
				pstmt.setInt(3, coid);
				int re = pstmt.executeUpdate();
				result = re > 0;
			}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (conn != null) {
					conn.close();
				}
			}

		return result;
	}

	/**
	 * ��ѯ��PONumber�Ƿ����
	 * 
	 * @author dancy
	 * @param conn
	 * @param (PONumber)pnum
	 * @return true:����,false:������
	 * @throws Exception
	 */
	public boolean isExistPCP(Connection conn, int pid, int proid, int comid)
			throws Exception {
		boolean isExist = false;
		String sql = "select count(poid) as count from pcp where poid = " + pid
				+ " and productid=" + proid + " and componentid=" + comid;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next() && rs.getInt("count") > 0) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		return isExist;
	}

	/**
	 * ɾ���Ӧ��product��component��PONumber��¼
	 * 
	 * @author dancy
	 * @param stmt
	 * @param proid
	 * @param comid
	 * @param pno
	 * @return true:ɾ��ɹ�,false:ɾ��ʧ��
	 * @throws Exception
	 */
	public boolean deletePONumberOfPCP(Statement stmt, int proid, int comid,
			int pno) throws Exception {
		boolean result = false;
		String sql = "delete from pcp where poid="
				+ pno
				+ " and productid="
				+ proid
				+ " and componentid="
				+ comid;
		try {
			int re = stmt.executeUpdate(sql);
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	/**
	 * ͨ��POID��ѯ�õ���Ӧ��product��component����Ϣ��list
	 * 
	 * @author dancy
	 * @param stmt
	 * @param poid
	 * @param lastRecordDate 
	 * @return pcplist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> searchPCPDetails(Statement stmt, int poid, String lastRecordDate)
			throws Exception {
		String sql = "select poid,(select PONumber from po where po.POID=pcp.poid) as 'pno', "
				+ "(select product from products where products.productid=pcp.productid) as 'product', "
				+ "(select componentName from components where components.componentid=pcp.componentid) as 'component' "
				+ "from pcp where poid=" + poid;
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("pno", rs.getString("pno"));
				map.put("product", rs.getString("product"));
				map.put("component", rs.getString("component"));
				map.put("last", lastRecordDate);
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return list;
	}

	/** ****************************PO LIST************************************ */
	/**
	 * ȡ�����е�po��������Ϣ��list
	 * 添加了对POStartDate，POEndDate，POused项的查询，FWJ on 2013-04-17
	 * @author dancy
	 * @param stmt
	 * @return plist
	 * @throws Exception
	 */
	public List<ProjectOrder> getPODetailsList(Statement stmt) throws Exception {
		List<ProjectOrder> plist = new ArrayList<ProjectOrder>();
		//修改了sql语句
		String sql = "select POID,PONumber,CostLocationID,POBalance,POAmount,POUsed,"
				+ "(select code from locationcodes where locationcodes.codeID=po.CostLocationID) as 'code',"
				+ "lockStatus,POStatus,POManagerid,POStartDate,POEndDate,"
				+ "(select POManager from pomanager where pomanager.POManagerID=po.POManagerid) as 'poManager' "
				+ "from po where po.poid!=-1 order by POID desc;";// ��ѯ���е�PO
		// String sql0 = "select POID,PONumber,CostLocationID,"
		// +"(select code from locationcodes where
		// locationcodes.codeID=po.CostLocationID) as 'code',"
		// +"lockStatus,POStatus,POManagerid,"
		// +"(select POManager from pomanager where
		// pomanager.POManagerID=po.POManagerid) as 'poManager' "
		// +"from po order by POID where
		// po_disabled='f';";//��ѯδ��ɾ���PO��po_disabled='f'��
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProjectOrder po = new ProjectOrder();
				po.setPOID(rs.getInt("POID"));
				po.setPONumber(rs.getString("PONumber"));
				po.setCostLocationID(rs.getInt("CostLocationID"));
				po.setLocationCode(rs.getString("code"));
				po.setLock(rs.getString("lockStatus"));
				po.setPOStatus(rs.getString("POStatus"));
				po.setPOManagerid(rs.getInt("POManagerid"));
				po.setPOManager(rs.getString("poManager"));
				po.setPoBalance(rs.getDouble("POBalance"));
				po.setPOAmount(rs.getDouble("POAmount"));
			//FWJ on 2013-04-17
				po.setPoUsed(rs.getDouble("POUsed"));
				po.setPOStartDate(rs.getString("POStartDate"));
				po.setPOEndDate(rs.getString("POEndDate"));
				plist.add(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
			if (null != stmt)
				stmt.close();
		}
		return plist;
	}

	/** ***************************PO ADD********************************* */
	/**
	 * ���һ���µ�PO��¼
	 * 
	 * @author dancy
	 * @param ProjectOrder
	 * @return true:��ӳɹ���false:���ʧ�ܡ�
	 * @throws Exception
	 */
	public int addPO(Connection conn, ProjectOrder po) throws Exception {
		boolean result = false;
		// ����¼�¼ǰ���ж��Ƿ��Ѵ��ڣ���PONumberΨһ
		result = this.isExistPO(conn, po.getPONumber());
		System.out.println("result:"+result);
		if (!result) {
			boolean result2 = false;
			String sql = "insert into po(PONumber,POAmount,POStartDate,POEndDate,"
					+ "POManagerid,Description,lockStatus,POStatus,NotStartStatusDate,"
					+ "OpenStatusDate,CloseStatusDate,AlertBalance,CloseBalance,Email,POUsed,POBalance,"
					+ "BYSQuatationNumber,BalanceUpdateDate,comment) values(?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,curdate(),?)";// ����ÿ������ӵ�BalanceUpdateDateΪ�����ü�¼�ĵ�������
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, po.getPONumber());
				pstmt.setDouble(2, po.getPOAmount());
				pstmt.setString(3, po.getPOStartDate());
				pstmt.setString(4, po.getPOEndDate());
//				pstmt.setInt(5, po.getBillCycleid());
				pstmt.setInt(5, po.getPOManagerid());
				pstmt.setString(6, po.getDescription());
				pstmt.setString(7, po.getLock());
				pstmt.setString(8, po.getPOStatus());
				pstmt.setString(9, po.getNotStartStatusDate());
				pstmt.setString(10, po.getOpenStatusDate());
				pstmt.setString(11, po.getCloseStatusDate());
				pstmt.setDouble(12, po.getAlertBalance());
				pstmt.setDouble(13, po.getCloseBalance());
				pstmt.setString(14, po.getEmail());
				pstmt.setDouble(15, po.getPoUsed());
				pstmt.setDouble(16, po.getPoBalance());
				pstmt.setString(17, po.getBYSQuatationNumber());
				pstmt.setString(18, po.getComment());

				int re = pstmt.executeUpdate();
				result2 = re > 0;
				System.out.println("result2:"+result2);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			// finally{
			// if(null!=conn){
			// conn.close();
			// }
			// if(null!=pstmt){
			// pstmt.close();
			// }
			// }
			if (result2)
				return 1;
			else
				return -1;
		}
		return -1;
	}

	/** *************************PO DELETE***************************** */
	/**
	 * ɾ��һ��PO��¼
	 * 
	 * @author dancy
	 * @param stmt
	 * @param poid
	 * @return true��ɾ��ɹ���false��ɾ��ʧ��
	 * @throws Exception
	 */
	public boolean deletePO(Statement stmt, int poid) throws Exception {
		boolean result = false;
		String sql = "delete from po where POID=" + poid;// ��ɾ
		String sql0 = "delete from pcp where poid=" + poid;// ��ɾ(��pcp�еĶ�Ӧ��¼Ҳɾ��)
		// String sql1 = "update po set po_disabled='t' where
		// POID="+poid;//��ɾ�����ÿ��ǽ�pcp�еĶ�Ӧ��¼Ҳɾ��
		try {
			int re = stmt.executeUpdate(sql);
			result = re > 0;
			int re0 = stmt.executeUpdate(sql0);
			result = re0 > 0;
			// int re1 = stmt.executeUpdate(sql1);
			// result = re1 > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
		return result;
	}

	/**
	 * ��ѯ���޸ĵ�PO��������Ϣ
	 * 
	 * @author dancy
	 * @param stmt
	 * @param poid
	 * @return po
	 * @throws Exception
	 */
	public ProjectOrder searchPOByID(Statement stmt, int poid) throws Exception {
		ProjectOrder po = null;
		String sql = "select PONumber,CostLocationID,POAmount,POStartDate,POEndDate,"
				+ "BillCycleid,POManagerid,Description,lockStatus,POStatus,NotStartStatusDate,"
				+ "OpenStatusDate,CloseStatusDate,AlertBalance,CloseBalance,Email,POUsed,POBalance,"
				+ "WBSNumber,BYSQuatationNumber,comment from po where POID="
				+ poid;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				po = new ProjectOrder();
				po.setPONumber(rs.getString("PONumber"));
				po.setCostLocationID(rs.getInt("CostLocationID"));
				po.setPOAmount(rs.getDouble("POAmount"));
				po.setPOStartDate(rs.getString("POStartDate"));
				po.setPOEndDate(rs.getString("POEndDate"));
				po.setBillCycleid(rs.getInt("BillCycleid"));
				po.setPOManagerid(rs.getInt("POManagerid"));
				po.setDescription(rs.getString("Description"));
				po.setLock(rs.getString("lockStatus"));
				po.setPOStatus(rs.getString("POStatus"));
				po.setNotStartStatusDate(rs.getString("NotStartStatusDate"));
				po.setOpenStatusDate(rs.getString("OpenStatusDate"));
				po.setCloseStatusDate(rs.getString("CloseStatusDate"));
				po.setAlertBalance(rs.getDouble("AlertBalance"));
				po.setCloseBalance(rs.getDouble("CloseBalance"));
				po.setEmail(rs.getString("Email"));
				po.setPoUsed(rs.getDouble("POUsed"));
				po.setPoBalance(rs.getDouble("POBalance"));
				po.setWBSNumber(rs.getString("WBSNumber"));
				po.setBYSQuatationNumber(rs.getString("BYSQuatationNumber"));
				po.setComment(rs.getString("comment"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// if(null!=stmt){
			// stmt.close();
			// }
			if (null != rs) {
				rs.close();
			}
		}
	return po;
}

	/**
	 * �����޸ĺ��PO��Ϣ
	 * 
	 * @author dancy
	 * @param conn
	 * @param ProjectOrder
	 * @param poid
	 * @return po
	 * @throws Exception
	 */
	public boolean savePO(Connection conn, ProjectOrder po, int poid)
			throws Exception {
		boolean result = false;
		String sql = "update po set POAmount=?,POStartDate=?,POEndDate=?,"
				+ "POManagerid=?,Description=?,lockStatus=?,POStatus=?,NotStartStatusDate=?,"
				+ "OpenStatusDate=?,CloseStatusDate=?,AlertBalance=?,CloseBalance=?,Email=?,POUsed=?,POBalance=?,"
				+ "BYSQuatationNumber=?,comment=? where POID="
				+ poid;// ����ÿ������ӵ�BalanceUpdateDateΪ�����ü�¼�ĵ�������
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, po.getPOAmount());
			pstmt.setString(2, po.getPOStartDate());
			pstmt.setString(3, po.getPOEndDate());
//			pstmt.setInt(4, po.getBillCycleid());
			pstmt.setInt(4, po.getPOManagerid());
			pstmt.setString(5, po.getDescription());
			pstmt.setString(6, po.getLock());
			pstmt.setString(7, po.getPOStatus());
			pstmt.setString(8, po.getNotStartStatusDate());
			pstmt.setString(9, po.getOpenStatusDate());
			pstmt.setString(10, po.getCloseStatusDate());
			pstmt.setDouble(11, po.getAlertBalance());
			pstmt.setDouble(12, po.getCloseBalance());
			pstmt.setString(13, po.getEmail());
			pstmt.setDouble(14, po.getPoUsed());
			pstmt.setDouble(15, po.getPoBalance());
//			pstmt.setString(17, po.getWBSNumber());
			pstmt.setString(16, po.getBYSQuatationNumber());
			pstmt.setString(17, po.getComment());

			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * ���һ���µ�status log��¼
	 * 
	 * @author dancy
	 * @param conn
	 * @param ProjectOrder
	 * @param poid
	 * @return true:��ӳɹ� false:���ʧ��
	 * @throws Exception
	 */
	public boolean addStatusLog(Connection conn, StatusChangeLog log)
			throws Exception {
		boolean result = false;
		String sql = "insert into postatuschangelog (PONumberid,StatusFrom,StatusTo,ChangeDateFrom,"
				+ "ChangeDateTo,TimeStamp,UserID) values (?,?,?,?,?, current_timestamp(),?)";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, log.getPoNum());
			pstmt.setString(2, log.getStatusFrom());
			pstmt.setString(3, log.getStatusTo());
			pstmt.setString(4, log.getChangeDateFrom());
			pstmt.setString(5, log.getChangeDateTo());
			pstmt.setInt(6, log.getChangeUserID());
			int re = pstmt.executeUpdate();
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != conn) {
				conn.close();
			}
		}
		return result;
	}

	/**
	 * ȡ��ĳPO��Ӧ������status log��Ϣ list
	 * 
	 * @author dancy
	 * @param conn
	 * @param poid
	 * @return loglist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List searchStatusLog(Connection conn, int pid) throws Exception {
		List<StatusChangeLog> loglist = new ArrayList<StatusChangeLog>();
		String sql = "select StatusFrom,StatusTo,ChangeDateFrom,ChangeDateTo,TimeStamp,"
				+ "(select username from user_table where postatuschangelog.UserId=user_table.user_id) as 'user'"
				+ " from postatuschangelog where PONumberid=" + pid
				+ " order by LogID desc;";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				StatusChangeLog log = new StatusChangeLog();
				log.setStatusFrom(rs.getString("StatusFrom"));
				log.setStatusTo(rs.getString("StatusTo"));
				log.setChangeDateFrom(rs.getString("ChangeDateFrom"));
				log.setChangeDateTo(rs.getString("ChangeDateTo"));
				log.setTimeStamp(rs.getString("TimeStamp"));
				log.setChangeUser(rs.getString("user"));
				loglist.add(log);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != conn) {
				conn.close();
			}
			if (null != rs) {
				rs.close();
			}
		}
		return loglist;
	}

	/**
	 * ʵ�ֶ�������POStatus,lockStatus,CostLocationID,POManagerid����ϲ�ѯ
	 * 
	 * @author dancy
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	public List<ProjectOrder> search(Statement stmt, StringBuffer sb)
			throws Exception {
		List<ProjectOrder> plist = new ArrayList<ProjectOrder>();

		String sql = "select POID,PONumber,"
				+ "lockStatus,POStatus,POManagerid,"
				+ "(select POManager from pomanager where pomanager.POManagerID=po.POManagerid) as 'poManager' "
				+ "from po " + sb;

		// System.out.println("show sql::"+sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProjectOrder po = new ProjectOrder();
				po.setPOID(rs.getInt("POID"));
				po.setPONumber(rs.getString("PONumber"));
				po.setLock(rs.getString("lockStatus"));
				po.setPOStatus(rs.getString("POStatus"));
				po.setPOManagerid(rs.getInt("POManagerid"));
				po.setPOManager(rs.getString("poManager"));
				
				plist.add(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return plist;
	}

	/**
	 * ��ѯ��ӦPO��ID(�������status log�е�PONumberid)
	 * 
	 * @param conn
	 * @param pno
	 * @return
	 * @throws Exception
	 */
	public int searchPOID(Connection conn, String pno) throws Exception {
		int pid = 0;
		String sql = "select POID from po where PONumber='" + pno + "'";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pid = rs.getInt("POID");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pid;
	}

	/**
	 * ��ѯ��PONumber�Ƿ����
	 * 
	 * @author dancy
	 * @param conn
	 * @param (PONumber)pnum
	 * @return true:����,false:������
	 * @throws Exception
	 */
	public boolean isExistPO(Connection conn, String pno) throws Exception {
		boolean isExist = false;
		String sql = "select count(PONumber) as count from po where PONumber ='"
				+ pno + "'";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next() && rs.getInt("count") > 0) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			// if (stmt != null) {
			// stmt.close();
			// }
		}

		return isExist;
	}

	/**
	 * ��ʼ��NonLaborCost
	 * 2012-01-12 activitypes-->worktype
	 * @author dancy
	 * @return nlist
	 * @throws Exception
	 * 2012-2-6 ��program Ϊprojectnames
	 */
	/**
	 *@author hanxiaoyu01
	 *2012-11-08
	 *ȥ����Project,ActiveType,SkillLevelName,TestAsset,Descriptionͬʱ����Comments��Quantity,Product,
	 *Component
	 */
	public List<NonLaborCost> getNonLaborCost(Statement stmt,SysUser u) throws Exception {
		String flag = "";
		if(u.getGroupID()!=-1)
		{
			flag = " where n.GroupID="+u.getGroupID();
		}
		if(u.getLevelID()>4)
		{
			flag = flag +" and n.creator='"+u.getUserName()+"'";
		}
		List<NonLaborCost> nlist = new ArrayList<NonLaborCost>();		
		String sql = "select *,(select groupName from groups g where g.groupId=n.GroupID) as 'group',"
			+ "(select PONumber from po where po.POID=n.poID) as 'pno',"
			+ "(select product from products p where p.productid=n.ProductId) as 'product',"
			+ "(select componentName  from components c where c.componentid=n.ComponentId) as 'comp',"
			+ "(select locationName from locations l where l.locationId=n.Locacleid) as 'loc'"
			+ " from nonlaborcosts n"+flag+" order by NonLaborCostID desc";
//		System.out.println("sql is: "+sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NonLaborCost nlc = new NonLaborCost();
				nlc.setNid(rs.getInt("NonLaborCostID"));
				nlc.setNdate(rs.getString("Date"));
				nlc.setNonLaborCost(rs.getDouble("NonlaborCosts"));
				nlc.setNotes(rs.getString("Notes"));
				nlc.setPurchaseOrderID(rs.getInt("poID"));
				nlc.setPurchaseOrder(rs.getString("pno"));
				nlc.setGroupNameID(rs.getInt("GroupID"));
				nlc.setGroupName(rs.getString("group"));
				nlc.setLocaleID(rs.getInt("Locacleid"));
				nlc.setLocale(rs.getString("loc"));
				nlc.setComments(rs.getString("Comments"));
				nlc.setQuantity(rs.getString("Quantity"));
				nlc.setProduct(rs.getString("product"));
				nlc.setProductId(rs.getInt("ProductId"));
				nlc.setComponent(rs.getString("comp"));
				nlc.setComponentId(rs.getInt("ComponentId"));
				nlc.setWBS(rs.getString("wbs"));
				nlc.setCreator(rs.getString("creator"));
				nlist.add(nlc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return nlist;
	}

	/**
	 * ��ʼ��PONumberList
	 * 
	 * @author dancy
	 * @return nlist
	 * @throws Exception
	 * @flag
	 * @ 2012-12-26 �޸�ȡ��po list�ķ�ʽ
	 */
	public List<ProjectOrder> loadPONumberList(Statement stmt,SysUser u) throws Exception {
		List<ProjectOrder> pnlist = new ArrayList<ProjectOrder>();
//		String sql = "select POID, PONumber from po order by PONumber;";
		String flag = "";
		if(u.getGroupID()!=-1)
		{
			flag = " where GroupID="+u.getGroupID();
		}
		if(u.getLevelID()>4)
		{
			flag = flag +" and creator='"+u.getUserName()+"'";
		}
		String sql = "select PurchaseOrder from nonlaborcosts"+flag+" group by PurchaseOrder";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ProjectOrder po = new ProjectOrder();
				po.setPONumber(rs.getString("PurchaseOrder"));
				pnlist.add(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pnlist;
	}

	public List<Groups> loadGroupList(Statement stmt,SysUser u) throws Exception {
		String flag = "";
		if(u.getGroupID()!=-1)
		{
			flag = " where groupId="+u.getGroupID();
		}
		List<Groups> gplist = new ArrayList<Groups>();
		String sql = "select groupId,groupName from groups"+flag+" order by groupId;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Groups gp = new Groups();
				gp.setGid(rs.getInt("groupId"));
				gp.setGname(rs.getString("groupName"));
				gplist.add(gp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return gplist;
	}

	public List<NonLaborCost> searchNonLaborCost(Statement stmt,
			StringBuffer str) throws Exception {
		List<NonLaborCost> nlist = new ArrayList<NonLaborCost>();
		String sql = "select n.*,(select groupName from groups g where g.groupId=n.groupID) as 'group',"
				+ "(select PONumber from po where po.POID=n.poID) as 'pno',"
				+ "(select product from products where products.productid=n.ProductId) as 'product',"
				+ "(select componentName from components c where c.componentid=n.ComponentId) as 'component',"
				+ "(select locationName from locations l where l.locationId=n.Locacleid) as 'loc'"
				+ " from nonlaborcosts n" + str;
		ResultSet rs = null;
//		System.out.println("sql="+sql);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NonLaborCost nlc = new NonLaborCost();
				nlc.setNid(rs.getInt("NonLaborCostID"));
				nlc.setNdate(rs.getString("Date"));
				nlc.setNonLaborCost(rs.getDouble("NonlaborCosts"));
				nlc.setNotes(rs.getString("Notes"));

				nlc.setPurchaseOrderID(rs.getInt("poID"));
				nlc.setPurchaseOrder(rs.getString("pno"));
				nlc.setGroupNameID(rs.getInt("GroupID"));
				nlc.setGroupName(rs.getString("group"));
				nlc.setLocaleID(rs.getInt("Locacleid"));
				nlc.setLocale(rs.getString("loc"));
				nlc.setProduct(rs.getString("product"));
				nlc.setProductId(rs.getInt("ProductId"));
				nlc.setComponent(rs.getString("component"));
				nlc.setComponentId(rs.getInt("ComponentId"));
				nlc.setComments(rs.getString("Comments"));
				nlc.setQuantity(rs.getString("Quantity"));
				nlc.setWBS(rs.getString("wbs"));
				nlc.setCreator(rs.getString("creator"));
				nlist.add(nlc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return nlist;
	}

	/**
	 * ɾ��NonLaborCost
	 * 
	 * @author dancy
	 * @return nlist
	 * @throws Exception
	 */
	public boolean deleteNonLaborCost(Statement stmt, int nid) throws Exception {
		boolean result = false;
		String sql = "delete from nonlaborcosts where NonLaborCostID=" + nid;// ��ɾ
		try {
			int re = stmt.executeUpdate(sql);
			result = re > 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
		return result;
	}
	/**
	 * �޸�nonlaborcost
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	
	/**
	 * @author hanxiaoyu01
	 * ȥ����Project,ActiveType,SkillLevelName,TestAsset,Descriptionͬʱ����Comments��Quantity,Product,
	 *Component
	 *2012-11-12
	 * 
	 * 
	 */
	@SuppressWarnings("deprecation")
	public boolean editNonLabor(Connection conn, NonLaborCost nonlaborcost) throws Exception
	{
		boolean result = false;
		String sql="update nonlaborcosts set GroupID=?, poid=?, "
			       +"Locacleid=?,NonlaborCosts=?,Notes=?,Date=?,"
			       +"ProductId=?,ComponentId=?,Quantity=?,Comments=? ,"
			       +"wbs=?,"
			       +"PurchaseOrder=( select PONumber from po where POID=? )"
			       +"where NonLaborCostID=?";
		
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nonlaborcost.getGroupNameID());
			pstmt.setInt(2, nonlaborcost.getPOID());
			pstmt.setInt(3, nonlaborcost.getLocaleID());
			pstmt.setDouble(4, nonlaborcost.getNonLaborCost());
			pstmt.setString(5, nonlaborcost.getNotes());
			pstmt.setDate(6, new Date(Date.parse(nonlaborcost.getNdate())));
			pstmt.setInt(7,nonlaborcost.getProductId());
			pstmt.setInt(8, nonlaborcost.getComponentId());
			pstmt.setString(9, nonlaborcost.getQuantity());
			pstmt.setString(10, nonlaborcost.getComments());
			pstmt.setString(11, nonlaborcost.getWBS());
			pstmt.setInt(12, nonlaborcost.getPOID());
			pstmt.setInt(13, nonlaborcost.getNid());
			int i = pstmt.executeUpdate();
			result = i>0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != pstmt)
				pstmt.close();
		}
		//System.out.println("add nonlaborcost result in DAO =" + result);
		return result;
	}
	/**
	 * �½�nonlaborcost
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public boolean saveNonLabor(Connection conn, NonLaborCost nonlaborcost) throws Exception
	{
		boolean result = false;
		String sql = "insert into nonlaborcosts " +
		"( GroupID,  poid,  Locacleid, " +
		"NonlaborCosts, Notes, Date," +
		"ProductId,ComponentId, Quantity,Comments,wbs,creator,PurchaseOrder,createTime) " +
		"values (?,?,?,?,?,?,?,?,?,?,?,?," +
		"(select PONumber from po where POID=?),curdate());";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nonlaborcost.getGroupNameID());
			pstmt.setInt(2, nonlaborcost.getPOID());
			pstmt.setInt(3, nonlaborcost.getLocaleID());
			pstmt.setDouble(4, nonlaborcost.getNonLaborCost());
			pstmt.setString(5, nonlaborcost.getNotes());
			pstmt.setDate(6, new Date(Date.parse(nonlaborcost.getNdate())));
			pstmt.setInt(7,nonlaborcost.getProductId());
			pstmt.setInt(8, nonlaborcost.getComponentId());
			pstmt.setString(9, nonlaborcost.getQuantity());
			pstmt.setString(10, nonlaborcost.getComments());
			pstmt.setString(11, nonlaborcost.getWBS());
			pstmt.setString(12, nonlaborcost.getCreator());
			pstmt.setInt(13, nonlaborcost.getPOID());
			int i = pstmt.executeUpdate();
			result = i>0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != pstmt)
				pstmt.close();
		}
		//System.out.println("add nonlaborcost result in DAO =" + result);
		return result;
	}
	/**
	 * ��ѯLocationcode
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> searchbyComponentAndProduct(Statement stmt,
			int componentid, String productname) throws Exception {
		List<Map> polist = new ArrayList<Map>();
		String sql = "select POId,PONumber from po where POID in "
				+ "( select poid from pcp where componentid = "
				+ componentid
				+ " "
				+ "and productid = ( select productid from products where product='"
				+ productname + "' ) );";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map map = new HashMap();
				map.put("poid", rs.getString("POId"));
				map.put("poname", rs.getString("PONumber"));
				polist.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return polist;
	}
	/**
	 * ��ѯLocationcode
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> searchLocationcode(Statement stmt) throws Exception {
		List<Map> Locationcodelist = new ArrayList<Map>();
		String sql = "select * from locationcodes;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map map = new HashMap();
				map.put("id", rs.getString("codeID"));
				map.put("data", rs.getString("code"));
				if (1 == rs.getInt("groupnum"))
					map.put("groupnum", "fw");
				else
					map.put("groupnum", "sw");
				Locationcodelist.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return Locationcodelist;
	}
	/**
	 * ͨ��nonlaborcostID����nonlaborcost
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	
	 /**@author hanxiaoyu01
	 *2012-11-08
	 *ȥ����Project,ActiveType,SkillLevelName,TestAsset,Descriptionͬʱ����Comments��Quantity,Product,
	 *Component
	 */
	public NonLaborCost searchNonLaborByID(Connection conn, int id) throws Exception
	{
		String sql = "select * from nonlaborcosts where NonLaborCostID=?";
		NonLaborCost nonlaborcost = new NonLaborCost();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				nonlaborcost.setNid(rs.getInt("NonLaborCostID"));
				nonlaborcost.setGroupNameID(rs.getInt("GroupID"));
				nonlaborcost.setNdate(rs.getString("Date"));
				nonlaborcost.setLocaleID(rs.getInt("Locacleid"));
				nonlaborcost.setNonLaborCost(rs.getDouble("NonlaborCosts"));
				nonlaborcost.setPOID(rs.getInt("poid"));
				nonlaborcost.setNotes(rs.getString("Notes"));
				nonlaborcost.setComments(rs.getString("Comments"));
				nonlaborcost.setQuantity(rs.getString("Quantity"));
				nonlaborcost.setProductId(rs.getInt("ProductId"));
				nonlaborcost.setComponentId(rs.getInt("ComponentId"));
				nonlaborcost.setWBS(rs.getString("wbs"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
			if (null != pstmt)
				pstmt.close();
		}
		return nonlaborcost;
	}
	/**
	 * ��ѯProgramlist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 * 2012-2-6 ��program Ϊprojectnames
	 */
	/*@SuppressWarnings("unchecked")
	public List<Map> loadProgramList(Statement stmt) throws Exception {
		List<Map> programlist = new ArrayList<Map>();
		String sql = "select * from projectnames order by projectname;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("id", rs.getString("projectnameid"));
				m.put("program", rs.getString("projectname"));
				if (1 == rs.getInt("groupnum"))
					m.put("groupnum", "fw");
				else
					m.put("groupnum", "sw");
				programlist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return programlist;
	}*/
	
	/**
	 * ��ѯtestassertlist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> loadTestAssetList(Statement stmt) throws Exception {
		List<Map> testassetlist = new ArrayList<Map>();
		String sql = "select * from testasset order by TestAsset;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("id", rs.getString("TestAssetID"));
				m.put("data", rs.getString("TestAsset"));
				if (1 == rs.getInt("groupnum"))
					m.put("groupnum", "fw");
				else
					m.put("groupnum", "sw");
				testassetlist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return testassetlist;
	}
	/**
	 * ��ѯactivitytypes 2012-1-12 modified to "worktype" by dancy
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List<Map> loadActivityTypesList(Statement stmt) throws Exception {
		List<Map> activitytypeslist = new ArrayList<Map>();
		String sql = "select * from worktype where groupnum=1 order by worktype;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("id", rs.getString("id"));
				m.put("worktype", rs.getString("worktype"));
				//m.put("groupnum", rs.getString("groupnum"));
				activitytypeslist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return activitytypeslist;
	}*/
	/**
	 * ��ѯskilllevelslist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List<Map> loadSkillLevelsList(Statement stmt) throws Exception {
		List<Map> skilllevelslist = new ArrayList<Map>();
		String sql = "select * from skilllevels order by skillLevelName;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("skillLevelId", rs.getString("skillLevelId"));
				m.put("skillLevelName", rs.getString("skillLevelName"));
				m.put("groupnum", rs.getString("groupnum"));
				skilllevelslist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return skilllevelslist;
	}*/
	/**
	 * ��ѯdescriptionslist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List<Map> loadDescriptionsList(Statement stmt) throws Exception {
		List<Map> descriptionslist = new ArrayList<Map>();
		String sql = "select * from descriptions order by description;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("descriptionid", rs.getString("descriptionid"));
				m.put("description", rs.getString("description"));
				m.put("groupnum", rs.getString("groupnum"));
				descriptionslist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return descriptionslist;
	}*/
	/**
	 * ��ѯlocationslist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> loadLocationsList(Statement stmt) throws Exception {
		List<Map> locationslist = new ArrayList<Map>();
		//添加了hide=0 FWJ 2013-05-09
		String sql = "select * from locations where hide=0 order by locationName;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("locationId", rs.getString("locationId"));
				m.put("locationName", rs.getString("locationName"));
				m.put("groupnum", rs.getString("groupnum"));
				locationslist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return locationslist;
	}
	/**
	 * ��ѯlocationslist
	 * 
	 * @author longzhe
	 * @param conn
	 * @param sb
	 * @return plist
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public List<Map> loadOTTypeList(Statement stmt) throws Exception {
		List<Map> ottypelist = new ArrayList<Map>();
		String sql = "select * from ottype order by OTTypeName;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map m = new HashMap();
				m.put("OTTypeId", rs.getString("OTTypeId"));
				m.put("OTTypeName", rs.getString("OTTypeName"));
				m.put("groupnum", rs.getString("groupnum"));
				ottypelist.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != rs)
				rs.close();
		}
		return ottypelist;
	}*/

/**
 * test override function ����name list
 * @param stmt
 * @param sql
 * @param id
 * @param data
 * @return
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public List<Map> searchDataList(Statement stmt, String sql, String id, String data) throws Exception {
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("id", rs.getString(id));
				map.put("data", rs.getString(data));
				//添加了对hide字段值的获取 FWJ on 2013-05-02
				map.put("hide", rs.getString("hide"));
				if("product".equals(data))
				{
					map.put("productyear", rs.getString("pyear"));
					map.put("bss", rs.getString("bss"));
					map.put("pform", rs.getString("pform"));
					map.put("short", rs.getString("FWQEOwner"));
				}
				//hanxiaoyu 2012-12-17ȥ�� groupnum
				/*if (1 == rs.getInt("groupnum"))
					map.put("groupnum", "fw");
				else
					map.put("groupnum", "sw");*/
				list.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return list;
	}
/**
 * �޸�
 * @param conn
 * @param name
 * @param id
 * @param sql
 * @return
 * @throws Exception
 */
public boolean modifyData(Connection conn, String name, int id, String sql) throws Exception {
	boolean result = false;
	PreparedStatement pstmt = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, id);
		int re = pstmt.executeUpdate();
		result = re > 0;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if (null != pstmt)
			pstmt.close();
		if (conn != null) {
			conn.close();
		}
	}
	return result;
}
/**
 * ɾ��
 * @param conn
 * @param id
 * @param sql
 * @return
 * @throws Exception
 */
public boolean deleteData(Connection conn, int id, String sql)throws Exception {
	boolean result = false;
	PreparedStatement pstmt = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		int re = pstmt.executeUpdate();
		result = re > 0;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if (null != pstmt)
			pstmt.close();
		if (conn != null) {
			conn.close();
		}
	}
	return result;
}

/**
 * set name list ���
 * @param conn
 * @param name
 * @param sql0
 * @param sql1
 * @return
 * @throws Exception
 */
public boolean addData(Connection conn, String name, String sql0, String sql1)throws Exception {
	boolean result = false;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		pstmt = conn.prepareStatement(sql0);
		pstmt.setString(1, name);
		rs = pstmt.executeQuery();
		if (!rs.next()) {
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, name);
			int re = pstmt.executeUpdate();
			result = re > 0;
		}

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != pstmt)
			pstmt.close();
		if (conn != null) {
			conn.close();
		}
	}
	return result;
}
/**
 * updateBalance����--ȡ��ÿ��po��Ӧ������project
 * @param stmt
 * @param sql0
 * @return
 * @throws Exception
 */
public List<Project> getProjectsByPO(Statement stmt, String sql0) throws Exception {
	List<Project> projectlist = new ArrayList<Project>();
	ResultSet rs = null;
	try{
		rs = stmt.executeQuery(sql0);
		while(rs.next())
		{
			Project pt = new Project();
			pt.setProjectID(rs.getInt("projectId"));
			projectlist.add(pt);
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		throw e;
	}finally
	{
		if (rs != null) {
			rs.close();
		}
	}
	
	return projectlist;
}
/**
 * ȡ��ÿ��po��ص���ݣ�NonLaborCost,ottype Rate,skillLevel��location��Ӧ��Rate,ÿ��project���ܹ�ʱ��
 * @param stmt
 * @param sql
 * @param str
 * @return
 * @throws Exception
 */
public Project getProjectsReference(Statement stmt, String sql,String str) throws Exception {
	Project pt = null;
	ResultSet rs = null;
	try{		
		rs = stmt.executeQuery(sql);
		pt = new Project();
		if(rs.next())
		{			
			if("nonLabor".equals(str))
			{
				pt.setSumNonLaborCost(rs.getDouble("nonLabor"));
			}
			if("OTRate".equals(str))
			{
				pt.setOTRate(rs.getFloat("OTRate"));
			}
			if("ptRate".equals(str))
			{
				pt.setRateP(rs.getFloat("Rate"));
			}
			if("sum".equals(str))
			{
				pt.setSumHours(rs.getFloat("sum"));
			}
			
		}
		else
		{
			if("nonLabor".equals(str))
			{
				pt.setSumNonLaborCost(0.0);
			}
			if("OTRate".equals(str))
			{
				pt.setOTRate(1);
			}
			if("ptRate".equals(str))
			{
				pt.setRateP(1);
			}
			if("sum".equals(str))
			{
				pt.setSumHours(0);
			}
			
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		throw e;
	}finally
	{
		if (rs != null) {
			rs.close();
		}
	}
	
	return pt;
}

public List<NonLaborCost> searchNonLaborByPOID(Statement stmt, int poid) throws Exception {
	 List<NonLaborCost> list = new ArrayList<NonLaborCost>();
	 String sql = "select Date from nonlaborcosts where poid="+poid+" order by Date;";
	 ResultSet rs = null;
	 try
	 {
		 rs = stmt.executeQuery(sql);
		 while(rs.next())
		 {
			 NonLaborCost nlc = new NonLaborCost();
			 nlc.setNdate(rs.getString("Date"));
			 list.add(nlc);
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
	 }
	return list;
}

/**
 * @author hanxiaoyu01
 * 2012-11-08
 * ����productlist
 * 
 */
@SuppressWarnings("unchecked")
public List<Map> loadProductList(Statement stmt) throws Exception{
	List<Map> productlist=new ArrayList<Map>();
	//添加了hide=0 FWJ 2013-05-09
	String sql="select * from products where hide =0 order by product";
	ResultSet rs=null;
	try{
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			Map m=new HashMap();
			m.put("productid", rs.getInt("productid"));
			m.put("product", rs.getString("product"));
			productlist.add(m);
		}
	}catch(Exception e){
		e.printStackTrace();
		 throw e;
	}finally{
		if(rs!=null){
			rs.close();
		}
	}
	return productlist;
}

@SuppressWarnings("unchecked")
public List<Map> loadComponentList(Statement stmt) throws Exception{
	List<Map>  componentlist=new ArrayList<Map>();
	//添加了hide=0 FWJ 2013-05-09
	String sql="select * from components where hide=0 order by componentName";
	ResultSet rs=null;
	try{
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			Map m=new HashMap();
			m.put("componentId", rs.getInt("componentid"));
			m.put("component", rs.getString("componentName"));
			componentlist.add(m);
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}finally{
		if(rs!=null){
			rs.close();
		}
	}
	
	return componentlist;
}
public boolean savePOBalance(Connection conn, String poid, String poused, String amount) throws Exception
{
	System.out.println("pobalance="+amount);
	String sql = "update po set POUsed="+poused+",POBalance="+amount+"-"+poused+" where POID="+poid;
	PreparedStatement  pstmt = null;
	boolean result = false;
	try
	{
		pstmt = conn.prepareStatement(sql);
		int re = pstmt.executeUpdate();
		result = re>0;
	}catch(Exception e)
	{
		e.printStackTrace();
		throw e;
	}
	finally
	{
		if(pstmt!=null)
		{
			pstmt.close();
		}
	}
	return result;
	
}
/**
 * ����Non-Labor Cost���棬ȡ��list
 * 
 * @param conn
 * @param startDate
 * @param endDate
 * @param gid
 * @return
 * @throws Exception
 * @ change gi to groupNames by dancy 2013-03-08
 */
public List<NonLaborCost> searchNonLaborReport(Connection conn,String startDate, String endDate, String groupNames, SysUser u)
throws Exception {
	List<NonLaborCost> list = new ArrayList<NonLaborCost>();
	ResultSet rs = null;
	PreparedStatement pstmt = null;// by collie 0503
	String flag = "";
	if(groupNames.indexOf(",")>0)
	{
		groupNames = groupNames.substring(0,groupNames.length()-1);
		flag = " and g.groupName in ("+groupNames+")";			
	}
	if(u.getLevelID()==4)
	{
		flag = " and g.groupName='"+groupNames+"'";	
	}
	String sql = "select n.*,g.groupName,l.locationName,p.product,c.componentName from nonlaborcosts n "
				+"left join groups g on g.groupId=n.GroupID "
				+"left join locations l on l.locationId=n.Locacleid "
				+"left join products p on p.productid=n.ProductId "
				+"left join components c on c.componentid=n.ComponentId "
				+"where n.Date>='"+startDate+"' and n.Date<='"+endDate+"'"
				+flag
				+" order by Date,PurchaseOrder,creator";
//	System.out.println("report sql is: "+sql);
	try {
		pstmt = conn.prepareStatement(sql);	
		rs = pstmt.executeQuery();
		while (rs.next()) 
		{
			NonLaborCost n = new NonLaborCost();
			n.setPonumber(rs.getString("PurchaseOrder"));
			n.setProduct(rs.getString("product"));
			n.setComponent(rs.getString("componentName"));
			n.setWBS(rs.getString("wbs"));
			n.setLocale(rs.getString("locationName"));
			n.setGroupName(rs.getString("groupName"));
			n.setNonLaborCost(rs.getDouble("NonlaborCosts"));
			n.setNotes(rs.getString("Notes"));
			n.setComments(rs.getString("Comments"));
			n.setQuantity(rs.getString("Quantity"));
			n.setCreator(rs.getString("creator"));
			n.setNdate(rs.getString("Date"));
			list.add(n);
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
	return list;
}
/**
 * 
 * @param conn
 * @param startDate
 * @param endDate
 * @param groupNames
 * @return
 * @throws Exception
 * change gid to groupNames by dancy 2013-03-08
 */
public List<CaseDefect> searchCaseReport(Connection conn, String startDate,String endDate, String groupNames,SysUser u) 
throws Exception{
	String flag = "";
	if(groupNames.indexOf(",")>0)
	{
		groupNames = groupNames.substring(0,groupNames.length()-1);
		flag = " and g.groupName in ("+groupNames+")";			
	}
	if(u.getLevelID()==4)
	{
		flag = " and g.groupName='"+groupNames+"'";	
	}
	System.out.println("here groups 3 is: "+ groupNames);
	List<CaseDefect> CaseList = new ArrayList<CaseDefect>();
	String sql = "select c.*,p.product,s.componentName,m.milestone,u.workloadgroupId,g.groupName from caseanddefect c "
			+ "left join products p on p.productid = c.productID "
			+ "left join components s on s.componentid = c.componentID " 
			+ "left join milestones m on m.milestoneid = c.milestoneid "
			+ "left join user_table u on u.username = c.creator "
			+ "left join groups g on g.groupId = u.workloadgroupId " 
			+ "where c.sDate>='"+startDate+"' and c.eDate<='"+endDate+"'"+flag+" order by id desc";
	System.out.println("sql = "+sql);
	ResultSet rs = null;
	Statement stmt = conn.createStatement();
	try {
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			CaseDefect cd = new CaseDefect();
			cd.setId(rs.getInt("id"));
			cd.setProduct(rs.getString("product"));
			cd.setComponentName(rs.getString("componentName"));
			cd.setSDate(rs.getString("sDate"));
			cd.setEDate(rs.getString("eDate"));
			cd.setCases(rs.getInt("cases"));
			cd.setUrgentdefect(rs.getInt("urgentdefect"));
			cd.setHighdefect(rs.getInt("highdefect"));
			cd.setNormaldefect(rs.getInt("normaldefect"));
			cd.setLowdefect(rs.getInt("lowdefect"));
			cd.setMilestone(rs.getString("milestone"));
			cd.setCreator(rs.getString("creator"));
			cd.setCreateTime(rs.getString("createTime"));
			CaseList.add(cd);
		}

	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if (null != rs)
			rs.close();
		if (null != stmt)
			stmt.close();
	}
	return CaseList;
}


/**
 * @author hanxiaoyu01
 * 2013-01-04
 * �鿴����PO��project�д治����
 */
  public int checkPO(Connection conn,int poid) throws Exception{
	  String sql="select * from projects where PONumberid=?";
	  ResultSet rs=null;
	  PreparedStatement pstmt=null;
	  int count=0;
	  try{
		  pstmt=conn.prepareStatement(sql);
		  pstmt.setInt(1, poid);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
			  count++;
		  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }finally{
		  if (rs != null) 
		  {
			  rs.close();
		  }
		  if (pstmt != null) 
		  {
			  pstmt.close();
		  }
	  }
	  return count;
  }
 
  /**
   * @author fengwenjing
   * 2013-04-27
   * 查询是否checkpo在monthlyexpense中被使用
   */
    public int checkPOInMonthlyExpense(Connection conn,int poid) throws Exception{
  	  String sql="select * from monthlyexpense where PONumberid=?";
  	  ResultSet rs=null;
  	  PreparedStatement pstmt=null;
  	  int count=0;
  	  try{
  		  pstmt=conn.prepareStatement(sql);
  		  pstmt.setInt(1, poid);
  		  rs=pstmt.executeQuery();
  		  while(rs.next()){
  			  count++;
  		  }
  	  }catch(Exception e){
  		  e.printStackTrace();
  	  }finally{
  		if (rs != null) 
		  {
			  rs.close();
		  }
		  if (pstmt != null) 
		  {
			  pstmt.close();
		  }
  	  }
  	  return count;
    }
    
    /**
     * @author fengwenjing
     * 2013-04-27
     * 查询是否po已经被创建中被使用
     */
      public int checkPOadd(Connection conn,String pon) throws Exception{
    	  String sql="select * from po where PONumber=?";
    	  ResultSet rs=null;
    	  PreparedStatement pstmt=null;
    	  int count=0;
    	  try{
    		  pstmt=conn.prepareStatement(sql);
    		  pstmt.setString(1, pon);
    		  rs=pstmt.executeQuery();
    		  while(rs.next()){
    			  count++;
    		  }
    	  }catch(Exception e){
    		  e.printStackTrace();
    	  }finally{
    		  if (rs != null) 
    		  {
    			  rs.close();
    		  }
    		  if (pstmt != null) 
    		  {
    			  pstmt.close();
    		  }
    	  }
    	  return count;
      }
  /**
   * @author hanxiaoyu01
   * @param conn
   * @param poid
   * @return
   * @throws Exception
   * 2013-01-06��PO��removeһ��POʱ�ж����PO�Ƿ���Component��Productʹ�ù�
   */
  public int checkPO2(Connection conn,int poid,String product,String component) throws Exception{
	  String sql="select * from projects where PONumberid=? and product=?  and componentName=?";
	  ResultSet rs=null;
	  PreparedStatement pstmt=null;
	  int count=0;
	  try{
		  pstmt=conn.prepareStatement(sql);
		  pstmt.setInt(1, poid);
		  pstmt.setString(2, product);
		  pstmt.setString(3, component);
		  rs=pstmt.executeQuery();
		  while(rs.next()){
			  count++;
		  }
	  }catch(Exception e){
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
	  return count;
  }
  
 /**
  * 
  * @param conn
  * @param startDate
  * @param endDate
  * @param gid
  * @return
  * @throws Exception
  */
  public List<Project> searchPOCostByProject(Connection conn,String startDate, String endDate, int gid)
  throws Exception {
  	List<Project> list = new ArrayList<Project>();
  	ResultSet rs = null;
  	PreparedStatement pstmt = null;// by collie 0503
  	String flag = "";
  	if(gid!=-1)
  	{
  		flag = " and p.groupId="+gid+" and nlc.GroupID="+gid;
  	}	
  	String sql = "select p.PONumberid ,po.PONumber,p.componentName,p.product,p.skillLevel,p.OTType,ed.sum1," 
  				+"pp.productid,"
  				+"p.componentid,"
  				+"groups.groupname ,nlc.sum2 from projects p "
  				+"left join products pp on pp.product=p.product "
  				+"left join (select e.projectId,sum(e.hours) as 'sum1' from expensedata e where e.createTime>='"
  				+startDate+"' and e.createTime<='"
  				+endDate+"' group by projectId) as ed on p.projectId=ed.projectId  "
  				+"left join (select n.poid,n.ComponentId,n.ProductId,sum(n.NonLaborCosts) as 'sum2' from nonlaborcosts n where n.Date>='"
  				+startDate+"' and n.Date<='"+endDate
  				+"' group by n.poid,n.ComponentId,n.ProductId ) as nlc on nlc.poid = p.PONumberid "
  				+"and nlc.ComponentId=p.componentid "
  				+"and nlc.ProductId=pp.productid "
  				+"left join po on po.POID=p.PONumberid "
  				+"left join groups on p.groupId=groups.groupId "
  				+"where ed.sum1>0 or nlc.sum2>0 "
  				+flag
  				+" order by po.PONumber,p.componentName,p.product";
//  	System.out.println("report sql is: "+sql);
  	try {
  		pstmt = conn.prepareStatement(sql);	
  		rs = pstmt.executeQuery();
  		while (rs.next()) 
  		{
  			Project p = new Project();
  			p.setPOName(rs.getString("PONumber"));
  			p.setComponent(rs.getString("componentName"));
  			p.setProduct(rs.getString("product"));
  			p.setSkillLevel(rs.getString("skillLevel"));
  			p.setOTType(rs.getString("OTType"));
  			p.setSumHours(rs.getFloat("sum1"));
  			p.setSumNonLaborCost(rs.getDouble("sum2"));  			
  			list.add(p);
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
  	return list;
  }
  
  /**
   * @author hanxiaoyu01
   * 2013-02-16
   * ɾ��Case and Defect��Ĭ��ֵ
   * @param conn
   * @param userId
   * @throws Exception
   */
  public void deleteDefaultCaseAndDefect(Connection conn,int userId) throws Exception{
	  String sql="delete from default_casedefect where userId=?";
	  PreparedStatement pstmt=null;
	  try{
		  pstmt=conn.prepareStatement(sql);
		  pstmt.setInt(1, userId);
		  pstmt.executeUpdate();
	  }catch(Exception e){
		  e.printStackTrace();
	  }finally{
		  if(pstmt!=null){
			  pstmt.close();
		  }
	  }
  }
  
  /**
   * @author hanxiaoyu01
   * 2013-02-16
   * ��Case and Defect��Ĭ��ֵ
   */
  public void addDefaultCaseAndDefect(Connection conn,int userId,DefaultCaseDefect dcd) throws Exception {
	  String sql="insert into default_casedefect values (?,?,?,?)";
	  PreparedStatement pstmt=null;
	  try{
		  pstmt=conn.prepareStatement(sql);
		  pstmt.setInt(1, userId);
		  pstmt.setInt(2, dcd.getProductId());
		  pstmt.setInt(3, dcd.getComponentId());
		  pstmt.setInt(4, dcd.getMilestoneId());
		  pstmt.executeUpdate();
	  }catch(Exception e){
		  e.printStackTrace();
	  }finally{
		  if(pstmt!=null){
			  pstmt.close();
		  }
	  }
  }

/**
 *  
 * @param conn
 * @param poid
 * @return
 * @throws Exception
 */
  public boolean searchbalance(Connection conn, int poid) throws Exception 
  {
  	boolean result = false;
  	String sql = "select (pobalance-alertbalance) as result from po where poid="+poid;
  	System.out.println(sql);
  	double i = 0;
  	PreparedStatement pstmt = null;
  	ResultSet rs = null;
  	try
  	{
  		pstmt = conn.prepareStatement(sql);
  		rs = pstmt.executeQuery(sql);
  		if(rs.next())
  		{
  			i=rs.getDouble("result");
  		}
  		System.out.println("-----------i in the searchbalance="+i);
  		result =(i<0);
  		System.out.println("poamount<alertbalance:"+result);
  	}catch(Exception e)
  	{
  		e.printStackTrace();
  		throw e;
  	}finally 
  	{
  		if (rs!=null) 
  		{
  			rs.close();
  		}
  		if (null!=pstmt) 
  		{
  			pstmt.close();
  		}
  	}
  	return result;	
  }
  
/**
 * 
 * @param conn
 * @param poid
 * @return
 * @throws Exception
 */
  public ProjectOrder searchPOBalance(Connection conn,int poid)throws Exception 
  {
  	ProjectOrder p = new ProjectOrder();
  	ResultSet rs = null;
  	PreparedStatement pstmt = null;// by collie 0503
  	String sql = "select po.poid, po.PONumber,po.poamount, po.POUsed, po.POBalance, po.AlertBalance, po.lockStatus,po.POStatus, " +
  			"po.POStartDate, po.POEndDate, po.Description, pm.POManager from pomanager pm, po " +
  			"where po.POManagerid = pm.POManagerID and po.poid="+poid;
  	System.out.println("the sql in the searchPOBalance is: "+sql);
  	try 
  	{
  		pstmt = conn.prepareStatement(sql);	
  		rs = pstmt.executeQuery();
  		if (rs.next()) 
  		{
  			p.setPOID(poid);
  			p.setPOAmount(rs.getDouble("POamount"));
  			p.setPONumber(rs.getString("PONumber"));
  			p.setPoUsed(rs.getDouble("POUsed"));
  			p.setPoBalance(rs.getDouble("POBalance"));
  			p.setAlertBalance(rs.getDouble("AlertBalance"));
  			p.setLock(rs.getString("lockStatus"));
  			p.setPOStatus(rs.getString("POStatus"));
  			p.setPOStartDate(rs.getString("POStartDate"));
  			p.setPOEndDate(rs.getString("POEndDate"));
  			if(rs.getString("po.Description")!=null)
  			{
  				p.setDescription(rs.getString("po.Description"));
  			}
  			else
  			{
  				p.setDescription("");
  			}
  			p.setPOManager(rs.getString("pomanager"));
  		}
  	} catch (Exception e) 
  	{
  		e.printStackTrace();
  		throw e;
  	} finally 
  	{
  		if(rs!=null)
  		{
  			rs.close();
  		}
  		if (pstmt != null) 
  		{
  			pstmt.close();
  		}
  	}
  	return p;
  }
  
}