package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

/**
 * 管理员用户
 * @author zhusimin
 *
 */
public class Administrator implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private PermissionLevel permissionLevel = new PermissionLevel();
	private PermissionName permissionName = new PermissionName();
	private SkillLevel skillLevel = new SkillLevel();
	private Location location = new Location();
	private OTType otType = new OTType();
	private LeaveType leaveType = new LeaveType();
	//hanxiaoyu01 2012-12-11
	private String email;
	private String sender;
	private String psw;
	public LeaveType getLeaveType()
	{
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType)
	{
		this.leaveType = leaveType;
	}
	public PermissionLevel getPermissionLevel()
	{
		return permissionLevel;
	}
	public void setPermissionLevel(PermissionLevel permissionLevel)
	{
		this.permissionLevel = permissionLevel;
	}
	public PermissionName getPermissionName()
	{
		return permissionName;
	}
	public void setPermissionName(PermissionName permissionName)
	{
		this.permissionName = permissionName;
	}
	public SkillLevel getSkillLevel()
	{
		return skillLevel;
	}
	public void setSkillLevel(SkillLevel skillLevel)
	{
		this.skillLevel = skillLevel;
	}
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location location)
	{
		this.location = location;
	}
	public OTType getOtType()
	{
		return otType;
	}
	public void setOtType(OTType otType)
	{
		this.otType = otType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	
}
