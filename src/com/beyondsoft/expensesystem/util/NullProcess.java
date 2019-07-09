package com.beyondsoft.expensesystem.util;

public final class NullProcess
{
	public static String toNotNull(String value)
	{
		if(value == null)
			return "";
		return value;
	}
}