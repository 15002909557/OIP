package com.beyondsoft.expensesystem.domain.checker;
import java.io.Serializable;
@SuppressWarnings("serial")
public class NonLaborCost implements Serializable
{
	private int nid = -1;
	private int groupNameID = -1;
	private String groupName = null;
	private String ndate = null;
	private int localeID = -1;
	private  String locale = null;
	private String purchaseOrder = null;
	private double nonLaborCost = 0;
	private String notes = null;
	private int purchaseOrderID = -1;
	
	private int POID = -1;
	private String ponumber = null;
	
	//2012-11-08 hanxiaoyu01
	private String Quantity;
	private String Comments;
	private int ProductId;
	private String Product;
	private String Component;
	private int ComponentId;
	private String WBS = "";
	private String creator = "";
	public String getWBS() {
		return WBS;
	}
	public void setWBS(String wBS) {
		WBS = wBS;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getPOID() {
		return POID;
	}
	public void setPOID(int poid) {
		POID = poid;
	}
	public String getPonumber() {
		return ponumber;
	}
	public void setPonumber(String ponumber) {
		this.ponumber = ponumber;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public int getGroupNameID() {
		return groupNameID;
	}
	public void setGroupNameID(int groupNameID) {
		this.groupNameID = groupNameID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getNdate() {
		return ndate;
	}
	public void setNdate(String ndate) {
		this.ndate = ndate;
	}
	public int getLocaleID() {
		return localeID;
	}
	public void setLocaleID(int localeID) {
		this.localeID = localeID;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public double getNonLaborCost() {
		return nonLaborCost;
	}
	public void setNonLaborCost(double nonLaborCost) {
		this.nonLaborCost = nonLaborCost;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getPurchaseOrderID() {
		return purchaseOrderID;
	}
	public void setPurchaseOrderID(int purchaseOrderID) {
		this.purchaseOrderID = purchaseOrderID;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}
	public String getComponent() {
		return Component;
	}
	public void setComponent(String component) {
		Component = component;
	}
	public int getComponentId() {
		return ComponentId;
	}
	public void setComponentId(int componentId) {
		ComponentId = componentId;
	}
	
}
