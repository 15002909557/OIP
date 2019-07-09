package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.beyondsoft.expensesystem.domain.system.SysUser;


/**
 * 用户权限下的Group信息
 * 
 * @author Longzhe
 * 
 */
@SuppressWarnings("serial")
public class Groups implements Serializable
{
	private int Gid = -1;
	private String Gname = "";
	private String user = "NA";
	private String Announcement = "";
	private int approve = 0;
	private String lockday = "";
	private String approveday = "";
	private int defaultUI = -1;
	private List<SysUser> GUsers = new ArrayList<SysUser>();
	private String comments = "";
	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getGid() {
		return Gid;
	}
	public void setGid(int gid) {
		Gid = gid;
	}
	public String getGname() {
		return Gname;
	}
	public void setGname(String gname) {
		Gname = gname;
	}
	public List<SysUser> getGUsers() {
		return GUsers;
	}
	public void setGUsers(List<SysUser> users) {
		GUsers = users;
	}
	public String getAnnouncement() {
		return Announcement;
	}
	public void setAnnouncement(String announcement) {
		Announcement = announcement;
	}
	public int getApprove() {
		return approve;
	}
	public void setApprove(int approve) {
		this.approve = approve;
	}
	public String getLockday() {
		return lockday;
	}
	public void setLockday(String lockday) {
		this.lockday = lockday;
	}
	public String getApproveday() {
		return approveday;
	}
	public void setApproveday(String approveday) {
		this.approveday = approveday;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getDefaultUI() {
		return defaultUI;
	}
	public void setDefaultUI(int defaultUI) {
		this.defaultUI = defaultUI;
	}
	
}
