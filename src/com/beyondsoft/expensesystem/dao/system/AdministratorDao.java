package com.beyondsoft.expensesystem.dao.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tools.bean.PageModel;
import tools.util.HQLQuery;

import com.beyondsoft.expensesystem.domain.checker.DefaultCaseDefect;
import com.beyondsoft.expensesystem.domain.checker.ExpenseDetail;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.DescriptionOfSkillLevel;

@SuppressWarnings("unchecked")
public class AdministratorDao
{
	private static AdministratorDao dao = null;

	public static AdministratorDao getInstance()
	{
		if (dao == null)
		{
			dao = new AdministratorDao();
		}
		return dao;
	}

	/**
	 * ��ѯgroups
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchGroups(Statement stmt, SysUser user)
			throws Exception
	{
		{
			String sql = "SELECT groups.groupName,groups.groupId from groups where locate((select groups.groupName from groups,user_table where user_table.workloadgroupId=groups.groupId and user_table.user_id="+user.getUserId()+"),groups.groupName)=1";
			List<Map> list = new ArrayList<Map>();
			ResultSet rs = null;
			Map<String, String> map = null;
			try
			{
				rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					map = new HashMap<String, String>();
					map.put("groupId", rs.getString("groupId"));
					map.put("groupName", rs.getString("groupName"));					
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
			}
			return list;
		}
	}

	
	/**
	 * ��ѯPermissionLevels
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchPermissionLevels(Connection conn, PageModel model)
			throws Exception
	{
		String sql = "select l.levelId,l.levelName,l.remove from permissionlevels l"
				+ " where l.levelId <> -1 order by l.levelId";
		List list = null;
		HQLQuery _PageFind = null;
		try
		{
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return list;
	}
/**
 * ȡ���û���ɫ�б�
 * @param stmt
 * @return
 * @throws Exception
 * @flag
 */
	
