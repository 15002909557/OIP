/**
 * ���ܣ����ҳ�������
 * �÷���this.aa = new Array("txtName", "������ʾ��Ϣ�����磺txtName����Ϊ�գ���"[, "0.00"]);
 * ˵��������ĵ�һ��Ϊ'��Ҫ����Ŀؼ�������'������ĵڶ���Ϊ'�������ʾ��Ϣ'������ĵ������ѡ��Ϊ'�ÿؼ��ĳ�ʼֵ'�������˸�����ʼֵҲ��Ϊ�մ���
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
 * ���ܣ�����ʽ
 * �÷���this.aa = new Array("txtName", "������ʾ��Ϣ�����磺txtName��ʽ����ȷ��Ӧ��Ϊ�����ͣ���", new Function("varName", "this.dataformat='��Ҫ���ĸ�ʽ����'; return this[varName];"));
 * ˵������Ҫ���ĸ�ʽ���ͣ�date ���ڸ�ʽ��integer ���͡�posInteger �����͡�number �����͡�posNumber �������͡�email �������䡢IDCard ���֤��phone �绰����
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
			//����Ƿ�������
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
			//�������
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
			//���������
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
			//��鸡����
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
			//�����������
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
			//������֤��
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
			//��������ַ
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
			//���绰����
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
			//���绰����
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
 * ���ܣ����ؼ��ķ�Χ
 * �÷���this.aa = new Array("txtRate", "������ʾ��Ϣ�����磺˰��Ӧ��0��1֮�䣡��", "[0,1]");
 * ˵����'['��']'��ʾ���Ե��ڣ�[0,1]��ʾ0<=x<=1,��(0,1)��ʾ0<x<1��'#'��ʾ����飬���磺[#,1]��ʾx<=1,[0,#]��ʾx>=0��
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
 * ���ܣ���鳤��
 * �÷���this.aa = new Array("txtName", "������ʾ��Ϣ�����磺��½�ʺ�Ӧ��С����λ����", new Function("varName", "���ȶ���'; return this[varName];"));
 * ˵�������ȶ��壺��С���ȣ�this.minlength='��С�ĳ���ֵ����������'����󳤶ȣ�this.maxlength='���ĳ���ֵ����������'��
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
 * ���ܣ������ؼ�ֵ�Ƚ��Ⱥ�txtStartNameӦ����txtEndName֮ǰ
 * �÷���this.aa = new Array("txtStartName","txtEndName", "������ʾ��Ϣ�����磺dtStart������dtEnd֮��","�Ƚϵ�����")
 * ˵����txtStartName.valueӦ����txtEndName.value֮ǰ�������߶��������ڷŴ���ѡ�����ı�׼��ʽ��;�Ƚϵ�����Ϊ:����'date'������'number'
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
						//���
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
 * ���ܣ�����Ƿ������ֺʹ�Сд��ĸ֮����ַ���
 * �÷���checkCommonLetter(form.txtName.value)
 * ˵�����Ѿ����зǿյ���֤,����Χ��0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ��
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
 * ���ܣ�����Ƿ��д�Сд��ĸ֮����ַ���
 * �÷���checkBigLetter(form.txtName.value)
 * ˵�����Ѿ����зǿյ���֤������Χ��0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ��
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
 * ���ܣ����s�е��ַ��Ƿ���bag�е��ַ�ƥ��
 * �÷���checkCharsNotInBag (s, bag)
 * ˵����
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
 * ���ܣ����ÿؼ���ֵ�Ƿ�������
 * �÷���checkNotChinese(form.txtName.value)
 * ˵��������Χ��ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789><,[]{}?/+=|\\'\":;~!#$%()`��
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
 * ���ܣ����绰����
 * �÷���checkPhone(form.txtName.value)
 * ˵�����Ѿ����зǿյ���֤����Χ��1234567890()-��
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
 * ���ܣ�����Ƿ�Ϊ�����ַ
 * �÷���checkEmail(form.txtName.value)
 * ˵�����Ѿ����зǿ���֤
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
 * �ж����֤����ĺϷ���
 * @param strIdNo ���֤����
 * @return boolean �Ϸ� - true; ���Ϸ� - false
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
 * ���ܣ�����Ƿ�Ϊ����
 * �÷���checkInteger(form.txtName.value)
 * ˵����
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
 * ���ܣ�����Ƿ�Ϊ������
 * �÷���checkPosInteger(form.txtName.value)
 * ˵����
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
 * ���ܣ�����Ƿ��Ǹ�����
 * �÷���checkNumber(form.txtName.value)
 * ˵����
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
 * ���ܣ�����Ƿ�����������
 * �÷���checkPosNumber(form.txtName.value)
 * ˵����
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
 * ���ܣ�����Ƿ�Ϊ����
 * �÷���checkDate(form.txtName.value)
 * ˵��������ֵ��0����������  1��������;ʱ���ʽΪ2003-12-12
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
 * ���ܣ�У��ʱ�� 
 * �÷���checkTime(form.txtName)
 * ˵����ʱ��ĸ�ʽ��׼��ʽΪ"20:12:13"
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
 * ���ܣ��Ƚ�����ʱ��form.tmBegin.valueӦ����form.tmEnd.value֮ǰ
 * �÷���compareTime(form.tmBegin.value,form.tmEnd.value, strAlert)
 * ˵�������ڵı�׼��ʽΪ"20:12:13",strAlertΪ������ʾ��Ϣ
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
 * ���ܣ��Ƚ����������Ⱥ�form.dtStart.valueӦ����form.dtEnd.value֮ǰ
 * �÷���checkCompareDate(form.dtStart.value,form.dtEnd.value)
 * ˵�������form.dtStart.value>form.dtEnd.value������false,���򷵻�true;���ڵı�׼��ʽΪ"2003-12-13"
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
 * ���ܣ��Ƚ��������ִ�С
 * �÷���formatDrpNumber(form.txtName1.value,form.txtName2.value)
 * ˵�������form.txtName1.value>form.txtName2.value,����false,���򷵻�true
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
 * ���ܣ�����һ�����飬���磺strData="[5,3]",�򷵻�'[','5','3',']'����������
 * �÷���analyze(strData)
 * ˵�������磺strData="[5,3]",�򷵻�'[','5','3',']'����������
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

/********************* ������غ��� Start *******************/
/**
 * ���ܣ����ص�ǰ����
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
 * ���ܣ����ص�ǰʱ��
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
 * ���ܣ�����Ƿ���������
 * �÷���validateDate(form.txtName, ���ڱ�����ʽ) 
 * ˵�������ڱ�����ʽ��Ϊ:"2003-12-13"��"2003��12��13��"��"12/13/2003"�ȣ������м�ļ�������д,���ڵ�λ��Ҳ����Ϊ:"������"��"������"����ʽ
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
 * ���ܣ� �鿴��,��,���Ƿ���������Χ����
 * �÷���isValidDate(day, month, year)
 * ˵������,��,�յķ�Χ����ʵ�����еķ�Χ
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
 * ���ܣ���������=dtBase+lTerm�������,lTermΪ���뼶ʱ��
 * �÷���getTime("2003-12-13",lTerm)
 * ˵����lTerm����Ϊ������Ҳ����Ϊ������;����һ��"2003-12-13"��ʽ������
 */
