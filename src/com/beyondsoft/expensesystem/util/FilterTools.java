package com.beyondsoft.expensesystem.util;

import org.apache.commons.lang.StringUtils;

public class FilterTools {

	public static boolean isElementContained(String source, String target, String regex)
	{
		boolean result = false;
		
		if(StringUtils.isNotBlank(source))
		{
			if(!source.contains(regex) && source.equalsIgnoreCase(target))
			{
				result = true;
			}
			else
			{
				String[] sourceArr = source.split(regex);
				for(int i=0; i<sourceArr.length; i++)
				{
					if(sourceArr[i].replace(";", ",").equalsIgnoreCase(target))
					{
						result = true;
					}
				}
			}
		}
		
		
		
		return result;
	}
	
	public static boolean isUseSameTemplate(String source, String target)
	{
		boolean result = false;
		if(StringUtils.isNotBlank(source) && StringUtils.isNotBlank(target))
		{
			result = DescriptionOfSkillLevel.descriptions.get(target).equalsIgnoreCase(DescriptionOfSkillLevel.descriptions.get(source));
		}
		return result;
	}
}
