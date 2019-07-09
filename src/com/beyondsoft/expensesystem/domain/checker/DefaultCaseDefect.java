package com.beyondsoft.expensesystem.domain.checker;

public class DefaultCaseDefect {
	private int userId;
	private int productId;
	private int componentId;
	private int milestoneId;
	public DefaultCaseDefect(){}
	public DefaultCaseDefect(int userId, int productId, int componentId,
			int milestoneId) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.componentId = componentId;
		this.milestoneId = milestoneId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getComponentId() {
		return componentId;
	}
	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}
	public int getMilestoneId() {
		return milestoneId;
	}
	public void setMilestoneId(int milestoneId) {
		this.milestoneId = milestoneId;
	}
	
}
