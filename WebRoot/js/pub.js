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
		alert("����\n��ʼ���ڲ��ܴ�����ֹ���ڣ�");
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
 * ���ܣ����ص�ǰ��������
 */
function showDateChinese()
{
  var d = new Date();
  var s = d.getYear() + "��";
  s += (d.getMonth() + 1) + "��";
  s += d.getDate() + "��";
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
		alert("����ѡ��һ��ɾ����¼��"); 
		return null;
	}

	if (!confirm("ȷ��Ҫɾ����Щ��¼��?"))
		return null;
	return returnValue;
}

/**
 * У������
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
					alert(fieldCaption + " ����������!");
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
 * �ж��Ƿ�Ϊ�շ���
 * @param pageField Ҫ�жϵ�field
 * @param fieldCaption Ҫ�жϵ�field��
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
// ����/��ʾ
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
 * �����鿴
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
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "�����鿴", winStyle);
	//}
}

/**
 * ģ�帽���༭
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
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "�����鿴", winStyle);
	}
}

/**
 * ���̸����鿴
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
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype, "�����鿴", winStyle);
	}
}

/**
 * �����鿴(�ļ��������ļ����)
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
		a=window.open("../GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype + "&recid="+recid, "�����鿴", winStyle);
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
 * �����鿴(��ҳ)
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
		a=window.open("./GGDownload?filename=" + filename + "&fileUrl=" + fileUrl + "&contenttype=" + contenttype + "&filetype="+filetype + "&recid="+recid, "�����鿴", winStyle);
//	}
	if (filetype == "FzFileSend")
	{
		document.location.reload();
	}
}

/**
 * ѡ����
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����(�������ʹ�ã�ѡ��һ������)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����(�������ʹ�ã�ѡ��һ������)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����(�������ʹ�ã�ѡ��һ������)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����(�������ʹ�ã�ѡ��������)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param roleid ��ɫID
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
 * ѡ����Ա(�������ʹ�ã�ѡ��һ����Ա)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����Ա(�������ʹ�ã�ѡ��һ����Ա)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����Ա(�������ʹ�ã�ѡ��һ����Ա)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����Ա(�������ʹ�ã�ѡ������Ա)
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
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
 * ѡ����Ȩ�޵���Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param moduleflag ģ����
 * @param submodflag ��Ŀ��
 * @param funcflag ������
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
 * ��ȥ�����ı�����������ͷβ�Ŀո�
 * @param pageField Ҫ�жϵ�field
 * @param fieldCaption Ҫ�жϵ�field��
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

// תFzFileSend
function sendfile(formtype, filetype) {
	var form = document.forms[0];	
	var recid = updateRecord(form.recid);
	if (recid == null)  return;
	// ѡ����Ա
	a=findUser(form.sender,form.sendername,"","");
	if (form.sender.value != null && form.sender.value != "") {
		form.action = "../gwgl/FzFileSend.do?operate=savefromothers&operPage=fz_FileSend_list_send&fzFileSend.type="+formtype+"&fzFileSend.title="+recid.split("@")[1]+"&fzFileSend.sourceId="+recid.split("@")[0]+"&fzFileSend.maxSeq="+recid.split("@")[2]+"&fzFileSend.userid="+form.sender.value+"&fzFileSend.username="+form.sendername.value+"&formtype="+formtype+"&filetype="+filetype; 
		form.submit(); 
	}
}

// תFzFileSend
function viewsendfile(recid,formtype, filetype) {
	var form = document.forms[0];	
	var title = form.title.value;
	var maxSeq = form.maxSeq.value;
	// ѡ����Ա
	a=findUser(form.sender,form.sendername,"","");
	if (form.sender.value != null && form.sender.value != "") {
		form.action = "../gwgl/FzFileSend.do?operate=savefromothers&operPage=fz_FileSend_list_send&fzFileSend.type="+formtype+"&fzFileSend.title="+title+"&fzFileSend.sourceId="+recid+"&fzFileSend.maxSeq="+maxSeq+"&fzFileSend.userid="+form.sender.value+"&fzFileSend.username="+form.sendername.value+"&formtype="+formtype+"&filetype="+filetype; 
		form.submit(); 
	}
}

// תFzFileView
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
		alert("����ѡ��һ��������¼��");
		return;
	}
	if(t > 1){
		alert("һ��ֻ���Բ���һ����¼��");
		return;
	}
	if (returnValue == null)  return;
	// ѡ������
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
//ȫѡ��ȡ����ǰҳ��List�б��
function checkAll() {
	var currentForm = document.forms[0];
	bCheck = !bCheck;
	var ChkEls = document.getElementsByName("recid");
	for( var i=0;i<ChkEls.length;i++ ) {
		ChkEls.item(i).checked = bCheck;
	}
	if( bCheck )
		currentForm.idCheckAll.alt = "ȫ��ȡ��";
	else
		currentForm.idCheckAll.alt = "ȫ��ѡ��";
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
 * �ж��Ƿ�ֻ��һ��checkboxѡ��
 * @param elem Ҫ�жϵ�checkbox
 * @return ����һ��ѡ�е�checkbox��ֵ
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
		alert("����ѡ��һ��������¼��");
		return;
	}
	if(t > 1){
		alert("һ��ֻ���Բ���һ����¼��");
		return;
	}

	return new Array(returnValue,hiddenReturn);
}

/**
 * ѡ���¼������ר�÷���
 * @param elem Ҫ�жϵ�checkbox
 * @return ����һ��ѡ�е�checkbox��ֵ��ƴ��
 */
