/**
 * 功能：检查页面必输项
 * 用法：this.aa = new Array("txtName", "出错提示信息【例如：txtName不能为空！】"[, "0.00"]);
 * 说明：数组的第一项为'需要检验的控键的名称'、数组的第二项为'出错的提示信息'、数组的第三项可选，为'该控键的初始值'，若填了该项，则初始值也作为空处理
 */
function validateRequired(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oRequired = new required();
	for (x in oRequired) {
		var obj,obj1;
		
		eval("obj = form."+oRequired[x][0]);
		
		if ((obj.type == 'text' ||
			obj.type == 'textarea' ||
			obj.type == 'select-one' ||
			obj.type == 'radio' ||
			obj.type == 'password') &&
			((obj.value == '' && oRequired[x][2] == undefined) ||
			(oRequired[x][2] != undefined && obj.value == oRequired[x][2] || obj.value == ''))) {

			myMistakeColor(obj);
			if (i == 0) {
				focusField = obj;
			}
			fields[i++] = oRequired[x][1];
			bValid = false;
		}  else { 
			myRigthColor(obj);
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}

/**
 * 功能：检查格式
 * 用法：this.aa = new Array("txtName", "出错提示信息【例如：txtName格式不正确，应该为正整型！】", new Function("varName", "this.dataformat='需要检查的格式类型'; return this[varName];"));
 * 说明：需要检查的格式类型：date 日期格式、integer 整型、posInteger 正整型、number 浮点型、posNumber 正浮点型、email 电子邮箱、IDCard 身份证、phone 电话号码
 */
function validateDataFormat(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oDataFormat = new dataFormat();
	for (x in oDataFormat) {
		var obj;

		eval("obj = form."+oDataFormat[x][0]);
		
		if (obj.type == 'text' ||
		obj.type == 'textarea') {
			//检查是否日期型
			if(oDataFormat[x][2]("dataformat") == 'date')
			{
				if(obj.value != '' && checkDate(obj.value) == 0)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查整型
			if(oDataFormat[x][2]("dataformat") == 'integer')
			{
				if(obj.value != '' && checkInteger(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查正整型
			if(oDataFormat[x][2]("dataformat") == 'posInteger')
			{
				if(obj.value != '' && checkPosInteger(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查浮点型
			if(oDataFormat[x][2]("dataformat") == 'number')
			{
				if(obj.value != '' && checkNumber(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查正浮点型
			if(oDataFormat[x][2]("dataformat") == 'posNumber')
			{
				if(obj.value != '' && checkPosNumber(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查身份证号
			if(oDataFormat[x][2]("dataformat") == 'IDCard')
			{
				if(obj.value != '' && isValidIdCardNo(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查邮箱地址
			if(oDataFormat[x][2]("dataformat") == 'email')
			{
				if(obj.value != '' && checkEmail(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查电话号码
			if(oDataFormat[x][2]("dataformat") == 'phone')
			{
				if(obj.value != '' && checkPhone(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
			//检查电话号码
			if(oDataFormat[x][2]("dataformat") == 'commonLetter')
			{
				if(obj.value != '' && checkCommonLetter(obj.value) == false)
				{
					myMistakeColor(obj);
					if (i == 0) 
					{
						focusField = obj;
					}
					fields[i++] = oDataFormat[x][1];
					bValid = false;						
				}  else { 
					myRigthColor(obj);
				}
			}
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}

/**
 * 功能：检查控件的范围
 * 用法：this.aa = new Array("txtRate", "出错提示信息【例如：税率应在0到1之间！】", "[0,1]");
 * 说明：'['或']'表示可以等于，[0,1]表示0<=x<=1,则(0,1)表示0<x<1；'#'表示不检查，例如：[#,1]表示x<=1,[0,#]表示x>=0；
 */
function validateDataRange(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oRequired = new dataRange();
	for (x in oRequired) {
		var obj,obj1;
		
		eval("obj = form."+oRequired[x][0]);
		
		if ((obj.type == 'text' ||
			obj.type == 'textarea' ||
			obj.type == 'select-one' ||
			obj.type == 'radio' ||
			obj.type == 'password') && (obj.value != ''))
			{
			
			var arry = analyze(oRequired[x][2]);
			var b = true;
			var objValue = parseFloat(obj.value);
			var value1 = parseFloat(arry[1]);
			var value2 = parseFloat(arry[2]);
			
			if(value1 != 'NaN' && objValue < arry[1])
			{
				b = false;
			}
			if(value1 != 'NaN' && arry[0] == '(')
			{
				if(objValue == value1)
				{
					b = false;
				}
			}
			
			if(b == true)
			{
				if(value2 != 'NaN' && objValue > value2)
				{
					b = false;
				}
				if(value2 != 'NaN' && arry[3] == ')')
				{
					if(objValue == value2)
					{
						b = false;
					}
				}
			}
	
			if(b == false)
			{
				myMistakeColor(obj);
				if (i == 0) {
					focusField = obj;
				}
				fields[i++] = oRequired[x][1];
				bValid = false;
			}  else {
				myRigthColor(obj);
			}
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}

/**
 * 功能：检查长度
 * 用法：this.aa = new Array("txtName", "出错提示信息【例如：登陆帐号应不小与四位！】", new Function("varName", "长度定义'; return this[varName];"));
 * 说明：长度定义：最小长度：this.minlength='最小的长度值【正整数】'；最大长度：this.maxlength='最大的长度值【正整数】'；
 */
function validateCheckLength(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oCheckLength = new checkLength();
	for (x in oCheckLength) {
		var obj;

		eval("obj = form."+oCheckLength[x][0]);
		
		if (obj.type == 'text' ||
		obj.type == 'textarea') {
			if(oCheckLength[x][2]("maxlength") == undefined)
			{
				var iMin = parseInt(oCheckLength[x][2]("minlength"));
				if (obj.value != '' && checkValueLength(obj.value) < iMin) {
					myMistakeColor(obj);
					if (i == 0) {
						focusField = obj;
					}
					fields[i++] = oCheckLength[x][1];
					bValid = false;
				}  else {
					myRigthColor(obj);
				}
			}else{
				var iMax = parseInt(oCheckLength[x][2]("maxlength"));
				if (obj.value != '' && checkValueLength(obj.value) > iMax) {
					myMistakeColor(obj);
					if (i == 0) {
						focusField = obj;
					}
					fields[i++] = oCheckLength[x][1];
					bValid = false;
				} else {
					myRigthColor(obj);
				}			
			}
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}


/**
 * 功能：两个控键值比较先后txtStartName应该在txtEndName之前
 * 用法：this.aa = new Array("txtStartName","txtEndName", "出错提示信息【例如：dtStart不能在dtEnd之后】","比较的类型")
 * 说明：txtStartName.value应该在txtEndName.value之前（且两者都是如日期放大镜中选出来的标准格式）;比较的类型为:日期'date'、数字'number'
 */
function validateDataCompare(form) {

	var bValid = true;
	var focusField = null;
	var i = 0;
	var fields = new Array();
	oDataFormat = new dataCompare();

	for (x in oDataFormat) {
		var obj,obj1;
		
		eval("obj = form."+oDataFormat[x][0]);
		eval("obj1 = form."+oDataFormat[x][1]);	
		
		if ((obj.type == 'text' ||
			obj.type == 'textarea' ||
			obj.type == 'hidden') && 
			(obj1.type == 'text' || 
			obj1.type == 'textarea' ||
			obj1.type == 'hidden')) 
		{
			if(obj.value != '' && obj1.value != '')
			{
				if(oDataFormat[x][3] == 'date')
				{
					if(checkCompareDate(obj.value,obj1.value) == false)
					{
						myMistakeColor(obj);
						//检查
						if (i == 0)
						{
							focusField = obj;
						}
						fields[i++] = oDataFormat[x][2];
						bValid = false;								
					} else {
						myRigthColor(obj);
					}		
				}else if(oDataFormat[x][3] == 'number'){
					if(checkCompareNum(obj.value,obj1.value) == false)
					{
						myMistakeColor(obj);
						if (i == 0)
						{
							focusField = obj;
						}
						fields[i++] = oDataFormat[x][2];
						bValid = false;								
					} else {
						myRigthColor(obj);
					}		
				}
				
			}
		}
	}
	if (fields.length > 0) {
		focusField.focus();
		alert(fields.join('\n'));
	}
	return bValid;
}

/**
 * 功能：检查是否含有数字和大小写字母之外的字符。
 * 用法：checkCommonLetter(form.txtName.value)
 * 说明：已经带有非空的验证,【范围：0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ】
 */
function checkCommonLetter(input) {
	
	if(input == '')
	{
		return false;
	}
	
	var checkOK = "0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var checkStr = input;
	
	for (i = 0;  i < checkStr.length;  i++){
		ch = checkStr.charAt(i);
		if (checkOK.indexOf(ch) == -1)
		{
			return (false);
		}
	}
	return true;
}

/**
 * 功能：检查是否含有大小写字母之外的字符。
 * 用法：checkBigLetter(form.txtName.value)
 * 说明：已经带有非空的验证，【范围：0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ】
 */
function checkBigLetter(input) {
	
	if(input == '')
	{
		return false;
	}
	
	var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var checkStr = input;
	
	for (i = 0;  i < checkStr.length;  i++){
		ch = checkStr.charAt(i);
		if (checkOK.indexOf(ch) == -1)
		{
			return (false);
		}
	}
	return true;
}

/**
 * 功能：检查s中的字符是否与bag中的字符匹配
 * 用法：checkCharsNotInBag (s, bag)
 * 说明：
 */
function checkCharsNotInBag (s, bag)
{ 
    var i,c;
    for (i = 0; i < s.length; i++) { 
        c = s.charAt(i);
        if (bag.indexOf(c) == -1) 
			return true;
    }
    return false;
}

/**
 * 功能：检查该控键的值是否不是中文
 * 用法：checkNotChinese(form.txtName.value)
 * 说明：【范围：ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789><,[]{}?/+=|\\'\":;~!#$%()`】
 */
function checkNotChinese(s)
{
    var errorChar;
    var badChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789><,[]{}?/+=|\\'\":;~!#$%()`";
    if (checkCharsNotInBag(s, badChar) == true ) {
        return true;
    } 
    return false;
}

/**
 * 功能：检查电话号码
 * 用法：checkPhone(form.txtName.value)
 * 说明：已经带有非空的验证【范围：1234567890()-】
 */
function checkPhone(value)
{
	if(value == '')
	{
		return false;
	}
	
	var re = "1234567890-";
	var checkStr = value;
	for (i = 0;  i < checkStr.length;  i++)
	{
		ch = checkStr.charAt(i);
		if (re.indexOf(ch) == -1)
		{
			return (false);
		}
	}
	return true;
}

/**
 * 功能：检查是否为邮箱地址
 * 用法：checkEmail(form.txtName.value)
 * 说明：已经带有非空验证
 */
function checkEmail(value)
{
	if(value == '')
	{
		return false;
	}
	
	var i = value.indexOf("@"); 

	if(parseInt(i) > 1)
	{
		return true;
	}
	
	return false;
}

/**
 * 判断身份证号码的合法性
 * @param strIdNo 身份证号码
 * @return boolean 合法 - true; 不合法 - false
 */
function isValidIdCardNo(strIdNo)
{
	strIdNo = trim(strIdNo);
	var length = strIdNo.length;
	var birthday;

	if (length == 0)
	{
		return true;
	}

	if (length != 15 && length!= 18)
	{
		return false;
	}

	for (var i = 0; i < length - 1; i++)
	{
		if (strIdNo.charAt(i) < '0' || strIdNo.charAt(i) > '9')
		{
			return false;
		}
	}
	if (length == 15 && (strIdNo.charAt(i) < '0' || strIdNo.charAt(i) > '9'))
	{
		return false;
	}
	
	return true;
}

/**
 * 功能：检查是否为整型
 * 用法：checkInteger(form.txtName.value)
 * 说明：
 */
function checkInteger(inputVal) 
{
	var inputStr = inputVal.toString();
	
	for (var i = 0; i < inputVal.length; i ++ ) 
	{
		var oneChar = inputVal.charAt(i);
		if (i == 0 && (oneChar == "+" || oneChar == "-") )
		{
			if (inputVal.length == 1 )
			{
				return false;
			}else{ 
				continue;
			}
		}
		
		if(oneChar == ",")
		{
			continue;
		}
		
		if (oneChar < "0" || oneChar > "9")
			return false;
	}
	return true;
}

/**
 * 功能：检查是否为正整型
 * 用法：checkPosInteger(form.txtName.value)
 * 说明：
 */
function checkPosInteger(inputVal) 
{
	var inputStr = inputVal.toString();
	
	for (var i = 0; i < inputVal.length; i ++ ) 
	{
		var oneChar = inputVal.charAt(i);
		if (i == 0 && oneChar == "+")
		{
			if (inputVal.length == 1 )
			{
				return false;
			}else{ 
				continue;
			}
		}
		
		if(oneChar == ",")
		{
			continue;
		}
		
		if (oneChar < "0" || oneChar > "9")
			return false;
	}
	return true;
}

/**
 * 功能：检查是否是浮点数
 * 用法：checkNumber(form.txtName.value)
 * 说明：
 */
function checkNumber(input) 
{
	var oneDecimal = false;
	var inputVal = input.toString();
	
	for (var i = 0; i < inputVal.length; i ++ ) 
	{
		var oneChar = inputVal.charAt(i);
		
		if (i == 0 && (oneChar == "+" || oneChar == "-") )
		{
			if (inputVal.length == 1 )
			{
			 	return false;
			}
			else continue;
		}
		if (oneChar == "." && !oneDecimal) {
			oneDecimal = true;
			continue;
		}
		if(oneChar == ",")
		{
			continue;
		}
		if (oneChar < "0" || oneChar > "9")
		{
		 	return false;
		}
	}
	return true;
}

/**
 * 功能：检查是否是正浮点数
 * 用法：checkPosNumber(form.txtName.value)
 * 说明：
 */
function checkPosNumber(input) 
{
	var oneDecimal = false;
	var inputVal = input.toString();
	
	for (var i = 0; i < inputVal.length; i ++ ) 
	{
		var oneChar = inputVal.charAt(i);
		
		if (i == 0 && oneChar == "+")
		{
			if (inputVal.length == 1 )
			{
			 	return false;
			}
			else continue;
		}
		if (oneChar == "." && !oneDecimal) {
			oneDecimal = true;
			continue;
		}
		if(oneChar == ",")
		{
			continue;
		}
		if (oneChar < "0" || oneChar > "9")
		{
		 	return false;
		}
	}
	return true;
}

/**
 * 功能：检查是否为日期
 * 用法：checkDate(form.txtName.value)
 * 说明：返回值：0：不是日期  1：是日期;时间格式为2003-12-12
 */
function checkDate(datestr)
{
	var lthdatestr
	if (datestr != "")
		lthdatestr= datestr.length ;
	else
		lthdatestr=0;
		
	var tmpy="";
	var tmpm="";
	var tmpd="";
	//var datestr;
	var status;
	status=0;
	if ( lthdatestr== 0)
		return 0

	
	for (i=0;i<lthdatestr;i++)
	{	if (datestr.charAt(i)== '-')
		{
			status++;
		}
		if (status>2)
		{
			//alert("Invalid format of date!");
			return 0;
		}
		if ((status==0) && (datestr.charAt(i)!='-'))
		{
			tmpy=tmpy+datestr.charAt(i)
		}
		if ((status==1) && (datestr.charAt(i)!='-'))
		{
			tmpm=tmpm+datestr.charAt(i)
		}
		if ((status==2) && (datestr.charAt(i)!='-'))
		{
			tmpd=tmpd+datestr.charAt(i)
		}

	}
	year=new String (tmpy);
	month=new String (tmpm);
	day=new String (tmpd)
	//	alert(year);
	//	alert(month);
	//	alert(day);
	//tempdate= new String (year+month+day);
	//alert(tempdate);
	if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2))
	{
		//alert("Invalid format of date!");
		return 0;
	}
	if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) )
	{
		//alert ("Invalid month or day!");
		return 0;
	}
	if (!((year % 4)==0) && (month==2) && (day==29))
	{
		//alert ("This is not a leap year!");
		return 0;
	}
	if ((month<=7) && ((month % 2)==0) && (day>=31))
	{
		//alert ("This month is a small month!");
		return 0;
	
	}
	if ((month>=8) && ((month % 2)==1) && (day>=31))
	{
		//alert ("This month is a small month!");
		return 0;
	}
	if ((month==2) && (day==30))
	{
		alert("The Febryary never has this day!");
		return 0;
	}
	return 1;
}

/**
 * 功能：校验时间 
 * 用法：checkTime(form.txtName)
 * 说明：时间的格式标准格式为"20:12:13"
 */
function checkTime(inputElement)
{
	var strTime = trim(inputElement.value);
	var len = strTime.length;
	if (strTime.charAt(len-1) == ':') 
		strTime = strTime.substring(0, len-1);
	var strSplitTime = strTime.split(":");
	var lenSplit = strSplitTime.length;
	for (var i=0; i < lenSplit; i++)
	{
		if (strSplitTime[i].length > 2) return false;
		if (isNaN(strSplitTime[i])) return false;
	}
	if (lenSplit == 1) {
		strTime = strTime + ":00";
	} 
	inputElement.value = strTime;
	var time = new Date("10 Jun, 2003 "+ strTime);
	if (isNaN(time)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 功能：比较两个时间form.tmBegin.value应该在form.tmEnd.value之前
 * 用法：compareTime(form.tmBegin.value,form.tmEnd.value, strAlert)
 * 说明：日期的标准格式为"20:12:13",strAlert为出错提示信息
 */
function compareTime(tmBegin, tmEnd, strAlert)
{
	var dtBegin = new Date("10 Jun, 2003 " + tmBegin.value);
	var dtEnd = new Date("10 Jun, 2003 " + tmEnd.value);
	if ( dtBegin.getTime() >= dtEnd.getTime() )
	{
		alert(strAlert);
		return false;
	} else {
		return true;
	}
}

/**
 * 功能：比较两个日期先后form.dtStart.value应该在form.dtEnd.value之前
 * 用法：checkCompareDate(form.dtStart.value,form.dtEnd.value)
 * 说明：如果form.dtStart.value>form.dtEnd.value，返回false,否则返回true;日期的标准格式为"2003-12-13"
 */
function checkCompareDate(dtStart,dtEnd)
{
	var temp,s;
	temp=new String(dtStart);

	s = new String("");
	for(var i=0;i<=temp.length-1;i++)
	{
		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
			s=s+"/";
		else
		{

			s=s+temp.charAt(i);
		}
	}
	dtOne=new Date(s);
	
	temp=new String(dtEnd);
	s=new String("");
	for(var i=0;i<=temp.length-1;i++)
	{
		if(temp.charAt(i)=="-" || temp.charAt(i)=="/")
			s=s+"/";
		else
		{
			s=s+temp.charAt(i);
		}
	}
	dtTwo=new Date(s);

	if(dtOne.valueOf()>dtTwo.valueOf())
	{
		alert("错误：\n起始日期不能大于终止日期！");
		return false;
	}
	return true;	
}

/**
 * 功能：比较两个数字大小
 * 用法：formatDrpNumber(form.txtName1.value,form.txtName2.value)
 * 说明：如果form.txtName1.value>form.txtName2.value,返回false,否则返回true
 */
function checkCompareNum(dStart,dEnd)
{
	var v1 = parseFloat(dStart);
	var v2 = parseFloat(dEnd);
	
	if(v1 > v2)
	{
		return false;
	}
	return true;
}

/**
 * 功能：返回一个数组，例如：strData="[5,3]",则返回'[','5','3',']'这样的数组
 * 用法：analyze(strData)
 * 说明：例如：strData="[5,3]",则返回'[','5','3',']'这样的数组
 */
 function analyze(strData)
 {
 	var str = new String(strData);
 	var arry = new Array();
 	var index = str.indexOf(",");
 	
 	arry[0] = str.substring(0,1);
 	arry[1] = str.substring(1,index);
 	arry[2] = str.substring(index+1,str.length-1);
 	arry[3] = str.substring(str.length-1,str.length);
 	
 	return arry;
 }

/********************* 日期相关函数 Start *******************/
/**
 * 功能：返回当前日期
 */
function showDate()
{
  var d = new Date();
  var s = d.getYear() + "-";
  s += (d.getMonth() + 1) + "-";
  s += d.getDate();
  return(s);
}

/**
 * 功能：返回当前日期中文
 */
function showDateChinese()
{
  var d = new Date();
  var s = d.getYear() + "年";
  s += (d.getMonth() + 1) + "月";
  s += d.getDate() + "日";
  return(s);
}

/**
 * 功能：返回当前时间
 */
function showTime()
{
  var c = ":";
  var d = new Date();
  var s = d.getHours() + c;
  s += d.getMinutes() + c;
  s += d.getSeconds();
  return(s);
}

/**
 * 功能：检查是否是日期型
 * 用法：validateDate(form.txtName, 日期表现形式) 
 * 说明：日期表现形式可为:"2003-12-13"、"2003年12月13日"、"12/13/2003"等，日期中间的间隔符随便写,日期的位置也可以为:"年月日"或"月日年"的形式
 */
function validateDate(dateInput, datePattern) 
{
   var bValid = true;
   var focusField = null;
   var i = 0;
   
   if (dateInput == null) return bValid;
   var dateValue = dateInput.value;
   if ((dateInput.type == 'text' ||
        dateInput.type == 'textarea') &&
       (dateValue.length > 0) &&
       (datePattern.length > 0)) {
       var MONTH = "MM";
       var DAY = "dd";
       var YEAR = "yyyy";
       var orderMonth = datePattern.indexOf(MONTH);
       var orderDay = datePattern.indexOf(DAY);
       var orderYear = datePattern.indexOf(YEAR);
	   //alert(orderYear);
	   //alert(orderMonth);
	   //alert(orderDay);
       if ((orderDay < orderYear && orderDay > orderMonth)) {
           var iDelim1 = orderMonth + MONTH.length;
           var iDelim2 = orderDay + DAY.length;
           var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
           var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
           if (iDelim1 == orderDay && iDelim2 == orderYear) {
              dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
           } else if (iDelim1 == orderDay) {
              dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
           } else if (iDelim2 == orderYear) {
              dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
           } else {
              dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
           }
           var matched = dateRegexp.exec(dateValue);
           if(matched != null) {
              if (!isValidDate(matched[2], matched[1], matched[3])) {
                 bValid =  false;
              }
           } else {
              bValid =  false;
           }
       } else if ((orderMonth < orderYear && orderMonth > orderDay)) {
           var iDelim1 = orderDay + DAY.length;
           var iDelim2 = orderMonth + MONTH.length;
           var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
           var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
           if (iDelim1 == orderMonth && iDelim2 == orderYear) {
               dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
           } else if (iDelim1 == orderMonth) {
               dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
           } else if (iDelim2 == orderYear) {
               dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
           } else {
               dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
           }
           var matched = dateRegexp.exec(dateValue);
           if(matched != null) {
               if (!isValidDate(matched[1], matched[2], matched[3])) {
                   bValid =  false;
                }
           } else {
               bValid =  false;
           }
       } else if ((orderMonth > orderYear && orderMonth < orderDay)) {
           var iDelim1 = orderYear + YEAR.length;
           var iDelim2 = orderMonth + MONTH.length;
           var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
           var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
           if (iDelim1 == orderMonth && iDelim2 == orderDay) {
               dateRegexp = new RegExp("^(\\d{4})(\\d{2})(\\d{2})$");
           } else if (iDelim1 == orderMonth) {
               dateRegexp = new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$");
           } else if (iDelim2 == orderDay) {
               dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$");
           } else {
               dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$");
           }
           var matched = dateRegexp.exec(dateValue);
           if(matched != null) {
               if (!isValidDate(matched[3], matched[2], matched[1])) {
                    bValid =  false;
               }
           } else {
                bValid =  false;
           }
       } else {
           bValid =  false;
       }
   }
 
   return bValid;
}

/**
 * 功能： 查看年,月,日是否在正常范围里面
 * 用法：isValidDate(day, month, year)
 * 说明：年,月,日的范围是现实生活中的范围
 */
function isValidDate(day, month, year) 
{
    if (month < 1 || month > 12) {
            return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
        (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
                   (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}

/**
 * 功能：计算日期=dtBase+lTerm后的日期,lTerm为毫秒级时间
 * 用法：getTime("2003-12-13",lTerm)
 * 说明：lTerm可以为正整数也可以为负整数;返回一个"2003-12-13"格式的日期
 */
function getTime(dtBase,lTerm)
{
	var vDate = analyzeDate(dtBase);
	var calendar = new Date(vDate[0],vDate[1]-1,vDate[2],0,0,0);
	
	if(checkInteger(lTerm) == false)
	{
		return "";
	}
	
	//一天的毫秒数
	var lOneTime = 24*3600*1000;

	var lSumTime = lTerm*lOneTime;

	//取得日期的毫秒数
	lSumTime = calendar.getTime()+lSumTime;

	var newCalendar = new Date();
	newCalendar.setTime(lSumTime);

	return getDateString(newCalendar);
}

/**
 * 功能：计算工作日=dtBase+lTerm后的日期,lTerm为毫秒级时间(整数)
 * 用法：getTime("2003-12-13",lTerm)
 * 说明：lTerm可以为正整数也可以为负整数;返回一个"2003-12-13"格式的日期;工作日期是不算周末的;
 */
function getWorkTime(dtBase,lTerm)
{
	var vDate = analyzeDate(dtBase);
	var calendar = new Date(vDate[0],vDate[1]-1,vDate[2],0,0,0);
	
	if(checkInteger(lTerm) == false)
	{
		return "";
	}
	
	//一天的毫秒数
	var lOneTime = 24*3600*1000;	
	
	if(lTerm == 0)
	{
		var day = calendar.getDay();
		//如果是周末
		if(day == 6 || day == 0)
		{
			lTerm = 1;
		}
	}
	
	while(lTerm != 0)
	{
		var lTime = calendar.getTime();
		
		if(lTerm < 0)
		{
			lTime = lTime - lOneTime;
		}else{
			lTime = lTime + lOneTime;
		}
		calendar.setTime(lTime);
		
		var day = calendar.getDay();
		//如果不是周末
		if(day != 6 && day != 0)
		{
			if(lTerm < 0) 
			{
				lTerm ++;
			}else{
				lTerm --;
			}
		}
	}
	
	return getDateString(calendar);
}

/**
 * 功能：将"2003-12-13"型日期用"年、月、日"的一个数组保存起来
 * 用法：analyzeDate(dtBase)
 * 说明：返回的数组第一个元素存放的是日，第二个是月,第三个是年;
 */
function analyzeDate(dtBase)
{
	var vDate = new Array(3);
	var strDate = new String(dtBase);
	var index = strDate.indexOf("-");

	var i=0;
	while(index != -1)
	{
		vDate[i++] = strDate.substring(0,index);
		strDate = strDate.substring(index+1,strDate.length);

		index = strDate.indexOf("-");
	}

	vDate[i] = strDate;

	return vDate;
}

/**
 * 功能：将一个calendar对象转换个"2003-12-13"形式的日期
 * 用法：getDateString(calendar)
 * 说明：返回"2003-12-13"形式的日期
 */
function getDateString(calendar)
{
	var year = calendar.getYear();
	var month = calendar.getMonth()+1;
	var date = calendar.getDate();

	if(year < 100)
	{
		year += 1900;
	}

	var sMonth = new String(month);
	if(sMonth.length == 1)
	{
		sMonth = "0"+sMonth;
	}

	var sDate = new String(date);
	if(sDate.length == 1)
	{
		sDate = "0"+sDate;
	}

	return year+"-"+sMonth+"-"+sDate;
}

/********************* 日期相关函数 End ********************/
/**********************************************************/
/**
 * 功能：使对象变成所指定的颜色
 * 用法：myColor(form.txtName)
 * 说明：【辅助函数，出错时改变对象颜色"#FFFFCC"】
 */
function myMistakeColor(object)
{
	if(object != null)
	{
		object.style.backgroundColor = "#FFFFCC";
	}
}
/**
 * 功能：使对象变成所指定的颜色
 * 用法：myColor(form.txtName)
 * 说明：【辅助函数，不出错时改变对象颜色"#FFFFFF"】
 */
function myRigthColor(object)
{
	if(object != null)
	{
		object.style.backgroundColor = "#FFFFFF";
	}
}
/**********************************************************/


/**
 * 去除字符串两端的空格
 * @param strSrc 要去除空格的字符串
 * @return 去除空格后的字符串
 */
function trim(strSrc) 
{
   if (strSrc == null) 
   {
      return "";
   }
   return strSrc.replace(/(^\s+)|(\s+$)/g,"");
}


/**
 * 计算字符串的实际长度(一个中文字算两个字符)
 * @param strSrc 要计算的字符串
 * @return 实际长度(一个中文字算两个字符)
 */
function checkValueLength(strSrc) 
{
 	var lthstr
	if (strSrc != "")
		lthstr= strSrc.length ;
	else
		lthstr=0;
		
	if ( lthstr== 0)
		return 0

	
	for (i=0;i<lthstr;i++)
	{	if (checkNotChinese(strSrc.charAt(i)))
		{
			lthstr++;
		}
	}
	
	return lthstr;
}

/**
 * 判断是否只有一个checkbox选中
 * @param elem 要判断的checkbox
 * @return 返回一个选中的checkbox的值
 */
function updateRecord(elem) 
{
	var t = 0;
	var returnValue = "";
	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		if(elem.checked == true)
		{
			returnValue = elem.value;			
			t++;
		}
	}else{
		for(var i = 0; i < elem.length; i++)
		{
			if(elem[i].checked == true)
			{
				returnValue = elem[i].value;				
				t++;
			}
		}
	}	
	
	if(t < 1){
		alert("至少选择一条操作记录！");
		return;
	}
	if(t > 1){
		alert("一次只可以操作一条记录！");
		return;
	}

	return returnValue;
}

/**
 * 删除操作的专用方法
 * @param elem 要判断的checkbox
 * @return 返回一个选中的checkbox的值的拼串
 */
function deleteRecord(elem)
{
	var lean = false;
	var returnValue = "('";

	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		if(elem.checked == true)
		{
			returnValue += elem.value + "','";
			lean = true;
		}
	}else{
		for(var i = 0; i < elem.length; i++)
		{
			if(elem[i].checked == true)
			{
				returnValue += elem[i].value + "','";
				lean = true;
			}
		}
	}

	returnValue = returnValue.substr(0,returnValue.length - 2) + ")";   
	
	if(!lean){
		alert("至少选择一条删除记录！"); 
		return null;
	}

	if (!confirm("确定要删除这些记录吗?"))
		return null;
	return returnValue;
}

/**
 * 校验整型
 *	
 */
function checkInteger(pageField, fieldCaption){	
	str = "document.forms[0]."+pageField +".value" ;
	streval =  eval(str);
	str2 = "document.forms[0]."+pageField;
	streval2 =  eval(str2);
	inputStr = streval.toString();
	for (var i = 0; i < inputStr.length; i++) {
		var oneChar = inputStr.charAt(i);
		if (( oneChar < '0' || oneChar > '9')) {
			if (oneChar != '.') {
				if (fieldCaption != null && fieldCaption != "") {
					alert(fieldCaption + " 必须是数字!");
					streval2.select();
					streval2.focus();
					return false;
				} else {
					return false;
				}
			}
		}
	}
	return true;
}


/**
 * 判断是否为空方法
 * @param pageField 要判断的field
 * @param fieldCaption 要判断的field名
 * @return 
 */
function isNull(pageField, fieldCaption)
{
	var str = pageField.value;
	str = str.replace(/[ ]/g,""); 
	if ((str == null)||(str == "")) {
		if (fieldCaption != null && fieldCaption != "") {
			alert("Please enter " + fieldCaption + "!");
			try {
				pageField.select();
				pageField.focus();
			} catch (e) {
			}
		}
		return true;
	}
	return false;
}
// 隐藏/显示
function show_hideUserInf(id)
{
	if(document.all("userInf"+id).style.display=="")
	{
		document.all("userInf"+id).style.display = "none";
		return;
	}
	document.all("userInf"+id).style.display = "";
}

/**
 * 附件查看
 *
 * @param filename 
 * @param fileUrl 
 * @param filetype 
 * @param contenttype
 */
function viewAttach(filename,fileUrl,filetype,contenttype,workflowname) {
	/*if ((contenttype == "application/vnd.ms-excel") || (contenttype == "application/msword")) {
		filename = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.length);
		newwindow = window.open("../pub/OfficeControl.do?operate=toView&operPage=off_attachedit&option=read&type=" + filetype + "&filename=" + filename + "&nextfilename=" + filename + "&fileUrl=" + fileUrl + "&stepid=" + stepid + "&workflowname=" + workflowname,"","width=1000,height=800,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
		                                                 
		if (document.all){
			newwindow.moveTo(0,0)
			newwindow.resizeTo(screen.width,screen.height)
		}
	} else {*/
		winStyle = "";
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "附件查看", winStyle);
	//}
}

/**
 * 模板附件编辑
 *
 * @param filename 
 * @param fileUrl 
 * @param filetype 
 * @param contenttype
 */
function viewAttach4(filename,fileUrl,filetype,contenttype,option,domainid,userdomainid) {
	if (domainid == userdomainid && ((contenttype == "application/vnd.ms-excel") || (contenttype == "application/msword"))) {
		filename = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.length);
		newwindow = window.open("../pub/OfficeControl.do?operate=toViewAttach&operPage=off_attachedit&option="+option+"&type=" + filetype + "&filename=" + filename + "&nextfilename=" + filename + "&fileUrl=" + fileUrl,"","width=1000,height=800,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
		                                                 
		if (document.all){
			newwindow.moveTo(0,0)
			newwindow.resizeTo(screen.width,screen.height)
		}
	} else {
		winStyle = "";
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "附件查看", winStyle);
	}
}

/**
 * 流程附件查看
 *
 * @param filename 
 * @param fileUrl 
 * @param filetype 
 * @param contenttype
 */
function viewAttach3(filename,fileUrl,filetype,contenttype,stepid,workflowname) {
	if ((contenttype == "application/vnd.ms-excel") || (contenttype == "application/msword")) {
		filename = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.length);
		url = "../pub/OfficeControl.do?operate=toViewAttach&operPage=off_attachedit&type=" + filetype + "&filename=" + filename + "&nextfilename=" + filename + "&fileUrl=" + fileUrl + "&stepid=" + stepid + "&workflowname=" + workflowname;

		if (stepid == 0)
		{
			url += "&option=read";
		}
		newwindow = window.open(url,"","width=1000,height=800,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
		                                                 
		if (document.all){
			newwindow.moveTo(0,0)
			newwindow.resizeTo(screen.width,screen.height)
		}
	} else {
		winStyle = "";
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "附件查看", winStyle);
	}
}

/**
 * 附件查看(文件阅览，文件快递)
 *
 * @param filename 
 * @param fileUrl 
 * @param filetype 
 * @param contenttype
 * @param recid
 */
function viewAttach1(filename,fileUrl,filetype,contenttype,recid) {
/*	if ((contenttype == "application/vnd.ms-excel") || (contenttype == "application/msword")) {
		filename = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.length);
		newwindow = window.open("../pub/OfficeControl.do?operate=toEditFile&operPage=off_attachedit&option=read&type=" + filetype + "&fileid=" + recid + "&filename=" + filename + "&nextfilename=" + filename + "&fileUrl=" + fileUrl,"","width=1000,height=800,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
		if (document.all){
			newwindow.moveTo(0,0)
			newwindow.resizeTo(screen.width,screen.height)
		}
	} else {*/
		winStyle = "";
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype + "&recid="+recid, "附件查看", winStyle);
	//}
	if (filetype == "DocFileSend")
	{
		document.location.reload();
	}
/*	var ftpadd="../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype + "&recid="+recid;
	document.forms[0].action = ftpadd;// + filename;
	document.forms[0].target="blank";
	document.forms[0].submit();*/
}

/**
 * 附件查看(首页)
 *
 * @param filename 
 * @param fileUrl 
 * @param filetype 
 * @param contenttype
 * @param recid
 */
function viewAttach2(filename,fileUrl,filetype,contenttype,recid) {
/*	if ((contenttype == "application/vnd.ms-excel") || (contenttype == "application/msword")) {
		filename = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.length);
		newwindow = window.open("./pub/OfficeControl.do?operate=toEditFile&operPage=off_attachedit&option=read&type=" + filetype + "&fileid=" + recid + "&filename=" + filename + "&nextfilename=" + filename + "&fileUrl=" + fileUrl,"","width=1000,height=800,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
		if (document.all){
			newwindow.moveTo(0,0)
			newwindow.resizeTo(screen.width,screen.height)
		}
	} else {*/
		winStyle = "";
		a=window.open("./GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype + "&recid="+recid, "附件查看", winStyle);
//	}
	if (filetype == "FzFileSend")
	{
		document.location.reload();
	}
}

/**
 * 选择部门
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function findDept(code,name,roleid,type)
{
	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no';
	var url = '../system/Dept_main.jsp?type='+type+'&code='+code.value+'&name='+name.value;   
	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择部门
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function findDeptDomain(code,name,roleid,type,domainId)
{
	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no';
	if(domainId==undefined){
		domainId = "";
	}
	var url = '../system/Dept_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&domainId='+domainId;   
	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择部门(表单设计中使用，选择一个部门)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getdeptlist1(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_HIDDEN"); 

	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no'; 
	var url = '../system/Dept_main.jsp?type=one&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择部门(表单设计中使用，选择一个部门)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getdeptlist3(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_hidden"); 

	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no'; 
	var url = '../system/Dept_main.jsp?type=one&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype); 

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择部门(表单设计中使用，选择一个部门)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getdeptlist6(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_hidden"); 

	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no'; 
	var url = '../system/Dept_main.jsp?type=one&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype); 

	if(rn != null){
		code.value = rn.name;
		name.value = rn.name;
	}
}
/**
 * 选择部门(表单设计中使用，选择多个部门)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getdeptlist2(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_HIDDEN"); 

	var stype = 'dialogWidth:570px;dialogHeight:560px;status:no'; 
	var url = '../system/Dept_main.jsp?code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param type 是否可以多选one:只能选一个
 * @param roleid 角色ID
 * @return 
 */
function findUser(code,name,roleid,type)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value + '&roleid='+roleid;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员(表单设计中使用，选择一个人员)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getuserlist1(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_HIDDEN"); 

	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no'; 
	var url = '../system/Person_main.jsp?type=1&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员(表单设计中使用，选择一个人员)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getuserlist3(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_hidden"); 

	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no'; 
	var url = '../system/Person_main.jsp?type=1&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);  

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员(表单设计中使用，选择一个人员)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getuserlist6(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_hidden"); 

	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no'; 
	var url = '../system/Person_main.jsp?type=1&code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);  

	if(rn != null){
		code.value = rn.name;
		name.value = rn.name;
	}
}

function setHiddenValue()
{
	var obj = event.srcElement;			
	if(obj.tagName == 'INPUT')
	{
		eval("document.all." + obj.name + "_hidden").value = obj.value;
	}

	if(obj.tagName == 'SELECT')
	{
		eval("document.all." + obj.name + "_hidden").value = obj.value;
	}
}


/**
 * 选择人员(表单设计中使用，选择多个人员)
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 */
function getuserlist2(ctrlobj)
{

	var  name = eval("document.forms[0]." + ctrlobj); 
	var  code = eval("document.forms[0]." + ctrlobj + "_HIDDEN"); 

	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no'; 
	var url = '../system/Person_main.jsp?code='+code.value+'&name='+name.value;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择有权限的人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param moduleflag 模块名
 * @param submodflag 栏目名
 * @param funcflag 功能名
 * @return 
 */
function findFuncUser(code,name,type,moduleflag,submodflag,funcflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&moduleflag='+moduleflag+'&submodflag='+submodflag+'&funcflag='+funcflag;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 出去所有文本框输入内容头尾的空格
 * @param pageField 要判断的field
 * @param fieldCaption 要判断的field名
 * @return 
 */
function trimAllText(){
    var i = document.forms.length;
	for (var j = 0 ; j < i ; j++) {
		var m = document.forms[j].elements.length;
		for (var n = 0 ; n < m ; n++) {
			if (document.forms[j].elements[n].type == 'text') {
				document.forms[j].elements[n].value = trim(document.forms[j].elements[n].value );
			}
		}
	}
}

// 转FzFileSend
function sendfile(formtype, filetype) {
	var form = document.forms[0];	
	var recid = updateRecord(form.recid);
	if (recid == null)  return;
	// 选择人员
	a=findUser(form.sender,form.sendername,"","");
	if (form.sender.value != null && form.sender.value != "") {
		form.action = "../gwgl/FzFileSend.do?operate=savefromothers&operPage=fz_FileSend_list_send&fzFileSend.type="+formtype+"&fzFileSend.title="+recid.split("@")[1]+"&fzFileSend.sourceId="+recid.split("@")[0]+"&fzFileSend.maxSeq="+recid.split("@")[2]+"&fzFileSend.userid="+form.sender.value+"&fzFileSend.username="+form.sendername.value+"&formtype="+formtype+"&filetype="+filetype; 
		form.submit(); 
	}
}

// 转FzFileSend
function viewsendfile(recid,formtype, filetype) {
	var form = document.forms[0];	
	var title = form.title.value;
	var maxSeq = form.maxSeq.value;
	// 选择人员
	a=findUser(form.sender,form.sendername,"","");
	if (form.sender.value != null && form.sender.value != "") {
		form.action = "../gwgl/FzFileSend.do?operate=savefromothers&operPage=fz_FileSend_list_send&fzFileSend.type="+formtype+"&fzFileSend.title="+title+"&fzFileSend.sourceId="+recid+"&fzFileSend.maxSeq="+maxSeq+"&fzFileSend.userid="+form.sender.value+"&fzFileSend.username="+form.sendername.value+"&formtype="+formtype+"&filetype="+filetype; 
		form.submit(); 
	}
}

// 转FzFileView
function tofileview(formtype, filetype) {
	var form = document.forms[0];	
	//var recid = updateRecord(form.recid);
	
	var t = 0;
	var returnValue = "";
	var sfkey = "";
	if(form.recid == undefined) {
		return;
	}
	if(isNaN(form.recid.length)){
		if(form.recid.checked == true)
		{
			returnValue = form.recid.value;			
			t++;
		}
	}else{
		for(var i = 0; i < form.recid.length; i++)
		{
			if(form.recid[i].checked == true)
			{
				returnValue = form.recid[i].value;	
				sfkey = document.all['sfkeyvalue'][i].innerHTML;					
				t++;
			}
		}
	}	
	
	if(t < 1){
		alert("至少选择一条操作记录！");
		return;
	}
	if(t > 1){
		alert("一次只可以操作一条记录！");
		return;
	}
	if (returnValue == null)  return;
	// 选择类型
	a=window.showModalDialog("../grms/FzFileView.do?operate=toviewfile&operPage=fz_fileview_type&fzFileView.title="+returnValue.split("@")[1]+"&fzFileView.maxSeq="+returnValue.split("@")[2]+"&fzFileView.sfkey="+sfkey+"&formtype="+formtype+"&filetype="+filetype,'','dialogWidth:500px;dialogHeight:300px;status:no');	
}
function openfullwindow(url) {
	winStyle = "width=750,height=250,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no";
	newwindow=window.open(url, "", winStyle);
	if (document.all){
		newwindow.moveTo(0,0)
		newwindow.resizeTo(screen.width,screen.height)
	}
}
var bCheck = false;
//全选或取消当前页中List列表框
function checkAll() {
	var currentForm = document.forms[0];
	bCheck = !bCheck;
	var ChkEls = document.getElementsByName("recid");
	for( var i=0;i<ChkEls.length;i++ ) {
		ChkEls.item(i).checked = bCheck;
	}
	if( bCheck )
		currentForm.idCheckAll.alt = "全部取消";
	else
		currentForm.idCheckAll.alt = "全部选中";
}
function searchfile(actionname,filename) 
{
	var form = document.forms[0];	
	form.action = "../gwgl/"+actionname+".do?operate=searchDoc&operPage="+filename+"&search=3";
	form.submit(); 
}
function toarchive() {
	var form = document.forms[0];
	var actionvalue = form.refreshaction.value;
	var formtype = form.formtype.value;
	var fileid = form.fileid.value;
	window.open(actionvalue+"_print&toview=3&archive=true&fileid="+fileid+"&formtype="+formtype,"","width=750,height=250,resizable=yes,scrollbars=yes,menubar=no,status=no,toolbar=no");
}
function toDocument(fileid,maxSeq,filetype,formtype,title,signDate,keyWord,sfkey){
	var form = document.forms[0];
	form.tablehtml.value=document.all['tableid'].innerHTML;
	form.loghtml.value=flowlog.document.all['logtable'].innerHTML;
	form.action="../archive/DaDocument.do?operate=toinsert&operPage=document_edit&fileid="+fileid+"&fileseq="+maxSeq+"&formtype="+formtype+"&filetype="+filetype+"&daDocument.title="+title+"&daDocument.strSignDate="+signDate+"&daDocument.keyWord="+keyWord+"&daDocument.sfkey="+sfkey;
	form.submit();
}
/**
 * 判断是否只有一个checkbox选中
 * @param elem 要判断的checkbox
 * @return 返回一个选中的checkbox的值
 */
function updateRecordhidd(elem,hiddenvalue) 
{
	var t = 0;
	var returnValue = "";
	var hiddenReturn = "";
	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		if(elem.checked == true)
		{
			returnValue = elem.value;			
			hiddenReturn = hiddenvalue.value;	
			t++;
		}
	}else{
		for(var i = 0; i < elem.length; i++)
		{
			if(elem[i].checked == true)
			{
				returnValue = elem[i].value;	
				hiddenReturn = hiddenvalue[i].value;				
				t++;
			}
		}
	}	
	
	if(t < 1){
		alert("至少选择一条操作记录！");
		return;
	}
	if(t > 1){
		alert("一次只可以操作一条记录！");
		return;
	}

	return new Array(returnValue,hiddenReturn);
}

/**
 * 选择记录操作的专用方法
 * @param elem 要判断的checkbox
 * @return 返回一个选中的checkbox的值的拼串
 */
function selectRecord(elem)
{
	var lean = 0;
	var returnValue = "('";

	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		alert("至少要选择2条记录合并!");
		return;
		/*if(elem.checked == true)
		{
			returnValue += elem.value + "','";
			lean = true;
		}*/
	}else{
		for(var i = 0; i < elem.length; i++)
		{
			if(elem[i].checked == true)
			{
				returnValue += elem[i].value + "','";
				lean++;
			}
		}
	}

	returnValue = returnValue.substr(0,returnValue.length - 2) + ")";   
	
	if(lean < 2){
		alert("至少要选择2条记录合并!"); 
		return null;
	}
	return returnValue;
}
// 排序
function lorder(strSelect,notselect)
{
	try {
		var selectnode = eval("currentForm."+strSelect+"1");
		var notselectnode = eval("currentForm."+notselect+"1");
		var currentForm = document.forms[0];
		if (currentForm.orderFild.value == strSelect)
		{
			if (currentForm.order.value == "asc")
			{
				currentForm.order.value = "desc";
				selectnode.src = "../images/orderdown.gif";
			} else {
				currentForm.order.value = "asc";
				selectnode.src = "../images/orderup.gif";
			}
		} else {
			currentForm.order.value = "asc";
			selectnode.src = "../images/orderup.gif";
		}
		currentForm.orderFild.value = strSelect;
	} catch (e) {
	}
	selectnode.style.display = "";
	notselectnode.style.display = "none";
	//alert(temp.src);
	//currentForm.submit();
}


/**  选择部门
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param domainId 域id (1：为空则查询当前用户所在域 2：为all则查询全局 3：如果要查询多个域请用","隔开 example:domainId1,domainId2)
 */
/**function findDept1(code,name,domainId,roleid,type)
{	
	var stype = 'dialogWidth:660px;dialogHeight:530px;status:no';
	var url = '../directoryInquiries/department/Dept_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&domainId='+ domainId;   

	var rn = window.open(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
} */

/**
 * 选择人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param type 是否可以多选one:只能选一个
 * @param roleid  按角色方式查询时角色ID (如果要查询多个角色请用","隔开 example:roleid1,roleid2 如果要查询所有，则为空)
 * @param domainId 域id (1：为空则查询当前用户所在域 2：为all则查询全局 3：如果要查询多个域请用","隔开 example:domainId1,domainId2)
 * @param extflag 如果为true 则表示所输入的domainId为排除在外的域id 
 * @return 
 */
/**function findUser1(code,name,domainId,roleid,type,extflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../directoryInquiries/person/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value + '&roleid='+roleid+'&domainId='+ domainId+ '&extflag='+ extflag;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
} */

/**
 * 选择有权限的人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param moduleflag 模块名
 * @param submodflag 栏目名
 * @param funcflag 功能名
 * @param domainId 域id (1：为空则查询当前用户所在域 2：为all则查询全局 3：如果要查询多个域请用","隔开 example:domainId1,domainId2)
 * @param extflag 如果为true 则表示所输入的domainId为排除在外的域id  
 * @return 
 */
/**function findFuncUser1(code,name,domainId,extflag,type,moduleflag,submodflag,funcflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../directoryInquiries/person/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&moduleflag='+moduleflag+'&submodflag='+submodflag+'&funcflag='+funcflag+ '&domainId='+ domainId;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}
 */
/**
 * 选择人员，部门，角色，工作属性
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param type 是否可以多选one:只能选一个
 * @param roleid  按角色方式查询时角色ID (如果要查询多个角色请用","隔开 example:roleid1,roleid2 如果要查询所有，则为空)
 * @param domainId 域id (1：为空则查询当前用户所在域 2：为all则查询全局 3：如果要查询多个域请用","隔开 example:domainId1,domainId2)
 * @param extflag 如果为true 则表示所输入的domainId为排除在外的域id 
 * @return 
 */
function findAll(code,name,domainId,roleid,type,extflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/all_main.jsp?type='+type+'&code='+code.value+'&name='+name.value + '&roleid='+roleid+'&domainId='+ domainId+ '&extflag='+ extflag;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param type 是否可以多选one:只能选一个
 * @param roleid 角色ID
 * @return 
 */
function findUsers(code,name,domainId,roleid,type,extflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value + '&roleid='+roleid+ '&domainId='+domainId;   

	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}


function findAllForArray(code,name,domainId,roleid,type,extflag)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/all_main.jsp?type='+type+'&code='+code.value+'&name='+name.value + '&roleid='+roleid+'&domainId='+ domainId+ '&extflag='+ extflag;   

	var rn = window.showModalDialog(url,'',stype);
	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}else{
	 return;
	}
	return new Array(rn.code,rn.name);
}

/**
 * 选择人员
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param type 是否可以多选one:只能选一个
 * @param domainId 域ID
 * @param filtrate 传入参数 根据com.kingtake.flowdesigner.bean.WorkFlowConstant中 地址薄中属性标识与属性id用*号隔开，不同的属性过滤条件用","隔开。
 	eg:WorkFlowConstant.ATTRIBUTE_ACTIVITY_USER*0001,WorkFlowConstant.ATTRIBUTE_ACTIVITY_ROLE*a123;
 
 * @return 
 */
function findAuthorizeUser(code,name,domainId,type,filtrate)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&domainId='+domainId+'&filtrate='+filtrate;
	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

/**
 * 选择人员,多deptflag 以判断弹出时默认是部门还是角色。
 *
 * @param code 要回写的field名
 * @param name 要回写的fieldname名
 * @param domainId 域ID
 * @param type 是否可以多选one:只能选一个
 * @param deptflag 部门标识。当'dept'时，默认弹出部门；当'role'时，默认弹出角色。
 * @param filtrate 传入参数 根据com.kingtake.flowdesigner.bean.WorkFlowConstant中 地址薄中属性标识与属性id用*号隔开，不同的属性过滤条件用","隔开。
 	eg:WorkFlowConstant.ATTRIBUTE_ACTIVITY_USER*0001,WorkFlowConstant.ATTRIBUTE_ACTIVITY_ROLE*a123;
 
 * @return 
 */
function findAuthorizeUser1(code,name,domainId,type,deptflag,filtrate)
{
	var stype = 'dialogWidth:760px;dialogHeight:530px;status:no';
	var url = '../system/Person_main.jsp?type='+type+'&code='+code.value+'&name='+name.value+'&domainId='+domainId+'&filtrate='+filtrate+'&deptflag='+deptflag;
	var rn = window.showModalDialog(url,'',stype);

	if(rn != null){
		code.value = rn.code;
		name.value = rn.name;
	}
}

var http_request = false;
var objselect = "";
var flag = "";

function send_request(url, xss) {
	send_request(url, xss, "true");
}
function send_request(url, xss, firstflag) {//初始化、指定处理函数、发送请求的函数
	http_request = false;
	this.objselect = xss;
	flag = firstflag;
	//开始初始化XMLHttpRequest对象
	if(window.XMLHttpRequest) { //Mozilla 浏览器
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {//设置MiME类别
			http_request.overrideMimeType('text/xml');
		}
	}
	else if (window.ActiveXObject) { // IE浏览器
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	
	if (!http_request) { // 异常，创建对象实例失败
		window.alert("不能创建XMLHttpRequest对象实例.");
		return false;
	}
	http_request.onreadystatechange = processRequest;
	// 确定发送请求的方式和URL以及是否同步执行下段代码
	http_request.open("GET", url, true);
	http_request.send(null);
}
// 处理返回信息的函数
function processRequest() 
{
	if (http_request.readyState == 4) 
	{ // 判断对象状态
		if (http_request.status == 200) 
		{ // 信息已经成功返回，开始处理信息
			var xmlobj = http_request.responseText;
			var xmldoc=new ActiveXObject("MSXML2.DOMDocument");
			xmldoc.loadXML(xmlobj);
			var oNodes = xmldoc.selectSingleNode("//xml");

			var xsselect = objselect.split("@");
			for (var i = 0; i < xsselect.length; i++)
			{
				xsselect[i] = eval('document.all.'+ xsselect[i]);
				// 删除原来的options
				for (var n = xsselect[i].options.length - 1; n > -1; n--) {
					xsselect[i].options[n] = null;
				}
			}
			if (oNodes != null && oNodes.childNodes.length > 0)
			{
				for (var j = 0; j < xsselect.length; j++)
				{
					if (oNodes.childNodes[j].childNodes[0] != null)
					{
						// 第一个option置空
						if (flag == "true")
						{			
							xsselect[j].options[0] = null;
						}
						// 循环加入option
						for (var n = 0; n < oNodes.childNodes[j].childNodes.length; n++) {
							var nodename = oNodes.childNodes[j].childNodes[n].attributes.getNamedItem("name").nodeValue;
							var nodecode = oNodes.childNodes[j].childNodes[n].attributes.getNamedItem("code").nodeValue;
							if (flag == "true")
							{
								xsselect[j].options[n + 1] = new Option(nodename, nodecode);
							} else {
								xsselect[j].options[n] = new Option(nodename, nodecode);
							}
						}
					}
				}
			}
		} else 
		{ //页面不正常
			alert("您所请求的页面有异常。");
		}
	}
}
/**
 * 功能：检查用户的权限,返回true表示有权限,false表示没有权限
 */
function getPurview(user,userlist)
{
	if (user == null || user == "" || userlist == null || userlist == "")
	{
		return false;
	}
	user = "," + user + ",";
	userlist = "," + userlist + ",";
	if (userlist.indexOf(user) == -1)
	{
		return false;
	}
	else
	{
		return true;
	}
}
//屏蔽右键
//document.oncontextmenu=new Function("event.returnValue=false");
//屏蔽Ctrl+A
//document.onselectstart=new Function("event.returnValue=false");

//ondragstart
/*
self.onError=null;
currentX = currentY = 0;
whichIt = null;
lastScrollX = 0; lastScrollY = 0;
NS = (document.layers) ? 1 : 0;
IE = (document.all) ? 1: 0;
<!-- STALKER CODE -->
function heartBeat() {
        if(IE) { diffY = document.body.scrollTop; diffX = document.body.scrollLeft; }
    if(NS) { diffY = self.pageYOffset; diffX = self.pageXOffset; }
        if(diffY != lastScrollY) {
                percent = .1 * (diffY - lastScrollY);
                if(percent > 0) percent = Math.ceil(percent);
                else percent = Math.floor(percent);
                                if(IE) document.all.floater.style.pixelTop += percent;
                                if(NS) document.floater.top += percent;
                lastScrollY = lastScrollY + percent;
    }
        if(diffX != lastScrollX) {
                percent = .1 * (diffX - lastScrollX);
                if(percent > 0) percent = Math.ceil(percent);
                else percent = Math.floor(percent);
                if(IE) document.all.floater.style.pixelLeft += percent;
                if(NS) document.floater.left += percent;
                lastScrollX = lastScrollX + percent;
        }
}

<!-- /STALKER CODE -->
<!-- DRAG DROP CODE -->
function checkFocus(x,y) {
        stalkerx = document.floater.pageX;
        stalkery = document.floater.pageY;
        stalkerwidth = document.floater.clip.width;
        stalkerheight = document.floater.clip.height;
        if( (x > stalkerx && x < (stalkerx+stalkerwidth)) && (y > stalkery && y < (stalkery+stalkerheight))) return true;
        else return false;
}

function grabIt(e) {
        if(IE) {
                whichIt = event.srcElement;
                while (whichIt.id.indexOf("floater") == -1) {
                        whichIt = whichIt.parentElement;
                        if (whichIt == null) { return true; }
            }
                whichIt.style.pixelLeft = whichIt.offsetLeft;
            whichIt.style.pixelTop = whichIt.offsetTop;
                currentX = (event.clientX + document.body.scrollLeft);
                   currentY = (event.clientY + document.body.scrollTop);
        } else {
        window.captureEvents(Event.MOUSEMOVE);
        if(checkFocus (e.pageX,e.pageY)) {
                whichIt = document.floater;
                StalkerTouchedX = e.pageX-document.floater.pageX;
                StalkerTouchedY = e.pageY-document.floater.pageY;
        }
        }
    return true;
}
function moveIt(e) {
        if (whichIt == null) { return false; }
        if(IE) {
            newX = (event.clientX + document.body.scrollLeft);
            newY = (event.clientY + document.body.scrollTop);
            distanceX = (newX - currentX);    distanceY = (newY - currentY);
            currentX = newX;    currentY = newY;
            whichIt.style.pixelLeft += distanceX;
            whichIt.style.pixelTop += distanceY;
                if(whichIt.style.pixelTop < document.body.scrollTop) whichIt.style.pixelTop = document.body.scrollTop;
                if(whichIt.style.pixelLeft < document.body.scrollLeft) whichIt.style.pixelLeft = document.body.scrollLeft;
                if(whichIt.style.pixelLeft > document.body.offsetWidth - document.body.scrollLeft - whichIt.style.pixelWidth - 20) whichIt.style.pixelLeft = document.body.offsetWidth - whichIt.style.pixelWidth - 20;
                if(whichIt.style.pixelTop > document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5) whichIt.style.pixelTop = document.body.offsetHeight + document.body.scrollTop - whichIt.style.pixelHeight - 5;
                event.returnValue = false;
        } else {
                whichIt.moveTo(e.pageX-StalkerTouchedX,e.pageY-StalkerTouchedY);
        if(whichIt.left < 0+self.pageXOffset) whichIt.left = 0+self.pageXOffset;
        if(whichIt.top < 0+self.pageYOffset) whichIt.top = 0+self.pageYOffset;
        if( (whichIt.left + whichIt.clip.width) >= (window.innerWidth+self.pageXOffset-17)) whichIt.left = ((window.innerWidth+self.pageXOffset)-whichIt.clip.width)-17;
        if( (whichIt.top + whichIt.clip.height) >= (window.innerHeight+self.pageYOffset-17)) whichIt.top = ((window.innerHeight+self.pageYOffset)-whichIt.clip.height)-17;
        return false;
        }
    return false;
}
function dropIt() {
        whichIt = null;
    if(NS) window.releaseEvents (Event.MOUSEMOVE);
    return true;
}
<!-- DRAG DROP CODE -->
if(NS) {
        window.captureEvents(Event.MOUSEUP|Event.MOUSEDOWN);
        window.onmousedown = grabIt;
         window.onmousemove = moveIt;
        window.onmouseup = dropIt;
}
if(IE) {
        document.onmousedown = grabIt;
         document.onmousemove = moveIt;
        document.onmouseup = dropIt;
}
if(NS || IE) action = window.setInterval("heartBeat()",1);*/
