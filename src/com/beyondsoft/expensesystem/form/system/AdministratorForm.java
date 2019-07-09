package com.beyondsoft.expensesystem.form.system;

import com.beyondsoft.expensesystem.domain.system.Administrator;
import com.beyondsoft.expensesystem.domain.system.Location;
import com.beyondsoft.expensesystem.domain.system.OTType;
import com.beyondsoft.expensesystem.domain.system.PermissionLevel;
import com.beyondsoft.expensesystem.domain.system.PermissionName;
import com.beyondsoft.expensesystem.domain.system.SkillLevel;
import com.beyondsoft.expensesystem.util.BaseActionForm;

public class AdministratorForm extends BaseActionForm
{
	private static final long serialVersionUID = 1L;
	
	private Administrator administrator = new Administrator();
	private PermissionLevel permissionLevel = new PermissionLevel();
	private PermissionName permissionName = new PermissionName();
	private SkillLevel skillLevel = new SkillLevel();
	private Location location = new Location();
	private OTType otType = new OTType();
	
	//hanxiaoyu01 2012-12-11
	private String email;
	private String sender;
	private String psw;
	
	public Administrator getAdministrator()
	{
		return administrator;
	}
	public void setAdministrator(Administrator administrator)
	{
		this.administrator = administrator;
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
