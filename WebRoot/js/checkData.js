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
		alert("至少选择一条记录！"); 
		return;
	}

	return returnValue;
}