package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ReportData_PayChex implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer intUserID = 0;
	
	private String strEmployNumber6 = space(6);
	private String strEmployName25 = space(25);
	private String strOverrideDept6 = space(6);
	private String strJob12 = space(12);
	private String strShift1 = "1";
	private String strDE1	= "D";
	private String strEarnDeductionCode2 = space(2);
	
	@SuppressWarnings("unused")
	private double douRate = 0;
	private String strRate9 = zero(9);		
	private String strHours8 = zero(8);	
	private Date datDate=null;
	
	private String strYear4 = space(4);
	private String strMonth2 = space(2);
	private String strDay2 = space(2);
	private String strFillerA2 = space(2);
	private String strFillerB2 = space(2);
	private String strAmount9 = zero(9);
	private String strSeqNumber1 = "1";
	private String strOverrideDivision6 = space(6);
	private String strOverrideBranch6	= space(6);
	private String strOverrideState2 = space(2);
	private String strOverrideLocal10 = space(10);
	private String strStateLocalMiscField1 = space(1);
	private String strRateNumber1	= "1";
	private String strSocSecNumber11 = "###-##-####";
	
	private boolean remove = false;

	private void setAmount9(){
		Double douHours=Double.parseDouble(this.strHours8);
		Double douRate=Double.parseDouble(this.strRate9);
		Double douAmount=douHours*douRate;
		this.strAmount9=douAmount.toString();
		//System.out.println("Hours*Rate=Amount : "+this.strHours8+"*"+this.strRate9+"="+this.strAmount9);
	}
	
	private String formatNumber(String strNumber,int size,int dec){
		String strResult="";
		
		if (null==strNumber || strNumber.equals("")){
			strNumber="0";
		}
		strNumber=strNumber.trim();
		if (strNumber.length()>size){
			strResult=fillSpaceTo("Err!",size);
		}else{
			Double douNumber=Double.parseDouble(strNumber);
			for (int i=0;i<dec;i++){
				douNumber=douNumber*10;
			}
			Integer intNumber=Integer.valueOf((int)Math.floor(douNumber));
			//System.out.println(intNumber);
			strResult=fillZeroTo(intNumber.toString(),size);
			//System.out.println(strResult);
		}
		
		return strResult;
	}
	public String space(int s){
		String str="";
		for (int i=0;i<s;i++){
			str+=" ";
		}
		return str;
	}
	public String zero(int s){
		String str="";
		for (int i=0;i<s;i++){
			str+="0";
		}
		return str;
	}
	
	public String fillSpaceTo(String oStr, int s){
		String str="";
		if (oStr.length()>s){
			str=oStr.substring(0, s);
		}else{
			str=oStr;
			while (str.length()<s){
				str=" "+str;
			}
		}
		return str;
	}
	public String fillZeroTo(String oStr, int s){
		String str="";
		if (oStr.length()>s){
			str=oStr.substring(0, s);
		}else{
			str=oStr;
			while (str.length()<s){
				str="0"+str;
			}
		}
		return str;
	}
	public String getPayChexString(){
		String str="";
		
		str+=fillZeroTo(this.strEmployNumber6,6);
		str+=fillSpaceTo(strEmployName25,25);
		str+=fillSpaceTo(strOverrideDept6,6);
		str+=fillSpaceTo(strJob12,12);
		str+=fillZeroTo(strShift1,1);
		str+=fillSpaceTo(strDE1,1);
		str+=fillSpaceTo(strEarnDeductionCode2,2);
		
		str+=this.formatNumber(strRate9,9,4);
		str+=this.formatNumber(strHours8,8,4);
		
		str+=fillZeroTo(strYear4,4);
		str+=fillZeroTo(strMonth2,2);
		str+=fillZeroTo(strDay2,2);
		
		str+=fillSpaceTo(strFillerA2,2);
		str+=fillSpaceTo(strFillerB2,2);
		str+=this.formatNumber(strAmount9,9,2);
		str+=fillZeroTo(strSeqNumber1,1);
		str+=fillSpaceTo(strOverrideDivision6,6);
		str+=fillSpaceTo(strOverrideBranch6,6);
		str+=fillSpaceTo(strOverrideState2,2);
		str+=fillSpaceTo(strOverrideLocal10,10);
		str+=fillSpaceTo(strStateLocalMiscField1,1);
		str+=fillSpaceTo(strRateNumber1,1);
		str+=fillSpaceTo(strSocSecNumber11,11);
		
		
		//str+=intUserID.toString();
		//str+=strEmployNumber6;		
		//if (null!=datDate){
		//	str+=datDate.toString();
		//}else{
		//	str+=space(8);
		//}
		//str+="\r\n";
		
		return str;
	}
	
	public Integer getIntUserID() {
		return intUserID;
	}
	public ArrayList<ReportData_PayChex> mergeDup(
			ArrayList<ReportData_PayChex> listReportData){
		ArrayList<ReportData_PayChex> listResult = new ArrayList<ReportData_PayChex>();
		ReportData_PayChex tempData = null;
		
		if (null!=listReportData){
			int i=0;
			while ( i<listReportData.size()){
				if (!listReportData.get(i).isRemove()){
					int j=i+1;					
					tempData=listReportData.get(i);
					listReportData.get(i).setRemove(true);
					String tempHours=tempData.getStrHours8();
					
					while (j<listReportData.size()){
						if (!listReportData.get(j).isRemove()){
							if (listReportData.get(j).getIntUserID()==tempData.getIntUserID() && 
									//listReportData.get(j).getStrRate9()==tempData.getStrRate9() &&
									listReportData.get(j).getDatDate().equals(tempData.getDatDate())){
								
								String tempHours1=tempHours;
								String tempHours2=listReportData.get(j).getStrHours8();
								listReportData.get(j).setRemove(true);
								
								tempHours=Double.toString((Double.parseDouble("0"+tempHours1)+Double.parseDouble("0"+tempHours2)));
							}
						}					
						j++;
					}
					tempData.setStrHours8(tempHours);
					listResult.add(tempData);
				}				
				i++;
			}
		}
		
		return listResult;
	}

	public void setIntUserID(Integer intUserID) {
		this.intUserID = intUserID;
	}

	public String getStrEmployNumber6() {
		return strEmployNumber6;
	}

	public void setStrEmployNumber6(String strEmployNumber6) {
		this.strEmployNumber6 = strEmployNumber6;
	}

	public String getStrEmployName25() {
		return strEmployName25;
	}

	public void setStrEmployName25(String strEmployName25) {
		this.strEmployName25 = strEmployName25;
	}

	public String getStrOverrideDept6() {
		return strOverrideDept6;
	}

	public void setStrOverrideDept6(String strOverrideDept6) {
		this.strOverrideDept6 = strOverrideDept6;
	}

	public String getStrJob12() {
		return strJob12;
	}

	public void setStrJob12(String strJob12) {
		this.strJob12 = strJob12;
	}

	public String getStrShift1() {
		return strShift1;
	}

	public void setStrShift1(String strShift1) {
		this.strShift1 = strShift1;
	}

	public String getStrDE1() {
		return strDE1;
	}

	public void setStrDE1(String strDE1) {
		this.strDE1 = strDE1;
	}

	public String getStrEarnDeductionCode2() {
		return strEarnDeductionCode2;
	}

	public void setStrEarnDeductionCode2(String strEarnDeductionCode2) {
		this.strEarnDeductionCode2 = strEarnDeductionCode2;
	}

	public String getStrRate9() {
		return strRate9;
	}

	private void setStrRate9(String strRate9) {
		this.strRate9 = strRate9;
		this.setAmount9();
	}

	public String getStrHours8() {
		return strHours8;
	}

	public void setStrHours8(String strHours8) {
		this.strHours8 = strHours8;
		this.setAmount9();
	}

	public String getStrYear4() {
		return strYear4;
	}

	public void setStrYear4(String strYear4) {
		this.strYear4 = strYear4;
	}

	public String getStrMonth2() {
		return strMonth2;
	}

	public void setStrMonth2(String strMonth2) {
		this.strMonth2 = strMonth2;
	}

	public String getStrDay2() {
		return strDay2;
	}

	public void setStrDay2(String strDay2) {
		this.strDay2 = strDay2;
	}

	public String getStrFillerA2() {
		return strFillerA2;
	}

	public void setStrFillerA2(String strFillerA2) {
		this.strFillerA2 = strFillerA2;
	}

	public String getStrFillerB2() {
		return strFillerB2;
	}

	public void setStrFillerB2(String strFillerB2) {
		this.strFillerB2 = strFillerB2;
	}


	public String getStrSeqNumber1() {
		return strSeqNumber1;
	}

	public void setStrSeqNumber1(String strSeqNumber1) {
		this.strSeqNumber1 = strSeqNumber1;
	}

	public String getStrOverrideDivision6() {
		return strOverrideDivision6;
	}

	public void setStrOverrideDivision6(String strOverrideDivision6) {
		this.strOverrideDivision6 = strOverrideDivision6;
	}

	public String getStrOverrideBranch6() {
		return strOverrideBranch6;
	}

	public void setStrOverrideBranch6(String strOverrideBranch6) {
		this.strOverrideBranch6 = strOverrideBranch6;
	}

	public String getStrOverrideState2() {
		return strOverrideState2;
	}

	public void setStrOverrideState2(String strOverrideState2) {
		this.strOverrideState2 = strOverrideState2;
	}

	public String getStrOverrideLocal10() {
		return strOverrideLocal10;
	}

	public void setStrOverrideLocal10(String strOverrideLocal10) {
		this.strOverrideLocal10 = strOverrideLocal10;
	}

	public String getStrStateLocalMiscField1() {
		return strStateLocalMiscField1;
	}

	public void setStrStateLocalMiscField1(String strStateLocalMiscField1) {
		this.strStateLocalMiscField1 = strStateLocalMiscField1;
	}

	public String getStrRateNumber1() {
		return strRateNumber1;
	}

	public void setStrRateNumber1(String strRateNumber1) {
		this.strRateNumber1 = strRateNumber1;
	}

	public String getStrSocSecNumber11() {
		return strSocSecNumber11;
	}

	public void setStrSocSecNumber11(String strSocSecNumber11) {
		this.strSocSecNumber11 = strSocSecNumber11;
	}

	public Date getDatDate() {
		return datDate;
	}

	@SuppressWarnings("deprecation")
	public void setDatDate(Date datDate) {
		if (null!=datDate){
			this.datDate = datDate;
			this.strYear4 = Integer.toString(1900+datDate.getYear());
			this.strMonth2 = Integer.toString(1+datDate.getMonth());
			this.strDay2 = Integer.toString(datDate.getDate());
		}else{
			this.datDate = new Date();
			this.datDate.setYear(2011-1900);
			this.datDate.setMonth(1);
			this.datDate.setDate(1);			
			this.datDate.setHours(0);
			this.datDate.setMinutes(0);
			this.datDate.setSeconds(0);
			this.strYear4 = Integer.toString(1900+this.datDate.getYear());
			this.strMonth2 = Integer.toString(1+this.datDate.getMonth());
			this.strDay2 = Integer.toString(this.datDate.getDate());
		}
		System.out.println("domain PayChex:"+this.strMonth2);
		
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void setDouRate(double douRate) {
		this.douRate = douRate;
		this.setStrRate9(Double.toString(douRate));
	}

	
		
	
}
