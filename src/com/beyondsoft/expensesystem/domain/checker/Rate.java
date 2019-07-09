package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Rate implements Serializable
{
	private int rateId = -1;
	private Double rateValue = 0.00;
	private int skillLevelId = -1;
	private String skillLevel = "";
	private int locationId = -1;
	private String location = "";
	private int OTTypeId = -1;
	private String OTType = "";

	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public int getSkillLevelId() {
		return skillLevelId;
	}
	public void setSkillLevelId(int skillLevelId) {
		this.skillLevelId = skillLevelId;
	}
	public String getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getOTTypeId() {
		return OTTypeId;
	}
	public void setOTTypeId(int typeId) {
		OTTypeId = typeId;
	}
	public String getOTType() {
		return OTType;
	}
	public void setOTType(String type) {
		OTType = type;
	}
	public Double getRateValue() {
		return rateValue;
	}
	public void setRateValue(Double rateValue) {
		this.rateValue = rateValue;
	}
	
}