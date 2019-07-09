package com.beyondsoft.expensesystem.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DescriptionOfSkillLevel {
	
	public static Map<String, String> descriptions = getDescriptions();
	public static Map<String, String> workTypes = getWorkTypes();
	public static Map<String, String> specDesc = getSpecialDescription();
	public static Map<String, String> specNotDesc = specialDescriptionNotNeeded();
	public static Map<String, String> specNeedDesc = specialDescriptionNeeded();
	public DescriptionOfSkillLevel()
	{
		
	}
	
	
	public static Map<String, String> getDescriptions()
	{
		Map<String, String> descMap = new HashMap<String, String>();
		descMap.put("Tech I - Tester I", "Tester");
		descMap.put("Tech II - Tester II", "Tester");
		descMap.put("Tech III - Tester III", "Tester");
		descMap.put("Test Tech II", "Tester");
		descMap.put("Test Tech III", "Tester");
		descMap.put("Test Engineer I", "Test Developer");
		descMap.put("Test Engineer II", "Test Developer");
		descMap.put("Test Engineer III", "Test Engineer");
		descMap.put("Test Engineer III - Regional", "Test Engineer");
		descMap.put("Test Engineer III - Regional NP", "Test Engineer");
		descMap.put("Test Lead", "Test Lead");
		descMap.put("Software Engineer", "Software Engineer");
		descMap.put("Software Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Systems / Software Engineer", "Software Engineer");
		descMap.put("Systems / Software Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Systems / SW Engineer", "Software Engineer");
		descMap.put("Systems / SW Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Automation Engineer", "Software Engineer");
		descMap.put("Automation Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Project Manager", "Project Manager");
		descMap.put("Project Manager II", "Project Manager");
		descMap.put("Project Manager, Sr.", "Project Manager");
		
		return descMap;
	}
	
	public static Map<String, String> getWorkTypes()
	{
		Map<String, String> atMap = new HashMap<String, String>();
		
		atMap.put("Tester", "");
		atMap.put("NotForTester", "'Test Lead', 'Test Development'");
		atMap.put("Test Developer", "'Test Development', 'Test Execution - Auto'");
		atMap.put("Test Engineer", "'Test Lead', 'Test Development'");
		atMap.put("Test Lead", "'Test Lead'");
		atMap.put("Software Engineer", "'Test Execution', 'Test Lead', 'Test Development', 'Other', 'Adhoc Testing', 'Defect Management', 'Regression Testing'");
		atMap.put("Software Engineer, Sr.", "'Test Development', 'Other'");
		atMap.put("Project Manager", "'Administrative'");
		
		return atMap;
	}
	
	public static Map<String, String> specialDescriptionNotNeeded()
	{
		Map<String, String> descMap = new HashMap<String, String>();
		descMap.put("Software Engineer", "Software Engineer");
		descMap.put("Software Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Systems / Software Engineer", "Software Engineer");
		descMap.put("Systems / Software Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Systems / SW Engineer", "Software Engineer");
		descMap.put("Systems / SW Engineer, Sr.", "Software Engineer, Sr.");
		descMap.put("Automation Engineer", "Software Engineer");
		descMap.put("Automation Engineer, Sr.", "Software Engineer, Sr.");
		return descMap;
	}
	
	public static Map<String, String> specialDescriptionNeeded()
	{
		Map<String, String> descMap = new HashMap<String, String>();
		descMap.put("Test Engineer III", "Test Engineer");
		descMap.put("Test Engineer III - Regional", "Test Engineer");
		descMap.put("Test Engineer III - Regional NP", "Test Engineer");
		return descMap;
	}
	
	public static Map<String, String> getSpecialDescription()
	{
		Map<String, String> atMap = new HashMap<String, String>();
		atMap.put("Test Lead", "Test Lead");
		atMap.put("Test Development", "Test Developer");
		return atMap;
	}
	
	public static String getActiveTypeBySkillLevel(String skillLevel)
	{
		return descriptions.get(workTypes.get(skillLevel));
	}

	public static String getActiveTypeByDescription(String description)
	{
		return workTypes.get(description);
	}
	
	public static String getDescriptionBySkillLevel(String skillLevel)
	{
		return descriptions.get(skillLevel);
	}
	
	public static String getDesc(String workType, String skillLevel)
	{
		String desc = descriptions.get(skillLevel);
		String nDesc = specNotDesc.get(skillLevel);
		String dDesc = specNeedDesc.get(skillLevel);
		String sDesc = specDesc.get(workType);
		
		if(StringUtils.isNotBlank(nDesc))
		{
			if(nDesc.equals(sDesc) && StringUtils.isNotBlank(sDesc))
			{
				desc = sDesc;
			}
		}
		else
		{
			if(StringUtils.isNotBlank(dDesc) && StringUtils.isNotBlank(sDesc))
			{
				desc = sDesc;
			}
		}
		
		return desc;
	}
}

