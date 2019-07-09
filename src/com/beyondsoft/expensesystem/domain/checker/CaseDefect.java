package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;

/*
 * 2011-11-18 by Dancy
 */

@SuppressWarnings("serial")
public class CaseDefect implements Serializable 
{
	private int id = -1;
	private int productID = -1;
	private int componentID = -1;
	private String sDate = null;
	private String eDate = null;
	private int cases = -1;
	private int urgentdefect = -1;
	private int highdefect = -1;
	private int normaldefect = -1;
	private int lowdefect = -1;
	private int milestoneid = -1;
	private String product = "";
	private String componentName = "";
	private String milestone = "";
	private String Creator = "";//dancy 2012-12-26
	private String createTime = null;
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String creator) {
		Creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getProductID(){
		return productID;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public int getUrgentdefect() {
		return urgentdefect;
	}
	public void setUrgentdefect(int urgentdefect) {
		this.urgentdefect = urgentdefect;
	}
	public int getHighdefect() {
		return highdefect;
	}
	public void setHighdefect(int highdefect) {
		this.highdefect = highdefect;
	}
	public int getNormaldefect() {
		return normaldefect;
	}
	public void setNormaldefect(int normaldefect) {
		this.normaldefect = normaldefect;
	}
	public int getLowdefect() {
		return lowdefect;
	}
	public void setLowdefect(int lowdefect) {
		this.lowdefect = lowdefect;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getSDate() {
		return sDate;
	}
	public void setSDate(String sdate) {
		sDate = sdate;
	}
	public String getEDate() {
		return eDate;
	}
	public void setEDate(String edate) {
		eDate = edate;
	}
	public int getMilestoneid() {
		return milestoneid;
	}
	public void setMilestoneid(int milestoneid) {
		this.milestoneid = milestoneid;
	}

}