function selectRecord(elem)
{
	var lean = 0;
	var returnValue = "('";

	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		alert("����Ҫѡ��2����¼�ϲ�!");
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
		alert("����Ҫѡ��2����¼�ϲ�!"); 
		return null;
	}
	return returnValue;
}
// ����
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


/**  ѡ����
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param domainId ��id (1��Ϊ�����ѯ��ǰ�û������� 2��Ϊall���ѯȫ�� 3�����Ҫ��ѯ���������","���� example:domainId1,domainId2)
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
 * ѡ����Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param roleid  ����ɫ��ʽ��ѯʱ��ɫID (���Ҫ��ѯ�����ɫ����","���� example:roleid1,roleid2 ���Ҫ��ѯ���У���Ϊ��)
 * @param domainId ��id (1��Ϊ�����ѯ��ǰ�û������� 2��Ϊall���ѯȫ�� 3�����Ҫ��ѯ���������","���� example:domainId1,domainId2)
 * @param extflag ���Ϊtrue ���ʾ�������domainIdΪ�ų��������id 
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
 * ѡ����Ȩ�޵���Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param moduleflag ģ����
 * @param submodflag ��Ŀ��
 * @param funcflag ������
 * @param domainId ��id (1��Ϊ�����ѯ��ǰ�û������� 2��Ϊall���ѯȫ�� 3�����Ҫ��ѯ���������","���� example:domainId1,domainId2)
 * @param extflag ���Ϊtrue ���ʾ�������domainIdΪ�ų��������id  
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
 * ѡ����Ա�����ţ���ɫ����������
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param roleid  ����ɫ��ʽ��ѯʱ��ɫID (���Ҫ��ѯ�����ɫ����","���� example:roleid1,roleid2 ���Ҫ��ѯ���У���Ϊ��)
 * @param domainId ��id (1��Ϊ�����ѯ��ǰ�û������� 2��Ϊall���ѯȫ�� 3�����Ҫ��ѯ���������","���� example:domainId1,domainId2)
 * @param extflag ���Ϊtrue ���ʾ�������domainIdΪ�ų��������id 
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
 * ѡ����Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param roleid ��ɫID
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
 * ѡ����Ա
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param domainId ��ID
 * @param filtrate ������� ����com.kingtake.flowdesigner.bean.WorkFlowConstant�� ��ַ�������Ա�ʶ������id��*�Ÿ�������ͬ�����Թ���������","������
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
 * ѡ����Ա,��deptflag ���жϵ���ʱĬ���ǲ��Ż��ǽ�ɫ��
 *
 * @param code Ҫ��д��field��
 * @param name Ҫ��д��fieldname��
 * @param domainId ��ID
 * @param type �Ƿ���Զ�ѡone:ֻ��ѡһ��
 * @param deptflag ���ű�ʶ����'dept'ʱ��Ĭ�ϵ������ţ���'role'ʱ��Ĭ�ϵ�����ɫ��
 * @param filtrate ������� ����com.kingtake.flowdesigner.bean.WorkFlowConstant�� ��ַ�������Ա�ʶ������id��*�Ÿ�������ͬ�����Թ���������","������
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
function send_request(url, xss, firstflag) {//��ʼ����ָ������������������ĺ���
	http_request = false;
	this.objselect = xss;
	flag = firstflag;
	//��ʼ��ʼ��XMLHttpRequest����
	if(window.XMLHttpRequest) { //Mozilla �����
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {//����MiME���
			http_request.overrideMimeType('text/xml');
		}
	}
	else if (window.ActiveXObject) { // IE�����
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	
	if (!http_request) { // �쳣����������ʵ��ʧ��
		window.alert("���ܴ���XMLHttpRequest����ʵ��.");
		return false;
	}
	http_request.onreadystatechange = processRequest;
	// ȷ����������ķ�ʽ��URL�Լ��Ƿ�ͬ��ִ���¶δ���
	http_request.open("GET", url, true);
	http_request.send(null);
}
// ��������Ϣ�ĺ���
function processRequest() 
{
	if (http_request.readyState == 4) 
	{ // �ж϶���״̬
		if (http_request.status == 200) 
		{ // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var xmlobj = http_request.responseText;
			var xmldoc=new ActiveXObject("MSXML2.DOMDocument");
			xmldoc.loadXML(xmlobj);
			var oNodes = xmldoc.selectSingleNode("//xml");

			var xsselect = objselect.split("@");
			for (var i = 0; i < xsselect.length; i++)
			{
				xsselect[i] = eval('document.all.'+ xsselect[i]);
				// ɾ��ԭ����options
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
						// ��һ��option�ÿ�
						if (flag == "true")
						{			
							xsselect[j].options[0] = null;
						}
						// ѭ������option
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
		{ //ҳ�治����
			alert("���������ҳ�����쳣��");
		}
	}
}
/**
 * ���ܣ�����û���Ȩ��,����true��ʾ��Ȩ��,false��ʾû��Ȩ��
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
//�����Ҽ�
//document.oncontextmenu=new Function("event.returnValue=false");
//����Ctrl+A
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