	public List searchPermissionLevels(Statement stmt) throws Exception
	{
		String sql = "select l.levelId,l.levelName,l.remove from permissionlevels l"
				+ " where l.levelId <> -1 order by l.levelId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("levelId", rs.getString("levelId"));
				map.put("levelName", rs.getString("levelName"));
				map.put("remove", rs.getString("remove"));
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
		}
		return list;
	}

	/**
	 * ��ѯPermissionNames
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchPermissionNames(Connection conn, PageModel model)
			throws Exception
	{
		String sql = "select p.permissionId,p.permissionName,p.remove from permissionnames p"
				+ " where p.permissionId <> -1 order by p.permissionId";
		List list = null;
		HQLQuery _PageFind = null;
		try
		{
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return list;
	}

	public List searchPermissionNames(Statement stmt) throws Exception
	{
		String sql = "select p.permissionId,p.permissionName,p.remove from permissionnames p"
				+ " where p.permissionId <> -1 order by p.permissionId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("permissionId", rs.getString("permissionId"));
				map.put("permissionName", rs.getString("permissionName"));
				map.put("remove", rs.getString("remove"));
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
		}
		return list;
	}

	/**
	 * ��ѯSkillLevels
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchSkillLevels(Connection conn, PageModel model)
			throws Exception
	{
		String sql = "select s.skillLevelId,s.skillLevelName from skilllevels s"
				+ " where s.skillLevelId <> -1 order by s.skillLevelId";
		List list = null;
		HQLQuery _PageFind = null;
		try
		{
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return list;
	}
	/**author=longzhe ����ݿ��ҵ����е�skilllevel ������ʱѡ��
	 * 
	 * @param stmt
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public List searchSkillLevels(Statement stmt) throws Exception
	{
		String sql = "select s.skillLevelId,s.skillLevelName, s.groupnum "
					+" from skilllevels s order by s.groupnum, s.skillLevelName";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("skillLevelId", rs.getString("skillLevelId"));
				map.put("skillLevelName", rs.getString("skillLevelName"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	public List searchSkillLevels_fw(Statement stmt) throws Exception
	{
		String sql = "select s.skillLevelId,s.skillLevelName from skilllevels s"
			+ " where s.skillLevelId <> -1 and s.groupnum=1 order by s.skillLevelId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("skillLevelId", rs.getString("skillLevelId"));
				map.put("skillLevelName", rs.getString("skillLevelName"));
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
		}
		return list;
	}
	public List searchSkillLevels_sw(Statement stmt) throws Exception
	{
		String sql = "select s.skillLevelId,s.skillLevelName,s.skilllevelshortname from skilllevels s"
				+ " where s.skillLevelId <> -1 and s.groupnum=2 order by s.skillLevelId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("skillLevelId", rs.getString("skillLevelId"));
				map.put("skillLevelName", rs.getString("skillLevelName"));
				map.put("shortname", rs.getString("skilllevelshortname"));
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
		}
		return list;
	}

	/**
	 * ��ѯLocations
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchLocations(Connection conn, PageModel model)
			throws Exception
	{
		String sql = "select l.locationId,l.locationName from locations l"
				+ " where l.locationId <> -1 order by l.locationId";
		List list = null;
		HQLQuery _PageFind = null;
		try
		{
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return list;
	}
	//author=longzhe ����ݿ��ҵ����е�location ������ʱѡ��
	public List searchLocations(Statement stmt) throws Exception
	{
		String sql = "select l.locationId,l.locationName, l.groupnum from locations l "
					+" order by l.groupnum, l.locationId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("locationId", rs.getString("locationId"));
				map.put("locationName", rs.getString("locationName"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	public List searchLocations_fw(Statement stmt) throws Exception
	{
		//FWJ 2013-05-09 添加了hide=0
		String sql = "select l.locationId,l.locationName from locations l"
				+ " where l.locationId <> -1 and l.groupnum=1 and hide=0 order by l.locationId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("locationId", rs.getString("locationId"));
				map.put("locationName", rs.getString("locationName"));
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
		}
		return list;
	}
	public List searchLocations_sw(Statement stmt) throws Exception
	{
		String sql = "select l.locationId,l.locationName from locations l"
				+ " where l.locationId <> -1 and l.groupnum=2 order by l.locationId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("locationId", rs.getString("locationId"));
				map.put("locationName", rs.getString("locationName"));
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
		}
		return list;
	}

	/**
	 * ��ѯOTTypes
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchOTTypes(Connection conn, PageModel model)
			throws Exception
	{
		String sql = "select o.OTTypeId,o.OTTypeName from ottype o "
				+ " where o.OTTypeId <> -1 order by o.OTTypeId";
		List list = null;
		HQLQuery _PageFind = null;
		try
		{
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return list;
	}
	
	//author=longzhe ����ݿ��ҵ����е�projectName ������ʱѡ��
	public List searchProjectNames(Statement stmt) throws Exception
	{
		String sql = "select p.projectnameid, p.projectname,p.groupnum from projectnames p order by p.groupnum,p.projectname;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = null;
		//list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("projectnameid", rs.getString("projectnameid"));
				map.put("projectname",rs.getString("projectname"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	/**
	 * new project��ʱ���ѯcomponent�б�
	 * 
	 * @param stmt
	 * @return List
	 * @throws Exception
	 */
	public List searchcomponentNames_fw(Statement stmt) throws Exception
	{
		//FWJ 2013-05-09 添加了hide=0
		String sql = "select componentid, componentName from components where groupnum=1 and hide=0 order by componentName;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = new HashMap<String, String>();
		map.put("componentName","");
		list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("componentid",rs.getString("componentid"));
				map.put("componentName",rs.getString("componentName"));
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
		}
		return list;
	}
	/**
	 * new project��ʱ���ѯPO�б�
	 * 
	 * @param stmt
	 * @return List
	 * @throws Exception
	 */
	public List searchPO_fw(Statement stmt) throws Exception
	{
		String sql = "select POID , PONumber from po order by PONumber;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = new HashMap<String, String>();
		map.put("PONumber","PONumber");
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("POID", rs.getString("POID"));
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
		}
		return list;
	}
	
	public List searchProjectNames_sw(Statement stmt) throws Exception
	{
		String sql = "select p.projectname from projectnames p where p.groupnum=2 order by p.projectname; ";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = null;
		//����new project
		map = new HashMap<String, String>();
		map.put("projectName", "New Project");
		list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("projectName",rs.getString("projectName"));
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
		}
		return list;
	}
	//author=longzhe ����ݿ��ҵ����е�product ������ʱѡ��
	public List searchProductNames(Statement stmt, int i) throws Exception
	{
		String str="";
		if(i==0){
			str=" where hide=0";
		}
		String sql = "select p.productid, p.product, p.groupnum from products p"+str+" order by p.groupnum, p.product;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = null;
		//list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("productid", rs.getString("productid"));
				map.put("product",rs.getString("product"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	public List searchProductNames_fw(Statement stmt) throws Exception
	{
		
		//FWJ 2013-05-09 添加了hide=0
		String sql = "select p.product from products p where p.groupnum=1 and hide=0 order by p.product;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = null;
		//����new product
		map = new HashMap<String, String>();
		map.put("productName", "");
		list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("productName",rs.getString("product"));
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
		}
		return list;
	}
	public List searchProductNames_sw(Statement stmt) throws Exception
	{
		String sql = "select p.product from products p where p.groupnum=2 order by p.product;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String,String> map = null;
		//����new product
		map = new HashMap<String, String>();
		map.put("productName", "New Product");
		list.add(map);
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("productName",rs.getString("product"));
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
		}
		return list;
	}
	//author=longzhe ����ݿ��ҵ����е�ottype ������ʱѡ��
	public List searchOTTypes(Statement stmt) throws Exception
	{
		String sql = "select o.OTTypeId,o.OTTypeName,o.groupnum from ottype o "
					+" order by o.groupnum, length(o.OTTypeName)";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("OTTypeId", rs.getString("OTTypeId"));
				map.put("OTTypeName", rs.getString("OTTypeName"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	public List searchOTTypes_fw(Statement stmt) throws Exception
	{
		//FWJ 2013-05-09 添加了hide=0
		String sql = "select o.OTTypeId,o.OTTypeName from ottype o where hide=0 order by o.OTTypeName desc";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("OTTypeId", rs.getString("OTTypeId"));
				map.put("OTTypeName", rs.getString("OTTypeName"));
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
		}
		return list;
	}
	public List searchOTTypes_sw(Statement stmt) throws Exception
	{
		String sql = "select o.OTTypeId,o.OTTypeName from ottype o"
			+ " where o.OTTypeId <> -1 and o.groupnum=2 order by length(o.OTTypeName)";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("OTTypeId", rs.getString("OTTypeId"));
				map.put("OTTypeName", rs.getString("OTTypeName"));
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
		}
		return list;
	}
	
	public List searchLeaveTypes(Statement stmt) throws Exception
	{
		String sql = "select l.leaveTypeId,l.leaveTypeName from leavetype l"
			+ " where l.leaveTypeId <> -1 order by l.leaveTypeId";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("leaveTypeId", rs.getString("leaveTypeId"));
				map.put("leaveTypeName", rs.getString("leaveTypeName"));
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
		}
		return list;
	}
	//author=longzhe
	public List searchDescription(Statement stmt) throws Exception
	{
		String sql = "select d.descriptionid,d.description,d.groupnum from descriptions d"
			+ " order by d.groupnum, d.description";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("id", rs.getString("descriptionid"));
				map.put("description", rs.getString("description"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	public List searchDescription_fw(Statement stmt) throws Exception
	{
		String sql = "select d.descriptionid,d.description,d.groupnum from descriptions d"
			+ " where d.groupnum=1 order by d.groupnum, d.description";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("id", rs.getString("descriptionid"));
				map.put("description", rs.getString("description"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
	//author=longzhe
	public List searchSkillCategory(Statement stmt) throws Exception
	{
		String sql = "select s.skillcategoryid,s.skillcategory,s.groupnum from skillcategories s"
			+ " order by s.groupnum, s.skillcategory";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("id", rs.getString("skillcategoryid"));
				map.put("skillcategory", rs.getString("skillcategory"));
				map.put("groupnum", rs.getString("groupnum"));
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
		}
		return list;
	}
/**
 * ȡ���û�approve��Ȩ���б�
 * @author=longzhe
 * @flag
 */
	public List searchAppvoeLevel(Statement stmt) throws Exception
	{
		String sql = "select * from approvelevel;";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				map = new HashMap<String, String>();
				map.put("id", rs.getString("value"));
				map.put("discribe", rs.getString("discribe"));
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
		}
		return list;
	}
//@Dancy 2011-11-21
	public List searchComponent(Statement stmt, int i) throws Exception
	{
		String str="";
		if(i==0){
			str=" where hide=0";
		}
		String sql="select * from components"+str+" order by components.componentName";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("componentid", rs.getString("componentid"));
				map.put("componentName", rs.getString("componentName"));
				map.put("groupnum", rs.getString("groupnum"));
				list.add(map);
				
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}		
		return list;
	}
	
	public List searchMilestoneList(Statement stmt, int i) throws Exception {
		String str="";
		if(i==0){
			str=" where hide=0";
		}
		String sql = "select milestoneid, milestone, groupnum from milestones"+str+" order by groupnum,milestone";
		ResultSet rs = null;
		List<Map> list = new ArrayList<Map>();
		Map<String, String> map = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("milestoneid", rs.getString("milestoneid"));
				map.put("milestone", rs.getString("milestone"));
				map.put("groupnum", rs.getString("groupnum"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != rs)
				rs.close();
//			if (null != stmt)
//				stmt.close();
		}
		return list;
	}

	/**
	 * 
	 * @param stmt
	 * @return
	 * @throws Exception
	 * @Dancy 2011-12-14
	 * @flag
	 */
	public List searchLocationCode(Statement stmt) throws Exception
	{
		String sql="select * from locationcodes";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("codeID", rs.getString("codeID"));
				map.put("code", rs.getString("code"));
				map.put("groupnum", rs.getString("groupnum"));
				list.add(map);			
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}		
		return list;
	}
	
	/**
	 * 
	 * @param stmt
	 * @return
	 * @throws Exception
	 * @Dancy 2011-12-14
	 * @flag
	 */
	public List searchPOManager(Statement stmt, int i) throws Exception
	{
		//增加了hide=0 FWJ 2013-05-13
		String str="";
		if(i==0){
			str=" where hide =0";
		}
		String sql="select * from pomanager"+str;
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("POManagerID", rs.getString("POManagerID"));
				map.put("POManager", rs.getString("POManager"));
				if (1 == rs.getInt("groupnum"))
					map.put("groupnum", "fw");
				else
					map.put("groupnum", "sw");
				list.add(map);			
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}		
		return list;
	}
	/**
	 * 
	 * @param stmt
	 * @return
	 * @throws Exception
	 * @Dancy 2011-12-14
	 * @flag
	 */
	public List searchBillCycle(Statement stmt) throws Exception
	{
		String sql="select * from billcycles";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("cycleID", rs.getString("cycleID"));
				map.put("BillCycles", rs.getString("BillCycles"));
				list.add(map);			
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}		
		return list;
	}
	/**
	 * Ϊ���һ���µ�PO��ȡ��wbs�б�
	 * @param stmt
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public List searchWBS(Statement stmt) throws Exception
	{
		//添加了hide=0语句 FWJ 2013-05-09
		String sql="select * from wbs where hide=0";
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("wbsid", rs.getString("wbsid"));
				map.put("wbs", rs.getString("wbs"));
				list.add(map);			
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}		
		return list;
	}
	
/**
 * ���skillLevelȡ��workType list	
 * @param stmt
 * @param skillLevel
 * @param wlist
 * @return
 * @throws Exception
 */
	public List searchWorkTypeList(Statement stmt,String skillLevel,List<ExpenseDetail> wlist) throws Exception
	{
		List templist = new ArrayList();
		String ww = "";
		String str="";
		if(wlist.size()>0)
		{
			for(int i=0;i<wlist.size();i++)
			{
				ww = ww + ",'" + wlist.get(i).getWorktype()+"'";
			}
			//Added by FWJ 2013-05-14
			templist.add((wlist.get(0).getWorktype()));
			int result=0;
			for(int i=1;i<wlist.size();i++){
				for(int j=0;j<templist.size();j++){
					result=0;
					if(wlist.get(i).getWorktype().equals(templist.get(j))){
						result=1;
						break;
						}
					}
				if(result!=1){
					templist.add(wlist.get(i).getWorktype());
				}
			}
//			if(templist.size()==0){
//				str="'"+templist.get(0)+"'";
//			}else {
//				str="'"+templist.get(0)+"'";
//				for(int i=1;i<templist.size();i++){
//				str=str+",'"+templist.get(i)+"'";
//			}
//			}
//			System.out.println(str);
			for(int i=0;i<templist.size();i++){
				str=str+",'"+templist.get(i)+"'";
			}
		}
		// change by dancy 2013/04/01
		//修改Test Case Development为Test Development FWJ 2013-05-22
//		String sql = "";
//		if(wlist.size()==0&&skillLevel.indexOf("Test Engineer")>=0)
//		{
//			sql = "select id, worktype from worktype where worktype='Test Development'";
//		}
//		if(wlist.size()==0&&skillLevel.indexOf("III")>=0)
//		{
//			
//			sql = "select id, worktype from worktype where worktype in('Test Development','Test Lead')";
//		}
//		if(wlist.size()>=1&&skillLevel.indexOf("III")>=0)
//		{
//			
//		//	sql = "select id, worktype from worktype where worktype in('Test Case Development','Test Lead') and worktype not in('"+wlist.get(0).getWorktype()+"')";
//			sql = "select id, worktype from worktype where worktype in('Test Development','Test Lead') and worktype not in('"+"'"+str+")";
//		}
//		if(wlist.size()==0&&skillLevel.indexOf("Project Manager")>=0)
//		{
//			sql = "select id, worktype from worktype where hide=0 and worktype='Administrative'";
//		}
//		if(skillLevel.indexOf("Tester")>=0)
//		{
//			sql = "select id, worktype from worktype where hide=0 and worktype not in('Test Lead','Test Development','Management'"+ww+")";
//		}
		
		System.out.println("get worktype for skillLevel=" + skillLevel);
		
		String desc = DescriptionOfSkillLevel.descriptions.get(skillLevel);
		System.out.println("got desc=" + desc);
		
		String workTypes = DescriptionOfSkillLevel.workTypes.get(desc);
		System.out.println("got workTypes=" + workTypes);
		
		String sql = "select id, worktype from worktype where hide=0";
		if(StringUtils.isBlank(workTypes))
		{
			String notForTester = DescriptionOfSkillLevel.getActiveTypeByDescription("NotForTester");
			if(str.length()>1&& str.startsWith(","))
			{
				sql = sql + " and worktype not in ("+ notForTester +  str + ")";
			}
			else
			{
				sql = sql + " and worktype not in (" +notForTester+ ")";
			}
		}
		else
		{
			sql = sql + " and worktype in (" +workTypes+ ")";
			if(str.length()>1&& str.startsWith(","))
			{
				str = str.substring(1);
				sql = sql + " and worktype not in (" +str+ ")";
			}
		}
		
		System.out.println("sql for worktype = "+sql);
		List<Map> list = new ArrayList<Map>();
		ResultSet rs = null;
		Map<String, String> map = null;
		if(!sql.equals(""))
		{
			try
			{
				rs = stmt.executeQuery(sql);
				while (rs.next())
				{
					map = new HashMap<String, String>();
					map.put("wid", rs.getString("id"));
					map.put("worktype", rs.getString("worktype"));
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
				if (stmt != null) 
				{
					stmt.close();
				}
			}
		}
		return list;
	}
	
	/**
	 *@author hanxiaoyu01
	 *����Case and Defect��Ĭ��ֵ
	 * 2013-02-16
	 */
	public DefaultCaseDefect findDefaultCaseDefect(Connection conn,int userId)throws Exception{
		String sql="select * from default_casedefect where userId=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		DefaultCaseDefect dcd=new DefaultCaseDefect();
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				dcd.setUserId(userId);
				dcd.setProductId(rs.getInt("productId"));
				dcd.setComponentId(rs.getInt("compomentId"));
				dcd.setMilestoneId(rs.getInt("milestoneId"));
			}
		}catch(Exception e){
			if(rs!=null){
				rs.close();
			}
		}
		return dcd;
	}
}
