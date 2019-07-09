package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.beyondsoft.expensesystem.domain.checker.Rate;

@SuppressWarnings("unchecked")
public class RateDao {
	private static RateDao dao = null;
	
	public static RateDao getInstance() {
		if (dao == null) {
			dao = new RateDao();
		}
		return dao;
	}
	/**
	 * 查询所有Rates信息
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	
	public List searchRates(Connection conn) throws Exception
	{
		List<Rate> ratelist = new ArrayList<Rate>();
		String sql = "select rateId,rateValue, "
							+"skillLevelId, (select skillLevelName from skilllevels where skilllevels.skillLevelId = rates.skillLevelId) as skillLevelName, "
							+"locationId,(select locationName from locations where locations.locationId = rates.locationId ) as locationName, "
							+"OTTypeId,(select OTTypeName from ottype where ottype.OTTypeId = rates.OTTypeId ) as OTTypeName "
					+"from rates;";
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Rate r = new Rate();
				r.setRateId(rs.getInt("rateId"));
				r.setRateValue(rs.getDouble("rateValue"));
				r.setSkillLevelId(rs.getInt("skillLevelId"));
				r.setSkillLevel(rs.getString("skillLevelName"));
				r.setLocationId(rs.getInt("locationId"));
				r.setLocation(rs.getString("locationName"));
				r.setOTTypeId(rs.getInt("OTTypeId"));
				r.setOTType(rs.getString("OTTypeName"));
				ratelist.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (conn != null)
				conn.close();
		}
		
		return ratelist;
	}
	/**
	 * 增加一条rate信息
	 * @param stmt
	 * @param skillLevel
	 * @param location
	 * @param OTType
	 * @param rate
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public String addRate(Statement stmt,String skillLevel,String location,String OTType,Double rate) 
							throws Exception
	{
		String result = "";
		String sql = "select rateId from rates where "
						+"skillLevelId=(select skillLevelId from skillLevels where skillLevelName='"+skillLevel+"' ) and "
						+"locationId=(select locationId from locations where locationName='"+location+"' ) and "
						+"OTTypeId=(select OTTypeId from ottype where OTTypeName='"+OTType+"' );";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				result = "This rate already exist,refer to ID:"+rs.getString("rateId");
			}
			else
			{
				rs.close();
				sql = "insert rates(skillLevelId,locationId,OTTypeId,rateValue) values("
						+"(select skillLevelId from skillLevels where skillLevelName='"+skillLevel+"'),"
						+"(select locationId from locations where locationName='"+location+"' ),"
						+"(select OTTypeId from ottype where OTTypeName='"+OTType+"' ),"
						+ rate+");";
				int i = stmt.executeUpdate(sql);
				if(i>0)
					result = "Added rate Successful";
				else
					result = "Added rate failed";
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null)
				stmt.close();
		}
		return result;
	}
	/**
	 * 删除一条rate信息
	 * @param stmt
	 * @param rateid
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public boolean deleteRate(Statement stmt, int rateid) throws Exception
	{
		boolean result = false;
		String sql = "delete from rates where rateId="+rateid;
		try{
			int i = stmt.executeUpdate(sql);
			result = i>0;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null)
				stmt.close();
		}
		return result;
	}
	/**
	 * 查找一条rate详细信息
	 * @param stmt
	 * @param rateid
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public Rate searchRatebyId(Statement stmt, int rateid) throws Exception
	{
		Rate rate = null;
		String sql = "select rateId,rateValue, "
						+"skillLevelId, (select skillLevelName from skilllevels where skilllevels.skillLevelId = rates.skillLevelId) as skillLevelName, "
						+"locationId,(select locationName from locations where locations.locationId = rates.locationId ) as locationName, "
						+"OTTypeId,(select OTTypeName from ottype where ottype.OTTypeId = rates.OTTypeId ) as OTTypeName "
					+"from rates where rateId="+rateid+";";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				rate = new Rate();
				rate.setRateId(rs.getInt("rateId"));
				rate.setRateValue(rs.getDouble("rateValue"));
				rate.setSkillLevelId(rs.getInt("skillLevelId"));
				rate.setSkillLevel(rs.getString("skillLevelName"));
				rate.setLocationId(rs.getInt("locationId"));
				rate.setLocation(rs.getString("locationName"));
				rate.setOTTypeId(rs.getInt("OTTypeId"));
				rate.setOTType(rs.getString("OTTypeName"));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null)
				stmt.close();
		}
		return rate;
	}
	/**
	 * 修改保存一条rate信息
	 * @param mapping
	 * @param form
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public boolean saveRate(Statement stmt,int rateid, String skillLevel, String location, String OTType, Double rate)
							throws Exception
	{
		boolean result = false;
		String sql = "update rates set skillLevelId="
						+"(select skillLevelId from skillLevels where skillLevelName='"+skillLevel+"'),"
						+"locationId="
						+"(select locationId from locations where locationName='"+location+"' ),"
						+"OTTypeId="
						+"(select OTTypeId from ottype where OTTypeName='"+OTType+"'),"
						+"rateValue="+rate
						+"where rateId="+rateid;
		try{
			int i = stmt.executeUpdate(sql);
			result = i>0;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null)
				stmt.close();
		}
		return result;
	}
}