function getTime(dtBase,lTerm)
{
	var vDate = analyzeDate(dtBase);
	var calendar = new Date(vDate[0],vDate[1]-1,vDate[2],0,0,0);
	
	if(checkInteger(lTerm) == false)
	{
		return "";
	}
	
	//һ��ĺ�����
	var lOneTime = 24*3600*1000;

	var lSumTime = lTerm*lOneTime;

	//ȡ�����ڵĺ�����
	lSumTime = calendar.getTime()+lSumTime;

	var newCalendar = new Date();
	newCalendar.setTime(lSumTime);

	return getDateString(newCalendar);
}

/**
 * ���ܣ����㹤����=dtBase+lTerm�������,lTermΪ���뼶ʱ��(����)
 * �÷���getTime("2003-12-13",lTerm)
 * ˵����lTerm����Ϊ������Ҳ����Ϊ������;����һ��"2003-12-13"��ʽ������;���������ǲ�����ĩ��;
 */
function getWorkTime(dtBase,lTerm)
{
	var vDate = analyzeDate(dtBase);
	var calendar = new Date(vDate[0],vDate[1]-1,vDate[2],0,0,0);
	
	if(checkInteger(lTerm) == false)
	{
		return "";
	}
	
	//һ��ĺ�����
	var lOneTime = 24*3600*1000;	
	
	if(lTerm == 0)
	{
		var day = calendar.getDay();
		//�������ĩ
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
		//���������ĩ
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
 * ���ܣ���"2003-12-13"��������"�ꡢ�¡���"��һ�����鱣������
 * �÷���analyzeDate(dtBase)
 * ˵�������ص������һ��Ԫ�ش�ŵ����գ��ڶ�������,����������;
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
 * ���ܣ���һ��calendar����ת����"2003-12-13"��ʽ������
 * �÷���getDateString(calendar)
 * ˵��������"2003-12-13"��ʽ������
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

/********************* ������غ��� End ********************/
/**********************************************************/
/**
 * ���ܣ�ʹ��������ָ������ɫ
 * �÷���myColor(form.txtName)
 * ˵��������������������ʱ�ı������ɫ"#FFFFCC"��
 */
function myMistakeColor(object)
{
	if(object != null)
	{
		object.style.backgroundColor = "#FFFFCC";
	}
}
/**
 * ���ܣ�ʹ��������ָ������ɫ
 * �÷���myColor(form.txtName)
 * ˵����������������������ʱ�ı������ɫ"#FFFFFF"��
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
 * ȥ���ַ������˵Ŀո�
 * @param strSrc Ҫȥ���ո���ַ���
 * @return ȥ���ո����ַ���
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
 * �����ַ�����ʵ�ʳ���(һ���������������ַ�)
 * @param strSrc Ҫ������ַ���
 * @return ʵ�ʳ���(һ���������������ַ�)
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
 * �ж��Ƿ�ֻ��һ��checkboxѡ��
 * @param elem Ҫ�жϵ�checkbox
 * @return ����һ��ѡ�е�checkbox��ֵ
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
		alert("����ѡ��һ��������¼��");
		return;
	}
	if(t > 1){
		alert("һ��ֻ���Բ���һ����¼��");
		return;
	}

	return returnValue;
}

/**
 * ɾ��������ר�÷���
 * @param elem Ҫ�жϵ�checkbox
 * @return ����һ��ѡ�е�checkbox��ֵ��ƴ��
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
		alert("����ѡ��һ����¼��"); 
		return;
	}

	return returnValue;
}