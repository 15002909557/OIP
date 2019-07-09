document.onkeydown = nextFocus;	// work together to analyze keystrokes 
/**
 * 跳转到下一个焦点
 *
 * 日期:2005-06-02
 * 作者:徐知宝
 */
function nextFocus()
{
	var elem = window.event.srcElement;

	if (window.event.keyCode != 13) return;
	if (elem.type == 'textarea') return;

	if (isSubmitType(elem.type)){		
		return true;
	}

	var begin = elem.sourceIndex + 1;
	var end = document.all.length; 

	var nextelem = getNextFocus(begin, end);
	if (nextelem == null) {
		nextelem = getNextFocus(0, begin);
	}

	if (isSubmitType(nextelem.type)) {
		nextelem.focus();
		//elem.click();
		return false;
	} else {
		nextelem.focus();
		return true;
	}

}

/**
 * 获取下一个焦点
 *
 * 日期:2005-06-02
 * 作者:徐知宝
 */
function getNextFocus(begin, end)
{
	var elem = null;

	for (var i = begin; i < end; i++) {
		elem = document.all[i];
		if (elem.type == null) continue;
		if (isFocusType(elem.type) && !elem.disabled) {
			if (elem.readOnly != null && elem.readOnly) continue;
			try {
				return elem;
			} catch (e) {
				continue;
			}
		}
	}

	return null;
}

/**
 * 是否是获取焦点的类型
 *
 * 日期:2005-06-02
 * 作者:徐知宝
 */
function isFocusType(type)
{
	var types = new Array(
		"button",
		"checkbox",
		"file",
		"image",
		"password",
		"radio",
		"reset",
		"submit",
		"text",
		"textarea",
		"select-multiple",
		"select-one");

	for (var i = 0; i < types.length; i++) {
		if (type == types[i]) return true;
	}

	return false;
}

/**
 * 是否为默认提交元素
 *
 * 日期:2005-06-02
 * 作者:徐知宝
 */
function isSubmitType(type)
{
	var types = new Array("submit","button","image");

	for (var i = 0; i < types.length; i++) {
		if (type == types[i]) return true;
	}

	return false;
}